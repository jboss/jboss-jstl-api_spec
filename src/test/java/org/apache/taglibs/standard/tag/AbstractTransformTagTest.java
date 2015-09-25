/*
 * JBoss, Home of Professional Open Source.
 * Copyright (c) 2015, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.apache.taglibs.standard.tag;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.taglibs.standard.lang.jstl.test.PageContextImpl;
import org.apache.taglibs.standard.tag.common.xml.TransformSupport;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public abstract class AbstractTransformTagTest<T extends TransformSupport> {
    protected T tag;
    private StringWriter writer = new StringWriter();

    @Before
    public void before() {
        tag = createTag();
        tag.setPageContext(new PageContextImpl() {
            // TODO: maybe mock the JspWriter
            @Override
            public JspWriter getOut() {
                return new JspWriter(2048, true) {
                    @Override
                    public void newLine() throws IOException {
                        throw new RuntimeException("NYI: .newLine");
                    }

                    @Override
                    public void print(final boolean b) throws IOException {
                        throw new RuntimeException("NYI: .print");
                    }

                    @Override
                    public void print(final char c) throws IOException {
                        throw new RuntimeException("NYI: .print");
                    }

                    @Override
                    public void print(final int i) throws IOException {
                        throw new RuntimeException("NYI: .print");
                    }

                    @Override
                    public void print(final long l) throws IOException {
                        throw new RuntimeException("NYI: .print");
                    }

                    @Override
                    public void print(final float f) throws IOException {
                        throw new RuntimeException("NYI: .print");
                    }

                    @Override
                    public void print(final double d) throws IOException {
                        throw new RuntimeException("NYI: .print");
                    }

                    @Override
                    public void print(final char[] s) throws IOException {
                        throw new RuntimeException("NYI: .print");
                    }

                    @Override
                    public void print(final String s) throws IOException {
                        throw new RuntimeException("NYI: .print");
                    }

                    @Override
                    public void print(final Object obj) throws IOException {
                        throw new RuntimeException("NYI: .print");
                    }

                    @Override
                    public void println() throws IOException {
                        throw new RuntimeException("NYI: .println");
                    }

                    @Override
                    public void println(final boolean x) throws IOException {
                        throw new RuntimeException("NYI: .println");
                    }

                    @Override
                    public void println(final char x) throws IOException {
                        throw new RuntimeException("NYI: .println");
                    }

                    @Override
                    public void println(final int x) throws IOException {
                        throw new RuntimeException("NYI: .println");
                    }

                    @Override
                    public void println(final long x) throws IOException {
                        throw new RuntimeException("NYI: .println");
                    }

                    @Override
                    public void println(final float x) throws IOException {
                        throw new RuntimeException("NYI: .println");
                    }

                    @Override
                    public void println(final double x) throws IOException {
                        throw new RuntimeException("NYI: .println");
                    }

                    @Override
                    public void println(final char[] x) throws IOException {
                        throw new RuntimeException("NYI: .println");
                    }

                    @Override
                    public void println(final String x) throws IOException {
                        throw new RuntimeException("NYI: .println");
                    }

                    @Override
                    public void println(final Object x) throws IOException {
                        throw new RuntimeException("NYI: .println");
                    }

                    @Override
                    public void clear() throws IOException {
                        throw new RuntimeException("NYI: .clear");
                    }

                    @Override
                    public void clearBuffer() throws IOException {
                        throw new RuntimeException("NYI: .clearBuffer");
                    }

                    @Override
                    public void flush() throws IOException {
                        throw new RuntimeException("NYI: .flush");
                    }

                    @Override
                    public void close() throws IOException {
                        throw new RuntimeException("NYI: .close");
                    }

                    @Override
                    public int getRemaining() {
                        throw new RuntimeException("NYI: .getRemaining");
                    }

                    @Override
                    public void write(final char[] cbuf, final int off, final int len) throws IOException {
                        writer.write(cbuf, off, len);
                    }
                };
            }
        });
    }

    protected abstract T createTag();

    protected abstract void init(final String xml, final String xslt) throws JspException;

    /**
     * https://bugzilla.redhat.com/show_bug.cgi?id=1266002
     */
    @Test
    public void testEmptyWithXmlAttr() throws JspException {
        final String xslt = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">" +
                "<xsl:output method=\"text\"/>" +
                "<xsl:template match=\"/\">" +
                "   <xsl:value-of select=\"dummy\"/>" +
                "</xsl:template>\n" +
                "</xsl:stylesheet>";
        init("<dummy>test</dummy>", xslt);
        tag.doStartTag();
        tag.doEndTag();
        assertEquals("test", writer.toString());
    }

}
