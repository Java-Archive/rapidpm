package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.logic.tasks;

import com.vaadin.ui.Table;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.CalculatorScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.uicomponents.ButtonComponent;

public class DelRowLogic
{
	private ButtonComponent button;
	private CalculatorScreen screen;

	public DelRowLogic(CalculatorScreen screen, ButtonComponent button)
	{
		this.screen = screen;
		this.button = button;
	}

	public void execute()
	{
		final Table tabelle = screen.getTabelle();
		tabelle.removeItem(button.getItemId());
	}
}
