package br.com.crista.fashion.repository.impl;

import br.com.crista.fashion.bean.VendaBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Slf4j
@Repository("clienteRepositoryImpl")
public class ClienteRepositoryImpl {

    @PersistenceContext
    EntityManager entityManager;

    public Integer getMaiorAtrasNosUltimosNPagementos(Long clienteId, Integer ultimosPagamentos) {
        try {
            String sql = " select max(atraso.dias_pago_atraso) from (\n" +
                    "select\n" +
                    "pg.data_pagto,\n" +
                    "v.cliente_id,\n" +
                    "DATE_PART('day', pg.data_pagto\\:\\:timestamp - p.data_vencimento\\:\\:timestamp) as dias_pago_atraso\n" +
                    "from pagamento pg\n" +
                    "join parcela p on p.id = pg.parcela_id\n" +
                    "join carne cc on cc.id = p.carne_id\n" +
                    "join venda v on v.id = cc.venda_id\n" +
                    "where\n" +
                    "(pg.flg_Cancelado is null or pg.flg_Cancelado is false)\n" +
                    "and DATE_PART('day', pg.data_pagto\\:\\:timestamp - p.data_vencimento\\:\\:timestamp) > 0\n" +
                    "and v.cliente_id =:clienteId\n" +
                    "order by pg.data_pagto desc\n" +
                    "limit :limite) as atraso ";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("clienteId", clienteId);
            query.setParameter("limite", ultimosPagamentos);
            return (Integer) query.getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    public VendaBean getUltimaCompra(Long clienteId) {
        try {
            String hql = "select v from Venda v \n" +
                    "where \n" +
                    "v.cliente.id =:clienteId \n" +
                    "and v.id = (select max(x.id) from Venda x where x.cliente.id =:clienteId)\n";
            Query query = entityManager.createQuery(hql);
            query.setParameter("clienteId", clienteId);
            return (VendaBean) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
