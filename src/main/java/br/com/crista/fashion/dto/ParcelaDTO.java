package br.com.crista.fashion.dto;

import br.com.crista.fashion.bean.ParcelaBean;
import br.com.crista.fashion.utils.StringUtils;
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
public class ParcelaDTO extends GenericDTO<ParcelaBean> {

    private String dataInicial;
    private String dataFinal;

    private Long marcaId;
    private String marca;
    private String nomeProduto;
    private Long produtoId;
    private Long clienteId;
    private String nomeCliente;
    private String cpf;
    private BigDecimal valorParcela;
    private BigDecimal vlTarifa;
    private BigDecimal vlParcelaSemJuros;
    private Integer numero;
    private Calendar dataVencimento;
    private Calendar dataPagamento;
    private Long vendaId;
    private String status;

    public ParcelaDTO(ParcelaBean bean) {
        super(bean);
        marcaId = bean.getLoja().getId();
        marca = bean.getLoja().getNomeFantasia();

        if (bean.getProduto() != null) {
            nomeProduto = bean.getProduto().getNome();
            produtoId = bean.getProduto().getId();
        }

        clienteId = bean.getCliente().getId();
        nomeCliente = bean.getCliente().getNome();
        cpf = StringUtils.inserirMascaraCpfCnpj(bean.getCliente().getCpf());
        valorParcela = bean.getValorParcela();
        vlTarifa = bean.getVlTarifa();
        vlParcelaSemJuros = bean.getVlParcelaSemJuros();
        numero = bean.getNumero();
        dataVencimento = bean.getDataVencimento();
        dataPagamento = bean.getDataPagto();
        vendaId = bean.getVenda().getId();

        if (bean.getStatus() != null) {
            status = bean.getStatus().getLabel();
        }
    }
}
