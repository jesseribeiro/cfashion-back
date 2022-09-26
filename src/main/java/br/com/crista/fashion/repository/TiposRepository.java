package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.GenericBean;
import br.com.crista.fashion.dto.CodigoDescricaoDTO;
import br.com.crista.fashion.dto.LabelDescricaoDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TiposRepository extends CrudRepository<GenericBean, Long>, GenericRepository {

    @Query("SELECT new br.com.crista.fashion.dto.CodigoDescricaoDTO(x.id , x.nomeFantasia) FROM Loja x "+WHERE_EXCLUIDO)
    List<CodigoDescricaoDTO> findAllLojas();

    @Query("SELECT new br.com.crista.fashion.dto.LabelDescricaoDTO(x.login, x.nome) FROM Usuario x " + WHERE_EXCLUIDO)
    List<LabelDescricaoDTO> findAllUsuarios();
}
