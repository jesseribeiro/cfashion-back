package br.com.crista.fashion.utils;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.Writer;
import java.util.Iterator;

public class NoNamesWriter extends DelegatingXMLStreamWriter {

  private static final NamespaceContext emptyNamespaceContext = new NamespaceContext() {

    @Override
    public String getNamespaceURI(String prefix) {
      return "";
    }

    @Override
    public String getPrefix(String namespaceURI) {
      return "";
    }

    @Override
    public Iterator getPrefixes(String namespaceURI) {
      return null;
    }

  };

  public static XMLStreamWriter filter(Writer writer) throws XMLStreamException {
    return new NoNamesWriter(XMLOutputFactory.newInstance().createXMLStreamWriter(writer));
  }

  public NoNamesWriter(XMLStreamWriter writer) {
    super(writer);
  }

  @Override
  public NamespaceContext getNamespaceContext() {
    return emptyNamespaceContext;
  }

}