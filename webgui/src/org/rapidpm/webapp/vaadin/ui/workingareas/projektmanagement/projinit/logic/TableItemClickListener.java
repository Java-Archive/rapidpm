package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component.Event;
import com.vaadin.ui.Field;
import com.vaadin.ui.GridLayout;
import org.apache.log4j.Logger;
import org.rapidpm.Constants;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesFieldValidator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.AufwandProjInitScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.datenmodell.KnotenBlattEnum;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

//TODO under construction!
public class TableItemClickListener implements ItemClickListener {

    private static final Logger logger = Logger.getLogger(TableItemClickListener.class);

    private JPAContainer<PlannedProject> plannedProjectJPAContainer = JPAContainerFactory.make(PlannedProject.class,
            Constants.PERSISTENCE_UNIT);
    private JPAContainer<PlanningUnit> planningUnitJPAContainer = JPAContainerFactory.make(PlanningUnit.class,
            Constants.PERSISTENCE_UNIT);

    private AufwandProjInitScreen screen;
    private KnotenBlattEnum knotenBlattEnum;

    private ResourceBundle messages;
    private TableItemClickListenerBean bean;

    private PlannedProject projekt;
    private List<PlanningUnit> planningUnits;


    public TableItemClickListener(final ResourceBundle bundle, final AufwandProjInitScreen screen) {
        this.messages = bundle;
        this.screen = screen;

        bean = EJBFactory.getEjbInstance(TableItemClickListenerBean.class);

        projekt = (PlannedProject) plannedProjectJPAContainer.getIdByIndex(0);

        planningUnits = new ArrayList<>();
        Collection<Object> objectCollection = planningUnitJPAContainer.getItemIds();
        for(Object obj : objectCollection){
            planningUnits.add(planningUnitJPAContainer.getItem(obj).getEntity());
        }
    }

    @Override
    public void itemClick(final ItemClickEvent event) {
        final GridLayout formUnterlayout = screen.getUpperFormLayout();
        final FieldGroup fieldGroup = new FieldGroup(event.getItem());
        for (final Object listener : screen.getSaveButton().getListeners(Event.class)) {
            if (listener instanceof ClickListener) {
                screen.getSaveButton().removeClickListener((ClickListener) listener);
            }

        }
        formUnterlayout.removeAllComponents();
        final Object itemId = event.getItemId();
        final HierarchicalContainer dataSource = screen.getDataSource();
        final String aufgabeFromBundle = messages.getString("aufgabe");
        final String planningUnitName = dataSource.getItem(itemId).getItemProperty(aufgabeFromBundle).getValue()
                .toString();
        //final PlanningUnit planningUnit = baseDaoFactoryBean.getPlanningUnitDAO().loadPlanningUnitByName
        //        (planningUnitName);
        //baseDaoFactoryBean.getEntityManager().refresh(planningUnit);
        PlanningUnit planningUnit = null;
        for(Object aPlanningUnit : planningUnitJPAContainer.getItemIds()){
            PlanningUnit planningUnit1 = (PlanningUnit) aPlanningUnit;
            if(planningUnitName.equals(planningUnit1.getPlanningUnitName())){
                planningUnit = planningUnit1;
            }
        }
        if (planningUnit != null) {
            final List<PlanningUnit> kindPlanningUnits = planningUnit.getKindPlanningUnits();
            if (kindPlanningUnits != null && (!kindPlanningUnits.isEmpty()) ) {
                knotenBlattEnum = KnotenBlattEnum.KNOTEN;
                buildRequiredFields(formUnterlayout, fieldGroup);
            } else {
                knotenBlattEnum = KnotenBlattEnum.BLATT;
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

        screen.getSaveButton().addClickListener(new SaveButtonClickListener(messages, fieldGroup, screen,
                knotenBlattEnum, itemId));
        screen.getFormLayout().setVisible(true);
    }

    private void buildRequiredFields(final GridLayout formUnterlayout, final FieldGroup fieldGroup) {
        for (final Object prop : fieldGroup.getUnboundPropertyIds()) {
            final String aufgabe = messages.getString("aufgabe");
            if (prop.equals(aufgabe))
                formUnterlayout.addComponent(fieldGroup.buildAndBind(prop));
        }
        for (final Object propertyId : fieldGroup.getBoundPropertyIds()) {
            fieldGroup.getField(propertyId).setRequired(true);
        }
    }



}
