package br.com.crista.fashion.enumeration;

import br.com.crista.fashion.dto.LabelDescricaoDTO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum EnumStatusRenegociacao {
    EM_ABERTO("Em Aberto"),
    PAGO("Pago"),
    CANCELADA("Cancelada"),
    RENEGOCIADO("Renegociado");

    private String label;

    EnumStatusRenegociacao(String label){
        this.label = label;
    }

    public static List<LabelDescricaoDTO> getLabels(){
        return Arrays.stream(values()).map(status -> new LabelDescricaoDTO(status.name(), status.getLabel())).collect(Collectors.toList());
    }
}
