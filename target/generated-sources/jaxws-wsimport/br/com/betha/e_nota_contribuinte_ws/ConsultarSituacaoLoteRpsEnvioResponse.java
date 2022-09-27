
package br.com.betha.e_nota_contribuinte_ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ConsultarSituacaoLoteRpsEnvioResponse complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ConsultarSituacaoLoteRpsEnvioResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ConsultarSituacaoLoteRpsResposta" type="{http://www.betha.com.br/e-nota-contribuinte-ws}consultarSituacaoLoteRpsResposta" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsultarSituacaoLoteRpsEnvioResponse", propOrder = {
    "consultarSituacaoLoteRpsResposta"
})
public class ConsultarSituacaoLoteRpsEnvioResponse {

    @XmlElement(name = "ConsultarSituacaoLoteRpsResposta")
    protected ConsultarSituacaoLoteRpsResposta consultarSituacaoLoteRpsResposta;

    /**
     * Obtém o valor da propriedade consultarSituacaoLoteRpsResposta.
     * 
     * @return
     *     possible object is
     *     {@link ConsultarSituacaoLoteRpsResposta }
     *     
     */
    public ConsultarSituacaoLoteRpsResposta getConsultarSituacaoLoteRpsResposta() {
        return consultarSituacaoLoteRpsResposta;
    }

    /**
     * Define o valor da propriedade consultarSituacaoLoteRpsResposta.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsultarSituacaoLoteRpsResposta }
     *     
     */
    public void setConsultarSituacaoLoteRpsResposta(ConsultarSituacaoLoteRpsResposta value) {
        this.consultarSituacaoLoteRpsResposta = value;
    }

}
