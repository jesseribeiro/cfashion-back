package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.LojaBean;
import br.com.crista.fashion.dto.LojaDTO;
import br.com.crista.fashion.enumeration.StatusLoja;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LojaRepository extends CrudRepository<LojaBean, Long>, GenericRepository  {

    String FilterPagination = WHERE_EXCLUIDO
            + " and (:id is null or id = :id ) "
            + " and (:razaoSocial is null or LOWER(x.razaoSocial) LIKE LOWER(CONCAT('%',:razaoSocial, '%'))) "
            + " and (:nomeFantasia is null or LOWER(x.nomeFantasia) LIKE LOWER(CONCAT('%',:nomeFantasia,'%'))) "
            + " and (:cnpj is null or x.cnpj = :cnpj) "
            + " and (:status is null or x.status = :status) ";

    @Query(value = "SELECT new br.com.crista.fashion.dto.LojaDTO(x) FROM Loja x "
            + FilterPagination + " order by x.razaoSocial ",
            countQuery = "Select count(x) From Loja x " + FilterPagination)
    Page<LojaDTO> pagination(@Param("id") Long id, @Param("razaoSocial") String razaoSocial,
                             @Param("nomeFantasia") String nomeFantasia,
                             @Param("cnpj") String cnpj, @Param("status") StatusLoja status,
                             Pageable paging);

    @Query(value = "SELECT x FROM Loja x WHERE x.razaoSocial =:nomeLoja ")
    LojaBean findLojaByRazaoSocial(@Param("nomeLoja") String nomeLoja);

    @Query(value= " SELECT count(1) from venda v WHERE " +
            " DATE_PART('month', v.data_venda) = DATE_PART('month', current_date) " +
            " and DATE_PART('year', v.data_venda) = DATE_PART('year', current_date) " +
            " and v.loja_id =:lojaId ", nativeQuery = true)
    Long qtdVendasLojaMes(@Param("lojaId")Long lojaId);
}
