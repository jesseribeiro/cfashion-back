package br.com.crista.fashion.dto;

import br.com.crista.fashion.bean.VendaBean;
import br.com.crista.fashion.enumeration.EnumTipoPagamento;
import br.com.crista.fashion.json.MoneyDeserializerJson;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class VendaDTO extends GenericDTO<VendaBean> {

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal vlParcela;
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal vlProduto;
    private Integer qtdParcela;

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal frete;
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal descontos;
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal comissao;

    private List<ParcelaClienteDTO> parcelas;

    private Long clienteId;
    private Long lojaId;
    private String nomeProduto;
    private Long produtoId;
    private Boolean status;

    private EnumTipoPagamento tipo;


    private BigDecimal valorParcela;

}
