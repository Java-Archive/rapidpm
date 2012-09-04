package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.rapidpm.webapp.vaadin.Projekt;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitElement;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.AufwandProjInitScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.datenmodell.*;

import java.util.List;

import static org.rapidpm.Constants.AUFGABE_SPALTE;

public class SaveButtonClickListener implements ClickListener {
    private FieldGroup fieldGroup;
    private AufwandProjInitScreen screen;
    private KnotenBlatt knotenBlatt;
    private Object itemId;
    private PlanningUnit foundPlanningUnit = null;

    public SaveButtonClickListener(final FieldGroup fieldGroup, final AufwandProjInitScreen screen, 
                                   final KnotenBlatt knotenBlatt, final Object itemId) {
        this.fieldGroup = fieldGroup;
        this.screen = screen;
        this.knotenBlatt = knotenBlatt;
        this.itemId = itemId;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        try {
            fieldGroup.commit();
            final Projekt projekt = screen.getProjektBean().getProjekt();
            final Item item = screen.getDataSource().getItem(itemId);
            final String planningUnitName = item.getItemProperty(AUFGABE_SPALTE).getValue().toString();
            if (knotenBlatt.equals(KnotenBlatt.PLANNING_UNIT_GROUP)) {
                for (final PlanningUnitGroup planningUnitGroup : projekt.getPlanningUnitGroups()) {
                    if (planningUnitGroup.getPlanningUnitGroupName().equals(itemId)) {
                        planningUnitGroup.setPlanningUnitGroupName(planningUnitName);
                    }
                }
            } else {
                final List<PlanningUnitGroup> planningUnitGroups = projekt.getPlanningUnitGroups();
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
                        final Property<?> planningUnitElementCellContent = item.getItemProperty(planningUnitElementRessourceGroupName);
                        final String daysHoursMinutesString = planningUnitElementCellContent.getValue().toString();
                        final String[] daysHoursMinutes = daysHoursMinutesString.split(":");
                        final int plannedDays = Integer.parseInt(daysHoursMinutes[0]);
                        final int plannedHours = Integer.parseInt(daysHoursMinutes[1]);
                        final int plannedMinutes = Integer.parseInt(daysHoursMinutes[2]);
                        planningUnitElement.setPlannedDays(plannedDays);
                        planningUnitElement.setPlannedHours(plannedHours);
                        planningUnitElement.setPlannedMinutes(plannedMinutes);
                    }
                }
            }
            final TreeTableFiller filler = new TreeTableFiller(screen,screen.getProjektBean(),
                    screen.getRessourceGroupsBean(),screen.getTreeTable(),screen.getDataSource());
            filler.fill();

            final OverviewTableFiller overviewTableFiller = new OverviewTableFiller(screen.getUebersichtTable(),
                    screen.getProjektBean(),screen.getRessourceGroupsBean());
            overviewTableFiller.fill();

            screen.fillFields();
            screen.getFormLayout().setVisible(false);
        } catch (CommitException e) {
            //tue nichts falls commit nicht erfolgreich war
        }
    }

    private void getPlanningUnit(List<PlanningUnit> planningUnits, String itemId) {
        for (final PlanningUnit planningUnit : planningUnits) {
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
