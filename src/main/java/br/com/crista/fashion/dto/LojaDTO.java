package br.com.crista.fashion.dto;

import br.com.crista.fashion.bean.LojaBean;
import br.com.crista.fashion.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LojaDTO extends GenericDTO<LojaBean> {

    private String nomeFantasia;
    private String telefone;
    private String pessoaContato;
    private String email;
    private String whatsapp;
    private String dataCadastro;

    public LojaDTO(LojaBean bean){
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
}
