package br.com.crista.fashion.bean;

import br.com.crista.fashion.enumeration.EnumMovimentacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

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
}
