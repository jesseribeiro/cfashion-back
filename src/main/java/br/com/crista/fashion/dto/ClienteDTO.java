package br.com.crista.fashion.dto;

import br.com.crista.fashion.bean.ClienteBean;
import br.com.crista.fashion.bean.EnderecoBean;
import br.com.crista.fashion.utils.StringUtils;
import lombok.*;

import java.util.Calendar;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDTO extends GenericDTO<ClienteBean> {

    // filtros
    private String dataInicial;
    private String dataFinal;

    private Calendar dataCadastro;
    private Calendar dataNascimento;
    private String nome;
    private String cpf;
    private String sexo;
    private String celular;
    private String email;
    private EnderecoBean endereco;

    private String cidade;
    private String estado;
    private Integer qtd;

    public ClienteDTO(ClienteBean bean) {
        super(bean);
        nome = bean.getNome();
        cpf = StringUtils.inserirMascaraCpfCnpj(bean.getCpf());
        sexo = bean.getSexo();
        celular = bean.getCelular();
        email = bean.getEmail();
        dataCadastro = bean.getDataCadastro();
        dataNascimento = bean.getDataNascimento();
        endereco = bean.getEndereco();
    }

    @Override
    public ClienteBean converter(ClienteBean bean) {
        bean = super.converter(bean);
        bean.setNome(nome);
        bean.setCpf(StringUtils.desformataCpfCnpj(cpf));
        bean.setSexo(sexo);
        bean.setCelular(celular);
        bean.setEmail(email);
        bean.setEndereco(endereco);
        bean.setDataNascimento(dataNascimento);
        return bean;
    }
}
