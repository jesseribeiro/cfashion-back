package br.com.crista.fashion.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.crista.fashion.dto.LabelDescricaoDTO;

import lombok.Getter;

@Getter
public enum EnumSexo {

    F("Feminino"),
    M("Masculino");

    private final String label;

    EnumSexo(String label) {

        this.label = label;
    }

    public static List<LabelDescricaoDTO> getLabels() {

        return Arrays.stream(values())
                .map(sexo -> new LabelDescricaoDTO(sexo.name(), sexo.getLabel()))
                .collect(Collectors.toList());
    }
}
