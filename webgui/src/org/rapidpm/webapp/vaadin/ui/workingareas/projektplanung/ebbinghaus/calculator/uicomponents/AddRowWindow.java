package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.uicomponents;

import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.CalculatorScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.datenmodell.RowBean;

import java.util.Iterator;

public class AddRowWindow extends Window
{
	private Root root;

	private GridLayout grid = new GridLayout(2, 2);
	private HorizontalLayout buttons = new HorizontalLayout();
	private Button saveButton = new Button("Save");
	private Button cancelButton = new Button("Cancel");
	private Table tabelle;
	private RowFieldGroup fieldGroup;

	public AddRowWindow(Root root, CalculatorScreen screen)
	{
		this.root = root;
		tabelle = screen.getTabelle();
		setCaption("Add");
		setHeight("400px");
		setWidth("400px");
		setPositionX(50);
		setPositionY(100);

		final RowBean row = new RowBean();
		fieldGroup = new RowFieldGroup(row);

		for (Component comp : fieldGroup.getComponents())
		{
			grid.addComponent(comp);
		}
		addComponent(grid);

		buttons.addComponent(saveButton);
		buttons.addComponent(cancelButton);

		addComponent(buttons);

		addListeners(row);

	}

	private void addListeners(RowBean rowBean)
	{
		final RowBean row = rowBean;
		saveButton.addListener(new ClickListener()
		{

			@Override
			public void buttonClick(ClickEvent event)
			{
				boolean allFilled = true;
				Iterator<Component> it = AddRowWindow.this.grid
						.getComponentIterator();
				while (it.hasNext())
				{
					final Component component = it.next();
					if (component instanceof AbstractField)
					{
						if (((TextField) component).getValue() == null)
							allFilled = false;
					}
				}
				if (allFilled)
				{
					try
					{
						fieldGroup.commit();
						tabelle.getContainerDataSource().addItem(row);
						AddRowWindow.this.close();
					} catch (CommitException e)
					{
						// don't try to commit or to close window if commit
						// fails
					}

				} else
				{
					AddRowWindow.this.addComponent(new Label(
							"Alle Felder ausfuellen"));
				}

			}

		});

		cancelButton.addListener(new ClickListener()
		{

			@Override
			public void buttonClick(ClickEvent event)
			{
				AddRowWindow.this.close();
			}
		});

	}

	public void show()
	{
		//root.set
	}
}
