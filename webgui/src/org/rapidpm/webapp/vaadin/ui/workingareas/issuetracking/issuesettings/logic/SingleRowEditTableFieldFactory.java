package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings.logic;

import com.vaadin.data.Container;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TableFieldFactory;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 02.11.12
 * Time: 12:49
 * To change this template use File | Settings | File Templates.
 */
public class SingleRowEditTableFieldFactory implements TableFieldFactory {
    private static Logger logger = Logger.getLogger(SingleRowEditTableFieldFactory.class);

    private Object entity;

    public SingleRowEditTableFieldFactory(Object entity) {
        if (entity == null)
            throw new NullPointerException("Entity must not be null");

        this.entity = entity;
    }

    @Override
    public Field<?> createField(Container container, Object itemId, Object propertyId, Component uiContext) {
        if (logger.isDebugEnabled()) {
            logger.debug("CreateField given itemId   : " + this.entity);
            logger.debug("CreateField function itemId: " + itemId);
        }
        if (itemId.equals(entity)) {
            if (logger.isDebugEnabled())
                logger.debug("Items are equal");
            return DefaultFieldFactory.get().createField(container, itemId, propertyId, uiContext);
        }
        return null;
    }
}
