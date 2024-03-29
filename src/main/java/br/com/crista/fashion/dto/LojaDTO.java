package br.com.crista.fashion.dto;

import static java.util.Objects.isNull;

import br.com.crista.fashion.bean.LojaBean;
import br.com.crista.fashion.utils.DateUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LojaDTO extends GenericDTO<LojaBean> {

    private String nomeFantasia;
    private String telefone;
    private String pessoaContato;
    private String email;
    private String whatsapp;
    private String dataCadastro;

    public LojaDTO(LojaBean bean) {

        id = bean.getId();
        nomeFantasia = bean.getNomeFantasia();
        telefone = bean.getTelefone();
        pessoaContato = bean.getPessoaContato();
        email = bean.getEmail();
        whatsapp = bean.getWhatsapp();
        dataCadastro = DateUtils.getDiaMesAnoPortugues(bean.getDataCadastro());
    }

    @Override
    public LojaBean converter(LojaBean bean) {

        bean = super.converter(bean);
        bean.setNomeFantasia(nomeFantasia);
        bean.setTelefone(telefone);
        bean.setPessoaContato(pessoaContato);
        bean.setEmail(email);
        bean.setWhatsapp(whatsapp);
        return bean;
    }

    public String getNomeFantasia() {

        return isNull(nomeFantasia) ? "" : nomeFantasia;
    }
}
