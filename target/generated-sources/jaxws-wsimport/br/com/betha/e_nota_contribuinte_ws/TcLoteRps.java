
package br.com.betha.e_nota_contribuinte_ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de tcLoteRps complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcLoteRps"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NumeroLote" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="Cnpj" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InscricaoMunicipal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="QuantidadeRps" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="CodigoMunicipio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ListaRps" type="{http://www.betha.com.br/e-nota-contribuinte-ws}tcListaRps"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcLoteRps", propOrder = {
    "numeroLote",
    "cnpj",
    "inscricaoMunicipal",
    "quantidadeRps",
    "codigoMunicipio",
    "listaRps"
})
public class TcLoteRps {

    @XmlElement(name = "NumeroLote")
    protected long numeroLote;
    @XmlElement(name = "Cnpj", required = true)
    protected String cnpj;
    @XmlElement(name = "InscricaoMunicipal")
    protected String inscricaoMunicipal;
    @XmlElement(name = "QuantidadeRps")
    protected int quantidadeRps;
    @XmlElement(name = "CodigoMunicipio")
    protected String codigoMunicipio;
    @XmlElement(name = "ListaRps", required = true)
    protected TcListaRps listaRps;
    @XmlAttribute(name = "Id", required = true)
    protected String id;

    /**
     * Obtém o valor da propriedade numeroLote.
     * 
     */
    public long getNumeroLote() {
        return numeroLote;
    }

    /**
     * Define o valor da propriedade numeroLote.
     * 
     */
    public void setNumeroLote(long value) {
        this.numeroLote = value;
    }

    /**
     * Obtém o valor da propriedade cnpj.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * Define o valor da propriedade cnpj.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCnpj(String value) {
        this.cnpj = value;
    }

    /**
     * Obtém o valor da propriedade inscricaoMunicipal.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    /**
     * Define o valor da propriedade inscricaoMunicipal.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInscricaoMunicipal(String value) {
        this.inscricaoMunicipal = value;
    }

    /**
     * Obtém o valor da propriedade quantidadeRps.
     * 
     */
    public int getQuantidadeRps() {
        return quantidadeRps;
    }

    /**
     * Define o valor da propriedade quantidadeRps.
     * 
     */
    public void setQuantidadeRps(int value) {
        this.quantidadeRps = value;
    }

    /**
     * Obtém o valor da propriedade codigoMunicipio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoMunicipio() {
        return codigoMunicipio;
    }

    /**
     * Define o valor da propriedade codigoMunicipio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoMunicipio(String value) {
        this.codigoMunicipio = value;
    }

    /**
     * Obtém o valor da propriedade listaRps.
     * 
     * @return
     *     possible object is
     *     {@link TcListaRps }
     *     
     */
    public TcListaRps getListaRps() {
        return listaRps;
    }

    /**
     * Define o valor da propriedade listaRps.
     * 
     * @param value
     *     allowed object is
     *     {@link TcListaRps }
     *     
     */
    public void setListaRps(TcListaRps value) {
        this.listaRps = value;
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
