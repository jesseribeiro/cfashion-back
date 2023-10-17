package br.com.crista.fashion.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.crista.fashion.dto.LabelDescricaoDTO;

import lombok.Getter;

@Getter
public enum EnumCategoria {

    BLUSA("Blusa"),
    CALCA("Calça"),
    CAMISA("Camisa"),
    CASACO("Casaco"),
    CONJUNTO("Conjunto"),
    JEANS("Jeans"),
    PLUS_SIZE("Plus size"),
    SAIA("Saia"),
    VESTIDO("Vestido"),
    BOLSA("Bolsa"),
    PERFUME("Perfume");

    private final String label;

    EnumCategoria(String label) {

        this.label = label;
    }

    public static List<LabelDescricaoDTO> getLabels() {

        return Arrays.stream(values())
                .map(status -> new LabelDescricaoDTO(status.name(), status.getLabel()))
                .collect(Collectors.toList());
    }
}
