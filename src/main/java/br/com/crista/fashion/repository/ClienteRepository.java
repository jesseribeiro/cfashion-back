package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.ClienteBean;
import br.com.crista.fashion.dto.ClienteDTO;
import br.com.crista.fashion.dto.ParcelaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public interface  ClienteRepository extends CrudRepository<ClienteBean, Long>, GenericRepository {

    @Query("Select new br.com.crista.fashion.dto.ClienteDTO(x) from Cliente x "+WHERE_EXCLUIDO)
    List<ClienteDTO> findAllDTO();

    String filterPagination = WHERE_EXCLUIDO
            + " and (:nome is null or LOWER(x.nome) LIKE LOWER(CONCAT('%',:nome,'%'))) "
            + " and (:cpf is null or x.cpf LIKE CONCAT('%',:cpf, '%')) ";

    @Query(value = "SELECT new br.com.crista.fashion.dto.ClienteDTO(x) FROM Cliente x "
            + filterPagination + " order by x.nome asc ",
            countQuery = "Select count(x) From Cliente x " + filterPagination)
    Page<ClienteDTO> pagination(@Param("nome") String nome,
                                @Param("cpf") String cpf,
                                Pageable paging);

    Optional<ClienteBean> findByCpf(String cpf);

    @Query(" Select new br.com.crista.fashion.dto.ClienteDTO(x) " +
            " from Cliente x " +
            " Where x.id = :id")
    ClienteDTO findDTO(@Param("id") Long id);

    String filterDatasPagto = " and (x.dataPagto >= :dataInicial and x.dataPagto <= :dataFinal)";
    String filterPagto = " From Parcela as x " +
            " JOIN x.cliente as c " +
            " WHERE c.id = :clienteId" +
            " and x.status <> 'NAO_PAGA' ";
    @Query(value = "SELECT new br.com.crista.fashion.dto.ParcelaDTO(x) " + filterPagto + filterDatasPagto,
            countQuery = "Select count(*) " + filterPagto + filterDatasPagto)
    Page<ParcelaDTO> paginationPagamentos(@Param("clienteId") Long clienteId,
                                          @Param("dataInicial") Calendar dataInicial,
                                          @Param("dataFinal") Calendar dataFinal,
                                          Pageable paging);

    @Query(value = "SELECT new br.com.crista.fashion.dto.ParcelaDTO(x) " + filterPagto ,
            countQuery = "Select count(*) " + filterPagto)
    Page<ParcelaDTO> paginationPagamentosSemDatas(@Param("clienteId") Long clienteId,
                                                  Pageable paging);
}
