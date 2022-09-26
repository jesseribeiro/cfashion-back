package br.com.crista.fashion.bean;

import br.com.crista.fashion.enumeration.EnumGrupoScore;
import br.com.crista.fashion.enumeration.EnumEscolaridade;
import br.com.crista.fashion.enumeration.EnumStatusCliente;
import br.com.crista.fashion.utils.NullUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;

@Audited
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Entity(name = "Cliente")
@Table(name = "cliente", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "cpf"
        })
})
public class ClienteBean extends GenericBean {

    private String nome;
    private String cpf;
    private String identidade;
    private String orgaoEmissor;

    private String uf;

    @Enumerated(EnumType.STRING)
    private EnumGrupoScore grupo;

    @Enumerated(EnumType.STRING)
    private EnumStatusCliente status;

    @Column(name = "data_emissao")
    @Temporal(TemporalType.DATE)
    private Calendar dataEmissao;

    @Column(name = "data_nascimento")
    @Temporal(TemporalType.DATE)
    private Calendar dataNascimento;

    private String naturalEstado;
    private Integer naturalCidade;
    private String sexo;
    private String estadoCivil;
    private Integer dependentes;
    private String nomePai;
    private String nomeMae;
    private String telResidencial;
    private String celular;
    private String email;

    @Enumerated(value = EnumType.STRING)
    private EnumEscolaridade escolaridade;
    private BigDecimal rendaPrincipal;

    @Column(name = "outras_rendas")
    private BigDecimal outrasRendas;

    @Embedded
    private EnderecoBean endereco;

    private String tipoResidencia;
    private Integer tempoResidenciaAnos;
    private Integer tempoResidenciaMeses;
    private String resideDesde;
    private String empresa;
    private String cargo;
    private String trabalhaNessaEmpresaDesde;
    private Integer tempoEmpregoAnos;
    private Integer tempoEmpregoMeses;
    private String telComercial;
    private String ramal;
    private String nomeRef1;
    private String relacaoRef1;
    private String telRef1;
    private String nomeRef2;
    private String relacaoRef2;
    private String telRef2;
    private String nomeRef3;
    private String relacaoRef3;
    private String telRef3;
    private String nomeRef4;
    private String relacaoRef4;
    private String telRef4;

    private BigDecimal limiteCompartilhado;

    private String tipoClienteScore;

    @Column(name = "permitir_envio_spc")
    private Boolean permitirEnvioSPC;

    private BigDecimal valorAluguel;

    @JoinColumn(name = "endereco_secundario")
    private Boolean enderecoSecundario;

    private Integer score;

    @Column(name = "data_status")
    @Temporal(TemporalType.DATE)
    private Calendar dataStatus;

    @Column(name = "data_alteracao_limite")
    @Temporal(TemporalType.DATE)
    private Calendar dataAlteracaoLimite;

    @Column(name = "data_ultima_cobranca")
    @Temporal(TemporalType.DATE)
    private Calendar dataUltimaCobranca;

    @Column(name = "data_primeira_compra")
    @Temporal(TemporalType.DATE)
    private Calendar dataPrimeiraCompra;

    @Column(name = "data_ultima_compra")
    @Temporal(TemporalType.DATE)
    private Calendar dataUltimaCompra;

    public boolean getPermitirEnvioSPC() {
        return NullUtils.isNull(permitirEnvioSPC);
    }

    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonResult = mapper.writeValueAsString(this);
            return jsonResult;
        } catch (JsonProcessingException e) {
            log.error("Erro ao converter objeto cliente para JSON");
        }
        return null;
    }

    public ClienteBean jsonToObject(String jsonInput) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonInput, ClienteBean.class);
    }
}
