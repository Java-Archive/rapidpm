package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.datenmodell;

import com.vaadin.data.util.BeanItemContainer;

import java.io.Serializable;

public class RowContainer extends BeanItemContainer<RowBean> implements
		Serializable
{

	public RowContainer() throws IllegalArgumentException
	{
		super(RowBean.class);
	}

	public static RowContainer fill()
	{
		final RowBean row = new RowBean();
		row.setRessource("GF");
		row.setBruttoGehalt(200000.0);
		row.setHoursPerWeek(40);
		row.setWeeksPerYear(50);
		row.setFacturizable(0.5);
		row.setExternalEurosPerHour(125.0);
		row.setPlanAnzahl(1);
		final RowBean row2 = new RowBean();
		row2.setRessource("Multiprojekt Manager");
		row2.setBruttoGehalt(83000.0);
		row2.setHoursPerWeek(40);
		row2.setWeeksPerYear(46);
		row2.setFacturizable(0.5);
		row2.setExternalEurosPerHour(125.0);
		row2.setPlanAnzahl(1);
		final RowBean row3 = new RowBean();
		row3.setRessource("Projektleiter / Scrum Master");
		row3.setBruttoGehalt(72000.0);
		row3.setHoursPerWeek(40);
		row3.setWeeksPerYear(46);
		row3.setFacturizable(0.75);
		row3.setExternalEurosPerHour(75.0);
		row3.setPlanAnzahl(1);
		final RowBean row4 = new RowBean();
		row4.setRessource("Senior Projektmitarbeiter");
		row4.setBruttoGehalt(72000.0);
		row4.setHoursPerWeek(40);
		row4.setWeeksPerYear(46);
		row4.setFacturizable(0.75);
		row4.setExternalEurosPerHour(65.0);
		row4.setPlanAnzahl(1);
		final RowBean row5 = new RowBean();
		row5.setRessource("Junior Projektmitarbeiter");
		row5.setBruttoGehalt(45000.0);
		row5.setHoursPerWeek(40);
		row5.setWeeksPerYear(46);
		row5.setFacturizable(0.75);
		row5.setExternalEurosPerHour(45.0);
		row5.setPlanAnzahl(1);
		final RowBean row6 = new RowBean();
		row6.setRessource("Aushilfe / stud. Projektmitarbeiter");
		row6.setBruttoGehalt(17000.0);
		row6.setHoursPerWeek(40);
		row6.setWeeksPerYear(46);
		row6.setFacturizable(0.75);
		row6.setExternalEurosPerHour(25.0);
		row6.setPlanAnzahl(1);
		final RowBean row7 = new RowBean();
		row7.setRessource("externe Ressource");
		row7.setBruttoGehalt(125000.0);
		row7.setHoursPerWeek(40);
		row7.setWeeksPerYear(46);
		row7.setFacturizable(0.80);
		row7.setExternalEurosPerHour(90.0);
		row7.setPlanAnzahl(1);
		final RowBean row8 = new RowBean();
		row8.setRessource("xxx");
		row8.setBruttoGehalt(1.0);
		row8.setHoursPerWeek(40);
		row8.setWeeksPerYear(46);
		row8.setFacturizable(0.50);
		row8.setExternalEurosPerHour(0.0);
		row8.setPlanAnzahl(1);
		final RowBean row9 = new RowBean();
		row9.setRessource("Backoffice");
		row9.setBruttoGehalt(62000.0);
		row9.setHoursPerWeek(20);
		row9.setWeeksPerYear(46);
		row9.setFacturizable(0.01);
		row9.setExternalEurosPerHour(0.0);
		row9.setPlanAnzahl(1);
		final RowBean row10 = new RowBean();
		row10.setRessource("Backoffice");
		row10.setBruttoGehalt(62000.0);
		row10.setHoursPerWeek(20);
		row10.setWeeksPerYear(46);
		row10.setFacturizable(0.01);
		row10.setExternalEurosPerHour(0.0);
		row10.setPlanAnzahl(1);
		final RowBean row11 = new RowBean();
		row11.setRessource("Backoffice");
		row11.setBruttoGehalt(62000.0);
		row11.setHoursPerWeek(20);
		row11.setWeeksPerYear(46);
		row11.setFacturizable(0.01);
		row11.setExternalEurosPerHour(0.0);
		row11.setPlanAnzahl(1);
		final RowBean row12 = new RowBean();
		row12.setRessource("Backoffice");
		row12.setBruttoGehalt(62000.0);
		row12.setHoursPerWeek(20);
		row12.setWeeksPerYear(46);
		row12.setFacturizable(0.01);
		row12.setExternalEurosPerHour(0.0);
		row12.setPlanAnzahl(1);

		final RowContainer container = new RowContainer();

		container.addBean(row);
		container.addBean(row2);
		container.addBean(row3);
		container.addBean(row4);
		container.addBean(row5);
		container.addBean(row6);
		container.addBean(row7);
		container.addBean(row8);
		container.addBean(row9);
		container.addBean(row10);
		container.addBean(row11);
		container.addBean(row12);
		return container;
	}

}
