
package br.com.betha.e_nota_contribuinte_ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java de tcInfRps complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcInfRps"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IdentificacaoRps" type="{http://www.betha.com.br/e-nota-contribuinte-ws}tcIdentificacaoRps"/&gt;
 *         &lt;element name="DataEmissao" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="NaturezaOperacao" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="RegimeEspecialTributacao" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="OptanteSimplesNacional" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IncentivadorCultural" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="RpsSubstituido" type="{http://www.betha.com.br/e-nota-contribuinte-ws}tcIdentificacaoRps" minOccurs="0"/&gt;
 *         &lt;element name="Servico" type="{http://www.betha.com.br/e-nota-contribuinte-ws}tcDadosServico"/&gt;
 *         &lt;element name="Prestador" type="{http://www.betha.com.br/e-nota-contribuinte-ws}tcIdentificacaoPrestador"/&gt;
 *         &lt;element name="Tomador" type="{http://www.betha.com.br/e-nota-contribuinte-ws}tcDadosTomador"/&gt;
 *         &lt;element name="IntermediarioServico" type="{http://www.betha.com.br/e-nota-contribuinte-ws}tcIdentificacaoIntermediarioServico" minOccurs="0"/&gt;
 *         &lt;element name="ConstrucaoCivil" type="{http://www.betha.com.br/e-nota-contribuinte-ws}tcDadosConstrucaoCivil" minOccurs="0"/&gt;
 *         &lt;element name="OutrasInformacoes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CondicaoPagamento" type="{http://www.betha.com.br/e-nota-contribuinte-ws}tcCondicoesPagamentos" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcInfRps", propOrder = {
    "identificacaoRps",
    "dataEmissao",
    "naturezaOperacao",
    "regimeEspecialTributacao",
    "optanteSimplesNacional",
    "incentivadorCultural",
    "status",
    "rpsSubstituido",
    "servico",
    "prestador",
    "tomador",
    "intermediarioServico",
    "construcaoCivil",
    "outrasInformacoes",
    "condicaoPagamento"
})
public class TcInfRps {

    @XmlElement(name = "IdentificacaoRps", required = true)
    protected TcIdentificacaoRps identificacaoRps;
    @XmlElement(name = "DataEmissao", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataEmissao;
    @XmlElement(name = "NaturezaOperacao")
    protected int naturezaOperacao;
    @XmlElement(name = "RegimeEspecialTributacao")
    protected Integer regimeEspecialTributacao;
    @XmlElement(name = "OptanteSimplesNacional")
    protected int optanteSimplesNacional;
    @XmlElement(name = "IncentivadorCultural")
    protected int incentivadorCultural;
    @XmlElement(name = "Status")
    protected int status;
    @XmlElement(name = "RpsSubstituido")
    protected TcIdentificacaoRps rpsSubstituido;
    @XmlElement(name = "Servico", required = true)
    protected TcDadosServico servico;
    @XmlElement(name = "Prestador", required = true)
    protected TcIdentificacaoPrestador prestador;
    @XmlElement(name = "Tomador", required = true)
    protected TcDadosTomador tomador;
    @XmlElement(name = "IntermediarioServico")
    protected TcIdentificacaoIntermediarioServico intermediarioServico;
    @XmlElement(name = "ConstrucaoCivil")
    protected TcDadosConstrucaoCivil construcaoCivil;
    @XmlElement(name = "OutrasInformacoes")
    protected String outrasInformacoes;
    @XmlElement(name = "CondicaoPagamento")
    protected List<TcCondicoesPagamentos> condicaoPagamento;
    @XmlAttribute(name = "Id")
    protected String id;

    /**
     * Obtém o valor da propriedade identificacaoRps.
     * 
     * @return
     *     possible object is
     *     {@link TcIdentificacaoRps }
     *     
     */
    public TcIdentificacaoRps getIdentificacaoRps() {
        return identificacaoRps;
    }

    /**
     * Define o valor da propriedade identificacaoRps.
     * 
     * @param value
     *     allowed object is
     *     {@link TcIdentificacaoRps }
     *     
     */
    public void setIdentificacaoRps(TcIdentificacaoRps value) {
        this.identificacaoRps = value;
    }

    /**
     * Obtém o valor da propriedade dataEmissao.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEmissao() {
        return dataEmissao;
    }

    /**
     * Define o valor da propriedade dataEmissao.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEmissao(XMLGregorianCalendar value) {
        this.dataEmissao = value;
    }

    /**
     * Obtém o valor da propriedade naturezaOperacao.
     * 
     */
    public int getNaturezaOperacao() {
        return naturezaOperacao;
    }

    /**
     * Define o valor da propriedade naturezaOperacao.
     * 
     */
    public void setNaturezaOperacao(int value) {
        this.naturezaOperacao = value;
    }

    /**
     * Obtém o valor da propriedade regimeEspecialTributacao.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRegimeEspecialTributacao() {
        return regimeEspecialTributacao;
    }

    /**
     * Define o valor da propriedade regimeEspecialTributacao.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRegimeEspecialTributacao(Integer value) {
        this.regimeEspecialTributacao = value;
    }

    /**
     * Obtém o valor da propriedade optanteSimplesNacional.
     * 
     */
    public int getOptanteSimplesNacional() {
        return optanteSimplesNacional;
    }

    /**
     * Define o valor da propriedade optanteSimplesNacional.
     * 
     */
    public void setOptanteSimplesNacional(int value) {
        this.optanteSimplesNacional = value;
    }

    /**
     * Obtém o valor da propriedade incentivadorCultural.
     * 
     */
    public int getIncentivadorCultural() {
        return incentivadorCultural;
    }

    /**
     * Define o valor da propriedade incentivadorCultural.
     * 
     */
    public void setIncentivadorCultural(int value) {
        this.incentivadorCultural = value;
    }

    /**
     * Obtém o valor da propriedade status.
     * 
     */
    public int getStatus() {
        return status;
    }

    /**
     * Define o valor da propriedade status.
     * 
     */
    public void setStatus(int value) {
        this.status = value;
    }

    /**
     * Obtém o valor da propriedade rpsSubstituido.
     * 
     * @return
     *     possible object is
     *     {@link TcIdentificacaoRps }
     *     
     */
    public TcIdentificacaoRps getRpsSubstituido() {
        return rpsSubstituido;
    }

    /**
     * Define o valor da propriedade rpsSubstituido.
     * 
     * @param value
     *     allowed object is
     *     {@link TcIdentificacaoRps }
     *     
     */
    public void setRpsSubstituido(TcIdentificacaoRps value) {
        this.rpsSubstituido = value;
    }

    /**
     * Obtém o valor da propriedade servico.
     * 
     * @return
     *     possible object is
     *     {@link TcDadosServico }
     *     
     */
    public TcDadosServico getServico() {
        return servico;
    }

    /**
     * Define o valor da propriedade servico.
     * 
     * @param value
     *     allowed object is
     *     {@link TcDadosServico }
     *     
     */
    public void setServico(TcDadosServico value) {
        this.servico = value;
    }

    /**
     * Obtém o valor da propriedade prestador.
     * 
     * @return
     *     possible object is
     *     {@link TcIdentificacaoPrestador }
     *     
     */
    public TcIdentificacaoPrestador getPrestador() {
        return prestador;
    }

    /**
     * Define o valor da propriedade prestador.
     * 
     * @param value
     *     allowed object is
     *     {@link TcIdentificacaoPrestador }
     *     
     */
    public void setPrestador(TcIdentificacaoPrestador value) {
        this.prestador = value;
    }

    /**
     * Obtém o valor da propriedade tomador.
     * 
     * @return
     *     possible object is
     *     {@link TcDadosTomador }
     *     
     */
    public TcDadosTomador getTomador() {
        return tomador;
    }

    /**
     * Define o valor da propriedade tomador.
     * 
     * @param value
     *     allowed object is
     *     {@link TcDadosTomador }
     *     
     */
    public void setTomador(TcDadosTomador value) {
        this.tomador = value;
    }

    /**
     * Obtém o valor da propriedade intermediarioServico.
     * 
     * @return
     *     possible object is
     *     {@link TcIdentificacaoIntermediarioServico }
     *     
     */
    public TcIdentificacaoIntermediarioServico getIntermediarioServico() {
        return intermediarioServico;
    }

    /**
     * Define o valor da propriedade intermediarioServico.
     * 
     * @param value
     *     allowed object is
     *     {@link TcIdentificacaoIntermediarioServico }
     *     
     */
    public void setIntermediarioServico(TcIdentificacaoIntermediarioServico value) {
        this.intermediarioServico = value;
    }

    /**
     * Obtém o valor da propriedade construcaoCivil.
     * 
     * @return
     *     possible object is
     *     {@link TcDadosConstrucaoCivil }
     *     
     */
    public TcDadosConstrucaoCivil getConstrucaoCivil() {
        return construcaoCivil;
    }

    /**
     * Define o valor da propriedade construcaoCivil.
     * 
     * @param value
     *     allowed object is
     *     {@link TcDadosConstrucaoCivil }
     *     
     */
    public void setConstrucaoCivil(TcDadosConstrucaoCivil value) {
        this.construcaoCivil = value;
    }

    /**
     * Obtém o valor da propriedade outrasInformacoes.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutrasInformacoes() {
        return outrasInformacoes;
    }

    /**
     * Define o valor da propriedade outrasInformacoes.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutrasInformacoes(String value) {
        this.outrasInformacoes = value;
    }

    /**
     * Gets the value of the condicaoPagamento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the condicaoPagamento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCondicaoPagamento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TcCondicoesPagamentos }
     * 
     * 
     */
    public List<TcCondicoesPagamentos> getCondicaoPagamento() {
        if (condicaoPagamento == null) {
            condicaoPagamento = new ArrayList<TcCondicoesPagamentos>();
        }
        return this.condicaoPagamento;
    }

    /**
     * Obtém o valor da propriedade id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Define o valor da propriedade id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
