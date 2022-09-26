package br.com.crista.fashion.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
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
