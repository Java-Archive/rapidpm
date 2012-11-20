package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components;

import com.vaadin.ui.Table;
import org.apache.log4j.Logger;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 28.08.12
 * Time: 10:42
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class MyTable extends Table implements ColumnCollapseHandable {

    private static final Logger logger = Logger.getLogger(MyTable.class);
    private MyTreeTable connectedTreeTable;

    public MyTable() {
        setColumnReorderingAllowed(true);
        setColumnCollapsingAllowed(true);
    }

    public void setConnectedTable(final MyTreeTable table) {
        connectedTreeTable = table;
    }

    public void setColumnCollapsedEnd(final Object propertyId, final boolean collapsed) {
        try {
            super.setColumnCollapsed(propertyId, collapsed);
        } catch (Exception e) {
            logger.warn("Exception in MyTable");
        }
    }

    @Override
    public void setColumnCollapsed(final Object propertyId, final boolean collapsed) {
        super.setColumnCollapsed(propertyId, collapsed);
        try {
            connectedTreeTable.setColumnCollapsedEnd(propertyId, collapsed);
        } catch (Exception e) {
            logger.warn("Exception in MyTable");
        }
    }
}
