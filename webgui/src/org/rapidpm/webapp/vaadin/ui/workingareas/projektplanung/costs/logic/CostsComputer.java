package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.costs.logic;

import com.vaadin.data.util.HierarchicalContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.costs.CostsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.Projekt;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 31.08.12
 * Time: 11:04
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class CostsComputer {
    private CostsScreen screen;
    private ArrayList<RessourceGroup> ressourceGroups;
    private HashMap<RessourceGroup, DaysHoursMinutesItem> absoluteWerte = new HashMap<RessourceGroup, DaysHoursMinutesItem>();
    private HashMap<RessourceGroup, Double> relativeWerte = new HashMap<RessourceGroup, Double>();
    private Projekt projekt;
    private Integer gesamtSumme;
    private HierarchicalContainer dataSource;

    public CostsComputer(CostsScreen screen) {
        this.screen = screen;
        dataSource = screen.getDataSource();
    }

//    public void compute() {
//        projekt = screen.getProjektBean().getProjekt();
//        ressourceGroups = screen.getRessourceGroupsBean().getRessourceGroups();
//
//        for (final RessourceGroup spalte : this.ressourceGroups) {
//            absoluteWerte.put(spalte, new DaysHoursMinutesItem());
//            relativeWerte.put(spalte, 0.0);
//        }
//
//        computePlanningUnitGroups();
//        computeTotalsAbsolute();
//        comptueTotalsRelative();
//    }
//
//
//    private void computePlanningUnits(List<PlanningUnit> planningUnits, String parent, Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap) {
//        for (PlanningUnit planningUnit : planningUnits) {
//            if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
//                addiereZeileZurRessourceMap(ressourceGroupDaysHoursMinutesItemMap, planningUnit);
//            } else {
//                computePlanningUnits(planningUnit.getKindPlanningUnits(), planningUnit.getPlanningUnitElementName(), ressourceGroupDaysHoursMinutesItemMap);
//            }
//        }
//        for (RessourceGroup spalte : ressourceGroups) {
//            dataSource.getItem(parent).getItemProperty(spalte.getName()).setValue(ressourceGroupDaysHoursMinutesItemMap.get(spalte).toString());
//        }
//    }
//
//    private void addiereZeileZurRessourceMap(Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap, PlanningUnit planningUnit) {
//        for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
//            if (!planningUnitElement.getRessourceGroup().getName().equals("Aufgabe")) {
//                final RessourceGroup ressourceGroup1 = planningUnitElement.getRessourceGroup();
//                final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem();
//                daysHoursMinutesItem.setDays(planningUnitElement.getPlannedDays());
//                daysHoursMinutesItem.setHours(planningUnitElement.getPlannedHours());
//                daysHoursMinutesItem.setMinutes(planningUnitElement.getPlannedMinutes());
//                if (ressourceGroupDaysHoursMinutesItemMap.containsKey(ressourceGroup1)) {
//                    daysHoursMinutesItem.setDays(daysHoursMinutesItem.getDays() + ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup1).getDays());
//                    daysHoursMinutesItem.setHours(daysHoursMinutesItem.getHours() + ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup1).getHours());
//                    daysHoursMinutesItem.setMinutes(daysHoursMinutesItem.getMinutes() + ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup1).getMinutes());
//                }
//                correctDaysHoursMinutesItem(daysHoursMinutesItem);
//                ressourceGroupDaysHoursMinutesItemMap.put(ressourceGroup1, daysHoursMinutesItem);
//            }
//        }
//    }
//
//    private void computePlanningUnitGroups() {
//        for (final PlanningUnitGroup planningUnitGroup : projekt.getPlanningUnitGroups()) {
//            final Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap = new HashMap<>();
//            final String planningUnitGroupName = planningUnitGroup.getPlanningUnitName();
//            if (planningUnitGroup.getPlanningUnitList() == null || planningUnitGroup.getPlanningUnitList().isEmpty()) {
//                for (final RessourceGroup spalte : ressourceGroups) {
//                    final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem();
//                    Integer index = null;
//                    for(final PlanningUnitElement planningUnitElement : planningUnitGroup.getPlanningUnitElementList()){
//                        final String planningUnitElementRessourceGroupName = planningUnitElement.getRessourceGroup().getName();
//                        if(planningUnitElementRessourceGroupName.equals(spalte.getName())){
//                            index = planningUnitGroup.getPlanningUnitElementList().indexOf(planningUnitElement);
//                        }
//                    }
//                    final PlanningUnitElement planningUnitElement = planningUnitGroup.getPlanningUnitElementList().get(index);
//                    daysHoursMinutesItem.setDays(planningUnitElement.getPlannedDays());
//                    daysHoursMinutesItem.setHours(planningUnitElement.getPlannedHours());
//                    daysHoursMinutesItem.setMinutes(planningUnitElement.getPlannedMinutes());
//                    dataSource.getItem(planningUnitGroupName).getItemProperty(spalte.getName()).setValue(daysHoursMinutesItem.toString());
//                }
//            } else {
//                computePlanningUnits(planningUnitGroup.getPlanningUnitList(), planningUnitGroupName, ressourceGroupDaysHoursMinutesItemMap);
//            }
//        }
//    }
//
//    private void correctDaysHoursMinutesItem(DaysHoursMinutesItem item) {
//        final int hours = item.getMinutes() / 60;
//        if (hours > 0) {
//            item.setHours(item.getHours() + hours);
//            item.setMinutes(item.getMinutes() - (hours * 60));
//        }
//        final int days = item.getHours() / 24;
//        if (days > 0) {
//            item.setDays(item.getDays() + days);
//            item.setHours(item.getHours() - (days * 24));
//        }
//    }
//
//    private void computeTotalsAbsolute() {              //holt sich Werte aus Container, NICHT aus Projekt(bean)
//        for (RessourceGroup ressourceGroup : ressourceGroups) {
//            if (!ressourceGroup.getName().equals("Aufgabe")) {
//                final DaysHoursMinutesItem item = new DaysHoursMinutesItem();
//                for (PlanningUnitGroup planningUnitGroup : screen.getProjektBean().getProjekt().getPlanningUnitGroups()) {
//                    final String planningUnitGroupName = planningUnitGroup.getPlanningUnitName();
//                    final String zellenInhalt = screen.getDataSource().getItem(planningUnitGroupName).getItemProperty(ressourceGroup.getName()).getValue().toString();
//                    final String[] daysHoursMinutes = zellenInhalt.split(":");
//
//                    if (zellenInhalt.equals("")) {
//                    } else {
//                        item.setDays(item.getDays() + Integer.parseInt(daysHoursMinutes[0]));
//                        item.setHours(item.getHours() + Integer.parseInt(daysHoursMinutes[1]));
//                        item.setMinutes(item.getMinutes() + Integer.parseInt(daysHoursMinutes[2]));
//                    }
//                }
//                absoluteWerte.put(ressourceGroup, item);
//            }
//        }
//    }
//
//    private void comptueTotalsRelative() {
//        gesamtSumme = 0;
//        for (Map.Entry<RessourceGroup, DaysHoursMinutesItem> absoluteWerteEntry : absoluteWerte.entrySet()) {
//            gesamtSumme += absoluteWerteEntry.getValue().getDays() * 24 * 60;
//            gesamtSumme += absoluteWerteEntry.getValue().getHours() * 60;
//            gesamtSumme += absoluteWerteEntry.getValue().getMinutes();
//        }
//        for (Map.Entry<RessourceGroup, DaysHoursMinutesItem> absoluteWerteEntry : absoluteWerte.entrySet()) {
//            final RessourceGroup absoluterWertRessourceGroup = absoluteWerteEntry.getKey();
//            Integer absoluterWertWert = absoluteWerteEntry.getValue().getDays() * 24 * 60;
//            absoluterWertWert += absoluteWerteEntry.getValue().getHours() * 60;
//            absoluterWertWert += absoluteWerteEntry.getValue().getMinutes();
//            relativeWerte.put(absoluterWertRessourceGroup, absoluterWertWert.doubleValue() / gesamtSumme.doubleValue() * 100.0);
//        }
//    }
//
//    public Integer getGesamtSumme() {
//        return gesamtSumme;
//    }
//
//    public void setGesamtSumme(Integer gesamtSumme) {
//        this.gesamtSumme = gesamtSumme;
//    }
//
//    public void setValuesInScreen() {
//
//
//        screen.getSummeInMinField().setValue(getDaysHoursMinutesString());
//
//        screen.getUebersichtTable().addItem("absolut");
//        screen.getUebersichtTable().addItem("relativ");
//
//        screen.getUebersichtTable().getItem("absolut").getItemProperty("Angabe").setValue("Summe in h");
//        for (Object spalte : screen.getUebersichtTable().getItem("absolut").getItemPropertyIds()) {
//            if (!spalte.equals("Angabe")) {
//                for (Map.Entry<RessourceGroup, DaysHoursMinutesItem> absoluteWerteEntry : absoluteWerte.entrySet()) {
//                    if (absoluteWerteEntry.getKey().getName().equals(spalte.toString())) {
//                        screen.getUebersichtTable().getItem("absolut").getItemProperty(spalte).setValue(absoluteWerte.get(absoluteWerteEntry.getKey()).toString());
//                    }
//
//                }
//
//            }
//
//        }
//
//        DecimalFormat format = new DecimalFormat("#0.00");
//        final Item relativZeile = screen.getUebersichtTable().getItem("relativ");
//        relativZeile.getItemProperty("Angabe").setValue("Summe in %");
//        for (Object spalte : relativZeile.getItemPropertyIds()) {
//            if (!spalte.equals("Angabe"))
//                for (Map.Entry<RessourceGroup, Double> relativeWerteEntry : relativeWerte.entrySet()) {
//                    if (relativeWerteEntry.getKey().getName().equals(spalte.toString())) {
//                        relativZeile.getItemProperty(spalte).setValue(format.format(relativeWerte.get(relativeWerteEntry.getKey())).toString());
//                    }
//
//                }
//        }
//    }
//
//    private String getDaysHoursMinutesString() {
//        String daysString = null;
//        String hoursString = null;
//        String minutesString = null;
//        final Integer days = gesamtSumme / 1440;
//        if (days < 10) {
//            if (days < 1) {
//                daysString = "00";
//            } else {
//                daysString = "0" + String.valueOf(days);
//            }
//        } else {
//            daysString = String.valueOf(days);
//        }
//        final Integer gesamtSummeOhneTage = gesamtSumme - (days * 24 * 60);
//        final Integer hours = gesamtSummeOhneTage / 60;
//        if (hours < 10) {
//            if (hours < 1) {
//                hoursString = "00";
//            } else {
//                hoursString = "0" + String.valueOf(hours);
//            }
//        } else {
//            hoursString = String.valueOf(hours);
//        }
//        final Integer gesamtSummeOhneTageUndStunden = gesamtSummeOhneTage - (hours * 60);
//        if (gesamtSummeOhneTageUndStunden < 10) {
//            if (gesamtSummeOhneTageUndStunden < 1) {
//                minutesString = "00";
//            } else {
//                minutesString = "0" + String.valueOf(gesamtSummeOhneTageUndStunden);
//            }
//        } else {
//            minutesString = String.valueOf(gesamtSummeOhneTageUndStunden);
//        }
//        return (daysString + ":" + hoursString + ":" + minutesString);
//    }


}