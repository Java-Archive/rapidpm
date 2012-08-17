package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.logic;

import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.TreeTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.AufwandProjInitScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProjInitComputer {
    private AufwandProjInitScreen screen;
    private ArrayList<RessourceGroup> spalten = new ArrayList<RessourceGroup>();
    private HashMap<RessourceGroup, DaysHoursMinutesItem> absoluteWerte = new HashMap<RessourceGroup, DaysHoursMinutesItem>();
    private HashMap<RessourceGroup, Double> relativeWerte = new HashMap<RessourceGroup, Double>();
    private Projekt projekt;
    private Integer gesamtSumme;
    private HierarchicalContainer dataSource;

    public ProjInitComputer(AufwandProjInitScreen screen) {
        this.screen = screen;
        dataSource = screen.getDataSource();
    }

    public void compute() {
        projekt = screen.getProjektBean().getProjekt();
        final ArrayList<RessourceGroup> ressourceGroups = screen.getProjektBean().getProjekt().getRessourceGroups();
        for(RessourceGroup ressourceGroup : ressourceGroups){
            spalten.add(ressourceGroup);
        }

        for (RessourceGroup spalte : spalten) {
            absoluteWerte.put(spalte, new DaysHoursMinutesItem());
            relativeWerte.put(spalte, 0.0);
        }

        computePlanningUnitGroups();
        computeTotalsAbsolute();
        comptueTotalsRelative();
    }

    private void computePlanningUnitGroups() {
        //final ArrayList<RessourceGroup> spalten = projekt.getRessourceGroups();//ressourceGorups
        final Map<RessourceGroup, String> spaltenWerteMap = new HashMap<>();

        for (PlanningUnitGroup planningUnitGroup : projekt.getPlanningUnitGroups()){
            final Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap = new HashMap<>();
            if(planningUnitGroup.getPlanningUnitList().size() > 0){
                for(PlanningUnit planningUnit : planningUnitGroup.getPlanningUnitList()){

                    for(PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList())  {
                        if(!planningUnitElement.getRessourceGroup().getName().equals("Aufgabe")) {
                            final RessourceGroup ressourceGroup1 = planningUnitElement.getRessourceGroup();
                            final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem();
                            daysHoursMinutesItem.setDays(planningUnitElement.getPlannedDays());
                            daysHoursMinutesItem.setHours(planningUnitElement.getPlannedHours());
                            daysHoursMinutesItem.setMinutes(planningUnitElement.getPlannedMinutes());
                            if(ressourceGroupDaysHoursMinutesItemMap.containsKey(ressourceGroup1)){
                                daysHoursMinutesItem.setDays(daysHoursMinutesItem.getDays() + ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup1).getDays());
                                daysHoursMinutesItem.setHours( daysHoursMinutesItem.getHours() + ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup1).getHours());
                                daysHoursMinutesItem.setMinutes(daysHoursMinutesItem.getMinutes() + ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup1).getMinutes());
                            }
                            correctDaysHoursMinutesItem(daysHoursMinutesItem);
                            ressourceGroupDaysHoursMinutesItemMap.put(ressourceGroup1,daysHoursMinutesItem);
                        }


                    }
                }
                for(RessourceGroup spalte : spalten){
                    if(!spalte.getName().equals("Aufgabe")){
                        System.out.println(ressourceGroupDaysHoursMinutesItemMap.get(spalte).toString());
                        System.out.println(spalte.getName());
                        System.out.println(planningUnitGroup.getPlanningUnitName());
                        Item item = dataSource.getItem(planningUnitGroup.getPlanningUnitName());
                        dataSource.getItem(planningUnitGroup.getPlanningUnitName()).getItemProperty(spalte.getName()).setValue(ressourceGroupDaysHoursMinutesItemMap.get(spalte).toString());
                    }

                }
            } else {
                for(RessourceGroup spalte : spalten){
                    if(!spalte.getName().equals("Aufgabe")){
                        dataSource.getItem(planningUnitGroup.getPlanningUnitName()).getItemProperty(spalte.getName()).setValue("");
                    }
                }
            }

        }

//        for (Integer parentId : ersteEbeneIds) {
//            final HashMap<String, Integer> spaltenMap = new HashMap<String, Integer>();
//            try {
//                for (Object id : container.getChildren(parentId)) {
//                    for (String spaltenName : spaltenNamen) {
//                        if (container.getItem(id).getItemProperty(spaltenName).getValue() != null) {
//                            final String spaltenNameString = spaltenName.toString();
//                            final Integer zellenInhalt = (Integer) container.getItem(id).getItemProperty(spaltenName).getValue();
//                            if (spaltenMap.containsKey(spaltenName)) {
//                                spaltenMap.put(spaltenNameString, spaltenMap.get(spaltenNameString) + zellenInhalt);
//                            } else {
//                                spaltenMap.put(spaltenNameString, zellenInhalt);
//                            }
//                        }
//
//                    }
//                }
//                for (Object spaltenName : spaltenNamen) {
//                    final Property<?> spalte = container.getItem(parentId).getItemProperty(spaltenName);
//                    final String spaltenNameString = spaltenName.toString();
//                    spalte.setValue(spaltenMap.get(spaltenNameString));
//                }
//            } catch (NullPointerException ex) {
//                ///
//            }
//
//        }
    }

    private void correctDaysHoursMinutesItem(DaysHoursMinutesItem item) {
        final int hours = item.getMinutes() / 60;
        if(hours > 0){
            item.setHours(item.getHours() + hours);
            item.setMinutes(item.getMinutes() - (hours * 60));
        }
        final int days = item.getHours() / 24;
        if(days > 0){
            item.setDays(item.getDays() + days);
            item.setHours(item.getHours() - (days * 24));
        }
    }

    private void computeTotalsAbsolute() {              //holt sich Werte aus Container, NICHT aus Projekt(bean)
        final TreeTable table = screen.getTreeTable();
        for(RessourceGroup ressourceGroup : screen.getProjektBean().getProjekt().getRessourceGroups()){
            if(!ressourceGroup.getName().equals("Aufgabe")){
                final DaysHoursMinutesItem item = new DaysHoursMinutesItem();
                for(PlanningUnitGroup planningUnitGroup : screen.getProjektBean().getProjekt().getPlanningUnitGroups()){
                    final String planningUnitGroupName = planningUnitGroup.getPlanningUnitName();
                    final String zellenInhalt = screen.getDataSource().getItem(planningUnitGroupName).getItemProperty(ressourceGroup.getName()).getValue().toString();
                    final String[] daysHoursMinutes = zellenInhalt.split(":");

                    if(zellenInhalt.equals("")){
                    } else {
                        item.setDays(item.getDays() + Integer.parseInt(daysHoursMinutes[0]));
                        item.setHours(item.getHours() + Integer.parseInt(daysHoursMinutes[1]));
                        item.setMinutes(item.getMinutes() + Integer.parseInt(daysHoursMinutes[2]));
                    }
                }
                absoluteWerte.put(ressourceGroup, item);
            }
        }
    }

    private void comptueTotalsRelative() {
        gesamtSumme = 0;
        for (Map.Entry<RessourceGroup, DaysHoursMinutesItem> absoluteWerteEntry : absoluteWerte.entrySet()) {
            gesamtSumme += absoluteWerteEntry.getValue().getDays()*24*60;
            gesamtSumme += absoluteWerteEntry.getValue().getHours()*60;
            gesamtSumme += absoluteWerteEntry.getValue().getMinutes();
        }
        for (Map.Entry<RessourceGroup, DaysHoursMinutesItem> absoluteWerteEntry : absoluteWerte.entrySet()) {
            final RessourceGroup absoluterWertRessourceGroup = absoluteWerteEntry.getKey();
            Integer absoluterWertWert = absoluteWerteEntry.getValue().getDays()*24*60;
            absoluterWertWert += absoluteWerteEntry.getValue().getHours()*60;
            absoluterWertWert += absoluteWerteEntry.getValue().getMinutes();
            relativeWerte.put(absoluterWertRessourceGroup, absoluterWertWert.doubleValue() / gesamtSumme.doubleValue() * 100.0);
        }
    }
//
//    public HashMap<String, Integer> getAbsoluteWerte() {
//        return absoluteWerte;
//    }
//
//    public void setAbsoluteWerte(HashMap<String, Integer> absoluteWerte) {
//        this.absoluteWerte = absoluteWerte;
//    }
//
//    public HashMap<String, Double> getRelativeWerte() {
//        return relativeWerte;
//    }
//
//    public void setRelativeWerte(HashMap<String, Double> relativeWerte) {
//        this.relativeWerte = relativeWerte;
//    }

    public Integer getGesamtSumme() {
        return gesamtSumme;
    }

    public void setGesamtSumme(Integer gesamtSumme) {
        this.gesamtSumme = gesamtSumme;
    }

    public void setValuesInScreen() {


        screen.getSummeInMinField().setValue(getDaysHoursMinutesString());

        screen.getUebersichtTable().addItem("absolut");
        screen.getUebersichtTable().addItem("relativ");

        screen.getUebersichtTable().getItem("absolut").getItemProperty("Angabe").setValue("Summe in h");
        for(Object spalte : screen.getUebersichtTable().getItem("absolut").getItemPropertyIds()) {
            if(!spalte.equals("Angabe")){
                for(Map.Entry<RessourceGroup, DaysHoursMinutesItem> absoluteWerteEntry : absoluteWerte.entrySet()){
                    if(absoluteWerteEntry.getKey().getName().equals(spalte.toString())){
                        System.out.println("jetzt:"+absoluteWerte.get(absoluteWerteEntry.getKey()));
                        screen.getUebersichtTable().getItem("absolut").getItemProperty(spalte).setValue(absoluteWerte.get(absoluteWerteEntry.getKey()).toString());
                    }

                }

            }

        }

        screen.getUebersichtTable().getItem("relativ").getItemProperty("Angabe").setValue("Summe in %");
        for(Object spalte : screen.getUebersichtTable().getItem("relativ").getItemPropertyIds()) {
            if(!spalte.equals("Angabe"))
                for(Map.Entry<RessourceGroup, Double> relativeWerteEntry : relativeWerte.entrySet()){
                    if(relativeWerteEntry.getKey().getName().equals(spalte.toString())){
                        screen.getUebersichtTable().getItem("relativ").getItemProperty(spalte).setValue(relativeWerte.get(relativeWerteEntry.getKey()).toString());
                    }

                }
        }
    }

    private String getDaysHoursMinutesString() {
        String daysString = null;
        String hoursString = null;
        String minutesString = null;
        final Integer days = gesamtSumme / 1440;
        if(days < 10){
            if(days < 1){
                daysString = "00";
            } else{
                daysString = "0"+ String.valueOf(days);
            }
        } else {
            daysString = String.valueOf(days);
        }
        final Integer gesamtSummeOhneTage = gesamtSumme - (days * 24 * 60);
        final Integer hours = gesamtSummeOhneTage / 60;
        if(hours < 10){
            if(hours < 1){
                hoursString = "00";
            } else{
                hoursString = "0"+ String.valueOf(hours);
            }
        } else {
            hoursString = String.valueOf(hours);
        }
        final Integer gesamtSummeOhneTageUndStunden = gesamtSummeOhneTage - (hours * 60);
        if(gesamtSummeOhneTageUndStunden < 10){
            if(gesamtSummeOhneTageUndStunden < 1){
                minutesString = "00";
            } else{
               minutesString = "0"+ String.valueOf(gesamtSummeOhneTageUndStunden);
            }
        } else {
            minutesString = String.valueOf(gesamtSummeOhneTageUndStunden);
        }
        return(daysString+":"+hoursString+":"+minutesString);
    }



}
