/*
 * Copyright (c) 2005 - 2008 Aduna.
 * All rights reserved.
 * 
 * Licensed under the Open Software License version 3.0.
 */
package org.rapidpm.tools;

import org.xml.sax.SAXException;

import java.util.Map;

/**
 * A listener for events reported by SimpleSAXParser.
 */
public interface SimpleSAXListener {

    /**
     * Notifies the listener that the parser has started parsing.
     */
    public void startDocument() throws SAXException;

    /**
     * Notifies the listener that the parser has finished parsing.
     */
    public void endDocument() throws SAXException;

    /**
     * Reports a start tag to the listener. The method call reports the tag's name, the attributes that
     * were found in the start tag and any text that was found after the start tag.
     *
     * @param tagName The tag name.
     * @param atts    A map containing key-value-pairs representing the attributes that were found in the
     *                start tag.
     * @param text    The text immediately following the start tag, or an empty string if the start tag was
     *                followed by a nested start tag or if no text (other than whitespace) was found between
     *                start- and end tag.
     */
    public void startTag(String tagName, Map atts, String text) throws SAXException;

    /**
     * Reports an end tag to the listener.
     *
     * @param tagName The tag name.
     */
    public void endTag(String tagName) throws SAXException;
}