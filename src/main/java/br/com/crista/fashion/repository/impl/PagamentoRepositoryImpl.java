package br.com.crista.fashion.repository.impl;

import br.com.crista.fashion.dto.ClienteLojaDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.enumeration.EnumBanco;
import br.com.crista.fashion.enumeration.EnumTipoPagamento;
import br.com.crista.fashion.enumeration.TipoFormaPagamento;
import br.com.crista.fashion.utils.Constants;
import br.com.crista.fashion.utils.DateUtils;
import br.com.crista.fashion.utils.SQLUtils;
import br.com.crista.fashion.utils.StringUtils;
import br.com.crista.fashion.dto.PagamentoDTO;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("pagamentoRepositoryImpl")
public class PagamentoRepositoryImpl {

    @PersistenceContext
    EntityManager entityManager;

    public Page paginationPagamento(PaginationFilterDTO<PagamentoDTO> paginationFilterDTO, Long LojaId) {
        Pageable paging = PageRequest.of(paginationFilterDTO.getPageNo(), paginationFilterDTO.getPageSize(), Sort.by(paginationFilterDTO.getSortBy()));
        PagamentoDTO filtros = paginationFilterDTO.getFiltros();
        filtros.setLojaId(LojaId);
        String sql = " From pagamento x \n" +
                " join parcela p on p.id = x.parcela_id\n" +
                " join carne cc on cc.id = p.carne_id\n" +
                " join venda v on v.id = cc.venda_id\n" +
                " join loja l on l.id = v.loja_id\n" +
                " join cliente c on c.id = v.cliente_id\n" +
                " join empresa e on e.id = l.empresa_id " +
                " WHERE (x.flg_cancelado is null or x.flg_cancelado is false) ";

        if (filtros.getRedeId() != null && filtros.getRedeId().intValue() != Constants.SELECT_TODAS.intValue()) {
            sql += " and e.rede_id = :redeId ";
        }
        if (filtros.getDataInicial() != null) {
            sql += " and x.data_pagto >=:dataInicial";
        }
        if (filtros.getDataFinal() != null) {
            sql += " and x.data_pagto <=:dataFinal";
        }
        if (filtros.getLojaId() != null) {
            sql += " and x.loja_id =:lojaId";
        }

        Query queryCount = entityManager.createNativeQuery("Select count(1) " + sql);
        addParamQuery(filtros, queryCount);
        long countResult = ((BigInteger) queryCount.getSingleResult()).intValue();

        Query queryTotal = entityManager.createNativeQuery("Select sum(x.valor_pago) " + sql);
        addParamQuery(filtros, queryTotal);
        BigDecimal valorTotal = (BigDecimal) queryTotal.getSingleResult();

        Query query = entityManager.createNativeQuery("Select x.id, x.data_pagto, x.valor_pago, x.tipo, " +
                " COALESCE(l.nome_fantasia, l.razao_social) as nomeLoja, " +
                " x.flg_cancelado, x.motivo_cancelamento, c.id as cliente_id, c.cpf, p.numero, l.id as loja_id," +
                " x.tipo_pagamento, x.banco "+sql+ " order by x.data_pagto");
        addParamQuery(filtros, query);
        query.setFirstResult((paging.getPageNumber()) * paging.getPageSize());
        query.setMaxResults(paging.getPageSize());
        List<PagamentoDTO> pagamentos = new ArrayList<>();
        List<Object[]> listaObjects = query.getResultList();
        EnumBanco banco;
        for (Object[] line : listaObjects) {
            PagamentoDTO dto = new PagamentoDTO();
            String tipoPagto;
            Integer numeroParcela;
            try {
                dto.setId(((BigInteger) line[0]).longValue());
                dto.setDataPagto(DateUtils.getDiaMesAnoPortugues(DateUtils.convertDateSQL(line[1])));
                dto.setValorPago((BigDecimal) line[2]);
                tipoPagto = (String) line[3];
                dto.setNomeLoja((String) line[4]);
                dto.setFlgCancelado(line[5] == null ? false : (Boolean) line[5]);
                dto.setMotivoCancelamento(line[6] == null ? "" : (String) line[6]);
                dto.setClienteId(((BigInteger) line[7]).longValue());
                dto.setClienteCpf(StringUtils.inserirMascaraCpfCnpj((String) line[8]));
                numeroParcela = (Integer) line[9];
                dto.setNumeroParcela(numeroParcela);
                dto.setLojaId(SQLUtils.getLong(line[10]));
                dto.setFormaPagamento((String)line[11]);
                if(dto.getFormaPagamento() != null) {
                    if(dto.getFormaPagamento().equals(EnumTipoPagamento.BANCO.name())) {
                        if(line[12] != null) {
                            banco = EnumBanco.valueOf((String) line[12]);
                            dto.setFormaPagamentoDetalhe(banco.getLabel());
                        }
                    } else {
                        if(dto.getFormaPagamento().equals(EnumTipoPagamento.LOJA.name())) {
                            dto.setFormaPagamentoDetalhe(dto.getNomeLoja());
                        }
                    }
                }

                TipoFormaPagamento formaTipoPagamento = TipoFormaPagamento.valueOf(tipoPagto);
                dto.setTipoPagto((numeroParcela == Constants.PARCELA_ENTRADA ? "Entrada " : "") + formaTipoPagamento.getLabel());

                pagamentos.add(dto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("valorTotal", valorTotal);
        map.put("qtdItems", countResult);
        map.put("pagamentos", pagamentos);
        List<Map> lista = new ArrayList<>();
        lista.add(map);
        return new PageImpl(lista, paging, countResult);
    }

    public List<PagamentoDTO> quitacaoDeCompra(ClienteLojaDTO dto) {
        String sql = "select new br.com.crista.fashion.dto.PagamentoDTO(pa) From Pagamento pa\n" +
                " JOIN pa.parcela p \n" +
                " JOIN p.carne as cc \n" +
                " JOIN cc.venda as v \n" +
                " JOIN v.loja as l \n" +
                " JOIN v.cliente as c \n" +
                " WHERE c.id = :cliente_id \n" +
                " and l.id = :loja_id";

        Query query = entityManager.createQuery(sql);

        if (dto.getLojaId() != null){
            query.setParameter("loja_id", dto.getLojaId());
        }
        if (dto.getClienteId() != null){
            query.setParameter("cliente_id", dto.getClienteId());
        }
        List<PagamentoDTO> listaResultados = query.getResultList();

        return listaResultados;
    }

    public Page paginationPagamentoOld(PaginationFilterDTO<PagamentoDTO> paginationFilterDTO) {
        Pageable paging = PageRequest.of(paginationFilterDTO.getPageNo(), paginationFilterDTO.getPageSize(), Sort.by(paginationFilterDTO.getSortBy()));
        PagamentoDTO filtros = paginationFilterDTO.getFiltros();
        String sql = " From Pagamento as x " +
                " x.parcela p" +
                " p.carne cc" +
                " cc.venda as v " +
                " v.loja l " +
                " v.cliente c " +
                // " WHERE (x.flgCancelado is null or x.flgCancelado is false) ";
                " WHERE 1 = 1 ";
        if (filtros.getRedeId() != null && filtros.getRedeId().intValue() != Constants.SELECT_TODAS.intValue()) {
            sql += " and l.empresa.rede.id = :redeId ";
        }
        if (filtros.getDataInicial() != null) {
            sql += " and x.dataPagto >=:dataInicial";
        }
        if (filtros.getDataFinal() != null) {
            sql += " and x.dataPagto <=:dataFinal";
        }

        Query queryCount = entityManager.createQuery("Select count(1) " + sql);
        addParamQuery(filtros, queryCount);
        long countResult = (long) queryCount.getSingleResult();

        Query queryTotal = entityManager.createQuery("Select sum(x.valorPago) " + sql);
        addParamQuery(filtros, queryTotal);
        BigDecimal valorTotal = (BigDecimal) queryTotal.getSingleResult();

        Query query = entityManager.createQuery("SELECT new br.com.crista.fashion.dto.PagamentoDTO(x) "+sql);
        addParamQuery(filtros, query);
        query.setFirstResult((paging.getPageNumber()) * paging.getPageSize());
        query.setMaxResults(paging.getPageSize());
        List<PagamentoDTO> pagamentos = query.getResultList();

        Map<String, Object> map = new HashMap<>();
        map.put("valorTotal", valorTotal);
        map.put("qtdItems", countResult);
        map.put("pagamentos", pagamentos);
        List<Map> lista = new ArrayList<>();
        lista.add(map);
        return new PageImpl(lista, paging, countResult);
    }

    private void addParamQuery(PagamentoDTO filtros, Query query) {
        if (filtros.getRedeId() != null && filtros.getRedeId().intValue() != Constants.SELECT_TODAS.intValue()) {
            query.setParameter("redeId", filtros.getRedeId());
        }
        if (filtros.getDataInicial() != null) {
            query.setParameter("dataInicial", DateUtils.zeraHorario(filtros.getDataInicial()));
        }
        if (filtros.getDataFinal() != null) {
            query.setParameter("dataFinal", DateUtils.setUltimaHoraDoDia(filtros.getDataFinal()));
        }
        if (filtros.getLojaId() != null) {
            query.setParameter("lojaId", filtros.getLojaId());
        }
    }
}
