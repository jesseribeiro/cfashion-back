package br.com.crista.fashion.enumeration;

import br.com.crista.fashion.dto.LabelDescricaoDTO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum EnumEscolaridade {
    ANALFABETO("Analfabeto"),
    PRIMEIRO_GRAU_INCOMPLETO("1 Grau Incompleto"),
    PRIMEIRO_GRAU_COMPLETO("1 Grau Completo"),
    SEGUNDO_GRAU_INCOMPLETO("2 Grau Incompleto"),
    SEGUNDO_GRAU_COMPLETO("2 Grau Completo"),
    SUPERIOR_INCOMPLETO("Superior Incompleto"),
    SUPERIOR_COMPLETO("Superior Completo"),
    POS_GRADUACAO("Pós Graduação"),
    OUTROS("Outros");

    private String descricao;

    EnumEscolaridade(String descricao) {
        this.descricao = descricao;
    }

    public static List<LabelDescricaoDTO> getDescricoes(){
        return Arrays.stream(values()).map(escolaridade -> new LabelDescricaoDTO(escolaridade.name(), escolaridade.getDescricao())).collect(Collectors.toList());
    }

    public static EnumEscolaridade getValue(String value) {
        for (EnumEscolaridade e : values()) {
            if(e.name().equals(value)) {
                return e;
            }
            if(String.valueOf(e.ordinal()) == value) {
                return e;
            }
            if(e.getDescricao().equals(value)) {
                return e;
            }
        }
        return OUTROS;
    }
}
