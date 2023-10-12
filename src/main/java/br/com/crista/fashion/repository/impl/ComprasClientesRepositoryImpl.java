package br.com.crista.fashion.repository.impl;

import br.com.crista.fashion.dto.ComprasDTO;
import br.com.crista.fashion.dto.FiltroRelatorioDTO;
import br.com.crista.fashion.utils.DateUtils;
import br.com.crista.fashion.utils.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Repository("vendasRepositoryImpl")
public class ComprasClientesRepositoryImpl {

    @PersistenceContext
    EntityManager entityManager;

    public List<ComprasDTO> getComprasClientes(FiltroRelatorioDTO filtro) {

        String sql = " " +
                " SELECT " +
                "   to_char(v.data_venda, 'YYYY-MM-DD') as data, " +
                "   v.tipo, " +
                "   v.valor_total, " +
                "   v.status, " +
                "   p.categoria, " +
                "   c.nome, " +
                "   c.cpf, " +
                "   c.cidade_nome " +
                " FROM " +
                "   venda v " +
                " JOIN produto p " +
                "   ON p.id = v.produto_id " +
                " JOIN cliente c " +
                "   ON c.id = v.cliente_id " +
                " WHERE 1=1 ";

        if (nonNull(filtro.getDataInicio())) {

            sql += " AND v.data_venda >= :data_inicial ";
        }

        if (nonNull(filtro.getDataFim())) {

            sql += " AND v.data_venda <= :data_final ";
        }

        if (nonNull(filtro.getTipo()) && !filtro.getTipo().equalsIgnoreCase("TODAS")) {

            sql += " AND v.tipo =:tipo ";
        }

        if (nonNull(filtro.getCategoria()) && !filtro.getCategoria().equalsIgnoreCase("TODAS")) {

            sql += " AND p.categoria =:categoria ";
        }

        if (nonNull(filtro.getStatus()) && !filtro.getStatus().equalsIgnoreCase("TODAS")) {

            sql += " AND v.status =:status ";
        }

        sql += " AND (:cpf is null OR c.cpf LIKE CONCAT('%',:cpf, '%')) " +
               " AND (:nome is null OR LOWER(c.nome) LIKE LOWER(CONCAT('%',:nome, '%'))) " +
               " ORDER BY c.nome ASC, v.data_venda DESC";

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
                compras.setNomeCliente((String) line[5]);
                compras.setCpf((String) line[6]);
                compras.setCidade((String) line[7]);

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

        if (nonNull(filtro.getTipo()) && !filtro.getTipo().equalsIgnoreCase("TODAS")) {

            query.setParameter("tipo", filtro.getTipo());
        }

        if (nonNull(filtro.getCategoria()) && !filtro.getCategoria().equalsIgnoreCase("TODAS")) {

            query.setParameter("categoria", filtro.getCategoria());
        }

        if (nonNull(filtro.getStatus()) && !filtro.getStatus().equalsIgnoreCase("TODAS")) {

            query.setParameter("status", filtro.getStatus());
        }

        query.setParameter("cpf", nonNull(filtro.getCpf()) ? StringUtils.removePontos(filtro.getCpf()) : null);
        query.setParameter("nome", filtro.getNomeCliente());
    }
}