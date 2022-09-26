package br.com.crista.fashion.dto;

import br.com.crista.fashion.bean.ClienteLojaBean;
import br.com.crista.fashion.utils.StringUtils;
import lombok.*;

import java.util.Calendar;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteLojaDTO extends GenericDTO<ClienteLojaBean> {

    private Long clienteId;
    private String nome;
    private String cpf;
    private String identidade;
    private Calendar dataNascimento;

    private Calendar clienteDesde;
    private String nomeLoja;
    private String nomeEmpresa;
    private Long lojaId;
    private Long redeId;
    private Long empresaId;
    private String telefone;
    private Long algorixId;
    private String segmentacao;
    private Boolean segmento_atualizado;

    public ClienteLojaDTO(ClienteLojaBean bean) {
        super(bean);
        this.clienteId = bean.getCliente().getId();
        this.nome = bean.getCliente().getNome();
        try {
            this.cpf = StringUtils.inserirMascaraCpfCnpj(bean.getCliente().getCpf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.identidade = bean.getCliente().getIdentidade();
        this.dataNascimento = bean.getCliente().getDataNascimento();

        this.clienteDesde = bean.getDataCadastro();
        this.lojaId = bean.getLoja().getId();
        this.nomeLoja = bean.getLoja().getNomeFantasia();
        this.algorixId = bean.getAlgorixId();
        if(bean.getSegmentacaoCliente() != null) {
            this.segmento_atualizado = bean.getSegmentoAtualizado();
        }
        if(bean.getSegmentacaoCliente() != null) {
            this.segmentacao = bean.getSegmentacaoCliente().getLabel();
        }
        this.telefone = (bean.getCliente().getTelResidencial() != null && !bean.getCliente().getTelResidencial().isEmpty()) ? bean.getCliente().getTelResidencial() : bean.getCliente().getCelular();
    }
}
