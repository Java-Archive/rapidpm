package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning;

import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.Constants;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.webapp.vaadin.MainUI;
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
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.04.12
 * Time: 09:43
 */
public class ProjektplanungScreen extends Screen {

  public static final Logger logger = Logger.getLogger(ProjektplanungScreen.class);
  public static final long PLATZHALTER_ID = 666l;
  private HorizontalLayout borderLayout = new HorizontalLayout();
  private RapidPanel leftColumn = new RapidPanel();
  private RapidPanel centerColumn = new RapidPanel();
  private RapidPanel rightColumn = new RapidPanel();
  private RapidPanel mainPanel;
  private RapidPanel ressourcesPanel;
  private RapidPanel planningUnitPanel;
  private RapidPanel treePanel;
  private RapidPanel detailsPanel;
  private PlanningUnitSelect planningUnitSelect;
  private PlanningUnitsTree planningUnitsTree;
  private PlanningUnitsTreePanelLayout planningUnitsTreePanelLayout;
  private HorizontalLayout addParentPlanningUnitLayout = new HorizontalLayout();
  private TextField addParentPlanningUnitField = new TextField();
  private Button addParentButton = new Button();
  private PlanningUnit tempPlanningUnit = new PlanningUnit();
  private DaoFactory daoFactory = DaoFactorySingleton.getInstance();
  private TabSheet tabSheet = new TabSheet();
  //Buttons im TreePanelLayout
  private Button addButton = new Button("+");
  private Button deleteButton = new Button("-");
  private Button addParentsButton = new Button();
  private Button addDescriptionOrTestCaseButton = new Button();


  public ProjektplanungScreen(final MainUI ui) {
    super(ui);
    addParentPlanningUnitField.focus();

    try {
      final List<PlannedProject> plannedProjects = daoFactory.getPlannedProjectDAO().findAll();
      if (plannedProjects == null || plannedProjects.isEmpty()) {
        throw new NoProjectsException();
      }

      final PlanningCalculator calculator = new PlanningCalculator(messagesBundle, ui);
      calculator.calculate();

      addDescriptionOrTestCaseButton.addClickListener(event -> ui.addWindow(new AddDescriptionsOrTestCasesWindow((PlanningUnit) planningUnitsTree.getValue(), ui,
          messagesBundle)));

      leftColumn.setSizeFull();
      centerColumn.setSizeFull();
      rightColumn.setSizeFull();

      rightColumn.addComponent(addDescriptionOrTestCaseButton);
      rightColumn.addComponent(tabSheet);

      planningUnitPanel = new RapidPanel();
      treePanel = new RapidPanel();
      detailsPanel = new RapidPanel();
      ressourcesPanel = new RapidPanel();

      leftColumn.addComponent(planningUnitPanel);
      leftColumn.addComponent(treePanel);

      centerColumn.addComponent(detailsPanel);
      centerColumn.addComponent(ressourcesPanel);
      mainPanel = new RapidPanel();

      ressourcesPanel.setSizeFull();
      borderLayout.setSizeFull();

      //rightColumn.addComponent();

      buildPlanningUnitPanel();
      doInternationalization();
      setComponents();
    } catch (final NoProjectsException e) {
      removeAllComponents();
      final NoProjectsScreen noProjectsScreen = new NoProjectsScreen(ui);
      addComponent(noProjectsScreen);
    }

  }

  private void buildPlanningUnitPanel() {
    setAddParentButtonListener();
    setAddParentsButtonListener();
    addParentPlanningUnitLayout.addComponents(addParentPlanningUnitField, addParentButton, addParentsButton);
    addParentButton.setSizeUndefined();
    addParentsButton.setSizeUndefined();
    addParentPlanningUnitLayout.setComponentAlignment(addParentButton, Alignment.BOTTOM_LEFT);
    addParentPlanningUnitLayout.setComponentAlignment(addParentsButton, Alignment.BOTTOM_LEFT);
    planningUnitSelect = new PlanningUnitSelect(ui);
    planningUnitSelect.addListenerComponent(deleteButton);
    planningUnitSelect.addListenerComponent(addButton);
    addParentPlanningUnitField.setWidth("160px");
    final PlannedProject projectFromDB = planningUnitSelect.getProjectFromDB();
    final List<?> ids = (List<?>) planningUnitSelect.getItemIds();
    planningUnitSelect.addValueChangeListener(valueChangeEvent -> {
      final PlanningUnit planningUnitFromSelect = (PlanningUnit) valueChangeEvent.getProperty().getValue();
      PlanningUnit planningUnitFromDB = daoFactory.getPlanningUnitDAO().findByID
          (planningUnitFromSelect.getId(), true);
      if (planningUnitFromDB != null) {
//                    daoFactory.getEntityManager().refresh(planningUnitFromDB);
      } else {
        planningUnitFromDB = planningUnitFromSelect;
      }
      treePanel.removeAllComponents();
      detailsPanel.removeAllComponents();
      treePanel.setCaption(planningUnitFromSelect.getPlanningUnitName());
      fillTreePanel(planningUnitFromDB, projectFromDB);
    });
    if (ids != null && !ids.isEmpty()) {
      final PlanningUnit firstPlanningUnit = daoFactory.getPlanningUnitDAO().findByID(((PlanningUnit) ids.get(0)).getId(), true);
//            daoFactory.getEntityManager().refresh(firstPlanningUnit);
      planningUnitSelect.setValue(firstPlanningUnit);
    } else {
      tempPlanningUnit.setId("666");
      tempPlanningUnit.setPlanningUnitName("Platzhalter");
      tempPlanningUnit.setTestcases(new ArrayList<>());
      tempPlanningUnit.setDescriptions(new ArrayList<>());
      tempPlanningUnit.setKindPlanningUnits(new ArrayList<>());
      planningUnitSelect.addItem(tempPlanningUnit);
      planningUnitSelect.setValue(tempPlanningUnit);
    }
    planningUnitPanel.setCaption(projectFromDB.getProjektName());
    planningUnitPanel.addComponent(addParentPlanningUnitLayout);
    planningUnitPanel.addComponent(planningUnitSelect);
  }

  @Override
  public void doInternationalization() {
    detailsPanel.setCaption(messagesBundle.getString("details"));
    addParentPlanningUnitField.setCaption(messagesBundle.getString("planning_fastadd"));
    addParentButton.setCaption("+");
    addParentsButton.setCaption("++");
    leftColumn.setCaption(messagesBundle.getString("planning_planningunits"));
    centerColumn.setCaption(messagesBundle.getString("planning_detailsandressources"));
    rightColumn.setCaption(messagesBundle.getString("planning_descriptionandtestcases"));
    addDescriptionOrTestCaseButton.setCaption("+");
  }

  @Override
  public void setComponents() {
    activeVerticalFullScreenSize(true);
    borderLayout.addComponent(leftColumn);
    borderLayout.addComponent(centerColumn);
    borderLayout.addComponent(rightColumn);
    borderLayout.setExpandRatio(leftColumn, 30);
    borderLayout.setExpandRatio(centerColumn, 30);
    borderLayout.setExpandRatio(rightColumn, 40);
    addComponent(borderLayout);
  }

  private void setAddParentButtonListener() {
    addParentButton.addClickListener(event -> {
      try {
        final PlannedProject projekt = daoFactory.getPlannedProjectDAO()
            .findByID(ui.getCurrentProject().getId(), true);
        PlanningUnit newPlanningUnit = new PlanningUnit();
        final String newPlanningUnitName = addParentPlanningUnitField.getValue();
        if (newPlanningUnitName.matches(Constants.EMPTY_OR_SPACES_ONLY_PATTERN)) {
          throw new InvalidNameException();
        } else {
          newPlanningUnit.setPlanningUnitName(newPlanningUnitName);
        }
        if (newPlanningUnit.getParent() != null) {
          final String parentsPlanningUnitName = newPlanningUnit.getParent().getPlanningUnitName();
          if (newPlanningUnitName.equals(parentsPlanningUnitName)) {
            throw new SameNameException();
          }
        }
        final PlanningUnit foundPlanningUnit = daoFactory.getPlanningUnitDAO().loadPlanningUnitByName
            (newPlanningUnitName);
        if (foundPlanningUnit != null && foundPlanningUnit.getParent() == null) {
          throw new SameNameException();
        }
        newPlanningUnit.setKindPlanningUnits(new ArrayList<>());
        if (newPlanningUnit.getParent() != null) {
          final List<PlanningUnit> geschwisterPlanningUnits = newPlanningUnit.getParent().getKindPlanningUnits();
          if (geschwisterPlanningUnits == null || geschwisterPlanningUnits.size() <= 1) {
            newPlanningUnit.setPlanningUnitElementList(new ArrayList<>());
            for (final PlanningUnitElement planningUnitElementFromParent : newPlanningUnit.getParent().getPlanningUnitElementList()) {
              final PlanningUnitElement planningUnitElement = new PlanningUnitElement();
              planningUnitElement.setRessourceGroup(planningUnitElementFromParent.getRessourceGroup());
              planningUnitElement.setPlannedMinutes(planningUnitElementFromParent.getPlannedMinutes());
              newPlanningUnit.getPlanningUnitElementList().add(planningUnitElement);
            }
          } else {
            addTemporaryPlanningUnitElementsToPlanningUnit(newPlanningUnit);
          }
        } else {
          addTemporaryPlanningUnitElementsToPlanningUnit(newPlanningUnit);
        }
        final Benutzer notAssignedUser = daoFactory.getBenutzerDAO().findByID(ui.getCurrentUser().getId(), true);
        newPlanningUnit.setResponsiblePerson(notAssignedUser);
        try {
          newPlanningUnit = daoFactory.getPlanningUnitDAO().createEntityFull(newPlanningUnit);
        } catch (NotYetImplementedException | InvalidKeyException | MissingNonOptionalPropertyException e) {
          Notification.show(e.getMessage());
        }
        if (newPlanningUnit.getParent() == null) {
          daoFactory.getProjectDAO().addPlanningUnitToProject(newPlanningUnit, projekt);
        } else {
          final PlanningUnit parentPlanningUnit = daoFactory.getPlanningUnitDAO().findByID(newPlanningUnit.getParent().getId(), false);
          daoFactory.getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(parentPlanningUnit, newPlanningUnit);
        }
        final MainUI ui1 = getUi();
        ui1.setWorkingArea(new ProjektplanungScreen(ui1));
      } catch (final InvalidNameException e) {
        Notification.show(messagesBundle.getString("planning_invalidname"));
      } catch (final SameNameException e) {
        Notification.show(messagesBundle.getString("planning_samename"));
      }
    });
  }

  private void setAddParentsButtonListener() {
    addParentsButton.addClickListener(event -> {
      final AddRootPlanningUnitsWindow addRootPlanningUnitsWindow = new AddRootPlanningUnitsWindow(ui, messagesBundle);
      ui.addWindow(addRootPlanningUnitsWindow);
    });
  }

  public void fillTreePanel(PlanningUnit selectedPlanningUnit, final PlannedProject projekt) {
    try {
      selectedPlanningUnit = DaoFactorySingleton.getInstance().getPlanningUnitDAO().loadFull(selectedPlanningUnit);
    } catch (InvalidKeyException | NotYetImplementedException e) {
      Notification.show("DBError: " + e.getMessage());
    }
    planningUnitsTree = new PlanningUnitsTree(this, selectedPlanningUnit);
    planningUnitsTree.select(selectedPlanningUnit);
    planningUnitsTreePanelLayout = new PlanningUnitsTreePanelLayout(projekt, ProjektplanungScreen.this);
    treePanel.removeAllComponents();
    treePanel.addComponent(planningUnitsTreePanelLayout);
    final TreeValueChangeListener listener = planningUnitsTree.getListener();
    final Button renameButton = planningUnitsTreePanelLayout.getRenameButton();
    listener.setDeleteButton(deleteButton);
    listener.setRenameButton(renameButton);
  }

  private void addTemporaryPlanningUnitElementsToPlanningUnit(final PlanningUnit planningUnit) {
    planningUnit.setPlanningUnitElementList(new ArrayList<>());
    final List<RessourceGroup> ressourceGroups = daoFactory.getRessourceGroupDAO().findAll();
    for (final RessourceGroup ressourceGroup : ressourceGroups) {
      final PlanningUnitElement planningUnitElement = new PlanningUnitElement();
      planningUnitElement.setPlannedMinutes(0);
      planningUnitElement.setRessourceGroup(ressourceGroup);
      planningUnit.getPlanningUnitElementList().add(planningUnitElement);
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

  public TabSheet getTabSheet() {
    return tabSheet;
  }

  public void setTabSheet(TabSheet tabSheet) {
    this.tabSheet = tabSheet;
  }

  public Button getAddButton() {
    return addButton;
  }

  public Button getDeleteButton() {
    return deleteButton;
  }

}
