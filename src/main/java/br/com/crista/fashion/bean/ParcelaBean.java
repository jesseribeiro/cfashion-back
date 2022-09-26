package br.com.crista.fashion.bean;

import br.com.crista.fashion.enumeration.EnumStatusParcela;
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
@Entity(name = "Parcela")
public class ParcelaBean extends GenericBean {
    private BigDecimal valor;

    /*
        Esse é o valor que a Pratico irá repassar para o lojista, ou seja, o valor sem juros e taxas ...
        Ex.:
        loja vende um produto de R$ 300,00 em 6x, com uma taxa de 10 + 5% juros ao mês
        o valor da parcela será R$ 61,50 +- fiz de cabeça, enfim o valor com juros ... é da Prático,
        o valor repasse é q o lojista irá receber nos repasses parcelados ou adiantado
        o valor repasse será: R$ 50,00 por parcela
     */
    private BigDecimal valorRepasse;

    // A entrada vai ser registrada como parcela ZERO para ser associada a pagamento, nesse caso já entra paga
    private Integer numero;

    @Column(name = "data_vencimento")
    @Temporal(TemporalType.DATE)
    private Calendar dataVencimento;

    @Enumerated(EnumType.STRING)
    private EnumStatusParcela status;

    @JsonIgnore
    @JoinColumn(name = "carne_id")
    @ManyToOne
    private CarneBean carne;

    private BigDecimal vlTarifa;

    private BigDecimal vlJuros;

    @Column(name = "vl_parcela_sem_juros")
    private BigDecimal vlParcelaSemTaxaJuros;

    @OneToOne(mappedBy = "parcela", fetch = FetchType.LAZY)
    private PagamentoBean pagamento;

    @Column(name = "data_para_repasse")
    @Temporal(TemporalType.DATE)
    private Calendar dataParaRepasse;

    private Long acordoIdAlgorix;

    private Long sequencialCarneAlgorix;
}
