package br.com.crista.fashion.repository.impl;

import static br.com.crista.fashion.utils.Constants.TODAS;
import static java.util.Objects.nonNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.crista.fashion.dto.FiltroRelatorioDTO;
import br.com.crista.fashion.dto.ProdutoDTO;
import br.com.crista.fashion.utils.Constants;
import br.com.crista.fashion.utils.DateUtils;

@Repository("listaProdutosRepositoryImpl")
public class ListaProdutosRepositoryImpl {

    @PersistenceContext
    EntityManager entityManager;

    public List<ProdutoDTO> getListaProdutos(FiltroRelatorioDTO filtro) {

        String sql = " " +
                " SELECT " +
                "   p.data_cadastro, " +
                "   p.nome, " +
                "   p.categoria, " +
                "   p.codigo, " +
                "   p.cor, " +
                "   p.tamanho, " +
                "   p.qtd, " +
                "   p.valor_compra, " +
                "   l.nome_fantasia " +
                " FROM " +
                "   produto p " +
                " JOIN loja l " +
                "   ON l.id = p.marca_id " +
                " WHERE 1=1 ";

        if (nonNull(filtro.getMarcaId()) && filtro.getMarcaId().intValue() != Constants.SELECT_TODAS) {

            sql += " AND p.marca_id =:loja_id ";
        }

        if (nonNull(filtro.getCategoria()) && !filtro.getCategoria().equalsIgnoreCase(TODAS)) {

            sql += " AND p.categoria =:categoria ";
        }

        if (filtro.getTemEstoque()) {

            sql += " AND p.qtd > 0 ";
        }

        if (nonNull(filtro.getDataInicio())) {

            sql += " AND p.data_cadastro >= :data_inicial ";
        }

        if (nonNull(filtro.getDataFim())) {

            sql += " AND p.data_cadastro <= :data_final ";
        }

        sql += " ORDER BY p.codigo ASC";

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

        if (nonNull(filtro.getMarcaId()) && filtro.getMarcaId().intValue() != Constants.SELECT_TODAS) {

            query.setParameter("loja_id", filtro.getMarcaId());
        }

        if (nonNull(filtro.getDataInicio())) {

            query.setParameter("data_inicial", DateUtils.zeraHorario(filtro.getDataInicio()));
        }

        if (nonNull(filtro.getDataFim())) {

            query.setParameter("data_final", DateUtils.setUltimaHoraDoDia(filtro.getDataFim()));
        }

        if (nonNull(filtro.getCategoria()) && !filtro.getCategoria().equalsIgnoreCase(TODAS)) {

            query.setParameter("categoria", filtro.getCategoria());
        }
    }
}