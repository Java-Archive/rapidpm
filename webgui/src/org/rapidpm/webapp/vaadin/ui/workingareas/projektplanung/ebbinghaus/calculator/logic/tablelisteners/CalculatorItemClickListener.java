package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.logic.tablelisteners;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component.Event;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.logic.CalculatorFieldsComputer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.logic.CalculatorTableComputer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.logic.tableedit.EditModeGetter;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.logic.tableedit.EditModes;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.uicomponents.ItemClickDependentComponent;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.uicomponents.RowFieldGroup;

import java.util.ArrayList;

public class CalculatorItemClickListener implements ItemClickListener
{

	private ArrayList<ItemClickDependentComponent> components = new ArrayList<ItemClickDependentComponent>();

	private boolean state = false;
	private Layout upperFormLayout;
    private Layout formLayout;
	private Button saveButton;
	private Button deleteButton;

	private TextField betriebsFraField;
	private TextField betriebsStdField;

	private Table table;

	public CalculatorItemClickListener(
			ArrayList<ItemClickDependentComponent> components,
			Button deleteButton, Layout upperFormLayout, Layout formLayout, Button saveButton,
			Table tabelle, TextField betriebsFraField,
			TextField betriebsStdField)
	{
		this.components = components;
		this.deleteButton = deleteButton;
		this.upperFormLayout = upperFormLayout;
        this.formLayout = formLayout;
		this.saveButton = saveButton;
		this.betriebsFraField = betriebsFraField;
		this.betriebsStdField = betriebsStdField;
		table = tabelle;
		informComponents(state);
	}

	@Override
	public void itemClick(ItemClickEvent event)
	{

		if(EditModeGetter.getMode() == EditModes.ROWEDIT)
		{
			formLayout.setVisible(true);
			
			upperFormLayout.removeAllComponents();
			for(final Object listener : saveButton.getListeners(Event.class))
			{
				if(listener instanceof ClickListener)
				{
					saveButton.removeListener((ClickListener)listener);
				}
					
			}
			deleteButton.setEnabled(true);

			final Item item = event.getItem();
			final RowFieldGroup fieldGroup = new RowFieldGroup(item);
			final ArrayList<Component> components = fieldGroup.getComponents();
			for (final Component component : components)
			{
				upperFormLayout.addComponent(component);
			}

			saveButton.addListener(new ClickListener()
			{

				@Override
				public void buttonClick(ClickEvent event)
				{
					try
					{
						fieldGroup.commit();
						final CalculatorTableComputer computer = new CalculatorTableComputer(
								table);
						computer.computeColumns();

						final CalculatorFieldsComputer fieldsComputer = new CalculatorFieldsComputer(
								table);
						fieldsComputer.compute();
						betriebsFraField.setValue(fieldsComputer
								.getBetriebsFraAsString());
						betriebsStdField.setValue(fieldsComputer
								.getBetriebsStundeAsString());
						upperFormLayout.setVisible(false);
						saveButton.setVisible(false);
					} catch (CommitException e)
					{
						e.printStackTrace();
					}
				}
			});
			saveButton.setVisible(true);

			if (event.getItemId() == null)
				state = false;
			else
			{
				final Object itemId = event.getItemId();
				informComponents(itemId);
				state = true;
			}

			informComponents(state);

		}
		
	}

	private void informComponents(boolean state)
	{
		for (final ItemClickDependentComponent component : components)
		{
			component.getState(state);
		}
	}

	private void informComponents(Object itemId)
	{
		for (final ItemClickDependentComponent component : components)
		{
			component.setItemId(itemId);
		}
	}

}
