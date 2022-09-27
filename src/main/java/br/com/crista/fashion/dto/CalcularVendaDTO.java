package br.com.crista.fashion.dto;

import br.com.crista.fashion.json.MoneyDeserializerJson;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalcularVendaDTO {

    private String nomeProduto;
    private Long clienteId;
    private Long autorizacaoId;
    private Long lojaId;
    private Long planoPagamentoId;
    private String formaPagamento;

    private Integer diasPrimeiraParcela;

    private Boolean flagAutorizacao;

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal vlProduto;

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal vlEntrada;
}
