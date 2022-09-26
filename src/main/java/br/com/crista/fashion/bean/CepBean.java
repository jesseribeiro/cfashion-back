package br.com.crista.fashion.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@Setter
@Entity(name = "cep")
@Table(name = "cep", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "cep"
        })
})
public class CepBean extends GenericBean{

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge;
}
