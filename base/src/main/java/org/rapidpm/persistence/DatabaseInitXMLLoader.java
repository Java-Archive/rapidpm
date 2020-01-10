package org.rapidpm.persistence;

import org.rapidpm.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 20.12.12
 * Time: 11:17
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseInitXMLLoader {

    public DatabaseInitXMLLoader() {
    }

    public <T> void initDatatype(final Class typeClass, final GraphBaseDAO<T> dao, final Long projectId) {
        final List<T> list = dao.loadAllEntities(projectId);
        if (list.isEmpty()) {
            try {
                final InputStream inputStream;
                inputStream = this.getClass().getClassLoader().getResourceAsStream(Constants.ISSUE_SETTINGS_XML_PATH);
                if (inputStream == null) {
                    throw new FileNotFoundException(Constants.ISSUE_SETTINGS_XML_PATH);
                }
                final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                final Document doc = dBuilder.parse(inputStream);
                doc.getDocumentElement().normalize();

                final NodeList nodes = doc.getElementsByTagName(typeClass.getSimpleName());

                for (int i = 0; i < nodes.getLength(); i++) {
                    final T instance = (T) typeClass.getConstructor(Long.class).newInstance(projectId);

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
                    dao.persist(instance);
                }
            } catch (InstantiationException | IllegalAccessException | NoSuchFieldException | IOException
                    | SAXException | ParserConfigurationException | NoSuchMethodException | InvocationTargetException e) {
            }
        }  else {
        }
    }

    private static String getValue(String tag, Element element) {
        final NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        final Node node = nodes.item(0);
        return node.getNodeValue();
    }
}
