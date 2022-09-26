package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.LojaEnderecoBean;
import org.springframework.data.repository.CrudRepository;

public interface LojaEnderecoRepository extends CrudRepository<LojaEnderecoBean, Long>, GenericRepository  {
}
