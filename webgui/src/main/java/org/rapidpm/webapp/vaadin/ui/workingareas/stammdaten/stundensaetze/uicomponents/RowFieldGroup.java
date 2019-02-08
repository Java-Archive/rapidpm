package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.DoubleRangeValidator;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;

import java.util.*;

public class RowFieldGroup extends Binder<RessourceGroup> {

    private RessourceGroup ressourceGroup;

    private TextField name = new TextField();
    private TextField hoursPerWeek = new TextField();
    private TextField weeksPerYear = new TextField();
    private TextField planAnzahl = new TextField();
    private TextField facturizable = new TextField();
    private TextField externalEurosPerHour = new TextField();
    private TextField bruttoGehalt = new TextField();

    private Map<TextField, DefaultValue> defaultValueByField = new HashMap<>();
    private Map<TextField, String> labelByField = new HashMap<>();

    public RowFieldGroup() {
        super(RessourceGroup.class);
        ressourceGroup = new RessourceGroup();
        setBean(ressourceGroup);
        buildForm();
    }

    private void buildForm() {
        ResourceBundle messages = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);
        forField(name)
                .withNullRepresentation("")
                .bind(RessourceGroup.NAME);
        labelByField.put(name, messages.getString(RessourceGroup.NAME));
        defaultValueByField.put(name, DefaultValue.String);
        forField(hoursPerWeek)
                .withNullRepresentation("")
                .withConverter(new StringToIntegerConverter("Integer-Value!"))
                .asRequired()
                .bind(RessourceGroup.HOURS_PER_WEEK);
        labelByField.put(hoursPerWeek, messages.getString(RessourceGroup.HOURS_PER_WEEK));
        defaultValueByField.put(hoursPerWeek, DefaultValue.Integer);
        forField(weeksPerYear)
                .withNullRepresentation("")
                .withConverter(new StringToIntegerConverter("Integer-Value!"))
                .asRequired()
                .bind(RessourceGroup.WEEKS_PER_YEAR);
        labelByField.put(weeksPerYear, messages.getString(RessourceGroup.WEEKS_PER_YEAR));
        defaultValueByField.put(weeksPerYear, DefaultValue.Integer);
        forField(planAnzahl)
                .withNullRepresentation("")
                .withConverter(new StringToIntegerConverter("Integer-Value!"))
                .asRequired()
                .bind(RessourceGroup.PLAN_ANZAHL);
        labelByField.put(planAnzahl, messages.getString(RessourceGroup.PLAN_ANZAHL));
        defaultValueByField.put(planAnzahl, DefaultValue.Integer);
        forField(facturizable)
                .withNullRepresentation("")
                .withConverter(Double::parseDouble, value -> value == null ? "0.0" : value.toString())
                .withValidator(new DoubleRangeValidator("Double-Value!", 0.0d, 100.0d))
                .asRequired()
                .bind(RessourceGroup.FACTURIZABLE);
        labelByField.put(facturizable, messages.getString(RessourceGroup.FACTURIZABLE));
        defaultValueByField.put(facturizable, DefaultValue.Double);
        forField(externalEurosPerHour)
                .withNullRepresentation("")
                .withConverter(Double::parseDouble, value -> value == null ? "0.0" : value.toString())
                .withValidator(new DoubleRangeValidator("Double-Value!", 0.0d, 100.0d))
                .asRequired()
                .bind(RessourceGroup.EXTERNAL_EUROS_PER_HOUR);
        labelByField.put(externalEurosPerHour, messages.getString(RessourceGroup.EXTERNAL_EUROS_PER_HOUR));
        defaultValueByField.put(externalEurosPerHour, DefaultValue.Double);
        forField(bruttoGehalt)
                .withNullRepresentation("")
                .withConverter(Double::parseDouble, value -> value == null ? "0.0" : value.toString())
                .withValidator(new DoubleRangeValidator("Double-Value!", 0.0d, 100.0d))
                .asRequired()
                .bind(RessourceGroup.BRUTTOGEHALT);
        labelByField.put(bruttoGehalt, messages.getString(RessourceGroup.BRUTTOGEHALT));
        defaultValueByField.put(bruttoGehalt, DefaultValue.Double);
    }

    String getDefaultValueByField(TextField textField) {
        return defaultValueByField.get(textField).getDefaultValue();
    }

    String getLabelByField(TextField textField) {
        return labelByField.get(textField);
    }

    List<TextField> getTextFields() {
        return new ArrayList<>(Arrays.asList(name, hoursPerWeek, weeksPerYear, planAnzahl, facturizable, externalEurosPerHour, bruttoGehalt));
    }

    public void commit() {
        try {
            writeBean(ressourceGroup);
        } catch (ValidationException e) {
            Notification.show(e.getLocalizedMessage());
        }
    }
}
