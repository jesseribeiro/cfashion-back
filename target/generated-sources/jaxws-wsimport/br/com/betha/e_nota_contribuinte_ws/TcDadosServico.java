
package br.com.betha.e_nota_contribuinte_ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de tcDadosServico complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcDadosServico"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Valores" type="{http://www.betha.com.br/e-nota-contribuinte-ws}tcValores"/&gt;
 *         &lt;element name="ItemListaServico" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CodigoCnae" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodigoTributacaoMunicipio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Discriminacao" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CodigoMunicipio" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="CodigoPais" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcDadosServico", propOrder = {
    "valores",
    "itemListaServico",
    "codigoCnae",
    "codigoTributacaoMunicipio",
    "discriminacao",
    "codigoMunicipio",
    "codigoPais"
})
public class TcDadosServico {

    @XmlElement(name = "Valores", required = true)
    protected TcValores valores;
    @XmlElement(name = "ItemListaServico", required = true)
    protected String itemListaServico;
    @XmlElement(name = "CodigoCnae")
    protected String codigoCnae;
    @XmlElement(name = "CodigoTributacaoMunicipio")
    protected String codigoTributacaoMunicipio;
    @XmlElement(name = "Discriminacao", required = true)
    protected String discriminacao;
    @XmlElement(name = "CodigoMunicipio")
    protected int codigoMunicipio;
    @XmlElement(name = "CodigoPais")
    protected String codigoPais;

    /**
     * Obtém o valor da propriedade valores.
     * 
     * @return
     *     possible object is
     *     {@link TcValores }
     *     
     */
    public TcValores getValores() {
        return valores;
    }

    /**
     * Define o valor da propriedade valores.
     * 
     * @param value
     *     allowed object is
     *     {@link TcValores }
     *     
     */
    public void setValores(TcValores value) {
        this.valores = value;
    }

    /**
     * Obtém o valor da propriedade itemListaServico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemListaServico() {
        return itemListaServico;
    }

    /**
     * Define o valor da propriedade itemListaServico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemListaServico(String value) {
        this.itemListaServico = value;
    }

    /**
     * Obtém o valor da propriedade codigoCnae.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoCnae() {
        return codigoCnae;
    }

    /**
     * Define o valor da propriedade codigoCnae.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoCnae(String value) {
        this.codigoCnae = value;
    }

    /**
     * Obtém o valor da propriedade codigoTributacaoMunicipio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoTributacaoMunicipio() {
        return codigoTributacaoMunicipio;
    }

    /**
     * Define o valor da propriedade codigoTributacaoMunicipio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoTributacaoMunicipio(String value) {
        this.codigoTributacaoMunicipio = value;
    }

    /**
     * Obtém o valor da propriedade discriminacao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscriminacao() {
        return discriminacao;
    }

    /**
     * Define o valor da propriedade discriminacao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscriminacao(String value) {
        this.discriminacao = value;
    }

    /**
     * Obtém o valor da propriedade codigoMunicipio.
     * 
     */
    public int getCodigoMunicipio() {
        return codigoMunicipio;
    }

    /**
     * Define o valor da propriedade codigoMunicipio.
     * 
     */
    public void setCodigoMunicipio(int value) {
        this.codigoMunicipio = value;
    }

    /**
     * Obtém o valor da propriedade codigoPais.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoPais() {
        return codigoPais;
    }

    /**
     * Define o valor da propriedade codigoPais.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoPais(String value) {
        this.codigoPais = value;
    }

}
