package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.Constants;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.webapp.vaadin.ui.MainAppLayout;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.descriptionandtestcases.AddDescriptionsOrTestCasesWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all.PlanningUnitsTree;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all.PlanningUnitsTreePanelLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all.exceptions.SameNameException;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.parents.AddRootPlanningUnitsWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.parents.PlanningUnitSelect;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic.PlanningCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic.TreeValueChangeListener;

import javax.naming.InvalidNameException;
import java.util.*;
import java.util.stream.Collectors;

@Route(value = "projectplanning", layout = MainAppLayout.class)
@Caption("Projektplanung")
@Icon(VaadinIcon.PAPERPLANE)
public class ProjektplanungScreen
    extends Screen {

  private             HorizontalLayout             borderLayout                = new HorizontalLayout();
  private             RapidPanel                   leftColumn                  = new RapidPanel();
  private             RapidPanel                   centerColumn                = new RapidPanel();
  private             RapidPanel                   rightColumn                 = new RapidPanel();
  private             RapidPanel                   mainPanel;
  private             RapidPanel                   ressourcesPanel;
  private             RapidPanel                   planningUnitPanel;
  private             RapidPanel                   treePanel;
  private             RapidPanel                   detailsPanel;
  private             PlanningUnitSelect           planningUnitSelect;
  private             PlanningUnitsTree            planningUnitsTree;
  private             PlanningUnitsTreePanelLayout planningUnitsTreePanelLayout;
  private             HorizontalLayout             addParentPlanningUnitLayout = new HorizontalLayout();
  private             TextField                    addParentPlanningUnitField  = new TextField();
  private             Button                       addParentButton             = new Button();
  private             PlanningUnit                 tempPlanningUnit            = new PlanningUnit();
  private             DaoFactory                   daoFactory                  = DaoFactorySingelton.getInstance();
  private             Tabs                         tabSheet                    = new Tabs();

  //Buttons im TreePanelLayout
  private Button addButton    = new Button("+");
  private Button deleteButton = new Button("-");

  private Button         addParentsButton               = new Button();
  private Button         addDescriptionOrTestCaseButton = new Button();
  private ResourceBundle messagesBundle;

  public static final long PLATZHALTER_ID = 666l;


  public ProjektplanungScreen() {
    addParentPlanningUnitField.focus();

    try {
      final List<PlannedProject> plannedProjects = daoFactory.getPlannedProjectDAO()
                                                             .loadAllEntities();
      if (plannedProjects == null || plannedProjects.isEmpty()) {
        throw new NoProjectsException();
      }

      messagesBundle = VaadinSession.getCurrent()
                                    .getAttribute(ResourceBundle.class);
      final PlanningCalculator calculator = new PlanningCalculator(messagesBundle);
      calculator.calculate();

      addDescriptionOrTestCaseButton.addClickListener(
          (ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> new AddDescriptionsOrTestCasesWindow(
              planningUnitsTree.getSelectedItems()
                               .iterator()
                               .next(), messagesBundle).open());

      leftColumn.setSizeFull();
      centerColumn.setSizeFull();
      rightColumn.setSizeFull();

      rightColumn.add(addDescriptionOrTestCaseButton);
      rightColumn.add(tabSheet);

      planningUnitPanel = new RapidPanel();
      treePanel         = new RapidPanel();
      detailsPanel      = new RapidPanel();
      ressourcesPanel   = new RapidPanel();

      leftColumn.add(planningUnitPanel);
      leftColumn.add(treePanel);

      centerColumn.add(detailsPanel);
      centerColumn.add(ressourcesPanel);
      mainPanel = new RapidPanel();

      ressourcesPanel.setSizeFull();
      borderLayout.setSizeFull();

      rightColumn.add();

      buildPlanningUnitPanel();
      doInternationalization();
      setComponents();
    } catch (final NoProjectsException e) {
      removeAll();
      final NoProjectsScreen noProjectsScreen = new NoProjectsScreen();
      add(noProjectsScreen);
    }

  }

  private void buildPlanningUnitPanel() {
    setAddParentButtonListener();
    setAddParentsButtonListener();
    addParentPlanningUnitLayout.add(addParentPlanningUnitField, addParentButton, addParentsButton);
    addParentButton.setSizeUndefined();
    addParentsButton.setSizeUndefined();
    addParentPlanningUnitLayout.setAlignSelf(FlexComponent.Alignment.START, addParentButton);
    addParentPlanningUnitLayout.setAlignSelf(FlexComponent.Alignment.START, addParentsButton);
    planningUnitSelect = new PlanningUnitSelect();
    planningUnitSelect.addListenerComponent(deleteButton);
    planningUnitSelect.addListenerComponent(addButton);
    addParentPlanningUnitField.setWidth("160px");
    final PlannedProject     projectFromDB = planningUnitSelect.getProjectFromDB();
    final List<PlanningUnit> ids           = planningUnitSelect.getDataProvider()
                                                               .fetch(new Query<>())
                                                               .collect(Collectors.toList());
    planningUnitSelect.addValueChangeListener(
        (HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ListBox<PlanningUnit>, PlanningUnit>>) valueChangeEvent -> {
          final PlanningUnit planningUnitFromSelect = valueChangeEvent.getValue();
          PlanningUnit       planningUnitFromDB     = daoFactory.getPlanningUnitDAO()
                                                                .findByID(planningUnitFromSelect.getId());
          if (planningUnitFromDB != null) {
            daoFactory.getEntityManager()
                      .refresh(planningUnitFromDB);
          } else {
            planningUnitFromDB = planningUnitFromSelect;
          }
          treePanel.removeAllComponents();
          detailsPanel.removeAllComponents();
          treePanel.setText(planningUnitFromSelect.getPlanningUnitName());
          fillTreePanel(planningUnitFromDB, projectFromDB);
        });
    if (!ids.isEmpty()) {
      final PlanningUnit firstPlanningUnit = daoFactory.getPlanningUnitDAO()
                                                       .findByID((ids.get(0)).getId());
      daoFactory.getEntityManager()
                .refresh(firstPlanningUnit);
      planningUnitSelect.setValue(firstPlanningUnit);
    } else {
      tempPlanningUnit.setId(666l);
      tempPlanningUnit.setPlanningUnitName("Platzhalter");
      tempPlanningUnit.setTestcases(new ArrayList<>());
      tempPlanningUnit.setDescriptions(new ArrayList<>());
      tempPlanningUnit.setKindPlanningUnits(new HashSet<>());
      List<PlanningUnit> items = planningUnitSelect.getDataProvider()
                                                   .fetch(new Query<>())
                                                   .collect(Collectors.toList());
      if (!items.isEmpty()) {
        items.add(tempPlanningUnit);
      }
      planningUnitSelect.setItems(items);
      planningUnitSelect.setValue(tempPlanningUnit);
    }
    planningUnitPanel.setText(projectFromDB.getProjektName());
    planningUnitPanel.add(addParentPlanningUnitLayout);
    planningUnitPanel.add(planningUnitSelect);
  }

  private void setAddParentsButtonListener() {
    addParentsButton.addClickListener(
        (ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> new AddRootPlanningUnitsWindow(
            messagesBundle).open());
  }

  private void setAddParentButtonListener() {
    addParentButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
      try {
        final PlannedProject projekt             = daoFactory.getPlannedProjectDAO()
                                                             .findByID(VaadinSession.getCurrent()
                                                                                    .getAttribute(PlannedProject.class)
                                                                                    .getId());
        final PlanningUnit   newPlanningUnit     = new PlanningUnit();
        final String         newPlanningUnitName = addParentPlanningUnitField.getValue();
        if (newPlanningUnitName.matches(Constants.EMPTY_OR_SPACES_ONLY_PATTERN)) {
          throw new InvalidNameException();
        } else {
          newPlanningUnit.setPlanningUnitName(newPlanningUnitName);
        }
        if (newPlanningUnit.getParent() != null) {
          final String parentsPlanningUnitName = newPlanningUnit.getParent()
                                                                .getPlanningUnitName();
          if (newPlanningUnitName.equals(parentsPlanningUnitName)) {
            throw new SameNameException();
          }
        }
        final PlanningUnit foundPlanningUnit = daoFactory.getPlanningUnitDAO()
                                                         .loadPlanningUnitByName(newPlanningUnitName);
        if (foundPlanningUnit != null && foundPlanningUnit.getParent() == null) {
          throw new SameNameException();
        }
        daoFactory.saveOrUpdateTX(newPlanningUnit);
        newPlanningUnit.setKindPlanningUnits(new HashSet<>());
        if (newPlanningUnit.getParent() != null) {
          final PlanningUnit parentPlanningUnit = daoFactory.getPlanningUnitDAO()
                                                            .findByID(newPlanningUnit.getParent()
                                                                                     .getId());
          parentPlanningUnit.getKindPlanningUnits()
                            .add(newPlanningUnit);
          daoFactory.saveOrUpdateTX(parentPlanningUnit);
        }

        final List<RessourceGroup> ressourceGroups = daoFactory.getRessourceGroupDAO()
                                                               .loadAllEntities();
        if (newPlanningUnit.getParent() != null) {
          final Set<PlanningUnit> geschwisterPlanningUnits = newPlanningUnit.getParent()
                                                                            .getKindPlanningUnits();
          if (geschwisterPlanningUnits == null || geschwisterPlanningUnits.size() <= 1) {
            newPlanningUnit.setPlanningUnitElementList(new ArrayList<>());
            for (final PlanningUnitElement planningUnitElementFromParent : newPlanningUnit.getParent()
                                                                                          .getPlanningUnitElementList()) {
              final PlanningUnitElement planningUnitElement = new PlanningUnitElement();
              planningUnitElement.setRessourceGroup(planningUnitElementFromParent.getRessourceGroup());
              planningUnitElement.setPlannedMinutes(planningUnitElementFromParent.getPlannedMinutes());
              daoFactory.saveOrUpdateTX(planningUnitElement);
              newPlanningUnit.getPlanningUnitElementList()
                             .add(planningUnitElement);
            }
          } else {
            createNewPlanningUnitElements(newPlanningUnit, ressourceGroups, daoFactory);
          }
        } else {
          createNewPlanningUnitElements(newPlanningUnit, ressourceGroups, daoFactory);
        }
        final Benutzer notAssignedUser = daoFactory.getBenutzerDAO()
                                                   .findByID(1l);
        newPlanningUnit.setResponsiblePerson(notAssignedUser);
        daoFactory.saveOrUpdateTX(newPlanningUnit);
        if (newPlanningUnit.getParent() == null) {
          projekt.getPlanningUnits()
                 .add(newPlanningUnit);
        }
        daoFactory.saveOrUpdateTX(projekt);
        daoFactory.getEntityManager()
                  .refresh(projekt);
        addParentPlanningUnitField.clear();
        List<PlanningUnit> items = planningUnitSelect.getDataProvider()
                                                     .fetch(new Query<>())
                                                     .collect(Collectors.toList());
        if (!items.isEmpty()) {
          items.add(newPlanningUnit);
        }
        planningUnitSelect.setItems(items);
      } catch (final InvalidNameException e) {
        Notification.show(messagesBundle.getString("planning_invalidname"));
      } catch (final SameNameException e) {
        Notification.show(messagesBundle.getString("planning_samename"));
      }
    });
  }

  public void doInternationalization() {
    detailsPanel.setTitle(messagesBundle.getString("details"));
    addParentPlanningUnitField.setLabel(messagesBundle.getString("planning_fastadd"));
    addParentButton.setText("+");
    addParentsButton.setText("++");
    leftColumn.setTitle(messagesBundle.getString("planning_planningunits"));
    centerColumn.setTitle(messagesBundle.getString("planning_detailsandressources"));
    rightColumn.setTitle(messagesBundle.getString("planning_descriptionandtestcases"));
    addDescriptionOrTestCaseButton.setText("+");
  }

  public void fillTreePanel(final PlanningUnit selectedPlanningUnit, final PlannedProject projekt) {
    planningUnitsTree = new PlanningUnitsTree(this, selectedPlanningUnit);
    planningUnitsTree.setId("planningUnitsTree");
    planningUnitsTree.select(selectedPlanningUnit);
    planningUnitsTree.getListener()
                     .refreshTestCasesAndDescriptionsFor(selectedPlanningUnit);
    if (planningUnitsTreePanelLayout != null) {
      planningUnitsTreePanelLayout.removeListeners();
    }
    planningUnitsTreePanelLayout = new PlanningUnitsTreePanelLayout(projekt, ProjektplanungScreen.this);
    treePanel.removeAllComponents();
    treePanel.add(planningUnitsTreePanelLayout);
    final TreeValueChangeListener listener     = planningUnitsTree.getListener();
    final Button                  renameButton = planningUnitsTreePanelLayout.getRenameButton();
    listener.setDeleteButton(deleteButton);
    listener.setRenameButton(renameButton);
  }

  public void setComponents() {
    borderLayout.add(leftColumn);
    borderLayout.add(centerColumn);
    borderLayout.add(rightColumn);
//        borderLayout.setExpandRatio(leftColumn, 30);
//        borderLayout.setExpandRatio(centerColumn, 30);
//        borderLayout.setExpandRatio(rightColumn, 40);
    add(borderLayout);
  }

  private void createNewPlanningUnitElements(final PlanningUnit planningUnit,
                                             final List<RessourceGroup> ressourceGroups,
                                             final DaoFactory daoFactory) {
    planningUnit.setPlanningUnitElementList(new ArrayList<>());
    for (final RessourceGroup ressourceGroup : ressourceGroups) {
      final PlanningUnitElement planningUnitElement = new PlanningUnitElement();
      planningUnitElement.setPlannedMinutes(0);
      planningUnitElement.setRessourceGroup(ressourceGroup);
      daoFactory.saveOrUpdateTX(planningUnitElement);
      planningUnit.getPlanningUnitElementList()
                  .add(planningUnitElement);
    }
  }

  public PlanningUnitsTree getPlanningUnitsTree() {
    return planningUnitsTree;
  }

  public RapidPanel getDetailsPanel() {
    return detailsPanel;
  }

  public RapidPanel getMainPanel() {
    return mainPanel;
  }

  public RapidPanel getRessourcesPanel() {
    return ressourcesPanel;
  }

  public PlanningUnit getTempPlanningUnit() {
    return tempPlanningUnit;
  }

  public PlanningUnitsTreePanelLayout getPlanningUnitsTreePanelLayout() {
    return planningUnitsTreePanelLayout;
  }

  public PlanningUnitSelect getPlanningUnitSelect() {
    return planningUnitSelect;
  }

  public RapidPanel getRightColumn() {
    return rightColumn;
  }

  public Button getAddDescriptionOrTestCaseButton() {
    return addDescriptionOrTestCaseButton;
  }

  public Tabs getTabs() {
    return tabSheet;
  }

  public void setTabs(Tabs tabSheet) {
    this.tabSheet = tabSheet;
  }

  public Button getAddButton() {
    return addButton;
  }

  public Button getDeleteButton() {
    return deleteButton;
  }

}
