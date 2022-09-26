package br.com.crista.fashion.bean;

import br.com.crista.fashion.enumeration.EnumRecusaVenda;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;

@Audited
@Getter
@Setter
@Entity( name = "Autorizacao")
public class AutorizacaoBean extends GenericBean {

    private BigDecimal valorRecusado;
    private BigDecimal limiteCliente;

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
    @JoinColumn(name = "venda_id")
    @ManyToOne
    private VendaBean venda;

    @Enumerated(EnumType.STRING)
    private EnumRecusaVenda motivoRecusa;

}
