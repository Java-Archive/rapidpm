package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.ui.EditableLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ProjektFieldGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;

import static org.rapidpm.Constants.COMMIT_EXCEPTION_MESSAGE;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 11.01.13
 * Time: 13:35
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class ExternalDailyRateEditableLayout
    extends EditableLayout {

  private ProjektFieldGroup fieldGroup;
  private TextField         externalDailyRateField;
  private PlannedProject    currentProject;

  public ExternalDailyRateEditableLayout() {
    final DaoFactory     daoFactory                         = DaoFactorySingelton.getInstance();
    final PlannedProject plannedProjectFromSessionAttribute = VaadinSession.getCurrent()
                                                                           .getAttribute(PlannedProject.class);
    currentProject         = daoFactory.getPlannedProjectDAO()
                                       .findByID(plannedProjectFromSessionAttribute.getId());
    fieldGroup             = new ProjektFieldGroup(currentProject, messages);
    externalDailyRateField = (TextField) fieldGroup.getFieldForProperty(PlannedProject.EXTERNALDAILYRATE)
                                                   .orElse(new TextField());
    saveButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
      try {
        fieldGroup.writeBean(currentProject);
        daoFactory.saveOrUpdateTX(currentProject);
        UI.getCurrent()
          .navigate(StundensaetzeScreen.class.getAnnotation(Route.class)
                                             .value());
      } catch (final NullPointerException e) {
//        logger.info(COMMIT_EXCEPTION_MESSAGE);
      } catch (final Exception e) {
//        logger.warn("Exception", e);
      }
    });
    cancelButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
      fieldGroup.readBean(currentProject);
      fieldGroup.getFieldForProperty(PlannedProject.EXTERNALDAILYRATE)
                .ifPresent(field -> {
                  componentsLayout.add(externalDailyRateField);
                  externalDailyRateField.setReadOnly(true);
                  externalDailyRateField.setErrorMessage(messages.getString("stdsatz_currencyOnly"));
                  buttonLayout.setVisible(false);
                });
    });
    buildForm();
  }


  @Override
  protected void buildForm() {
    fieldGroup.getFields()
              .forEach(field -> {
                field.setReadOnly(false);
                if (field instanceof ComboBox) {
                  ((ComboBox) field).setAllowCustomValue(false);
                }
              });
    fieldGroup.getFieldForProperty(PlannedProject.EXTERNALDAILYRATE)
              .ifPresent(field -> field.setReadOnly(true));
    externalDailyRateField.setReadOnly(true);
    componentsLayout.add(externalDailyRateField);
  }


  @Override
  protected void setLayout() {
    componentsLayout = new FormLayout();
  }

  public PlannedProject getCurrentProject() {
    return currentProject;
  }

  public TextField getExternalDailyRateField() {
    return externalDailyRateField;
  }

  public void setExternalDailyRateField(final TextField externalDailyRateField) {
    this.externalDailyRateField = externalDailyRateField;
  }

  public ProjektFieldGroup getFieldGroup() {
    return fieldGroup;
  }

  public void setFieldGroup(ProjektFieldGroup fieldGroup) {
    this.fieldGroup = fieldGroup;
  }
}
