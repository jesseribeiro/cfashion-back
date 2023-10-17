package br.com.crista.fashion.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.crista.fashion.dto.LabelDescricaoDTO;

import lombok.Getter;

@Getter
public enum EnumTamanho {

    EP("EP"),
    PP("PP"),
    P("P"),
    M("M"),
    G("G"),
    GG("GG"),
    G1("G1"),
    G2("G2"),
    G3("G3"),
    _36("36"),
    _38("38"),
    _40("40"),
    _42("42"),
    _44("44"),
    _46("46"),
    _48("48"),
    _50("50"),
    ML40("40ML"),
    ML60("60ML"),
    ML80("80ML"),
    ML100("100ML");

    private final String label;

    EnumTamanho(String label) {

        this.label = label;
    }

    public static List<LabelDescricaoDTO> getLabels() {

        return Arrays.stream(values())
                .map(tamanho -> new LabelDescricaoDTO(tamanho.name(), tamanho.getLabel()))
                .collect(Collectors.toList());
    }
}
