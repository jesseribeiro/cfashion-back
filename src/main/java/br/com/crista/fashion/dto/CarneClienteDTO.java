package br.com.crista.fashion.dto;

import br.com.crista.fashion.bean.CarneBean;
import br.com.crista.fashion.bean.ParcelaBean;
import br.com.crista.fashion.enumeration.TipoFormaPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarneClienteDTO extends GenericDTO {

    // filtros
    private Calendar dataInicial;
    private Calendar dataFinal;
    private Long lojaId;
    private String status;

    private String tipoCarne;
    private boolean selected;
    private boolean expanded;
    private Long carneId;
    private Long vendaId;
    private Long renegociacaoId;
    private Calendar dataCompra;
    private Integer qtdParcela;
    private String nomeLoja;
    private BigDecimal valorTotal;
    private BigDecimal valorEntrada;
    private BigDecimal peMultaPorAtraso;
    private BigDecimal peJurosMoraPorAtraso;
    // utilizado na renegociacao
    private BigDecimal saldoDevedorCarne;
    private BigDecimal totalAtrasoCarne;
    private BigDecimal totalVencerCarne;
    private BigDecimal totalMultaJurosCarne;

    private List<ParcelaClienteDTO> parcelas;
    private TipoFormaPagamento formaPagamento;

    public CarneClienteDTO(CarneBean carne) {
        this.tipoCarne = carne.getTipoCarne().getLabel();
        this.expanded = false;
        this.selected = false;
        this.carneId = carne.getId();
        if(carne.getVenda() != null) {
            this.vendaId = carne.getVenda().getId();
            this.nomeLoja = carne.getVenda().getLoja().getNomeFantasia();
        }
        this.dataCompra = carne.getDataCompra();
        this.qtdParcela = carne.getQtdParcela();
        this.status = carne.getStatus().getLabel();
        this.valorTotal = carne.getValorTotal();
        this.valorEntrada = carne.getValorEntrada();

        this.parcelas = new ArrayList<>();
        for(ParcelaBean p : carne.getParcelas()) {
            this.parcelas.add(new ParcelaClienteDTO(p));
        }

        this.formaPagamento = carne.getFormaPagamento();

        Collections.sort(this.parcelas, Comparator.comparingInt(ParcelaClienteDTO::getNumero));
    }
}
