package br.com.crista.fashion.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.crista.fashion.dto.LabelDescricaoDTO;

public enum EnumRole {

    ADMIN("Admin"),
    SUPERVISOR("Supervisor"),
    COMERCIAL("Comercial"),
    NEGOCIADOR("Negociador"),
    CREDIARISTA("Crediarista");

    private final String label;

    EnumRole(String label) {

        this.label = label;
    }

    public static List<LabelDescricaoDTO> getLabels() {

        return Arrays.stream(values())
                .map(tipo -> new LabelDescricaoDTO(tipo.name(), tipo.getLabel()))
                .collect(Collectors.toList());
    }

    public String getLabel() {

        return label;
    }
}
