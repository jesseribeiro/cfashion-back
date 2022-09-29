package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.VendaBean;
import org.springframework.data.repository.CrudRepository;

public interface VendaRepository extends CrudRepository<VendaBean, Long>, GenericRepository {

}
