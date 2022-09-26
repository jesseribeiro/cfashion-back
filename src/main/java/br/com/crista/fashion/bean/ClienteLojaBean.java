package br.com.crista.fashion.bean;

import br.com.crista.fashion.enumeration.EnumSegmentacao;
import br.com.crista.fashion.enumeration.EnumStatusCliente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Calendar;

@Audited
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "ClienteLoja")
public class ClienteLojaBean extends GenericBean {

    @JsonIgnore
    @JoinColumn(name = "loja_id")
    @ManyToOne
    private LojaBean loja;

    @JsonIgnore
    @JoinColumn(name = "cliente_id")
    @ManyToOne
    private ClienteBean cliente;

    @Enumerated(EnumType.STRING)
    private EnumStatusCliente status;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataStatus;

    @Enumerated(value = EnumType.STRING)
    private EnumSegmentacao segmentacaoCliente;

    @JoinColumn(name = "segmento_atualizado")
    private Boolean segmentoAtualizado;
}
