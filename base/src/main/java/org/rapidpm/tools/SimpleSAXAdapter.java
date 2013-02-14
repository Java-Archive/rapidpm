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
 * An implementation of SimpleSAXListener providing dummy implementations for all its methods.
 */
public class SimpleSAXAdapter implements SimpleSAXListener {

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void startTag(final String tagName, final Map atts, final String text) throws SAXException {
    }

    @Override
    public void endTag(final String tagName) throws SAXException {
    }
}