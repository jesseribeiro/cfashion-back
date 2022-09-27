
package org.w3._2000._09.xmldsig_;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import br.com.betha.e_nota_contribuinte_ws.TcKeyInfo;
import br.com.betha.e_nota_contribuinte_ws.TcSigInfo;


/**
 * <p>Classe Java de Signature complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="Signature"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SignedInfo" type="{http://www.betha.com.br/e-nota-contribuinte-ws}tcSigInfo" form="qualified"/&gt;
 *         &lt;element name="SignatureValue" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/&gt;
 *         &lt;element name="KeyInfo" type="{http://www.betha.com.br/e-nota-contribuinte-ws}tcKeyInfo" form="qualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Signature", propOrder = {
    "signedInfo",
    "signatureValue",
    "keyInfo"
})
public class Signature {

    @XmlElement(name = "SignedInfo", required = true)
    protected TcSigInfo signedInfo;
    @XmlElement(name = "SignatureValue", required = true)
    protected String signatureValue;
    @XmlElement(name = "KeyInfo", required = true)
    protected TcKeyInfo keyInfo;

    /**
     * Obtém o valor da propriedade signedInfo.
     * 
     * @return
     *     possible object is
     *     {@link TcSigInfo }
     *     
     */
    public TcSigInfo getSignedInfo() {
        return signedInfo;
    }

    /**
     * Define o valor da propriedade signedInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link TcSigInfo }
     *     
     */
    public void setSignedInfo(TcSigInfo value) {
        this.signedInfo = value;
    }

    /**
     * Obtém o valor da propriedade signatureValue.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignatureValue() {
        return signatureValue;
    }

    /**
     * Define o valor da propriedade signatureValue.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignatureValue(String value) {
        this.signatureValue = value;
    }

    /**
     * Obtém o valor da propriedade keyInfo.
     * 
     * @return
     *     possible object is
     *     {@link TcKeyInfo }
     *     
     */
    public TcKeyInfo getKeyInfo() {
        return keyInfo;
    }

    /**
     * Define o valor da propriedade keyInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link TcKeyInfo }
     *     
     */
    public void setKeyInfo(TcKeyInfo value) {
        this.keyInfo = value;
    }

}
