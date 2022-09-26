package br.com.crista.fashion.bean;

import br.com.crista.fashion.utils.NullUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.Calendar;

@MappedSuperclass
public abstract class GenericBean {
    @Id
    @Column(
            name = "id"
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    protected Long id;

    @Column(name = "data_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataCadastro;

    @Column(name = "data_exclusao")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataExclusao;

    @Column(name = "data_alteracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataAlteracao;

    @Column(name = "is_excluido")
    private Boolean isExcluido;

    @Column(name = "algorix_id")
    @JsonIgnore
    private Long algorixId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_excluiu_id")
    @JsonIgnore
    private UsuarioBean usuarioExcluiu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_alteracao_id")
    @JsonIgnore
    private UsuarioBean usuarioAlteracao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getDataExclusao() {
        return dataExclusao;
    }

    public void setDataExclusao(Calendar dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    public Boolean isExcluido() {
        return NullUtils.isNull(isExcluido);
    }

    public void setExcluido(Boolean excluido) {
        isExcluido = excluido;
    }

    public Calendar getDataCadastro() {
        return dataCadastro;
    }

    public Long getAlgorixId() {
        return algorixId;
    }

    public void setDataCadastro(Calendar dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Calendar getDataAlteracao() {
        return dataAlteracao;
    }

    public void setAlgorixId(Long algorixId) {
        this.algorixId = algorixId;
    }

    public void setDataAlteracao(Calendar dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public UsuarioBean getUsuarioExcluiu() {
        return usuarioExcluiu;
    }

    public void setUsuarioExcluiu(UsuarioBean usuarioExcluiu) {
        this.usuarioExcluiu = usuarioExcluiu;
    }

    public UsuarioBean getUsuarioAlteracao() {
        return usuarioAlteracao;
    }

    public void setUsuarioAlteracao(UsuarioBean usuarioAlteracao) {
        this.usuarioAlteracao = usuarioAlteracao;
    }

    public int hashCode() {
        boolean prime = true;
        int result = 1;
        result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            GenericBean other = (GenericBean)obj;
            if (this.id == null) {
                return other.id == null;
            } else {
                return this.id.equals(other.id);
            }
        }
    }

    public JSONObject jsonAudit() {
        JSONObject obj = new JSONObject();
        obj.put("generic", "TODO implemntar na classe filha");
        return obj;
    }
}
