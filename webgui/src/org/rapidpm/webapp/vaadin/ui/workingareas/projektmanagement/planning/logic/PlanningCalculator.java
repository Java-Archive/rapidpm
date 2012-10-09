package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic;

import org.apache.log4j.Logger;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.HOURS_DAY;
import static org.rapidpm.Constants.MINS_HOUR;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 30.08.12
 * Time: 13:47
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
@SuppressWarnings("ClassWithoutNoArgConstructor")
public class PlanningCalculator {

    private static final Logger logger = Logger.getLogger(PlanningCalculator.class);

    private List<RessourceGroup> ressourceGroups;
    private PlannedProject projekt;
    private ResourceBundle messages;
    private PlanningCalculatorBean planningCalculatorBean;


    public PlanningCalculator(final ResourceBundle bundle) {
        this.messages = bundle;
        planningCalculatorBean = EJBFactory.getEjbInstance(PlanningCalculatorBean.class);
        final DaoFactoryBean daoFactoryBean = planningCalculatorBean.getDaoFactoryBean();

        final EntityManager entityManager = daoFactoryBean.getEntityManager();
        for(final PlannedProject plannedProject : daoFactoryBean.getPlannedProjectDAO().loadAllEntities()){
            entityManager.refresh(plannedProject);
        }
        for(final PlanningUnitElement planningUnitElement : daoFactoryBean.getPlanningUnitElementDAO().loadAllEntities()){
            entityManager.refresh(planningUnitElement);
        }

        final List<PlannedProject> plannedProjects = daoFactoryBean.getPlannedProjectDAO().loadAllEntities();
        projekt = plannedProjects.get(0);
        ressourceGroups = daoFactoryBean.getRessourceGroupDAO().loadAllEntities();
    }

    public void calculate() {
        calculatePlanningUnits();
        planningCalculatorBean.getDaoFactoryBean().saveOrUpdate(projekt);
    }

    private void calculatePlanningUnits() {
        for (final PlanningUnit planningUnit : projekt.getPlanningUnits()) {
            final Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap = new HashMap<>();
            final List<PlanningUnit> kindPlanningUnits = planningUnit.getKindPlanningUnits();
            if (kindPlanningUnits == null || kindPlanningUnits.isEmpty()) {
                for (final RessourceGroup spalte : ressourceGroups) {
                    final PlanningUnitElement planningUnitElement = new PlanningUnitElement();
                    planningUnitElement.setPlannedDays(0);
                    planningUnitElement.setPlannedHours(0);
                    planningUnitElement.setPlannedMinutes(0);
                    planningUnitElement.setRessourceGroup(spalte);
                    planningUnit.getPlanningUnitElementList().add(planningUnitElement);
                }
            } else {
                this.calculatePlanningUnits(kindPlanningUnits, planningUnit,
                        ressourceGroupDaysHoursMinutesItemMap);
            }
        }
    }


    private void calculatePlanningUnits(final List<PlanningUnit> planningUnits, final PlanningUnit parent,
                                      final Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap) {
        for (final PlanningUnit planningUnit : planningUnits) {
            final List<PlanningUnit> kindPlanningUnits = planningUnit.getKindPlanningUnits();
            if (kindPlanningUnits == null || kindPlanningUnits.isEmpty()) {
                this.addiereZeileZurRessourceMap(ressourceGroupDaysHoursMinutesItemMap, planningUnit);
            } else {
                this.calculatePlanningUnits(kindPlanningUnits, planningUnit,
                        ressourceGroupDaysHoursMinutesItemMap);
            }
        }
        for (final RessourceGroup ressourceGroup : ressourceGroups) {
            Integer daysFromMap;
            Integer hoursFromMap;
            Integer minutesFromMap;
            try{
                daysFromMap = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getDays();
                hoursFromMap = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getHours();
                minutesFromMap = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getMinutes();
            }catch(NullPointerException e){
                daysFromMap = 0;
                hoursFromMap = 0;
                minutesFromMap = 0;
            }

            PlanningUnitElement element = new PlanningUnitElement();
            for (final PlanningUnitElement planningUnitElement : parent.getPlanningUnitElementList()) {
                if (planningUnitElement.getRessourceGroup().equals(ressourceGroup)) {
                    element = planningUnitElement;
                }
            }
            final int index = parent.getPlanningUnitElementList().indexOf(element);
            final PlanningUnitElement planningUnitElement = parent.getPlanningUnitElementList().get(index);
            planningUnitElement.setPlannedDays(daysFromMap);
            planningUnitElement.setPlannedHours(hoursFromMap);
            planningUnitElement.setPlannedMinutes(minutesFromMap);
            planningCalculatorBean.getDaoFactoryBean().saveOrUpdate(planningUnitElement);
        }
    }

    private void addiereZeileZurRessourceMap(final Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap,
                                             final PlanningUnit planningUnit) {
        final List<PlanningUnitElement> planningUnitElementList = planningUnit.getPlanningUnitElementList();
        for (final PlanningUnitElement planningUnitElement : planningUnitElementList) {
            final RessourceGroup ressourceGroup = planningUnitElement.getRessourceGroup();
            final String aufgabe = messages.getString("aufgabe");
            if (!ressourceGroup.getName().equals(aufgabe)) {
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
                logger.warn("unerwartetes Verhalten in PlanningCalculator");
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

}
