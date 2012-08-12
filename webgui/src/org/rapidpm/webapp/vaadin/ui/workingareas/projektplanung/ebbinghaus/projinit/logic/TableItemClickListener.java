package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.projinit.logic;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component.Event;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.projinit.AufwandProjInitScreen;

public class TableItemClickListener implements ItemClickListener
{

	private AufwandProjInitScreen screen;

	public TableItemClickListener(AufwandProjInitScreen screen)
	{
		this.screen = screen;
	}

	@Override
	public void itemClick(ItemClickEvent event)
	{
		for(final Object listener : screen.getSaveButton().getListeners(Event.class))
		{
			if(listener instanceof ClickListener)
			{
				screen.getSaveButton().removeListener((ClickListener)listener);
			}
				
		}
		screen.getUpperFormLayout().removeAllComponents();
		final FieldGroup fieldGroup = new FieldGroup(event.getItem());
		if (!screen.getErsteEbeneIds().contains(event.getItemId()))
		{
			for (final Object prop : fieldGroup.getUnboundPropertyIds())
			{
				screen.getUpperFormLayout().addComponent(
						fieldGroup.buildAndBind(prop));
			}
		} else
		{
			for (final Object prop : fieldGroup.getUnboundPropertyIds())
			{
				if (prop.equals("Aufgabe"))
					screen.getUpperFormLayout().addComponent(
							fieldGroup.buildAndBind(prop));
			}
		}
		screen.getSaveButton().addListener(new SaveButtonClickListener(fieldGroup, screen));
		screen.getFormLayout().setVisible(true);

	}

}
