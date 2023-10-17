package br.com.crista.fashion.dto;

import java.util.Calendar;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.crista.fashion.enumeration.EnumTipoRelatorio;
import br.com.crista.fashion.json.CalendarDeserializerJson;

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
public class FiltroRelatorioDTO {

    Long marcaId;
    Long lojaId;

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
