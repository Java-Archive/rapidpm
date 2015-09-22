package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze;

import com.vaadin.event.MouseEvents;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroupDAO;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ProjektFieldGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.StundensaetzeCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.StundensaetzeTableCreator;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit.EditGroupValueChangeListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit.TableEditFieldFactory;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks.AddRowClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks.DelRowClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ButtonComponent;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.EditOptionButtonGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ExternalDailyRateEditableLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ItemClickDependentComponent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static org.rapidpm.Constants.DECIMAL_FORMAT;
import static org.rapidpm.Constants.EUR;

//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;

public class StundensaetzeScreen extends Screen {

  private FormLayout betriebsFieldsLayout = new FormLayout();
  private ExternalDailyRateEditableLayout externerTagessatzLayout;
  private Panel externerTagessatzPanel = new Panel();
  private TextField mindestManntageField;
  private TextField betriebsStundeField;
  private Button addRowButton = new Button("+");
  private ButtonComponent delRowButton = new ButtonComponent("-");
  private GridLayout tabellenTasksLayout = new GridLayout(2, 2);
  private Table tabelle;
  private Label addDeleteRessourceLabel = new Label();
  private Label editModeLabel = new Label();
  private HorizontalLayout tabellenLayout = new HorizontalLayout();
  private VerticalLayout saveButtonLayout = new VerticalLayout();
  private Button saveButton = new Button();
  private OptionGroup optionGroup;
//    private StundensaetzeScreenBean screenBean;


  private List<ItemClickDependentComponent> dependentComponents = new ArrayList<>();

  public StundensaetzeScreen(final MainUI ui) {
    super(ui);
    try {
      final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
      final List<PlannedProject> plannedProjects = daoFactory.getPlannedProjectDAO().findAll();
      if (plannedProjects == null || plannedProjects.isEmpty()) {
        throw new NoProjectsException();
      }
      betriebsStundeField = new TextField();
      mindestManntageField = new TextField();

      betriebsFieldsLayout.addComponent(betriebsStundeField);
      betriebsFieldsLayout.addComponent(mindestManntageField);

      externerTagessatzLayout = new ExternalDailyRateEditableLayout(this, externerTagessatzPanel);
      externerTagessatzPanel.addClickListener(new MouseEvents.ClickListener() {
        @Override
        public void click(MouseEvents.ClickEvent event) {

          externerTagessatzLayout.setFieldGroup(new ProjektFieldGroup(externerTagessatzLayout
              .getCurrentProject(), messagesBundle));
          externerTagessatzLayout.setExternalDailyRateField((TextField) externerTagessatzLayout.getFieldGroup()
              .getField(PlannedProject.EXTERNALDAILYRATE));
          externerTagessatzLayout.getComponentsLayout().removeAllComponents();
          externerTagessatzLayout.getComponentsLayout().addComponent(externerTagessatzLayout.getExternalDailyRateField());
          externerTagessatzLayout.getExternalDailyRateField().focus();
          externerTagessatzLayout.getExternalDailyRateField().selectAll();
        }
      });
      externerTagessatzPanel.setContent(externerTagessatzLayout);
      externerTagessatzPanel.setSizeUndefined();
      externerTagessatzPanel.setStyleName(Reindeer.PANEL_LIGHT);
      dependentComponents.add(delRowButton);

      // formlayout wird bis zum itemclicklistener durchgereicht, savelayout
      // ebenfalls
      tabelle = new Table();
      tabelle.setImmediate(true);
      tabellenLayout.setSizeFull();
      tabellenLayout.addComponent(tabelle);
      tabellenLayout.setSpacing(true);

      optionGroup = new EditOptionButtonGroup(messagesBundle);
      saveButtonLayout.setSpacing(true);
      saveButtonLayout.addComponent(saveButton);
      optionGroup.addValueChangeListener(new EditGroupValueChangeListener(this, dependentComponents, delRowButton,
          messagesBundle, saveButtonLayout, optionGroup, saveButton, tabelle));
      optionGroup.setImmediate(true);

      delRowButton.setEnabled(false);
      delRowButton.addClickListener(new DelRowClickListener(this, delRowButton, messagesBundle));
      tabellenTasksLayout.setWidth("500px");
      tabellenTasksLayout.addComponent(addDeleteRessourceLabel);
      tabellenTasksLayout.addComponent(editModeLabel);
      final HorizontalLayout addDeleteLayout = new HorizontalLayout();

      addDeleteLayout.addComponent(addRowButton);
      addDeleteLayout.addComponent(delRowButton);
      tabellenTasksLayout.addComponent(addDeleteLayout);
      tabellenTasksLayout.addComponent(optionGroup);
      addRowButton.addClickListener(new AddRowClickListener(ui, this));


      //updatet die Table
      generateTableAndCalculate();

      doInternationalization();
      setComponents();
    } catch (final NoProjectsException e) {
      removeAllComponents();
      final NoProjectsScreen noProjectsScreen = new NoProjectsScreen(ui);
      addComponent(noProjectsScreen);
    }
  }

  public void generateTableAndCalculate() {
//        screenBean = EJBFactory.getEjbInstance(StundensaetzeScreenBean.class);
//        final DaoFactoryBean baseDaoFactoryBean = screenBean.getDaoFactoryBean();
    final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
    final RessourceGroupDAO ressourceGroupDAO = daoFactory.getRessourceGroupDAO();

    final List<RessourceGroup> ressourceGroups = ressourceGroupDAO.findAll();
    final List<RessourceGroup> containerBeans = new ArrayList<>();
    for (final RessourceGroup ressourceGroup : ressourceGroups) {
      containerBeans.add(ressourceGroup);
    }
    final StundensaetzeTableCreator creator = new StundensaetzeTableCreator(this, containerBeans);

    tabelle.setTableFieldFactory(new TableEditFieldFactory());

    final StundensaetzeCalculator calculator = new StundensaetzeCalculator(this, tabelle);
    calculator.calculate();

    final DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);
    tabelle.setColumnFooter(RessourceGroup.SUM_PER_MONTH, format.format(calculator.getSummeProMonat()) + EUR);
    tabelle.setColumnFooter(RessourceGroup.SUM_PER_DAY, format.format(calculator.getSummeProTag()) + EUR);
    tabelle.setColumnFooter(RessourceGroup.NAME, StundensaetzeCalculator.GESAMTSUMMEN);

    mindestManntageField.setReadOnly(false);
    betriebsStundeField.setReadOnly(false);

    betriebsStundeField.setValue(format.format(calculator.getBetriebsStunde()) + EUR);
    mindestManntageField.setValue(format.format(calculator.getMindestManntage()));

    mindestManntageField.setReadOnly(true);
    betriebsStundeField.setReadOnly(true);

    tabelle.setEditable(false);
    tabelle.setSelectable(true);
  }

  @Override
  public void doInternationalization() {
    betriebsStundeField.setCaption(messagesBundle.getString("stdsatz_businesshour"));
    mindestManntageField.setCaption(messagesBundle.getString("stdsatz_businessMinManDays"));
    saveButton.setCaption(messagesBundle.getString("save"));
    editModeLabel.setValue(messagesBundle.getString("stdsatz_editMode"));
    addDeleteRessourceLabel.setValue(messagesBundle.getString("stdsatz_addDelete"));
    optionGroup.setValue(messagesBundle.getString("stdsatz_rowmode"));

  }

  @Override
  public void setComponents() {
    addComponent(betriebsFieldsLayout);
    addComponent(externerTagessatzPanel);
    addComponent(tabellenTasksLayout);
    addComponent(saveButtonLayout);
    addComponent(tabellenLayout);
  }

    /* getters and setters */

  public Table getTabelle() {
    return tabelle;
  }

  public VerticalLayout getSaveButtonLayout() {
    return saveButtonLayout;
  }
}
