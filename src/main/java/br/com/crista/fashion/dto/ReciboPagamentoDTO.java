package br.com.crista.fashion.dto;

import br.com.crista.fashion.bean.ClienteBean;
import br.com.crista.fashion.bean.LojaBean;
import br.com.crista.fashion.utils.StringUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
public class ReciboPagamentoDTO {

    private boolean flgCancelado;
    private String dadosLoja;
    private String dadosCliente;
    private String numeroParcela;
    private Calendar vencimento;
    private Long pagamentoId;
    private String dataPagto;
    private BigDecimal valorPago;
    private String nomeCidade;

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
            if(this.nomeCidade != null) {
                this.dadosLoja += " - " + this.nomeCidade;
            }
            this.dadosLoja += "/" + loja.getEndereco().getEndereco().getEstado();
            this.dadosLoja += "\nCEP: " + loja.getEndereco().getEndereco().getCep();
        }
        this.dadosLoja += "\nTEL: " + loja.getTelComercial();
        this.dadosLoja += "\nCNPJ: " + loja.getCnpj();
    }
}
