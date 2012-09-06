package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic;

import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitElement;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.Projekt;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupsBean;

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
@SuppressWarnings("ClassWithoutNoArgConstructor")
public class PlanningCalculator {

    private final ProjektBean projektBean;
    private final RessourceGroupsBean ressourceGroupsBean;
    private List<RessourceGroup> ressourceGroups;
    private Projekt projekt;


    public PlanningCalculator(final ProjektBean projektBean, final RessourceGroupsBean ressourceGroupsBean) {
        this.projektBean = projektBean;
        this.ressourceGroupsBean = ressourceGroupsBean;
    }

    public void compute() {
        projekt = projektBean.getProjekt();
        ressourceGroups = ressourceGroupsBean.getRessourceGroups();

        this.computePlanningUnitGroups();
    }

    private void computePlanningUnitGroups() {
        for (final PlanningUnitGroup planningUnitGroup : projekt.getPlanningUnitGroups()) {
            final Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap = new HashMap<>();
            final List<PlanningUnit> planningUnitGroupPlanningUnitList = planningUnitGroup.getPlanningUnitList();
            if (planningUnitGroupPlanningUnitList == null || planningUnitGroupPlanningUnitList.isEmpty()) {
                for (final RessourceGroup spalte : ressourceGroups) {
                    final PlanningUnitElement planningUnitElement = new PlanningUnitElement();
                    planningUnitElement.setPlannedDays(0);
                    planningUnitElement.setPlannedHours(0);
                    planningUnitElement.setPlannedMinutes(0);
                    planningUnitElement.setRessourceGroup(spalte);
                    planningUnitGroup.getPlanningUnitElementList().add(planningUnitElement);
                }
            } else {
                this.computePlanningUnits(planningUnitGroupPlanningUnitList, planningUnitGroup.getPlanningUnitGroupName(),
                        ressourceGroupDaysHoursMinutesItemMap);
            }
        }
    }


    private void computePlanningUnits(final List<PlanningUnit> planningUnits, final String parent,
                                      final Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap) {
        for (final PlanningUnit planningUnit : planningUnits) {
            final List<PlanningUnit> kindPlanningUnits = planningUnit.getKindPlanningUnits();
            if (kindPlanningUnits == null || kindPlanningUnits.isEmpty()) {
                this.addiereZeileZurRessourceMap(ressourceGroupDaysHoursMinutesItemMap, planningUnit);
            } else {
                this.computePlanningUnits(kindPlanningUnits, planningUnit.getPlanningUnitName(),
                        ressourceGroupDaysHoursMinutesItemMap);
            }
        }
        boolean parentIsGroup = false;
        for (final RessourceGroup ressourceGroup : ressourceGroups) {
            for (final PlanningUnitGroup group : projektBean.getProjekt().getPlanningUnitGroups()) {
                if (group.getPlanningUnitGroupName().equals(parent)) {
                    parentIsGroup = true;
                }
            }
            final Integer daysFromMap = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getDays();
            final Integer hoursFromMap = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getHours();
            final Integer minutesFromMap = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getMinutes();
            if (parentIsGroup) {
                PlanningUnitElement element = new PlanningUnitElement();
                final List<PlanningUnitElement> planningUnitElementList = projekt.getPlanningUnitGroup(parent)
                        .getPlanningUnitElementList();
                for (final PlanningUnitElement planningUnitElement : planningUnitElementList) {
                    if (planningUnitElement.getRessourceGroup().getName().equals(ressourceGroup.getName())) {
                        element = planningUnitElement;
                    }
                }
                final int index = planningUnitElementList.indexOf(element);
                final PlanningUnitElement planningUnitElement = planningUnitElementList.get(index);
                planningUnitElement.setPlannedDays(daysFromMap);
                planningUnitElement.setPlannedHours(hoursFromMap);
                planningUnitElement.setPlannedMinutes(minutesFromMap);
            } else {
                final ArrayList<PlanningUnit> planningUnitsResultList = new ArrayList<>();
                for (final PlanningUnitGroup planningUnitGroup : projekt.getPlanningUnitGroups()) {
                    projekt.findPlanningUnitAndWriteReferenceInList(planningUnitGroup.getPlanningUnitList(), parent,
                            planningUnitsResultList);
                }
                PlanningUnitElement element = new PlanningUnitElement();
                for (final PlanningUnitElement planningUnitElement : planningUnitsResultList.get(0)
                        .getPlanningUnitElementList()) {
                    if (planningUnitElement.getRessourceGroup().getName().equals(ressourceGroup.getName())) {
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
        final List<PlanningUnitElement> planningUnitElementList = planningUnit.getPlanningUnitElementList();
        for (final PlanningUnitElement planningUnitElement : planningUnitElementList) {
            final RessourceGroup ressourceGroup = planningUnitElement.getRessourceGroup();
            if (!ressourceGroup.getName().equals(AUFGABE_SPALTE)) {
                final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem();
                final int plannedDays = planningUnitElement.getPlannedDays();
                final int plannedHours = planningUnitElement.getPlannedHours();
                final int plannedMinutes = planningUnitElement.getPlannedMinutes();
                daysHoursMinutesItem.setDays(plannedDays);
                daysHoursMinutesItem.setHours(plannedHours);
                daysHoursMinutesItem.setMinutes(plannedMinutes);
                if (ressourceGroupDaysHoursMinutesItemMap.containsKey(ressourceGroup)) {
                    final Integer days = daysHoursMinutesItem.getDays();
                    final Integer hours = daysHoursMinutesItem.getHours();
                    final Integer minutes = daysHoursMinutesItem.getMinutes();
                    final Integer daysFromMap = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getDays();
                    final Integer hoursFromMap = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getHours();
                    final Integer minutesFromMap = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getMinutes();
                    daysHoursMinutesItem.setDays(days + daysFromMap);
                    daysHoursMinutesItem.setHours(hours + hoursFromMap);
                    daysHoursMinutesItem.setMinutes(minutes + minutesFromMap);
                }
                this.correctDaysHoursMinutesItem(daysHoursMinutesItem);
                ressourceGroupDaysHoursMinutesItemMap.put(ressourceGroup, daysHoursMinutesItem);
            } else {
                //TODO logger...
            }
        }
    }


    private void correctDaysHoursMinutesItem(final DaysHoursMinutesItem item) {
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


    @Override
    public String toString() {
        return "PlanningCalculator{" +
                "projektBean=" + projektBean +
                ", ressourceGroupsBean=" + ressourceGroupsBean +
                ", ressourceGroups=" + ressourceGroups +
                ", projekt=" + projekt +
                '}';
    }
}
