package br.com.crista.fashion.dto;

import static java.util.Objects.nonNull;

import java.math.BigDecimal;
import java.util.Calendar;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.crista.fashion.bean.MovimentacaoBean;
import br.com.crista.fashion.json.MoneyDeserializerJson;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovimentacaoDTO extends GenericDTO<MovimentacaoBean> {

    private Calendar dataInicial;
    private Calendar dataFinal;

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal valor;

    private String tipo;
    private String descricao;
    private Calendar dataLancamento;

    private String data;

    public MovimentacaoDTO(MovimentacaoBean bean) {

        super(bean);
        valor = bean.getValor();
        descricao = bean.getDescricao();
        dataLancamento = bean.getDataLancamento();

        if (nonNull(bean.getTipo())) {

            tipo = bean.getTipo().getLabel();
        }
    }
}
