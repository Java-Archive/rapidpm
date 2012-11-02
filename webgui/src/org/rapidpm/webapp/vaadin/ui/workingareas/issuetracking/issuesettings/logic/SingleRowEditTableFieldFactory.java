package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings.logic;

import com.vaadin.data.Container;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TableFieldFactory;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 02.11.12
 * Time: 12:49
 * To change this template use File | Settings | File Templates.
 */
public class SingleRowEditTableFieldFactory implements TableFieldFactory {
    private static Logger logger = Logger.getLogger(SingleRowEditTableFieldFactory.class);

    private Object itemId;

    public SingleRowEditTableFieldFactory(Object itemId) {
        this.itemId = itemId;
    }

    @Override
    public Field<?> createField(Container container, Object itemId, Object propertyId, Component uiContext) {
        logger.info("SaveItem: " + this.itemId);
        logger.info("SaveItem: " + itemId);
        if (itemId.equals(this.itemId)) {
            logger.info("if true");
            return DefaultFieldFactory.get().createField(container, itemId, propertyId, uiContext);
        }
        return null;
    }
}
