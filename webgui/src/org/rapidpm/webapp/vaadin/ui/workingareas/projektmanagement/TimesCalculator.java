package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement;

//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;
import com.vaadin.server.VaadinSession;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProjectDAO;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroupDAO;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;

import java.text.DecimalFormat;
import java.util.*;

import static org.rapidpm.Constants.*;

/**
* RapidPM - www.rapidpm.org
* User: Marco
* Date: 31.08.12
* Time: 14:34
* This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
*/
public class TimesCalculator {

    private final Screen screen;
    private List<RessourceGroup> ressourceGroups;
    private ResourceBundle messages;
    private MainUI ui;

    private PlannedProject currentProject;

    private Map<RessourceGroup, Double> relativeWerte = new HashMap<>();
    private final Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap = new HashMap<>();
    private Integer gesamtSummeInMin;
    private Double mannTageExakt;
    
    private Integer workingHoursPerDay;
//    private TimesCalculatorBean bean;

    public TimesCalculator(final Screen screen) {
        this.screen = screen;
        this.messages = screen.getMessagesBundle();
        this.ui = screen.getUi();
//        bean = EJBFactory.getEjbInstance(TimesCalculatorBean.class);
//        final DaoFactoryBean baseDaoFactoryBean = bean.getDaoFactoryBean();
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final RessourceGroupDAO ressourceGroupDAO = daoFactory.getRessourceGroupDAO();
        ressourceGroups = ressourceGroupDAO.loadAllEntities();
    }

    public void calculate() {
        final VaadinSession session = screen.getUi().getSession();
        currentProject = session.getAttribute(PlannedProject.class);
        for (final RessourceGroup spalte : this.ressourceGroups) {
            relativeWerte.put(spalte, 0.0);
        }

        calculatePlanningUnitsAndTotalsAbsolut();
        calculateTotalsRelative();
        calculateMannTage();
    }

    private void calculateMannTage() {
        mannTageExakt = gesamtSummeInMin / MINS_HOUR.doubleValue() / currentProject.getHoursPerWorkingDay().doubleValue();
    }

    private void calculatePlanningUnitsAndTotalsAbsolut() {
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final PlannedProjectDAO plannedProjectDAO = daoFactory.getPlannedProjectDAO();
        final PlannedProject plannedProjectFromSession = ui.getSession().getAttribute(PlannedProject.class);
        final PlannedProject projekt = plannedProjectDAO.findByID(plannedProjectFromSession.getId());
        for (final PlanningUnit planningUnit : projekt.getPlanningUnits()) {
            daoFactory.getEntityManager().refresh(planningUnit);
            calculatePlanningUnits(planningUnit, planningUnit.getKindPlanningUnits());
        }
    }


    private void calculatePlanningUnits(PlanningUnit parentPlanningUnit, final Set<PlanningUnit> planningUnits) {
        if(planningUnits == null || planningUnits.isEmpty()){
            addiereZeileZurRessourceMap(ressourceGroupDaysHoursMinutesItemMap, parentPlanningUnit);
        } else {
            for (final PlanningUnit planningUnit : planningUnits) {
                final Set<PlanningUnit> kindPlanningUnits = planningUnit.getKindPlanningUnits();
                if (kindPlanningUnits == null || kindPlanningUnits.isEmpty()) {
                    addiereZeileZurRessourceMap(ressourceGroupDaysHoursMinutesItemMap, planningUnit);
                } else {
                    calculatePlanningUnits(planningUnit, kindPlanningUnits);
                }
            }
        }
    }

    private void addiereZeileZurRessourceMap(final Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap,
                                             final PlanningUnit planningUnit) {
        for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
            final RessourceGroup oldRessourceGroup = planningUnitElement.getRessourceGroup();
            final String aufgabe = messages.getString("aufgabe");
            if (!oldRessourceGroup.getName().equals(aufgabe)) {
                final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem(planningUnitElement,
                        currentProject.getHoursPerWorkingDay());
                if (ressourceGroupDaysHoursMinutesItemMap.containsKey(oldRessourceGroup)) {
                    final int days = daysHoursMinutesItem.getDays() + ressourceGroupDaysHoursMinutesItemMap.get(oldRessourceGroup).getDays();
                    final int hours = daysHoursMinutesItem.getHours() + ressourceGroupDaysHoursMinutesItemMap.get(oldRessourceGroup).getHours();
                    final int minutes = daysHoursMinutesItem.getMinutes() + ressourceGroupDaysHoursMinutesItemMap.get(oldRessourceGroup).getMinutes();
                    daysHoursMinutesItem.setDays(days);
                    daysHoursMinutesItem.setHours(hours);
                    daysHoursMinutesItem.setMinutes(minutes);
                }
                planningUnitElement.setPlannedMinutes(daysHoursMinutesItem.getMinutesFromDaysHoursMinutes());
                final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
                daoFactory.saveOrUpdateTX(planningUnitElement);
                ressourceGroupDaysHoursMinutesItemMap.put(oldRessourceGroup, daysHoursMinutesItem);
            }
        }
    }

    private void calculateTotalsRelative() {
        final Integer minsPerDay = (int)(currentProject.getHoursPerWorkingDay() * MINS_HOUR);
        gesamtSummeInMin = 0;
        for (final Map.Entry<RessourceGroup, DaysHoursMinutesItem> absoluteWerteEntry :
                ressourceGroupDaysHoursMinutesItemMap.entrySet()) {
            gesamtSummeInMin += absoluteWerteEntry.getValue().getDays() * minsPerDay;
            gesamtSummeInMin += absoluteWerteEntry.getValue().getHours() * MINS_HOUR;
            gesamtSummeInMin += absoluteWerteEntry.getValue().getMinutes();
        }
        for (final Map.Entry<RessourceGroup, DaysHoursMinutesItem> absoluteWerteEntry :
                ressourceGroupDaysHoursMinutesItemMap.entrySet()) {
            final RessourceGroup absoluterWertRessourceGroup = absoluteWerteEntry.getKey();

            Integer absoluterWertWert = absoluteWerteEntry.getValue().getDays() * minsPerDay;
            absoluterWertWert += absoluteWerteEntry.getValue().getHours() * MINS_HOUR;
            absoluterWertWert += absoluteWerteEntry.getValue().getMinutes();
            relativeWerte.put(absoluterWertRessourceGroup, absoluterWertWert.doubleValue() / gesamtSummeInMin.doubleValue() * 100.0);
        }
    }



    public Map<RessourceGroup, Double> getRelativeWerte() {
        return relativeWerte;
    }

    public Map<RessourceGroup, DaysHoursMinutesItem> getAbsoluteWerte() {
        return ressourceGroupDaysHoursMinutesItemMap;
    }

    public DaysHoursMinutesItem getGesamtSummeItem() {
        final VaadinSession session = screen.getUi().getSession();
        final PlannedProject currentProject = session.getAttribute(PlannedProject.class);
        final PlanningUnitElement tempPlanningUnitElement = new PlanningUnitElement();
        tempPlanningUnitElement.setPlannedMinutes(gesamtSummeInMin);
        final DaysHoursMinutesItem item = new DaysHoursMinutesItem(tempPlanningUnitElement, currentProject.getHoursPerWorkingDay());
        item.calculateMinutesToDaysHoursMinutes();
        return item;
    }

    public String getMannTageGerundet() {
        final DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);
        return format.format(mannTageExakt);
    }
}
