package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.logic;

import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.*;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.rapidpm.Constants.*;

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
    private List<RessourceGroup> ressourceGroups;

    private Map<RessourceGroup, Double> relativeWerte = new HashMap<RessourceGroup, Double>();
    private final Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap = new HashMap<>();
    private Integer gesamtSummeInMin;
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
        mannTageExakt = gesamtSummeInMin / MINS_HOUR.doubleValue() / WORKINGHOURS_DAY.doubleValue();
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

    private void addiereZeileZurRessourceMap(final Map<RessourceGroup, DaysHoursMinutesItem>
                                                     ressourceGroupDaysHoursMinutesItemMap, PlanningUnit planningUnit) {
        for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
            if (!planningUnitElement.getRessourceGroup().getName().equals(AUFGABE_SPALTE)) {
                final RessourceGroup ressourceGroup1 = planningUnitElement.getRessourceGroup();
                final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem();
                daysHoursMinutesItem.setDays(planningUnitElement.getPlannedDays());
                daysHoursMinutesItem.setHours(planningUnitElement.getPlannedHours());
                daysHoursMinutesItem.setMinutes(planningUnitElement.getPlannedMinutes());
                if (ressourceGroupDaysHoursMinutesItemMap.containsKey(ressourceGroup1)) {
                    final int days = daysHoursMinutesItem.getDays() + ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup1).getDays();
                    final int hours = daysHoursMinutesItem.getHours() + ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup1).getHours();
                    final int minutes = daysHoursMinutesItem.getMinutes() + ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup1).getMinutes();
                    daysHoursMinutesItem.setDays(days);
                    daysHoursMinutesItem.setHours(hours);
                    daysHoursMinutesItem.setMinutes(minutes);
                }
                correctDaysHoursMinutesItem(daysHoursMinutesItem);
                ressourceGroupDaysHoursMinutesItemMap.put(ressourceGroup1, daysHoursMinutesItem);
            }
        }
    }

    private void computeTotalsRelative() {
        gesamtSummeInMin = 0;
        for (final Map.Entry<RessourceGroup, DaysHoursMinutesItem> absoluteWerteEntry :
                ressourceGroupDaysHoursMinutesItemMap.entrySet()) {
            gesamtSummeInMin += absoluteWerteEntry.getValue().getDays() * MINS_DAY;
            gesamtSummeInMin += absoluteWerteEntry.getValue().getHours() * MINS_HOUR;
            gesamtSummeInMin += absoluteWerteEntry.getValue().getMinutes();
        }
        for (final Map.Entry<RessourceGroup, DaysHoursMinutesItem> absoluteWerteEntry :
                ressourceGroupDaysHoursMinutesItemMap.entrySet()) {
            final RessourceGroup absoluterWertRessourceGroup = absoluteWerteEntry.getKey();
            Integer absoluterWertWert = absoluteWerteEntry.getValue().getDays() * MINS_DAY;
            absoluterWertWert += absoluteWerteEntry.getValue().getHours() * MINS_HOUR;
            absoluterWertWert += absoluteWerteEntry.getValue().getMinutes();
            relativeWerte.put(absoluterWertRessourceGroup, absoluterWertWert.doubleValue() / gesamtSummeInMin.doubleValue() * 100.0);
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

    public Map<RessourceGroup, Double> getRelativeWerte() {
        return relativeWerte;
    }

    public Map<RessourceGroup, DaysHoursMinutesItem> getAbsoluteWerte() {
        return ressourceGroupDaysHoursMinutesItemMap;
    }

    public Integer getGesamtSummeInMin() {
        return gesamtSummeInMin;
    }

    public DaysHoursMinutesItem getGesamtSummeItem(){
        final DaysHoursMinutesItem item = new DaysHoursMinutesItem();
        item.setMinutes(gesamtSummeInMin);
        correctDaysHoursMinutesItem(item);
        return item;
    }

    public Double getMannTageExakt() {
        return mannTageExakt;
    }

    public String getMannTageGerundet() {
        final DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);
        return format.format(mannTageExakt);
    }
}
