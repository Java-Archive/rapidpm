package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractTextField;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitGroup;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 19.09.12
 * Time: 10:22
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningUnitGroupFieldGroup extends FieldGroup {

    public PlanningUnitGroupFieldGroup(PlanningUnitGroup planningUnitGroup) {
        setItemDataSource(new BeanItem<>(planningUnitGroup));
        buildForm();
    }

    private void buildForm() {
        for (final Object propertyId : getUnboundPropertyIds()) {
            final String spaltenName = propertyId.toString();
            if(spaltenName.equals(PlanningUnitGroup.NAME) || spaltenName.equals(PlanningUnitGroup.ID)){
                final AbstractTextField field = (AbstractTextField) buildAndBind(propertyId);
                field.setNullRepresentation("");
                field.setRequired(true);
                field.setReadOnly(false);
            }
        }
    }
}
