package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.RessourceGroup;

import java.util.ArrayList;
import java.util.Iterator;

public class RowFieldGroup extends FieldGroup {

    private Button saveButton = new Button("Save");
    private Button cancelButton = new Button("Cancel");

    private GridLayout gridLayout = new GridLayout(2, 2);
    private HorizontalLayout horizontalButtonLayout = new HorizontalLayout();

    public RowFieldGroup(RessourceGroup row) {
        setItemDataSource(new BeanItem<RessourceGroup>(row));
        buildForm();
    }

    public RowFieldGroup(Item item) {
        setItemDataSource(item);

        buildForm();
    }

    private void buildForm() {
        for (final Object propertyId : getUnboundPropertyIds()) {
            final String spaltenName = propertyId.toString();
            if (!spaltenName.equals("sumPerDay")
                    && !spaltenName.equals("sumPerMonth")
                    && !spaltenName.equals("bruttoPerMonth")
                    && !spaltenName.equals("operativeEurosPerHour")
                    && !spaltenName.equals("eurosPerHour")
                    && !spaltenName.equals("hoursPerYear")){
                    AbstractTextField field = (AbstractTextField) buildAndBind(propertyId);
                field.setNullRepresentation("");
                gridLayout.addComponent(field);
            }
        }

        horizontalButtonLayout.setSpacing(true);
        horizontalButtonLayout.addComponent(saveButton);
        horizontalButtonLayout.addComponent(cancelButton);

    }

    public ArrayList<Component> getComponents() {
        final ArrayList<Component> components = new ArrayList<Component>();
        final Iterator<Component> componentIterator = gridLayout.getComponentIterator();
        while (componentIterator.hasNext()) {
            components.add(componentIterator.next());
        }
        return components;
    }

    public ArrayList<Component> getFooterComponents() {
        final ArrayList<Component> components = new ArrayList<Component>();
        final Iterator<Component> componentIterator = horizontalButtonLayout.getComponentIterator();
        while (componentIterator.hasNext()) {
            components.add(componentIterator.next());
        }
        return components;
    }

}
