package br.com.crista.fashion.enumeration;

import br.com.crista.fashion.dto.LabelDescricaoDTO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum TipoContaBanco {
    CONTA_CORRENTE("Conta Corrente", "C/C"),
    POUPANCA("Poupança", "C/I");

    private String label;
    private String sigla;
    TipoContaBanco(String label, String sigla){
        this.label = label;
        this.sigla = sigla;
    }

    public static List<LabelDescricaoDTO> getLabels(){
        return Arrays.stream(values()).map(tipoContaBanco -> new LabelDescricaoDTO(tipoContaBanco.name(), tipoContaBanco.getLabel())).collect(Collectors.toList());
    }
}
