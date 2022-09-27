package br.com.crista.fashion.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;

@Audited
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
