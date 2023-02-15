package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.ComissaoBean;
import br.com.crista.fashion.dto.ComissaoDTO;
import br.com.crista.fashion.enumeration.EnumTipoPagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ComissaoRepository extends CrudRepository<ComissaoBean, Long>, GenericRepository {

    @Query(value = "SELECT x.comissao FROM Comissao x WHERE x.tipo =:tipo ")
    BigDecimal findComissao(@Param("tipo") EnumTipoPagamento tipo);

    @Query(value = "SELECT new br.com.crista.fashion.dto.ComissaoDTO(x) FROM Comissao x "
            + " order by x.id asc ",
            countQuery = "Select count(x) From Comissao x ")
    Page<ComissaoDTO> pagination(Pageable paging);

}
