package br.com.crista.fashion.dto;


import br.com.crista.fashion.bean.PagamentoBean;
import br.com.crista.fashion.enumeration.EnumTipoPagamento;
import br.com.crista.fashion.utils.Constants;
import br.com.crista.fashion.utils.DateUtils;
import br.com.crista.fashion.utils.StringUtils;
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
public class PagamentoDTO extends GenericDTO {

    // filtros
    private Calendar dataInicial;
    private Calendar dataFinal;
    private Long lojaId;
    private Long redeId;
    private Long localPagamentoId;
    private Long clienteId;
    private String clienteCpf;

    private Long id;
    private String dataPagto;
    private BigDecimal valorPago;
    private String tipoPagto;
    private String nomeLoja;
    private boolean flgCancelado;
    private String motivoCancelamento;
    private Integer numeroParcela;
    private Integer diferencaDias;
    private String formaPagamento;
    private String formaPagamentoDetalhe;

    private BigDecimal qtdPagamentos;

    public PagamentoDTO(PagamentoBean pagamento) {
        this.id = pagamento.getId();
        this.dataPagto = DateUtils.getDiaMesAnoPortugues(pagamento.getDataPagto());
        this.valorPago = pagamento.getValorPago();
        this.tipoPagto = pagamento.getParcela().getNumero() == Constants.PARCELA_ENTRADA ? "Entrada " : "";
        this.tipoPagto += pagamento.getTipo().getLabel();
        if(pagamento.getLoja() != null) {
            this.nomeLoja = pagamento.getLoja().getNomeFantasia();
        }
        this.flgCancelado = pagamento.getFlgCancelado() != null ? pagamento.getFlgCancelado() : false;
        this.motivoCancelamento = pagamento.getMotivoCancelamento();
        if(pagamento.getParcela() != null
                && pagamento.getParcela().getCarne() != null
                && pagamento.getParcela().getCarne().getVenda() != null
                && pagamento.getParcela().getCarne().getVenda().getCliente() != null) {
            this.clienteId = pagamento.getParcela().getCarne().getVenda().getCliente().getId();
            this.clienteCpf = StringUtils.inserirMascaraCpfCnpj(pagamento.getParcela().getCarne().getVenda().getCliente().getCpf());
            this.localPagamentoId = pagamento.getParcela().getCarne().getVenda().getLoja().getId();
            if(pagamento.getParcela().getCarne().getVenda().getLoja() != null) {
                this.lojaId = pagamento.getParcela().getCarne().getVenda().getLoja().getId();
            }
        }
        if (pagamento.getDiferencaDias() != null) {
            this.diferencaDias = pagamento.getDiferencaDias();
        }
        if (pagamento.getTipoPagamento() != null) {
            this.formaPagamento = pagamento.getTipoPagamento().getLabel();
            /*if (pagamento.getTipoPagamento() == EnumTipoPagamento.BANCO) {
                if(pagamento.getBanco() != null) {
                    this.formaPagamentoDetalhe = pagamento.getBanco().getLabel();
                }
            }*/
            if (pagamento.getTipoPagamento() == EnumTipoPagamento.LOJA) {
                if(pagamento.getLoja() != null) {
                    this.formaPagamentoDetalhe = pagamento.getLoja().getNomeFantasia();
                }
            }
        }
    }
}
