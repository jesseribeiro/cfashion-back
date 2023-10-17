package br.com.crista.fashion.bean;

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
@Entity (name = "Estado")
public class EstadoBean extends GenericBean {

    private String nome;

    private String uf;
}
