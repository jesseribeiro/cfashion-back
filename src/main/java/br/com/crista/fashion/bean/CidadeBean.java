package br.com.crista.fashion.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity (name = "Cidade")
public class CidadeBean extends GenericBean {

    private String nome;

    private Integer ibge;

    @JoinColumn(name = "uf", nullable = false)
    @ManyToOne
    private EstadoBean estado;

    private String nomeSemAcento;

    private String latLon;
}
