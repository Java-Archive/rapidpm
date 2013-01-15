package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic;

import org.apache.log4j.Logger;
//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.*;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;

import javax.persistence.EntityManager;
import java.util.*;

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
    private DaoFactory daoFactory;
    private Integer counter = 0;

    private Map<PlanningUnitElement, Integer> planningUnitElements2 = new HashMap<>();
    private Map<PlanningUnitElement, Integer> planningUnitElements1 = new HashMap<>();
//    private PlanningCalculatorBean planningCalculatorBean;


    public PlanningCalculator(final ResourceBundle bundle, MainUI ui) {
        this.messages = bundle;
//        planningCalculatorBean = EJBFactory.getEjbInstance(PlanningCalculatorBean.class);
//        final DaoFactoryBean daoFactoryBean = planningCalculatorBean.getDaoFactoryBean();

        daoFactory = DaoFactorySingelton.getInstance();
        final PlannedProjectDAO plannedProjectDAO = daoFactory.getPlannedProjectDAO();
        final PlannedProject projectFromSession = ui.getSession().getAttribute(PlannedProject.class);
        projekt = plannedProjectDAO.findByID(projectFromSession.getId());
        daoFactory.getEntityManager().refresh(projekt);
    }

    public void calculate() {
        for (final PlanningUnit planningUnit : projekt.getPlanningUnits()) {
            resetParents(planningUnit);
            calculateRessources(planningUnit);
            daoFactory.new Transaction() {
                @Override
                public void doTask() {
                    final EntityManager entityManager = daoFactory.getEntityManager();
                    for(final PlanningUnitElement planningUnitElement : planningUnitElements2.keySet()){
                        entityManager.persist(planningUnitElement);
                    }
                    entityManager.flush();
                    entityManager.refresh(projekt);
                }
            }.execute();
        }

    }

    private void resetParents(final PlanningUnit planningUnit) {

        if(planningUnit.getKindPlanningUnits() != null && !planningUnit.getKindPlanningUnits().isEmpty()){
            for(final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()){
                planningUnitElement.setPlannedDays(0);
                planningUnitElement.setPlannedHours(0);
                planningUnitElement.setPlannedMinutes(0);
                planningUnitElements1.put(planningUnitElement,counter++);
            }
            for(final PlanningUnit kindPlanningUnit : planningUnit.getKindPlanningUnits()){
                resetParents(kindPlanningUnit);
            }
            daoFactory.new Transaction() {
                @Override
                public void doTask() {
                    final EntityManager entityManager = daoFactory.getEntityManager();
                    for(final PlanningUnitElement planningUnitElement : planningUnitElements1.keySet()){
                        entityManager.persist(planningUnitElement);
                    }
                    entityManager.flush();
                }
            }.execute();
        } else {
            //do nothing
        }
    }

    private void calculateRessources(final PlanningUnit planningUnit) {
            if(planningUnit.getKindPlanningUnits() != null && !planningUnit.getKindPlanningUnits().isEmpty()){
                for(final PlanningUnit kindPlanningUnit : planningUnit.getKindPlanningUnits()){
                    calculateRessources(kindPlanningUnit);
                    generateCells(planningUnit, kindPlanningUnit);
                }
            } else {
                //do nothing
            }
    }

    private void generateCells(final PlanningUnit planningUnit, final PlanningUnit kindPlanningUnit) {

        for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
            final RessourceGroup parentPlanningUnitRessourceGroup = planningUnitElement.getRessourceGroup();
            for (final PlanningUnitElement kindPlanningUnitElement : kindPlanningUnit.getPlanningUnitElementList()) {
                final RessourceGroup kindPlanningUnitElementRessourceGroup = kindPlanningUnitElement.getRessourceGroup();
                if (kindPlanningUnitElementRessourceGroup.equals(parentPlanningUnitRessourceGroup)) {
                    final DaysHoursMinutesItem planningUnitElementItem = new DaysHoursMinutesItem(planningUnitElement);
                    final DaysHoursMinutesItem childPlanningUnitElementItem = new DaysHoursMinutesItem
                            (kindPlanningUnitElement);

                    planningUnitElementItem.setDays(planningUnitElementItem.getDays() + childPlanningUnitElementItem
                            .getDays());
                    planningUnitElementItem.setHours(planningUnitElementItem.getHours() + childPlanningUnitElementItem
                            .getHours());
                    planningUnitElementItem.setMinutes(planningUnitElementItem.getMinutes() +
                            childPlanningUnitElementItem.getMinutes());

                    correctDaysHoursMinutesItem(planningUnitElementItem);

                    planningUnitElement.setPlannedDays(planningUnitElementItem.getDays());
                    planningUnitElement.setPlannedHours(planningUnitElementItem.getHours());
                    planningUnitElement.setPlannedMinutes(planningUnitElementItem.getMinutes());
                    planningUnitElements2.put(planningUnitElement,counter++);
                }

                //
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


//    private void calculatePlanningUnits(final Set<PlanningUnit> planningUnits, final PlanningUnit parent,
//                                      final Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap) {
//        if(parent.getKindPlanningUnits() == null || parent.getKindPlanningUnits().isEmpty()){
//            this.addiereZeileZurRessourceMap(ressourceGroupDaysHoursMinutesItemMap, parent);
//        } else {
//            for (final PlanningUnit planningUnit : planningUnits) {
//                final Set<PlanningUnit> kindPlanningUnits = planningUnit.getKindPlanningUnits();
//                if (kindPlanningUnits == null || kindPlanningUnits.isEmpty()) {
//                    this.addiereZeileZurRessourceMap(ressourceGroupDaysHoursMinutesItemMap, planningUnit);
//                } else {
//                    this.calculatePlanningUnits(kindPlanningUnits, planningUnit,
//                            ressourceGroupDaysHoursMinutesItemMap);
//                }
//            }
//        }
//        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
//        for (final RessourceGroup ressourceGroup : ressourceGroups) {
//            Integer daysFromMap;
//            Integer hoursFromMap;
//            Integer minutesFromMap;
//            try{
//                daysFromMap = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getDays();
//                hoursFromMap = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getHours();
//                minutesFromMap = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getMinutes();
//            }catch(NullPointerException e){
//                daysFromMap = 0;
//                hoursFromMap = 0;
//                minutesFromMap = 0;
//            }
//
//            PlanningUnitElement element = new PlanningUnitElement();
//            for (final PlanningUnitElement planningUnitElement : parent.getPlanningUnitElementList()) {
//                if (planningUnitElement.getRessourceGroup().equals(ressourceGroup)) {
//                    element = planningUnitElement;
//                }
//            }
//            final int index = parent.getPlanningUnitElementList().indexOf(element);
//            final PlanningUnitElement planningUnitElement = parent.getPlanningUnitElementList().get(index);
//            planningUnitElement.setPlannedDays(daysFromMap);
//            planningUnitElement.setPlannedHours(hoursFromMap);
//            planningUnitElement.setPlannedMinutes(minutesFromMap);
//
//            daoFactory.saveOrUpdateTX(planningUnitElement);
//        }
//    }
//
//    private void addiereZeileZurRessourceMap(final Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap,
//                                             final PlanningUnit planningUnit) {
//        final List<PlanningUnitElement> planningUnitElementList = planningUnit.getPlanningUnitElementList();
//        for (final PlanningUnitElement planningUnitElement : planningUnitElementList) {
//            final RessourceGroup ressourceGroup = planningUnitElement.getRessourceGroup();
//            final String aufgabe = messages.getString("aufgabe");
//            if (!ressourceGroup.getName().equals(aufgabe)) {
//                final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem();
//                final int plannedDays = planningUnitElement.getPlannedDays();
//                final int plannedHours = planningUnitElement.getPlannedHours();
//                final int plannedMinutes = planningUnitElement.getPlannedMinutes();
//                daysHoursMinutesItem.setDays(plannedDays);
//                daysHoursMinutesItem.setHours(plannedHours);
//                daysHoursMinutesItem.setMinutes(plannedMinutes);
//                if (ressourceGroupDaysHoursMinutesItemMap.containsKey(ressourceGroup)) {
//                    final Integer days = daysHoursMinutesItem.getDays();
//                    final Integer hours = daysHoursMinutesItem.getHours();
//                    final Integer minutes = daysHoursMinutesItem.getMinutes();
//                    final Integer daysFromMap = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getDays();
//                    final Integer hoursFromMap = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getHours();
//                    final Integer minutesFromMap = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup).getMinutes();
//                    daysHoursMinutesItem.setDays(days + daysFromMap);
//                    daysHoursMinutesItem.setHours(hours + hoursFromMap);
//                    daysHoursMinutesItem.setMinutes(minutes + minutesFromMap);
//                }
//                this.correctDaysHoursMinutesItem(daysHoursMinutesItem);
//                ressourceGroupDaysHoursMinutesItemMap.put(ressourceGroup, daysHoursMinutesItem);
//                System.out.println("Put "+daysHoursMinutesItem.toString()+" for "+ressourceGroup.getName() + "into " +
//                        "map");
//            } else {
//                logger.warn("unerwartetes Verhalten in PlanningCalculator");
//            }
//        }
//    }




}
