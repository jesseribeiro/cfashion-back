
package br.com.betha.e_nota_contribuinte_ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.w3._2000._09.xmldsig_.Signature;


/**
 * <p>Classe Java de tcRps complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcRps"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InfRps" type="{http://www.betha.com.br/e-nota-contribuinte-ws}tcInfRps"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcRps", propOrder = {
    "infRps",
    "signature"
})
public class TcRps {

    @XmlElement(name = "InfRps", required = true)
    protected TcInfRps infRps;
    @XmlElement(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#", required = true)
    protected Signature signature;

    /**
     * Obtém o valor da propriedade infRps.
     * 
     * @return
     *     possible object is
     *     {@link TcInfRps }
     *     
     */
    public TcInfRps getInfRps() {
        return infRps;
    }

    /**
     * Define o valor da propriedade infRps.
     * 
     * @param value
     *     allowed object is
     *     {@link TcInfRps }
     *     
     */
    public void setInfRps(TcInfRps value) {
        this.infRps = value;
    }

    /**
     * Obtém o valor da propriedade signature.
     * 
     * @return
     *     possible object is
     *     {@link Signature }
     *     
     */
    public Signature getSignature() {
        return signature;
    }

    /**
     * Define o valor da propriedade signature.
     * 
     * @param value
     *     allowed object is
     *     {@link Signature }
     *     
     */
    public void setSignature(Signature value) {
        this.signature = value;
    }

}
