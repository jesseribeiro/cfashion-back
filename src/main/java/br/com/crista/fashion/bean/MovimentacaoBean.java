package br.com.crista.fashion.bean;

import br.com.crista.fashion.enumeration.EnumMovimentacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

@Audited
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity( name = "Movimentacao")
public class MovimentacaoBean extends GenericBean {

    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private EnumMovimentacao tipo;

    private String descricao;

    @Column(name = "data_lancamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataLancamento;
}
