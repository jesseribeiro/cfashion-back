package br.com.crista.fashion.utils;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Slf4j
public class TratadorCertificado {
	/*
	 * 	Classe para tratamento de certificados. Deve fazer a manipulacao
	 * dos certificados exportando chaves, assinando XML's e demais funcoes.
	 *
	 */
	private static final String PROVIDER_CLASS_NAME = "org.jcp.xml.dsig.internal.dom.XMLDSigRI";
	private static final String PROVIDER_NAME = "jsr105Provider";

	public static final String algoritmo= "RSA"; 
	public static final String algoritmoAssinatura= "MD5withRSA"; 
	private static final String C14N_TRANSFORM_METHOD = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";

	private static File file =  null;
	private static String alias= "";
	private static char[] senha= "".toCharArray();

	static XMLSignatureFactory sig;
	static X509Certificate cert;
	static KeyInfo ki; 
	static SignedInfo si;
	static KeyStore rep;
	
	public static PrivateKey getChavePrivada() throws Exception{
				
		InputStream entrada= new FileInputStream(file);
		rep.load(entrada, senha);
		entrada.close();
		Key chavePrivada= (Key) rep.getKey(alias, senha);
		if(chavePrivada instanceof PrivateKey){
			log.info("Chave Privada encontrada!");
			return (PrivateKey) chavePrivada;
		}
		return null;		
	}
	
	public static PublicKey getChavePublica() throws Exception{
			
		InputStream entrada= new FileInputStream(file);
		rep.load(entrada, senha);
		entrada.close();
		Key chave= (Key) rep.getKey(alias, senha);		
		Certificate cert= (Certificate) rep.getCertificate(alias);//O tipo de dado é declarado desse modo por haver ambiguidade (Classes assinadas com o mesmo nome "Certificate")
		PublicKey chavePublica= cert.getPublicKey();
		log.info("Chave Pública encontrada!");
		return chavePublica;		
	}
	
	public static boolean verificarAssinatura(PublicKey chave, byte[] buffer, byte[] assinado) throws Exception{
		
		Signature assinatura= Signature.getInstance(algoritmoAssinatura);
		assinatura.initVerify(chave);
		assinatura.update(buffer, 0, buffer.length);
		return assinatura.verify(assinado);
	}
	
	public static byte[] criarAssinatura(PrivateKey chavePrivada, byte[] buffer) throws Exception{
		
		Signature assinatura= Signature.getInstance(algoritmoAssinatura);		
		assinatura.initSign(chavePrivada);
		assinatura.update(buffer, 0, buffer.length);
		return assinatura.sign();
	}
	
	public static String getValidade(X509Certificate cert){
		try{
			cert.checkValidity();
			return "Certificado válido!";
		}
		catch(CertificateExpiredException e){
			return "Certificado expirado!";
		}
		catch(CertificateNotYetValidException e){
			return "Certificado inválido!";
		}		
	}	
	
	public static X509Certificate getCertificado(File _file, String _senha) throws Exception{
		file = _file;
		senha = _senha.toCharArray();
		InputStream dado= new FileInputStream(file);		
		rep = KeyStore.getInstance("JKS");
		rep.load(dado, senha);
		alias = rep.aliases().nextElement();
		cert = (X509Certificate) rep.getCertificate(alias);
		String retorno= TratadorCertificado.getValidade(cert);
		log.info(retorno);
		return cert;
	}	

	/*
		Byte inv�lido 2 da sequ�ncia UTF-8 do byte 3
		https://www.javaavancado.com/byte-invalido-2-da-sequencia-utf-8-do-byte-3/
	 */
	public static String assinarRps(String xml, boolean isFile, File certificado, String senha) throws Exception {
		getCertificado(certificado, senha);

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		Document doc;
		if( isFile ) {
			doc = dbf.newDocumentBuilder().parse(new FileInputStream(xml));
			log.info("File Documento ok!");
		} else {
			log.info("XML: " + xml);
			InputStream is = new ByteArrayInputStream(xml.getBytes());
			Reader reader = new InputStreamReader(is,"UTF-8");
			InputSource io = new InputSource(reader);
			io.setEncoding("UTF-8");
			doc = dbf.newDocumentBuilder().parse(io);
			log.info("XML Documento ok!");
		}
		
		sig = XMLSignatureFactory.getInstance("DOM");
		
		ArrayList<Transform> transformList= new ArrayList<Transform>();
		Transform enveloped= sig.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null);
		Transform c14n= sig.newTransform(C14N_TRANSFORM_METHOD, (TransformParameterSpec) null);
		transformList.add(enveloped);
		transformList.add(c14n);

		Reference ref= sig.newReference
		 ("", sig.newDigestMethod(DigestMethod.SHA1, null),
				  transformList,
				     null, null);
		si = sig.newSignedInfo(
			sig.newCanonicalizationMethod
				(CanonicalizationMethod.INCLUSIVE,
					(C14NMethodParameterSpec) null),
						sig.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
						Collections.singletonList(ref)
		);
		
		KeyInfoFactory kif = sig.getKeyInfoFactory();
		List x509Content = new ArrayList();		
		x509Content.add(cert);
		X509Data xd = kif.newX509Data(x509Content);
		ki = kif.newKeyInfo(Collections.singletonList(xd));		
		
		DOMSignContext dsc = new DOMSignContext(getChavePrivada(), doc.getDocumentElement());
		XMLSignature signature = sig.newXMLSignature(si, ki);
		signature.sign(dsc);


		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(os));

		// <?xml version="1.0" encoding="ISO-8859-1"?> 43
		// <?xml version="1.0" encoding="UTF-8" standalone="no"?> 54
		String xmlRetorno = os.toString().substring(43);
		log.info(xmlRetorno);

		return xmlRetorno;
	}

	public static String assinarLote(String xml, File certificado, String senha, String id)
			throws Exception {

		getCertificado(certificado, senha);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));

		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

		ArrayList<Object> transformList = new ArrayList<Object>();
		TransformParameterSpec tps = null;
		Transform envelopedTransform = fac.newTransform(Transform.ENVELOPED, tps);
		transformList.add(envelopedTransform);
		Transform c14n = fac.newTransform("http://www.w3.org/TR/2001/REC-xml-c14n-20010315", tps);
		transformList.add(c14n);

		Reference ref = fac.newReference("", fac.newDigestMethod(DigestMethod.SHA1, null),
				transformList, null, null);

		SignedInfo si = fac
				.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
						(C14NMethodParameterSpec) null), fac.newSignatureMethod(
						SignatureMethod.RSA_SHA1, null), Collections.singletonList(ref));

		KeyInfoFactory kif = sig.getKeyInfoFactory();
		List x509Content = new ArrayList();
		x509Content.add(cert);
		X509Data xd = kif.newX509Data(x509Content);
		ki = kif.newKeyInfo(Collections.singletonList(xd));

		DOMSignContext dsc = new DOMSignContext
				(getChavePrivada(), doc.getDocumentElement());
		XMLSignature signature = sig.newXMLSignature(si, ki);
		signature.sign(dsc);

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(os));

		NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");

		if (nl.getLength() == 0) {
			throw new RuntimeException("Elemento da assinatura n�o encontrado no XML");
		}

		// <?xml version="1.0" encoding="UTF-8" standalone="no"?>
		return os.toString().substring(54);
	}

	/*
	public String assinarRps(String xml, KeyStore ks, String alias, String senha,
							 String nomeCertificado) throws Exception {
		//		KeyStore ks2 = KeyStore.getInstance("PKCS12");
		//		ks2.load(new FileInputStream("certificado/" + nomeCertificado),
		//		        senha.toCharArray());

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document docs = builder.parse(new ByteArrayInputStream(xml.getBytes()));

		NodeList elements = docs.getElementsByTagName("InfRps");
		Element el = (Element) elements.item(0);
		String id = el.getAttribute("Id");

		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

		ArrayList<Object> transformList = new ArrayList<Object>();
		TransformParameterSpec tps = null;
		Transform envelopedTransform = fac.newTransform(Transform.ENVELOPED, tps);
		transformList.add(envelopedTransform);
		Transform c14n = fac.newTransform("http://www.w3.org/TR/2001/REC-xml-c14n-20010315", tps);
		transformList.add(c14n);

		Reference ref = fac.newReference("#" + id, fac.newDigestMethod(DigestMethod.SHA1, null),
				transformList, null, null);

		SignedInfo si = fac
				.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE,
						(C14NMethodParameterSpec) null), fac.newSignatureMethod(
						SignatureMethod.RSA_SHA1, null), Collections.singletonList(ref));

		KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(alias,
				new KeyStore.PasswordProtection(senha.toCharArray()));

		X509Certificate cert = (X509Certificate) keyEntry.getCertificate();
		KeyInfoFactory kif = fac.getKeyInfoFactory();
		ArrayList<Object> x509Content = new ArrayList<Object>();
		x509Content.add(cert);
		X509Data xd = kif.newX509Data(x509Content);
		KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);

		DOMSignContext dsc = new DOMSignContext(keyEntry.getPrivateKey(),
				docs.getDocumentElement());
		XMLSignature signature = fac.newXMLSignature(si, ki);
		signature.sign(dsc);

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(docs), new StreamResult(os));

		NodeList nl = docs.getElementsByTagNameNS(XMLSignature.XMLNS,
				"Signature");

		if (nl.getLength() == 0) {
			throw new RuntimeException("Elemento da assinatura não encontrado no XML");
		}

		// <?xml version="1.0" encoding="ISO-8859-1"?>
		String xmlRetorno = os.toString().substring(43);

		return xmlRetorno;
	}
	*/
}