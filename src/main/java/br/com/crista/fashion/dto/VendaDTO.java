package br.com.crista.fashion.dto;

import static java.util.Objects.nonNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.crista.fashion.bean.ParcelaBean;
import br.com.crista.fashion.bean.VendaBean;
import br.com.crista.fashion.json.MoneyDeserializerJson;
import br.com.crista.fashion.utils.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VendaDTO extends GenericDTO<VendaBean> {

    private Calendar dataInicial;
    private Calendar dataFinal;

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal vlTarifa;
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal vlParcela;
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal vlProduto;
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal vlTotal;
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal freteReceber;
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal fretePagar;
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

    private String data;
    private String categoria;

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
        vlTotal = bean.getValorTotal();
        vlProduto = bean.getValorProduto();
        vlTarifa = bean.getValorTarifa();
        freteReceber = bean.getFreteReceber();
        fretePagar = bean.getFretePagar();
        descontos = bean.getDescontos();
        comissao = bean.getComissao();

        if (nonNull(bean.getTipo())) {

            tipo = bean.getTipo().getLabel();
        }

        if (nonNull(bean.getStatus())) {

            status = bean.getStatus().getLabel();
        }

        this.parcelas = new ArrayList<>();

        for (ParcelaBean p : bean.getParcelas()) {

            this.parcelas.add(new ParcelaDTO(p));
        }
    }
}
