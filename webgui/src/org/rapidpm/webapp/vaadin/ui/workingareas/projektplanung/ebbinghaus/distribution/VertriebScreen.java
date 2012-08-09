package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.distribution;

import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.Screen;

public class VertriebScreen extends Screen
{
	private TextField vertrieblerField;
	private TextField datumField;
	private TextField summeMitAufschlagField;
	private TextField summeOhneAufschlagField;
	private TextField verhandelterPreisField;
	private TextArea bemerkungenArea;

	private Table vertriebsTable;

	private VerticalLayout vertrieblerLayout = new VerticalLayout();
	private VerticalLayout tableLayout = new VerticalLayout();
	private GridLayout bottomLayout;

	public VertriebScreen()
	{
		erstelleVertrieblerLayout();
		erstelleStandardTableLayout(new Label("Uebersicht"), vertriebsTable,
				tableLayout);
		erstelleBottomLayout();
        setComponents();
	}

	private void erstelleBottomLayout()
	{
		bottomLayout = new GridLayout(2, 5);
		summeMitAufschlagField = new TextField();
		summeMitAufschlagField.setEnabled(false);
		summeOhneAufschlagField = new TextField();
		summeOhneAufschlagField.setEnabled(false);
		verhandelterPreisField = new TextField();
		bemerkungenArea = new TextArea();
		bemerkungenArea.setWidth("500px");
		bemerkungenArea.setHeight("300px");
		// GridLayout bottomGridLayout = new GridLayout(2,5);

		final Label eins = new Label("Summe mit Verhandlungsaufschlag: ");
		final Label zwei = new Label("Summe ohne Verhandlungsaufschlag: ");
		final Label drei = new Label("--------------------------------");
		final Label vier = new Label("verhandelter Preis: ");
		final Label fuenf = new Label("Bemerkungen: ");

		bottomLayout.setMargin(true, false, true, false);

		bottomLayout.addComponent(eins);
		bottomLayout.addComponent(summeMitAufschlagField);
		bottomLayout.addComponent(zwei);
		bottomLayout.addComponent(summeOhneAufschlagField);
		bottomLayout.addComponent(drei);
		bottomLayout.addComponent(new Label(""));
		bottomLayout.addComponent(vier);
		bottomLayout.addComponent(verhandelterPreisField);
		bottomLayout.addComponent(fuenf);

		bottomLayout.addComponent(bemerkungenArea);
		bottomLayout.setComponentAlignment(eins, Alignment.MIDDLE_RIGHT);
		bottomLayout.setComponentAlignment(summeMitAufschlagField,
				Alignment.MIDDLE_LEFT);
		bottomLayout.setComponentAlignment(zwei, Alignment.MIDDLE_RIGHT);
		bottomLayout.setComponentAlignment(summeOhneAufschlagField,
				Alignment.MIDDLE_LEFT);
		bottomLayout.setComponentAlignment(drei, Alignment.MIDDLE_RIGHT);
		bottomLayout.setComponentAlignment(vier, Alignment.MIDDLE_RIGHT);
		bottomLayout.setComponentAlignment(verhandelterPreisField,
				Alignment.MIDDLE_LEFT);
		bottomLayout.setComponentAlignment(fuenf, Alignment.MIDDLE_RIGHT);
		bottomLayout.setComponentAlignment(bemerkungenArea,
				Alignment.MIDDLE_LEFT);

	}

	private void erstelleStandardTableLayout(Label ueberschrift, Table tabelle,
			Layout layout)
	{
		tabelle = new Table();
		layout.addComponent(ueberschrift);
		layout.addComponent(tabelle);
		layout.setMargin(true, false, true, false);
	}

	private void erstelleVertrieblerLayout()
	{
		vertrieblerLayout.setWidth("600px");
		vertrieblerField = new TextField();
		datumField = new TextField();
		final Label a = new Label("Verantwortlicher Vertriebler:");
		final Label b = new Label("Datum:");
		HorizontalLayout zeile1 = new HorizontalLayout();
		zeile1.addComponent(a);
		zeile1.addComponent(vertrieblerField);
		zeile1.setSizeFull();
		final HorizontalLayout zeile2 = new HorizontalLayout();
		zeile2.addComponent(b);
		zeile2.addComponent(datumField);
		zeile2.setSizeFull();
		vertrieblerLayout.addComponent(zeile1);
		vertrieblerLayout.addComponent(zeile2);
		vertrieblerLayout.setMargin(true, false, true, false);
	}

	private void setComponents()
	{
		addComponent(vertrieblerLayout);
		addComponent(tableLayout);
		addComponent(bottomLayout);
	}


	public TextField getVertrieblerField()
	{
		return vertrieblerField;
	}

	public void setVertrieblerField(TextField vertrieblerField)
	{
		this.vertrieblerField = vertrieblerField;
	}

	public TextField getDatumField()
	{
		return datumField;
	}

	public void setDatumField(TextField datumField)
	{
		this.datumField = datumField;
	}

	public TextField getSummeMitAufschlagField()
	{
		return summeMitAufschlagField;
	}

	public void setSummeMitAufschlagField(TextField summeMitAufschlagField)
	{
		this.summeMitAufschlagField = summeMitAufschlagField;
	}

	public TextField getSummeOhneAufschlagField()
	{
		return summeOhneAufschlagField;
	}

	public void setSummeOhneAufschlagField(TextField summeOhneAufschlagField)
	{
		this.summeOhneAufschlagField = summeOhneAufschlagField;
	}

	public TextField getVerhandelterPreisField()
	{
		return verhandelterPreisField;
	}

	public void setVerhandelterPreisField(TextField verhandelterPreisField)
	{
		this.verhandelterPreisField = verhandelterPreisField;
	}

	public TextArea getBemerkungenArea()
	{
		return bemerkungenArea;
	}

	public void setBemerkungenArea(TextArea bemerkungenArea)
	{
		this.bemerkungenArea = bemerkungenArea;
	}

	public Table getVertriebsTable()
	{
		return vertriebsTable;
	}

	public void setVertriebsTable(Table vertriebsTable)
	{
		this.vertriebsTable = vertriebsTable;
	}

	public VerticalLayout getVertrieblerLayout()
	{
		return vertrieblerLayout;
	}

	public void setVertrieblerLayout(VerticalLayout vertrieblerLayout)
	{
		this.vertrieblerLayout = vertrieblerLayout;
	}

	public VerticalLayout getTableLayout()
	{
		return tableLayout;
	}

	public void setTableLayout(VerticalLayout tableLayout)
	{
		this.tableLayout = tableLayout;
	}

}