package br.com.crista.fashion.repository.impl;

import br.com.crista.fashion.dto.VendaDTO;
import br.com.crista.fashion.enumeration.EnumStatus;
import br.com.crista.fashion.utils.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.List;

@Repository("vendaRepositoryImpl")
public class VendaRepositoryImpl {

    @PersistenceContext
    EntityManager entityManager;

    public Page<VendaDTO> pagination(Long clienteId, Long lojaId, EnumStatus status,
                                     Calendar dataInicial, Calendar dataFinal, Pageable paging) {
        String filtros = " From Venda as x " +
                " JOIN x.loja as l " +
                " JOIN x.cliente as c " +
                " WHERE 1 = 1 ";
        if (lojaId != null) {
            filtros += " and l.id = :lojaId ";
        }
        if (clienteId != null) {
            filtros += " and c.id = :clienteId ";
        }
        if (status != null) {
            filtros += " and x.status = :status ";
        }
        if (dataInicial != null) {
            filtros += " and x.dataVenda >=:dataInicial";
        }
        if (dataFinal != null) {
            filtros += " and x.dataVenda <=:dataFinal";
        }

        Query queryTotal = entityManager.createQuery("Select count(1) " + "" + filtros);
        addParamQuery(clienteId, lojaId, status, dataInicial, dataFinal, queryTotal);
        long countResult = (long) queryTotal.getSingleResult();

        Query query = entityManager.createQuery("SELECT new br.com.crista.fashion.dto.VendaDTO(x) " + "" + filtros + " order by x.dataVenda desc");
        addParamQuery(clienteId, lojaId, status, dataInicial, dataFinal, query);
        query.setFirstResult((paging.getPageNumber()) * paging.getPageSize());
        query.setMaxResults(paging.getPageSize());

        List<VendaDTO> vendas = query.getResultList();
        return new PageImpl<>(vendas, paging, countResult);
    }

    private void addParamQuery( Long clienteId, Long lojaId, EnumStatus status, Calendar dataInicial, Calendar dataFinal, Query query) {
        if (lojaId != null) {
            query.setParameter("lojaId", lojaId);
        }
        if (clienteId != null) {
            query.setParameter("clienteId", clienteId);
        }
        if (status != null) {
            query.setParameter("status", status);
        }
        if (dataInicial != null) {
            dataInicial = DateUtils.zeraHorario(dataInicial);
            query.setParameter("dataInicial", dataInicial);
        }
        if (dataFinal != null) {
            dataFinal = DateUtils.setUltimaHoraDoDia(dataFinal);
            query.setParameter("dataFinal", dataFinal);
        }
    }

    public List<VendaDTO> findVendasCliente(Long clienteId, Calendar dataInicial, Calendar dataFinal, EnumStatus status) {
        String sqlVendasCompra = " SELECT new br.com.crista.fashion.dto.VendaDTO(x) From Venda as x " +
                " JOIN x.cliente as c " +
                " WHERE " +
                " c.id =:clienteId ";

        String filtros = "";
        if(dataInicial != null) {
            filtros += " and x.dataVenda >= :dataInicial ";
        }
        if(dataFinal != null) {
            filtros += " and x.dataVenda <= :dataFinal ";
        }
        if(status != null) {
            filtros += " and x.status = :status ";
        }
        sqlVendasCompra += filtros;

        Query query = entityManager.createQuery(sqlVendasCompra);
        addFiltros(clienteId, dataInicial, dataFinal, status, query);

        List<VendaDTO> vendas = query.getResultList();
        return vendas;
    }

    private void addFiltros(Long clienteId, Calendar dataInicial, Calendar dataFinal, EnumStatus status, Query query) {
        query.setParameter("clienteId", clienteId);
        if (status != null) {
            query.setParameter("status", status);
        }
        if (dataInicial != null) {
            query.setParameter("dataInicial", DateUtils.zeraHorario(dataInicial));
        }
        if (dataFinal != null) {
            query.setParameter("dataFinal", DateUtils.setUltimaHoraDoDia(dataFinal));
        }
    }
}
