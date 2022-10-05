package br.com.crista.fashion.bean;

import br.com.crista.fashion.enumeration.EnumStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

@Audited
@Getter
@Setter
@Entity(name = "Parcela")
public class ParcelaBean extends GenericBean {
    private BigDecimal valorParcela;
    private Integer numero;

    @Column(name = "data_vencimento")
    @Temporal(TemporalType.DATE)
    private Calendar dataVencimento;

    @JsonIgnore
    @JoinColumn(name = "venda_id")
    @ManyToOne
    private VendaBean venda;

    @Column(name = "data_pagamento")
    @Temporal(TemporalType.DATE)
    private Calendar dataPagto;

    @JsonIgnore
    @JoinColumn(name = "loja_id")
    @ManyToOne
    private LojaBean loja;

    @JsonIgnore
    @JoinColumn(name = "cliente_id")
    @ManyToOne
    private ClienteBean cliente;

    @Enumerated(EnumType.STRING)
    private EnumStatus status;

    @JsonIgnore
    @NotAudited
    @JoinColumn(name = "produto_id")
    @ManyToOne
    private ProdutoBean produto;
}
