package br.com.crista.fashion.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.crista.fashion.dto.LabelDescricaoDTO;

import lombok.Getter;

@Getter
public enum EnumTipoPagamento {

    CARTAO_CREDITO("Cartão de Crédito"),
    PIX("Pix"),
    BOLETO("Boleto"),
    MAGALU("Magalu"),
    AMERICANAS("Americanas"),
    MERCADO_LIVRE("Mercado Livre");

    private final String label;

    EnumTipoPagamento(String label) {

        this.label = label;
    }

    public static List<LabelDescricaoDTO> getLabels() {

        return Arrays.stream(values())
                .map(tipo -> new LabelDescricaoDTO(tipo.name(), tipo.getLabel()))
                .collect(Collectors.toList());
    }
}
