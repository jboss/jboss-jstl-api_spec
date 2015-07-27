/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc., and individual contributors as indicated
 * by the @authors tag.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
