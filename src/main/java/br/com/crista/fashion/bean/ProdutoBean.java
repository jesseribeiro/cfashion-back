package br.com.crista.fashion.bean;

import br.com.crista.fashion.enumeration.EnumCategoria;
import br.com.crista.fashion.enumeration.EnumTamanho;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

@Getter
@Setter
@Entity(name = "Produto")
@Table(name = "produto", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "codigo"
        })
})
public class ProdutoBean extends GenericBean {

    private String nome;
    private BigDecimal valorCompra;

    @Enumerated(EnumType.STRING)
    private EnumTamanho tamanho;

    @Enumerated(EnumType.STRING)
    private EnumCategoria categoria;

    private String cor;
    private String codigo;
    private Integer qtd;

    private Calendar dataSaida;

    @JsonIgnore
    @NotAudited
    @JoinColumn(name = "marca_id")
    @ManyToOne
    private LojaBean marca;
}
