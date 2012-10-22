package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractTextField;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 18.09.12
 * Time: 11:24
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class ProjektFieldGroup extends FieldGroup {

    public ProjektFieldGroup(final PlannedProject projekt) {
        setItemDataSource(new BeanItem<>(projekt));
        buildForm();
    }

    private void buildForm() {
        for (final Object propertyId : getUnboundPropertyIds()) {
            final String spaltenName = propertyId.toString();
            if(spaltenName.equals(PlannedProject.ID) ){
//                final TextField field = build(PlannedProject.ID, String.class, TextField.class);
//                field.setNullRepresentation("");
//                field.setRequired(true);
//                field.setValue("autom.");
//                field.setEnabled(false);
            } else if (spaltenName.equals(PlannedProject.NAME)){
                final AbstractTextField field = (AbstractTextField) buildAndBind(propertyId);
                field.setNullRepresentation("");
                field.setRequired(true);
            }
        }
    }
}

