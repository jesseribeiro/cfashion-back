package br.com.crista.fashion.dto;

import br.com.crista.fashion.enumeration.EnumStatusParcela;
import br.com.crista.fashion.bean.ClienteBean;
import br.com.crista.fashion.bean.LojaBean;
import br.com.crista.fashion.bean.ParcelaBean;
import br.com.crista.fashion.utils.Constants;
import br.com.crista.fashion.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReciboVendaDTO {

    private Long vendaId;
    private Long lojaId;
    private Long clienteId;
    private Calendar dataVenda;
    private BigDecimal valorVenda;
    private BigDecimal valorEntrada;
    private BigDecimal saldoDisponivel;
    private Integer qtdParcela;
    private String codigoAutorizacao;
    private String tipoAutorizacao;
    private List<ParcelaReciboVendaDTO> parcelas;
    private String dadosLoja;
    private String dadosCliente;
    private String nomeCidade;
    private boolean flgCancelado;

    public void setDadosCliente(ClienteBean cliente) {
        try {
            this.dadosCliente = cliente.getNome() + "\n CPF: " + StringUtils.inserirMascaraCpfCnpj(cliente.getCpf());
        } catch (Exception e) {
            this.dadosCliente = cliente.getNome() + "\n CPF: " + cliente.getCpf();
        }
    }

    /*
    A BARATEIRA - SAO BENTO DO SUL
    AVENIDA ARGOLO, 86, CENTRO
    SÃO BENTO DO SUL / SC CEP: 89280
    (47)3635-3455
    CNPJ:08.279.898/0001-90
     */

    public void setDadosLoja(LojaBean loja) {
        this.dadosLoja = loja.getNomeFantasia();
        if (loja.getEndereco() != null && loja.getEndereco().getEndereco() != null) {
            this.dadosLoja += "\n" + loja.getEndereco().getEndereco().getLogradouro();
            if(loja.getEndereco().getEndereco().getNumero() != null) {
                this.dadosLoja += ", " + loja.getEndereco().getEndereco().getNumero();
            }
            this.dadosLoja += "\n" + loja.getEndereco().getEndereco().getBairro();
            if (this.nomeCidade != null) {
                this.dadosLoja += " - " + this.nomeCidade;
            }
            this.dadosLoja += "/" + loja.getEndereco().getEndereco().getEstado();
            this.dadosLoja += "\nCEP: " + loja.getEndereco().getEndereco().getCep();
        }
        this.dadosLoja += "\nTEL: " + loja.getTelComercial();
        this.dadosLoja += "\nCNPJ: " + loja.getCnpj();
    }

    public void setParcelas(List<ParcelaBean> parcelasBean) {
        this.parcelas = parcelasBean.stream()
                .filter(parcela -> parcela.getNumero() != Constants.PARCELA_ENTRADA && !parcela.getStatus().equals(EnumStatusParcela.CANCELADA))
                .map(parcela -> new ParcelaReciboVendaDTO(parcela.getValor(), parcela.getNumero(), parcela.getDataVencimento()))
                .sorted(Comparator.comparingInt(ParcelaReciboVendaDTO::getNumero))
                .collect(Collectors.toList());
    }

}
