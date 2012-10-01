package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.ressources;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.event.MouseEvents;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.MyFormLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic.PlanningCalculator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.rapidpm.Constants.*;
import static org.rapidpm.Constants.HOURS_DAY;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 30.08.12
 * Time: 11:24
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningRessourcesMyFormLayout extends MyFormLayout {

    private static final Logger logger = Logger.getLogger(PlanningRessourcesMyFormLayout.class);

    private Table tabelle = new Table();
    private Map<String, String> ressourceGroupStringMap;
    private List<PlanningUnitElement> planningUnitElements;

    public PlanningRessourcesMyFormLayout(final PlanningUnit planningUnit, final ProjektplanungScreen screen,
                                          final Panel screenPanel) {
        super(screen, screenPanel);
        planningUnitElements = planningUnit.getPlanningUnitElementList();
        buildTable();
        buildForm();
        for (final Object listener : screenPanel.getListeners(MouseEvents.ClickEvent.class)) {
            screenPanel.removeClickListener((MouseEvents.ClickListener) listener);
        }
    }

    public PlanningRessourcesMyFormLayout(final PlanningUnit planningUnit, final ProjektplanungScreen screen,
                                          final Panel screenPanel, boolean hasChildren) {
        super(screen, screenPanel);
        planningUnitElements = planningUnit.getPlanningUnitElementList();
        buildTable();
        ressourceGroupStringMap = new HashMap<>();
        for (final Object spalte2 : tabelle.getContainerPropertyIds()) {
            for(final Object zeile2 : tabelle.getItemIds()) {
                final String cellContent2 = tabelle.getItem(zeile2).getItemProperty(spalte2)
                        .getValue().toString();
                ressourceGroupStringMap.put(spalte2.toString(), cellContent2);
            }
        }
        buildForm();
        if (hasChildren) {
            for (final Object listener : screenPanel.getListeners(MouseEvents.ClickEvent.class)) {
                screenPanel.removeClickListener((MouseEvents.ClickListener) listener);
            }
        } else {
            cancelButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    buildTable();
                    buttonLayout.setVisible(false);
                    tabelle.setEditable(false);
                }
            });

            saveButton.addClickListener(new Button.ClickListener() {

                private final Pattern COMPILE = Pattern.compile(DAYSHOURSMINUTES_REGEX);
                private final Pattern SPLIT = Pattern.compile(":");

                @Override
                public void buttonClick(Button.ClickEvent event) {
                    try{


                        System.out.println("before: "+ressourceGroupStringMap);



                        tabelle.commit();
                        for (final Object spalte : tabelle.getContainerPropertyIds()) {
                            for(final Object zeile : tabelle.getItemIds()){
                                final String cellContent = tabelle.getItem(zeile).getItemProperty(spalte)
                                        .getValue().toString();
                                if(!COMPILE.matcher(cellContent).matches()){
                                    throw new FieldGroup.CommitException();
                                }
                                final List<PlanningUnitElement> planningUnitElementList = planningUnit.getPlanningUnitElementList();
                                for (final PlanningUnitElement planningUnitElement : planningUnitElementList) {
                                    final RessourceGroup ressourceGroup = planningUnitElement.getRessourceGroup();
                                    if (ressourceGroup.getName().equals(spalte.toString())) {
                                        final String[] daysHoursMinutes = SPLIT.split(cellContent);
                                        if( !(ressourceGroupStringMap.get(spalte).equals(cellContent)) ){
                                            final int plannedDays = Integer.parseInt(daysHoursMinutes[0]);
                                            final int plannedHours = Integer.parseInt(daysHoursMinutes[1]);
                                            final int plannedMinutes = Integer.parseInt(daysHoursMinutes[2]);
                                            planningUnitElement.setPlannedDays(plannedDays);
                                            planningUnitElement.setPlannedHours(plannedHours);
                                            planningUnitElement.setPlannedMinutes(plannedMinutes);
                                            final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem();
                                            final String[] cellContentBeforeSave = SPLIT.split
                                                    (ressourceGroupStringMap.get(ressourceGroup.getName()));
                                            final int plannedDaysBefore = Integer.parseInt(cellContentBeforeSave[0]);
                                            final int plannedHoursBefore = Integer.parseInt(cellContentBeforeSave[1]);
                                            final int plannedMinutesBefore = Integer.parseInt(cellContentBeforeSave[2]);
                                            if(plannedDaysBefore == plannedDays){
                                                daysHoursMinutesItem.setDays(0);
                                            } else {
                                                daysHoursMinutesItem.setDays(plannedDays);
                                            }
                                            if(plannedHoursBefore == plannedHours){
                                                daysHoursMinutesItem.setHours(0);
                                            } else {
                                                daysHoursMinutesItem.setHours(plannedHours);
                                            }if(plannedMinutesBefore == plannedMinutes){
                                                daysHoursMinutesItem.setMinutes(0);
                                            } else {
                                                daysHoursMinutesItem.setMinutes(plannedMinutes);
                                            }
                                            correctDaysHoursMinutesItem(daysHoursMinutesItem);
                                            saveTimesRecursively(daysHoursMinutesItem, planningUnit.getParent(), spalte);
                                        }

                                    }
                                }
                            }
                        }
                        final PlanningCalculator calculator = new PlanningCalculator(screen.getMessagesBundle());
                        calculator.calculate();
                        tabelle.setEditable(false);
                        buttonLayout.setVisible(false);
                        screen.getUi().setWorkingArea(new ProjektplanungScreen(screen.getUi()));
                    }catch (CommitException e){
                        logger.info(COMMIT_EXCEPTION_MESSAGE);
                        Notification.show("Eingabe nach Schema [d]d:hh:mm");
                    }catch(Exception e){
                        logger.warn("Exception",e);
                    }
                }
            });
        }
    }

    private void saveTimesRecursively(DaysHoursMinutesItem daysHoursMinutesItem, PlanningUnit planningUnit, Object propertyId) {
        final DaysHoursMinutesItem newItem = new DaysHoursMinutesItem();
        if(planningUnit != null){
            for(final PlanningUnit kindPlanningUnit : planningUnit.getKindPlanningUnits()){
                for (final PlanningUnitElement planningUnitElement : kindPlanningUnit.getPlanningUnitElementList()) {
                    final RessourceGroup ressourceGroup = planningUnitElement.getRessourceGroup();
                    if (ressourceGroup.getName().equals(propertyId.toString())) {
                        final int plannedDays =  planningUnitElement.getPlannedDays() + daysHoursMinutesItem.getDays();
                        final int plannedHours = planningUnitElement.getPlannedHours() + daysHoursMinutesItem.getHours();
                        final int plannedMinutes = planningUnitElement.getPlannedHours() + daysHoursMinutesItem.getMinutes();
                        newItem.setDays(newItem.getDays() + plannedDays);
                        newItem.setHours(newItem.getHours() + plannedHours);
                        newItem.setMinutes(newItem.getMinutes() + plannedMinutes);
                        correctDaysHoursMinutesItem(newItem);
                    }
                    planningUnitElement.setPlannedDays(newItem.getDays());
                    planningUnitElement.setPlannedHours(newItem.getHours());
                    planningUnitElement.setPlannedMinutes(newItem.getMinutes());
                }
                saveTimesRecursively(newItem, planningUnit.getParent(), propertyId);
            }

        }

    }

    private void buildTable() {
        tabelle.removeAllItems();
        tabelle.setPageLength(2);
        tabelle.setColumnCollapsingAllowed(true);
        final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem();
        final String[] cells = new String[planningUnitElements.size()];
        Integer counter = 0;
        for (final PlanningUnitElement element : planningUnitElements) {
            final String spaltenName = element.getRessourceGroup().getName();
            tabelle.addContainerProperty(spaltenName, String.class, null);
            daysHoursMinutesItem.setDays(element.getPlannedDays());
            daysHoursMinutesItem.setHours(element.getPlannedHours());
            daysHoursMinutesItem.setMinutes(element.getPlannedMinutes());
            cells[counter] = daysHoursMinutesItem.toString();
            counter++;
        }
        try{
            final Object itemId = tabelle.addItem(cells, null);
            if(itemId == null){
                throw new NullPointerException();
            }
        } catch(NullPointerException e){
            logger.warn("tabelle konnte nicht erstellt werden");
        }
    }

    @Override
    protected void buildForm() {
        componentsLayout.addComponent(tabelle);
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
}
