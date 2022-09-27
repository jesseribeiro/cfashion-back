
package br.com.betha.e_nota_contribuinte_ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de EnviarLoteRpsEnvioResponse complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="EnviarLoteRpsEnvioResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EnviarLoteRpsResposta" type="{http://www.betha.com.br/e-nota-contribuinte-ws}enviarLoteRpsResposta" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnviarLoteRpsEnvioResponse", propOrder = {
    "enviarLoteRpsResposta"
})
public class EnviarLoteRpsEnvioResponse {

    @XmlElement(name = "EnviarLoteRpsResposta")
    protected EnviarLoteRpsResposta enviarLoteRpsResposta;

    /**
     * Obtém o valor da propriedade enviarLoteRpsResposta.
     * 
     * @return
     *     possible object is
     *     {@link EnviarLoteRpsResposta }
     *     
     */
    public EnviarLoteRpsResposta getEnviarLoteRpsResposta() {
        return enviarLoteRpsResposta;
    }

    /**
     * Define o valor da propriedade enviarLoteRpsResposta.
     * 
     * @param value
     *     allowed object is
     *     {@link EnviarLoteRpsResposta }
     *     
     */
    public void setEnviarLoteRpsResposta(EnviarLoteRpsResposta value) {
        this.enviarLoteRpsResposta = value;
    }

}
