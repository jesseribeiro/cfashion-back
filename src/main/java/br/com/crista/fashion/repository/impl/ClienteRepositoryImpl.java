package br.com.crista.fashion.repository.impl;

import br.com.crista.fashion.bean.VendaBean;
import br.com.crista.fashion.dto.ResumoClienteDTO;
import br.com.crista.fashion.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

@Slf4j
@Repository("clienteRepositoryImpl")
public class ClienteRepositoryImpl {

    @PersistenceContext
    EntityManager entityManager;

    private Long ULTIMO_MES = 0L;
    private Long DOIS_MESES = 1L;
    private Long TRES_MESES = 2L;
    private Long SEIS_MESES = 3L;
    private Long DOZE_MESES = 4L;
    private Long DEZOITO_MESES = 5L;

    public ResumoClienteDTO getResumoCliente(Long clienteId, Long lojaId) {
        String sql = " --- maio parte do resumo do cliente\n" +
                "select \n" +
                "r.nome as nomeRede,\n" +
                "e.nome as nomeEmpresa,\n" +
                "l.nome_fantasia as nomeLoja,\n" +
                "to_char(cl.data_cadastro, 'YYYY-MM-DD') as dataAssinatura,\n" +
                "to_char(compras.ultimaCompra,'YYYY-MM-DD') as ultimaCompra,\n" +
                "COALESCE(compras.qtdTotalCompras, 0) as qtdTotalCompras,\n" +
                "COALESCE(compras.vlTotalCompras, 0) as vlTotalCompras,\n"+
                "COALESCE(sum(compras_nao_paga.valor), 0) + COALESCE(sum(acordo_nao_pago.valor), 0) as saldoDevedorTotal,\n" +
                "COALESCE(sum(compras_mes.valor), 0) + COALESCE(sum(acordo_mes.valor), 0) as saldoDevedorMes,\n" +
                "to_char(cl.data_status, 'YYYY-MM-DD') as dataStatus,\n" +
                "cl.status as status\n" +
                "from cliente_loja cl\n" +
                "join cliente c on c.id = cl.cliente_id\n" +
                "join loja l on l.id = cl.loja_id\n" +
                "join empresa e on e.id = l.empresa_id\n" +
                "join rede r on r.id = e.rede_id\n" +
                "left join ( --- Todas compras\n" +
                "\tselect \n" +
                "\t\tv.cliente_id as cliente_id,\n" +
                "\t\tv.loja_id as loja_id,\n" +
                "\t\tmax(v.data_venda) as ultimaCompra,\n" +
                "\t\tCOALESCE(count(1), 0) as qtdTotalCompras,\n" +
                "\t\tCOALESCE(sum(v.valor_produto), 0) as vlTotalCompras\n" +
                "\t\tfrom venda v\n" +
                "\t\tgroup by \n" +
                "\t\tv.cliente_id, v.loja_id\n" +
                "\t\torder by v.cliente_id\n" +
                "\t) as compras on compras.cliente_id = cl.cliente_id and compras.loja_id = cl.loja_id\n" +
                "--- Compras não pagas\n" +
                "left join (\n" +
                "\tselect \n" +
                "\t\tcl.cliente_id as cliente_id,\n" +
                "\t\tcl.loja_id as loja_id,\n" +
                "\t\tCOALESCE(sum(p.valor),0) as valor\n" +
                "\t\tfrom cliente_loja cl\n" +
                "\t\tjoin venda v on v.cliente_id = cl.cliente_id and v.loja_id = cl.loja_id\n" +
                "\t\tjoin carne ca on ca.venda_id = v.id\n" +
                "\t\tjoin parcela p on p.carne_id = ca.id\n" +
                "\t\twhere \n" +
                "\t\tp.status = 'NAO_PAGA'\n" +
                "\t\tgroup by \n" +
                "\t\tcl.cliente_id, cl.loja_id\n" +
                "\t) as compras_nao_paga on compras_nao_paga.cliente_id = cl.cliente_id and compras_nao_paga.loja_id = cl.loja_id\n" +
                "left join (\n" +
                "\tselect \n" +
                "\t\tcl.cliente_id as cliente_id,\n" +
                "\t\tcl.loja_id as loja_id,\n" +
                "\t\tCOALESCE(sum(p.valor),0) as valor\n" +
                "\t\tfrom cliente_loja cl\n" +
                "\t\tjoin venda v on v.cliente_id = cl.cliente_id and v.loja_id = cl.loja_id\n" +
                "\t\tjoin carne ca on ca.venda_id = v.id\n" +
                "\t\tjoin parcela p on p.carne_id = ca.id\n" +
                "\t\twhere \n" +
                "\t\tp.status = 'NAO_PAGA'\n" +
                "\tand p.data_cadastro >= :dataInicio and p.data_cadastro <= :dataFim \n" +
                "\t\tgroup by \n" +
                "\t\tcl.cliente_id, cl.loja_id\n" +
                "\t) as compras_mes on compras_mes.cliente_id = cl.cliente_id and compras_mes.loja_id = cl.loja_id\n" +
                "--- Acordo\n" +
                "left join (\n" +
                "\tselect \n" +
                "\t\tcl.cliente_id as cliente_id,\n" +
                "\t\tcl.loja_id as loja_id,\n" +
                "\t\tCOALESCE(sum(p.valor),0) as valor\n" +
                "\t\tfrom cliente_loja cl\n" +
                "\t\tjoin renegociacao re on re.cliente_id = cl.cliente_id and re.loja_id = cl.cliente_id\n" +
                "\t\tjoin carne ca on ca.renegociacao_id = re.id\n" +
                "\t\tjoin parcela p on p.carne_id = ca.id\n" +
                "\t\twhere \n" +
                "\t\tp.status = 'NAO_PAGA'\n" +
                "\t\tgroup by \n" +
                "\t\tcl.cliente_id, cl.loja_id\n" +
                "\t) as acordo_nao_pago on acordo_nao_pago.cliente_id = cl.cliente_id and acordo_nao_pago.loja_id = cl.loja_id\n" +
                "left join (\n" +
                "\tselect \n" +
                "\t\tcl.cliente_id as cliente_id,\n" +
                "\t\tcl.loja_id as loja_id,\n" +
                "\t\tCOALESCE(sum(p.valor),0) as valor\n" +
                "\t\tfrom cliente_loja cl\n" +
                "\t\tjoin renegociacao re on re.cliente_id = cl.cliente_id and re.loja_id = cl.cliente_id\n" +
                "\t\tjoin carne ca on ca.renegociacao_id = re.id\n" +
                "\t\tjoin parcela p on p.carne_id = ca.id\n" +
                "\t\twhere \n" +
                "\t\tp.status = 'NAO_PAGA'\n" +
                "\tand p.data_cadastro >= :dataInicio and p.data_cadastro <= :dataFim \n" +
                "\t\tgroup by \n" +
                "\t\tcl.cliente_id, cl.loja_id\n" +
                "\t) as acordo_mes on acordo_mes.cliente_id = cl.cliente_id and acordo_mes.loja_id = cl.loja_id\n" +
                "where \n" +
                "cl.cliente_id =:clienteId \n" +
                "and cl.loja_id =:lojaId \n" +
                "group by \n" +
                "c.id, c.nome, r.nome, e.nome, \n" +
                "l.nome_fantasia, cl.data_cadastro,\n" +
                "cl.data_status, cl.status,\n" +
                "compras.ultimaCompra,\n" +
                "compras.qtdTotalCompras,\n" +
                "compras.vlTotalCompras\n";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("clienteId", clienteId);
        query.setParameter("lojaId", lojaId);

        Calendar dataInicio = Calendar.getInstance();
        Calendar dataFim = Calendar.getInstance();
        dataInicio = DateUtils.primeiroDiaMes(dataInicio);
        dataFim = DateUtils.ultimoDiaMes(dataFim);

        query.setParameter("dataInicio", dataInicio);
        query.setParameter("dataFim", dataFim);


        ResumoClienteDTO resumoCliente = new ResumoClienteDTO();
        List<Object[]> listaObjects = query.getResultList();
        for (Object[] line : listaObjects) {
            try {
                resumoCliente.setNomeRede(((String) line[0]));
                resumoCliente.setNomeEmpresa(((String) line[1]));
                resumoCliente.setNomeLoja(((String) line[2]));
                resumoCliente.setDataAssinatura(DateUtils.getDiaMesAno((String)line[3]));
                resumoCliente.setUltimaCompraEm(DateUtils.getDiaMesAno((String)line[4]));
                resumoCliente.setQtdTotalCompras(((BigInteger) line[5]).intValue());
                resumoCliente.setVlTotalCompras((BigDecimal) line[6]);
                resumoCliente.setSaldoDevedorTotal((BigDecimal) line[7]);
                resumoCliente.setSaldoDevedorMes((BigDecimal) line[8]);
                resumoCliente.setDataStatus(DateUtils.getDiaMesAno((String)line[9]));
                resumoCliente.setStatus((String) line[10]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.preencheResumoPagamentos(clienteId, lojaId, resumoCliente);
        this.maiorAtrasoAteHoje(clienteId, lojaId, resumoCliente, false);
        this.maiorAtrasoAteHoje(clienteId, lojaId, resumoCliente, true);
        this.emAtraso(clienteId, lojaId, resumoCliente);
        return resumoCliente;
    }

    private void preencheResumoPagamentos(Long clienteId, Long lojaId, ResumoClienteDTO resumoCliente) {
        String sql = " select " +
                "\tCASE \n" +
                "\t\tWHEN compras.ultimoPagto < acordo.ultimoPagto \n" +
                "\t\tTHEN to_char(max(acordo.ultimoPagto), 'YYYY-MM-DD') \n" +
                "\t\tELSE to_char(max(compras.ultimoPagto), 'YYYY-MM-DD') \n" +
                "\tEND ultimoPagto,\n" +
                "\tCOALESCE(sum(compras.qtdTotalPagamentos),0) + COALESCE(sum(acordo.qtdTotalPagamentos),0) as qtdTotalPagamentos,\n" +
                "\tCOALESCE(sum(compras.vlTotalPagamentos),0) + COALESCE(sum(acordo.vlTotalPagamentos),0) as vlTotalPagamentos \n" +
                "from cliente_loja cl\n" +
                "left join (\n" +
                "\tselect \n" +
                "\t\tcl.cliente_id,\n" +
                "\t\tcl.loja_id,\n" +
                "\t\tmax(pg.data_pagto) as ultimoPagto,\n" +
                "\t\tcount(pg.id) as qtdTotalPagamentos,\n" +
                "\t\tCOALESCE(sum(pg.valor_pago),0) as vlTotalPagamentos\n" +
                "\t\tfrom cliente_loja cl\n" +
                "\t\tjoin venda v on v.cliente_id = cl.cliente_id and v.loja_id = cl.loja_id\n" +
                "\t\tjoin carne ca on ca.venda_id = v.id\n" +
                "\t\tjoin parcela p on p.carne_id = ca.id\n" +
                "\t\tjoin pagamento pg on pg.parcela_id = p.id\n" +
                "\t\twhere \n" +
                "\t\t(pg.flg_cancelado is null or pg.flg_cancelado is false)\n" +
                "\t\tgroup by \n" +
                "\t\tcl.cliente_id,\n" +
                "\t\tcl.loja_id\n" +
                "\t) as compras on compras.cliente_id = cl.cliente_id and compras.loja_id = cl.loja_id\n" +
                "left join (\n" +
                "\tselect \n" +
                "\t\tcl.cliente_id,\n" +
                "\t\tcl.loja_id,\n" +
                "\t\tmax(pg.data_pagto) as ultimoPagto,\n" +
                "\t\tcount(pg.id) as qtdTotalPagamentos,\n" +
                "\t\tCOALESCE(sum(pg.valor_pago),0) as vlTotalPagamentos\n" +
                "\t\tfrom cliente_loja cl\n" +
                "\t\tjoin renegociacao r on r.cliente_id = cl.cliente_id and r.loja_id = cl.loja_id\n" +
                "\t\tjoin carne ca on ca.renegociacao_id = r.id\n" +
                "\t\tjoin parcela p on p.carne_id = ca.id\n" +
                "\t\tjoin pagamento pg on pg.parcela_id = p.id\n" +
                "\t\twhere \n" +
                "\t\t(pg.flg_cancelado is null or pg.flg_cancelado is false)\n" +
                "\t\tgroup by \n" +
                "\t\tcl.cliente_id,\n" +
                "\t\tcl.loja_id\n" +
                "\t) as acordo on acordo.cliente_id = cl.cliente_id and acordo.loja_id = cl.loja_id\t\n" +
                "where \n" +
                " cl.cliente_id =:clienteId \n" +
                " and cl.loja_id =:lojaId \n" +
                "group by \n" +
                "compras.ultimoPagto, acordo.ultimoPagto ";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("clienteId", clienteId);
        query.setParameter("lojaId", lojaId);

        List<Object[]> listaObjects = query.getResultList();
        for (Object[] line : listaObjects) {
            try {
                resumoCliente.setUltimoPagamentoEm(DateUtils.getDiaMesAno((String)line[0]));
                resumoCliente.setQtdTotalPagamentos(((BigDecimal) line[1]).intValue());
                resumoCliente.setVlTotalPagamentos(((BigDecimal) line[2]));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void maiorAtrasoAteHoje(Long clienteId, Long lojaId, ResumoClienteDTO resumoCliente, boolean maiorAtraso12Meses) {
        String sql = " select to_char(maiorAtraso.data_vencimento, 'YYYY-MM-DD') as data_vencimento,\n" +
                "maiorAtraso.maior_atraso_ate_hoje from (\n" +
                "\t( select -- PARCELAS COMPRA\n" +
                "\t\tcl.cliente_id,\n" +
                "\t\tcl.loja_id,\n" +
                "\t\tp.data_vencimento,\n" +
                "\t\tDATE_PART('day', COALESCE(pg.data_pagto, now())\\:\\:timestamp - p.data_vencimento\\:\\:timestamp) as dias_atraso\n" +
                "\t\tfrom cliente_loja cl\n" +
                "\t\tjoin venda v on v.cliente_id = cl.cliente_id and v.loja_id = cl.loja_id\n" +
                "\t\tjoin carne cc on cc.venda_id = v.id\n" +
                "\t\tjoin parcela p on p.carne_id = cc.id\n" +
                "\t\tleft join pagamento pg on pg.parcela_id = p.id\n" +
                "\t\twhere\n" +
                "\t\tp.data_vencimento < now()\n" +
                "\t\tand p.status <> 'CANCELADA'\n" +
                "\t\tand cl.cliente_id = :clienteId and cl.loja_id = :lojaId\n";
                if(maiorAtraso12Meses) {
                    sql += "\t\tand DATE_PART('day', p.data_vencimento\\:\\:timestamp - cl.data_cadastro\\:\\:timestamp) < 365";
                }
                sql += "\t\tand DATE_PART('day', COALESCE(pg.data_pagto, now())\\:\\:timestamp - p.data_vencimento\\:\\:timestamp) > 0\n" +
                "\t UNION -- PARCELAS ACORDO\n" +
                "\t select\n" +
                "\t\tcl.cliente_id,\n" +
                "\t\tcl.loja_id,\n" +
                "\t\tp.data_vencimento,\n" +
                "\t\tDATE_PART('day', COALESCE(pg.data_pagto, now())\\:\\:timestamp - p.data_vencimento\\:\\:timestamp) as dias_atraso\n" +
                "\t\tfrom cliente_loja cl\n" +
                "\t\tjoin renegociacao r on r.cliente_id = cl.cliente_id and r.loja_id = cl.loja_id\n" +
                "\t\tjoin carne cc on cc.renegociacao_id = r.id\n" +
                "\t\tjoin parcela p on p.carne_id = cc.id\n" +
                "\t\tleft join pagamento pg on pg.parcela_id = p.id\n" +
                "\t\twhere\n" +
                "\t\tp.data_vencimento < now()\n" +
                "\t\tand p.status <> 'CANCELADA'\n" +
                "\t\tand cl.cliente_id = :clienteId and cl.loja_id = :lojaId\n";
                if(maiorAtraso12Meses) {
                    sql += "\t\tand DATE_PART('day', p.data_vencimento\\:\\:timestamp - cl.data_cadastro\\:\\:timestamp) < 365";
                }
                sql += "\t\tand DATE_PART('day', COALESCE(pg.data_pagto, now())\\:\\:timestamp - p.data_vencimento\\:\\:timestamp) > 0\t \n" +
                "\t) as parcelas_atraso\n" +
                "\tjoin (\n" +
                "\t\tselect\n" +
                "\t\tcl.cliente_id,\n" +
                "\t\tcl.loja_id,\n" +
                "\t\tmax(DATE_PART('day', COALESCE(pg.data_pagto, now())\\:\\:timestamp - p.data_vencimento\\:\\:timestamp)) as maior_atraso_ate_hoje\n" +
                "\t\tfrom cliente_loja cl\n" +
                "\t\tjoin venda v on v.cliente_id = cl.cliente_id and v.loja_id = cl.loja_id\n" +
                "\t\tjoin carne cc on cc.venda_id = v.id\n" +
                "\t\tjoin parcela p on p.carne_id = cc.id\n" +
                "\t\tleft join pagamento pg on pg.parcela_id = p.id\n" +
                "\t\twhere\n" +
                "\t\tp.data_vencimento < now()\n" +
                "\t\tand p.status <> 'CANCELADA'\n" +
                "\t\tand cl.cliente_id = :clienteId and cl.loja_id = :lojaId\n";
                if(maiorAtraso12Meses) {
                    sql += "\t\tand DATE_PART('day', p.data_vencimento\\:\\:timestamp - cl.data_cadastro\\:\\:timestamp) < 365";
                }
                sql += "\t\tgroup by\n" +
                "\t\tcl.cliente_id,\n" +
                "\t\tcl.loja_id\n" +
                "\t\thaving max(DATE_PART('day', COALESCE(pg.data_pagto, now())\\:\\:timestamp - p.data_vencimento\\:\\:timestamp)) > 0\n" +
                "\tUNION\n" +
                "\t\tselect\n" +
                "\t\tcl.cliente_id,\n" +
                "\t\tcl.loja_id,\n" +
                "\t\tmax(DATE_PART('day', COALESCE(pg.data_pagto, now())\\:\\:timestamp - p.data_vencimento\\:\\:timestamp)) as maior_atraso_ate_hoje\n" +
                "\t\tfrom cliente_loja cl\n" +
                "\t\tjoin renegociacao r on r.cliente_id = cl.cliente_id and r.loja_id = cl.loja_id\n" +
                "\t\tjoin carne cc on cc.renegociacao_id = r.id\n" +
                "\t\tjoin parcela p on p.carne_id = cc.id\n" +
                "\t\tleft join pagamento pg on pg.parcela_id = p.id\n" +
                "\t\twhere\n" +
                "\t\tp.data_vencimento < now()\n" +
                "\t\tand p.status <> 'CANCELADA'\n" +
                "\t\tand cl.cliente_id = :clienteId and cl.loja_id = :lojaId\n";
                if(maiorAtraso12Meses) {
                    sql += "\t\tand DATE_PART('day', p.data_vencimento\\:\\:timestamp - cl.data_cadastro\\:\\:timestamp) < 365";
                }
                sql +="\t\tgroup by\n" +
                "\t\tcl.cliente_id,\n" +
                "\t\tcl.loja_id\n" +
                "\t\thaving max(DATE_PART('day', COALESCE(pg.data_pagto, now())\\:\\:timestamp - p.data_vencimento\\:\\:timestamp)) > 0\n" +
                "\t) as maior_atraso on \n" +
                "\t\tmaior_atraso.cliente_id = parcelas_atraso.cliente_id\n" +
                "\t\tand maior_atraso.loja_id = parcelas_atraso.loja_id\n" +
                "\t\tand maior_atraso.maior_atraso_ate_hoje = parcelas_atraso.dias_atraso\n" +
                ") as maiorAtraso ";
        try {

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("clienteId", clienteId);
            query.setParameter("lojaId", lojaId);

            List<Object[]> listaObjects = query.getResultList();
            for (Object[] line : listaObjects) {
                try {
                    if(maiorAtraso12Meses) {
                        resumoCliente.setMaiorAtrasoPrim12mesesVencto(DateUtils.getDiaMesAno((String) line[0]));
                        resumoCliente.setMaiorAtrasoPrim12mesesDias(((Double) line[1]).intValue());
                    } else {
                        resumoCliente.setMaiorAtrasoAteHojeVencto(DateUtils.getDiaMesAno((String) line[0]));
                        resumoCliente.setMaiorAtrasoAteHojeDias(((Double) line[1]).intValue());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void emAtraso(Long clienteId, Long lojaId, ResumoClienteDTO resumoCliente) {
        String sql = "select to_char(emAtraso.data_vencimento, 'YYYY-MM-DD') as data_vencimento,\n" +
                "emAtraso.maior_atraso from (\t\t\t\n" +
                "\t( select -- PARCELAS COMPRA\n" +
                "\t\tcl.cliente_id,\n" +
                "\t\tcl.loja_id,\n" +
                "\t\tp.data_vencimento,\n" +
                "\t\tDATE_PART('day', COALESCE(now())\\:\\:timestamp - p.data_vencimento\\:\\:timestamp) as dias_atraso \n" +
                "\t\tfrom cliente_loja cl\n" +
                "\t\tjoin venda v on v.cliente_id = cl.cliente_id and v.loja_id = cl.loja_id\n" +
                "\t\tjoin carne cc on cc.venda_id = v.id\n" +
                "\t\tjoin parcela p on p.carne_id = cc.id\n" +
                "\t\twhere\n" +
                "\t\tp.data_vencimento < now()\n" +
                "\t\tand p.status = 'NAO_PAGA'\n" +
                "\t\tand cl.cliente_id = :clienteId and cl.loja_id = :lojaId\t\t\n" +
                "\t UNION -- PARCELAS ACORDO\n" +
                "\t select\n" +
                "\t\tcl.cliente_id,\n" +
                "\t\tcl.loja_id,\n" +
                "\t\tp.data_vencimento,\n" +
                "\t\tDATE_PART('day', COALESCE(now())\\:\\:timestamp - p.data_vencimento\\:\\:timestamp) as dias_atraso \n" +
                "\t\tfrom cliente_loja cl\n" +
                "\t\tjoin renegociacao r on r.cliente_id = cl.cliente_id and r.loja_id = cl.loja_id\n" +
                "\t\tjoin carne cc on cc.renegociacao_id = r.id\n" +
                "\t\tjoin parcela p on p.carne_id = cc.id\n" +
                "\t\twhere\n" +
                "\t\tp.data_vencimento < now()\n" +
                "\t\tand p.status = 'NAO_PAGA'\n" +
                "\t\tand cl.cliente_id = :clienteId and cl.loja_id = :lojaId\n" +
                "\t) as parcelas_atraso\n" +
                "\tjoin (\n" +
                "\t\tselect\n" +
                "\t\tcl.cliente_id,\n" +
                "\t\tcl.loja_id,\n" +
                "\t\tmax(DATE_PART('day', COALESCE(now())\\:\\:timestamp - p.data_vencimento\\:\\:timestamp)) as maior_atraso\n" +
                "\t\tfrom cliente_loja cl\n" +
                "\t\tjoin venda v on v.cliente_id = cl.cliente_id and v.loja_id = cl.loja_id\n" +
                "\t\tjoin carne cc on cc.venda_id = v.id\n" +
                "\t\tjoin parcela p on p.carne_id = cc.id\n" +
                "\t\twhere\n" +
                "\t\tp.data_vencimento < now()\n" +
                "\t\tand p.status = 'NAO_PAGA'\n" +
                "\t\tand cl.cliente_id = :clienteId and cl.loja_id = :lojaId\n" +
                "\t\tgroup by\n" +
                "\t\tcl.cliente_id,\n" +
                "\t\tcl.loja_id\n" +
                "\tUNION\n" +
                "\t\tselect\n" +
                "\t\tcl.cliente_id,\n" +
                "\t\tcl.loja_id,\n" +
                "\t\tmax(DATE_PART('day', COALESCE(now())\\:\\:timestamp - p.data_vencimento\\:\\:timestamp)) as maior_atraso\n" +
                "\t\tfrom cliente_loja cl\n" +
                "\t\tjoin renegociacao r on r.cliente_id = cl.cliente_id and r.loja_id = cl.loja_id\n" +
                "\t\tjoin carne cc on cc.renegociacao_id = r.id\n" +
                "\t\tjoin parcela p on p.carne_id = cc.id\n" +
                "\t\twhere\n" +
                "\t\tp.data_vencimento < now()\n" +
                "\t\tand p.status = 'NAO_PAGA'\n" +
                "\t\tand cl.cliente_id = :clienteId and cl.loja_id = :lojaId\n" +
                "\t\tgroup by\n" +
                "\t\tcl.cliente_id,\n" +
                "\t\tcl.loja_id\n" +
                "\t) as em_atraso on \n" +
                "\t\tem_atraso.cliente_id = parcelas_atraso.cliente_id\n" +
                "\t\tand em_atraso.loja_id = parcelas_atraso.loja_id\n" +
                "\t\tand em_atraso.maior_atraso = parcelas_atraso.dias_atraso\n" +
                ") as emAtraso ";
        try {
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("clienteId", clienteId);
            query.setParameter("lojaId", lojaId);

            List<Object[]> listaObjects = query.getResultList();
            for (Object[] line : listaObjects) {
                try {
                    resumoCliente.setAtrasoVencto(DateUtils.getDiaMesAno((String)line[0]));
                    resumoCliente.setAtrasoDias(((Double) line[1]).intValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

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
