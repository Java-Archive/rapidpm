package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.projinit.logic;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
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
		if (!screen.getErsteEbeneIds().contains(event.getItemId()))
		{
			screen.getFormLayout().removeAllComponents();
			final FieldGroup fieldGroup = new FieldGroup(event.getItem());
			for (final Object prop : fieldGroup.getUnboundPropertyIds())
			{
				System.out.println(event.getItem().getItemProperty(prop)
						.getValue());
				screen.getFormLayout().addComponent(
						fieldGroup.buildAndBind(prop));
			}
		}

	}

}
