package br.com.crista.fashion.enumeration;

import br.com.crista.fashion.dto.LabelDescricaoDTO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum EnumTipoReferencia {

    A("Amigo"),
    C("Cônjuge"),
    F("Filho(a)"),
    I("Irmão"),
    M("Pai / Mãe"),
    P("Familiar"),
    X("Outros");

    private String label;

    EnumTipoReferencia(String label){
        this.label = label;
    }

    public static List<LabelDescricaoDTO> getLabels(){
        return Arrays.stream(values()).map(tipoReferencia -> new LabelDescricaoDTO(tipoReferencia.name(), tipoReferencia.getLabel())).collect(Collectors.toList());
    }

    public static String valueToString(String value) {
        if(value != null) {
            return valueOf(value).getLabel();
        }
        return null;
    }

    public static String nameToString(String value) {
        if(value != null) {
            for (EnumTipoReferencia e : EnumTipoReferencia.values()) {
                if (e.getLabel().equals(value)) {
                    return e.name();
                }
            }
        }
        return null;
    }
}
