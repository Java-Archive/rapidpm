package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell;

import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroupsBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 30.08.12
 * Time: 13:47
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class InitComputer {

    private ProjektBean projektBean;
    private RessourceGroupsBean ressourceGroupsBean;
    private ArrayList<RessourceGroup> ressourceGroups;
    private Projekt projekt;

    public InitComputer(ProjektBean projektBean, RessourceGroupsBean ressourceGroupsBean) {
        this.projektBean = projektBean;
        this.ressourceGroupsBean = ressourceGroupsBean;
    }

    public void compute() {
        projekt = projektBean.getProjekt();
        ressourceGroups = ressourceGroupsBean.getRessourceGroups();

        computePlanningUnitGroups();
    }

    private void computePlanningUnitGroups() {
        for (final PlanningUnitGroup planningUnitGroup : projekt.getPlanningUnitGroups()) {
            final Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap = new HashMap<>();
            if (planningUnitGroup.getPlanningUnitList() == null || planningUnitGroup.getPlanningUnitList().isEmpty()) {
                for (final RessourceGroup spalte : ressourceGroups) {
                    final PlanningUnitElement planningUnitElement = new PlanningUnitElement();
                    planningUnitElement.setPlannedDays(0);
                    planningUnitElement.setPlannedHours(0);
                    planningUnitElement.setPlannedMinutes(0);
                    planningUnitElement.setRessourceGroup(spalte);
                    planningUnitGroup.getPlanningUnitElementList().add(planningUnitElement);
                }
            } else {
                computePlanningUnits(planningUnitGroup.getPlanningUnitList(), planningUnitGroup.getPlanningUnitName(), ressourceGroupDaysHoursMinutesItemMap);
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
        boolean parentIsGroup = false;
        for (RessourceGroup ressourceGroup : ressourceGroups) {
            for(PlanningUnitGroup group : projektBean.getProjekt().getPlanningUnitGroups()){
                if (group.getPlanningUnitName().equals(parent)){
                    parentIsGroup = true;
                }
            }
            if(parentIsGroup){
                PlanningUnitElement element = new PlanningUnitElement();
                for(PlanningUnitElement planningUnitElement : projekt.getPlanningUnitGroup(parent).getPlanningUnitElementList()){
                    if (planningUnitElement.getRessourceGroup().getName().equals(ressourceGroup.getName())){
                        element = planningUnitElement;
                    }
                }
                final int index = projekt.getPlanningUnitGroup(parent).getPlanningUnitElementList().indexOf(element);
                PlanningUnitElement pue = projekt.getPlanningUnitGroup(parent).getPlanningUnitElementList().get(index);
                pue.setPlannedDays(ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getDays());
                pue.setPlannedHours(ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getHours());
                pue.setPlannedMinutes(ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getMinutes());
            }else{
                ArrayList<PlanningUnit> planningUnitsResultList = new ArrayList<>();
                for(PlanningUnitGroup planningUnitGroup : projekt.getPlanningUnitGroups()){
                    projekt.findPlanningUnitAndWriteReferenceInList(planningUnitGroup.getPlanningUnitList(),parent,planningUnitsResultList);
                }
                PlanningUnitElement element = new PlanningUnitElement();
                for(PlanningUnitElement planningUnitElement : planningUnitsResultList.get(0).getPlanningUnitElementList()){
                    if (planningUnitElement.getRessourceGroup().getName().equals(ressourceGroup.getName())){
                        element = planningUnitElement;
                    }
                }
                final int index = planningUnitsResultList.get(0).getPlanningUnitElementList().indexOf(element);
               PlanningUnitElement pue = planningUnitsResultList.get(0).getPlanningUnitElementList().get(index);
                pue.setPlannedDays(ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getDays());
                pue.setPlannedHours(ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getHours());
                pue.setPlannedMinutes(ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getMinutes());
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


}
