package br.com.crista.fashion.bean;

import br.com.crista.fashion.enumeration.EnumStatus;
import br.com.crista.fashion.enumeration.EnumTipoPagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@Audited
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
