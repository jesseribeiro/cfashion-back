package br.com.crista.fashion.bean;

import br.com.crista.fashion.enumeration.EnumCategoria;
import br.com.crista.fashion.enumeration.EnumTamanho;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Getter
@Setter
@Entity(name = "Produto")
public class ProdutoBean extends GenericBean {

    private String nome;
    private BigDecimal valorProduto;
    private BigDecimal valorCompra;

    @Enumerated(EnumType.STRING)
    private EnumTamanho tamanho;

    @Enumerated(EnumType.STRING)
    private EnumCategoria categoria;

    private String cor;
    private String codigo;
    private Integer qtd;
}
