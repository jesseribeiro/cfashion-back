package br.com.crista.fashion.bean;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.envers.NotAudited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.crista.fashion.enumeration.EnumCategoria;
import br.com.crista.fashion.enumeration.EnumTamanho;

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
