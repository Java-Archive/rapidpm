package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic;

import com.vaadin.ui.Notification;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.*;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.MainUI;

import java.security.InvalidKeyException;
import java.util.*;

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

    private PlannedProject projekt;
    private ResourceBundle messages;
    private DaoFactory daoFactory;
    private Integer counter = 0;

    private Map<PlanningUnitElement, Integer> planningUnitElements2 = new HashMap<>();
    private Map<PlanningUnitElement, Integer> planningUnitElements1 = new HashMap<>();


    public PlanningCalculator(final ResourceBundle bundle, MainUI ui) {
        this.messages = bundle;
        daoFactory = DaoFactorySingleton.getInstance();
        final PlannedProjectDAO plannedProjectDAO = daoFactory.getPlannedProjectDAO();
        final PlannedProject projectFromSession = ui.getSession().getAttribute(PlannedProject.class);
        projekt = plannedProjectDAO.findByID(projectFromSession.getId(), true);
//        daoFactory.getEntityManager().refresh(projekt);
    }

    public void calculate() {
        for (final PlanningUnit planningUnit : projekt.getPlanningUnits()) {
            resetParents(planningUnit);
            calculateRessources(planningUnit);
//            daoFactory.new Transaction() {
//                @Override
//                public void doTask() {
//                    final EntityManager orientDB = daoFactory.getEntityManager();
//                    for(final PlanningUnitElement planningUnitElement : planningUnitElements2.keySet()){
//                        orientDB.persist(planningUnitElement);
//                    }
//                    orientDB.flush();
//                    orientDB.refresh(projekt);
//                }
//            }.execute();
        }

    }

    private void resetParents(final PlanningUnit planningUnit) {

        if(planningUnit.getKindPlanningUnits() != null && !planningUnit.getKindPlanningUnits().isEmpty()){
            for(final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()){
                planningUnitElement.setPlannedMinutes(0);
                planningUnitElements1.put(planningUnitElement,counter++);
            }
            for(final PlanningUnit kindPlanningUnit : planningUnit.getKindPlanningUnits()){
                resetParents(kindPlanningUnit);
            }
//            daoFactory.new Transaction() {
//                @Override
//                public void doTask() {
//                    final EntityManager orientDB = daoFactory.getEntityManager();
//                    for(final PlanningUnitElement planningUnitElement : planningUnitElements1.keySet()){
//                        orientDB.persist(planningUnitElement);
//                    }
//                    orientDB.flush();
//                }
//            }.execute();
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
                    planningUnitElement.setPlannedMinutes(planningUnitElement.getPlannedMinutes() +
                            kindPlanningUnitElement.getPlannedMinutes());
                    planningUnitElements2.put(planningUnitElement,counter++);
                }
            }
        }
    }
}
