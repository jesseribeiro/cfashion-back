package br.com.crista.fashion.bean;

import br.com.crista.fashion.enumeration.EnumBanco;
import br.com.crista.fashion.enumeration.EnumTipoPagamento;
import br.com.crista.fashion.enumeration.TipoFormaPagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

@Audited
@Getter
@Setter
@Entity(name = "Pagamento")
public class PagamentoBean extends GenericBean {

    @Enumerated(EnumType.STRING)
    private TipoFormaPagamento tipo;

    @Column(name = "data_pagto", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataPagto;

    @Column(name = "valor_pago", nullable = false)
    private BigDecimal valorPago;

    private BigDecimal multa;

    private BigDecimal jurosMora;

    private BigDecimal desconto;

    @JsonIgnore
    @JoinColumn(name = "parcela_id", nullable = false)
    @OneToOne
    private ParcelaBean parcela;

    @JsonIgnore
    @JoinColumn(name = "loja_id")
    @ManyToOne
    private LojaBean loja;

    // caso o pagamento seja cancelado
    private String motivoCancelamento;

    @Column(name = "flg_cancelado", columnDefinition = "boolean DEFAULT false")
    private Boolean flgCancelado;

    @Column(name = "data_cancelamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataCancelamento;

    @JsonIgnore
    @JoinColumn(name = "usuario_cancelamento")
    @ManyToOne(fetch = FetchType.LAZY)
    private UsuarioBean usuarioCancelamento;

    @JsonIgnore
    @JoinColumn(name = "loja_cancelamento")
    @ManyToOne
    private LojaBean lojaCancelamento;

    @Column(name = "tipo_pagamento")
    @Enumerated(EnumType.STRING)
    private EnumTipoPagamento tipoPagamento;

    @Enumerated(value = EnumType.STRING)
    private EnumBanco banco;

    @Column(name = "diferença_dias_pagto")
    private Integer diferencaDias;
}
