package br.com.crista.fashion.enumeration;

import br.com.crista.fashion.dto.LabelDescricaoDTO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum EnumPeriodoRelatorio {
    HOJE("Hoje"),
    ONTEM("Ontem"),
    SETE_DIAS("Últimos 7 dias"),
    TRINTA_DIAS("Últimos 30 dias"),
    SESSENTA_DIAS("Últimos 60 dias");

    private String label;

    EnumPeriodoRelatorio(String label){
        this.label = label;
    }

    public static List<LabelDescricaoDTO> getLabels(){
        return Arrays.stream(values()).map(tipo -> new LabelDescricaoDTO(tipo.name(), tipo.getLabel())).collect(Collectors.toList());
    }
}
