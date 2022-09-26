package br.com.crista.fashion.enumeration;

import br.com.crista.fashion.dto.LabelDescricaoDTO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum EnumConcessaoCredito {
    CARTAO_BANDEIRA("1 Cartão Bandeira"),
    PERCENTUAL_RENDA("2 Percentual Renda"),
    CREDIT_SCORE("3 Credit Score"),
    GRUPO_RISCO("4 Grupo de Risco"), //Atualmente só é utilizado esse
    LIMITE_NEUROTECH("5 Limite Neurotech"),
    MANUAL("9 Manual");

    private String descricao;

    EnumConcessaoCredito(String descricao) {
        this.descricao = descricao;
    }

    public static List<LabelDescricaoDTO> getDescricoes(){
        return Arrays.stream(values()).map(concessaoCredito -> new LabelDescricaoDTO(concessaoCredito.name(), concessaoCredito.getDescricao())).collect(Collectors.toList());
    }

    public int getValue() {
        return ordinal() + 1;
    }
}
