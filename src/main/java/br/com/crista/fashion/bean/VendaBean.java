package br.com.crista.fashion.bean;

import br.com.crista.fashion.enumeration.EnumBanco;
import br.com.crista.fashion.enumeration.EnumTipoPagamento;
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
@Entity( name = "Venda")
public class VendaBean extends GenericBean {

    private BigDecimal valorProduto;
    private BigDecimal valorTotal;
    private Integer qtdParcela;
    private BigDecimal frete;
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

    @Enumerated(value = EnumType.STRING)
    private EnumBanco banco;

    @Column(name = "status", columnDefinition = "boolean DEFAULT false")
    private Boolean status;
}
