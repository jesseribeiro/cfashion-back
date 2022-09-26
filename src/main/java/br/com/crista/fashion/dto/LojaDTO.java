package br.com.crista.fashion.dto;

import br.com.crista.fashion.bean.EnderecoBean;
import br.com.crista.fashion.bean.LojaBean;
import br.com.crista.fashion.enumeration.StatusLoja;
import br.com.crista.fashion.json.CalendarDeserializerJson;
import br.com.crista.fashion.json.MoneyDeserializerJson;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Calendar;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LojaDTO extends GenericDTO<LojaBean> {

    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;
    private String status;
    private String telComercial;
    private String chaveTEF;
    private String pessoaContato;
    private String site;
    private String observacoes;
    private String ramoAtividadeLabel;
    private String empresaLabel;
    private String email;
    private String whatsapp;
    private Long ramoAtividadeId;
    private Long empresaId;
    private Long logoId;
    private boolean emitirCartaoLoja;
    private boolean envioSMSBoasVindas;
    private boolean permitirPagamentoLoja;
    private boolean permitirAutorizacaoCompra;
    private boolean possuiLimiteExclusivo;
    private boolean crediarioProprio;
    private boolean campanhaAtiva;
    private Long campanhaQtdVendas;
    private Long campanhaQtdDiasPrimeiraParcela;
    private EnderecoBean endereco;

    // Lancamentos Repasse automatico
    private Long tpLancamentoId;
    private Integer diaLancamento; // deixar fixo todo dia primeiro
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal vlLancamento;
    @JsonDeserialize(converter = CalendarDeserializerJson.class)
    private Calendar dataVigenciaLancamento;
    @JsonDeserialize(converter = CalendarDeserializerJson.class)
    private Calendar dataFimVigencia;

    public LojaDTO(LojaBean bean){
        id = bean.getId();
        razaoSocial = bean.getRazaoSocial();
        nomeFantasia = bean.getNomeFantasia();
        cnpj = bean.getCnpj();
        status = bean.getStatus().name();
        telComercial = bean.getTelComercial();
        chaveTEF = bean.getChaveTEF();
        pessoaContato = bean.getPessoaContato();
        site = bean.getSite();
        observacoes = bean.getObservacoes();
        email = bean.getEmail();
        whatsapp = bean.getWhatsapp();
        emitirCartaoLoja = bean.getIsEmitirCartaoLoja();
        envioSMSBoasVindas = bean.getIsEnviarSmsBoasVindas();
        permitirPagamentoLoja = bean.getIsPermitePagtoLoja();
        permitirAutorizacaoCompra = bean.getIsPermiteAutorizacaoCompraApp();
        possuiLimiteExclusivo = bean.getIsPossuiLimiteExclusivo();
        if (bean.getCrediarioProprio() != null) {
            crediarioProprio = bean.getCrediarioProprio();
        }

        campanhaAtiva = bean.getCampanhaAtiva();
        campanhaQtdVendas = bean.getCampanhaQtdVendas();
        campanhaQtdDiasPrimeiraParcela = bean.getCampanhaQtdDiasPrimeiraParcela();

        if(bean.getEndereco() != null) {
            endereco = bean.getEndereco().getEndereco();
        }
    }

    @Override
    public LojaBean converter(LojaBean bean) {
        bean = super.converter(bean);
        bean.setRazaoSocial(razaoSocial);
        bean.setNomeFantasia(nomeFantasia == null ? "" : nomeFantasia );
        bean.setCnpj(cnpj);
        bean.setStatus(StatusLoja.valueOf(status));
        bean.setTelComercial(telComercial);
        bean.setChaveTEF(chaveTEF);
        bean.setPessoaContato(pessoaContato);
        bean.setSite(site);
        bean.setObservacoes(observacoes);
        bean.setIsEmitirCartaoLoja(emitirCartaoLoja);
        bean.setIsEnviarSmsBoasVindas(envioSMSBoasVindas);
        bean.setIsPermitePagtoLoja(permitirPagamentoLoja);
        bean.setIsPermiteAutorizacaoCompraApp(permitirAutorizacaoCompra);
        bean.setIsPossuiLimiteExclusivo(possuiLimiteExclusivo);
        bean.setCampanhaAtiva(campanhaAtiva);
        bean.setCampanhaQtdVendas(campanhaQtdVendas);
        bean.setCampanhaQtdDiasPrimeiraParcela(campanhaQtdDiasPrimeiraParcela);
        bean.setEmail(email);
        bean.setCrediarioProprio(crediarioProprio);
        bean.setWhatsapp(whatsapp);
        return bean;
    }

    public String getRazaoSocial(){
        return razaoSocial == null ? "" : razaoSocial;
    }

    public String getNomeFantasia(){
        return nomeFantasia == null ? "" : nomeFantasia;
    }
}
