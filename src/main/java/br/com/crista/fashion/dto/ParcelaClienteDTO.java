package br.com.crista.fashion.dto;


import br.com.crista.fashion.bean.PagamentoBean;
import br.com.crista.fashion.bean.ParcelaBean;
import br.com.crista.fashion.enumeration.EnumTipoPagamento;
import br.com.crista.fashion.json.MoneyDeserializerJson;
import br.com.crista.fashion.utils.DateUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Calendar;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParcelaClienteDTO extends GenericDTO<ParcelaBean> {

    // utilizado no front
    private boolean selected;
    private boolean podeCancelar;

    // filtros
    private Calendar dataInicial;
    private Calendar dataFinal;
    private Long lojaId;
    private Long empresaId;
    private Long redeId;
    private String status;
    private String lojaCompra;

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal valor;
    private Integer numero;
    private String dataVencimento;

    private Long pagamentoId;
    private String dataPagto;
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal valorPago;
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal valorMulta;
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal valorJurosMora;
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal desconto;
    private String localPagto;
    private String nomeCliente;

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal peMulta;
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal peJurosMora;

    private String motivoCancelamento;
    private EnumTipoPagamento tipoPagamento;
    private boolean flgCancelado;
    private long qtdDiasAtraso;

    public ParcelaClienteDTO(ParcelaBean parcela) {
        super(parcela);
        this.selected = false;
        this.podeCancelar = false;
        this.flgCancelado = false;
        this.id = parcela.getId();
        this.valor = parcela.getValor();
        this.numero = parcela.getNumero();
        this.dataVencimento = DateUtils.getDiaMesAnoPortugues(parcela.getDataVencimento());
        this.status = parcela.getStatus().getLabel();
        if(parcela.getCarne().getVenda() != null) {
            this.lojaId = parcela.getCarne().getVenda().getLoja().getId();
            this.lojaCompra = parcela.getCarne().getVenda().getLoja().getNomeLoja();
        }
        PagamentoBean pagamento = parcela.getPagamento();
        if(pagamento != null) {
            this.pagamentoId = pagamento.getId();
            this.dataPagto = DateUtils.getDiaMesAnoPortugues(pagamento.getDataPagto());
            this.valorPago = pagamento.getValorPago();
            this.valorMulta = pagamento.getMulta();
            this.valorJurosMora = pagamento.getJurosMora();
            this.desconto = pagamento.getDesconto();
            if(pagamento.getTipoPagamento() != null) {
                this.localPagto = pagamento.getTipoPagamento().getLabel();
            }
            if(pagamento.getBanco() != null) {
                this.localPagto = pagamento.getBanco().getLabel();
            }
            if(pagamento.getFlgCancelado() != null && pagamento.getFlgCancelado()) {
                this.motivoCancelamento = pagamento.getMotivoCancelamento();
                this.flgCancelado = true;
            } else {
                this.podeCancelar = DateUtils.equalsDate(pagamento.getDataPagto(), Calendar.getInstance());
            }
        }

        this.peMulta = BigDecimal.ZERO;
        this.peJurosMora = BigDecimal.ZERO;

        qtdDiasAtraso = DateUtils.diffDateInDays(parcela.getDataVencimento(), Calendar.getInstance());
        if(qtdDiasAtraso < 0) {
            qtdDiasAtraso = 0;
        }
    }
}
