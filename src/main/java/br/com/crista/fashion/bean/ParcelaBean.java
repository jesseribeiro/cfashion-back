package br.com.crista.fashion.bean;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.NotAudited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.crista.fashion.enumeration.EnumStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
