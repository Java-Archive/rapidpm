package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.projinit.logic;

import com.vaadin.ui.TreeTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.projinit.AufwandProjInitScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class ProjInitComputer
{
	private AufwandProjInitScreen screen;
	private ArrayList<String> spaltenNamen = new ArrayList<String>();
	private HashMap<String, Integer> absolutes = new HashMap<String, Integer>();
	private HashMap<String, Double> relatives = new HashMap<String, Double>();
	private Integer gesamtSumme;
	
	public ProjInitComputer (AufwandProjInitScreen screen)
	{
		this.screen = screen;
	}
	
	public void compute()
	{
		spaltenNamen.add("Aushilfe (min)");
		spaltenNamen.add("Multiprojektmanager (min)");
		spaltenNamen.add("Projektmitarbeiter (min)");
		spaltenNamen.add("Projektleiter (min)");
		
		for(String spaltenName : spaltenNamen)
		{
			absolutes.put(spaltenName, 0);
			relatives.put(spaltenName, 0.0);
		}
		
		computePlanningUnitGroups();
		computeTotalsAbsolute();
		comptueTotalsRelative();
	}

	private void computePlanningUnitGroups()
	{
		final TreeTable table = screen.getTt();
		final ArrayList<Integer> ersteEbeneIds = screen.getErsteEbeneIds();
		for (Integer parentId : ersteEbeneIds)
		{
			final HashMap<String, Integer> spaltenMap = new HashMap<String, Integer>();
			try
			{
				for(Object id : table.getChildren(parentId))
				{
					for (String spaltenName : spaltenNamen)
					{
						if(table.getItem(id).getItemProperty(spaltenName).getValue() != null)
						{
							if (spaltenMap.containsKey(spaltenName))
							{
								spaltenMap.put(spaltenName.toString(), spaltenMap.get(spaltenName.toString()) + (Integer) table.getItem(id).getItemProperty(spaltenName).getValue());
							}
							else
							{
								spaltenMap.put(spaltenName.toString(), (Integer) table.getItem(id).getItemProperty(spaltenName).getValue());
							}
						}
							
					}
				}
				for (Object spaltenName : spaltenNamen)
				{
					table.getItem(parentId).getItemProperty(spaltenName).setValue(spaltenMap.get(spaltenName.toString()));
				}
			} catch (NullPointerException ex)
			{
				///
			}
			
		}
	}
	
	private void computeTotalsAbsolute()
	{
		final TreeTable table = screen.getTt();
		
		for(Object itemId : screen.getErsteEbeneIds())
		{
			for (String spaltenName : spaltenNamen)
			{
				if(table.getItem(itemId).getItemProperty(spaltenName).getValue() != null)
				{
					absolutes.put(spaltenName, absolutes.get(spaltenName) + (Integer) table.getItem(itemId).getItemProperty(spaltenName).getValue());
				}
			}
		}
	}
	
	private void comptueTotalsRelative()
	{
		gesamtSumme = 0;
		for (Entry<String, Integer> entry : absolutes.entrySet())
		{
			gesamtSumme += entry.getValue();
		}
		for(Entry<String,Integer> entry : absolutes.entrySet())
		{
			relatives.put(entry.getKey(), entry.getValue().doubleValue() / gesamtSumme.doubleValue() * 100.0);
		}
	}

	public HashMap<String, Integer> getAbsolutes()
	{
		return absolutes;
	}

	public void setAbsolutes(HashMap<String, Integer> absolutes)
	{
		this.absolutes = absolutes;
	}

	public HashMap<String, Double> getRelatives()
	{
		return relatives;
	}

	public void setRelatives(HashMap<String, Double> relatives)
	{
		this.relatives = relatives;
	}

	public Integer getGesamtSumme()
	{
		return gesamtSumme;
	}

	public void setGesamtSumme(Integer gesamtSumme)
	{
		this.gesamtSumme = gesamtSumme;
	}

	 public void setValuesInScreen()
     {
         screen.getSummeInMinField().setValue(getGesamtSumme().toString());

         screen.getUebersichtTable().getItem(1).getItemProperty("Angabe").setValue("Summe in %");
         screen.getUebersichtTable().getItem(1).getItemProperty("Aushilfe").setValue(getRelatives().get("Aushilfe (min)"));
         screen.getUebersichtTable().getItem(1).getItemProperty("Multiprojektmanager").setValue(getRelatives().get("Multiprojektmanager (min)"));
         screen.getUebersichtTable().getItem(1).getItemProperty("Projektmitarbeiter").setValue(getRelatives().get("Projektmitarbeiter (min)"));
         screen.getUebersichtTable().getItem(1).getItemProperty("Projektleiter").setValue(getRelatives().get("Projektleiter (min)"));

         screen.getUebersichtTable().getItem(2).getItemProperty("Angabe").setValue("Summe in h");
         screen.getUebersichtTable().getItem(2).getItemProperty("Aushilfe").setValue(getAbsolutes().get("Aushilfe (min)").doubleValue());
         screen.getUebersichtTable().getItem(2).getItemProperty("Multiprojektmanager").setValue(getAbsolutes().get("Multiprojektmanager (min)").doubleValue());
         screen.getUebersichtTable().getItem(2).getItemProperty("Projektmitarbeiter").setValue(getAbsolutes().get("Projektmitarbeiter (min)").doubleValue());
         screen.getUebersichtTable().getItem(2).getItemProperty("Projektleiter").setValue(getAbsolutes().get("Projektleiter (min)").doubleValue());
     }

	

}
