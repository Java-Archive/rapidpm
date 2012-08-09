package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.logic.tasks;

import com.vaadin.ui.Root;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.CalculatorScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.uicomponents.AddRowWindow;

public class AddRowLogic
{
	private Root root;
	private CalculatorScreen screen;

	public AddRowLogic(Root root, CalculatorScreen screen)
	{
		this.root = root;
		this.screen = screen;

	}

	public void execute()
	{
		final AddRowWindow window = new AddRowWindow(root, screen);
		window.show();
	}
}
