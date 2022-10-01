package br.com.crista.fashion.bean;

import br.com.crista.fashion.json.MoneyDeserializerJson;
import br.com.crista.fashion.utils.NullUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class RolePermissaoBean {

    // AUTONOMIA PARA LIMITES DE CRÉDITO
    // Informe abaixo os limites de crédito máximos permitidos para os clientes com restrição cadastral (SERASA / SPC / NEUROTECH / CRIVO) ou abaixo da pontuação mínima de SCORE.
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal limiteCredMensalRestricaoSPC;
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal limiteCredGlobalRestricaoSPC;
    // Informe abaixo os limites de crédito máximos permitidos para os clientes com restrição cadastral (cruzamento de dados).
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal limiteCredMensalRestricaoCruzarDados;
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal limiteCredGlobalRestricaoCruzarDados;
    // Informe abaixo os limites de crédito máximos permitidos para os clientes com restrição cadastral (grupo de risco).
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal limiteCredMensalRestricaoGrupoRisco;
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal limiteCredGlobalRestricaoGrupoRisco;
    // Informe abaixo os limites de crédito máximos que o usuário pode autorizar.
    // Só é utilizado em caso de autorização de limites superiores aos calculados automaticamente
    // pelo sistema e/ou documentos obrigatórios que não foram digitalizados.
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal limiteCredMensal;
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal limiteCredGlobal;

    // AUTONOMIA PARA AUTORIZACOES DE COMPRA
    // Informe abaixo o valor máximo de compra que o usuário pode autorizar. É utilizado nos casos em que uma autorização de compra foi recusada pelo sistema.
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal valorMaxAutorizaCompra;
    // Informe abaixo a quantidade máxima de dias em atraso de um cliente que o usuário pode autorizar. É utilizada nos casos em que a autorização de compra é recusada por atraso no pagamento da fatura.
    @Column(name = "qtd_max_dias_atraso_autoriza", columnDefinition = "int DEFAULT 0")
    private int qtdMaxDiasAtrasoAutoriza;
    // Informe abaixo a quantidade máxima de dias de carência para compra que o usuário pode autorizar.
    // Essa quantidade de dias é acrescentada a data da compra para efeito de faturamento da primeira parcela. Deixe ZERO para utilizar a carência padrão.UTILIZADO APENAS PARA CARTÃO CARNÊ.
    @Column(name = "qtd_max_dias_carencia_compra", columnDefinition = "int DEFAULT 0")
    private int qtdMaxDiasCarenciaCompra;
    // Informe abaixo se esse usuário vai poder informar valor de entrada nas autorizações de compra via WEB.
    @Column(name = "informar_valor_entrada")
    private Boolean informarValorEntrada;

    // AUTONOMIA PARA RENEGOCIAÇÃO (ACORDOS)
    // Informe abaixo o percentual máximo de desconto permitido sobre o valor em atraso e/ou a atualização monetária que o usuário pode conceder em um acordo.
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal peMaxDescontoAtrasoAcordo;
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal peMaxDescontoAtualizacaoAcordo;
    //Informe abaixo a quantidade máxima de parcelas permitida e até que valor mínimo de parcela que o usuário pode conceder em um acordo.
    @Column(name = "qtd_max_parcelas_acordo", columnDefinition = "int DEFAULT 0")
    private int qtdMaxParcelasAcordo;
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal valorMinParcelaAcordo;
    // Informe abaixo o percentual mínimo de juros permitido que o usuário pode conceder em um acordo.
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal peMinTaxaJurosAcordo;
    // Informe abaixo o valor mínimo de tarifa permitido que o usuário pode conceder em um acordo.
    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal valorMinTarifaAcordo;
    // Informe abaixo a quantidade mínima de dias em atraso do cliente para que o usuário possa realizar um acordo.
    @Column(name = "qtd_dias_atraso_min_para_acordo", columnDefinition = "int DEFAULT 0")
    private int qtdDiasAtrasoMinParaAcordo;

    // AUTONOMIA PARA COBRANÇA / ALTERAÇÃO DE VENCIMENTO
    // Informe abaixo a quantidade mínima e máxima de dias em atraso permitida para cobrança para esse perfil.
    @Column(name = "qtd_dias_min_cobranca", columnDefinition = "int DEFAULT 0")
    private int qtdDiasMinCobranca;
    @Column(name = "qtd_dias_max_cobranca", columnDefinition = "int DEFAULT 0")
    private int qtdDiasMaxCobranca;
    @Column(name = "gerar_fila_cobranca")
    private Boolean gerarFilaCobranca;

    // AUTONOMIA PARA CONSULTAS CADASTRAIS
    // Informe abaixo a quantidade máxima de consultas cadastrais permitidas para esse perfil. Informe 0 (ZERO) caso não deseje restringir a quantidade de consultas.
    @Column(name = "qtd_max_consultas_cadastral", columnDefinition = "int DEFAULT 0")
    private int qtdMaxConsultasCadastral;
    @Column(name = "ultimos_dias_avaliar_consultas_cadastral", columnDefinition = "int DEFAULT 0")
    private int ultimosDiasAvaliarConsultasCadastral;
    // permissões especiais para restrição cadastral
    @Column(name = "visualizar_resp_restricao_cadastral_inclusao")
    private Boolean visualizarRespRestricaoCadastralInclusao;
    @Column(name = "permitir_cadastro_restricao_cadastral")
    private Boolean permitirCadastroRestricaoCadastral;
    @Column(name = "visualizar_respanalise_visual_inclusao")
    private Boolean visualizarRespAnaliseVisualInclusao;
    @Column(name = "permitir_cadastro_analise_visual")
    private Boolean permitirCadastroAnaliseVisual;
    // permissões especiais para grupo de risco
    @Column(name = "visualizar_grupo_risco_inclusao")
    private Boolean visualizarGrupoRiscoInclusao;
    @Column(name = "permitir_cadastro_grupo_risco")
    private Boolean permitirCadastroGrupoRisco;
    // Selecione as permissões especiais para cruzamento de dados
    @Column(name = "visualizar_dados_cruzados_inclusao")
    private Boolean visualizarDadosCruzadosInclusao;
    @Column(name = "permitir_cadastro_dados_cruzados")
    private Boolean permitirCadastroDadosCruzados;
    // PERMISSÕES DE PESQUISA
    @Column(name = "pesquisa_cpf")
    private Boolean pesquisaCPF;
    @Column(name = "pesquisa_nome")
    private Boolean pesquisaNome;
    @Column(name = "pesquisa_identidade")
    private Boolean pesquisaIdentidade;
    @Column(name = "pesquisa_data_cadastro")
    private Boolean pesquisaDataCadastro;
    @Column(name = "pesquisa_telefone")
    private Boolean pesquisaTelefone;

    // PERMISSÕES ESPECIAIS
    // as informações do cliente que o perfil terá o direito de visualizar nos relatórios / telas.
    @Column(name = "visualizar_telefone")
    private Boolean visualizarTelefone;
    @Column(name = "visualizar_endereco")
    private Boolean visualizarEndereco;
    @Column(name = "visualizar_cpf")
    private Boolean visualizarCPF;
    @Column(name = "visualizar_nascimento")
    private Boolean visualizarNascimento;
    @Column(name = "visualizar_score")
    private Boolean visualizarScore;
    @Column(name = "visualizar_limites")
    private Boolean visualizarLimites;
    // Outras Permissões
    // Limitar autonomia de limite de crédito, pagamento fatura WEB e autorizações de compra a própria entidade (rede, loja ou convênio)
    @Column(name = "limitar_autonomia")
    private Boolean limitarAutonomia;
    @Column(name = "alterar_apenas_clientes_da_entidade")
    private Boolean alterarApenasClientesDaEntidade;
    @Column(name = "visualizar_pag_em_banco")
    private Boolean visualizarPagEmBanco;
    @Column(name = "visualizar_boleto_fatura")
    private Boolean visualizarBoletoFatura;
    @Column(name = "visualizar_msg_status_processamento")
    private Boolean visualizarMsgStatusProcessamento;
    @Column(name = "visualizar_parcelamento_fatura")
    private Boolean visualizarParcelamentoFatura;
    //(Utilizado para obrigar atualização ao solicitar segunda via de fatura)
    @Column(name = "dias_validade_ultima_alteracao_telefone_email", columnDefinition = "int DEFAULT 0")
    private int diasValidadeUltimaAlteracaoTelefoneEmail;

    public BigDecimal getLimiteCredMensalRestricaoSPC() {
        return limiteCredMensalRestricaoSPC;
    }

    public BigDecimal getLimiteCredGlobalRestricaoSPC() {
        return limiteCredGlobalRestricaoSPC;
    }

    public BigDecimal getLimiteCredMensalRestricaoCruzarDados() {
        return limiteCredMensalRestricaoCruzarDados;
    }

    public BigDecimal getLimiteCredGlobalRestricaoCruzarDados() {
        return limiteCredGlobalRestricaoCruzarDados;
    }

    public BigDecimal getLimiteCredMensalRestricaoGrupoRisco() {
        return limiteCredMensalRestricaoGrupoRisco;
    }

    public BigDecimal getLimiteCredGlobalRestricaoGrupoRisco() {
        return limiteCredGlobalRestricaoGrupoRisco;
    }

    public BigDecimal getLimiteCredMensal() {
        return limiteCredMensal;
    }

    public BigDecimal getLimiteCredGlobal() {
        return limiteCredGlobal;
    }

    public BigDecimal getValorMaxAutorizaCompra() {
        return valorMaxAutorizaCompra;
    }

    public int getQtdMaxDiasAtrasoAutoriza() {
        return qtdMaxDiasAtrasoAutoriza;
    }

    public int getQtdMaxDiasCarenciaCompra() {
        return qtdMaxDiasCarenciaCompra;
    }

    public Boolean getInformarValorEntrada() {
        return NullUtils.isNull(informarValorEntrada);
    }

    public BigDecimal getPeMaxDescontoAtrasoAcordo() {
        return peMaxDescontoAtrasoAcordo;
    }

    public BigDecimal getPeMaxDescontoAtualizacaoAcordo() {
        return peMaxDescontoAtualizacaoAcordo;
    }

    public int getQtdMaxParcelasAcordo() {
        return qtdMaxParcelasAcordo;
    }

    public BigDecimal getValorMinParcelaAcordo() {
        return valorMinParcelaAcordo;
    }

    public BigDecimal getPeMinTaxaJurosAcordo() {
        return peMinTaxaJurosAcordo;
    }

    public BigDecimal getValorMinTarifaAcordo() {
        return valorMinTarifaAcordo;
    }

    public int getQtdDiasAtrasoMinParaAcordo() {
        return qtdDiasAtrasoMinParaAcordo;
    }

    public int getQtdDiasMinCobranca() {
        return qtdDiasMinCobranca;
    }

    public int getQtdDiasMaxCobranca() {
        return qtdDiasMaxCobranca;
    }

    public Boolean getGerarFilaCobranca() {
        return NullUtils.isNull(gerarFilaCobranca);
    }

    public int getQtdMaxConsultasCadastral() {
        return qtdMaxConsultasCadastral;
    }

    public int getUltimosDiasAvaliarConsultasCadastral() {
        return ultimosDiasAvaliarConsultasCadastral;
    }

    public Boolean getVisualizarRespRestricaoCadastralInclusao() {
        return NullUtils.isNull(visualizarRespRestricaoCadastralInclusao);
    }

    public Boolean getPermitirCadastroRestricaoCadastral() {
        return NullUtils.isNull(permitirCadastroRestricaoCadastral);
    }

    public Boolean getVisualizarRespAnaliseVisualInclusao() {
        return NullUtils.isNull(visualizarRespAnaliseVisualInclusao);
    }

    public Boolean getPermitirCadastroAnaliseVisual() {
        return NullUtils.isNull(permitirCadastroAnaliseVisual);
    }

    public Boolean getVisualizarGrupoRiscoInclusao() {
        return NullUtils.isNull(visualizarGrupoRiscoInclusao);
    }

    public Boolean getPermitirCadastroGrupoRisco() {
        return NullUtils.isNull(permitirCadastroGrupoRisco);
    }

    public Boolean getVisualizarDadosCruzadosInclusao() {
        return NullUtils.isNull(visualizarDadosCruzadosInclusao);
    }

    public Boolean getPermitirCadastroDadosCruzados() {
        return NullUtils.isNull(permitirCadastroDadosCruzados);
    }

    public Boolean getPesquisaCPF() {
        return NullUtils.isNull(pesquisaCPF);
    }

    public Boolean getPesquisaNome() {
        return NullUtils.isNull(pesquisaNome);
    }

    public Boolean getPesquisaIdentidade() {
        return NullUtils.isNull(pesquisaIdentidade);
    }

    public Boolean getPesquisaDataCadastro() {
        return NullUtils.isNull(pesquisaDataCadastro);
    }

    public Boolean getPesquisaTelefone() {
        return NullUtils.isNull(pesquisaTelefone);
    }

    public Boolean getVisualizarTelefone() {
        return NullUtils.isNull(visualizarTelefone);
    }

    public Boolean getVisualizarEndereco() {
        return NullUtils.isNull(visualizarEndereco);
    }

    public Boolean getVisualizarCPF() {
        return NullUtils.isNull(visualizarCPF);
    }

    public Boolean getVisualizarNascimento() {
        return NullUtils.isNull(visualizarNascimento);
    }

    public Boolean getVisualizarScore() {
        return NullUtils.isNull(visualizarScore);
    }

    public Boolean getVisualizarLimites() {
        return NullUtils.isNull(visualizarLimites);
    }

    public Boolean getLimitarAutonomia() {
        return NullUtils.isNull(limitarAutonomia);
    }

    public Boolean getAlterarApenasClientesDaEntidade() {
        return NullUtils.isNull(alterarApenasClientesDaEntidade);
    }

    public Boolean getVisualizarPagEmBanco() {
        return NullUtils.isNull(visualizarPagEmBanco);
    }

    public Boolean getVisualizarBoletoFatura() {
        return NullUtils.isNull(visualizarBoletoFatura);
    }

    public Boolean getVisualizarMsgStatusProcessamento() {
        return NullUtils.isNull(visualizarMsgStatusProcessamento);
    }

    public Boolean getVisualizarParcelamentoFatura() {
        return NullUtils.isNull(visualizarParcelamentoFatura);
    }

    public int getDiasValidadeUltimaAlteracaoTelefoneEmail() {
        return diasValidadeUltimaAlteracaoTelefoneEmail;
    }
}
