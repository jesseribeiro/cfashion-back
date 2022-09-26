package br.com.crista.fashion.enumeration;

import br.com.crista.fashion.dto.LabelDescricaoDTO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum EnumTipoRegra {
    FAIXA("Por faixa"),
    SEGMENTACAO("Por segmentacao");

    private String label;

    EnumTipoRegra(String label){
        this.label = label;
    }

    public static List<LabelDescricaoDTO> getLabels(){
        return Arrays.stream(values()).map(tipo -> new LabelDescricaoDTO(tipo.name(), tipo.getLabel())).collect(Collectors.toList());
    }

    public static EnumTipoRegra getValue(String value) {
        for (EnumTipoRegra e : values()) {
            if(e.name().equals(value)) {
                return e;
            }
            if(String.valueOf(e.ordinal()) == value) {
                return e;
            }
            if(e.getLabel().equals(value)) {
                return e;
            }
        }
        return SEGMENTACAO;
    }
}
