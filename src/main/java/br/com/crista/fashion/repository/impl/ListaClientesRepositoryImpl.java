package br.com.crista.fashion.repository.impl;

import br.com.crista.fashion.dto.ClienteDTO;
import br.com.crista.fashion.dto.FiltroRelatorioDTO;
import br.com.crista.fashion.utils.DateUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository("listaClientesRepositoryImpl")
public class ListaClientesRepositoryImpl {

    @PersistenceContext
    EntityManager entityManager;

    public List<ClienteDTO> getListaClientes(FiltroRelatorioDTO filtro) {
        String sql = " select x.id, c.nome, c.cpf, c.celular, c.cidade_nome, c.estado, count(vid) from ( " +
                " select c.id, v.id as vid " +
                " from cliente c " +
                " full outer join venda v on v.cliente_id = c.id " +
                " where 1=1 ";

        if (filtro.getDataInicio() != null) {
            sql += " and c.data_cadastro >= :data_inicial ";
        }
        if (filtro.getDataFim() != null) {
            sql += " and c.data_cadastro <= :data_final ";
        }

        sql += " ) as x " +
                " join cliente c on c.id = x.id " +
                " where 1=1 ";

        if (filtro.getTemEstoque()) {
            sql += " and x.vid > 0 ";
        }

        sql += " group by x.id, x.nome, x.cpf, x.celular, x.cidade_nome, x.estado " +
                " order by x.nome ";

        Query query = entityManager.createNativeQuery(sql);
        addFilters(filtro, query);

        List<ClienteDTO> listaResultados = new ArrayList<>();
        List<Object[]> listaObjects = query.getResultList();
        for (Object[] line : listaObjects) {
            ClienteDTO clientes = new ClienteDTO();
            try {
                clientes.setId((Long) line[0]);
                clientes.setNome((String) line[1]);
                clientes.setCpf((String) line[2]);
                clientes.setCelular((String) line[3]);
                clientes.setCidade((String) line[4]);
                clientes.setEstado((String) line[5]);
                clientes.setQtd((Integer) line[6]);
                listaResultados.add(clientes);
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
    }
}