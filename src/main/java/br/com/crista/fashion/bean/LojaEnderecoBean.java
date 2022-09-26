package br.com.crista.fashion.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Audited
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "LojaEndereco")
public class LojaEnderecoBean extends GenericBean {

    @Embedded
    private EnderecoBean endereco;
}
