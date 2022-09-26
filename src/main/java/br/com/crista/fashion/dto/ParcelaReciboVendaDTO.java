package br.com.crista.fashion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Calendar;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ParcelaReciboVendaDTO {

    private BigDecimal valor;
    private Integer numero;
    private Calendar dataVencimento;
}
