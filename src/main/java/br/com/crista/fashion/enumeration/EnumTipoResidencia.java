package br.com.crista.fashion.enumeration;

import br.com.crista.fashion.dto.LabelDescricaoDTO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum EnumTipoResidencia {
    A("Alugada"),
    F("Financiada"),
    M("Com os Pais"),
    N("Com Familiares"),
    P("Própria"),
    X("Outra");

    private String label;

    EnumTipoResidencia(String label){
        this.label = label;
    }

    public static List<LabelDescricaoDTO> getLabels(){
        return Arrays.stream(values()).map(tipoResidencia -> new LabelDescricaoDTO(tipoResidencia.name(), tipoResidencia.getLabel())).collect(Collectors.toList());
    }

    public static String valueToString(String value) {
        if(value != null) {
            return valueOf(value).getLabel();
        }
        return null;
    }
}
