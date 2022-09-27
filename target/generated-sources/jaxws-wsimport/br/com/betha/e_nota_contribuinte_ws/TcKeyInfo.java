
package br.com.betha.e_nota_contribuinte_ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.w3._2000._09.xmldsig_.TcX509Data;


/**
 * <p>Classe Java de tcKeyInfo complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcKeyInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}X509Data"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcKeyInfo", propOrder = {
    "x509Data"
})
public class TcKeyInfo {

    @XmlElement(name = "X509Data", namespace = "http://www.w3.org/2000/09/xmldsig#", required = true)
    protected TcX509Data x509Data;

    /**
     * Obtém o valor da propriedade x509Data.
     * 
     * @return
     *     possible object is
     *     {@link TcX509Data }
     *     
     */
    public TcX509Data getX509Data() {
        return x509Data;
    }

    /**
     * Define o valor da propriedade x509Data.
     * 
     * @param value
     *     allowed object is
     *     {@link TcX509Data }
     *     
     */
    public void setX509Data(TcX509Data value) {
        this.x509Data = value;
    }

}
