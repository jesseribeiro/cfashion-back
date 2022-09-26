package br.com.crista.fashion.dto;

import br.com.crista.fashion.json.MoneyDeserializerJson;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NovoLimiteClienteDTO {
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal novoLimite;

    private Boolean flagIsVenda;
}
