package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitElement;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.Projekt;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.AufwandProjInitScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.datenmodell.KnotenBlattEnum;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupsBean;

import java.util.List;
import java.util.regex.Pattern;

import static org.rapidpm.Constants.AUFGABE_SPALTE;

public class SaveButtonClickListener implements ClickListener {
    private static final Pattern SPLITT_PATTERN = Pattern.compile(":");
    private FieldGroup fieldGroup;
    private AufwandProjInitScreen screen;
    private KnotenBlattEnum knotenBlattEnum;
    private Object itemId;
    private PlanningUnit foundPlanningUnit = null;

    public SaveButtonClickListener(final FieldGroup fieldGroup, final AufwandProjInitScreen screen,
                                   final KnotenBlattEnum knotenBlattEnum, final Object itemId) {
        this.fieldGroup = fieldGroup;
        this.screen = screen;
        this.knotenBlattEnum = knotenBlattEnum;
        this.itemId = itemId;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        try {
            fieldGroup.commit();
            final ProjektBean projektBean = screen.getProjektBean();
            final Projekt projekt = projektBean.getProjekt();
            final Item item = screen.getDataSource().getItem(itemId);
            final String planningUnitName = item.getItemProperty(AUFGABE_SPALTE).getValue().toString();
            if (knotenBlattEnum.equals(KnotenBlattEnum.PLANNING_UNIT_GROUP)) {
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
                    } else {
                        //TODO logger
                    }
                }
                if (knotenBlattEnum.equals(KnotenBlattEnum.PLANNING_UNIT_KNOTEN)) {
                    foundPlanningUnit.setPlanningUnitName(planningUnitName);
                } else {
                    foundPlanningUnit.setPlanningUnitName(planningUnitName);
                    for (final PlanningUnitElement planningUnitElement : foundPlanningUnit.getPlanningUnitElementList()) {
                        final String planningUnitElementRessourceGroupName = planningUnitElement.getRessourceGroup().getName();
                        final Property<?> planningUnitElementCellContent = item.getItemProperty(planningUnitElementRessourceGroupName);
                        final String daysHoursMinutesString = planningUnitElementCellContent.getValue().toString();
                        final String[] daysHoursMinutes = SPLITT_PATTERN.split(daysHoursMinutesString);
                        final int plannedDays = Integer.parseInt(daysHoursMinutes[0]);
                        final int plannedHours = Integer.parseInt(daysHoursMinutes[1]);
                        final int plannedMinutes = Integer.parseInt(daysHoursMinutes[2]);
                        planningUnitElement.setPlannedDays(plannedDays);
                        planningUnitElement.setPlannedHours(plannedHours);
                        planningUnitElement.setPlannedMinutes(plannedMinutes);
                    }
                }
            }
            final RessourceGroupsBean ressourceGroupsBean = screen.getRessourceGroupsBean();
            final TreeTableFiller filler = new TreeTableFiller(screen, projektBean,
                    ressourceGroupsBean, screen.getTreeTable(), screen.getDataSource());
            filler.fill();

            final OverviewTableFiller overviewTableFiller = new OverviewTableFiller(screen.getUebersichtTable(),
                    projektBean, ressourceGroupsBean);
            overviewTableFiller.fill();

            screen.fillFields();
            screen.getFormLayout().setVisible(false);
        } catch (CommitException e) {
            //tue nichts falls commit nicht erfolgreich war
            //TODO logger !!
        }
    }

    private void getPlanningUnit(final List<PlanningUnit> planningUnits, final String itemId) {
        for (final PlanningUnit planningUnit : planningUnits) {
            if (planningUnit.getPlanningUnitName().equals(itemId)) {
                foundPlanningUnit = planningUnit;
            } else {
                final List<PlanningUnit> kindPlanningUnits = planningUnit.getKindPlanningUnits();
                if (kindPlanningUnits != null && !kindPlanningUnits.isEmpty()) {
                    getPlanningUnit(kindPlanningUnits, itemId);
                }
            }
        }
    }

}
