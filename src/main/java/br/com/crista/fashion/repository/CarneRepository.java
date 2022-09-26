package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.CarneBean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarneRepository extends CrudRepository<CarneBean, Long>, GenericRepository {

    @Query("Select x From Carne x where x.venda.id =:vendaId")
    CarneBean findByVendaId(@Param("vendaId") Long vendaId);

    @Query("Select distinct c From Parcela p JOIN p.carne c where p.id in (:ids) ")
    List<CarneBean> findCarnesByParcelasIds(@Param("ids") List<Long> parcelasIds);

    @Query(value = " SELECT * from Carne x " +
            " where x.regra_id is null " +
            " limit 2000 ", nativeQuery = true)
    List<CarneBean> findCarneSemRegra();

    @Query(value = " SELECT count(1) from Carne x " +
            " where x.regra_id is null ", nativeQuery = true)
    Integer CountCarneSemRegra();

    @Query(value = " select * from carne c " +
            " join venda v on v.id = c.venda_id " +
            " join cliente cl on cl.id = v.cliente_id " +
            " where " +
            " c.status = 'PAGO' " +
            " and exists(select 1 from parcela where carne_id = c.id and status = 'NAO_PAGA') ", nativeQuery = true)
    List<CarneBean> listaCarnePagosComParcelasAbertas();
}
