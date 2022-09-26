package br.com.crista.fashion.dto;

import br.com.crista.fashion.enumeration.EnumStatusCliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NovoStatusClienteDTO {
    private EnumStatusCliente novoStatus;

}
