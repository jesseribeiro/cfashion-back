
package br.com.betha.e_nota_contribuinte_ws;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de consultarSituacaoLoteRpsResposta complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="consultarSituacaoLoteRpsResposta"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NumeroLote" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="Situacao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "consultarSituacaoLoteRpsResposta", propOrder = {
    "numeroLote",
    "situacao",
    "listaMensagemRetorno"
})
public class ConsultarSituacaoLoteRpsResposta {

    @XmlElement(name = "NumeroLote")
    protected BigInteger numeroLote;
    @XmlElement(name = "Situacao")
    protected String situacao;
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
     * Obtém o valor da propriedade situacao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSituacao() {
        return situacao;
    }

    /**
     * Define o valor da propriedade situacao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSituacao(String value) {
        this.situacao = value;
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
