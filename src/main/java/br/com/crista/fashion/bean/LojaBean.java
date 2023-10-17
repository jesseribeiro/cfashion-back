package br.com.crista.fashion.bean;

import javax.persistence.Column;
import javax.persistence.Entity;

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
@Entity( name = "Loja")
public class LojaBean extends GenericBean {

    private String nomeFantasia;
    private String email;
    private String whatsapp;

    @Column(name = "pessoa_contato")
    private String pessoaContato;

    @Column(name = "telefone")
    private String telefone;
}
