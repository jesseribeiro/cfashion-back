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
    private String tipo;
    private BigDecimal valor;
    private String status;
    private String categoria;
    private String nomeCliente;
    private String cpf;
    private String cidade;

}
