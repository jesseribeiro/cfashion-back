
package br.com.betha.e_nota_contribuinte_ws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de tcValores complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcValores"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ValorServicos" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="ValorDeducoes" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="ValorPis" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="ValorCofins" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="ValorInss" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="ValorIr" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="ValorCsll" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="IssRetido" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="ValorIss" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="OutrasRetencoes" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="BaseCalculo" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="Aliquota" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="ValorLiquidoNfse" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="ValorIssRetido" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="DescontoCondicionado" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="DescontoIncondicionado" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcValores", propOrder = {
    "valorServicos",
    "valorDeducoes",
    "valorPis",
    "valorCofins",
    "valorInss",
    "valorIr",
    "valorCsll",
    "issRetido",
    "valorIss",
    "outrasRetencoes",
    "baseCalculo",
    "aliquota",
    "valorLiquidoNfse",
    "valorIssRetido",
    "descontoCondicionado",
    "descontoIncondicionado"
})
public class TcValores {

    @XmlElement(name = "ValorServicos", required = true)
    protected BigDecimal valorServicos;
    @XmlElement(name = "ValorDeducoes")
    protected BigDecimal valorDeducoes;
    @XmlElement(name = "ValorPis")
    protected BigDecimal valorPis;
    @XmlElement(name = "ValorCofins")
    protected BigDecimal valorCofins;
    @XmlElement(name = "ValorInss")
    protected BigDecimal valorInss;
    @XmlElement(name = "ValorIr")
    protected BigDecimal valorIr;
    @XmlElement(name = "ValorCsll")
    protected BigDecimal valorCsll;
    @XmlElement(name = "IssRetido")
    protected Integer issRetido;
    @XmlElement(name = "ValorIss")
    protected BigDecimal valorIss;
    @XmlElement(name = "OutrasRetencoes")
    protected BigDecimal outrasRetencoes;
    @XmlElement(name = "BaseCalculo", required = true)
    protected BigDecimal baseCalculo;
    @XmlElement(name = "Aliquota")
    protected BigDecimal aliquota;
    @XmlElement(name = "ValorLiquidoNfse")
    protected BigDecimal valorLiquidoNfse;
    @XmlElement(name = "ValorIssRetido")
    protected BigDecimal valorIssRetido;
    @XmlElement(name = "DescontoCondicionado")
    protected BigDecimal descontoCondicionado;
    @XmlElement(name = "DescontoIncondicionado")
    protected BigDecimal descontoIncondicionado;

    /**
     * Obtém o valor da propriedade valorServicos.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorServicos() {
        return valorServicos;
    }

    /**
     * Define o valor da propriedade valorServicos.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorServicos(BigDecimal value) {
        this.valorServicos = value;
    }

    /**
     * Obtém o valor da propriedade valorDeducoes.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorDeducoes() {
        return valorDeducoes;
    }

    /**
     * Define o valor da propriedade valorDeducoes.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorDeducoes(BigDecimal value) {
        this.valorDeducoes = value;
    }

    /**
     * Obtém o valor da propriedade valorPis.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorPis() {
        return valorPis;
    }

    /**
     * Define o valor da propriedade valorPis.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorPis(BigDecimal value) {
        this.valorPis = value;
    }

    /**
     * Obtém o valor da propriedade valorCofins.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorCofins() {
        return valorCofins;
    }

    /**
     * Define o valor da propriedade valorCofins.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorCofins(BigDecimal value) {
        this.valorCofins = value;
    }

    /**
     * Obtém o valor da propriedade valorInss.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorInss() {
        return valorInss;
    }

    /**
     * Define o valor da propriedade valorInss.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorInss(BigDecimal value) {
        this.valorInss = value;
    }

    /**
     * Obtém o valor da propriedade valorIr.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorIr() {
        return valorIr;
    }

    /**
     * Define o valor da propriedade valorIr.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorIr(BigDecimal value) {
        this.valorIr = value;
    }

    /**
     * Obtém o valor da propriedade valorCsll.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorCsll() {
        return valorCsll;
    }

    /**
     * Define o valor da propriedade valorCsll.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorCsll(BigDecimal value) {
        this.valorCsll = value;
    }

    /**
     * Obtém o valor da propriedade issRetido.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIssRetido() {
        return issRetido;
    }

    /**
     * Define o valor da propriedade issRetido.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIssRetido(Integer value) {
        this.issRetido = value;
    }

    /**
     * Obtém o valor da propriedade valorIss.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorIss() {
        return valorIss;
    }

    /**
     * Define o valor da propriedade valorIss.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorIss(BigDecimal value) {
        this.valorIss = value;
    }

    /**
     * Obtém o valor da propriedade outrasRetencoes.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOutrasRetencoes() {
        return outrasRetencoes;
    }

    /**
     * Define o valor da propriedade outrasRetencoes.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOutrasRetencoes(BigDecimal value) {
        this.outrasRetencoes = value;
    }

    /**
     * Obtém o valor da propriedade baseCalculo.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBaseCalculo() {
        return baseCalculo;
    }

    /**
     * Define o valor da propriedade baseCalculo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBaseCalculo(BigDecimal value) {
        this.baseCalculo = value;
    }

    /**
     * Obtém o valor da propriedade aliquota.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAliquota() {
        return aliquota;
    }

    /**
     * Define o valor da propriedade aliquota.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAliquota(BigDecimal value) {
        this.aliquota = value;
    }

    /**
     * Obtém o valor da propriedade valorLiquidoNfse.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorLiquidoNfse() {
        return valorLiquidoNfse;
    }

    /**
     * Define o valor da propriedade valorLiquidoNfse.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorLiquidoNfse(BigDecimal value) {
        this.valorLiquidoNfse = value;
    }

    /**
     * Obtém o valor da propriedade valorIssRetido.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorIssRetido() {
        return valorIssRetido;
    }

    /**
     * Define o valor da propriedade valorIssRetido.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorIssRetido(BigDecimal value) {
        this.valorIssRetido = value;
    }

    /**
     * Obtém o valor da propriedade descontoCondicionado.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDescontoCondicionado() {
        return descontoCondicionado;
    }

    /**
     * Define o valor da propriedade descontoCondicionado.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDescontoCondicionado(BigDecimal value) {
        this.descontoCondicionado = value;
    }

    /**
     * Obtém o valor da propriedade descontoIncondicionado.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDescontoIncondicionado() {
        return descontoIncondicionado;
    }

    /**
     * Define o valor da propriedade descontoIncondicionado.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDescontoIncondicionado(BigDecimal value) {
        this.descontoIncondicionado = value;
    }

}
