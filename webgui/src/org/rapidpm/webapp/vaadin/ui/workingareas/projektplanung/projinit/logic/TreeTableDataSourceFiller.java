package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.logic;

import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ArrayList<RessourceGroup> ressourceGroups;
    private final Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap = new HashMap<>();

    private HierarchicalContainer dataSource;

    public TreeTableDataSourceFiller(RessourceGroupsBean rBean, ProjektBean pBean, HierarchicalContainer dSource){
        ressourceGroupsBean = rBean;
        projektBean = pBean;
        dataSource = dSource;
        ressourceGroups = ressourceGroupsBean.getRessourceGroups();

        dataSource.removeAllItems();
            dataSource.addContainerProperty("Aufgabe", String.class, null);
            for (final RessourceGroup ressourceGroup : ressourceGroups) {
                dataSource.addContainerProperty(ressourceGroup.getName(), String.class, "");
            }



    }

    public void fill(){
        computePlanningUnitGroupsAndTotalsAbsolut();
    }

    private void computePlanningUnitGroupsAndTotalsAbsolut() {
        for (final PlanningUnitGroup planningUnitGroup : projektBean.getProjekt().getPlanningUnitGroups()) {
            final Item planningUnitGroupItem = dataSource.addItem(planningUnitGroup.getPlanningUnitName());
            planningUnitGroupItem.getItemProperty("Aufgabe").setValue(planningUnitGroup.getPlanningUnitName());
            if (planningUnitGroup.getPlanningUnitList() == null || planningUnitGroup.getPlanningUnitList().isEmpty()) {
                for (final RessourceGroup spalte : ressourceGroups) {
                    for(final PlanningUnitElement planningUnitElement : planningUnitGroup.getPlanningUnitElementList()){
                        if(planningUnitElement.getRessourceGroup().equals(spalte)){
                            planningUnitElement.setPlannedDays(0);
                            planningUnitElement.setPlannedHours(0);
                            planningUnitElement.setPlannedMinutes(0);
                            DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem(planningUnitElement);
                            planningUnitGroupItem.getItemProperty(spalte.getName()).setValue(daysHoursMinutesItem.toString());
                        }
                    }
                }
            } else {
                computePlanningUnits(planningUnitGroup.getPlanningUnitList(), planningUnitGroup.getPlanningUnitName());
            }
        }
    }


    private void computePlanningUnits(List<PlanningUnit> planningUnits, String parent) {
        for (final PlanningUnit planningUnit : planningUnits) {
            final String planningUnitName = planningUnit.getPlanningUnitElementName();
            final Item planningUnitItem = dataSource.addItem(planningUnitName);
            planningUnitItem.getItemProperty("Aufgabe").setValue(planningUnitName);
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
            dataSource.getItem(parent).getItemProperty(spalte.getName()).setValue(ressourceGroupDaysHoursMinutesItemMap.get(spalte).toString());
        }
    }

    private void addiereZeileZurRessourceMap(PlanningUnit planningUnit) {
        for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
            if (!planningUnitElement.getRessourceGroup().getName().equals("Aufgabe")) {
                final RessourceGroup ressourceGroup1 = planningUnitElement.getRessourceGroup();
                final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem();
                daysHoursMinutesItem.setDays(planningUnitElement.getPlannedDays());
                daysHoursMinutesItem.setHours(planningUnitElement.getPlannedHours());
                daysHoursMinutesItem.setMinutes(planningUnitElement.getPlannedMinutes());
                if (ressourceGroupDaysHoursMinutesItemMap.containsKey(ressourceGroup1)) {
                    daysHoursMinutesItem.setDays(daysHoursMinutesItem.getDays() + ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup1).getDays());
                    daysHoursMinutesItem.setHours(daysHoursMinutesItem.getHours() + ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup1).getHours());
                    daysHoursMinutesItem.setMinutes(daysHoursMinutesItem.getMinutes() + ressourceGroupDaysHoursMinutesItemMap.get(ressourceGroup1).getMinutes());
                }
                correctDaysHoursMinutesItem(daysHoursMinutesItem);
                ressourceGroupDaysHoursMinutesItemMap.put(ressourceGroup1, daysHoursMinutesItem);
            }
        }
    }

    private void correctDaysHoursMinutesItem(DaysHoursMinutesItem item) {
        final int hours = item.getMinutes() / 60;
        if (hours > 0) {
            item.setHours(item.getHours() + hours);
            item.setMinutes(item.getMinutes() - (hours * 60));
        }
        final int days = item.getHours() / 24;
        if (days > 0) {
            item.setDays(item.getDays() + days);
            item.setHours(item.getHours() - (days * 24));
        }
    }
}
