package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.CepBean;
import org.springframework.data.repository.CrudRepository;

public interface CepRepository extends CrudRepository<CepBean, Long> {

    CepBean findByCep(String cep);
}
