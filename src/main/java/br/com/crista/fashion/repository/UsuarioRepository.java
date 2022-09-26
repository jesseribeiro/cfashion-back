package br.com.crista.fashion.repository;

import br.com.crista.fashion.bean.UsuarioBean;
import br.com.crista.fashion.dto.UsuarioRoleDTO;
import br.com.crista.fashion.dto.UsuarioDTO;
import br.com.crista.fashion.enumeration.EnumRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<UsuarioBean, Long>, GenericRepository  {

    @Query("Select u From Usuario u join fetch u.roles where u.id =?1")
    Optional<UsuarioBean> findById(Long id);

    @Query("Select u From Usuario u join fetch u.roles where LOWER(u.login) = LOWER(?1) and (u.isExcluido is null or u.isExcluido is false) and u.isAtivo is true ")
    Optional<UsuarioBean> findByLogin(String login);

    @Query("Select case when (count(u) > 0)  then true else false end From Usuario u where u.login = ?1 ")
    Boolean existsByLogin(String login);
// Aoo instalar a extension unaccent do Postgres, é possível fazer consultas ignorando a acentuação assim: + " and (:nome is null or unaccent(LOWER(u.nome)) LIKE unaccent(LOWER(CONCAT('%',:nome, '%')))) "
    String FilterPagination = " WHERE (u.isExcluido is null or u.isExcluido is false) "
            + " and (:nome is null or LOWER(u.nome) LIKE LOWER(CONCAT('%',:nome, '%'))) "
            + " and (:login is null or LOWER(u.login) LIKE LOWER(CONCAT('%',:login, '%'))) "
            + " and (:email is null or LOWER(u.email) LIKE LOWER(CONCAT('%',:email, '%'))) ";
    //@Query("SELECT new br.com.crista.fashion.dto.UsuarioDTO(u.id, u.empresa.id, u.nome, u.email, u.login, u.isAtivo) FROM Usuario u ")
    //LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%'))
    @Query(value = "SELECT new br.com.crista.fashion.dto.UsuarioDTO(u) FROM Usuario u "
            + FilterPagination ,
            countQuery = "Select count(u) From Usuario u " + FilterPagination)
    Page<UsuarioDTO> pagination(@Param("nome") String nome, @Param("login") String login, @Param("email") String email, Pageable pageable);

    @Query(value = "SELECT x from Usuario x where x.roleAtiva =:role")
    List<UsuarioBean> getAllUsuariosByRole(@Param("role") EnumRole role);

    @Query(value = "SELECT x from Usuario x where x.roleAtiva in ('ADMIN','SUPERVISOR','COMERCIAL','NEGOCIADOR') ")
    List<UsuarioBean> getAllUsuariosByRolePratico();

    @Query(value = "SELECT x from Usuario x where x.roleAtiva in ('ADMIN','SUPERVISOR') ")
    List<UsuarioBean> getAllUsuariosSupAdmin();

    @Query(value = "SELECT new br.com.crista.fashion.dto.UsuarioRoleDTO(x) " +
            " from Usuario x " +
            " where " +
            " x.roleAtiva in (:role)")
    List<UsuarioRoleDTO> getAllUsuariosDTOByRole(@Param("role") EnumRole[] role);
    
}