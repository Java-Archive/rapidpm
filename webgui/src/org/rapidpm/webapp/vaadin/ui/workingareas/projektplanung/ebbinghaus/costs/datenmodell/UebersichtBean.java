package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.costs.datenmodell;

import java.io.Serializable;

public class UebersichtBean implements Serializable
{

	public static final String[] VISIBLECOLUMNS = new String[] { "Bezeichnung",
			"Aushilfe", "Junior Projektmitarbeiter",
			"Senior Projektmitarbeiter",
			"intern / extern Projektmanager / Scrum Master", "GF",
			"externe Ressource" };
	public static final String[] COLUMNS = new String[] { "bezeichnung",
			"aushilfe", "juniorProjektmitarbeiter", "seniorProjektmitarbeiter",
			"internExternProjektManagerScrumMaster", "gf", "externeRessource" };

	private String bezeichnung;
	private Double aushilfe;
	private Double juniorProjektmitarbeiter;
	private Double seniorProjektmitarbeiter;
	private Double internExternProjektManagerScrumMaster;
	private Double gf;
	private Double externeRessource;

	public String getBezeichnung()
	{
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung)
	{
		this.bezeichnung = bezeichnung;
	}

	public Double getAushilfe()
	{
		return aushilfe;
	}

	public void setAushilfe(Double aushilfe)
	{
		this.aushilfe = aushilfe;
	}

	public Double getJuniorProjektmitarbeiter()
	{
		return juniorProjektmitarbeiter;
	}

	public void setJuniorProjektmitarbeiter(Double juniorProjektmitarbeiter)
	{
		this.juniorProjektmitarbeiter = juniorProjektmitarbeiter;
	}

	public Double getSeniorProjektmitarbeiter()
	{
		return seniorProjektmitarbeiter;
	}

	public void setSeniorProjektmitarbeiter(Double seniorProjektmitarbeiter)
	{
		this.seniorProjektmitarbeiter = seniorProjektmitarbeiter;
	}

	public Double getInternExternProjektManagerScrumMaster()
	{
		return internExternProjektManagerScrumMaster;
	}

	public void setInternExternProjektManagerScrumMaster(
			Double internExternProjektManagerScrumMaster)
	{
		this.internExternProjektManagerScrumMaster = internExternProjektManagerScrumMaster;
	}

	public Double getGf()
	{
		return gf;
	}

	public void setGf(Double gf)
	{
		this.gf = gf;
	}

	public Double getExterneRessource()
	{
		return externeRessource;
	}

	public void setExterneRessource(Double externeRessource)
	{
		this.externeRessource = externeRessource;
	}

	public static String[] getVisiblecolumns()
	{
		return VISIBLECOLUMNS;
	}

}
