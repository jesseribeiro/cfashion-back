package br.com.crista.fashion.dto;

import java.math.BigDecimal;

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
public class ComprasDTO {

    private String dataVenda;
    private String vencimento;
    private String tipo;
    private BigDecimal valor;
    private BigDecimal comissao;
    private BigDecimal tarifa;
    private String status;
    private String categoria;
    private String nomeCliente;
    private String nomeLoja;
    private String cpf;
    private String cidade;
    private Long lojaId;
    private Integer numero;

}
