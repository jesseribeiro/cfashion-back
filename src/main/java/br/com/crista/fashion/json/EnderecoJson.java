package br.com.crista.fashion.json;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoJson {

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge;
}
