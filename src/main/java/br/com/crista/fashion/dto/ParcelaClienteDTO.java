package br.com.crista.fashion.dto;

import br.com.crista.fashion.bean.ParcelaBean;
import br.com.crista.fashion.enumeration.EnumStatusParcela;
import br.com.crista.fashion.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParcelaClienteDTO extends GenericDTO<ParcelaBean> {

    private Long lojaId;
    private BigDecimal valor;
    private BigDecimal vlTarifa;
    private BigDecimal vlParcelaSemJuros;
    private Integer numero;
    private String dataVencimento;
    private String dataPagamento;
    private Long vendaId;
    private Long clienteId;
    private EnumStatusParcela status;

    public ParcelaClienteDTO(ParcelaBean parcela) {
        super(parcela);
        this.id = parcela.getId();
        this.numero = parcela.getNumero();
        this.dataVencimento = DateUtils.getDiaMesAnoPortugues(parcela.getDataVencimento());
        this.lojaId = parcela.getLoja().getId();
        this.valor = parcela.getValorParcela();
        this.vlTarifa = parcela.getVlTarifa();
        this.vlParcelaSemJuros = parcela.getVlParcelaSemJuros();
        if (parcela.getDataVencimento() != null) {
            this.dataPagamento = DateUtils.getDiaMesAnoPortugues(parcela.getDataVencimento());
        }
        this.vendaId = parcela.getVenda().getId();
        this.clienteId = parcela.getCliente().getId();
        this.status = parcela.getStatus();
    }
}
