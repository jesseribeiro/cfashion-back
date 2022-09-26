package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.CidadeBean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CidadeRepository extends CrudRepository<CidadeBean, Long>, GenericRepository {

    @Query("Select x from Cidade x "+WHERE_EXCLUIDO+" and x.estado.uf =:uf ")
    List<CidadeBean> findByUf(@Param("uf") String uf);

    @Query("Select x.id from Cidade x where LOWER(x.nome) =LOWER(:nome) ")
    List<Integer> findCidadeIbgeByNome (@Param("nome") String nome);

    CidadeBean findByIbge(Integer ibge);

    @Query("Select x from Cidade x where LOWER(x.nome) =LOWER(:nome) ")
    List<CidadeBean> findCidadesByNome (@Param("nome") String nome);

    @Query("Select x from Cidade x where LOWER(x.nomeSemAcento) =LOWER(:nome) ")
    List<CidadeBean> findCidadesByNomeSemAcento(@Param("nome") String nome);
}
