package br.com.crista.fashion.repository.impl;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository("planoPagamentoRepositoryImpl")
public class PlanoPagamentoRepositoryImpl {

    @PersistenceContext
    EntityManager entityManager;

    public List<Long> findIDsPlanosPagamentoByRegra(Long regraFinanceiraId) {
        String sqlPlanoIds = "Select plano_pagamento_id From regra_plano \n" +
                " WHERE regra_id =:regraId limit 10 ";
        Query query = entityManager.createNativeQuery(sqlPlanoIds);
        query.setParameter("regraId", regraFinanceiraId);

        List<Long> planos = new ArrayList<>();
        List<BigInteger> listaObjects = query.getResultList();
        for (BigInteger line : listaObjects) {
            planos.add(line.longValue());
        }
        return planos;
    }

    public Long findIDPlanosPagamentoWithRegra() {
        String sqlPlanoIds = "Select plano_pagamento_id From regra_plano limit 1\n";
        Query query = entityManager.createNativeQuery(sqlPlanoIds);

        List<Long> planos = new ArrayList<>();
        List<BigInteger> listaObjects = query.getResultList();
        for (BigInteger line : listaObjects) {
            planos.add(line.longValue());
        }
        if(!planos.isEmpty()) {
            return planos.get(0);
        }
        return null;
    }
}
