package br.com.crista.fashion.bean;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.NotAudited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.crista.fashion.enumeration.EnumStatus;
import br.com.crista.fashion.enumeration.EnumTipoPagamento;

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
@Entity( name = "Venda")
public class VendaBean extends GenericBean {

    private BigDecimal valorProduto;
    private BigDecimal valorTotal;
    private BigDecimal valorTarifa;
    private Integer qtdParcela;
    private BigDecimal freteReceber;
    private BigDecimal fretePagar;
    private BigDecimal descontos;
    private BigDecimal comissao;

    @Column(name = "data_venda")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataVenda;

    @JsonIgnore
    @JoinColumn(name = "cliente_id")
    @ManyToOne
    private ClienteBean cliente;

    @JsonIgnore
    @JoinColumn(name = "loja_id")
    @ManyToOne
    private LojaBean loja;

    @JsonIgnore
    @NotAudited
    @JoinColumn(name = "produto_id")
    @ManyToOne
    private ProdutoBean produto;

    @Enumerated(EnumType.STRING)
    private EnumTipoPagamento tipo;

    @Enumerated(EnumType.STRING)
    private EnumStatus status;

    @JsonIgnore
    @NotAudited
    @OneToMany(mappedBy = "venda", fetch = FetchType.LAZY)
    private List<ParcelaBean> parcelas;
}
