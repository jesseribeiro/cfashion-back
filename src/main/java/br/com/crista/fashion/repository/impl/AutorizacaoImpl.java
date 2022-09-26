package br.com.crista.fashion.repository.impl;

import br.com.crista.fashion.dto.AutorizacaoDTO;
import br.com.crista.fashion.enumeration.EnumStatusVenda;
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

@Repository("AutorizacaoImpl")
public class AutorizacaoImpl {

    @PersistenceContext
    EntityManager entityManager;

    public Page<AutorizacaoDTO> paginationAutorizacoes(Long redeId, Long empresaId, Long lojaId, Long clienteId,
                                                       Calendar dataInicial, Calendar dataFinal, EnumStatusVenda enumStatusVenda,
                                                       Pageable paging) {
        String filtros = " From Autorizacao as a " +
                " LEFT JOIN a.venda as v " +
                " LEFT JOIN Carne as cc on cc.venda.id = v.id " +
                " JOIN a.loja as l " +
                " JOIN a.cliente as c " +
                " WHERE 1 = 1 ";
        if (redeId != null) {
            filtros += " and l.empresa.rede.id = :redeId ";
        }
        if (empresaId != null) {
            filtros += " and l.empresa.id = :empresaId ";
        }
        if (lojaId != null) {
            filtros += " and l.id = :lojaId ";
        }
        if (clienteId != null) {
            filtros += " and c.id = :clienteId ";
        }
        if (enumStatusVenda != null) {
            filtros += " and v.status = :situacao ";
        }
        if (dataInicial != null) {
            filtros += " and a.dataCadastro >=:dataInicial";
        }
        if (dataFinal != null) {
            filtros += " and a.dataCadastro <=:dataFinal";
        }

        Query queryTotal = entityManager.createQuery("Select count(1) " + "" + filtros);
        addParamQuery(redeId, empresaId, lojaId, clienteId, dataInicial, dataFinal, enumStatusVenda, queryTotal);
        long countResult = (long) queryTotal.getSingleResult();

        Query query = entityManager.createQuery("SELECT new br.com.crista.fashion.dto.AutorizacaoDTO(a, cc) " + "" + filtros + " order by a.dataCadastro desc");
        addParamQuery(redeId, empresaId, lojaId, clienteId, dataInicial, dataFinal, enumStatusVenda, query);
        query.setFirstResult((paging.getPageNumber()) * paging.getPageSize());
        query.setMaxResults(paging.getPageSize());
        List<AutorizacaoDTO> autorizacoes = query.getResultList();

        return new PageImpl<>(autorizacoes, paging, countResult);
    }

    private void addParamQuery(Long redeId, Long empresaId, Long lojaId, Long clienteId, Calendar dataInicial, Calendar dataFinal, EnumStatusVenda enumStatusVenda, Query query) {
        if (redeId != null) {
            query.setParameter("redeId", redeId);
        }
        if (empresaId != null) {
            query.setParameter("empresaId", empresaId);
        }
        if (lojaId != null) {
            query.setParameter("lojaId", lojaId);
        }
        if (clienteId != null) {
            query.setParameter("clienteId", clienteId);
        }
        if (enumStatusVenda != null) {
            query.setParameter("situacao", enumStatusVenda);
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
}
