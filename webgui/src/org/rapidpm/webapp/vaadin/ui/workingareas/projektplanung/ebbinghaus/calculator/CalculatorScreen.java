package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator;

import com.vaadin.external.com.ibm.icu.text.DecimalFormat;
import com.vaadin.terminal.gwt.client.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.Constants;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.logic.CalculatorFieldsComputer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.logic.CalculatorTableCreator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.logic.tableedit.EditGroupValueChangeListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.logic.tasks.AddRowClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.logic.tasks.DelRowClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.uicomponents.ButtonComponent;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.uicomponents.EditOptionButtonGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.uicomponents.ItemClickDependentComponent;

import java.util.ArrayList;

public class CalculatorScreen extends Screen
{

    private Root root;
	private GridLayout gridLayout;
	private TextField betriebsfraField;
	private TextField betriebsstdField;
	private Button addRowButton = new Button("+");
	private ButtonComponent delRowButton = new ButtonComponent("-");
	private GridLayout tabellenTasksLayout = new GridLayout(2,2);
	private Table tabelle;
	private HorizontalLayout tabellenLayout = new HorizontalLayout();
	private GridLayout formLayout = new GridLayout(2, 2);
	private Button saveButton = new Button("Speichern");
	private EditOptionButtonGroup optionGroup = new EditOptionButtonGroup();

	
	private ArrayList<ItemClickDependentComponent> dependentComponents = new ArrayList<ItemClickDependentComponent>();

	public CalculatorScreen(Root root)
	{
        this.root = root;
		saveButton.setVisible(false);
		final Label betriebsstdLabel = new Label("Betriebsstunde / JTEL:");
		betriebsstdField = new TextField();
		final Label betriebsfraLabel = new Label("Betriebs??? / JTEL:");
		betriebsfraLabel.setContentMode(ContentMode.TEXT);
		betriebsfraField = new TextField();
		betriebsfraField.setEnabled(false);
		betriebsstdField.setEnabled(false);

		gridLayout = new GridLayout(2, 2);
		gridLayout.setMargin(true, false, true, false);
		gridLayout.setSpacing(true);
		gridLayout.addComponent(betriebsstdLabel);
		gridLayout.addComponent(betriebsstdField);
		gridLayout.addComponent(betriebsfraLabel);
		gridLayout.addComponent(betriebsfraField);
		gridLayout.setComponentAlignment(betriebsfraField,
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
				dependentComponents, delRowButton, formLayout, saveButton,
				betriebsfraField, betriebsstdField);

		tabelle = creator.getTabelle();
		tabellenLayout.addComponent(tabelle);
		tabellenLayout.setSpacing(true);
		tabellenLayout.setMargin(false, false, true, false);
		
		optionGroup.addListener(new EditGroupValueChangeListener(formLayout, optionGroup, betriebsstdField, betriebsfraField, saveButton, tabelle));
		optionGroup.setValue(Constants.ROWEDIT);
		optionGroup.setImmediate(true);

		delRowButton.setEnabled(false);
		delRowButton.addListener(new DelRowClickListener(this, delRowButton));
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
		

		final CalculatorFieldsComputer computer = new CalculatorFieldsComputer(
				tabelle);
		computer.compute();

		final DecimalFormat f = new DecimalFormat("0.00");
		betriebsstdField.setValue(f.format(computer.getBetriebsStunde())
				+ Constants.EUR);
		betriebsfraField.setValue(f.format(computer.getBetriebsFra())
				+ Constants.EUR);

        setComponents();
	}


	private void setComponents()
	{
		addComponent(gridLayout);
		addComponent(tabellenTasksLayout);
		addComponent(tabellenLayout);
		addComponent(formLayout);
		addComponent(saveButton);
	}

	/* getters and setters */

	public GridLayout getUpperHoriLayout()
	{
		return gridLayout;
	}

	public void setUpperHoriLayout(GridLayout upperHoriLayout)
	{
		this.gridLayout = upperHoriLayout;
	}

	public Table getTabelle()
	{
		return tabelle;
	}

	public void setTabelle(Table tabelle)
	{
		this.tabelle = tabelle;
	}

	public TextField getBetriebsfraField()
	{
		return betriebsfraField;
	}

	public void setBetriebsfraField(TextField betriebsfraField)
	{
		this.betriebsfraField = betriebsfraField;
	}

	public TextField getBetriebsstdField()
	{
		return betriebsstdField;
	}

	public void setBetriebsstdField(TextField betriebsstdField)
	{
		this.betriebsstdField = betriebsstdField;
	}

}
