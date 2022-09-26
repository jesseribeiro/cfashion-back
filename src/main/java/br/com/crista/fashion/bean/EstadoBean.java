package br.com.crista.fashion.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity (name = "Estado")
public class EstadoBean extends GenericBean {

    private String nome;

    private String uf;
}
