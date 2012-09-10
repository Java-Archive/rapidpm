package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component.Event;
import com.vaadin.ui.Field;
import com.vaadin.ui.GridLayout;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesFieldValidator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.Projekt;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.AufwandProjInitScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.datenmodell.KnotenBlattEnum;

import java.util.List;
import java.util.ResourceBundle;

public class TableItemClickListener implements ItemClickListener {

    private static final Logger logger = Logger.getLogger(TableItemClickListener.class);

    private AufwandProjInitScreen screen;
    private KnotenBlattEnum knotenBlattEnum;

    private PlanningUnit foundPlanningUnit = null;
    private ResourceBundle messages;

    public TableItemClickListener(final ResourceBundle bundle, final AufwandProjInitScreen screen) {
        this.messages = bundle;
        this.screen = screen;
    }

    @Override
    public void itemClick(final ItemClickEvent event) {
        final GridLayout formUnterlayout = screen.getUpperFormLayout();
        final FieldGroup fieldGroup = new FieldGroup(event.getItem());
        for (final Object listener : screen.getSaveButton().getListeners(Event.class)) {
            if (listener instanceof ClickListener) {
                screen.getSaveButton().removeListener((ClickListener) listener);
            }

        }
        formUnterlayout.removeAllComponents();
        final Object itemId = event.getItemId();
        final HierarchicalContainer dataSource = screen.getDataSource();
        final String aufgabeFromBundle = messages.getString("aufgabe");
        final String aufgabe = dataSource.getItem(itemId).getItemProperty(aufgabeFromBundle).getValue()
                .toString();
        final ProjektBean projektBean = screen.getProjektBean();
        final Projekt projekt = projektBean.getProjekt();
        final List<String> planningUnitGroupsNames = projekt.getPlanningUnitGroupsNames();

        foundPlanningUnit = null;
        if (planningUnitGroupsNames.contains(aufgabe)) {
            knotenBlattEnum = KnotenBlattEnum.PLANNING_UNIT_GROUP;
            buildRequiredFields(formUnterlayout, fieldGroup);
        } else {
            final List<PlanningUnitGroup> planningUnitGroups = projekt.getPlanningUnitGroups();
            //PlanningUnit planningUnit = null;
            for (final PlanningUnitGroup planningUnitGroup : planningUnitGroups) {
                if (foundPlanningUnit == null) {
                    getPlanningUnit(planningUnitGroup.getPlanningUnitList(), itemId.toString());
                }
            }
            if (foundPlanningUnit != null) {
                final List<PlanningUnit> kindPlanningUnits = foundPlanningUnit.getKindPlanningUnits();
                if (kindPlanningUnits != null && !kindPlanningUnits.isEmpty()) {
                    knotenBlattEnum = KnotenBlattEnum.PLANNING_UNIT_KNOTEN;
                    buildRequiredFields(formUnterlayout, fieldGroup);
                } else {
                    knotenBlattEnum = KnotenBlattEnum.PLANNING_UNIT_BLATT;
                    for (final Object prop : fieldGroup.getUnboundPropertyIds()) {
                        formUnterlayout.addComponent(fieldGroup.buildAndBind(prop));
                    }
                    for (final Object propertyId : fieldGroup.getBoundPropertyIds()) {
                        final Field<?> field = fieldGroup.getField(propertyId);
                        if (!propertyId.equals(aufgabeFromBundle)) {
                            field.addValidator(new DaysHoursMinutesFieldValidator());
                        }
                        field.setRequired(true);
                    }
                }
            } else {
                logger.warn("PlanningUnit nicht gefunden");
            }
        }

        screen.getSaveButton().addListener(new SaveButtonClickListener(messages, fieldGroup, screen, knotenBlattEnum,
                itemId));
        screen.getFormLayout().setVisible(true);
    }

    private void buildRequiredFields(GridLayout formUnterlayout, FieldGroup fieldGroup) {
        for (final Object prop : fieldGroup.getUnboundPropertyIds()) {
            final String aufgabe = messages.getString("aufgabe");
            if (prop.equals(aufgabe))
                formUnterlayout.addComponent(fieldGroup.buildAndBind(prop));
        }
        for (final Object propertyId : fieldGroup.getBoundPropertyIds()) {
            fieldGroup.getField(propertyId).setRequired(true);
        }
    }

    private void getPlanningUnit(final List<PlanningUnit> planningUnits, final String itemId) {
        for (final PlanningUnit planningUnit : planningUnits) {
            if (planningUnit.getPlanningUnitName().equals(itemId)) {
                foundPlanningUnit = planningUnit;
            } else {
                final List<PlanningUnit> kindPlanningUnits = planningUnit.getKindPlanningUnits();
                if ((kindPlanningUnits != null) && !kindPlanningUnits.isEmpty()) {
                    getPlanningUnit(kindPlanningUnits, itemId);
                }
            }
        }
    }


}
