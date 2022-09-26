package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.ParcelaBean;
import br.com.crista.fashion.enumeration.EnumStatusParcela;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Calendar;
import java.util.List;

public interface ParcelaRepository extends CrudRepository<ParcelaBean, Long>, GenericRepository {

    @Query(" SELECT min(p.id) From Parcela p " +
                    " JOIN p.carne cc " +
                    " JOIN cc.venda v " +
                    " JOIN v.cliente c " +
                    " WHERE " +
                    " c.id =:clienteId " +
                    " and p.status = 'NAO_PAGA'" +
                    " and p.dataVencimento = (SELECT min(p.dataVencimento) From Parcela p " +
                    " JOIN p.carne cc " +
                    " JOIN cc.venda v " +
                    " JOIN v.cliente c " +
                    " WHERE " +
                    " c.id =:clienteId" +
                    " and p.status = 'NAO_PAGA'" +
                    " and p.dataVencimento < :dataAtual)"
    )
    Long findParcelaNaoPagaComMaiorAtrasoDoCliente(@Param("clienteId") Long clienteId, @Param("dataAtual") Calendar dataAtual);

    @Query("Select x From Parcela x where x.carne.id =:carneId order by x.numero ")
    List<ParcelaBean> findParcelasByCarneId(@Param("carneId") Long carneId);

    //@Query("Select case when (count(x) > 0)  then true else false end From Loja x where x.empresa.id = :empresaId and (x.isExcluido is null or x.isExcluido is false) ")
    @Query("Select case when (count(x) > 0)  then true else false end From Parcela x where x.status = 'NAO_PAGA' and x.carne.id =:carneId")
    boolean existParcelaNaoPagaDoCarne(@Param("carneId") Long carneId);

    @Modifying
    @Query(value = "update parcela set status=:status where id in (:ids)", nativeQuery = true)
    void updateStatusParcelasByIds(@Param("status") EnumStatusParcela status, @Param("ids") List<Long> parcelasIds);

    @Query(value = " Select case when (count(p) > 0) then true else false end " +
            " From Parcela p " +
            " JOIN p.carne cc " +
            " JOIN cc.venda v " +
            " JOIN v.cliente c " +
            " where " +
            " c.id =:clienteId " +
            " and p.dataVencimento <:dataAtual " +
            " and p.status = 'NAO_PAGA' ")
    boolean existParcelaAtrasada(@Param("clienteId") Long clienteId,  @Param("dataAtual") Calendar dataAtual);

    @Query(value = " select l.id from loja l " +
            " where " +
            " l.empresa_id = :empresaId " +
            " and not exists(select 1 from regra_financeira " +
            " where loja_id = l.id) ",nativeQuery = true)
    List<Long> getVendasNaoRepassadasEmpresa(@Param("empresaId") long empresaId);

    @Query(value = " select l.id from loja l " +
            " join empresa e on e.id = l.empresa_id " +
            " where " +
            " e.rede_id = :redeId " +
            " and not exists(select 1 from regra_financeira  where empresa_id = e.id) " +
            " and not exists(select 1 from regra_financeira where loja_id = l.id) ",nativeQuery = true)
    List<Long> getVendasNaoRepassadasRede(@Param("redeId") long redeId);

    @Query(value = " Select case when (count(p) > 0) then true else false end " +
            " From Parcela p " +
            " JOIN p.carne cc " +
            " JOIN cc.venda v " +
            " JOIN v.cliente c " +
            " where " +
            " v.cliente.id =:clienteId " +
            " and v.loja.id =:lojaId " +
            " and p.dataVencimento >=:dataInicial " +
            " and p.dataVencimento <=:dataFinal " +
            " and p.status = 'NAO_PAGA' ")
    boolean existParcelaNaoPagaAno(@Param("clienteId") Long clienteId,
                                   @Param("lojaId") Long lojaId,
                                   @Param("dataInicial") Calendar dataInicial,
                                   @Param("dataFinal") Calendar dataFinal);
}
