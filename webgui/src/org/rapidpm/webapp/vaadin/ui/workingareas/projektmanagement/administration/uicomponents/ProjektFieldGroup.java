package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractTextField;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.Projekt;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 18.09.12
 * Time: 11:24
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class ProjektFieldGroup extends FieldGroup {

    public static final String PROJEKT_ID = "projektId";
    public static final String PROJEKT_NAME = "projektName";

    public ProjektFieldGroup(Projekt projekt) {
        setItemDataSource(new BeanItem<>(projekt));
        buildForm();
    }

    private void buildForm() {
        for (final Object propertyId : getUnboundPropertyIds()) {
            final String spaltenName = propertyId.toString();
            if(spaltenName.equals(PROJEKT_ID) || spaltenName.equals(PROJEKT_NAME)){
                final AbstractTextField field = (AbstractTextField) buildAndBind(propertyId);
                field.setNullRepresentation("");
                field.setRequired(true);
                field.setReadOnly(true);
            }
        }
    }
}

