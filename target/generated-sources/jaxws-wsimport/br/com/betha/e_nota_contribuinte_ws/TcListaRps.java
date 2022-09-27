
package br.com.betha.e_nota_contribuinte_ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de tcListaRps complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcListaRps"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Rps" type="{http://www.betha.com.br/e-nota-contribuinte-ws}tcRps" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcListaRps", propOrder = {
    "rps"
})
public class TcListaRps {

    @XmlElement(name = "Rps", required = true)
    protected List<TcRps> rps;

    /**
     * Gets the value of the rps property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rps property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRps().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TcRps }
     * 
     * 
     */
    public List<TcRps> getRps() {
        if (rps == null) {
            rps = new ArrayList<TcRps>();
        }
        return this.rps;
    }

}
