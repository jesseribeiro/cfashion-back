package br.com.crista.fashion.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class VendaNaMaoDTO {

    private BigDecimal vlParcela;
    private BigDecimal vlEntrada;
    private BigDecimal vlProduto; // esse é o valor que deverá ser repassado ao lojista, sem taxas e juros limpo
    private BigDecimal vlParcelaSemTaxaJuros;
    private Integer qtdParcela;

    private Long planoPagamentoId;
    private Long clienteId;
    private Long lojaId;
    private String nomeProduto;
    private String dataVenda;
    private String dataVencimento;

    public BigDecimal getValorTotal() {
        if(qtdParcela == 1) {
            return vlParcela.add(vlEntrada);
        } else {
            return vlParcela.multiply(new BigDecimal(qtdParcela - 1)).add(vlEntrada).add(vlParcela);
        }
    }
}
