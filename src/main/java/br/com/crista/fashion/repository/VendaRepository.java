package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.VendaBean;
import br.com.crista.fashion.dto.VendaDTO;
import br.com.crista.fashion.enumeration.EnumStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Calendar;

public interface VendaRepository extends CrudRepository<VendaBean, Long>, GenericRepository {

    String FilterPagination = WHERE_EXCLUIDO
            + " and (:clienteId is null or c.id =:clienteId ) "
            + " and (:marcaId is null or l.id =:marcaId ) "
            + " and (:status is null or x.status =:status) "
            + " and (:dataInicial is null or x.dataVenda >=:dataInicial) "
            + " and (:dataFinal is null or x.dataVenda <=:dataFinal) ";

    @Query(value = "SELECT new br.com.crista.fashion.dto.VendaDTO(x) FROM Venda x JOIN x.cliente as c JOIN x.loja as l "
            + FilterPagination + " order by x.dataVenda desc ",
            countQuery = "Select count(x) From Venda x JOIN x.cliente as c JOIN x.loja as l " + FilterPagination)
    Page<VendaDTO> pagination(@Param("clienteId") Long clienteId,
                              @Param("marcaId") Long marcaId,
                              @Param("status") EnumStatus status,
                              @Param("dataInicial") Calendar dataInicial,
                              @Param("dataFinal") Calendar dataFinal,
                              Pageable paging);
}
