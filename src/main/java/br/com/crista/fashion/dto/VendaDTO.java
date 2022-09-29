package br.com.crista.fashion.dto;

import br.com.crista.fashion.bean.VendaBean;
import br.com.crista.fashion.json.MoneyDeserializerJson;
import br.com.crista.fashion.utils.StringUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendaDTO extends GenericDTO<VendaBean> {

    private String dataInicial;
    private String dataFinal;

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal vlParcela;
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal vlProduto;
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal frete;
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal descontos;
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal comissao;

    private Calendar dataVenda;
    private String cpf;
    private Long clienteId;
    private Long marcaId;
    private String marca;
    private String nomeProduto;
    private Long produtoId;
    private String status;
    private String tipo;
    private BigDecimal valorParcela;
    private Integer qtdParcela;
    private List<ParcelaDTO> parcelas;

    public VendaDTO(VendaBean bean) {
        super(bean);
        clienteId = bean.getCliente().getId();
        cpf = StringUtils.inserirMascaraCpfCnpj(bean.getCliente().getCpf());
        marcaId = bean.getLoja().getId();
        marca = bean.getLoja().getNomeFantasia();
        nomeProduto = bean.getProduto().getNome();
        produtoId = bean.getProduto().getId();
        dataVenda = bean.getDataVenda();
        qtdParcela = bean.getQtdParcela();
        vlProduto = bean.getValorProduto();
        frete = bean.getFrete();
        descontos = bean.getDescontos();
        comissao = bean.getComissao();

        if (bean.getTipo() != null) {
            tipo = bean.getTipo().getLabel();
        }

        if (bean.getStatus() != null) {
            status = bean.getStatus().getLabel();
        }
    }
}
