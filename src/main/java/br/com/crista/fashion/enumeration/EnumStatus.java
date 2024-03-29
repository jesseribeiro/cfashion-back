package br.com.crista.fashion.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.crista.fashion.dto.LabelDescricaoDTO;

import lombok.Getter;

@Getter
public enum EnumStatus {

    PAGA("Pago"),
    NAO_PAGA("Em Aberto"),
    CANCELADA("Cancelado");

    private final String label;

    EnumStatus(String label) {

        this.label = label;
    }

    public static List<LabelDescricaoDTO> getLabels() {

        return Arrays.stream(values())
                .map(status -> new LabelDescricaoDTO(status.name(), status.getLabel()))
                .collect(Collectors.toList());
    }
}
