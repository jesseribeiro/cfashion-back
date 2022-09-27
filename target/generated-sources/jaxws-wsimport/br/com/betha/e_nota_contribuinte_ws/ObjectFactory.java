
package br.com.betha.e_nota_contribuinte_ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the br.com.betha.e_nota_contribuinte_ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ConsultarSituacaoLoteRpsEnvio_QNAME = new QName("http://www.betha.com.br/e-nota-contribuinte-ws", "ConsultarSituacaoLoteRpsEnvio");
    private final static QName _ConsultarSituacaoLoteRpsEnvioResponse_QNAME = new QName("http://www.betha.com.br/e-nota-contribuinte-ws", "ConsultarSituacaoLoteRpsEnvioResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: br.com.betha.e_nota_contribuinte_ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConsultarSituacaoLoteRpsEnvio }
     * 
     */
    public ConsultarSituacaoLoteRpsEnvio createConsultarSituacaoLoteRpsEnvio() {
        return new ConsultarSituacaoLoteRpsEnvio();
    }

    /**
     * Create an instance of {@link ConsultarSituacaoLoteRpsEnvioResponse }
     * 
     */
    public ConsultarSituacaoLoteRpsEnvioResponse createConsultarSituacaoLoteRpsEnvioResponse() {
        return new ConsultarSituacaoLoteRpsEnvioResponse();
    }

    /**
     * Create an instance of {@link TcIdentificacaoPrestador }
     * 
     */
    public TcIdentificacaoPrestador createTcIdentificacaoPrestador() {
        return new TcIdentificacaoPrestador();
    }

    /**
     * Create an instance of {@link ConsultarSituacaoLoteRpsResposta }
     * 
     */
    public ConsultarSituacaoLoteRpsResposta createConsultarSituacaoLoteRpsResposta() {
        return new ConsultarSituacaoLoteRpsResposta();
    }

    /**
     * Create an instance of {@link TcListaMensagemRetorno }
     * 
     */
    public TcListaMensagemRetorno createTcListaMensagemRetorno() {
        return new TcListaMensagemRetorno();
    }

    /**
     * Create an instance of {@link TcMensagemRetorno }
     * 
     */
    public TcMensagemRetorno createTcMensagemRetorno() {
        return new TcMensagemRetorno();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultarSituacaoLoteRpsEnvio }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ConsultarSituacaoLoteRpsEnvio }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.betha.com.br/e-nota-contribuinte-ws", name = "ConsultarSituacaoLoteRpsEnvio")
    public JAXBElement<ConsultarSituacaoLoteRpsEnvio> createConsultarSituacaoLoteRpsEnvio(ConsultarSituacaoLoteRpsEnvio value) {
        return new JAXBElement<ConsultarSituacaoLoteRpsEnvio>(_ConsultarSituacaoLoteRpsEnvio_QNAME, ConsultarSituacaoLoteRpsEnvio.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultarSituacaoLoteRpsEnvioResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ConsultarSituacaoLoteRpsEnvioResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.betha.com.br/e-nota-contribuinte-ws", name = "ConsultarSituacaoLoteRpsEnvioResponse")
    public JAXBElement<ConsultarSituacaoLoteRpsEnvioResponse> createConsultarSituacaoLoteRpsEnvioResponse(ConsultarSituacaoLoteRpsEnvioResponse value) {
        return new JAXBElement<ConsultarSituacaoLoteRpsEnvioResponse>(_ConsultarSituacaoLoteRpsEnvioResponse_QNAME, ConsultarSituacaoLoteRpsEnvioResponse.class, null, value);
    }

}
