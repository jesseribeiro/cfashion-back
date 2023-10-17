package br.com.crista.fashion.bean;

import javax.persistence.Embeddable;

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
