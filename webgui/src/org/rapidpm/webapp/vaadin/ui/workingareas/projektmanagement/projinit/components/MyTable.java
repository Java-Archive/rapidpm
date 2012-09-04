package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components;

import com.vaadin.ui.Table;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 28.08.12
 * Time: 10:42
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class MyTable extends Table implements ColumnCollapseHandable {

    private MyTreeTable connectedTreeTable;

    public MyTable()
    {
        setColumnReorderingAllowed(true);
        setColumnCollapsingAllowed(true);
    }

    public void setConnectedTable(MyTreeTable table){
        connectedTreeTable = table;
    }

    public void setColumnCollapsedEnd(Object propertyId, boolean collapsed){
        try{
            super.setColumnCollapsed(propertyId, collapsed);
        }catch(Exception e){
            //do nothing
        }
    }

    @Override
    public void setColumnCollapsed(Object propertyId, boolean collapsed){
        super.setColumnCollapsed(propertyId, collapsed);
        try{
            connectedTreeTable.setColumnCollapsedEnd(propertyId, collapsed);
        }catch(Exception e){
            //do nothing
        }
    }
}
