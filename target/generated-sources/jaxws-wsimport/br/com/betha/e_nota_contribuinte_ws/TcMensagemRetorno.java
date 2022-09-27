
package br.com.betha.e_nota_contribuinte_ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de tcMensagemRetorno complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcMensagemRetorno"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Mensagem" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Correcao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcMensagemRetorno", propOrder = {
    "codigo",
    "mensagem",
    "correcao"
})
public class TcMensagemRetorno {

    @XmlElement(name = "Codigo", required = true)
    protected String codigo;
    @XmlElement(name = "Mensagem", required = true)
    protected String mensagem;
    @XmlElement(name = "Correcao")
    protected String correcao;

    /**
     * Obtém o valor da propriedade codigo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Define o valor da propriedade codigo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigo(String value) {
        this.codigo = value;
    }

    /**
     * Obtém o valor da propriedade mensagem.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Define o valor da propriedade mensagem.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMensagem(String value) {
        this.mensagem = value;
    }

    /**
     * Obtém o valor da propriedade correcao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorrecao() {
        return correcao;
    }

    /**
     * Define o valor da propriedade correcao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrecao(String value) {
        this.correcao = value;
    }

}
