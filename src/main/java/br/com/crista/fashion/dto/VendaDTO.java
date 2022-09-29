package br.com.crista.fashion.dto;

import br.com.crista.fashion.bean.VendaBean;
import br.com.crista.fashion.json.MoneyDeserializerJson;
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

    // filtros
    private String dataInicial;
    private String dataFinal;

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

    private Calendar dataVenda;

    private List<ParcelaClienteDTO> parcelas;
    private String cpf;
    private Long clienteId;
    private Long marcaId;
    private String marca;
    private String nomeProduto;
    private Long produtoId;
    private String status;
    private String tipo;
    private BigDecimal valorParcela;

    public VendaDTO(VendaBean bean) {
        super(bean);
        clienteId = bean.getCliente().getId();
        marcaId = bean.getLoja().getId();
        marca = bean.getLoja().getNomeFantasia();
        dataVenda = bean.getDataVenda();

        qtdParcela = bean.getQtdParcela();
        vlProduto = bean.getValorProduto();
        nomeProduto = bean.getProduto().getNome();
        produtoId = bean.getProduto().getId();
        frete = bean.getFrete();
        descontos = bean.getDescontos();
        comissao = bean.getComissao();
        cpf = bean.getCliente().getCpf();

        if (bean.getStatus() != null) {
            tipo = bean.getTipo().getLabel();
        }

        if (bean.getStatus() != null) {
            status = bean.getStatus().getLabel();
        }
    }
}
