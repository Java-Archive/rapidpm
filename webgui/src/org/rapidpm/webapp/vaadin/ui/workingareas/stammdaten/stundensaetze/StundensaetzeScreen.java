package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze;

import com.vaadin.ui.*;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroupDAO;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.StundensaetzeCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.StundensaetzeTableCreator;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit.EditGroupValueChangeListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit.TableEditFieldFactory;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks.AddRowClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks.DelRowClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ButtonComponent;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.EditOptionButtonGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ItemClickDependentComponent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static org.rapidpm.Constants.DECIMAL_FORMAT;
import static org.rapidpm.Constants.EUR;

public class StundensaetzeScreen extends Screen {

    private FormLayout betriebsFieldsLayout = new FormLayout();
    private TextField betriebsWertField;
    private TextField betriebsstdField;
    private Button addRowButton = new Button("+");
    private ButtonComponent delRowButton = new ButtonComponent("-");
    private GridLayout tabellenTasksLayout = new GridLayout(2, 2);
    private Table tabelle;
    private Label addDeleteRessourceLabel = new Label();
    private Label editModeLabel = new Label();
    private HorizontalLayout tabellenLayout = new HorizontalLayout();
    private VerticalLayout formLayout = new VerticalLayout();
    private GridLayout upperFormLayout = new GridLayout(2, 2);
    private HorizontalLayout lowerFormLayout = new HorizontalLayout();
    private Button saveButton = new Button();
    private OptionGroup optionGroup;
    private StundensaetzeScreenBean screenBean;


    private List<ItemClickDependentComponent> dependentComponents = new ArrayList<>();

    public StundensaetzeScreen(final MainUI ui) {
        super(ui);
        saveButton.setVisible(false);
        betriebsstdField = new TextField();
        betriebsWertField = new TextField();
        betriebsWertField.setEnabled(false);
        betriebsstdField.setEnabled(false);

        betriebsFieldsLayout.addComponent(betriebsstdField);
        betriebsFieldsLayout.addComponent(betriebsWertField);


        dependentComponents.add(delRowButton);

        // formlayout wird bis zum itemclicklistener durchgereicht, savelayout
        // ebenfalls
        tabelle = new Table();
        tabelle.setImmediate(true);
        tabellenLayout.setSizeFull();
        tabellenLayout.addComponent(tabelle);
        tabellenLayout.setSpacing(true);

        optionGroup = new EditOptionButtonGroup(messagesBundle);
        optionGroup.addValueChangeListener(new EditGroupValueChangeListener(this, messagesBundle, formLayout,
                upperFormLayout,
                lowerFormLayout, optionGroup, saveButton, tabelle));
        optionGroup.setImmediate(true);

        delRowButton.setEnabled(false);
        delRowButton.addClickListener(new DelRowClickListener(this, delRowButton));
        tabellenTasksLayout.setWidth("500px");
        tabellenTasksLayout.addComponent(addDeleteRessourceLabel);
        tabellenTasksLayout.addComponent(editModeLabel);
        final HorizontalLayout addDeleteLayout = new HorizontalLayout();

        addDeleteLayout.addComponent(addRowButton);
        addDeleteLayout.addComponent(delRowButton);
        tabellenTasksLayout.addComponent(addDeleteLayout);
        tabellenTasksLayout.addComponent(optionGroup);
        addRowButton.addClickListener(new AddRowClickListener(ui, this));

        formLayout.setSpacing(true);
        lowerFormLayout.addComponent(saveButton);
        formLayout.addComponent(upperFormLayout);
        formLayout.addComponent(lowerFormLayout);

        //updatet die Table
        generateTableAndCalculate();

        doInternationalization();
        setComponents();
    }

    public void generateTableAndCalculate() {
        screenBean = EJBFactory.getEjbInstance(StundensaetzeScreenBean.class);
        final DaoFactoryBean baseDaoFactoryBean = screenBean.getDaoFactoryBean();
        final RessourceGroupDAO ressourceGroupDAO = baseDaoFactoryBean.getRessourceGroupDAO();
        final List<RessourceGroup> ressourceGroups = ressourceGroupDAO.loadAllEntities();
        final List<RessourceGroup> containerBeans = new ArrayList<>();
        for(final RessourceGroup ressourceGroup : ressourceGroups){
            containerBeans.add(ressourceGroup);
        }
        final StundensaetzeTableCreator creator = new StundensaetzeTableCreator(
                this, containerBeans, dependentComponents, delRowButton, upperFormLayout, lowerFormLayout,
                formLayout, saveButton);

        tabelle.setTableFieldFactory(new TableEditFieldFactory());

        final StundensaetzeCalculator calculator = new StundensaetzeCalculator(tabelle);
        calculator.calculate();

        final DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);
        tabelle.setColumnFooter(RessourceGroup.SUM_PER_MONTH, format.format(calculator.getSummeProMonat())+ EUR);
        tabelle.setColumnFooter(RessourceGroup.SUM_PER_DAY, format.format(calculator.getSummeProTag()) + EUR);
        tabelle.setColumnFooter(RessourceGroup.NAME, StundensaetzeCalculator.GESAMTSUMMEN);

        betriebsstdField.setValue(format.format(calculator.getBetriebsStunde()) + EUR);
        betriebsWertField.setValue(format.format(calculator.getBetriebsWert()) + EUR);

        tabelle.setEditable(false);
    }

    @Override
    public void doInternationalization() {
        betriebsstdField.setCaption(messagesBundle.getString("stdsatz_businesshour"));
        betriebsWertField.setCaption(messagesBundle.getString("stdsatz_businessvalue"));
        saveButton.setCaption(messagesBundle.getString("save"));
        editModeLabel.setValue(messagesBundle.getString("stdsatz_editMode"));
        addDeleteRessourceLabel.setValue(messagesBundle.getString("stdsatz_addDelete"));
        optionGroup.setValue(messagesBundle.getString("stdsatz_rowmode"));

    }

    @Override
    public void setComponents() {
        addComponent(betriebsFieldsLayout);
        addComponent(tabellenTasksLayout);
        addComponent(formLayout);
        addComponent(tabellenLayout);
    }

    /* getters and setters */

    public Table getTabelle() {
        return tabelle;
    }

    public void setTabelle(Table tabelle) {
        this.tabelle = tabelle;
    }

    public TextField getBetriebsWertField() {
        return betriebsWertField;
    }

    public void setBetriebsWertField(TextField betriebsWertField) {
        this.betriebsWertField = betriebsWertField;
    }

    public TextField getBetriebsstdField() {
        return betriebsstdField;
    }

    public void setBetriebsstdField(TextField betriebsstdField) {
        this.betriebsstdField = betriebsstdField;
    }

    public VerticalLayout getFormLayout() {
        return formLayout;
    }

    public void setFormLayout(VerticalLayout formLayout) {
        this.formLayout = formLayout;
    }

    public StundensaetzeScreenBean getScreenBean() {
        return screenBean;
    }
}
