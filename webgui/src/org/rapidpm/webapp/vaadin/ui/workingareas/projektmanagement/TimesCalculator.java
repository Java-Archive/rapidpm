package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement;

import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroupDAO;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.*;

/**
* RapidPM - www.rapidpm.org
* User: Marco
* Date: 31.08.12
* Time: 14:34
* This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
*/
public class TimesCalculator {

    private List<RessourceGroup> ressourceGroups;
    private ResourceBundle messages;

    private Map<RessourceGroup, Double> relativeWerte = new HashMap<>();
    private final Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap = new HashMap<>();
    private Integer gesamtSummeInMin;
    private Double mannTageExakt;
    private TimesCalculatorBean bean;

    public TimesCalculator(final ResourceBundle bundle) {
        this.messages = bundle;
        bean = EJBFactory.getEjbInstance(TimesCalculatorBean.class);
        final DaoFactoryBean baseDaoFactoryBean = bean.getDaoFactoryBean();
        final RessourceGroupDAO ressourceGroupDAO = baseDaoFactoryBean.getRessourceGroupDAO();
        ressourceGroups = ressourceGroupDAO.loadAllEntities();
    }

    public void calculate() {
        for (final RessourceGroup spalte : this.ressourceGroups) {
            relativeWerte.put(spalte, 0.0);
        }

        calculatePlanningUnitsAndTotalsAbsolut();
        calculateTotalsRelative();
        calculateMannTage();
    }

    private void calculateMannTage() {
        mannTageExakt = gesamtSummeInMin / MINS_HOUR.doubleValue() / WORKINGHOURS_DAY.doubleValue();
    }

    private void calculatePlanningUnitsAndTotalsAbsolut() {
        //final Integer currentProjectIndex = projektBean.getCurrentProjectIndex();
        //final Projekt projekt = projektBean.getProjekte().get(currentProjectIndex);
        final PlannedProject projekt = bean.getDaoFactoryBean().getPlannedProjectDAO().loadAllEntities().get(0);
        for (final PlanningUnit planningUnit : projekt.getPlanningUnits()) {
            calculatePlanningUnits(planningUnit.getKindPlanningUnits());
        }
    }


    private void calculatePlanningUnits(final List<PlanningUnit> planningUnits) {
        for (final PlanningUnit planningUnit : planningUnits) {
            final List<PlanningUnit> kindPlanningUnits = planningUnit.getKindPlanningUnits();
            if (kindPlanningUnits == null || kindPlanningUnits.isEmpty()) {
                addiereZeileZurRessourceMap(ressourceGroupDaysHoursMinutesItemMap, planningUnit);
            } else {
                calculatePlanningUnits(kindPlanningUnits);
            }
        }

    }

    private void addiereZeileZurRessourceMap(final Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap,
                                             final PlanningUnit planningUnit) {
        for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
            final RessourceGroup oldRessourceGroup = planningUnitElement.getRessourceGroup();
            final String aufgabe = messages.getString("aufgabe");
            if (!oldRessourceGroup.getName().equals(aufgabe)) {
                final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem();
                daysHoursMinutesItem.setDays(planningUnitElement.getPlannedDays());
                daysHoursMinutesItem.setHours(planningUnitElement.getPlannedHours());
                daysHoursMinutesItem.setMinutes(planningUnitElement.getPlannedMinutes());
                if (ressourceGroupDaysHoursMinutesItemMap.containsKey(oldRessourceGroup)) {
                    final int days = daysHoursMinutesItem.getDays() + ressourceGroupDaysHoursMinutesItemMap.get(oldRessourceGroup).getDays();
                    final int hours = daysHoursMinutesItem.getHours() + ressourceGroupDaysHoursMinutesItemMap.get(oldRessourceGroup).getHours();
                    final int minutes = daysHoursMinutesItem.getMinutes() + ressourceGroupDaysHoursMinutesItemMap.get(oldRessourceGroup).getMinutes();
                    daysHoursMinutesItem.setDays(days);
                    daysHoursMinutesItem.setHours(hours);
                    daysHoursMinutesItem.setMinutes(minutes);
                }
                correctDaysHoursMinutesItem(daysHoursMinutesItem);
                ressourceGroupDaysHoursMinutesItemMap.put(oldRessourceGroup, daysHoursMinutesItem);
            }
        }
    }

    private void calculateTotalsRelative() {
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

    public Map<RessourceGroup, Double> getRelativeWerte() {
        return relativeWerte;
    }

    public Map<RessourceGroup, DaysHoursMinutesItem> getAbsoluteWerte() {
        return ressourceGroupDaysHoursMinutesItemMap;
    }

    public Integer getGesamtSummeInMin() {
        return gesamtSummeInMin;
    }

    public DaysHoursMinutesItem getGesamtSummeItem() {
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
