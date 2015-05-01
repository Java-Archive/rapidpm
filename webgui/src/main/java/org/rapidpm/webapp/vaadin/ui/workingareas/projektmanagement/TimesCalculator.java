package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement;

//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;
import com.vaadin.server.VaadinSession;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
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
    private final Map<RessourceGroup, Integer> ressourceGroupDaysHoursMinutesItemMap = new HashMap<>();
    private Integer gesamtSummeInMin;
    private Double mannTageExakt;

    public TimesCalculator(final Screen screen) {
        this.screen = screen;
        this.messages = screen.getMessagesBundle();
        this.ui = screen.getUi();
        final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
        final RessourceGroupDAO ressourceGroupDAO = daoFactory.getRessourceGroupDAO();
        ressourceGroups = ressourceGroupDAO.findAll();
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
        final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
        for (final PlanningUnit planningUnit : currentProject.getPlanningUnits()) {
//            daoFactory.getEntityManager().refresh(planningUnit);
            calculatePlanningUnits(planningUnit, planningUnit.getKindPlanningUnits());
        }
    }


    private void calculatePlanningUnits(final PlanningUnit parentPlanningUnit, final Set<PlanningUnit> planningUnits) {
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

    private void addiereZeileZurRessourceMap(final Map<RessourceGroup, Integer> ressourceGroupDaysHoursMinutesItemMap,
                                             final PlanningUnit planningUnit) {
        for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
            final RessourceGroup ressourceGroup = planningUnitElement.getRessourceGroup();
            final String aufgabe = messages.getString("aufgabe");
            if (!ressourceGroup.getName().equals(aufgabe)) {
                if (ressourceGroupDaysHoursMinutesItemMap.containsKey(ressourceGroup)) {
                            final Integer oldMinutes = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup);
                            ressourceGroupDaysHoursMinutesItemMap.put(ressourceGroup,
                                    planningUnitElement.getPlannedMinutes() + oldMinutes);
                } else {
                    ressourceGroupDaysHoursMinutesItemMap.put(ressourceGroup, planningUnitElement.getPlannedMinutes());
                }
            }
        }
    }

    private void calculateTotalsRelative() {
        gesamtSummeInMin = 0;
        for (final Map.Entry<RessourceGroup, Integer> absoluteWerteEntry : ressourceGroupDaysHoursMinutesItemMap
                .entrySet()) {
            gesamtSummeInMin += absoluteWerteEntry.getValue();
        }
        for (final Map.Entry<RessourceGroup, Integer> absoluteWerteEntry : ressourceGroupDaysHoursMinutesItemMap
                .entrySet()) {
            final RessourceGroup absoluterWertRessourceGroup = absoluteWerteEntry.getKey();
            final Integer absoluterWertWert = absoluteWerteEntry.getValue();
            relativeWerte.put(absoluterWertRessourceGroup, absoluterWertWert.doubleValue() / gesamtSummeInMin.doubleValue() * 100.0);
        }
    }

    public Map<RessourceGroup, Double> getRelativeWerte() {
        return relativeWerte;
    }

    public Map<RessourceGroup, Integer> getAbsoluteWerte() {
        return ressourceGroupDaysHoursMinutesItemMap;
    }

    public DaysHoursMinutesItem getGesamtSummeItem() {
        final VaadinSession session = screen.getUi().getSession();
        final PlannedProject currentProject = session.getAttribute(PlannedProject.class);
        final DaysHoursMinutesItem item = new DaysHoursMinutesItem(gesamtSummeInMin, currentProject.getHoursPerWorkingDay());
        return item;
    }

    public String getMannTageGerundet() {
        final DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);
        return format.format(mannTageExakt);
    }
}
