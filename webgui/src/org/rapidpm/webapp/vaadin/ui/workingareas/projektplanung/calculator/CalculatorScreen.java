package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator;

import com.vaadin.external.com.ibm.icu.text.DecimalFormat;
import com.vaadin.terminal.gwt.client.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.Constants;
import org.rapidpm.webapp.vaadin.MainRoot;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.logic.CalculatorFieldsComputer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.logic.CalculatorTableCreator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.logic.tableedit.EditGroupValueChangeListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.logic.tasks.AddRowClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.logic.tasks.DelRowClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.uicomponents.ButtonComponent;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.uicomponents.EditOptionButtonGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.uicomponents.ItemClickDependentComponent;

import java.util.ArrayList;

public class CalculatorScreen extends Screen {

    private MainRoot root;
    private GridLayout gridLayout;
    private TextField betriebsWertField;
    private TextField betriebsstdField;
    private Button addRowButton = new Button("+");
    private ButtonComponent delRowButton = new ButtonComponent("-");
    private GridLayout tabellenTasksLayout = new GridLayout(2, 2);
    private Table tabelle;
    private HorizontalLayout tabellenLayout = new HorizontalLayout();
    private VerticalLayout formLayout = new VerticalLayout();
    private GridLayout upperFormLayout = new GridLayout(2, 2);
    private HorizontalLayout lowerFormLayout = new HorizontalLayout();
    private Button saveButton = new Button("Speichern");
    private EditOptionButtonGroup optionGroup = new EditOptionButtonGroup();


    private ArrayList<ItemClickDependentComponent> dependentComponents = new ArrayList<ItemClickDependentComponent>();

    public CalculatorScreen(MainRoot root) {
        this.root = root;
        saveButton.setVisible(false);
        final Label betriebsstdLabel = new Label("Betriebsstunde / JTEL:");
        betriebsstdField = new TextField();
        final Label betriebsfraLabel = new Label("Betriebs??? / JTEL:");
        betriebsfraLabel.setContentMode(ContentMode.TEXT);
        betriebsWertField = new TextField();
        betriebsWertField.setEnabled(false);
        betriebsstdField.setEnabled(false);

        gridLayout = new GridLayout(2, 2);
        gridLayout.setMargin(true, false, true, false);
        gridLayout.setSpacing(true);
        gridLayout.addComponent(betriebsstdLabel);
        gridLayout.addComponent(betriebsstdField);
        gridLayout.addComponent(betriebsfraLabel);
        gridLayout.addComponent(betriebsWertField);
        gridLayout.setComponentAlignment(betriebsWertField,
                Alignment.MIDDLE_CENTER);
        gridLayout.setComponentAlignment(betriebsstdField,
                Alignment.MIDDLE_CENTER);
        gridLayout.setComponentAlignment(betriebsfraLabel,
                Alignment.MIDDLE_RIGHT);
        gridLayout.setComponentAlignment(betriebsstdLabel,
                Alignment.MIDDLE_RIGHT);

        dependentComponents.add(delRowButton);

        // formlayout wird bis zum itemclicklistener durchgereicht, savelayout
        // ebenfalls
        final CalculatorTableCreator creator = new CalculatorTableCreator(
                root.getRessourceGroupsBean(), dependentComponents, delRowButton, upperFormLayout, lowerFormLayout, formLayout, saveButton,
                betriebsWertField, betriebsstdField);

        tabelle = creator.getTabelle();
        tabellenLayout.addComponent(tabelle);
        tabellenLayout.setSpacing(true);
        tabellenLayout.setMargin(false, false, true, false);

        optionGroup.addListener(new EditGroupValueChangeListener(formLayout, upperFormLayout, lowerFormLayout, optionGroup, betriebsstdField, betriebsWertField, saveButton, tabelle));
        optionGroup.setValue(Constants.ROWEDIT);
        optionGroup.setImmediate(true);

        delRowButton.setEnabled(false);
        delRowButton.addListener(new DelRowClickListener(root, this, delRowButton));
        tabellenTasksLayout.setWidth("500px");
        tabellenTasksLayout.addComponent(new Label("Ressourcen hinzufuegen / loeschen:"));
        tabellenTasksLayout.addComponent(new Label("Editiermodus:"));
        final HorizontalLayout addDeleteLayout = new HorizontalLayout();

        addDeleteLayout.addComponent(addRowButton);
        addDeleteLayout.addComponent(delRowButton);
        //addDeleteLayout.setSpacing(true);
        tabellenTasksLayout.addComponent(addDeleteLayout);
        tabellenTasksLayout.addComponent(optionGroup);
        addRowButton.addListener(new AddRowClickListener(root, this));
        //tabellenTasksLayout.setSpacing(true);

        formLayout.setSpacing(true);
        lowerFormLayout.addComponent(saveButton);
        formLayout.addComponent(upperFormLayout);
        formLayout.addComponent(lowerFormLayout);


        final CalculatorFieldsComputer computer = new CalculatorFieldsComputer(
                tabelle);
        computer.compute();

        final DecimalFormat f = new DecimalFormat("0.00");
        betriebsstdField.setValue(f.format(computer.getBetriebsStunde())
                + Constants.EUR);
        betriebsWertField.setValue(f.format(computer.getBetriebsWert())
                + Constants.EUR);

        setComponents();
    }


    private void setComponents() {
        addComponent(gridLayout);
        addComponent(tabellenTasksLayout);
        addComponent(tabellenLayout);
        addComponent(formLayout);
    }

    /* getters and setters */

    public GridLayout getUpperHoriLayout() {
        return gridLayout;
    }

    public void setUpperHoriLayout(GridLayout upperHoriLayout) {
        this.gridLayout = upperHoriLayout;
    }

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
}
