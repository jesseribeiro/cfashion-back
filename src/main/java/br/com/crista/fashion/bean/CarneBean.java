package br.com.crista.fashion.bean;

import br.com.crista.fashion.enumeration.EnumStatusCarne;
import br.com.crista.fashion.enumeration.TipoCarne;
import br.com.crista.fashion.enumeration.TipoFormaPagamento;
import br.com.crista.fashion.utils.NullUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

// https://pt.stackoverflow.com/questions/234755/diferen%C3%A7as-onetomany-manytomany-manytoone-onetoone
@Audited
@Getter
@Setter
@Entity(name = "Carne")
public class CarneBean extends GenericBean {

    @Column(name = "data_compra")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataCompra;

    private Integer qtdParcela;

    private BigDecimal valorTotal;

    private BigDecimal valorEntrada;

    @Enumerated(EnumType.STRING)
    private EnumStatusCarne status;

    @JsonIgnore
    @JoinColumn(name = "venda_id")
    @OneToOne
    private VendaBean venda;

    @JsonIgnore
    @NotAudited
    @OneToMany(mappedBy = "carne", fetch = FetchType.LAZY)
    private List<ParcelaBean> parcelas;

    @Enumerated(EnumType.STRING)
    private TipoFormaPagamento formaPagamento;

    @Enumerated(EnumType.STRING)
    private TipoCarne tipoCarne;

    @Column(name = "boletos_gerado")
    private Boolean boletosGerado;

    private BigDecimal valorJurosParcelaImport;
    private BigDecimal valorTarifaParcelaImport;
    private BigDecimal valorAtualizacaoParcelaImport;

    private Long acordoIdAlgorix;

    private Long sequencialCarneAlgorix;

    public boolean getBoletosGerado() {
        return NullUtils.isNull(boletosGerado);
    }
}
