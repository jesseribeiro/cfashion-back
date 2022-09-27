
package br.com.betha.e_nota_contribuinte_ws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de tcParcelas complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcParcelas"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Parcela" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="DataVencimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Valor" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcParcelas", propOrder = {
    "parcela",
    "dataVencimento",
    "valor"
})
public class TcParcelas {

    @XmlElement(name = "Parcela")
    protected int parcela;
    @XmlElement(name = "DataVencimento")
    protected String dataVencimento;
    @XmlElement(name = "Valor")
    protected BigDecimal valor;

    /**
     * Obtém o valor da propriedade parcela.
     * 
     */
    public int getParcela() {
        return parcela;
    }

    /**
     * Define o valor da propriedade parcela.
     * 
     */
    public void setParcela(int value) {
        this.parcela = value;
    }

    /**
     * Obtém o valor da propriedade dataVencimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataVencimento() {
        return dataVencimento;
    }

    /**
     * Define o valor da propriedade dataVencimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataVencimento(String value) {
        this.dataVencimento = value;
    }

    /**
     * Obtém o valor da propriedade valor.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * Define o valor da propriedade valor.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValor(BigDecimal value) {
        this.valor = value;
    }

}
