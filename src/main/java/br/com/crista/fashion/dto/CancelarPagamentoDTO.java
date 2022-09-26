package br.com.crista.fashion.dto;

import br.com.crista.fashion.bean.PagamentoBean;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CancelarPagamentoDTO extends GenericDTO<PagamentoBean> {

    private String motivo;
    private Long usuarioId;
    private Long lojaId;

    public CancelarPagamentoDTO(PagamentoBean bean) {
        super(bean);
    }
}
