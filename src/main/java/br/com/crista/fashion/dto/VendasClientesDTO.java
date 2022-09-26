package br.com.crista.fashion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class VendasClientesDTO {
    private BigInteger clienteId;
    private BigInteger qtdVendas;

}
