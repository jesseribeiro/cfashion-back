package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.ClienteBean;
import br.com.crista.fashion.dto.ClienteDTO;
import br.com.crista.fashion.dto.ClienteLojaDTO;
import br.com.crista.fashion.dto.PagamentoDTO;
import br.com.crista.fashion.dto.ParcelaClienteDTO;
import br.com.crista.fashion.enumeration.EnumStatusParcela;
import br.com.crista.fashion.enumeration.TipoFormaPagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public interface  ClienteRepository extends CrudRepository<ClienteBean, Long>, GenericRepository {

    @Query("Select new br.com.crista.fashion.dto.ClienteDTO(x) from Cliente x "+WHERE_EXCLUIDO)
    List<ClienteDTO> findAllDTO();

    @Query(value = "Select count(1) from Cliente x where x.data_cadastro <:data", nativeQuery = true)
    BigInteger qtdClientesData(@Param("data") Calendar data);

    String filterPagination = " From ClienteLoja as cl JOIN cl.cliente as x JOIN cl.loja as l "
            + WHERE_EXCLUIDO
            + " and (:lojaId is null or l.id = :lojaId ) "
            + " and (:nome is null or LOWER(x.nome) LIKE LOWER(CONCAT('%',:nome, '%'))) "
            + " and (:cpf is null or x.cpf LIKE CONCAT('%',:cpf, '%')) "
            + " and (:dataNascimento is null or x.dataNascimento =:dataNascimento) "
            + " and (:telefone is null or ((x.telResidencial LIKE CONCAT('%',:telefone, '%')) or (x.celular LIKE CONCAT('%',:telefone, '%')) or (x.telComercial LIKE CONCAT('%',:telefone, '%')))) "
            + " and (:identidade is null or x.identidade = :identidade) ";

    @Query(value = "SELECT new br.com.crista.fashion.dto.ClienteLojaDTO(cl) " + filterPagination,
            countQuery = "Select count(x) " + filterPagination)
    Page<ClienteLojaDTO> pagination(@Param("lojaId") Long lojaId,
                                    @Param("nome") String nome,
                                    @Param("cpf") String cpf,
                                    @Param("dataNascimento") Calendar dataNascimento,
                                    @Param("telefone") String telefone,
                                    @Param("identidade") String identidade,
                                    Pageable paging);

    Optional<ClienteBean> findByNome(String nome);

    Optional<ClienteBean> findByCpf(String cpf);

    @Query(" Select new br.com.crista.fashion.dto.ClienteDTO(x) " +
            " from Cliente x " +
            " Where x.id = :id")
    ClienteDTO findDTO(@Param("id") Long id);

    @Query( value = " select \n" +
            "\tc.limite_compartilhado - (COALESCE(sum(compras.valor_total),0) + COALESCE(sum(acordo.valor_total),0))\n" +
            "\tfrom cliente c\n" +
            "left join (\n" +
            "\tselect \n" +
            "\t\tc.id as cliente_id,\n" +
            "\t\tCOALESCE(sum(p.valor),0) as valor_total\n" +
            "\t\tfrom cliente c\n" +
            "\t\tjoin venda v on v.cliente_id = c.id\n" +
            "\t\tjoin carne ca on ca.venda_id = v.id\n" +
            "\t\tjoin parcela p on p.carne_id = ca.id\n" +
            "\t\twhere \n" +
            "\t\tv.limite_exclusivo_id is null\n" +
            "\t\tand p.status = 'NAO_PAGA'\n" +
            "\t\tgroup by \n" +
            "\t\tc.id\n" +
            "\t) as compras on compras.cliente_id = c.id\n" +
            "left join (\n" +
            "\tselect \n" +
            "\t\tc.id as cliente_id,\n" +
            "\t\tCOALESCE(sum(p.valor),0) as valor_total\n" +
            "\t\tfrom cliente c\n" +
            "\t\tjoin renegociacao r on r.cliente_id = c.id\n" +
            "\t\tjoin carne ca on ca.renegociacao_id = r.id\n" +
            "\t\tjoin parcela p on p.carne_id = ca.id\n" +
            "\t\twhere \n" +
            "\t\tp.status = 'NAO_PAGA'\n" +
            "\t\tgroup by \n" +
            "\t\tc.id\n" +
            "\t) as acordo on acordo.cliente_id = c.id\n" +
            "\tWHERE c.id =:clienteId \n" +
            "\tgroup by\n" +
            "\tc.limite_compartilhado ", nativeQuery = true)
    BigDecimal findLimiteCompartilhadoDisponivel(@Param("clienteId") Long clienteId);

    @Query( " Select le.limite - COALESCE((Select sum(p.valor) FROM Carne cc " +
                " join cc.venda v " +
                " join cc.parcelas p" +
                " WHERE v.cliente.id =:clienteId " +
                " and v.loja.id =:lojaId " +
                " and v.limiteExclusivo is not null " +
                " and p.status = 'NAO_PAGA'), 0) " +
            " FROM LimiteExclusivo le WHERE le.cliente.id =:clienteId and le.loja.id =:lojaId ")
    BigDecimal findLimiteExclusivoDisponivel(@Param("clienteId") Long clienteId, @Param("lojaId") Long lojaId);

    @Query( " Select COALESCE(le.limite, 0) FROM LimiteExclusivo le WHERE le.cliente.id =:clienteId and le.loja.id =:lojaId ")
    BigDecimal findLimiteExclusivoCliente(@Param("clienteId") Long clienteId, @Param("lojaId") Long lojaId);


    String filterDatasPagto = " and (x.dataPagto >= :dataInicial and x.dataPagto <= :dataFinal)";
    String filterPagto = " From Pagamento as x " +
            " JOIN x.parcela as p " +
            " JOIN p.carne as cc " +
            " JOIN cc.venda as v " +
            " JOIN v.cliente as c " +
            " WHERE c.id = :clienteId " +
            " and (:lojaId is null or v.loja.id = :lojaId) ";
    @Query(value = "SELECT new br.com.crista.fashion.dto.PagamentoDTO(x) " + filterPagto + filterDatasPagto,
            countQuery = "Select count(*) " + filterPagto + filterDatasPagto)
    Page<PagamentoDTO> paginationPagamentos(@Param("clienteId") Long clienteId,
                                            @Param("lojaId") Long lojaId,
                                            @Param("dataInicial") Calendar dataInicial,
                                            @Param("dataFinal") Calendar dataFinal,
                                            Pageable paging);

    @Query(value = "SELECT new br.com.crista.fashion.dto.PagamentoDTO(x) " + filterPagto ,
            countQuery = "Select count(*) " + filterPagto)
    Page<PagamentoDTO> paginationPagamentosSemDatas(@Param("clienteId") Long clienteId,
                                                           @Param("lojaId") Long lojaId,
                                                           Pageable paging);

    String filterDatasVencimento = " and (p.dataVencimento >= :dataInicial and p.dataVencimento <= :dataFinal)";
    String filterParcelas = " From Parcela p " +
            " LEFT JOIN p.pagamento pa " +
            " JOIN p.carne as cc " +
            " JOIN cc.venda as v " +
            " JOIN v.loja as l " +
            " JOIN v.cliente as c " +
            " WHERE c.id = :clienteId " +
            " and (:lojaId is null or l.id = :lojaId) " +
            " and (:status is null or p.status = :status) " +
            " and (:formaPagamento is null or cc.formaPagamento = :formaPagamento) ";
    @Query(value = "SELECT new br.com.crista.fashion.dto.ParcelaClienteDTO(p) " + filterParcelas + filterDatasVencimento,
            countQuery = "Select count(*) " + filterParcelas + filterDatasVencimento)
    Page<ParcelaClienteDTO> paginationParcelas(@Param("clienteId") Long clienteId,
                                               @Param("lojaId") Long lojaId,
                                               @Param("status") EnumStatusParcela status,
                                               @Param("dataInicial") Calendar dataInicial,
                                               @Param("dataFinal") Calendar dataFinal,
                                               @Param("formaPagamento") TipoFormaPagamento formaPagamento,
                                               Pageable paging);

    @Query(value = "SELECT new br.com.crista.fashion.dto.ParcelaClienteDTO(p) " + filterParcelas ,
            countQuery = "Select count(*) " + filterParcelas)
    Page<ParcelaClienteDTO> paginationParcelasSemDatas(@Param("clienteId") Long clienteId,
                                                       @Param("lojaId") Long lojaId,
                                                       @Param("status") EnumStatusParcela status,
                                                       @Param("formaPagamento") TipoFormaPagamento formaPagamento,
                                                       Pageable paging);

    @Query("SELECT count(1) FROM Pagamento p JOIN p.parcela pa JOIN pa.carne ca JOIN ca.venda v WHERE v.cliente.id =:clienteId ")
    Integer getQtdPagamentos(@Param("clienteId") Long clienteId);

    @Query(value = "SELECT x FROM Cliente x WHERE x.cpf =:cpf ")
    ClienteBean getClienteByCpf(@Param("cpf") String cpf);

    @Query("Select case when (count(x) > 0)  then true else false end From Cliente x where x.cpf =:cpf")
    boolean existClienteCpf(@Param("cpf") String cpf);
}
