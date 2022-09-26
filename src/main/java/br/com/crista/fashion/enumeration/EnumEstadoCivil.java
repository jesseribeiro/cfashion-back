package br.com.crista.fashion.enumeration;

import br.com.crista.fashion.dto.LabelDescricaoDTO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum EnumEstadoCivil {
    C("Casado"),
    D("Divorciado"),
    S("Solteiro"),
    U("União Estável"),
    V("Viúvo"),
    X("Outro");

    private String label;
    EnumEstadoCivil(String label){
        this.label = label;
    }

    public static List<LabelDescricaoDTO> getLabels(){
        return Arrays.stream(values()).map(estadoCivil -> new LabelDescricaoDTO(estadoCivil.name(), estadoCivil.getLabel())).collect(Collectors.toList());
    }

    public static String valueToString(String value) {
        if(value != null) {
            return valueOf(value).getLabel();
        }
        return null;
    }

}
