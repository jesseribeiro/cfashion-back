package br.com.crista.fashion.repository.impl;

import br.com.crista.fashion.dto.FiltroRelatorioDTO;
import br.com.crista.fashion.dto.MovimentacaoDTO;
import br.com.crista.fashion.utils.DateUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository("listaMovimentacaoRepositoryImpl")
public class ListaMovimentacaoRepositoryImpl {

    @PersistenceContext
    EntityManager entityManager;

    public List<MovimentacaoDTO> getListaMovimentacao(FiltroRelatorioDTO filtro) {
        String sql = " select * from ( " +

                " select to_char(m.data_lancamento, 'YYYY-MM-DD') as data, m.tipo as tipo, m.valor, m.descricao from movimentacao m " +

                " UNION ALL " +

                " select to_char(v.data_venda, 'YYYY-MM-DD') as data, 'ENTRADA' as tipo, v.valor_produto, CONCAT('Venda - ', c.nome) from venda v " +
                " join cliente c on c.id = v.cliente_id " +
                " ) as x" +
                " where 1=1 ";

        if (filtro.getDataInicio() != null) {
            sql += " and x.data >= :data_inicial ";
        }
        if (filtro.getDataFim() != null) {
            sql += " and x.data <= :data_final ";
        }
        if (filtro.getTipo() != null) {
            sql += " and x.tipo =:tipo ";
        }

        sql += " order by x.data ";

        Query query = entityManager.createNativeQuery(sql);
        addFilters(filtro, query);

        List<MovimentacaoDTO> listaResultados = new ArrayList<>();
        List<Object[]> listaObjects = query.getResultList();
        for (Object[] line : listaObjects) {
            MovimentacaoDTO movimentacao = new MovimentacaoDTO();
            try {
                movimentacao.setData((String) line[0]);
                movimentacao.setTipo((String) line[1]);
                movimentacao.setValor((BigDecimal) line[2]);
                movimentacao.setDescricao((String) line[3]);
                listaResultados.add(movimentacao);
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
        if (filtro.getTipo() != null) {
            query.setParameter("tipo", filtro.getTipo());
        }
    }
}