package br.com.crista.fashion.dto;

import br.com.crista.fashion.bean.VendaBean;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendaDTO extends GenericDTO<VendaBean> {

    private Long carneId;
    public VendaDTO(VendaBean vendaBean, Long carneId) {
        super(vendaBean);
        this.carneId = carneId;
    }
}
