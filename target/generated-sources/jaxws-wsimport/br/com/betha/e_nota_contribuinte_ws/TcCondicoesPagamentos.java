
package br.com.betha.e_nota_contribuinte_ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de tcCondicoesPagamentos complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcCondicoesPagamentos"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Condicao" type="{http://www.betha.com.br/e-nota-contribuinte-ws}Condicao"/&gt;
 *         &lt;element name="QtdParcela" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="Parcelas" type="{http://www.betha.com.br/e-nota-contribuinte-ws}tcParcelas" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcCondicoesPagamentos", propOrder = {
    "condicao",
    "qtdParcela",
    "parcelas"
})
public class TcCondicoesPagamentos {

    @XmlElement(name = "Condicao", required = true)
    @XmlSchemaType(name = "string")
    protected Condicao condicao;
    @XmlElement(name = "QtdParcela")
    protected Integer qtdParcela;
    @XmlElement(name = "Parcelas")
    protected List<TcParcelas> parcelas;

    /**
     * Obtém o valor da propriedade condicao.
     * 
     * @return
     *     possible object is
     *     {@link Condicao }
     *     
     */
    public Condicao getCondicao() {
        return condicao;
    }

    /**
     * Define o valor da propriedade condicao.
     * 
     * @param value
     *     allowed object is
     *     {@link Condicao }
     *     
     */
    public void setCondicao(Condicao value) {
        this.condicao = value;
    }

    /**
     * Obtém o valor da propriedade qtdParcela.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getQtdParcela() {
        return qtdParcela;
    }

    /**
     * Define o valor da propriedade qtdParcela.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setQtdParcela(Integer value) {
        this.qtdParcela = value;
    }

    /**
     * Gets the value of the parcelas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parcelas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParcelas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TcParcelas }
     * 
     * 
     */
    public List<TcParcelas> getParcelas() {
        if (parcelas == null) {
            parcelas = new ArrayList<TcParcelas>();
        }
        return this.parcelas;
    }

}
