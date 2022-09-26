package br.com.crista.fashion.enumeration;

import br.com.crista.fashion.dto.LabelDescricaoDTO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum EnumStatusParcela {
    PAGA("Pago"),
    NAO_PAGA("Em Aberto"),
    CANCELADA("Cancelado"),
    RENEGOCIADA("Renegociada");

    private String label;

    EnumStatusParcela(String label){
        this.label = label;
    }

    public static List<LabelDescricaoDTO> getLabels(){
        return Arrays.stream(values()).map(status -> new LabelDescricaoDTO(status.name(), status.getLabel())).collect(Collectors.toList());
    }

    public static EnumStatusParcela getValueOf(final String value) {
        if(value != null) {
            for(EnumStatusParcela e : EnumStatusParcela.values()) {
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

    public static EnumStatusParcela converStatusAlgorixToTrulogic(String statusAlgorix) {
        if("P".equals(statusAlgorix)) {
            return PAGA;
        }
        if("C".equals(statusAlgorix)) {
            return CANCELADA;
        }
        if("R".equals(statusAlgorix)) {
            return RENEGOCIADA;
        }
        if("A".equals(statusAlgorix)) {
            return NAO_PAGA;
        }
        return NAO_PAGA;
    }
}
