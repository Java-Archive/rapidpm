package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.logic;

import org.rapidpm.webapp.vaadin.Constants;

import com.vaadin.external.com.ibm.icu.text.DecimalFormat;
import com.vaadin.ui.Table;

public class CalculatorFieldsComputer
{

	private Table tabelle;
	private Double betriebsStunde;
	private Double betriebsFra;

	public CalculatorFieldsComputer(Table tabelle)
	{
		this.tabelle = tabelle;
	}

	public void compute()
	{
		Double summeProTag = 0.0;

		for (final Object itemId : tabelle.getItemIds())
		{
			summeProTag += (Double) tabelle.getItem(itemId)
					.getItemProperty("sumPerDay").getValue();
		}
		betriebsStunde = summeProTag / Constants.HOURSPERDAY;
		betriebsFra = summeProTag / Constants.KONSTANTE;
	}

	public Double getBetriebsStunde()
	{
		return betriebsStunde;
	}

	public Double getBetriebsFra()
	{
		return betriebsFra;
	}

	public String getBetriebsStundeAsString()
	{
		final DecimalFormat f = new DecimalFormat("0.00");
		return f.format(betriebsStunde) + Constants.EUR;
	}

	public String getBetriebsFraAsString()
	{
		final DecimalFormat f = new DecimalFormat("0.00");
		return f.format(betriebsFra) + Constants.EUR;
	}

}
