
package br.com.betha.e_nota_contribuinte_ws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de Condicao.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * <p>
 * <pre>
 * &lt;simpleType name="Condicao"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="A_VISTA"/&gt;
 *     &lt;enumeration value="A_PRAZO"/&gt;
 *     &lt;enumeration value="NA_APRESENTACAO"/&gt;
 *     &lt;enumeration value="CARTAO_DEBITO"/&gt;
 *     &lt;enumeration value="CARTAO_CREDITO"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "Condicao")
@XmlEnum
public enum Condicao {

    A_VISTA,
    A_PRAZO,
    NA_APRESENTACAO,
    CARTAO_DEBITO,
    CARTAO_CREDITO;

    public String value() {
        return name();
    }

    public static Condicao fromValue(String v) {
        return valueOf(v);
    }

}
