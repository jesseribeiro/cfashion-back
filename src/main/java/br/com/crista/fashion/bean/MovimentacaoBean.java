package br.com.crista.fashion.bean;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.crista.fashion.enumeration.EnumMovimentacao;

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
