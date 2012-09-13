package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.event.MouseEvents;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic.PlanningCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.Projekt;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.OldRessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.OldRessourceGroupsBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import static org.rapidpm.Constants.*;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 30.08.12
 * Time: 11:24
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningRessourcesMyFormLayout extends MyFormLayout {

    private static final Logger logger = Logger.getLogger(PlanningRessourcesMyFormLayout.class);

    private List<TextField> ressourceGroupFields = new ArrayList<>();

    public PlanningRessourcesMyFormLayout(final PlanningUnitGroup planningUnitGroup, final ProjektplanungScreen screen,
                                          final Panel screenPanel) {
        super(planningUnitGroup.getIssueBase(), screen, screenPanel);
        final ProjektBean projektBean = screen.getProjektBean();
        final Projekt projekt = projektBean.getProjekt();
        final List<OldRessourceGroup> oldRessourceGroupList = projekt.getOldRessourceGroups();
        for (final OldRessourceGroup oldRessourceGroup : oldRessourceGroupList) {
            buildField(oldRessourceGroup, planningUnitGroup);
        }
        buildForm();
        for (final Object listener : screenPanel.getListeners(MouseEvents.ClickEvent.class)) {
            screenPanel.removeListener((MouseEvents.ClickListener) listener);
        }
    }

    public PlanningRessourcesMyFormLayout(final PlanningUnit planningUnit, final ProjektplanungScreen screen,
                                          final Panel screenPanel, boolean hasChildren) {
        super(planningUnit.getIssueBase(), screen, screenPanel);
        final ProjektBean projektBean = screen.getProjektBean();
        final Projekt projekt = projektBean.getProjekt();
        final List<OldRessourceGroup> oldRessourceGroupArrayList = projekt.getOldRessourceGroups();
        for (final OldRessourceGroup oldRessourceGroup : oldRessourceGroupArrayList) {
            buildField(oldRessourceGroup, planningUnit);
        }
        buildForm();
        if (hasChildren) {
            for (final Object listener : screenPanel.getListeners(MouseEvents.ClickEvent.class)) {
                screenPanel.removeListener((MouseEvents.ClickListener) listener);
            }
        } else {
            cancelButton.addListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    for (final TextField textField : ressourceGroupFields) {
                        for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
                            final OldRessourceGroup oldRessourceGroup = planningUnitElement.getOldRessourceGroup();
                            if (oldRessourceGroup.getName().equals(textField.getCaption())) {
                                final DaysHoursMinutesItem item = new DaysHoursMinutesItem();
                                item.setDays(planningUnitElement.getPlannedDays());
                                item.setHours(planningUnitElement.getPlannedHours());
                                item.setMinutes(planningUnitElement.getPlannedMinutes());
                                textField.setValue(item.toString());
                            }
                        }
                    }
                    final Iterator<Component> componentIterator = componentsLayout.getComponentIterator();
                    while (componentIterator.hasNext()) {
                        final Component component = componentIterator.next();
                        if (component instanceof Field) {
                            component.setReadOnly(true);
                        }
                    }
                    buttonLayout.setVisible(false);
                }
            });

            saveButton.addListener(new Button.ClickListener() {

                private final Pattern COMPILE = Pattern.compile(":");

                @Override
                public void buttonClick(Button.ClickEvent event) {
                    try{
                        for (final TextField textField : ressourceGroupFields) {
                            if(!textField.isValid()){
                                throw new FieldGroup.CommitException();
                            }
                            final List<PlanningUnitElement> planningUnitElementList = planningUnit.getPlanningUnitElementList();
                            for (final PlanningUnitElement planningUnitElement : planningUnitElementList) {
                                final OldRessourceGroup oldRessourceGroup = planningUnitElement.getOldRessourceGroup();
                                if (oldRessourceGroup.getName().equals(textField.getCaption())) {
                                    final String[] daysHoursMinutes = COMPILE.split(textField.getValue());
                                    planningUnitElement.setPlannedDays(Integer.parseInt(daysHoursMinutes[0]));
                                    planningUnitElement.setPlannedHours(Integer.parseInt(daysHoursMinutes[1]));
                                    planningUnitElement.setPlannedMinutes(Integer.parseInt(daysHoursMinutes[2]));
                                }
                            }
                        }
                        final OldRessourceGroupsBean oldRessourceGroupsBean = screen.getOldRessourceGroupsBean();
                        final PlanningCalculator calculator = new PlanningCalculator(screen.getMessagesBundle(),
                                projektBean, oldRessourceGroupsBean);
                        calculator.calculate();

                        final Iterator<Component> componentIterator = componentsLayout.getComponentIterator();
                        while (componentIterator.hasNext()) {
                            final Component component = componentIterator.next();
                            if (component instanceof Field) {
                                component.setReadOnly(true);
                            }
                        }
                        buttonLayout.setVisible(false);
                    }catch (CommitException e){
                        logger.info(COMMIT_EXCEPTION_MESSAGE);
                    }catch(Exception e){
                        logger.warn("Exception",e);
                    }
                }
            });
        }


    }


    private void buildField(OldRessourceGroup oldRessourceGroup, PlanningUnitGroup planningUnitGroup) {
        final TextField field = new TextField(oldRessourceGroup.getName());
        final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem();

        PlanningUnitElement element = new PlanningUnitElement();
        for (final PlanningUnitElement planningUnitElement : planningUnitGroup.getPlanningUnitElementList()) {
            final String elementRessourceGroupName = planningUnitElement.getOldRessourceGroup().getName();
            if (elementRessourceGroupName.equals(oldRessourceGroup.getName())) {
                element = planningUnitElement;
            }
        }
        final int index = planningUnitGroup.getPlanningUnitElementList().indexOf(element);
        final PlanningUnitElement planningUnitElement = planningUnitGroup.getPlanningUnitElementList().get(index);
        daysHoursMinutesItem.setDays(planningUnitElement.getPlannedDays());
        daysHoursMinutesItem.setHours(planningUnitElement.getPlannedHours());
        daysHoursMinutesItem.setMinutes(planningUnitElement.getPlannedMinutes());
        field.setValue(daysHoursMinutesItem.toString());
        field.setReadOnly(true);
        field.addValidator(new DaysHoursMinutesFieldValidator());
        ressourceGroupFields.add(field);
    }

    private void buildField(OldRessourceGroup oldRessourceGroup, PlanningUnit planningUnit) {
        final TextField field = new TextField(oldRessourceGroup.getName());
        final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem();
        PlanningUnitElement element = new PlanningUnitElement();
        for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
            final String elementRessourceGroupName = planningUnitElement.getOldRessourceGroup().getName();
            if (elementRessourceGroupName.equals(oldRessourceGroup.getName())) {
                element = planningUnitElement;
            }
        }
        final int index = planningUnit.getPlanningUnitElementList().indexOf(element);
        final PlanningUnitElement planningUnitElement = planningUnit.getPlanningUnitElementList().get(index);
        daysHoursMinutesItem.setDays(planningUnitElement.getPlannedDays());
        daysHoursMinutesItem.setHours(planningUnitElement.getPlannedHours());
        daysHoursMinutesItem.setMinutes(planningUnitElement.getPlannedMinutes());
        field.setValue(daysHoursMinutesItem.toString());
        field.setReadOnly(true);
        field.addValidator(new DaysHoursMinutesFieldValidator());
        ressourceGroupFields.add(field);
    }

    @Override
    protected void buildForm() {
        for (final TextField field : ressourceGroupFields) {
            componentsLayout.addComponent(field);
        }
    }
}
