package br.com.crista.fashion.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ParcelaCompraDTO {

    /**
     * Separei em vlPrimeiraParcela por causa do arredondamento das outras parcelas,
     * no caso o vlPrimeiraParcela irá receber todos os centavos das parcelas,
     * o vlPrimeiraParcela não é a mesma coisa q a vlEntrada nao tem relação.
     */
    private BigDecimal vlPrimeiraParcela;
    private BigDecimal vlDemaisParcela;
    private BigDecimal vlEntrada;
    private BigDecimal vlParcelaSemTaxaJuros; // esse é o valor que deverá ser repassado ao lojista, sem taxas e juros limpo
    private Integer qtdParcela;

    private BigDecimal jurosFinanc;
    private BigDecimal tarifa;

    // dados do plano pagamento q foi utilizado para esse calculo
    private Long planoPagamentoId;

    public BigDecimal getValorTotal() {
        if(qtdParcela == 1) {
            return vlPrimeiraParcela.add(vlEntrada);
        } else {
            return vlDemaisParcela.multiply(new BigDecimal(qtdParcela - 1)).add(vlEntrada).add(vlPrimeiraParcela);
        }
    }
}
