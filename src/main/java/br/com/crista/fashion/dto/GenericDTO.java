package br.com.crista.fashion.dto;

import static java.util.Objects.isNull;

import br.com.crista.fashion.bean.GenericBean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
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

        if (isNull(bean.getId())) {

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
