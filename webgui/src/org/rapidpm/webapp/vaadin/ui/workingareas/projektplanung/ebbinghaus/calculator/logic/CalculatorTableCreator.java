package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.logic;

import com.vaadin.ui.Button;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.datenmodell.RowBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.datenmodell.RowContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.logic.tablelisteners.CalculatorItemClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.logic.tablelisteners.CalculatorItemSetChangeListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.uicomponents.ItemClickDependentComponent;

import java.util.ArrayList;
import java.util.Locale;

public class CalculatorTableCreator
{
	private Table tabelle;

	public CalculatorTableCreator(
			ArrayList<ItemClickDependentComponent> itemClickdependentComponents,
			Button deleteButton, Layout formLayout, Button saveButton,
			TextField betriebsFraField, TextField betriebsStdField)
	{
		tabelle = new Table();
		tabelle.setLocale(Locale.GERMANY);
		tabelle.setColumnCollapsingAllowed(true);
		tabelle.setColumnReorderingAllowed(true);
		tabelle.setImmediate(true);
		tabelle.setSelectable(true);
		tabelle.setNullSelectionAllowed(false);
		tabelle.addListener(new CalculatorItemClickListener(
				itemClickdependentComponents, deleteButton, formLayout,
				saveButton, tabelle, betriebsFraField, betriebsStdField));
		tabelle.addListener(new CalculatorItemSetChangeListener(tabelle,
				betriebsStdField, betriebsFraField));
		tabelle.setReadOnly(false);

		tabelle.setContainerDataSource(RowContainer.fill());

		tabelle.setVisibleColumns(RowBean.COLUMNS);
		tabelle.setColumnHeaders(RowBean.VISIBLECOLUMNS);

		tabelle.setFooterVisible(true);

		final CalculatorConverterAdder convertersAdder = new CalculatorConverterAdder();
		convertersAdder.addConvertersTo(tabelle);

		final CalculatorTableComputer computer = new CalculatorTableComputer(tabelle);
		computer.computeColumns();

	}

	public Table getTabelle()
	{
		return tabelle;
	}

	public void setTabelle(Table tabelle)
	{
		this.tabelle = tabelle;
	}

}
