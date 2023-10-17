package br.com.crista.fashion.bean;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
