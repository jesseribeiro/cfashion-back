package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.LimiteExclusivoBean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LimiteExclusivoRepository extends CrudRepository<LimiteExclusivoBean, Long> {

    @Query("SELECT x FROM LimiteExclusivo x WHERE x.cliente.id =:clienteId and x.loja.id =:lojaId")
    Optional<LimiteExclusivoBean> findLimiteClienteByLoja(@Param("clienteId") Long clienteId, @Param("lojaId") Long lojaId);

    @Query("SELECT x FROM LimiteExclusivo x WHERE x.cliente.id =:clienteId")
    List<LimiteExclusivoBean> findAllLimiteByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT x FROM LimiteExclusivo x WHERE x.loja.id =:lojaId")
    List<LimiteExclusivoBean> findAllLimiteByLojaId(@Param("lojaId") Long lojaId);
}
