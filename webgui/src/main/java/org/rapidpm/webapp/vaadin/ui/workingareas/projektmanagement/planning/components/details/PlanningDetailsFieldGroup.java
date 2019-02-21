package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.details;


import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.Nameable;

import java.util.*;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 26.09.12
 * Time: 12:51
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningDetailsFieldGroup extends Binder<PlanningUnit> {
    private ResourceBundle messages;

    private ComboBox<Nameable> responsiblePersonBox;
    private TextField orderNumberField;
    private TextField complexityField;
    private TextField storyPointsField;

    public PlanningDetailsFieldGroup(final ResourceBundle messages, final PlanningUnit unmanagedPlanningUnit) {
        super(PlanningUnit.class);
        this.messages = messages;
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final PlanningUnit planningUnit = daoFactory.getPlanningUnitDAO().findByID(unmanagedPlanningUnit.getId());
        buildForm();
        readBean(Objects.requireNonNullElse(planningUnit, unmanagedPlanningUnit));
    }

    private void buildForm() {
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final List<Benutzer> users = daoFactory.getBenutzerDAO().loadAllEntities();
        responsiblePersonBox = generateBox(messages.getString("planning_responsible"));
        responsiblePersonBox.setItems(users.stream().map(benutzer -> (Nameable) benutzer));
        responsiblePersonBox.setReadOnly(true);
        forField(responsiblePersonBox)
                .withNullRepresentation(null)
                .asRequired()
                .bind(PlanningUnit.RESPONSIBLE);
        orderNumberField = new TextField(messages.getString("planning_ordernumber"));
        orderNumberField.setReadOnly(true);
        forField(orderNumberField)
                .withNullRepresentation("")
                .withConverter(new StringToIntegerConverter(""))
                .asRequired()
                .bind(PlanningUnit.ORDERNUMBER);
        complexityField = new TextField(messages.getString("planning_complexity"));
        complexityField.setReadOnly(true);
        forField(complexityField)
                .withNullRepresentation("")
                .withConverter(new StringToIntegerConverter(""))
                .asRequired()
                .bind(PlanningUnit.COMPLEXITY);
        storyPointsField = new TextField(messages.getString("planning_storypoints"));
        storyPointsField.setReadOnly(true);
        forField(storyPointsField)
                .withNullRepresentation("")
                .withConverter(new StringToIntegerConverter(""))
                .asRequired()
                .bind(PlanningUnit.STORYPTS);
    }

    public ComboBox<Nameable> generateBox(final String caption){
        final ComboBox<Nameable> box = new ComboBox<>(caption);
        box.setItemLabelGenerator(Nameable::name);
        return box;
    }

    public List<AbstractField> getFieldList() {
        return new ArrayList<>(Arrays.asList(responsiblePersonBox, orderNumberField, complexityField, storyPointsField));
    }

    public Optional<AbstractField> getFieldForProperty(String property) {
        switch (property) {
            case PlanningUnit.RESPONSIBLE:
                return Optional.ofNullable(responsiblePersonBox);
            case PlanningUnit.ORDERNUMBER:
                return Optional.ofNullable(orderNumberField);
            case PlanningUnit.COMPLEXITY:
                return Optional.ofNullable(complexityField);
            case PlanningUnit.STORYPTS:
                return Optional.ofNullable(storyPointsField);
            default:
                return Optional.empty();
        }
    }
}
