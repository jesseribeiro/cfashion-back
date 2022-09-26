package br.com.crista.fashion.dto;

import br.com.crista.fashion.bean.ClienteBean;
import br.com.crista.fashion.bean.EnderecoBean;
import br.com.crista.fashion.enumeration.EnumEscolaridade;
import br.com.crista.fashion.enumeration.EnumGrupoScore;
import br.com.crista.fashion.json.MoneyDeserializerJson;
import br.com.crista.fashion.utils.DateUtils;
import br.com.crista.fashion.utils.StringUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDTO extends GenericDTO<ClienteBean> {

    // filtros
    private String dataInicial;
    private String dataFinal;

    private String dataCadastro;
    private Long lojaId;
    private Long redeId;
    private Long empresaId;
    private String nome;
    private String cpf;
    private String segmentacao;
    private String identidade;
    private String orgaoEmissor;
    private String dataEmissao;
    private String uf;
    private String grupo;
    private String dataNascimento;
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
    private String escolaridade;

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal rendaPrincipal;

    private EnderecoBean endereco;
    private String tipoResidencia;
    private Integer tempoResidenciaAnos;
    private Integer tempoResidenciaMeses;
    private String resideDesde;
    private String empresa;
    private String cargo;
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
    private String status;
    private String trabalhaNessaEmpresaDesde;
    private boolean permitirEnvioSPC;
    private boolean jaTemCadastroNaLoja;

    private Long provedor;
    private String outroProvedor;

    private Boolean flagAutorizacao;
    private Boolean autorizaCompra;

    private Boolean enderecoSecundario;

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal outrasRendas;

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal limiteCompartilhado;

    private String tipoClienteScore;
    private String descricao;
    private String observacao;
    private String assuntoObs;
    private Long exibirNaLojaObs;
    private String exibirAte;
    private Boolean exibirMsg;

    private String mensagemErro;

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal valorAluguel;

    private Long empregoCBO;

    private Integer score;

    public ClienteDTO(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    public ClienteDTO(ClienteBean bean) {
        super(bean);
        nome = bean.getNome();
        cpf = bean.getCpf();
        identidade = bean.getIdentidade();
        orgaoEmissor = bean.getOrgaoEmissor();
        dataEmissao = DateUtils.getDiaMesAno(bean.getDataEmissao());
        uf = bean.getUf();
        grupo = bean.getGrupo().name();
        dataNascimento = DateUtils.getDiaMesAno(bean.getDataNascimento());
        naturalEstado = bean.getNaturalEstado();
        naturalCidade = bean.getNaturalCidade();
        sexo = bean.getSexo();
        estadoCivil = bean.getEstadoCivil();
        dependentes = bean.getDependentes();
        nomePai = bean.getNomePai();
        nomeMae = bean.getNomeMae();
        telResidencial = bean.getTelResidencial();
        celular = bean.getCelular();
        email = bean.getEmail();
        if(bean.getEscolaridade() != null) {
            escolaridade = bean.getEscolaridade().name();
        }
        rendaPrincipal = bean.getRendaPrincipal();
        endereco = bean.getEndereco();
        tipoResidencia = bean.getTipoResidencia();
        tempoResidenciaAnos = bean.getTempoResidenciaAnos();
        tempoResidenciaMeses = bean.getTempoResidenciaMeses();
        empresa = bean.getEmpresa();
        cargo = bean.getCargo();
        tempoEmpregoAnos = bean.getTempoEmpregoAnos();
        tempoEmpregoMeses = bean.getTempoEmpregoMeses();
        telComercial = bean.getTelComercial();
        ramal = bean.getRamal();
        nomeRef1 = bean.getNomeRef1();
        relacaoRef1 = bean.getRelacaoRef1();
        telRef1 = bean.getTelRef1();
        nomeRef2 = bean.getNomeRef2();
        relacaoRef2 = bean.getRelacaoRef2();
        telRef2 = bean.getTelRef2();
        nomeRef3 = bean.getNomeRef3();
        relacaoRef3 = bean.getRelacaoRef3();
        telRef3 = bean.getTelRef3();
        nomeRef4 = bean.getNomeRef4();
        relacaoRef4 = bean.getRelacaoRef4();
        telRef4 = bean.getTelRef4();
        dataCadastro = DateUtils.getDiaMesAno(bean.getDataCadastro());
        limiteCompartilhado = bean.getLimiteCompartilhado();
        tipoClienteScore = bean.getTipoClienteScore();
        permitirEnvioSPC = bean.getPermitirEnvioSPC();
        resideDesde = bean.getResideDesde();
        trabalhaNessaEmpresaDesde = bean.getTrabalhaNessaEmpresaDesde();
        enderecoSecundario = bean.getEnderecoSecundario();
        if(bean.getOutrasRendas() != null) {
            outrasRendas = bean.getOutrasRendas();
        }
        if(bean.getStatus() != null) {
            status = bean.getStatus().name();
        }
        if (bean.getValorAluguel() != null) {
            valorAluguel = bean.getValorAluguel();
        }

        this.jaTemCadastroNaLoja = false;
        if (bean.getScore() != null) {
            this.score = bean.getScore();
        }
    }

    @Override
    public ClienteBean converter(ClienteBean bean) {
        bean = super.converter(bean);
        bean.setNome(nome);
        bean.setCpf(StringUtils.desformataCpfCnpj(cpf));
        bean.setIdentidade(identidade);
        bean.setOrgaoEmissor(orgaoEmissor);
        bean.setDataEmissao(DateUtils.getDiaMesAno(dataEmissao));
        bean.setUf(uf);
        bean.setGrupo(EnumGrupoScore.valueOf(grupo));
        bean.setDataNascimento(DateUtils.getDiaMesAno(dataNascimento));
        bean.setNaturalEstado(naturalEstado);
        bean.setNaturalCidade(naturalCidade);
        bean.setSexo(sexo);
        bean.setEstadoCivil(estadoCivil);
        bean.setDependentes(dependentes);
        bean.setNomePai(nomePai);
        bean.setNomeMae(nomeMae);
        bean.setTelResidencial(telResidencial);
        bean.setCelular(celular);
        bean.setEmail(email);
        if(escolaridade != null && !escolaridade.isEmpty()) {
            bean.setEscolaridade(EnumEscolaridade.getValue(escolaridade));
        }
        bean.setRendaPrincipal(rendaPrincipal);
        bean.setEndereco(endereco);
        bean.setTipoResidencia(tipoResidencia);
        bean.setTempoResidenciaAnos(tempoResidenciaAnos);
        bean.setTempoResidenciaMeses(tempoResidenciaMeses);
        bean.setEmpresa(empresa);
        bean.setCargo(cargo);
        bean.setTempoEmpregoAnos(tempoEmpregoAnos);
        bean.setTempoEmpregoMeses(tempoEmpregoMeses);
        bean.setTelComercial(telComercial);
        bean.setRamal(ramal);
        bean.setNomeRef1(nomeRef1);
        bean.setRelacaoRef1(relacaoRef1);
        bean.setTelRef1(telRef1);
        bean.setNomeRef2(nomeRef2);
        bean.setRelacaoRef2(relacaoRef2);
        bean.setTelRef2(telRef2);
        bean.setNomeRef3(nomeRef3);
        bean.setRelacaoRef3(relacaoRef3);
        bean.setTelRef3(telRef3);
        bean.setNomeRef4(nomeRef4);
        bean.setRelacaoRef4(relacaoRef4);
        bean.setTelRef4(telRef4);
        bean.setLimiteCompartilhado(limiteCompartilhado);
        bean.setTipoClienteScore(tipoClienteScore);
        bean.setPermitirEnvioSPC(permitirEnvioSPC);
        bean.setResideDesde(resideDesde);
        bean.setTrabalhaNessaEmpresaDesde(trabalhaNessaEmpresaDesde);
        bean.setEnderecoSecundario(enderecoSecundario);
        if (valorAluguel != null) {
            bean.setValorAluguel(valorAluguel);
        }
        if (outrasRendas != null) {
            bean.setOutrasRendas(outrasRendas);
        }
        bean.setScore(score);
        return bean;
    }

    public String calculaTempoResidencia(){
        if(this.resideDesde != null) {
            return "";
        }
        return null;
    }

    public String calculaTempoTrabalho(){
        if(this.trabalhaNessaEmpresaDesde != null) {
            return "";
        }
        return null;
    }


}
