
package br.com.betha.e_nota_contribuinte_ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.w3._2000._09.xmldsig_.TcCanonicalizationMethod;
import org.w3._2000._09.xmldsig_.TcSignatureMethod;


/**
 * <p>Classe Java de tcSigInfo complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcSigInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}CanonicalizationMethod"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}SignatureMethod"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Reference"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcSigInfo", propOrder = {
    "canonicalizationMethod",
    "signatureMethod",
    "reference"
})
public class TcSigInfo {

    @XmlElement(name = "CanonicalizationMethod", namespace = "http://www.w3.org/2000/09/xmldsig#", required = true)
    protected TcCanonicalizationMethod canonicalizationMethod;
    @XmlElement(name = "SignatureMethod", namespace = "http://www.w3.org/2000/09/xmldsig#", required = true)
    protected TcSignatureMethod signatureMethod;
    @XmlElement(name = "Reference", namespace = "http://www.w3.org/2000/09/xmldsig#", required = true)
    protected TcReference reference;

    /**
     * Obtém o valor da propriedade canonicalizationMethod.
     * 
     * @return
     *     possible object is
     *     {@link TcCanonicalizationMethod }
     *     
     */
    public TcCanonicalizationMethod getCanonicalizationMethod() {
        return canonicalizationMethod;
    }

    /**
     * Define o valor da propriedade canonicalizationMethod.
     * 
     * @param value
     *     allowed object is
     *     {@link TcCanonicalizationMethod }
     *     
     */
    public void setCanonicalizationMethod(TcCanonicalizationMethod value) {
        this.canonicalizationMethod = value;
    }

    /**
     * Obtém o valor da propriedade signatureMethod.
     * 
     * @return
     *     possible object is
     *     {@link TcSignatureMethod }
     *     
     */
    public TcSignatureMethod getSignatureMethod() {
        return signatureMethod;
    }

    /**
     * Define o valor da propriedade signatureMethod.
     * 
     * @param value
     *     allowed object is
     *     {@link TcSignatureMethod }
     *     
     */
    public void setSignatureMethod(TcSignatureMethod value) {
        this.signatureMethod = value;
    }

    /**
     * Obtém o valor da propriedade reference.
     * 
     * @return
     *     possible object is
     *     {@link TcReference }
     *     
     */
    public TcReference getReference() {
        return reference;
    }

    /**
     * Define o valor da propriedade reference.
     * 
     * @param value
     *     allowed object is
     *     {@link TcReference }
     *     
     */
    public void setReference(TcReference value) {
        this.reference = value;
    }

}
