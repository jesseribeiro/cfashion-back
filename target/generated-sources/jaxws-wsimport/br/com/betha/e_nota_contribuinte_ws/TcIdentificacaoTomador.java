
package br.com.betha.e_nota_contribuinte_ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de tcIdentificacaoTomador complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcIdentificacaoTomador"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CpfCnpj" type="{http://www.betha.com.br/e-nota-contribuinte-ws}tcCpfCnpj" minOccurs="0"/&gt;
 *         &lt;element name="InscricaoMunicipal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InscricaoEstadual" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcIdentificacaoTomador", propOrder = {
    "cpfCnpj",
    "inscricaoMunicipal",
    "inscricaoEstadual"
})
public class TcIdentificacaoTomador {

    @XmlElement(name = "CpfCnpj")
    protected TcCpfCnpj cpfCnpj;
    @XmlElement(name = "InscricaoMunicipal")
    protected String inscricaoMunicipal;
    @XmlElement(name = "InscricaoEstadual")
    protected String inscricaoEstadual;

    /**
     * Obtém o valor da propriedade cpfCnpj.
     * 
     * @return
     *     possible object is
     *     {@link TcCpfCnpj }
     *     
     */
    public TcCpfCnpj getCpfCnpj() {
        return cpfCnpj;
    }

    /**
     * Define o valor da propriedade cpfCnpj.
     * 
     * @param value
     *     allowed object is
     *     {@link TcCpfCnpj }
     *     
     */
    public void setCpfCnpj(TcCpfCnpj value) {
        this.cpfCnpj = value;
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
     * Obtém o valor da propriedade inscricaoEstadual.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    /**
     * Define o valor da propriedade inscricaoEstadual.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInscricaoEstadual(String value) {
        this.inscricaoEstadual = value;
    }

}
