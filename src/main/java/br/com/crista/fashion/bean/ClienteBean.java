package br.com.crista.fashion.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Cliente")
@Table(name = "cliente", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "cpf"
        })
})
public class ClienteBean extends GenericBean {

    private String nome;
    private String cpf;
    private String sexo;
    private String celular;
    private String email;

    @Column(name = "data_nascimento")
    @Temporal(TemporalType.DATE)
    private Calendar dataNascimento;

    @Embedded
    private EnderecoBean endereco;

    @Column(name = "data_primeira_compra")
    @Temporal(TemporalType.DATE)
    private Calendar dataPrimeiraCompra;

    @Column(name = "data_ultima_compra")
    @Temporal(TemporalType.DATE)
    private Calendar dataUltimaCompra;
}
