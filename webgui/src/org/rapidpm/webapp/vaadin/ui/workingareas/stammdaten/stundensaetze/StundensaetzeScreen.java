package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze;

import com.vaadin.external.com.ibm.icu.text.DecimalFormat;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.MainRoot;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.StundensaetzeFieldsCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.StundensaetzeTableCreator;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit.EditGroupValueChangeListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks.AddRowClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks.DelRowClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ButtonComponent;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.EditOptionButtonGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ItemClickDependentComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.*;

public class StundensaetzeScreen extends Screen {

    private MainRoot root;

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


    private List<ItemClickDependentComponent> dependentComponents = new ArrayList<>();

    public StundensaetzeScreen(MainRoot root) {
        super(root);
        saveButton.setVisible(false);
        betriebsstdField = new TextField();
        betriebsWertField = new TextField();
        betriebsWertField.setEnabled(false);
        betriebsstdField.setEnabled(false);

        betriebsFieldsLayout.addComponent(betriebsstdField);
        betriebsFieldsLayout.addComponent(betriebsWertField);
        betriebsFieldsLayout.setMargin(true, false, true, false);


        dependentComponents.add(delRowButton);

        // formlayout wird bis zum itemclicklistener durchgereicht, savelayout
        // ebenfalls
        final StundensaetzeTableCreator creator = new StundensaetzeTableCreator(
                root, dependentComponents, delRowButton, upperFormLayout, lowerFormLayout,
                formLayout, saveButton, betriebsWertField, betriebsstdField);

        tabelle = creator.getTabelle();
        tabellenLayout.addComponent(tabelle);
        tabellenLayout.setSpacing(true);
        tabellenLayout.setMargin(false, false, true, false);

        optionGroup = new EditOptionButtonGroup(messagesBundle);
        optionGroup.addListener(new EditGroupValueChangeListener(messagesBundle, formLayout, upperFormLayout,
                lowerFormLayout, optionGroup, betriebsstdField, betriebsWertField, saveButton, tabelle));
        optionGroup.setImmediate(true);

        delRowButton.setEnabled(false);
        delRowButton.addListener(new DelRowClickListener(root, this, delRowButton));
        tabellenTasksLayout.setWidth("500px");
        tabellenTasksLayout.addComponent(addDeleteRessourceLabel);
        tabellenTasksLayout.addComponent(editModeLabel);
        final HorizontalLayout addDeleteLayout = new HorizontalLayout();

        addDeleteLayout.addComponent(addRowButton);
        addDeleteLayout.addComponent(delRowButton);
        tabellenTasksLayout.addComponent(addDeleteLayout);
        tabellenTasksLayout.addComponent(optionGroup);
        addRowButton.addListener(new AddRowClickListener(root, this));

        formLayout.setSpacing(true);
        lowerFormLayout.addComponent(saveButton);
        formLayout.addComponent(upperFormLayout);
        formLayout.addComponent(lowerFormLayout);


        final StundensaetzeFieldsCalculator calculator = new StundensaetzeFieldsCalculator(
                tabelle);
        calculator.compute();

        final DecimalFormat f = new DecimalFormat(DECIMAL_FORMAT);
        betriebsstdField.setValue(f.format(calculator.getBetriebsStunde()) + EUR);
        betriebsWertField.setValue(f.format(calculator.getBetriebsWert()) + EUR);

        doInternationalization();
        setComponents();
    }

    @Override
    protected void doInternationalization() {
        betriebsstdField.setCaption(messagesBundle.getString("stdsatz_businesshour"));
        betriebsWertField.setCaption(messagesBundle.getString("stdsatz_businessvalue"));
        saveButton.setCaption(messagesBundle.getString("save"));
        editModeLabel.setValue(messagesBundle.getString("stdsatz_editMode"));
        addDeleteRessourceLabel.setValue(messagesBundle.getString("stdsatz_addDelete"));
        optionGroup.setValue(messagesBundle.getString("stdsatz_rowmode"));

    }


    private void setComponents() {
        addComponent(betriebsFieldsLayout);
        addComponent(tabellenTasksLayout);
        addComponent(tabellenLayout);
        addComponent(formLayout);
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
}
