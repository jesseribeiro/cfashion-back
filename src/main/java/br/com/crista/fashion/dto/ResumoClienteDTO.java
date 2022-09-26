package br.com.crista.fashion.dto;


import lombok.*;

import java.math.BigDecimal;
import java.util.Calendar;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResumoClienteDTO {

    private Long clienteId;
    private Long lojaId;
    private String nomeRede;
    private String nomeEmpresa;
    private String nomeLoja;
    private Calendar dataAssinatura;
    private Calendar ultimaCompraEm;
    private int qtdTotalCompras;
    private BigDecimal vlTotalCompras;
    private BigDecimal saldoDevedorTotal;
    private BigDecimal saldoDevedorMes;
    private BigDecimal limiteExclusivo;
    private BigDecimal limiteCompartilhadoDisponivel;
    private BigDecimal limiteExclusivoDisponivel;
    private Calendar ultimoPagamentoEm;
    private int qtdTotalPagamentos;
    private BigDecimal vlTotalPagamentos;
    private Calendar dataStatus;
    private String status;

    private int atrasoDias;
    private Calendar atrasoVencto;
    private int maiorAtrasoAteHojeDias;
    private Calendar maiorAtrasoAteHojeVencto;
    private int maiorAtrasoPrim12mesesDias;
    private Calendar maiorAtrasoPrim12mesesVencto;

    private String creditScore;


}
