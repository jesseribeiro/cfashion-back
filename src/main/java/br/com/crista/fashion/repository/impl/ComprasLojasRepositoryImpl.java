package br.com.crista.fashion.repository.impl;

import br.com.crista.fashion.dto.ComprasDTO;
import br.com.crista.fashion.dto.FiltroRelatorioDTO;
import br.com.crista.fashion.utils.DateUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Repository("comprasLojasRepositoryImpl")
public class ComprasLojasRepositoryImpl {

    @PersistenceContext
    EntityManager entityManager;

    public List<ComprasDTO> getComprasLojas(FiltroRelatorioDTO filtro) {

        String sql = " " +
                " SELECT " +
                "   to_char(v.data_venda, 'YYYY-MM-DD') as data, " +
                "   v.tipo, " +
                "   v.valor_total, " +
                "   v.status, " +
                "   p.categoria, " +
                "   l.nome_fantasia, " +
                "   l.id " +
                " FROM " +
                "   venda v " +
                " JOIN produto p " +
                "   ON p.id = v.produto_id " +
                " JOIN loja l " +
                "   ON l.id = v.loja_id " +
                " WHERE 1=1 ";

        if (nonNull(filtro.getDataInicio())) {

            sql += " AND v.data_venda >= :data_inicial ";
        }

        if (nonNull(filtro.getDataFim())) {

            sql += " AND v.data_venda <= :data_final ";
        }

        if (nonNull(filtro.getTipo()) && isFalse(filtro.getTipo().equalsIgnoreCase("TODAS"))) {

            sql += " AND v.tipo =:tipo ";
        }

        if (nonNull(filtro.getCategoria()) && isFalse(filtro.getCategoria().equalsIgnoreCase("TODAS"))) {

            sql += " AND p.categoria =:categoria ";
        }

        if (nonNull(filtro.getStatus()) && isFalse(filtro.getStatus().equalsIgnoreCase("TODAS"))) {

            sql += " AND v.status =:status ";
        }

        if (nonNull(filtro.getLojaId()) && isFalse(filtro.getLojaId() == -1)) {

            sql += " AND l.id =:loja_id ";
        }


        sql += " ORDER BY l.id ASC, v.data_venda DESC";

        Query query = entityManager.createNativeQuery(sql);
        addFilters(filtro, query);

        List<ComprasDTO> listaResultados = new ArrayList<>();
        List<Object[]> listaObjects = query.getResultList();

        for (Object[] line : listaObjects) {

            ComprasDTO compras = new ComprasDTO();

            try {

                compras.setDataVenda((String) line[0]);
                compras.setTipo((String) line[1]);
                compras.setValor((BigDecimal) line[2]);
                compras.setStatus((String) line[3]);
                compras.setCategoria((String) line[4]);
                compras.setNomeLoja((String) line[5]);
                compras.setLojaId(((BigInteger) line[6]).longValue());

                listaResultados.add(compras);

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

        if (nonNull(filtro.getTipo()) && isFalse(filtro.getTipo().equalsIgnoreCase("TODAS"))) {

            query.setParameter("tipo", filtro.getTipo());
        }

        if (nonNull(filtro.getCategoria()) && isFalse(filtro.getCategoria().equalsIgnoreCase("TODAS"))) {

            query.setParameter("categoria", filtro.getCategoria());
        }

        if (nonNull(filtro.getStatus()) && isFalse(filtro.getStatus().equalsIgnoreCase("TODAS"))) {

            query.setParameter("status", filtro.getStatus());
        }

        if (nonNull(filtro.getLojaId()) && isFalse(filtro.getLojaId() == -1)) {

            query.setParameter("loja_id", filtro.getLojaId());
        }
    }
}