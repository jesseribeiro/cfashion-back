package br.com.crista.fashion.bean;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.envers.NotAudited;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.crista.fashion.enumeration.EnumRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
@Entity( name = "Usuario")
@Table(name = "usuario", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "login"
        })
})
public class UsuarioBean extends GenericBean {

    private String nome;
    private String email;

    @NotBlank
    @Size(min=3, max = 50)
    private String login;

    private String senha;

    @Column(name = "is_ativo")
    private Boolean isAtivo;

    @Column(name = "bloqueio_ip")
    private String bloqueioIp;

    private String ip;

    @Enumerated(EnumType.STRING)
    private EnumRole roleAtiva;

    @JsonIgnore
    @NotAudited
    @ManyToMany
    @JoinTable(name="usuario_role", joinColumns=
            {@JoinColumn(name="usuario_id")}, inverseJoinColumns=
            {@JoinColumn(name="role_id")})
    private List<RoleBean> roles;

    public UsuarioBean() {
        roles = new ArrayList<>();
    }

    @Override
    public JSONObject jsonAudit() {

        JSONObject obj = new JSONObject();
        obj.put("nome", this.getNome());
        obj.put("email", this.getEmail());
        obj.put("login", this.getLogin());
        obj.put("roleAtiva", this.getRoleAtiva());
        return obj;
    }

    public Boolean getIsAtivo() {

        return isNull(isAtivo);
    }
}
