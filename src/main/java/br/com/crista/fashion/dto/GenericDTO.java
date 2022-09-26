package br.com.crista.fashion.dto;

import br.com.crista.fashion.bean.GenericBean;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GenericDTO<T extends GenericBean> {

    protected Long id;
    protected String mensagem;
    protected PermissaoDTO permissao;

    public GenericDTO() {
    }

    public GenericDTO(T bean) {
        this.id = bean.getId();
    }

    public GenericDTO(Long id) {
        this.id = id;
    }

    public T converter(T bean) {
        if (bean.getId() == null) {
            bean.setId(this.id);
        }

        return bean;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
