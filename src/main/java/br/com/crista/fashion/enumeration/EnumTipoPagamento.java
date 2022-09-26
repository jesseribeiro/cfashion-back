package br.com.crista.fashion.enumeration;

import br.com.crista.fashion.dto.LabelDescricaoDTO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum EnumTipoPagamento {
    ADMINISTRADORA("Administradora"),
    CARTAO_CREDITO("Cartão de Crédito"),
    TED("Ted"),
    PIX("Pix"),
    LOJA("Loja"),
    BANCO("Banco"),
    TODOS("Todos");

    private String label;


    EnumTipoPagamento(String label){
        this.label = label;
    }

    public static List<LabelDescricaoDTO> getLabels(){
        return Arrays.stream(values()).map(tipo -> new LabelDescricaoDTO(tipo.name(), tipo.getLabel())).collect(Collectors.toList());
    }
}
