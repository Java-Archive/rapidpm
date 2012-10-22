package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.HierarchicalContainer;
import org.apache.log4j.Logger;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.AufwandProjInitScreen;

import javax.persistence.EntityManager;
import java.util.*;

import static org.rapidpm.Constants.HOURS_DAY;
import static org.rapidpm.Constants.MINS_HOUR;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 31.08.12
 * Time: 16:29
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class TreeTableDataSourceFiller {

    private static final Logger logger = Logger.getLogger(TreeTableDataSourceFiller.class);

    private TreeTableDataSourceFillerBean bean;
    private List<RessourceGroup> ressourceGroups;
    private final Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap = new HashMap<>();
    private ResourceBundle messages;
    private HierarchicalContainer dataSource;
    private AufwandProjInitScreen screen;

    public TreeTableDataSourceFiller(final AufwandProjInitScreen screen, final ResourceBundle bundle,
                                     final HierarchicalContainer dSource) {
        this.screen = screen;
        this.messages = bundle;
        dataSource = dSource;

        bean = EJBFactory.getEjbInstance(TreeTableDataSourceFillerBean.class);
        final DaoFactoryBean baseDaoFactoryBean = bean.getDaoFactoryBean();
        refreshEntities(baseDaoFactoryBean);

        ressourceGroups = baseDaoFactoryBean.getRessourceGroupDAO().loadAllEntities();

        dataSource.removeAllItems();
        final String aufgabe = messages.getString("aufgabe");
        dataSource.addContainerProperty(aufgabe, String.class, null);
        for (final RessourceGroup ressourceGroup : ressourceGroups) {
            dataSource.addContainerProperty(ressourceGroup.getName(), String.class, "");
        }
    }

    public void fill() {
        computePlanningUnitsAndTotalsAbsolut();
    }

    private void computePlanningUnitsAndTotalsAbsolut() {
        final PlannedProject projectFromSession = screen.getUi().getCurrentProject();
        final PlannedProject projectFromDB = bean.getDaoFactoryBean().getPlannedProjectDAO().findByID
                (projectFromSession.getId());
        final Set<PlanningUnit> planningUnits = projectFromDB.getPlanningUnits();
        for (final PlanningUnit planningUnit : planningUnits) {
            final String planningUnitName = planningUnit.getPlanningUnitName();
            final Item planningUnitItem = dataSource.addItem(planningUnitName);
            final String aufgabe = messages.getString("aufgabe");
            planningUnitItem.getItemProperty(aufgabe).setValue(planningUnitName);
            final List<PlanningUnit> planningUnitList = planningUnit.getKindPlanningUnits();
            if (planningUnitList == null || planningUnitList.isEmpty()) {
                for (final RessourceGroup spalte : ressourceGroups) {
                    for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
                        if (planningUnitElement.getRessourceGroup().equals(spalte)) {
                            planningUnitElement.setPlannedDays(0);
                            planningUnitElement.setPlannedHours(0);
                            planningUnitElement.setPlannedMinutes(0);
                            final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem(planningUnitElement);
                            final Property<?> itemProperty = planningUnitItem.getItemProperty(spalte.getName());
                            itemProperty.setValue(daysHoursMinutesItem.toString());
                        }
                    }
                }
            } else {
                computePlanningUnits(planningUnitList, planningUnitName);
            }
        }
    }


    private void computePlanningUnits(final List<PlanningUnit> planningUnits, final String parent) {
        for (final PlanningUnit planningUnit : planningUnits) {
            final String planningUnitName = planningUnit.getPlanningUnitName();
            final Item planningUnitItem = dataSource.addItem(planningUnitName);
            final String aufgabe = messages.getString("aufgabe");
            planningUnitItem.getItemProperty(aufgabe).setValue(planningUnitName);
            dataSource.setParent(planningUnitName, parent);
            final List<PlanningUnit> kindPlanningUnits = planningUnit.getKindPlanningUnits();
            if (kindPlanningUnits == null || kindPlanningUnits.isEmpty()) {
                for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
                    final DaysHoursMinutesItem item = new DaysHoursMinutesItem(planningUnitElement);
                    planningUnitItem.getItemProperty(planningUnitElement.getRessourceGroup().getName()).setValue(item.toString());
                }
                addiereZeileZurRessourceMap(planningUnit);
            } else {
                computePlanningUnits(kindPlanningUnits, planningUnitName);
            }
        }
        for (final RessourceGroup spalte : ressourceGroups) {
            final String mapValue = ressourceGroupDaysHoursMinutesItemMap.get(spalte).toString();
            dataSource.getItem(parent).getItemProperty(spalte.getName()).setValue(mapValue);
        }
    }

    private void addiereZeileZurRessourceMap(final PlanningUnit planningUnit) {
        for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
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
                    final DaysHoursMinutesItem daysHoursMinutesItemFromMap = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup);
                    daysHoursMinutesItem.setDays(days + daysHoursMinutesItemFromMap.getDays());
                    daysHoursMinutesItem.setHours(hours + daysHoursMinutesItemFromMap
                            .getHours());
                    daysHoursMinutesItem.setMinutes(minutes + daysHoursMinutesItemFromMap.getMinutes());
                }
                correctDaysHoursMinutesItem(daysHoursMinutesItem);
                ressourceGroupDaysHoursMinutesItemMap.put(ressourceGroup, daysHoursMinutesItem);
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

    private void refreshEntities(final DaoFactoryBean baseDaoFactoryBean) {
        final EntityManager entityManager = baseDaoFactoryBean.getEntityManager();
        for(final PlannedProject plannedProject : baseDaoFactoryBean.getPlannedProjectDAO().loadAllEntities()){
            entityManager.refresh(plannedProject);
        }
        for(final PlanningUnitElement planningUnitElement : baseDaoFactoryBean.getPlanningUnitElementDAO().loadAllEntities()){
            entityManager.refresh(planningUnitElement);
        }
        for(final RessourceGroup ressourceGroup : baseDaoFactoryBean.getRessourceGroupDAO().loadAllEntities()){
            entityManager.refresh(ressourceGroup);
        }
    }
}
