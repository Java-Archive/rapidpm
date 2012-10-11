package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.TextField;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 13.09.12
 * Time: 09:23
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class RowEditFieldFactory implements TableFieldFactory {

    private Item item;

    public RowEditFieldFactory(final Item item){
        this.item = item;
    }

    @Override
    public Field<?> createField(final Container container, final Object itemId, final Object propertyId,
                                final Component uiContext) {
        if((((RessourceGroup)itemId)).equals(((BeanItem<RessourceGroup>)item).getBean())){
            final String spaltenName = propertyId.toString();
            if(!spaltenName.startsWith("transient")){
                final TextField field = new TextField();
                final String cellValue = container.getItem(itemId).getItemProperty(propertyId).getValue().toString();
                field.setValue(cellValue);
                return field;
            }
        }
        return null;
    }
}
