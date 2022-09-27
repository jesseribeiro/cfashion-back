
package br.com.betha.e_nota_contribuinte_ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.w3._2000._09.xmldsig_.TcDigestMethod;


/**
 * <p>Classe Java de tcReference complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcReference"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Transforms"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}DigestMethod"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}DigestValue"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="URI" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcReference", propOrder = {
    "transforms",
    "digestMethod",
    "digestValue"
})
public class TcReference {

    @XmlElement(name = "Transforms", namespace = "http://www.w3.org/2000/09/xmldsig#", required = true)
    protected TcTransforms transforms;
    @XmlElement(name = "DigestMethod", namespace = "http://www.w3.org/2000/09/xmldsig#", required = true)
    protected TcDigestMethod digestMethod;
    @XmlElement(name = "DigestValue", namespace = "http://www.w3.org/2000/09/xmldsig#", required = true)
    protected String digestValue;
    @XmlAttribute(name = "URI", required = true)
    protected String uri;

    /**
     * Obtém o valor da propriedade transforms.
     * 
     * @return
     *     possible object is
     *     {@link TcTransforms }
     *     
     */
    public TcTransforms getTransforms() {
        return transforms;
    }

    /**
     * Define o valor da propriedade transforms.
     * 
     * @param value
     *     allowed object is
     *     {@link TcTransforms }
     *     
     */
    public void setTransforms(TcTransforms value) {
        this.transforms = value;
    }

    /**
     * Obtém o valor da propriedade digestMethod.
     * 
     * @return
     *     possible object is
     *     {@link TcDigestMethod }
     *     
     */
    public TcDigestMethod getDigestMethod() {
        return digestMethod;
    }

    /**
     * Define o valor da propriedade digestMethod.
     * 
     * @param value
     *     allowed object is
     *     {@link TcDigestMethod }
     *     
     */
    public void setDigestMethod(TcDigestMethod value) {
        this.digestMethod = value;
    }

    /**
     * Obtém o valor da propriedade digestValue.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDigestValue() {
        return digestValue;
    }

    /**
     * Define o valor da propriedade digestValue.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDigestValue(String value) {
        this.digestValue = value;
    }

    /**
     * Obtém o valor da propriedade uri.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getURI() {
        return uri;
    }

    /**
     * Define o valor da propriedade uri.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setURI(String value) {
        this.uri = value;
    }

}
