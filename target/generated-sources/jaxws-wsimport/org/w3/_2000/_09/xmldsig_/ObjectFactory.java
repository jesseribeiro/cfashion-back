
package org.w3._2000._09.xmldsig_;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import br.com.betha.e_nota_contribuinte_ws.TcReference;
import br.com.betha.e_nota_contribuinte_ws.TcTransform;
import br.com.betha.e_nota_contribuinte_ws.TcTransforms;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.w3._2000._09.xmldsig_ package. 
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

    private final static QName _CanonicalizationMethod_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "CanonicalizationMethod");
    private final static QName _DigestMethod_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "DigestMethod");
    private final static QName _DigestValue_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "DigestValue");
    private final static QName _Reference_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Reference");
    private final static QName _Signature_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Signature");
    private final static QName _SignatureMethod_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SignatureMethod");
    private final static QName _Transform_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Transform");
    private final static QName _Transforms_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Transforms");
    private final static QName _X509Data_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509Data");
    private final static QName _TcCanonicalizationMethod_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "tcCanonicalizationMethod");
    private final static QName _TcDigestMethod_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "tcDigestMethod");
    private final static QName _TcSignatureMethod_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "tcSignatureMethod");
    private final static QName _TcX509Data_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "tcX509Data");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.w3._2000._09.xmldsig_
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TcCanonicalizationMethod }
     * 
     */
    public TcCanonicalizationMethod createTcCanonicalizationMethod() {
        return new TcCanonicalizationMethod();
    }

    /**
     * Create an instance of {@link TcDigestMethod }
     * 
     */
    public TcDigestMethod createTcDigestMethod() {
        return new TcDigestMethod();
    }

    /**
     * Create an instance of {@link Signature }
     * 
     */
    public Signature createSignature() {
        return new Signature();
    }

    /**
     * Create an instance of {@link TcSignatureMethod }
     * 
     */
    public TcSignatureMethod createTcSignatureMethod() {
        return new TcSignatureMethod();
    }

    /**
     * Create an instance of {@link TcX509Data }
     * 
     */
    public TcX509Data createTcX509Data() {
        return new TcX509Data();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TcCanonicalizationMethod }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TcCanonicalizationMethod }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "CanonicalizationMethod")
    public JAXBElement<TcCanonicalizationMethod> createCanonicalizationMethod(TcCanonicalizationMethod value) {
        return new JAXBElement<TcCanonicalizationMethod>(_CanonicalizationMethod_QNAME, TcCanonicalizationMethod.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TcDigestMethod }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TcDigestMethod }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "DigestMethod")
    public JAXBElement<TcDigestMethod> createDigestMethod(TcDigestMethod value) {
        return new JAXBElement<TcDigestMethod>(_DigestMethod_QNAME, TcDigestMethod.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "DigestValue")
    public JAXBElement<String> createDigestValue(String value) {
        return new JAXBElement<String>(_DigestValue_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TcReference }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TcReference }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "Reference")
    public JAXBElement<TcReference> createReference(TcReference value) {
        return new JAXBElement<TcReference>(_Reference_QNAME, TcReference.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Signature }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Signature }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "Signature")
    public JAXBElement<Signature> createSignature(Signature value) {
        return new JAXBElement<Signature>(_Signature_QNAME, Signature.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TcSignatureMethod }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TcSignatureMethod }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "SignatureMethod")
    public JAXBElement<TcSignatureMethod> createSignatureMethod(TcSignatureMethod value) {
        return new JAXBElement<TcSignatureMethod>(_SignatureMethod_QNAME, TcSignatureMethod.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TcTransform }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TcTransform }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "Transform")
    public JAXBElement<TcTransform> createTransform(TcTransform value) {
        return new JAXBElement<TcTransform>(_Transform_QNAME, TcTransform.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TcTransforms }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TcTransforms }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "Transforms")
    public JAXBElement<TcTransforms> createTransforms(TcTransforms value) {
        return new JAXBElement<TcTransforms>(_Transforms_QNAME, TcTransforms.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TcX509Data }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TcX509Data }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "X509Data")
    public JAXBElement<TcX509Data> createX509Data(TcX509Data value) {
        return new JAXBElement<TcX509Data>(_X509Data_QNAME, TcX509Data.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TcCanonicalizationMethod }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TcCanonicalizationMethod }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "tcCanonicalizationMethod")
    public JAXBElement<TcCanonicalizationMethod> createTcCanonicalizationMethod(TcCanonicalizationMethod value) {
        return new JAXBElement<TcCanonicalizationMethod>(_TcCanonicalizationMethod_QNAME, TcCanonicalizationMethod.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TcDigestMethod }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TcDigestMethod }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "tcDigestMethod")
    public JAXBElement<TcDigestMethod> createTcDigestMethod(TcDigestMethod value) {
        return new JAXBElement<TcDigestMethod>(_TcDigestMethod_QNAME, TcDigestMethod.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TcSignatureMethod }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TcSignatureMethod }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "tcSignatureMethod")
    public JAXBElement<TcSignatureMethod> createTcSignatureMethod(TcSignatureMethod value) {
        return new JAXBElement<TcSignatureMethod>(_TcSignatureMethod_QNAME, TcSignatureMethod.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TcX509Data }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TcX509Data }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "tcX509Data")
    public JAXBElement<TcX509Data> createTcX509Data(TcX509Data value) {
        return new JAXBElement<TcX509Data>(_TcX509Data_QNAME, TcX509Data.class, null, value);
    }

}
