package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.uicomponents;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.datenmodell.RowBean;

import java.util.ArrayList;
import java.util.Iterator;

public class RowFieldGroup extends FieldGroup
{

	private Button save = new Button("Save");
	private Button cancel = new Button("Cancel");

	private GridLayout layout = new GridLayout(2, 2);
	private HorizontalLayout buttonLayout = new HorizontalLayout();

	public RowFieldGroup(RowBean row)
	{
		setItemDataSource(new BeanItem<RowBean>(row));
		buildForm();
	}

	public RowFieldGroup(Item item)
	{
		setItemDataSource(item);
		buildForm();
	}

	private void buildForm()
	{
		for (final Object propertyId : getUnboundPropertyIds())
		{
			if (!propertyId.toString().equals("sumPerDay")
					&& !propertyId.toString().equals("sumPerMonth")
					&& !propertyId.toString().equals("bruttoPerMonth")
					&& !propertyId.toString().equals("operativeEurosPerHour")
					&& !propertyId.toString().equals("eurosPerHour")
					&& !propertyId.toString().equals("hoursPerYear"))
				layout.addComponent(buildAndBind(propertyId));
		}

		buttonLayout.setSpacing(true);
		buttonLayout.addComponent(save);
		buttonLayout.addComponent(cancel);

	}

	public ArrayList<Component> getComponents()
	{
		final ArrayList<Component> components = new ArrayList<Component>();
		final Iterator<Component> it = layout.getComponentIterator();
		while (it.hasNext())
		{
			components.add(it.next());
		}
		return components;
	}

	public ArrayList<Component> getFooterComponents()
	{
		final ArrayList<Component> components = new ArrayList<Component>();
		final Iterator<Component> it = buttonLayout.getComponentIterator();
		while (it.hasNext())
		{
			components.add(it.next());
		}
		return components;
	}

}
