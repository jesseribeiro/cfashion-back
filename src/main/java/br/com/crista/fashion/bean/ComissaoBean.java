package br.com.crista.fashion.bean;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

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
@Entity( name = "Comissao")
public class ComissaoBean extends GenericBean {

    private BigDecimal comissao;

    @Enumerated(EnumType.STRING)
    private EnumTipoPagamento tipo;
}
