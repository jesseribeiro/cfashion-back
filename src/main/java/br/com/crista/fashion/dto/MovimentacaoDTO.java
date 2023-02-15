package br.com.crista.fashion.dto;

import br.com.crista.fashion.bean.MovimentacaoBean;
import br.com.crista.fashion.json.MoneyDeserializerJson;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovimentacaoDTO extends GenericDTO<MovimentacaoBean> {

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal valor;

    private String tipo;
    private String descricao;

    public MovimentacaoDTO(MovimentacaoBean bean) {
        super(bean);
        valor = bean.getValor();
        descricao = bean.getDescricao();

        if (bean.getTipo() != null) {
            tipo = bean.getTipo().getLabel();
        }
    }
}
