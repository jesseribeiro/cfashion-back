package br.com.crista.fashion.enumeration;

import br.com.crista.fashion.dto.LabelDescricaoDTO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum EnumStatus {
    PAGA("Pago"),
    NAO_PAGA("Em Aberto"),
    CANCELADA("Cancelado");

    private String label;

    EnumStatus(String label){
        this.label = label;
    }

    public static List<LabelDescricaoDTO> getLabels(){
        return Arrays.stream(values()).map(status -> new LabelDescricaoDTO(status.name(), status.getLabel())).collect(Collectors.toList());
    }

    public static EnumStatus getValueOf(final String value) {
        if(value != null) {
            for(EnumStatus e : EnumStatus.values()) {
                if(e.getLabel().equals(value)) {
                    return e;
                }
                if(e.name().equals(value)) {
                    return e;
                }
            }
        }
        return null;
    }
}
