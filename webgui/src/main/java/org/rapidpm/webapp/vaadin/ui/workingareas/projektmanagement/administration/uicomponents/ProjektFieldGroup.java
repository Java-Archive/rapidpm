package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.ComboBox;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;

import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 18.09.12
 * Time: 11:24
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class ProjektFieldGroup extends FieldGroup {

    private static final Integer[] HOURS_PER_DAY_ARRAY = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
    private ResourceBundle messages;

    public ProjektFieldGroup(final PlannedProject projekt, final ResourceBundle messages) {
        this.messages = messages;
        setItemDataSource(new BeanItem<>(projekt));
        buildForm();
    }

    private void buildForm() {
        for (final Object propertyId : getUnboundPropertyIds()) {
            final String spaltenName = propertyId.toString();
            switch (spaltenName) {
                case PlannedProject.ID:
                    break;
                case PlannedProject.EXTERNALDAILYRATE:
                    final AbstractTextField externalDailyRateField = (AbstractTextField) buildAndBind(propertyId);
                    externalDailyRateField.setNullRepresentation("");
                    externalDailyRateField.setRequired(true);
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
                case PlannedProject.HOURSPERWORKINGDAY:
                    final ComboBox hoursBox = new ComboBox(messages.getString("project_hoursPerWorkingDay"),
                            Arrays.asList(HOURS_PER_DAY_ARRAY));
                    bind(hoursBox, propertyId);
                    hoursBox.setRequired(true);
                    hoursBox.setNullSelectionAllowed(false);
                    break;
                default:
                    break;
            }
        }
    }
}

