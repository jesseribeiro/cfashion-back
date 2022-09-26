package br.com.crista.fashion.dto;

import br.com.crista.fashion.enumeration.EnumStatusVenda;
import br.com.crista.fashion.enumeration.TipoFormaPagamento;
import br.com.crista.fashion.bean.AutorizacaoBean;
import br.com.crista.fashion.bean.CarneBean;
import br.com.crista.fashion.utils.DateUtils;
import br.com.crista.fashion.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Calendar;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AutorizacaoDTO extends GenericDTO {

    private String dataInicial;
    private String dataFinal;

    private Long carneId;
    private Long vendaId;
    private Long lojaId;
    private Long redeId;
    private Long empresaId;
    private Long clienteId;
    private Long pagamentoId;
    private Calendar dataAutorizacao;
    private String cpf;
    private BigDecimal valorVenda;
    private BigDecimal valorTotal;
    private Integer qtdParcela;
    private String nomeLoja;
    private String codigoAutorizacao;
    private String tipoAutorizacao;
    private String nomeUsuario;
    private String recusado;
    private String situacao;
    private boolean podeCancelar;
    private String dataCompra;

    public AutorizacaoDTO(AutorizacaoBean autorizacao, CarneBean carne) {
        podeCancelar = false;
        if (carne != null) {
            this.carneId = carne.getId();
            this.qtdParcela = carne.getQtdParcela();
            if (autorizacao.getVenda() != null && autorizacao.getVenda().getStatus() != null &&  carne.getFormaPagamento().equals(TipoFormaPagamento.CARNE)) {
                if(autorizacao.getVenda().getStatus().equals(EnumStatusVenda.AUTORIZADA)) {
                    podeCancelar = DateUtils.equalsDate(autorizacao.getVenda().getDataCadastro(), Calendar.getInstance());
                }
            }
        }
        if (autorizacao.getVenda() != null){
            this.vendaId = autorizacao.getVenda().getId();
            this.valorVenda = autorizacao.getVenda().getValorProduto();
            this.situacao = autorizacao.getVenda().getStatus().getLabel();
            this.valorTotal = carne.getValorTotal();
        }

        this.clienteId = autorizacao.getCliente().getId();
        this.dataAutorizacao = autorizacao.getDataCadastro();
        this.dataCompra = DateUtils.getDiaMesAno(autorizacao.getDataCadastro());

        try {
            this.cpf = StringUtils.inserirMascaraCpfCnpj(autorizacao.getCliente().getCpf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.lojaId = autorizacao.getLoja().getId();
        this.nomeLoja = autorizacao.getLoja().getRazaoSocial();

        if (autorizacao.getVendedor() != null) {
            this.nomeUsuario = autorizacao.getVendedor().getNome();
        }

        if (autorizacao.getMotivoRecusa() != null){
            this.recusado = autorizacao.getMotivoRecusa().getLabel();
        }


    }
}
