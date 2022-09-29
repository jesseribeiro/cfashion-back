package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.ProdutoBean;
import br.com.crista.fashion.dto.ProdutoDTO;
import br.com.crista.fashion.enumeration.EnumCategoria;
import br.com.crista.fashion.enumeration.EnumTamanho;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProdutoRepository extends CrudRepository<ProdutoBean, Long>, GenericRepository {

    String FilterPagination = WHERE_EXCLUIDO
            + " and (:id is null or x.id = :id ) "
            + " and (:lojaId is null or l.id = :lojaId ) "
            + " and (:categoria is null or x.categoria =:categoria) "
            + " and (:tamanho is null or x.tamanho =:tamanho) ";

    @Query(value = "SELECT new br.com.crista.fashion.dto.ProdutoDTO(x) FROM Produto x LEFT JOIN x.marca as l "
            + FilterPagination + " order by x.id asc ",
            countQuery = "Select count(x) From Produto x JOIN x.marca as l " + FilterPagination)
    Page<ProdutoDTO> pagination(@Param("id") Long id,
                                @Param("lojaId") Long lojaId,
                                @Param("categoria") EnumCategoria categoria,
                                @Param("tamanho") EnumTamanho tamanho,
                                Pageable paging);

    @Query(value = "SELECT x FROM Produto x WHERE x.nome =:nome ")
    ProdutoBean findByProduto(@Param("nome") String nome);
}
