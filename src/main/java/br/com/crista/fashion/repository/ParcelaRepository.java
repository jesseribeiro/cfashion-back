package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.ParcelaBean;
import org.springframework.data.repository.CrudRepository;

public interface ParcelaRepository extends CrudRepository<ParcelaBean, Long>, GenericRepository {

}
