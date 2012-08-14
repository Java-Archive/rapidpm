package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.logic;

import com.vaadin.data.Property;
import com.vaadin.ui.TreeTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.AufwandProjInitScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class ProjInitComputer {
    private AufwandProjInitScreen screen;
    private ArrayList<String> spaltenNamen = new ArrayList<String>();
    private HashMap<String, Integer> absoluteWerte = new HashMap<String, Integer>();
    private HashMap<String, Double> relativeWerte = new HashMap<String, Double>();
    private Integer gesamtSumme;

    public ProjInitComputer(AufwandProjInitScreen screen) {
        this.screen = screen;
    }

    public void compute() {
        spaltenNamen.add("Aushilfe (min)");
        spaltenNamen.add("Multiprojektmanager (min)");
        spaltenNamen.add("Projektmitarbeiter (min)");
        spaltenNamen.add("Projektleiter (min)");

        for (String spaltenName : spaltenNamen) {
            absoluteWerte.put(spaltenName, 0);
            relativeWerte.put(spaltenName, 0.0);
        }

        computePlanningUnitGroups();
        computeTotalsAbsolute();
        comptueTotalsRelative();
    }

    private void computePlanningUnitGroups() {
        final TreeTable table = screen.getTreeTable();
        final ArrayList<Integer> ersteEbeneIds = screen.getErsteEbeneIds();
        for (Integer parentId : ersteEbeneIds) {
            final HashMap<String, Integer> spaltenMap = new HashMap<String, Integer>();
            try {
                for (Object id : table.getChildren(parentId)) {
                    for (String spaltenName : spaltenNamen) {
                        if (table.getItem(id).getItemProperty(spaltenName).getValue() != null) {
                            final String spaltenNameString = spaltenName.toString();
                            final Integer zellenInhalt = (Integer) table.getItem(id).getItemProperty(spaltenName).getValue();
                            if (spaltenMap.containsKey(spaltenName)) {
                                spaltenMap.put(spaltenNameString, spaltenMap.get(spaltenNameString) + zellenInhalt);
                            } else {
                                spaltenMap.put(spaltenNameString, zellenInhalt);
                            }
                        }

                    }
                }
                for (Object spaltenName : spaltenNamen) {
                    final Property<?> spalte = table.getItem(parentId).getItemProperty(spaltenName);
                    final String spaltenNameString = spaltenName.toString();
                    spalte.setValue(spaltenMap.get(spaltenNameString));
                }
            } catch (NullPointerException ex) {
                ///
            }

        }
    }

    private void computeTotalsAbsolute() {
        final TreeTable table = screen.getTreeTable();

        for (Object itemId : screen.getErsteEbeneIds()) {
            for (String spaltenName : spaltenNamen) {
                if (table.getItem(itemId).getItemProperty(spaltenName).getValue() != null) {
                    absoluteWerte.put(spaltenName, absoluteWerte.get(spaltenName) + (Integer) table.getItem(itemId).getItemProperty(spaltenName).getValue());
                }
            }
        }
    }

    private void comptueTotalsRelative() {
        gesamtSumme = 0;
        for (Entry<String, Integer> absoluteWerteEntry : absoluteWerte.entrySet()) {
            gesamtSumme += absoluteWerteEntry.getValue();
        }
        for (Entry<String, Integer> absoluteWerteEntry : absoluteWerte.entrySet()) {
            final String absoluterWertName = absoluteWerteEntry.getKey();
            final Integer absoluterWertWert = absoluteWerteEntry.getValue();
            relativeWerte.put(absoluterWertName, absoluterWertWert.doubleValue() / gesamtSumme.doubleValue() * 100.0);
        }
    }

    public HashMap<String, Integer> getAbsoluteWerte() {
        return absoluteWerte;
    }

    public void setAbsoluteWerte(HashMap<String, Integer> absoluteWerte) {
        this.absoluteWerte = absoluteWerte;
    }

    public HashMap<String, Double> getRelativeWerte() {
        return relativeWerte;
    }

    public void setRelativeWerte(HashMap<String, Double> relativeWerte) {
        this.relativeWerte = relativeWerte;
    }

    public Integer getGesamtSumme() {
        return gesamtSumme;
    }

    public void setGesamtSumme(Integer gesamtSumme) {
        this.gesamtSumme = gesamtSumme;
    }

    public void setValuesInScreen() {
        screen.getSummeInMinField().setValue(getGesamtSumme().toString());

        screen.getUebersichtTable().getItem(1).getItemProperty("Angabe").setValue("Summe in %");
        screen.getUebersichtTable().getItem(1).getItemProperty("Aushilfe").setValue(getRelativeWerte().get("Aushilfe (min)"));
        screen.getUebersichtTable().getItem(1).getItemProperty("Multiprojektmanager").setValue(getRelativeWerte().get("Multiprojektmanager (min)"));
        screen.getUebersichtTable().getItem(1).getItemProperty("Projektmitarbeiter").setValue(getRelativeWerte().get("Projektmitarbeiter (min)"));
        screen.getUebersichtTable().getItem(1).getItemProperty("Projektleiter").setValue(getRelativeWerte().get("Projektleiter (min)"));

        screen.getUebersichtTable().getItem(2).getItemProperty("Angabe").setValue("Summe in h");
        screen.getUebersichtTable().getItem(2).getItemProperty("Aushilfe").setValue(getAbsoluteWerte().get("Aushilfe (min)").doubleValue());
        screen.getUebersichtTable().getItem(2).getItemProperty("Multiprojektmanager").setValue(getAbsoluteWerte().get("Multiprojektmanager (min)").doubleValue());
        screen.getUebersichtTable().getItem(2).getItemProperty("Projektmitarbeiter").setValue(getAbsoluteWerte().get("Projektmitarbeiter (min)").doubleValue());
        screen.getUebersichtTable().getItem(2).getItemProperty("Projektleiter").setValue(getAbsoluteWerte().get("Projektleiter (min)").doubleValue());
    }


}
