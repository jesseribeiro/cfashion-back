package br.com.crista.fashion.security.service;

import br.com.crista.fashion.bean.UsuarioBean;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class UsuarioPrincipal implements UserDetails {
    private static final long serialVersionUID = 1L;

    private UsuarioBean usuario;
    private Collection<? extends GrantedAuthority> authorities;

    public UsuarioPrincipal(UsuarioBean usuario,
                         Collection<? extends GrantedAuthority> authorities) {
        this.usuario = usuario;
        this.authorities = authorities;
    }

    public static UsuarioPrincipal build(UsuarioBean usuario) {
        List<GrantedAuthority> authorities = usuario.getRoles().stream().map(usuarioRole ->
                new SimpleGrantedAuthority(usuarioRole.getNome().name())
        ).collect(Collectors.toList());

        return new UsuarioPrincipal(usuario,
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getLogin();
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsuarioPrincipal user = (UsuarioPrincipal) o;
        return Objects.equals(usuario.getId(), user.getUsuario().getId());
    }
}
