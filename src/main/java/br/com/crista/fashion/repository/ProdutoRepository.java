package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.ProdutoBean;
import org.springframework.data.repository.CrudRepository;

public interface ProdutoRepository extends CrudRepository<ProdutoBean, Long>, GenericRepository {
    ProdutoBean findByNome(String nome);
}
