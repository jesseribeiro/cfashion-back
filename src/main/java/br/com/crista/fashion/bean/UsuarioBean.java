package br.com.crista.fashion.bean;

import br.com.crista.fashion.enumeration.EnumRole;
import br.com.crista.fashion.utils.NullUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.json.simple.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Audited
@AllArgsConstructor
@Getter
@Setter
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
        return NullUtils.isNull(isAtivo);
    }
}
