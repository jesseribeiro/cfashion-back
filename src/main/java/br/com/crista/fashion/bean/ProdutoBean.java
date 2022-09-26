package br.com.crista.fashion.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity(name = "Produto")
public class ProdutoBean extends GenericBean {

    private String nome;
    private String descricao;
}
