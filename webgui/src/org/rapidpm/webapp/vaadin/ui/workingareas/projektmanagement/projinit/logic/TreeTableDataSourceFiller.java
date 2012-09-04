package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.HierarchicalContainer;
import org.rapidpm.webapp.vaadin.ProjektBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitElement;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.rapidpm.Constants.*;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 31.08.12
 * Time: 16:29
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class TreeTableDataSourceFiller {

    private RessourceGroupsBean ressourceGroupsBean;
    private ProjektBean projektBean;
    private List<RessourceGroup> ressourceGroups;
    private final Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap = new HashMap<>();

    private HierarchicalContainer dataSource;

    public TreeTableDataSourceFiller(final RessourceGroupsBean rBean, final ProjektBean pBean, 
                                     final HierarchicalContainer dSource){
        ressourceGroupsBean = rBean;
        projektBean = pBean;
        dataSource = dSource;
        ressourceGroups = ressourceGroupsBean.getRessourceGroups();

        dataSource.removeAllItems();
            dataSource.addContainerProperty(AUFGABE_SPALTE, String.class, null);
            for (final RessourceGroup ressourceGroup : ressourceGroups) {
                dataSource.addContainerProperty(ressourceGroup.getName(), String.class, "");
            }



    }

    public void fill(){
        computePlanningUnitGroupsAndTotalsAbsolut();
    }

    private void computePlanningUnitGroupsAndTotalsAbsolut() {
        for (final PlanningUnitGroup planningUnitGroup : projektBean.getProjekt().getPlanningUnitGroups()) {
            final Item planningUnitGroupItem = dataSource.addItem(planningUnitGroup.getPlanningUnitGroupName());
            planningUnitGroupItem.getItemProperty(AUFGABE_SPALTE).setValue(planningUnitGroup.getPlanningUnitGroupName());
            if (planningUnitGroup.getPlanningUnitList() == null || planningUnitGroup.getPlanningUnitList().isEmpty()) {
                for (final RessourceGroup spalte : ressourceGroups) {
                    for(final PlanningUnitElement planningUnitElement : planningUnitGroup.getPlanningUnitElementList()){
                        if(planningUnitElement.getRessourceGroup().equals(spalte)){
                            planningUnitElement.setPlannedDays(0);
                            planningUnitElement.setPlannedHours(0);
                            planningUnitElement.setPlannedMinutes(0);
                            DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem(planningUnitElement);
                            final Property<?> itemProperty = planningUnitGroupItem.getItemProperty(spalte.getName());
                            itemProperty.setValue(daysHoursMinutesItem.toString());
                        }
                    }
                }
            } else {
                computePlanningUnits(planningUnitGroup.getPlanningUnitList(), planningUnitGroup.getPlanningUnitGroupName());
            }
        }
    }


    private void computePlanningUnits(List<PlanningUnit> planningUnits, String parent) {
        for (final PlanningUnit planningUnit : planningUnits) {
            final String planningUnitName = planningUnit.getPlanningUnitName();
            final Item planningUnitItem = dataSource.addItem(planningUnitName);
            planningUnitItem.getItemProperty(AUFGABE_SPALTE).setValue(planningUnitName);
            dataSource.setParent(planningUnitName, parent);
            if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
                for(final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()){
                    final DaysHoursMinutesItem item = new DaysHoursMinutesItem(planningUnitElement);
                    planningUnitItem.getItemProperty(planningUnitElement.getRessourceGroup().getName()).setValue(item.toString());
                }
                addiereZeileZurRessourceMap(planningUnit);
            } else {
                computePlanningUnits(planningUnit.getKindPlanningUnits(), planningUnitName);
            }
        }
        for (RessourceGroup spalte : ressourceGroups) {
            final String mapValue = ressourceGroupDaysHoursMinutesItemMap.get(spalte).toString();
            dataSource.getItem(parent).getItemProperty(spalte.getName()).setValue(mapValue);
        }
    }

    private void addiereZeileZurRessourceMap(PlanningUnit planningUnit) {
        for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
            if (!planningUnitElement.getRessourceGroup().getName().equals(AUFGABE_SPALTE)) {
                final RessourceGroup ressourceGroup1 = planningUnitElement.getRessourceGroup();
                final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem();
                final int plannedDays = planningUnitElement.getPlannedDays();               
                final int plannedHours = planningUnitElement.getPlannedHours();                
                final int plannedMinutes = planningUnitElement.getPlannedMinutes();
                daysHoursMinutesItem.setDays(plannedDays);
                daysHoursMinutesItem.setHours(plannedHours);
                daysHoursMinutesItem.setMinutes(plannedMinutes);
                if (ressourceGroupDaysHoursMinutesItemMap.containsKey(ressourceGroup1)) {
                    final Integer days = daysHoursMinutesItem.getDays();
                    final Integer hours = daysHoursMinutesItem.getHours();
                    final Integer minutes = daysHoursMinutesItem.getMinutes();
                    final DaysHoursMinutesItem daysHoursMinutesItemFromMap = ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup1);
                    daysHoursMinutesItem.setDays(days + daysHoursMinutesItemFromMap.getDays());
                    daysHoursMinutesItem.setHours(hours + daysHoursMinutesItemFromMap
                            .getHours());
                    daysHoursMinutesItem.setMinutes(minutes + daysHoursMinutesItemFromMap.getMinutes());
                }
                correctDaysHoursMinutesItem(daysHoursMinutesItem);
                ressourceGroupDaysHoursMinutesItemMap.put(ressourceGroup1, daysHoursMinutesItem);
            }
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
}
