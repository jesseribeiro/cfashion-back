package br.com.crista.fashion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ComprasDTO {
    private String dataVenda;
    private String nomeCliente;
    private String cpf;
    private BigDecimal valorProduto;
    private BigDecimal jurosCompra;
    private BigDecimal valorEntrada;
    private Integer qtParcela;
    private BigDecimal valorParcela;
    private String nomeRede;
    private String nomeLoja;

    private BigDecimal qtdVendas;
}
