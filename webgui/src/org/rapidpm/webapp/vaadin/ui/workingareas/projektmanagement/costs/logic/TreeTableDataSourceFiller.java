package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.HierarchicalContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitElement;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.Projekt;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.OldRessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.OldRessourceGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.HOURS_DAY;
import static org.rapidpm.Constants.STD_ANTEILE;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 31.08.12
 * Time: 16:29
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class TreeTableDataSourceFiller {
    private OldRessourceGroupsBean oldRessourceGroupsBean;
    private ProjektBean projektBean;
    private List<OldRessourceGroup> oldRessourceGroups;
    private final Map<OldRessourceGroup, Double> ressourceGroupsCostsMap = new HashMap<>();
    private ResourceBundle messages;

    private HierarchicalContainer dataSource;

    public TreeTableDataSourceFiller(final ResourceBundle bundle, final OldRessourceGroupsBean rBeanOld, final ProjektBean pBean,
                                     final HierarchicalContainer dSource) {
        messages = bundle;
        oldRessourceGroupsBean = rBeanOld;
        projektBean = pBean;
        dataSource = dSource;
        oldRessourceGroups = oldRessourceGroupsBean.getOldRessourceGroups();
        dataSource.removeAllItems();
        dataSource.addContainerProperty(messages.getString("aufgabe"), String.class, null);
        for (final OldRessourceGroup oldRessourceGroup : oldRessourceGroups) {
            dataSource.addContainerProperty(oldRessourceGroup.getName(), Double.class, "");
        }


    }

    public void fill() {
        computePlanningUnitGroupsAndTotalsAbsolut();
    }

    private void computePlanningUnitGroupsAndTotalsAbsolut() {
        final Projekt projekt = projektBean.getProjekt();
        final List<PlanningUnitGroup> planningUnitGroups = projekt.getPlanningUnitGroups();
        for (final PlanningUnitGroup planningUnitGroup : planningUnitGroups) {
            final String planningUnitGroupName = planningUnitGroup.getPlanningUnitGroupName();
            final Item planningUnitGroupItem = dataSource.addItem(planningUnitGroupName);
            planningUnitGroupItem.getItemProperty(messages.getString("aufgabe")).setValue(planningUnitGroupName);
            final List<PlanningUnit> planningUnitList = planningUnitGroup.getPlanningUnitList();
            if (planningUnitList == null || planningUnitList.isEmpty()) {
                for (final OldRessourceGroup spalte : oldRessourceGroups) {
                    final List<PlanningUnitElement> planningUnitElementList = planningUnitGroup.getPlanningUnitElementList();
                    for (final PlanningUnitElement planningUnitElement : planningUnitElementList) {
                        final OldRessourceGroup oldRessourceGroup = planningUnitElement.getOldRessourceGroup();
                        if (oldRessourceGroup.equals(spalte)) {
                            planningUnitGroupItem.getItemProperty(spalte.getName()).setValue(0.0);
                        }
                    }
                }
            } else {
                computePlanningUnits(planningUnitList, planningUnitGroupName);
            }
        }
    }


    private void computePlanningUnits(List<PlanningUnit> planningUnits, String parent) {
        for (final PlanningUnit planningUnit : planningUnits) {
            final String planningUnitName = planningUnit.getPlanningUnitName();
            final Item planningUnitItem = dataSource.addItem(planningUnitName);
            planningUnitItem.getItemProperty(messages.getString("aufgabe")).setValue(planningUnitName);
            dataSource.setParent(planningUnitName, parent);
            if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
                for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
                    final Double costs = getCosts(planningUnitElement);
                    final OldRessourceGroup oldRessourceGroup = planningUnitElement.getOldRessourceGroup();
                    planningUnitItem.getItemProperty(oldRessourceGroup.getName()).setValue(costs);
                }
                addiereZeileZurRessourceMap(planningUnit);
            } else {
                computePlanningUnits(planningUnit.getKindPlanningUnits(), planningUnitName);
            }
        }
        for (final OldRessourceGroup spalte : oldRessourceGroups) {
            final Item dataSourceItem = dataSource.getItem(parent);
            final Double newValue = ressourceGroupsCostsMap.get(spalte);
            final Property<?> itemProperty = dataSourceItem.getItemProperty(spalte.getName());
            itemProperty.setValue(newValue);
        }
    }

    private void addiereZeileZurRessourceMap(final PlanningUnit planningUnit) {
        final List<PlanningUnitElement> planningUnitElementList = planningUnit.getPlanningUnitElementList();
        for (final PlanningUnitElement planningUnitElement : planningUnitElementList) {
            final OldRessourceGroup oldRessourceGroup = planningUnitElement.getOldRessourceGroup();
            if (!oldRessourceGroup.getName().equals(messages.getString("aufgabe"))) {
                Double costs = getCosts(planningUnitElement);
                if (ressourceGroupsCostsMap.containsKey(oldRessourceGroup)) {
                    costs += ressourceGroupsCostsMap.get(oldRessourceGroup);
                }
                ressourceGroupsCostsMap.put(oldRessourceGroup, costs);
            }
        }
    }

    private Double getCosts(final PlanningUnitElement planningUnitElement) {
        final int hoursFromDays = HOURS_DAY * planningUnitElement.getPlannedDays();
        final int hours = planningUnitElement.getPlannedHours();
        final double hoursFromMinutes = STD_ANTEILE * planningUnitElement.getPlannedMinutes();

        final Double totalHours = hoursFromDays + hours + hoursFromMinutes;

        final OldRessourceGroup oldRessourceGroup = planningUnitElement.getOldRessourceGroup();
        final Double externalEurosPerHour = oldRessourceGroup.getExternalEurosPerHour();

        return totalHours * externalEurosPerHour;
    }

}
