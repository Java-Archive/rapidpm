package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.Constants;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.textelement.TextElement;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.Nameable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;

import java.util.*;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 26.09.12
 * Time: 12:51
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningUnitFieldGroup
    extends Binder<PlanningUnit> {

  private List<AbstractField> fieldList = new ArrayList<>();
  private TextField           nameField;
  private TextField           storyPointsField;
  private TextField           complexityField;
  private TextField           orderNumberField;
  private ComboBox<Nameable>  parentBox;
  private ComboBox<Nameable>  responsibleBox;

  private PlanningUnit selectedPlanningUnit;

  private ProjektplanungScreen screen;
  private ResourceBundle       messages;
  private DaoFactory           daoFactory;

  public PlanningUnitFieldGroup(final ProjektplanungScreen screen) {
    super(PlanningUnit.class);
    this.screen   = screen;
    this.messages = VaadinSession.getCurrent()
                                 .getAttribute(ResourceBundle.class);
    daoFactory    = DaoFactorySingelton.getInstance();
    final PlanningUnit planningUnit = new PlanningUnit();
    planningUnit.setTestcases(new ArrayList<>());
    planningUnit.setPlanningUnitElementList(new ArrayList<>());
    selectedPlanningUnit = screen.getPlanningUnitSelect()
                                 .getValue();
    buildForm();
    readBean(planningUnit);

  }

  public PlanningUnitFieldGroup(final ProjektplanungScreen screen, final PlanningUnit thePlanningUnit) {
    super(PlanningUnit.class);
    this.screen          = screen;
    this.messages        = VaadinSession.getCurrent()
                                        .getAttribute(ResourceBundle.class);
    daoFactory           = DaoFactorySingelton.getInstance();
    selectedPlanningUnit = daoFactory.getPlanningUnitDAO()
                                     .findByID(thePlanningUnit.getId());
    if (selectedPlanningUnit == null) {
      selectedPlanningUnit = thePlanningUnit;
    }
    buildForm();
    readBean(selectedPlanningUnit);
  }

  private void buildForm() {
    final List<Benutzer> users   = daoFactory.getBenutzerDAO()
                                             .loadAllEntities();
    final PlannedProject project = daoFactory.getProjectDAO()
                                             .findByID(VaadinSession.getCurrent()
                                                                    .getAttribute(PlannedProject.class)
                                                                    .getId());
    daoFactory.getEntityManager()
              .refresh(project);
    final Set<PlanningUnit> managedPlanningUnits = getAllPlanningUnits(screen.getPlanningUnitSelect()
                                                                             .getValue());
    fieldList = new ArrayList<>();
    nameField = new TextField(messages.getString("planning_name"));
    nameField.setMaxLength(Constants.FIELDLENGTH_LONG_NAME);
    forField(nameField).withNullRepresentation("")
                       .asRequired()
                       .bind(PlanningUnit.NAME);
    fieldList.add(nameField);
    storyPointsField = new TextField(messages.getString("planning_storypoints"));
    storyPointsField.setMaxLength(Constants.FIELDLENGTH_SMALL_NUMBER);
    forField(storyPointsField).withNullRepresentation("")
                              .withConverter(new StringToIntegerConverter(""))
                              .asRequired()
                              .bind(PlanningUnit.STORYPTS);
    fieldList.add(storyPointsField);
    complexityField = new TextField(messages.getString("planning_complexity"));
    complexityField.setRequired(true);
    forField(complexityField).withNullRepresentation("")
                             .withConverter(new StringToIntegerConverter(""))
                             .asRequired()
                             .bind(PlanningUnit.COMPLEXITY);
    fieldList.add(complexityField);
    orderNumberField = new TextField(messages.getString("planning_ordernumber"));
    orderNumberField.setRequired(true);
    forField(orderNumberField).withNullRepresentation("")
                              .withConverter(new StringToIntegerConverter(""))
                              .asRequired()
                              .bind(PlanningUnit.ORDERNUMBER);
    fieldList.add(orderNumberField);
    final PlanningUnit nullPlanningUnit = new PlanningUnit();
    nullPlanningUnit.setPlanningUnitName(messages.getString("planning_noparent"));
    parentBox = generateBox(messages.getString("planning_parent"));
    parentBox.setItems(managedPlanningUnits.stream()
                                           .map(planningUnit -> (Nameable) planningUnit));
    parentBox.setValue((Nameable) selectedPlanningUnit.getParent());
    parentBox.setRequired(true);
    forField(parentBox).withNullRepresentation(null)
                       .bind(PlanningUnit.PARENT);
    fieldList.add(parentBox);
    responsibleBox = generateBox(messages.getString("planning_responsible"));
    responsibleBox.setItems(users.stream()
                                 .map(benutzer -> (Nameable) benutzer));
    responsibleBox.setRequired(true);
    forField(responsibleBox).withNullRepresentation(null)
                            .asRequired()
                            .bind(PlanningUnit.RESPONSIBLE);
    fieldList.add(responsibleBox);
  }

  private Set<PlanningUnit> getAllPlanningUnits(final PlanningUnit parentPlanningUnit) {
    final Set<PlanningUnit> resultSet = new HashSet<>();
    getPlanningUnits(resultSet, parentPlanningUnit);
    return resultSet;
  }

  private void getPlanningUnits(final Set<PlanningUnit> resultSet, final PlanningUnit planningUnit) {
    resultSet.add(planningUnit);
    final Set<PlanningUnit> kindPlanningUnits = planningUnit.getKindPlanningUnits();
    if (kindPlanningUnits != null && !kindPlanningUnits.isEmpty()) {
      resultSet.addAll(kindPlanningUnits);
      for (final PlanningUnit kindKindPlanningUnit : kindPlanningUnits) {
        getPlanningUnits(resultSet, kindKindPlanningUnit);
      }
    }
  }

  public List<AbstractField> getFieldList() {
    return fieldList;
  }

  Optional<AbstractField> getFieldForProperty(String property) {
    switch (property) {
      case PlanningUnit.NAME:
        return Optional.ofNullable(nameField);
      case PlanningUnit.RESPONSIBLE:
        return Optional.ofNullable(responsibleBox);
      case PlanningUnit.PARENT:
        return Optional.ofNullable(parentBox);
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

  private ComboBox<Nameable> generateBox(final String caption) {
    final ComboBox<Nameable> box = new ComboBox<>(caption);
    box.setItemLabelGenerator(Nameable::name);
    return box;
  }

  public PlanningUnit getSelectedPlanningUnit() {
    return selectedPlanningUnit;
  }
}
