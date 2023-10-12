package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.GenericBean;
import br.com.crista.fashion.bean.RoleBean;
import br.com.crista.fashion.bean.UsuarioBean;
import br.com.crista.fashion.enumeration.EnumRole;
import br.com.crista.fashion.security.jwt.JwtProvider;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Objects.nonNull;

@Slf4j
@Getter
@Setter
public class GenericService<T extends GenericBean, DAO extends CrudRepository<T, Long>> {

    @Autowired
    private DAO repository;

    @Autowired
    private UsuarioLogadoService usuarioLogadoService;

    @Autowired
    JwtProvider jwtProvider;

    public UsuarioBean getUsuarioLogado() {

        return usuarioLogadoService.getUsuarioLogado();
    }

    public String getLoginUsuarioLogado() {

        UsuarioBean usuarioLogado = getUsuarioLogado();

        if (nonNull(usuarioLogado)) {

            return usuarioLogado.getLogin();
        }

        return null;
    }

    public T save(T bean) {

        if (nonNull(bean.getId())) {

            return update(bean);
        }

        bean.setDataCadastro(Calendar.getInstance());

        return repository.save(bean);
    }

    public T saveNoDate(T bean) {

        return repository.save(bean);
    }

    public T update(T bean) {

        bean.setDataAlteracao(Calendar.getInstance());

        return repository.save(bean);
    }

    public void delete(T bean) {

        bean.setDataExclusao(Calendar.getInstance());
        bean.setExcluido(true);
        repository.save(bean);
    }

    public T getById(Long id) {

        return repository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Entidade não encontrada")
        );
    }

    public List<T> convertIterableToList(Iterable<T> iterable) {

        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Foi criado esse controle de permissão aqui apenas de exemplo para onde realmente deve ser aplicado.
     * Funciona assim:
     * Quando a interface enviar um comando que precise de permissão, deverá ser passado para o método se o usuário
     * que fez a requisição tem permissão, caso não possua, deverá retornar status (405) METHOD_NOT_ALLOWED.
     * Na interface deverá receber esse código de resposta e abrir a tela de controle de permissão.
     * Ao informar o usuario e senha será chamado o comando de login que criará o token, ai será enviado novamente esse comando
     * com esse token nos params do request.
     * Dessa forma é possível via token descriptografálo e pegar as informaçoes do usuário e roles deles e validar se tem permissão
     * para realizar a operação, caso tenha então deixa executar a operação senão retornar o status (405) e o fluxo se inicia.
     */
    public boolean hasPermissao(HttpServletRequest request, EnumRole[] rolesPermitida) {

        if (hasPermissaoUsuarioLogado(rolesPermitida)) {

            return true;
        }

        if (nonNull(request) && nonNull(rolesPermitida)) {

            String token = request.getParameter("accessToken");

            if (nonNull(token)) {

                List<String> roles = jwtProvider.getRolesUsuarioByToken(token);

                if (nonNull(roles)) {

                    for (String roleUsuario: roles) {

                        for (EnumRole rolePermissao : rolesPermitida) {

                            if (rolePermissao.name().equalsIgnoreCase(roleUsuario)) {

                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean hasPermissaoUsuarioLogado(EnumRole [] rolesPermitida) {

        UsuarioBean usuarioLogado = getUsuarioLogado();

        if (nonNull(usuarioLogado) && nonNull(rolesPermitida)) {

            for (RoleBean roleUsuario : usuarioLogado.getRoles()) {

                for(EnumRole rolePermissao : rolesPermitida) {

                    if (rolePermissao.name().equalsIgnoreCase(roleUsuario.getNome().name())) {

                        return true;
                    }
                }
            }
        }

        return false;
    }
}
