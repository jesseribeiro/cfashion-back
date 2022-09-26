package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.PagamentoBean;
import br.com.crista.fashion.dto.PagamentoDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

public interface PagamentoRepository extends CrudRepository<PagamentoBean, Long>, GenericRepository {

    @Query(" Select x From Pagamento x " +
            " Where x.id=:id ")
    PagamentoBean getById(@Param("id") Long pagamentoId);

    @Query(" Select x From Pagamento x " +
            " Where x.algorixId =:algorixId " +
            " and x.parcela.id =:parcelaId ")
    PagamentoBean getPagamentoByAlgorix(@Param("algorixId") Long algorixId, @Param("parcelaId") Long parcelaId);

    @Query(" Select case when (count(x) > 0) then true else false end From Pagamento x " +
            " Where x.algorixId =:algorixId " +
            " and x.parcela.id =:parcelaId ")
    boolean existsPagamento(@Param("algorixId") Long algorixId, @Param("parcelaId") Long parcelaId);

    @Query(value = " select count(1) " +
            " from pagamento pg " +
            " join parcela p on p.id = pg.parcela_id " +
            " join carne c on c.id = p.carne_id " +
            " join venda v on v.id = c.venda_id " +
            " join loja l on l.id = v.loja_id " +
            " where pg.tipo_pagamento = 'BANCO' " +
            " and pg.data_pagto >= :dataInicio " +
            " and pg.data_pagto <= :dataFim ", nativeQuery = true)
    BigDecimal valorDespesasBoleto(@Param("dataInicio") Calendar dataInicio, @Param("dataFim") Calendar dataFim);

    @Query(" select new br.com.crista.fashion.dto.PagamentoDTO(pa) From Pagamento pa " +
            " JOIN pa.parcela p " +
            " JOIN p.carne as cc " +
            " JOIN cc.venda as v " +
            " JOIN v.loja as l " +
            " JOIN v.cliente as c " +
            " WHERE l.id = :lojaId " +
            " and pa.dataPagto >= :dataInicial " +
            " and pa.dataPagto <= :dataFim " +
            " order by c.id, pa.dataPagto ")
    List<PagamentoDTO> getPagamentosByLoja(@Param("lojaId") Long lojaId,
                                           @Param("dataInicial") Calendar dataInicial,
                                           @Param("dataFim") Calendar dataFim);
}
