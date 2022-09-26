package br.com.crista.fashion.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Audited
@Data
@Entity(name = "LimiteExclusivo")
public class LimiteExclusivoBean extends GenericBean {

    private BigDecimal limite;

    @JsonIgnore
    @JoinColumn(name = "cliente_id")
    @ManyToOne
    private ClienteBean cliente;

    @JsonIgnore
    @JoinColumn(name = "loja_id")
    @ManyToOne
    private LojaBean loja;
}
