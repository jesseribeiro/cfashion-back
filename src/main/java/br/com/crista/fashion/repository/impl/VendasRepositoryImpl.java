package br.com.crista.fashion.repository.impl;

import br.com.crista.fashion.dto.FiltroRelatorioDTO;
import br.com.crista.fashion.dto.VendaDTO;
import br.com.crista.fashion.utils.DateUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository("vendasRepositoryImpl")
public class VendasRepositoryImpl {

    @PersistenceContext
    EntityManager entityManager;

    public List<VendaDTO> getListaVendas(FiltroRelatorioDTO filtro) {
        String sql = " select to_char(v.data_venda, 'YYYY-MM-DD') as data, v.valor_produto, " +
                " v.tipo, v.frete_pagar, v.frete_receber, " +
                " v.descontos, v.comissao, v.valor_total, v.status, p.categoria " +
                " from venda v " +
                " join produto p on p.id = v.produto_id " +
                " where 1=1 ";

        if (filtro.getDataInicio() != null) {
            sql += " and v.data_venda >= :data_inicial ";
        }
        if (filtro.getDataFim() != null) {
            sql += " and v.data_venda <= :data_final ";
        }
        if (filtro.getCategoria() != null && !filtro.getCategoria().equalsIgnoreCase("TODAS")) {
            sql += " and p.categoria =:categoria ";
        }
        if (filtro.getTipo() != null && !filtro.getTipo().equalsIgnoreCase("TODAS")) {
            sql += " and v.tipo =:tipo ";
        }

        sql += " order by v.data_venda ";

        Query query = entityManager.createNativeQuery(sql);
        addFilters(filtro, query);

        List<VendaDTO> listaResultados = new ArrayList<>();
        List<Object[]> listaObjects = query.getResultList();
        for (Object[] line : listaObjects) {
            VendaDTO venda = new VendaDTO();
            try {
                venda.setData((String) line[0]);
                venda.setVlProduto((BigDecimal) line[1]);
                venda.setTipo((String) line[2]);
                venda.setFretePagar((BigDecimal) line[3]);
                venda.setFreteReceber((BigDecimal) line[4]);
                venda.setDescontos((BigDecimal) line[5]);
                venda.setComissao((BigDecimal) line[6]);
                venda.setVlTotal((BigDecimal) line[7]);
                venda.setStatus((String) line[8]);
                venda.setCategoria((String) line[9]);
                listaResultados.add(venda);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listaResultados;
    }

    private void addFilters(FiltroRelatorioDTO filtro, Query query) {
        if (filtro.getDataInicio() != null) {
            query.setParameter("data_inicial", DateUtils.zeraHorario(filtro.getDataInicio()));
        }
        if (filtro.getDataFim() != null) {
            query.setParameter("data_final", DateUtils.setUltimaHoraDoDia(filtro.getDataFim()));
        }
        if (filtro.getTipo() != null && !filtro.getTipo().equalsIgnoreCase("TODAS")) {
            query.setParameter("tipo", filtro.getTipo());
        }
        if (filtro.getCategoria() != null && !filtro.getCategoria().equalsIgnoreCase("TODAS")) {
            query.setParameter("categoria", filtro.getCategoria());
        }
    }
}