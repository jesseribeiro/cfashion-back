package br.com.crista.fashion.dto;

import br.com.crista.fashion.bean.ComissaoBean;
import br.com.crista.fashion.json.MoneyDeserializerJson;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.math.BigDecimal;

import static java.util.Objects.nonNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComissaoDTO extends GenericDTO<ComissaoBean> {

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal comissao;

    private String tipo;

    public ComissaoDTO(ComissaoBean bean) {

        super(bean);
        comissao = bean.getComissao();

        if (nonNull(bean.getTipo())) {

            tipo = bean.getTipo().getLabel();
        }
    }
}
