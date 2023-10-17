package br.com.crista.fashion.repository.impl;

import static java.util.Objects.nonNull;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.crista.fashion.dto.ClienteDTO;
import br.com.crista.fashion.dto.FiltroRelatorioDTO;
import br.com.crista.fashion.utils.DateUtils;

@Repository("listaClientesRepositoryImpl")
public class ListaClientesRepositoryImpl {

    @PersistenceContext
    EntityManager entityManager;

    public List<ClienteDTO> getListaClientes(FiltroRelatorioDTO filtro) {

        String sql = " " +
                " SELECT" +
                "   x.id, " +
                "   c.nome, " +
                "   c.cpf, " +
                "   c.celular, " +
                "   c.cidade_nome, " +
                "   c.estado, " +
                "   count(vid) as vendas " +
                " FROM ( " +
                "   SELECT " +
                "       c.id, " +
                "       v.id as vid " +
                "   FROM " +
                "       cliente c " +
                "   FULL OUTER JOIN venda v " +
                "       ON v.cliente_id = c.id " +
                "   WHERE 1=1 ";

        if (nonNull(filtro.getDataInicio())) {

            sql += " AND v.data_venda >= :data_inicial ";
        }

        if (nonNull(filtro.getDataFim())) {

            sql += " AND v.data_venda <= :data_final ";
        }

        sql += " ) AS x" +
               " JOIN cliente c " +
               "   ON c.id = x.id " +
               " WHERE 1=1 ";

        if (filtro.getTemEstoque()) {

            sql += " AND x.vid > 0 ";
        }

        sql += " GROUP BY x.id, c.nome, c.cpf, c.celular, c.cidade_nome, c.estado " +
               " ORDER BY ";

        if (filtro.getOrdenarClientes()) {

            sql += " vendas DESC, ";
        }

        sql += " c.nome ASC";

        Query query = entityManager.createNativeQuery(sql);
        addFilters(filtro, query);

        List<ClienteDTO> listaResultados = new ArrayList<>();
        List<Object[]> listaObjects = query.getResultList();

        for (Object[] line : listaObjects) {

            ClienteDTO clientes = new ClienteDTO();

            try {

                clientes.setId(((BigInteger) line[0]).longValue());
                clientes.setNome((String) line[1]);
                clientes.setCpf((String) line[2]);
                clientes.setCelular((String) line[3]);
                clientes.setCidade((String) line[4]);
                clientes.setEstado((String) line[5]);
                clientes.setQtd((BigInteger) line[6]);
                listaResultados.add(clientes);

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
    }
}