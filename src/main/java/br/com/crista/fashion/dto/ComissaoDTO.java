package br.com.crista.fashion.dto;

import static java.util.Objects.nonNull;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.crista.fashion.bean.ComissaoBean;
import br.com.crista.fashion.json.MoneyDeserializerJson;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
