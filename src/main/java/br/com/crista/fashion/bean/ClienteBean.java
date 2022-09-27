package br.com.crista.fashion.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Calendar;

@Audited
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
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
