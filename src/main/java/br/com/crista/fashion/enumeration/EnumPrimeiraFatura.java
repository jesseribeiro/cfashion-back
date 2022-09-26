package br.com.crista.fashion.enumeration;

import br.com.crista.fashion.dto.LabelDescricaoDTO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum EnumPrimeiraFatura {
    PAGA("Adimplente"),
    NAO_PAGA("Inadimplente");

    private String descricao;

    EnumPrimeiraFatura(String descricao) {
        this.descricao = descricao;
    }

    public static List<LabelDescricaoDTO> getDescricoes(){
        return Arrays.stream(values()).map(item -> new LabelDescricaoDTO(item.name(), item.getDescricao())).collect(Collectors.toList());
    }

    public static EnumPrimeiraFatura getValue(String value) {
        for (EnumPrimeiraFatura e : values()) {
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
        return NAO_PAGA;
    }
}
