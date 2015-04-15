package org.rapidpm;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 11.02.2010
 *        Time: 14:32:22
 */

//import org.apache.log4j.Logger;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

public class JDomTester {
    // private static final Logger logger = Logger.getLogger(JDomTester.class);
    // public static final Log logger = LogFactory.getLog(JDomTester.class);
    private static final SimpleDateFormat format = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM, Locale.GERMANY);

    public static void main(final String[] args) throws JDOMException, IOException, ParserConfigurationException {
        final SAXBuilder builder = new SAXBuilder(false);
        //Document doc = builder.build(new File("../"));

        final Document document = new Document();
        //final Element rootElement = document.getRootElement();
        final Element rootElement = new Element("documents");
        final Attribute timeStamp = new Attribute("timestamp", format.format(new Date()));
        rootElement.setAttribute(timeStamp);
        document.setRootElement(rootElement);

        final Element page = new Element("document");
        final Attribute uri = new Attribute("uri", "www.heise.de");
        page.setAttribute(uri);

        final Element doc_element = new Element("doc_element");
        final Attribute name = new Attribute("name", "content");
        final Attribute value = new Attribute("value", "Das ist der INhalt der Seite");
        doc_element.setAttribute(name);
        doc_element.setAttribute(value);
        page.setContent(doc_element);
        rootElement.addContent(page);

        final Element pageII = new Element("document");
        final Attribute uriII = new Attribute("uri", "www.heise.de");
        pageII.setAttribute(uriII);

        final Element doc_elementII = new Element("doc_element");
        final Attribute nameII = new Attribute("name", "content");
        final Attribute valueII = new Attribute("value", "BLABLA");
        doc_elementII.setAttribute(nameII);
        doc_elementII.setAttribute(valueII);
        pageII.setContent(doc_elementII);
        rootElement.addContent(pageII);

        final XMLOutputter outputter = new XMLOutputter();

        // TODO Fix for JDom2
//        final List<Element> contentListe = rootElement.getContent();
//        final Element element = document.getRootElement();
//        for (final Element documentElement : contentListe) {
//            final Attribute attributeURI = documentElement.getAttribute("uri");
//            final String url = attributeURI.getValue();
//
//
//            outputter.output(documentElement, System.out);
//            System.out.append(Constants.LINE_BREAK);
//        }

/*

        outputter.output(document, System.out);
        outputter.output(rootElement, System.out);
        System.out.flush();
*/


/*
        final String xmlDoc = "<documents timestamp=\"2010-02-11-15-00\"><document uri=\"www.heise.de\"><doc_element name=\"content\" value=\"Das ist der INhalt der Seite\" /></document></documents>";
        final Document restoredDoc = builder.build(new StringReader(xmlDoc));
        outputter.output(restoredDoc, System.out);
        System.out.flush();

        final List<Element> content = restoredDoc.getContent();
        for (Element element : content) {
            outputter.output(element, System.out);
            System.out.append(Constants.LINE_BREAK);
        }
*/

    }

}