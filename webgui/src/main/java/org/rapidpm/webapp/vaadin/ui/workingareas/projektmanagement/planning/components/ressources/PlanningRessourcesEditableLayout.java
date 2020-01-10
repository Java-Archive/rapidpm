package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.ressources;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.Registration;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.EditableLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 30.08.12
 * Time: 11:24
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningRessourcesEditableLayout
    extends EditableLayout {

  private final ProjektplanungScreen screen;
  private       List<TextField>      ressourceGroupFields = new ArrayList<>();
  private       List<Registration>   listeners            = new ArrayList<>();

  public PlanningRessourcesEditableLayout(final PlanningUnit planningUnit,
                                          final ProjektplanungScreen screen,
                                          final Component screenPanel,
                                          boolean hasChildren) {
    super();
    this.screen = screen;
    final DaoFactory           daoFactory      = DaoFactorySingelton.getInstance();
    final List<RessourceGroup> ressourceGroups = daoFactory.getRessourceGroupDAO()
                                                           .loadAllEntities();
    for (final RessourceGroup ressourceGroup : ressourceGroups) {
      buildField(ressourceGroup, planningUnit);
    }
    buildForm();
    if (hasChildren) {
      for (final Registration registration : listeners) {
        registration.remove();
      }
    } else {
      cancelButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
        for (final TextField textField : ressourceGroupFields) {
          for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
            if (planningUnitElement.getRessourceGroup()
                                   .getName()
                                   .equals(textField.getLabel())) {
              final PlannedProject currentProject = VaadinSession.getCurrent()
                                                                 .getAttribute(PlannedProject.class);
              final DaysHoursMinutesItem item = new DaysHoursMinutesItem(planningUnitElement,
                                                                         currentProject.getHoursPerWorkingDay());
              textField.setValue(item.toString());
            }
          }
        }
        final Iterator<Component> componentIterator = componentsLayout.getChildren()
                                                                      .iterator();
        while (componentIterator.hasNext()) {
          final Component component = componentIterator.next();
          if (component instanceof AbstractField) {
            ((AbstractField) component).setReadOnly(true);
          }
        }
        buttonLayout.setVisible(false);
      });

      saveButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
        boolean allValid = true;
        for (final TextField textField : ressourceGroupFields) {
//                        if (!textField.isValid()){
//                            allValid = false;
//                        }
        }
        if (allValid) {
          for (final TextField textField : ressourceGroupFields) {
            for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
              if (planningUnitElement.getRessourceGroup()
                                     .getName()
                                     .equals(textField.getLabel())) {
                final VaadinSession  session        = VaadinSession.getCurrent();
                final PlannedProject currentProject = session.getAttribute(PlannedProject.class);
                final DaysHoursMinutesItem item = new DaysHoursMinutesItem(textField.getValue(),
                                                                           currentProject.getHoursPerWorkingDay());
                planningUnitElement.setPlannedMinutes(item.getMinutesFromDaysHoursMinutes());
                daoFactory.saveOrUpdateTX(planningUnitElement);
                final Iterator<Component> componentIterator = componentsLayout.getChildren()
                                                                              .iterator();
                while (componentIterator.hasNext()) {
                  final Component component = componentIterator.next();
                  if (component instanceof AbstractField) {
                    ((AbstractField) component).setReadOnly(true);
                  }
                }
                buttonLayout.setVisible(false);
              }
            }
          }
        }
      });
    }
  }

  private void buildField(final RessourceGroup ressourceGroup, final PlanningUnit planningUnit) {
    final TextField     field   = new TextField(ressourceGroup.getName());
    PlanningUnitElement element = new PlanningUnitElement();
    for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
      final String elementRessourceGroupName = planningUnitElement.getRessourceGroup()
                                                                  .getName();
      if (elementRessourceGroupName.equals(ressourceGroup.getName())) {
        element = planningUnitElement;
      }
    }
    final int                 index               = planningUnit.getPlanningUnitElementList()
                                                                .indexOf(element);
    final PlanningUnitElement planningUnitElement = planningUnit.getPlanningUnitElementList()
                                                                .get(index);
    final VaadinSession       session             = VaadinSession.getCurrent();
    final PlannedProject      currentProject      = session.getAttribute(PlannedProject.class);
    final DaysHoursMinutesItem item = new DaysHoursMinutesItem(planningUnitElement,
                                                               currentProject.getHoursPerWorkingDay());
    field.setValue(item.toString());
    field.setReadOnly(true);
//        field.addValidator(new DaysHoursMinutesFieldValidator(screen));
    ressourceGroupFields.add(field);
  }

  @Override
  protected void buildForm() {
    for (final TextField field : ressourceGroupFields) {
      componentsLayout.add(field);
    }
  }

  @Override
  protected void setLayout() {
//        componentsLayout = new VerticalLayout();
  }
}