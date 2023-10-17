package br.com.crista.fashion.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoDTO {

    private String logradouro;
    private String numero;
    private String bairro;
    private String cep;
    private String cidade;
    private String uf;
}
