package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projektinitialisierung.logic;

import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.rapidpm.transience.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.transience.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.transience.prj.projectmanagement.planning.PlanningUnitGroup;
import org.rapidpm.transience.prj.projectmanagement.planning.Projekt;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projektinitialisierung.ProjektinitialisierungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projektinitialisierung.components.MyTreeTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projektinitialisierung.datenmodell.*;

import java.util.ArrayList;
import java.util.List;

public class SaveButtonClickListener implements ClickListener {
    private FieldGroup fieldGroup;
    private ProjektinitialisierungScreen screen;
    private KnotenBlatt knotenBlatt;
    private Object itemId;
    private PlanningUnit foundPlanningUnit = null;

    public SaveButtonClickListener(FieldGroup fieldGroup, ProjektinitialisierungScreen screen, KnotenBlatt knotenBlatt, Object itemId) {
        this.fieldGroup = fieldGroup;
        this.screen = screen;
        this.knotenBlatt = knotenBlatt;
        this.itemId = itemId;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        try {
            fieldGroup.commit();
            screen.getTreeTable().setValue(null);    //null selection
            final Projekt projekt = screen.getProjektBean().getProjekt();
            final String planningUnitName = screen.getDataSource().getItem(itemId).getItemProperty("Aufgabe").getValue().toString();
            if (knotenBlatt.equals(KnotenBlatt.PLANNING_UNIT_GROUP)) {
                for (final PlanningUnitGroup planningUnitGroup : projekt.getPlanningUnitGroups()) {
                    if (planningUnitGroup.getPlanningUnitGroupName().equals(itemId)) {
                        planningUnitGroup.setPlanningUnitGroupName(planningUnitName);
                    }
                }
            } else {
                final ArrayList<PlanningUnitGroup> planningUnitGroups = screen.getProjektBean().getProjekt().getPlanningUnitGroups();

                for (final PlanningUnitGroup planningUnitGroup : planningUnitGroups) {
                    if (foundPlanningUnit == null) {
                        getPlanningUnit(planningUnitGroup.getPlanningUnitList(), itemId.toString());
                    }
                }
                if (knotenBlatt.equals(KnotenBlatt.PLANNING_UNIT_KNOTEN)) {
                    foundPlanningUnit.setPlanningUnitName(planningUnitName);
                } else {
                    foundPlanningUnit.setPlanningUnitName(planningUnitName);
                    for (final PlanningUnitElement planningUnitElement : foundPlanningUnit.getPlanningUnitElementList()) {
                        final String planningUnitElementRessourceGroupName = planningUnitElement.getRessourceGroup().getName();
                        final Property<?> planningUnitElementCellContent = screen.getDataSource().getItem(itemId).getItemProperty(planningUnitElementRessourceGroupName);
                        final String daysHoursMinutesString = planningUnitElementCellContent.getValue().toString();
                        final String[] daysHoursMinutes = daysHoursMinutesString.split(":");
                        planningUnitElement.setPlannedDays(Integer.parseInt(daysHoursMinutes[0]));
                        planningUnitElement.setPlannedHours(Integer.parseInt(daysHoursMinutes[1]));
                        planningUnitElement.setPlannedMinutes(Integer.parseInt(daysHoursMinutes[2]));
                    }
                }

//                for (final PlanningUnitGroup planningUnitGroup : projekt.getPlanningUnitGroups()) {
//                    for (final PlanningUnit planningUnit : planningUnitGroup.getPlanningUnitList()) {
//                        if (planningUnit.getPlanningUnitGroupName().equals(itemId)) {
//                            planningUnit.setPlanningUnitGroupName(planningUnitName);
//                            for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
//                                final String planningUnitElementRessourceGroupName = planningUnitElement.getRessourceGroup().getName();
//                                final Property<?> planningUnitElementCellContent = screen.getDataSource().getItem(itemId).getItemProperty(planningUnitElementRessourceGroupName);
//                                final String daysHoursMinutesString = planningUnitElementCellContent.getValue().toString();
//                                final String[] daysHoursMinutes = daysHoursMinutesString.split(":");
//                                planningUnitElement.setPlannedDays(Integer.parseInt(daysHoursMinutes[0]));
//                                planningUnitElement.setPlannedHours(Integer.parseInt(daysHoursMinutes[1]));
//                                planningUnitElement.setPlannedMinutes(Integer.parseInt(daysHoursMinutes[2]));
//                            }
//                        }
//                    }
//                }
            }
            final TreeTableContainerFiller filler = new TreeTableContainerFiller(screen.getProjektBean(), screen.getRessourceGroupsBean());
            filler.fill();
            screen.setDataSource(filler.getHierarchicalContainer());
            final MyTreeTable treeTable = new MyTreeTable();
            treeTable.setConnectedTable(screen.getUebersichtTable());
            //treeTable.setSizeFull();
            treeTable.setContainerDataSource(screen.getDataSource());
            treeTable.addListener(new TableItemClickListener(screen));
            treeTable.setColumnWidth("Aufgabe",250);
            screen.setTreeTable(treeTable);
            screen.getTable2layout().removeAllComponents();
            screen.getTable2layout().addComponent(screen.getTreeTable());
            //screen.getTable2layout().setSizeFull();

            final ProjektinitialisierungComputer computer = new ProjektinitialisierungComputer(screen);
            computer.compute();
            computer.setValuesInScreen();
            screen.getFormLayout().setVisible(false);
        } catch (CommitException e) {
            //tue nichts falls commit nicht erfolgreich war
        }
    }

    private void getPlanningUnit(List<PlanningUnit> planningUnits, String itemId) {
        for (PlanningUnit planningUnit : planningUnits) {
            if (planningUnit.getPlanningUnitName().equals(itemId)) {
                foundPlanningUnit = planningUnit;
            } else {
                if (planningUnit.getKindPlanningUnits() != null && !planningUnit.getKindPlanningUnits().isEmpty()) {
                    getPlanningUnit(planningUnit.getKindPlanningUnits(), itemId);
                }
            }
        }
    }

}
