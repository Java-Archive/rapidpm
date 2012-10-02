package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.ressources;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.event.MouseEvents;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import org.apache.log4j.Logger;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.MyFormLayout;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.regex.Pattern;

import static org.rapidpm.Constants.COMMIT_EXCEPTION_MESSAGE;
import static org.rapidpm.Constants.DAYSHOURSMINUTES_REGEX;

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

    private List<PlanningUnitElement> planningUnitElements;
    private PlanningRessourcesMyFormLayoutBean bean;
    private DaoFactoryBean baseDaoFactoryBean;

    public PlanningRessourcesMyFormLayout(final PlanningUnit thePlanningUnit, final ProjektplanungScreen screen,
                                          final Panel screenPanel, boolean hasChildren) {
        super(screen, screenPanel);
        bean = EJBFactory.getEjbInstance(PlanningRessourcesMyFormLayoutBean.class);
        baseDaoFactoryBean = bean.getDaoFactoryBean();
        refreshEntities(baseDaoFactoryBean);

        final PlanningUnit planningUnit = baseDaoFactoryBean.getPlanningUnitDAO().loadPlanningUnitByName
                (thePlanningUnit.getPlanningUnitName());
        planningUnitElements = planningUnit.getPlanningUnitElementList();
        buildTable();
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
                    try {

                        tabelle.commit();
                        for (final Object spalte : tabelle.getContainerPropertyIds()) {
                            for (final Object zeile : tabelle.getItemIds()) {
                                final String cellContent = tabelle.getItem(zeile).getItemProperty(spalte)
                                        .getValue().toString();
                                if (!COMPILE.matcher(cellContent).matches()) {
                                    throw new FieldGroup.CommitException();
                                }
                                final List<PlanningUnitElement> planningUnitElementList = planningUnit.getPlanningUnitElementList();
                                for (final PlanningUnitElement planningUnitElement : planningUnitElementList) {
                                    final RessourceGroup ressourceGroup = planningUnitElement.getRessourceGroup();
                                    if (ressourceGroup.getName().equals(spalte.toString())) {
                                        final String[] daysHoursMinutes = SPLIT.split(cellContent);
                                        planningUnitElement.setPlannedDays(Integer.parseInt(daysHoursMinutes[0]));
                                        planningUnitElement.setPlannedHours(Integer.parseInt(daysHoursMinutes[1]));
                                        planningUnitElement.setPlannedMinutes(Integer.parseInt(daysHoursMinutes[2]));
                                        baseDaoFactoryBean.saveOrUpdate(planningUnitElement);
                                    }
                                }
                            }
                        }
                        screen.getUi().setWorkingArea(new ProjektplanungScreen(screen.getUi()));
                    } catch (CommitException e) {
                        logger.info(COMMIT_EXCEPTION_MESSAGE);
                        Notification.show(messages.getString("planning_ressourcespattern"));
                    } catch (Exception e) {
                        logger.warn("Exception", e);
                    }
                }
            });
        }
    }

    private void buildTable() {
        refreshEntities(baseDaoFactoryBean);

        final List<RessourceGroup> ressourceGroups = baseDaoFactoryBean.getRessourceGroupDAO().loadAllEntities();
        final String[] spaltenNamen = new String[ressourceGroups.size()];
        Integer index = 0;
        for(final RessourceGroup ressourceGroup : ressourceGroups){
            spaltenNamen[index] = ressourceGroup.getName();
            index++;
        }

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
        try {
            final Object itemId = tabelle.addItem(cells, null);
            tabelle.setVisibleColumns(spaltenNamen);
            if (itemId == null) {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            logger.warn("tabelle konnte nicht erstellt werden");
        }
    }

    @Override
    protected void buildForm() {
        componentsLayout.addComponent(tabelle);
    }

    private void refreshEntities(DaoFactoryBean baseDaoFactoryBean) {
        final EntityManager entityManager = baseDaoFactoryBean.getEntityManager();
        for(final PlannedProject plannedProject : baseDaoFactoryBean.getPlannedProjectDAO().loadAllEntities()){
            entityManager.refresh(plannedProject);
        }
        for(final PlanningUnitElement planningUnitElement : baseDaoFactoryBean.getPlanningUnitElementDAO().loadAllEntities()){
            entityManager.refresh(planningUnitElement);
        }
        for(final PlanningUnit planningUnit : baseDaoFactoryBean.getPlanningUnitDAO().loadAllEntities()){
            entityManager.refresh(planningUnit);
        }
        for(final RessourceGroup ressourceGroup : baseDaoFactoryBean.getRessourceGroupDAO().loadAllEntities()){
            entityManager.refresh(ressourceGroup);
        }
    }

}
