package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.DoubleRangeValidator;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.ui.AbstractTextField;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;

import java.util.ArrayList;
import java.util.List;

import static org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.StundensaetzeTableCreator.*;

public class RowFieldGroup extends FieldGroup {


    private List<AbstractTextField> fieldList = new ArrayList<>();
    private AbstractTextField nameField;

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
                if(spaltenName.equals(NAME)){
                    nameField = field;
                } else if( spaltenName.equals(FACTURIZABLE)
                        || spaltenName.equals(BRUTTOGEHALT)
                        || spaltenName.equals(EXTERNAL_EUROS_PER_HOUR)){
                    field.addValidator(new DoubleRangeValidator("Double-Value!",null,null));
                    fieldList.add(field);
                } else {
                    field.addValidator(new IntegerRangeValidator("Integer-Value!",null,null));
                    fieldList.add(field);
                }

            }
        }
    }

    public AbstractTextField getNameField(){
        return nameField;
    }

    public List<AbstractTextField> getFieldList() {
        return fieldList;
    }

}
