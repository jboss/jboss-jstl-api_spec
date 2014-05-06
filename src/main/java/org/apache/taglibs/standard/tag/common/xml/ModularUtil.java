package org.apache.taglibs.standard.tag.common.xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author Tomaz Cerar (c) 2014 Red Hat Inc.
 */
public final class ModularUtil {

    private ModularUtil() {

    }

    public static TransformerFactory createTransformerFactory() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        TransformerFactory factory;
        try {
            Thread.currentThread().setContextClassLoader(XalanUtil.class.getClassLoader());
            factory = TransformerFactory.newInstance();
        } finally {
            Thread.currentThread().setContextClassLoader(cl);
        }
        return factory;
    }

    public static DocumentBuilderFactory createDocumentBuilderFactory() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        DocumentBuilderFactory factory;
        try {
            Thread.currentThread().setContextClassLoader(XalanUtil.class.getClassLoader());
            factory = DocumentBuilderFactory.newInstance();
        } finally {
            Thread.currentThread().setContextClassLoader(cl);
        }
        return factory;
    }

    public static SAXParserFactory createSAXParserFactory() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        SAXParserFactory factory;
        try {
            Thread.currentThread().setContextClassLoader(XalanUtil.class.getClassLoader());
            factory = SAXParserFactory.newInstance();
        } finally {
            Thread.currentThread().setContextClassLoader(cl);
        }
        return factory;
    }

    public static XMLReader createXMLReader() throws SAXException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        XMLReader reader;
        try {
            Thread.currentThread().setContextClassLoader(XalanUtil.class.getClassLoader());
            reader = XMLReaderFactory.createXMLReader();
        } finally {
            Thread.currentThread().setContextClassLoader(cl);
        }
        return reader;
    }
}
