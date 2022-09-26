package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.ClienteLojaBean;
import br.com.crista.fashion.bean.LojaBean;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

public interface ClienteLojaRepository extends CrudRepository<ClienteLojaBean, Long>, GenericRepository {

    @Query("SELECT case when (count(x) > 0) then true else false end FROM ClienteLoja x WHERE x.cliente.id = :clienteId and x.loja.id = :lojaId ")
    Boolean existsClienteIdAndLojaId(@Param("clienteId") Long clienteId, @Param("lojaId") Long lojaId);

    ClienteLojaBean findByClienteIdAndLojaId(Long clienteId, Long lojaId);

    @Query("SELECT x FROM ClienteLoja x WHERE x.cliente.id = :clienteId and x.loja.id <> :lojaId ")
    List<ClienteLojaBean> findOutrasLojasCliente(@Param("clienteId") Long clienteId, @Param("lojaId") Long lojaId);

    @Query(value = "SELECT x FROM ClienteLoja x WHERE x.cliente.id =:clienteId ")
    List<ClienteLojaBean> findLojaByClienteId(@Param("clienteId") Long clienteId);

    @Query(value = "SELECT x.loja FROM ClienteLoja x WHERE x.cliente.id =:clienteId ")
    List<LojaBean> findAllLojasCliente(@Param("clienteId") Long clienteId);

    @Modifying
    @Transactional
    @Query(value = "update cliente_loja set segmentacao_cliente =:segmento, segmento_atualizado = true where id in :lista ", nativeQuery = true)
    void updateClienteSegmento(@Param("segmento") String segmento, @Param("lista") List<Long> lista);

    @Modifying
    @Transactional
    @Query(value = "update cliente_loja set segmento_atualizado = false", nativeQuery = true)
    void updateAllSegmentoCliente();

    @Query(value = "SELECT count(x) FROM ClienteLoja x WHERE x.loja.id =:lojaId " +
            " and x.dataCadastro >=:dataInicial " +
            " and x.dataCadastro <=:dataFinal")
    Integer countClientesMes (@Param("dataInicial") Calendar dataInicial,
                              @Param("dataFinal") Calendar dataFinal,
                              @Param("lojaId") Long lojaId);

    @Query(" SELECT x FROM ClienteLoja x WHERE x.loja.id =:lojaId ")
    List<ClienteLojaBean> findClientesByLojaId(@Param("lojaId") Long lojaId);
}
