package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.MovimentacaoBean;
import br.com.crista.fashion.dto.MovimentacaoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MovimentacaoRepository extends CrudRepository<MovimentacaoBean, Long>, GenericRepository {

    @Query(value = "SELECT new br.com.crista.fashion.dto.MovimentacaoDTO(x) FROM Movimentacao x "
            + " order by x.id asc ",
            countQuery = "Select count(x) From Movimentacao x ")
    Page<MovimentacaoDTO> pagination(Pageable paging);

}
