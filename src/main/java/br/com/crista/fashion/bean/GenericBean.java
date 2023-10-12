package br.com.crista.fashion.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.Calendar;

import static java.util.Objects.isNull;

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
        return isNull(isExcluido);
    }

    public void setExcluido(Boolean excluido) {
        isExcluido = excluido;
    }

    public Calendar getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Calendar dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Calendar getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Calendar dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public int hashCode() {

        boolean prime = true;
        int result = 1;
        result = 31 * result + (isNull(this.id) ? 0 : this.id.hashCode());
        return result;
    }

    public boolean equals(Object obj) {

        if (this == obj) {

            return true;

        } else if (isNull(obj)) {

            return false;

        } else if (this.getClass() != obj.getClass()) {

            return false;

        } else {

            GenericBean other = (GenericBean)obj;

            if (isNull(this.id)) {

                return isNull(other.id);

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
