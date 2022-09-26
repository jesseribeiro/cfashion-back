package br.com.crista.fashion.bean;

import br.com.crista.fashion.enumeration.EnumStatusVenda;
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

    @Column(name = "data_venda")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataVenda;

    // esse é a data utilizada para calcular as datas de repasse das parcelas
    @Column(name = "data_corte")
    @Temporal(TemporalType.DATE)
    private Calendar dataCorte;

    @JsonIgnore
    @JoinColumn(name = "cliente_id")
    @ManyToOne
    private ClienteBean cliente;

    @JsonIgnore
    @JoinColumn(name = "loja_id")
    @ManyToOne
    private LojaBean loja;

    @JsonIgnore
    @JoinColumn(name = "vendedor_id")
    @ManyToOne
    private UsuarioBean vendedor;

    @JsonIgnore
    @NotAudited
    @JoinColumn(name = "produto_id")
    @ManyToOne
    private ProdutoBean produto;

    @JsonIgnore
    @JoinColumn(name = "limite_exclusivo_id")
    @ManyToOne
    private LimiteExclusivoBean limiteExclusivo;

    @Enumerated(EnumType.STRING)
    private EnumStatusVenda status;

    private Long sequencialCarneAlgorix;
}
