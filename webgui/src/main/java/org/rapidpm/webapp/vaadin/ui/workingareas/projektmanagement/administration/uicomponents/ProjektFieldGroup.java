package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;

import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 18.09.12
 * Time: 11:24
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class ProjektFieldGroup
    extends Binder<PlannedProject> {

  private static final Integer[]      HOURS_PER_DAY_ARRAY = {
      1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23
  };
  private              ResourceBundle messages;

  private TextField         externalDailyRateField;
  private TextField         nameField;
  private TextField         tokenField;
  private ComboBox<Integer> hoursBox;

  public ProjektFieldGroup(final PlannedProject projekt, final ResourceBundle messages) {
    super(PlannedProject.class);
    this.messages          = messages;
    externalDailyRateField = new TextField();
    nameField              = new TextField();
    tokenField             = new TextField();
    hoursBox               = new ComboBox<>(messages.getString("project_hoursPerWorkingDay"));
    buildForm();
    readBean(projekt);
  }

  private void buildForm() {
    externalDailyRateField = new TextField(messages.getString("externalEurosPerHour"));
    externalDailyRateField.setReadOnly(true);
    forField(externalDailyRateField).withNullRepresentation("")
                                    .withConverter(new StringToDoubleConverter(""))
                                    .asRequired()
                                    .bind(PlannedProject.EXTERNALDAILYRATE);
    nameField = new TextField(messages.getString("planning_name"));
    nameField.setReadOnly(true);
    forField(nameField).withNullRepresentation("")
                       .asRequired()
                       .bind(PlannedProject.NAME);
    tokenField = new TextField("Token");
    tokenField.setReadOnly(true);
    forField(tokenField).withNullRepresentation("")
                        .asRequired()
                        .bind(PlannedProject.TOKEN);
    hoursBox = new ComboBox<>(messages.getString("project_hoursPerWorkingDay"), Arrays.asList(HOURS_PER_DAY_ARRAY));
    hoursBox.setReadOnly(true);
    forMemberField(hoursBox).asRequired()
                            .bind(PlannedProject.HOURSPERWORKINGDAY);
  }

  public Optional<HasValue> getFieldForProperty(String property) {
    switch (property) {
      case PlannedProject.EXTERNALDAILYRATE:
        return Optional.ofNullable(externalDailyRateField);
      case PlannedProject.NAME:
        return Optional.ofNullable(nameField);
      case PlannedProject.TOKEN:
        return Optional.ofNullable(tokenField);
      case PlannedProject.HOURSPERWORKINGDAY:
        return Optional.ofNullable(hoursBox);
      default:
        return Optional.empty();
    }
  }
}

