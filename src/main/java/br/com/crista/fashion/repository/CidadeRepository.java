package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.CidadeBean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CidadeRepository extends CrudRepository<CidadeBean, Long>, GenericRepository {

    @Query("Select x from Cidade x "+WHERE_EXCLUIDO+" and x.estado.uf =:uf ")
    List<CidadeBean> findByUf(@Param("uf") String uf);

    CidadeBean findByIbge(Integer ibge);
}
