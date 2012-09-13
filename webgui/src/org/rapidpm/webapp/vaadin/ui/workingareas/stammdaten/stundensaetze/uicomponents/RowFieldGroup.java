package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;

import java.util.ArrayList;
import java.util.Iterator;

import static org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.StundensaetzeTableCreator.*;

public class RowFieldGroup extends FieldGroup {


    private GridLayout gridLayout = new GridLayout(2, 2);

    public RowFieldGroup(RessourceGroup row) {
        setItemDataSource(new BeanItem<>(row));
        buildForm();
    }

    private void buildForm() {
        for (final Object propertyId : getUnboundPropertyIds()) {
            final String spaltenName = propertyId.toString();
            if (spaltenName.equals(NAME)
                    || spaltenName.equals(BRUTTOGEHALT)
                    || spaltenName.equals(HOURS_PER_WEEK)
                    || spaltenName.equals(WEEKS_PER_YEAR)
                    || spaltenName.equals(FACTURIZABLE)
                    || spaltenName.equals(EXTERNAL_EUROS_PER_HOUR)
                    || spaltenName.equals(PLAN_ANZAHL)){
                    final AbstractTextField field = (AbstractTextField) buildAndBind(propertyId);
                field.setNullRepresentation("");
                field.setRequired(true);
                gridLayout.addComponent(field);
            }
        }
    }

    public ArrayList<Component> getComponents() {
        final ArrayList<Component> components = new ArrayList<Component>();
        final Iterator<Component> componentIterator = gridLayout.getComponentIterator();
        while (componentIterator.hasNext()) {
            components.add(componentIterator.next());
        }
        return components;
    }

}
