package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.ClienteBean;
import br.com.crista.fashion.bean.LojaBean;
import br.com.crista.fashion.dto.ClienteDTO;
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

    String filterPagination = WHERE_EXCLUIDO
            + " and (:nome is null or LOWER(x.nome) LIKE LOWER(CONCAT('%',:nome,'%'))) "
            + " and (:cpf is null or x.cpf LIKE CONCAT('%',:cpf, '%')) ";

    @Query(value = "SELECT new br.com.crista.fashion.dto.ClienteDTO(x) FROM Cliente x "
            + filterPagination + " order by x.nome asc ",
            countQuery = "Select count(x) From Cliente x " + filterPagination)
    Page<ClienteDTO> pagination(@Param("nome") String nome,
                                @Param("cpf") String cpf,
                                Pageable paging);

    @Query(value = "SELECT x FROM Loja x WHERE x.nomeFantasia =:nomeLoja ")
    LojaBean findLojaByNomeFantasia(@Param("nomeLoja") String nomeLoja);

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


    /*
    String filterDatasVencimento = " and (p.dataVencimento >= :dataInicial and p.dataVencimento <= :dataFinal)";
    String filterParcelas = " From Parcela p " +
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
    */

    @Query(value = "SELECT x FROM Cliente x WHERE x.cpf =:cpf ")
    ClienteBean getClienteByCpf(@Param("cpf") String cpf);

    @Query("Select case when (count(x) > 0)  then true else false end From Cliente x where x.cpf =:cpf")
    boolean existClienteCpf(@Param("cpf") String cpf);
}
