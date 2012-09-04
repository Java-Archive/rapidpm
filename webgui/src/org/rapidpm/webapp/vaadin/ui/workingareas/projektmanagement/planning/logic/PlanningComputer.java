package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic;

import org.rapidpm.webapp.vaadin.Projekt;
import org.rapidpm.webapp.vaadin.ProjektBean;
import org.rapidpm.webapp.vaadin.RessourceGroup;
import org.rapidpm.webapp.vaadin.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitElement;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.rapidpm.Constants.*;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 30.08.12
 * Time: 13:47
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningComputer {

    private ProjektBean projektBean;
    private RessourceGroupsBean ressourceGroupsBean;
    private List<RessourceGroup> ressourceGroups;
    private Projekt projekt;

    public PlanningComputer(ProjektBean projektBean, RessourceGroupsBean ressourceGroupsBean) {
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
                computePlanningUnits(planningUnitGroup.getPlanningUnitList(), planningUnitGroup.getPlanningUnitGroupName(),
                        ressourceGroupDaysHoursMinutesItemMap);
            }
        }
    }


    private void computePlanningUnits(final List<PlanningUnit> planningUnits, final String parent,
                                      final Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap) {
        for (final PlanningUnit planningUnit : planningUnits) {
            if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
                addiereZeileZurRessourceMap(ressourceGroupDaysHoursMinutesItemMap, planningUnit);
            } else {
                computePlanningUnits(planningUnit.getKindPlanningUnits(), planningUnit.getPlanningUnitName(),
                        ressourceGroupDaysHoursMinutesItemMap);
            }
        }
        boolean parentIsGroup = false;
        for (final RessourceGroup ressourceGroup : ressourceGroups) {
            for(final PlanningUnitGroup group : projektBean.getProjekt().getPlanningUnitGroups()){
                if (group.getPlanningUnitGroupName().equals(parent)){
                    parentIsGroup = true;
                }
            }
            final Integer daysFromMap = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getDays();
            final Integer hoursFromMap = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getHours();
            final Integer minutesFromMap = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getMinutes();
            if(parentIsGroup){
                PlanningUnitElement element = new PlanningUnitElement();
                for(final PlanningUnitElement planningUnitElement : projekt.getPlanningUnitGroup(parent)
                        .getPlanningUnitElementList()){
                    if (planningUnitElement.getRessourceGroup().getName().equals(ressourceGroup.getName())){
                        element = planningUnitElement;
                    }
                }
                final int index = projekt.getPlanningUnitGroup(parent).getPlanningUnitElementList().indexOf(element);
                final PlanningUnitElement planningUnitElement = projekt.getPlanningUnitGroup(parent)
                        .getPlanningUnitElementList().get(index);
                planningUnitElement.setPlannedDays(daysFromMap);
                planningUnitElement.setPlannedHours(hoursFromMap);
                planningUnitElement.setPlannedMinutes(minutesFromMap);
            }else{
                final ArrayList<PlanningUnit> planningUnitsResultList = new ArrayList<>();
                for(final PlanningUnitGroup planningUnitGroup : projekt.getPlanningUnitGroups()){
                    projekt.findPlanningUnitAndWriteReferenceInList(planningUnitGroup.getPlanningUnitList(),parent,
                            planningUnitsResultList);
                }
                PlanningUnitElement element = new PlanningUnitElement();
                for(final PlanningUnitElement planningUnitElement : planningUnitsResultList.get(0)
                        .getPlanningUnitElementList()){
                    if (planningUnitElement.getRessourceGroup().getName().equals(ressourceGroup.getName())){
                        element = planningUnitElement;
                    }
                }
                final int index = planningUnitsResultList.get(0).getPlanningUnitElementList().indexOf(element);
                final PlanningUnitElement planningUnitElement = planningUnitsResultList.get(0)
                        .getPlanningUnitElementList().get(index);
                planningUnitElement.setPlannedDays(daysFromMap);
                planningUnitElement.setPlannedHours(hoursFromMap);
                planningUnitElement.setPlannedMinutes(minutesFromMap);
            }
        }
    }

    private void addiereZeileZurRessourceMap(final Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap,
                                             final PlanningUnit planningUnit) {
        for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
            if (!planningUnitElement.getRessourceGroup().getName().equals(AUFGABE_SPALTE)) {
                final RessourceGroup ressourceGroup1 = planningUnitElement.getRessourceGroup();
                final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem();
                final int plannedDays = planningUnitElement.getPlannedDays();
                final int plannedHours = planningUnitElement.getPlannedHours();
                final int plannedMinutes = planningUnitElement.getPlannedMinutes();
                daysHoursMinutesItem.setDays(plannedDays);
                daysHoursMinutesItem.setHours(plannedHours);
                daysHoursMinutesItem.setMinutes(plannedMinutes);
                if (ressourceGroupDaysHoursMinutesItemMap.containsKey(ressourceGroup1)) {
                    final Integer days = daysHoursMinutesItem.getDays();
                    final Integer hours = daysHoursMinutesItem.getHours();
                    final Integer minutes = daysHoursMinutesItem.getMinutes();
                    final Integer daysFromMap = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup1).getDays();
                    final Integer hoursFromMap = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup1).getHours();
                    final Integer minutesFromMap = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup1).getMinutes();
                    daysHoursMinutesItem.setDays(days + daysFromMap);
                    daysHoursMinutesItem.setHours(hours + hoursFromMap);
                    daysHoursMinutesItem.setMinutes(minutes + minutesFromMap);
                }
                correctDaysHoursMinutesItem(daysHoursMinutesItem);
                ressourceGroupDaysHoursMinutesItemMap.put(ressourceGroup1, daysHoursMinutesItem);
            }
        }
    }



    private void correctDaysHoursMinutesItem(DaysHoursMinutesItem item) {
        final int hours = item.getMinutes() / MINS_HOUR;
        if (hours > 0) {
            item.setHours(item.getHours() + hours);
            item.setMinutes(item.getMinutes() - (hours * MINS_HOUR));
        }
        final int days = item.getHours() / HOURS_DAY;
        if (days > 0) {
            item.setDays(item.getDays() + days);
            item.setHours(item.getHours() - (days * HOURS_DAY));
        }
    }


}
