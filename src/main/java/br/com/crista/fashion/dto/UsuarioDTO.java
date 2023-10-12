package br.com.crista.fashion.dto;

import br.com.crista.fashion.bean.UsuarioBean;
import br.com.crista.fashion.enumeration.EnumRole;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO extends GenericDTO<UsuarioBean> {

    private Long redeId;
    private Long empresaId;
    private Long lojaId;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private List<Long> lojas;
    private List<Long> roles;
    private boolean ativo;
    private String rolesLabel;
    private String roleAtiva;
    private String bloqueioIp;
    private Boolean somenteBoleto;

    public UsuarioDTO(UsuarioBean bean) {

        super(bean.getId());
        this.nome = bean.getNome();
        this.email = bean.getEmail();
        this.login = bean.getLogin();
        this.ativo = bean.getIsAtivo();
        this.senha = null;
        this.bloqueioIp = bean.getBloqueioIp();

        this.somenteBoleto = false;

        this.roles = bean.getRoles().stream().map(role -> role.getId()).collect(Collectors.toList());

        if (!this.lojas.isEmpty()) {

            this.lojaId = this.lojas.get(0);
        }

        // utilizado a lista de roles para mostrar na paginacao, caso deva mostrar as lojas do usuario descomente essa lina
        this.rolesLabel = bean.getRoles().stream().map(role -> role.getNome().name()).collect(Collectors.joining(","));

        if (nonNull(bean.getRoleAtiva())) {

            this.roleAtiva = bean.getRoleAtiva().name();
        }
    }

    public UsuarioDTO(Long id, Long empresaId, String nome, String email, String login, boolean ativo) {

        super(id);
        this.empresaId = empresaId;
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.ativo = ativo;
        this.senha = null;
    }

    public UsuarioBean converter(UsuarioBean bean) {

        bean = super.converter(bean);
        bean.setNome(nome);
        bean.setEmail(email);
        bean.setLogin(login);
        bean.setIsAtivo(ativo);
        bean.setBloqueioIp(bloqueioIp);

        if (nonNull(this.roleAtiva)) {

            bean.setRoleAtiva(EnumRole.valueOf(this.roleAtiva));
        }

        return bean;
    }
}
