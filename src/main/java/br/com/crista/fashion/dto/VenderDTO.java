package br.com.crista.fashion.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VenderDTO {

    private Long lojaId;
    private BigDecimal vlTarifaParcelaBanco;
    private BigDecimal vlTarifaParcelaLoja;
    private BigDecimal vlLimitePreAprovado;

    private String nomeProduto;
    private BigDecimal vlProduto;
    private BigDecimal vlEntrada;
    private Integer qtdParcela;
    private BigDecimal vlParcela;
    private String codigoAutorizacao;
    private Integer parcelarEm;

    // Vem da tabela CobrarCarne pegar só o id e a descricao
    private List<String> formasPagamento;
    private String lojistaEmail;
}
