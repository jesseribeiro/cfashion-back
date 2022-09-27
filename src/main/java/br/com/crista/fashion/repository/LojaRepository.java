package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.LojaBean;
import br.com.crista.fashion.dto.LojaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LojaRepository extends CrudRepository<LojaBean, Long>, GenericRepository  {

    String FilterPagination = WHERE_EXCLUIDO
            + " and (:id is null or id = :id ) "
            + " and (:nomeFantasia is null or LOWER(x.nomeFantasia) LIKE LOWER(CONCAT('%',:nomeFantasia,'%'))) ";

    @Query(value = "SELECT new br.com.crista.fashion.dto.LojaDTO(x) FROM Loja x "
            + FilterPagination + " order by x.id desc ",
            countQuery = "Select count(x) From Loja x " + FilterPagination)
    Page<LojaDTO> pagination(@Param("id") Long id,
                             @Param("nomeFantasia") String nomeFantasia,
                             Pageable paging);

    @Query(value = "SELECT x FROM Loja x WHERE x.nomeFantasia =:nomeLoja ")
    LojaBean findLojaByNomeFantasia(@Param("nomeLoja") String nomeLoja);
}
