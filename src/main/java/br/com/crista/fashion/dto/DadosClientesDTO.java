package br.com.crista.fashion.dto;

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
public class DadosClientesDTO {

    private Integer estCivilNaoInformado;
    private Integer casado;
    private Integer divorciado;
    private Integer solteiro;
    private Integer uniaoEstavel;
    private Integer viuvo;
    private Integer outro;

    private Integer rendaNaoInformada;
    private Integer ate800;
    private Integer ate1500;
    private Integer ate2500;
    private Integer acima2500;

    private Integer ate25anos;
    private Integer ate35anos;
    private Integer ate45anos;
    private Integer mais45anos;

    private Integer sexoNaoInformado;
    private Integer feminino;
    private Integer masculino;
}
