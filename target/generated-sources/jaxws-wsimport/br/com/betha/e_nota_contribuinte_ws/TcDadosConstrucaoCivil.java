
package br.com.betha.e_nota_contribuinte_ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de tcDadosConstrucaoCivil complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcDadosConstrucaoCivil"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CodigoObra" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Art" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcDadosConstrucaoCivil", propOrder = {
    "codigoObra",
    "art"
})
public class TcDadosConstrucaoCivil {

    @XmlElement(name = "CodigoObra", required = true)
    protected String codigoObra;
    @XmlElement(name = "Art", required = true)
    protected String art;

    /**
     * Obtém o valor da propriedade codigoObra.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoObra() {
        return codigoObra;
    }

    /**
     * Define o valor da propriedade codigoObra.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoObra(String value) {
        this.codigoObra = value;
    }

    /**
     * Obtém o valor da propriedade art.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArt() {
        return art;
    }

    /**
     * Define o valor da propriedade art.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArt(String value) {
        this.art = value;
    }

}
