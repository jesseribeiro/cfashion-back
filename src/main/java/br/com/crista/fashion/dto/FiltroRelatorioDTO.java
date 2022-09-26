package br.com.crista.fashion.dto;

import br.com.crista.fashion.enumeration.*;
import br.com.crista.fashion.json.CalendarDeserializerJson;
import br.com.crista.fashion.json.MoneyDeserializerJson;
import br.com.crista.fashion.utils.DateUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FiltroRelatorioDTO {

    Long empresaId;
    Long lojaId;
    Long redeId;
    String nomeEmpresa;
    String nomeLoja;
    String nomeRede;
    Long tipoLancamentoId;
    @JsonDeserialize(converter = CalendarDeserializerJson.class)
    Calendar dataInicio;
    @JsonDeserialize(converter = CalendarDeserializerJson.class)
    Calendar dataFim;
    Double atrasoInicio;
    Double atrasoFim;
    @JsonDeserialize(converter = CalendarDeserializerJson.class)
    Calendar vencimentoAte;
    EnumTipoRelatorio tipoRel;
    String tpAnalise;
    Boolean listarContas;
    Boolean listarSaldos;
    Boolean listarVencimentos;
    Boolean listarLancamentos;
    Boolean listarEstornos;
    Boolean listarPagamentoAcordo;
    Boolean todasCobranca;
    Long tpLanc;
    Long tpCobranca;

    //compras por clientes
    String tpGrupos;
    EnumPeriodoRelatorio tpPeriodo;
    EnumMetodoPorRelatorio tpPor;
    Boolean totalizarCliente;
    Boolean totalizarDia;

    //compras por lojas
    EnumTotalizarPorDataRelatorio totalizacao;
    String operacao;

    //defasagem cobrança
    Integer inicioAtraso;
    Integer fimAtraso;
    Integer inicioDefas;
    Integer fimDefas;
    EnumSaldoDevedor saldoDevedor;
    Long tpOrdena;

    Boolean listarTelefone;
    Boolean listarSerasa;
    Boolean listarEndereco;

    //pagamento por local
    Long tpPagamento;

    //lojascredenciadas
    String estado;
    BigInteger ramoAtividadeId;
    Long status;

    Boolean comprasSemJuros;

    Long clienteAcordo;

    EnumTipoPagamento tipoPagamento;

    //usuarios
    Long isAtivo;
    String perfil;

    BigInteger retornoFinal;
    BigInteger retornoInicial;
    BigInteger remessaInicial;
    BigInteger remessaFinal;

    //compras por Parcelas
    @JsonDeserialize(converter = CalendarDeserializerJson.class)
    Calendar dataInicioCompra;
    @JsonDeserialize(converter = CalendarDeserializerJson.class)
    Calendar dataFimCompra;
    @JsonDeserialize(converter = CalendarDeserializerJson.class)
    Calendar dataInicioPagamento;
    @JsonDeserialize(converter = CalendarDeserializerJson.class)
    Calendar dataFimPagamento;

    EnumTipoPagamento banco;

    String situacao;

    //Cobrança por usuário analítico
    String procedimento;
    Long userPratico;
    String tpOcorrencia;
    String tpSituacao;

    //Retorno por usuário
    Boolean totalizarPagamentosFaixaAtraso;
    Boolean listarPagamentosPorUsuarios;

    Long cedente;

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    BigDecimal valor;

    //Acordos Efetuados
    EnumSituacaoConta tpSituacaoConta;
    String tpSituacaoAcordo;

    String dataDeInicio;
    Boolean inadimplencia;
    //Clientes Novos
    Boolean comprasCanceladas;

    public String getPeriodoFormatado() {
        if(dataInicio != null && dataFim != null) {
            return DateUtils.getDiaMesAnoPortugues(dataInicio) + " até " + DateUtils.getDiaMesAnoPortugues(dataFim);
        }
        return "";
    }
}
