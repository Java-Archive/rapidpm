/*
 * Copyright (c) 2005 - 2008 Aduna.
 * All rights reserved.
 * 
 * Licensed under the Open Software License version 3.0.
 */
package org.rapidpm.lang;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A utility class offering convenience methods for writing XML. This class takes care of character
 * escaping, identation, etc. This class does not verify that the written data is legal XML. It is the
 * callers responsibility to make sure that elements are properly nested, etc.
 * <p/>
 * <h3>Example:</h3>
 * <p/>
 * To write the following XML:
 * <p/>
 * <pre>
 *    &lt;?xml version='1.0' encoding='UTF-8'?&gt;
 *    &lt;xml-doc&gt;
 *      &lt;foo a=&quot;1&quot; b=&quot;2&amp;amp;3&quot;/&gt;
 *      &lt;bar&gt;Hello World!&lt;/bar&gt;
 *    &lt;/xml-doc&gt;
 * </pre>
 * <p/>
 * <p/>
 * one can use the following code:
 * <p/>
 * <pre>
 * XmlWriter xmlWriter = new XmlWriter(myWriter);
 * xmlWriter.setPrettyPrint(true);
 *
 * xmlWriter.startDocument();
 * xmlWriter.startTag(&quot;xml-doc&quot;);
 *
 * xmlWriter.setAttribute(&quot;a&quot;, 1);
 * xmlWriter.setAttribute(&quot;b&quot;, &quot;2&amp;3&quot;);
 * xmlWriter.emptyElement(&quot;foo&quot;);
 *
 * xmlWriter.textTag(&quot;bar&quot;, &quot;Hello World!&quot;);
 *
 * xmlWriter.endTag(&quot;xml-doc&quot;);
 * xmlWriter.endDocument();
 * </pre>
 */
public class XmlWriter {

    /**
     * The (platform-dependent) line separator as a char array.
     */
    private static final char[] LINE_SEPARATOR = System.getProperty("line.separator").toCharArray();

    /**
     * A wrapper around an OutputStream that handles the character encoding.
     */
    private Writer writer;

    /**
     * The required character encoding of the written data.
     */
    private String charEncoding;

    /**
     * Flag indicating whether the output should be printed pretty, i.e. adding newlines and indentation.
     */
    private boolean prettyPrint = false;

    /**
     * The current indentation level, i.e. the number of tabs to indent a start or end tag.
     */
    private int indentLevel = 0;

    /**
     * The string to use for indentation, e.g. a tab or a number of spaces.
     */
    private char[] indentString = "\t".toCharArray();

    /**
     * The names of the attributes for the next start tag.
     */
    private ArrayList attNames = new ArrayList();

    /**
     * A mapping from attribute names to values for the next start tag.
     */
    private HashMap attValues = new HashMap();

    /**
     * Creates a new XmlWriter that will write its data to the supplied Writer. Character encoding issues
     * are left to the supplier of the Writer.
     *
     * @param writer The Writer to write the XML to.
     */
    public XmlWriter(final Writer writer) {
        this.writer = writer;
    }

    /**
     * Creates a new XmlWriter that will write its data to the supplied OutputStream in the default UTF-8
     * character encoding.
     *
     * @param outputStream The OutputStream to write the XML to.
     */
    public XmlWriter(final OutputStream outputStream) {
        try {
            charEncoding = "UTF-8";
            writer = new OutputStreamWriter(outputStream, charEncoding);
            writer = new BufferedWriter(writer);
        } catch (UnsupportedEncodingException e) {
            // UTF-8 must be supported by all compliant JVM's, this exception should never be thrown.
            throw new RuntimeException("UTF-8 character encoding not supported on this platform");
        }
    }

    /**
     * Creates a new XmlWriter that will write its data to the supplied OutputStream in specified
     * character encoding.
     *
     * @param outputStream The OutputStream to write the XML to.
     */
    public XmlWriter(final OutputStream outputStream, final String charEncoding) throws UnsupportedEncodingException {
        this.charEncoding = charEncoding;
        writer = new OutputStreamWriter(outputStream, charEncoding);
        writer = new BufferedWriter(writer);
    }

    /**
     * Enables or disables pretty-printing. If pretty-printing is enabled, the XmlWriter will add
     * newlines and indentation to the written data. Pretty-printing is disabled by default.
     *
     * @param prettyPrint Flag indicating whether pretty-printing should be enabled.
     */
    public void setPrettyPrint(final boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
    }

    /**
     * Checks whether pretty-printing is enabled.
     *
     * @return true if pretty-printing is enabled, false otherwise.
     */
    public boolean prettyPrintEnabled() {
        return prettyPrint;
    }

    /**
     * Sets the string that should be used for indentation when pretty-printing is enabled. The default
     * indentation string is a tab character.
     *
     * @param indentString The indentation string, e.g. a tab or a number of spaces.
     */
    public void setIndentString(final String indentString) {
        this.indentString = indentString.toCharArray();
    }

    /**
     * Gets the string used for indentation.
     *
     * @return the indentation string.
     */
    public String getIndentString() {
        return new String(indentString);
    }

    /**
     * Writes the XML header for the XML file.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void startDocument() throws IOException {
        writer.write("<?xml version='1.0'");
        if (charEncoding != null) {
            writer.write(" encoding='");
            writer.write(charEncoding);
            writer.write('\'');
        }
        writer.write("?>");

        writeLn();
    }

    /**
     * Finishes writing and flushes the OutputStream or Writer that this XmlWriter is writing to.
     */
    public void endDocument() throws IOException {
        writer.flush();
        writer = null;
    }

    /**
     * Sets an attribute for the next start tag.
     *
     * @param name  The name of the attribute.
     * @param value The value of the attribute.
     */
    public void setAttribute(final String name, final String value) {
        attNames.add(name);
        attValues.put(name, value);
    }

    /**
     * Sets an attribute for the next start element.
     *
     * @param name  The name of the attribute.
     * @param value The value of the attribute. The integer value will be transformed to a string using
     *              the method String.valueOf(int).
     * @see String#valueOf(int)
     */
    public void setAttribute(final String name, final int value) {
        setAttribute(name, String.valueOf(value));
    }

    /**
     * Sets an attribute for the next start element.
     *
     * @param name  The name of the attribute.
     * @param value The value of the attribute. The long integer value will be transformed to a string
     *              using the method String.valueOf(long).
     * @see String#valueOf(long)
     */
    public void setAttribute(final String name, final long value) {
        setAttribute(name, String.valueOf(value));
    }

    /**
     * Sets an attribute for the next start element.
     *
     * @param name  The name of the attribute.
     * @param value The value of the attribute. The boolean value will be transformed to a string using
     *              the method String.valueOf(boolean).
     * @see String#valueOf(boolean)
     */
    public void setAttribute(final String name, final boolean value) {
        setAttribute(name, String.valueOf(value));
    }

    /**
     * Writes a start tag containing the previously set attributes.
     *
     * @param elName The element name.
     * @see #setAttribute(String, String)
     */
    public void startTag(final String elName) throws IOException {
        writeIndent();

        writer.write('<');
        writer.write(elName);
        writeAtts();
        writer.write('>');

        writeLn();

        indentLevel++;
    }

    /**
     * Writes an end tag.
     *
     * @param elName The element name.
     */
    public void endTag(final String elName) throws IOException {
        indentLevel--;
        writeIndent();

        writer.write("</");
        writer.write(elName);
        writer.write('>');

        writeLn();
    }

    /**
     * Writes an 'empty' element, e.g. &lt;foo/&gt;. The tag will contain any previously set attributes.
     *
     * @param elName The element name.
     * @see #setAttribute(String, String)
     */
    public void emptyElement(final String elName) throws IOException {
        writeIndent();

        writer.write('<');
        writer.write(elName);
        writeAtts();
        writer.write("/>");

        writeLn();
    }

    /**
     * Writes a start and end tag with the supplied text between them. The start tag will contain any
     * previously set attributes.
     *
     * @param elName The element name.
     * @param text   The text.
     * @see #setAttribute(String, String)
     */
    public void textElement(final String elName, final String text) throws IOException {
        writeIndent();

        writer.write('<');
        writer.write(elName);
        writeAtts();
        writer.write('>');

        text(text);

        writer.write("</");
        writer.write(elName);
        writer.write('>');

        writeLn();
    }

    /**
     * Writes a start and end tag with the supplied integer value between them. The start tag will
     * contain any previously set attributes.
     *
     * @param elName The element name.
     * @param value  The value of the attribute. The integer value will be transformed to a string using
     *               the method String.valueOf(int).
     * @see #setAttribute(String, String)
     * @see String#valueOf(int)
     */
    public void textElement(final String elName, final int value) throws IOException {
        textElement(elName, String.valueOf(value));
    }

    /**
     * Writes a start and end tag with the supplied long integer value between them. The start tag will
     * contain any previously set attributes.
     *
     * @param elName The element name.
     * @param value  The value of the attribute. The long integer value will be transformed to a string
     *               using the method String.valueOf(long).
     * @see #setAttribute(String, String)
     * @see String#valueOf(long)
     */
    public void textElement(final String elName, final long value) throws IOException {
        textElement(elName, String.valueOf(value));
    }

    /**
     * Writes a start and end tag with the supplied boolean value between them. The start tag will
     * contain any previously set attributes.
     *
     * @param elName The element name.
     * @param value  The value of the attribute. The boolean value will be transformed to a string using
     *               the method <tt>String.valueOf(boolean)</tt>.
     * @see #setAttribute(String, String)
     * @see String#valueOf(boolean)
     */
    public void textElement(final String elName, final boolean value) throws IOException {
        textElement(elName, String.valueOf(value));
    }

    /**
     * Writes a piece of text.
     *
     * @param text The text.
     */
    public void text(String text) throws IOException {
        // Escape special characters in text
        text = StringUtils.replace("&", "&amp;", text);
        text = StringUtils.replace("<", "&lt;", text);
        text = StringUtils.replace("]]>", "]]&gt;", text);
        writer.write(text);
    }

    /**
     * Writes a comment.
     *
     * @param comment The comment.
     */
    public void comment(final String comment) throws IOException {
        writeIndent();
        writer.write("<!-- ");
        writer.write(comment);
        writer.write(" -->");

        writeLn();
    }

    /**
     * Writes an empty line. A call to this method will be ignored when pretty-printing is disabled.
     */
    public void emptyLine() throws IOException {
        writeLn();
    }

    /**
     * Writes any set attributes and clears them afterwards.
     */
    private void writeAtts() throws IOException {
        final int nrAttNames = attNames.size();
        for (Object attName : attNames) {
            final String name = (String) attName;
            String value = (String) attValues.get(name);

            writer.write(' ');
            writer.write(name);
            writer.write("='");
            if (value != null) {
                // Escape special characters in attribute value
                value = StringUtils.replace("&", "&amp;", value);
                value = StringUtils.replace("<", "&lt;", value);
                value = StringUtils.replace("'", "&apos;", value);
                writer.write(value);
            }
            writer.write('\'');
        }

        attNames.clear();
        attValues.clear();
    }

    /**
     * Writes a a line-separator. The line-separator is not written when pretty-printing is disabled.
     */
    private void writeLn() throws IOException {
        if (prettyPrint) {
            writer.write(LINE_SEPARATOR);
        }
    }

    /**
     * Writes as much indentation strings as appropriate for the current indentation level. A call to
     * this method is ignored when pretty-printing is disabled.
     */
    private void writeIndent() throws IOException {
        if (prettyPrint) {
            for (int i = 0; i < indentLevel; i++) {
                writer.write(indentString);
            }
        }
    }
}