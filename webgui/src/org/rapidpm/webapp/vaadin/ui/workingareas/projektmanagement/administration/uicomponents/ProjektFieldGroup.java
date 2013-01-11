package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.TextField;
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
            switch (spaltenName) {
                case PlannedProject.ID:
//                final TextField field = build(PlannedProject.ID, String.class, TextField.class);
//                field.setNullRepresentation("");
//                field.setRequired(true);
//                field.setValue("autom.");
//                field.setEnabled(false);
                    break;
                case PlannedProject.EXTERNALDAILYRATE:
                    final AbstractTextField externalDailyRateField = (AbstractTextField) buildAndBind(propertyId);
                    bind(externalDailyRateField, propertyId);
                    break;
                case PlannedProject.NAME:
                    final AbstractTextField nameField = (AbstractTextField) buildAndBind(propertyId);
                    nameField.setNullRepresentation("");
                    nameField.setRequired(true);
                    break;
                case PlannedProject.TOKEN:
                    final AbstractTextField tokenField = (AbstractTextField) buildAndBind(propertyId);
                    tokenField.setNullRepresentation("");
                    tokenField.setRequired(true);
                    break;
                default:
                    break;
            }
        }
    }
}

