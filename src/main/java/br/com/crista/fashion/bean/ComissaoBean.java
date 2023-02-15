package br.com.crista.fashion.bean;

import br.com.crista.fashion.enumeration.EnumTipoPagamento;
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
@Entity( name = "Comissao")
public class ComissaoBean extends GenericBean {

    private BigDecimal comissao;

    @Enumerated(EnumType.STRING)
    private EnumTipoPagamento tipo;
}
