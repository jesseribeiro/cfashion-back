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

import static java.util.Objects.nonNull;

@Repository("listaMovimentacaoRepositoryImpl")
public class ListaMovimentacaoRepositoryImpl {

    @PersistenceContext
    EntityManager entityManager;

    public List<MovimentacaoDTO> getListaMovimentacao(FiltroRelatorioDTO filtro) {

        String sql = " " +
                " SELECT " +
                "   * " +
                " FROM ( " +

                "   SELECT " +
                "       to_char(m.data_lancamento, 'YYYY-MM-DD') as data, " +
                "       m.tipo as tipo, " +
                "       m.valor, " +
                "       m.descricao " +
                "   FROM " +
                "       movimentacao m " +

                "   UNION ALL " +

                "   SELECT " +
                "       to_char(v.data_venda, 'YYYY-MM-DD') as data, " +
                "       'ENTRADA' as tipo, " +
                "       v.valor_produto, " +
                "       CONCAT('Venda - ', c.nome) " +
                "   FROM " +
                "       venda v " +
                "   JOIN cliente c " +
                "       ON c.id = v.cliente_id " +

                " ) AS x" +
                " WHERE 1=1 ";

        if (nonNull(filtro.getDataInicio())) {

            sql += " AND x.data >= :data_inicial ";
        }

        if (nonNull(filtro.getDataFim())) {

            sql += " AND x.data <= :data_final ";
        }

        if (nonNull(filtro.getTipo())) {

            sql += " AND x.tipo =:tipo ";
        }

        sql += " ORDER BY x.data DESC";

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

        if (nonNull(filtro.getDataInicio())) {

            query.setParameter("data_inicial", DateUtils.zeraHorario(filtro.getDataInicio()));
        }

        if (nonNull(filtro.getDataFim())) {

            query.setParameter("data_final", DateUtils.setUltimaHoraDoDia(filtro.getDataFim()));
        }

        if (nonNull(filtro.getTipo())) {

            query.setParameter("tipo", filtro.getTipo());
        }
    }
}