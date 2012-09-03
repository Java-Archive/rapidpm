package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.logic;

import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.*;

import java.text.DecimalFormat;
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
public class TimesComputer {

    private RessourceGroupsBean ressourceGroupsBean;
    private ProjektBean projektBean;
    private ArrayList<RessourceGroup> ressourceGroups;

    private HashMap<RessourceGroup, Double> relativeWerte = new HashMap<RessourceGroup, Double>();
    private final Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap = new HashMap<>();
    private Integer gesamtSumme;
    private Double mannTageExakt;

    public TimesComputer(RessourceGroupsBean rBean, ProjektBean pBean){
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
        computeMannTage();
    }

    private void computeMannTage() {
        mannTageExakt = gesamtSumme / 60.0 * 8.0;
    }

    private void computePlanningUnitGroupsAndTotalsAbsolut() {
        for (final PlanningUnitGroup planningUnitGroup : projektBean.getProjekt().getPlanningUnitGroups()) {

                computePlanningUnits(planningUnitGroup.getPlanningUnitList());
            }
    }


    private void computePlanningUnits(List<PlanningUnit> planningUnits) {
        for (PlanningUnit planningUnit : planningUnits) {
            if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
                addiereZeileZurRessourceMap(ressourceGroupDaysHoursMinutesItemMap, planningUnit);
            } else {
                computePlanningUnits(planningUnit.getKindPlanningUnits());
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

    public Integer getGesamtSumme() {
        return gesamtSumme;
    }

    public DaysHoursMinutesItem getGesamtSummeItem(){
        final DaysHoursMinutesItem item = new DaysHoursMinutesItem();
        item.setMinutes(gesamtSumme);
        correctDaysHoursMinutesItem(item);
        return item;
    }

    public Double getMannTageExakt() {
        return mannTageExakt;
    }

    public String getMannTageGerundet() {
        final DecimalFormat format = new DecimalFormat("#0.00");
        return format.format(mannTageExakt);
    }
}
