package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.AutorizacaoBean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AutorizacaoRepository extends CrudRepository<AutorizacaoBean, Long>, GenericRepository {

    @Query("Select x from Autorizacao x "+WHERE_EXCLUIDO+" and x.venda.id =:vendaId")
    AutorizacaoBean autorizacaoVenda(@Param("vendaId") Long vendaId);
}
