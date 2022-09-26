package br.com.crista.fashion.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class EnderecoBean {

    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String estado;
    private Integer cidadeIbge;
    private String cidadeNome;
}
