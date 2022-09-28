package br.com.crista.fashion.dto;

import br.com.crista.fashion.bean.ProdutoBean;
import br.com.crista.fashion.enumeration.EnumCategoria;
import br.com.crista.fashion.enumeration.EnumTamanho;
import br.com.crista.fashion.json.MoneyDeserializerJson;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.math.BigDecimal;
import java.util.Calendar;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoDTO extends GenericDTO<ProdutoBean> {

    // filtros
    private String dataInicial;
    private String dataFinal;

    private Calendar dataCadastro;
    private String nome;
    private String tamanho;
    private String categoria;
    private String cor;
    private String codigo;
    private Integer qtd;

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal valorProduto;

    @JsonDeserialize(converter = MoneyDeserializerJson.class)
    private BigDecimal valorCompra;

    public ProdutoDTO(ProdutoBean bean) {
        super(bean);
        dataCadastro = bean.getDataCadastro();
        nome = bean.getNome();
        if (bean.getTamanho() != null) {
            tamanho = (bean.getTamanho()).getLabel();
        }
        if (bean.getCategoria() != null) {
            categoria = bean.getCategoria().toString();
        }
        cor = bean.getCor();
        codigo = bean.getCodigo();
        qtd = bean.getQtd();
        valorProduto = bean.getValorProduto();
        valorCompra = bean.getValorCompra();
    }

    @Override
    public ProdutoBean converter(ProdutoBean bean) {
        bean = super.converter(bean);
        bean.setNome(nome);
        bean.setTamanho(EnumTamanho.valueOf(tamanho));
        bean.setCategoria(EnumCategoria.valueOf(categoria));
        bean.setCor(cor);
        bean.setCodigo(codigo);
        bean.setQtd(qtd);
        bean.setValorProduto(valorProduto);
        bean.setValorCompra(valorCompra);
        return bean;
    }
}
