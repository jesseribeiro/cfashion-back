package br.com.crista.fashion.dto;

import br.com.crista.fashion.json.MoneyDeserializerJson;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.math.BigDecimal;
import java.util.Calendar;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalcularVendaDTO {

    private Long clienteId;
    private Long marcaId;
    private Long produtoId;
    private String categoria;
    private String nomeProduto;
    private String codigo;
    private String tipo;
    private Integer qtdParcela;
    private Calendar dataVenda;

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal valorVenda;

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal valorProduto;

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal valorParcela;

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal comissao;

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal frete;

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal desconto;

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal valorTarifa;
}
