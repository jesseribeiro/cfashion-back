package br.com.crista.fashion.dto;

import br.com.crista.fashion.enumeration.EnumTipoRelatorio;
import br.com.crista.fashion.json.CalendarDeserializerJson;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FiltroRelatorioDTO {

    Long marcaId;

    @JsonDeserialize(converter = CalendarDeserializerJson.class)
    Calendar dataInicio;
    @JsonDeserialize(converter = CalendarDeserializerJson.class)
    Calendar dataFim;

    EnumTipoRelatorio tipoRel;
    String categoria;
    String tipo;
    String status;
    Boolean temEstoque;

    String nomeCliente;
    String cpf;

    Boolean ordenarClientes;
}
