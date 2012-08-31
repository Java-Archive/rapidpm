package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.logic;

import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 31.08.12
 * Time: 14:34
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class TimesPerRessourceGroupComputer {

    private RessourceGroupsBean ressourceGroupsBean;
    private ProjektBean projektBean;
    private ArrayList<RessourceGroup> ressourceGroups;

    private HashMap<RessourceGroup, Double> relativeWerte = new HashMap<RessourceGroup, Double>();
    private final Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap = new HashMap<>();
    private Integer gesamtSumme;

    public TimesPerRessourceGroupComputer(RessourceGroupsBean rBean, ProjektBean pBean){
         ressourceGroupsBean = rBean;
        projektBean = pBean;
        ressourceGroups = ressourceGroupsBean.getRessourceGroups();
    }

    public void compute(){
        for (final RessourceGroup spalte : this.ressourceGroups) {
            relativeWerte.put(spalte, 0.0);
        }

        computePlanningUnitGroupsAndTotalsAbsolut();
        computeTotalsRelative();
    }

    private void computePlanningUnitGroupsAndTotalsAbsolut() {
        for (final PlanningUnitGroup planningUnitGroup : projektBean.getProjekt().getPlanningUnitGroups()) {
            final String planningUnitGroupName = planningUnitGroup.getPlanningUnitName();
            if (planningUnitGroup.getPlanningUnitList() == null || planningUnitGroup.getPlanningUnitList().isEmpty()) {
                for (final RessourceGroup spalte : ressourceGroups) {
                    for(PlanningUnitElement planningUnitElement : planningUnitGroup.getPlanningUnitElementList()){
                        if(planningUnitElement.getRessourceGroup().equals(spalte)){
                            planningUnitElement.setPlannedDays(0);
                            planningUnitElement.setPlannedHours(0);
                            planningUnitElement.setPlannedMinutes(0);
                        }
                    }
                }
            } else {
                computePlanningUnits(planningUnitGroup.getPlanningUnitList(), planningUnitGroupName, ressourceGroupDaysHoursMinutesItemMap);
            }
        }
    }


    private void computePlanningUnits(List<PlanningUnit> planningUnits, String parent, Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap) {
        for (PlanningUnit planningUnit : planningUnits) {
            if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
                addiereZeileZurRessourceMap(ressourceGroupDaysHoursMinutesItemMap, planningUnit);
            } else {
                computePlanningUnits(planningUnit.getKindPlanningUnits(), planningUnit.getPlanningUnitElementName(), ressourceGroupDaysHoursMinutesItemMap);
            }
        }
        for (RessourceGroup spalte : ressourceGroups) {
            for(PlanningUnitGroup planningUnitGroup : projektBean.getProjekt().getPlanningUnitGroups()){
                for(PlanningUnitElement planningUnitElement : planningUnitGroup.getPlanningUnitElementList()){
                    if(planningUnitElement.getRessourceGroup().equals(spalte)){
                        planningUnitElement.setPlannedDays(ressourceGroupDaysHoursMinutesItemMap.get(spalte).getDays());
                        planningUnitElement.setPlannedHours(ressourceGroupDaysHoursMinutesItemMap.get(spalte).getHours());
                        planningUnitElement.setPlannedMinutes(ressourceGroupDaysHoursMinutesItemMap.get(spalte).getMinutes());
                    }
                }
            }
        }
    }

    private void addiereZeileZurRessourceMap(Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap, PlanningUnit planningUnit) {
        for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
            if (!planningUnitElement.getRessourceGroup().getName().equals("Aufgabe")) {
                final RessourceGroup ressourceGroup1 = planningUnitElement.getRessourceGroup();
                final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem();
                daysHoursMinutesItem.setDays(planningUnitElement.getPlannedDays());
                daysHoursMinutesItem.setHours(planningUnitElement.getPlannedHours());
                daysHoursMinutesItem.setMinutes(planningUnitElement.getPlannedMinutes());
                if (ressourceGroupDaysHoursMinutesItemMap.containsKey(ressourceGroup1)) {
                    daysHoursMinutesItem.setDays(daysHoursMinutesItem.getDays() + ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup1).getDays());
                    daysHoursMinutesItem.setHours(daysHoursMinutesItem.getHours() + ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup1).getHours());
                    daysHoursMinutesItem.setMinutes(daysHoursMinutesItem.getMinutes() + ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup1).getMinutes());
                }
                correctDaysHoursMinutesItem(daysHoursMinutesItem);
                ressourceGroupDaysHoursMinutesItemMap.put(ressourceGroup1, daysHoursMinutesItem);
            }
        }
    }

//    private void computeTotalsAbsolute() {
//        for(RessourceGroup ressourceGroup : projektBean.getProjekt().getRessourceGroups()){
//            System.out.println(ressourceGroup.getName() + ":" + ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).toString());
//        }
//    }

    private void computeTotalsRelative() {
        gesamtSumme = 0;
        for (Map.Entry<RessourceGroup, DaysHoursMinutesItem> absoluteWerteEntry : ressourceGroupDaysHoursMinutesItemMap.entrySet()) {
            gesamtSumme += absoluteWerteEntry.getValue().getDays() * 24 * 60;
            gesamtSumme += absoluteWerteEntry.getValue().getHours() * 60;
            gesamtSumme += absoluteWerteEntry.getValue().getMinutes();
        }
        for (Map.Entry<RessourceGroup, DaysHoursMinutesItem> absoluteWerteEntry : ressourceGroupDaysHoursMinutesItemMap.entrySet()) {
            final RessourceGroup absoluterWertRessourceGroup = absoluteWerteEntry.getKey();
            Integer absoluterWertWert = absoluteWerteEntry.getValue().getDays() * 24 * 60;
            absoluterWertWert += absoluteWerteEntry.getValue().getHours() * 60;
            absoluterWertWert += absoluteWerteEntry.getValue().getMinutes();
            relativeWerte.put(absoluterWertRessourceGroup, absoluterWertWert.doubleValue() / gesamtSumme.doubleValue() * 100.0);
        }
    }

    private void correctDaysHoursMinutesItem(DaysHoursMinutesItem item) {
        final int hours = item.getMinutes() / 60;
        if (hours > 0) {
            item.setHours(item.getHours() + hours);
            item.setMinutes(item.getMinutes() - (hours * 60));
        }
        final int days = item.getHours() / 24;
        if (days > 0) {
            item.setDays(item.getDays() + days);
            item.setHours(item.getHours() - (days * 24));
        }
    }

    public HashMap<RessourceGroup, Double> getRelativeWerte() {
        return relativeWerte;
    }

    public Map<RessourceGroup, DaysHoursMinutesItem> getAbsoluteWerte() {
        return ressourceGroupDaysHoursMinutesItemMap;
    }
}
