package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.MovimentacaoBean;
import br.com.crista.fashion.dto.MovimentacaoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MovimentacaoRepository extends CrudRepository<MovimentacaoBean, Long>, GenericRepository {

    String FilterPagination = WHERE_EXCLUIDO;

    @Query(value = "SELECT new br.com.crista.fashion.dto.MovimentacaoDTO(x) FROM Movimentacao x "
            + FilterPagination + " order by x.dataLancamento desc ",
            countQuery = "Select count(x) From Movimentacao x " + FilterPagination)
    Page<MovimentacaoDTO> pagination(Pageable paging);

}
