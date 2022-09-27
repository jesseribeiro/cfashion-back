
package br.com.betha.e_nota_contribuinte_ws;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java de enviarLoteRpsResposta complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="enviarLoteRpsResposta"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NumeroLote" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="DataRecebimento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="Protocolo" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="ListaMensagemRetorno" type="{http://www.betha.com.br/e-nota-contribuinte-ws}tcListaMensagemRetorno" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "enviarLoteRpsResposta", propOrder = {
    "numeroLote",
    "dataRecebimento",
    "protocolo",
    "listaMensagemRetorno"
})
public class EnviarLoteRpsResposta {

    @XmlElement(name = "NumeroLote")
    protected BigInteger numeroLote;
    @XmlElement(name = "DataRecebimento")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRecebimento;
    @XmlElement(name = "Protocolo")
    protected BigInteger protocolo;
    @XmlElement(name = "ListaMensagemRetorno")
    protected TcListaMensagemRetorno listaMensagemRetorno;

    /**
     * Obtém o valor da propriedade numeroLote.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumeroLote() {
        return numeroLote;
    }

    /**
     * Define o valor da propriedade numeroLote.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumeroLote(BigInteger value) {
        this.numeroLote = value;
    }

    /**
     * Obtém o valor da propriedade dataRecebimento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRecebimento() {
        return dataRecebimento;
    }

    /**
     * Define o valor da propriedade dataRecebimento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRecebimento(XMLGregorianCalendar value) {
        this.dataRecebimento = value;
    }

    /**
     * Obtém o valor da propriedade protocolo.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getProtocolo() {
        return protocolo;
    }

    /**
     * Define o valor da propriedade protocolo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setProtocolo(BigInteger value) {
        this.protocolo = value;
    }

    /**
     * Obtém o valor da propriedade listaMensagemRetorno.
     * 
     * @return
     *     possible object is
     *     {@link TcListaMensagemRetorno }
     *     
     */
    public TcListaMensagemRetorno getListaMensagemRetorno() {
        return listaMensagemRetorno;
    }

    /**
     * Define o valor da propriedade listaMensagemRetorno.
     * 
     * @param value
     *     allowed object is
     *     {@link TcListaMensagemRetorno }
     *     
     */
    public void setListaMensagemRetorno(TcListaMensagemRetorno value) {
        this.listaMensagemRetorno = value;
    }

}
