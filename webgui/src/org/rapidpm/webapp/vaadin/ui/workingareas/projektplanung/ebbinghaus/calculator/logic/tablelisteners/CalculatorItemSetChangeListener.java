package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.logic.tablelisteners;

import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.logic.CalculatorFieldsComputer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.logic.CalculatorTableComputer;

public class CalculatorItemSetChangeListener implements ItemSetChangeListener
{
	 private Logger logger = Logger.getLogger("org.apache");

	private Table tabelle;
	private TextField betriebsstdField;
	private TextField betriebsfraField;

	public CalculatorItemSetChangeListener(Table tabelle, TextField std,
			TextField fra)
	{
		this.tabelle = tabelle;
		this.betriebsfraField = fra;
		this.betriebsstdField = std;
	}

	@Override
	public void containerItemSetChange(ItemSetChangeEvent event)
	{
		logger.debug("jetzt");
		final CalculatorTableComputer tableComp = new CalculatorTableComputer(
				tabelle);
		tableComp.computeColumns();
		final CalculatorFieldsComputer fieldsComp = new CalculatorFieldsComputer(
				tabelle);
		fieldsComp.compute();
		betriebsfraField.setValue(fieldsComp.getBetriebsFraAsString());
		betriebsstdField.setValue(fieldsComp.getBetriebsStundeAsString());
	}

}
