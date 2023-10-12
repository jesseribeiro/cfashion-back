package br.com.crista.fashion.dto;

import br.com.crista.fashion.bean.ParcelaBean;
import br.com.crista.fashion.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Calendar;

import static java.util.Objects.nonNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParcelaDTO extends GenericDTO<ParcelaBean> {

    private Calendar dataInicial;
    private Calendar dataFinal;

    private Long marcaId;
    private String marca;
    private String nomeProduto;
    private Long produtoId;
    private Long clienteId;
    private String nomeCliente;
    private String cpf;
    private BigDecimal valorParcela;
    private Integer numero;
    private Calendar dataVencimento;
    private Calendar dataPagamento;
    private Long vendaId;
    private String status;
    private String tipo;

    public ParcelaDTO(ParcelaBean bean) {

        super(bean);
        marcaId = bean.getLoja().getId();
        marca = bean.getLoja().getNomeFantasia();

        if (nonNull(bean.getProduto())) {

            nomeProduto = bean.getProduto().getNome();
            produtoId = bean.getProduto().getId();
        }

        clienteId = bean.getCliente().getId();
        nomeCliente = bean.getCliente().getNome();
        cpf = StringUtils.inserirMascaraCpfCnpj(bean.getCliente().getCpf());
        valorParcela = bean.getValorParcela();
        numero = bean.getNumero();
        dataVencimento = bean.getDataVencimento();
        dataPagamento = bean.getDataPagto();
        vendaId = bean.getVenda().getId();

        tipo = nonNull(bean.getVenda().getTipo()) ? bean.getVenda().getTipo().getLabel() : null;
        status = nonNull(bean.getStatus()) ? bean.getStatus().getLabel() : null;

    }
}
