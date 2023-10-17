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
import br.com.crista.fashion.dto.VendaDTO;
import br.com.crista.fashion.utils.DateUtils;

@Repository("listaVendasRepositoryImpl")
public class ListaVendasRepositoryImpl {

    @PersistenceContext
    EntityManager entityManager;

    public List<VendaDTO> getListaVendas(FiltroRelatorioDTO filtro) {

        String sql = " " +
                " SELECT " +
                "   to_char(v.data_venda, 'YYYY-MM-DD') as data, " +
                "   v.valor_produto, " +
                "   v.tipo, " +
                "   v.frete_pagar, " +
                "   v.frete_receber, " +
                "   v.descontos," +
                "   v.comissao, " +
                "   v.valor_total, " +
                "   v.status, " +
                "   p.categoria " +
                " FROM " +
                "   venda v " +
                " JOIN produto p " +
                "   ON p.id = v.produto_id " +
                " WHERE 1=1 ";

        if (nonNull(filtro.getDataInicio())) {

            sql += " AND v.data_venda >= :data_inicial ";
        }

        if (nonNull(filtro.getDataFim())) {

            sql += " AND v.data_venda <= :data_final ";
        }

        if (nonNull(filtro.getCategoria()) && !filtro.getCategoria().equalsIgnoreCase(TODAS)) {

            sql += " AND p.categoria =:categoria ";
        }

        if (nonNull(filtro.getTipo()) && !filtro.getTipo().equalsIgnoreCase(TODAS)) {

            sql += " AND v.tipo =:tipo ";
        }

        sql += " ORDER BY v.data_venda DESC";

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

        if (nonNull(filtro.getDataInicio())) {

            query.setParameter("data_inicial", DateUtils.zeraHorario(filtro.getDataInicio()));
        }

        if (nonNull(filtro.getDataFim())) {

            query.setParameter("data_final", DateUtils.setUltimaHoraDoDia(filtro.getDataFim()));
        }

        if (nonNull(filtro.getTipo()) && !filtro.getTipo().equalsIgnoreCase(TODAS)) {

            query.setParameter("tipo", filtro.getTipo());
        }

        if (nonNull(filtro.getCategoria()) && !filtro.getCategoria().equalsIgnoreCase(TODAS)) {

            query.setParameter("categoria", filtro.getCategoria());
        }
    }
}