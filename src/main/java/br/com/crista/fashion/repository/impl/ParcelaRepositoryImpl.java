package br.com.crista.fashion.repository.impl;

import br.com.crista.fashion.dto.ParcelaDTO;
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

import static java.util.Objects.nonNull;

@Repository("parcelaRepositoryImpl")
public class ParcelaRepositoryImpl {

    @PersistenceContext
    EntityManager entityManager;

    public Page<ParcelaDTO> pagination(Long clienteId, Long lojaId,
                                       EnumStatus status, Calendar dataInicial,
                                       Calendar dataFinal, Pageable paging) {

        String filtros = " From Parcela as x " +
                " JOIN x.loja as l " +
                " JOIN x.cliente as c " +
                " WHERE 1 = 1 ";

        if (nonNull(lojaId)) {

            filtros += " and l.id = :lojaId ";
        }

        if (nonNull(clienteId)) {

            filtros += " and c.id = :clienteId ";
        }

        if (nonNull(status)) {

            filtros += " and x.status = :status ";
        }

        if (nonNull(dataInicial)) {

            filtros += " and x.dataVencimento >=:dataInicial";
        }

        if (nonNull(dataFinal)) {

            filtros += " and x.dataVencimento <=:dataFinal";
        }

        Query queryTotal = entityManager.createQuery("Select count(1) " + "" + filtros);
        addParamQuery(clienteId, lojaId, status, dataInicial, dataFinal, queryTotal);
        long countResult = (long) queryTotal.getSingleResult();

        Query query = entityManager.createQuery("SELECT new br.com.crista.fashion.dto.ParcelaDTO(x) "
                + filtros + " order by x.venda.id asc, x.numero asc");

        addParamQuery(clienteId, lojaId, status, dataInicial, dataFinal, query);
        query.setFirstResult((paging.getPageNumber()) * paging.getPageSize());
        query.setMaxResults(paging.getPageSize());

        List<ParcelaDTO> parcelas = query.getResultList();

        return new PageImpl<>(parcelas, paging, countResult);
    }

    private void addParamQuery( Long clienteId, Long lojaId, EnumStatus status, Calendar dataInicial, Calendar dataFinal, Query query) {

        if (nonNull(lojaId)) {

            query.setParameter("lojaId", lojaId);
        }

        if (nonNull(clienteId)) {

            query.setParameter("clienteId", clienteId);
        }

        if (nonNull(status)) {

            query.setParameter("status", status);
        }

        if (nonNull(dataInicial)) {

            dataInicial = DateUtils.zeraHorario(dataInicial);
            query.setParameter("dataInicial", dataInicial);
        }

        if (nonNull(dataFinal)) {

            dataFinal = DateUtils.setUltimaHoraDoDia(dataFinal);
            query.setParameter("dataFinal", dataFinal);
        }
    }
}
