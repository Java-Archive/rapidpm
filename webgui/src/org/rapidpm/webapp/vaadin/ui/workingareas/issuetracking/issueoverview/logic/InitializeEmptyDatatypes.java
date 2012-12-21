package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.GraphBaseDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 20.12.12
 * Time: 11:17
 * To change this template use File | Settings | File Templates.
 */
public class InitializeEmptyDatatypes {
    private static final Logger logger = Logger.getLogger(InitializeEmptyDatatypes.class);

    public InitializeEmptyDatatypes() {
    }

    public <T> List<T> initDatatype(final Class typeClass, final GraphBaseDAO<T> dao){
        final List<T> list = dao.loadAllEntities();
        if (list.isEmpty()) {
            if (logger.isDebugEnabled())
                logger.debug("Create entity for: "+ typeClass.getSimpleName());
            try {
                final File defaultSettings = new File("C:\\rapidPmDefaultSettings.xml");
                final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                final Document doc = dBuilder.parse(defaultSettings);
                doc.getDocumentElement().normalize();

                final NodeList nodes = doc.getElementsByTagName(typeClass.getSimpleName());

                for (int i = 0; i < nodes.getLength(); i++) {
                    final T instance = (T) typeClass.newInstance();
                    final NodeList childNodes = nodes.item(i).getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        final Node node = childNodes.item(j);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            final Element element = (Element) node;
                            final Field field = typeClass.getDeclaredField(getValue("name", element));
                            boolean isAccessible = field.isAccessible();
                            field.setAccessible(true);
                            if (field.getType().equals(Integer.class)) {
                                field.set(instance, Integer.valueOf(getValue("value", element)));
                            } else {
                                field.set(instance, getValue("value", element));
                            }


                            field.setAccessible(isAccessible);
                        }
                    }
                    list.add(dao.persist(instance));
                }
        } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (ParserConfigurationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (SAXException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (NoSuchFieldException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }  else {
            if (logger.isDebugEnabled())
                logger.debug(typeClass.getSimpleName() + " has at least one entry");
        }
        return list;
    }

    private static String getValue(String tag, Element element) {
        final NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        final Node node = nodes.item(0);
        return node.getNodeValue();
    }

//    public static void main(String[] args) {
//        IssuePriorityDAO dao = DaoFactorySingelton.getInstance().getIssuePriorityDAO();
//        InitializeEmptyDatatypes init = new InitializeEmptyDatatypes();
//        init.initDatatype(IssuePriority.class, dao.loadAllEntities(), dao);
//    }

}
