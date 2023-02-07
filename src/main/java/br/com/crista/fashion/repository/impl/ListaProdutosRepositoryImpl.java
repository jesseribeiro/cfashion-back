package br.com.crista.fashion.repository.impl;

import br.com.crista.fashion.dto.FiltroRelatorioDTO;
import br.com.crista.fashion.dto.ProdutoDTO;
import br.com.crista.fashion.utils.Constants;
import br.com.crista.fashion.utils.DateUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository("listaProdutosRepositoryImpl")
public class ListaProdutosRepositoryImpl {

    @PersistenceContext
    EntityManager entityManager;

    public List<ProdutoDTO> getListaProdutos(FiltroRelatorioDTO filtro) {
        String sql = " Select p.data_cadastro, p.nome, p.categoria, p.codigo, p.cor, " +
                " p.tamanho, p.qtd, p.valor_compra, l.nome_fantasia " +
                " from produto p " +
                " join loja l on l.id = p.marca_id " +
                " where 1=1 ";

        if (filtro.getMarcaId() != null && filtro.getMarcaId().intValue() != Constants.SELECT_TODAS) {
            sql += " and p.marca_id =:loja_id ";
        }

        if (filtro.getCategoria() != null && !filtro.getCategoria().equalsIgnoreCase("TODAS")) {
            sql += " and p.categoria =:categoria ";
        }

        if (filtro.getTemEstoque()) {
            sql += " and p.qtd > 0 ";
        }

        if (filtro.getDataInicio() != null) {
            sql += " and p.data_cadastro >= :data_inicial ";
        }
        if (filtro.getDataFim() != null) {
            sql += " and p.data_cadastro <= :data_final ";
        }

        sql += " order by p.codigo ";

        Query query = entityManager.createNativeQuery(sql);
        addFilters(filtro, query);

        List<ProdutoDTO> listaResultados = new ArrayList<>();
        List<Object[]> listaObjects = query.getResultList();
        for (Object[] line : listaObjects) {
            ProdutoDTO produtos = new ProdutoDTO();
            try {
                produtos.setNome((String) line[1]);
                produtos.setCategoria((String) line[2]);
                produtos.setCodigo((String) line[3]);
                produtos.setCor((String) line[4]);
                produtos.setTamanho((String) line[5]);
                produtos.setQtd((Integer) line[6]);
                produtos.setValorCompra((BigDecimal) line[7]);
                produtos.setMarca((String) line[8]);
                listaResultados.add(produtos);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listaResultados;
    }

    private void addFilters(FiltroRelatorioDTO filtro, Query query) {
        if (filtro.getMarcaId() != null && filtro.getMarcaId().intValue() != Constants.SELECT_TODAS) {
            query.setParameter("loja_id", filtro.getMarcaId());
        }

        if (filtro.getDataInicio() != null) {
            query.setParameter("data_inicial", DateUtils.zeraHorario(filtro.getDataInicio()));
        }
        if (filtro.getDataFim() != null) {
            query.setParameter("data_final", DateUtils.setUltimaHoraDoDia(filtro.getDataFim()));
        }

        if (filtro.getCategoria() != null && !filtro.getCategoria().equalsIgnoreCase("TODAS")) {
            query.setParameter("categoria", filtro.getCategoria());
        }
    }
}