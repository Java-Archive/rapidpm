package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all;

import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;

import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 25.08.12
 * Time: 18:50
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */

public class PlanningUnitsTreePanelLayout extends HorizontalLayout {

    private static final Logger logger = Logger.getLogger(PlanningUnitsTreePanelLayout.class);

    private VerticalLayout leftLayout = new VerticalLayout();

    private HorizontalLayout buttonLayout = new HorizontalLayout();
    private PlannedProject projekt;

    private Button addButton = new Button("+");
    private Button deleteButton = new Button("-");
    private ResourceBundle messages;
    private PlanningUnitsTreePanelLayoutBean bean;
    private DaoFactoryBean baseDaoFactoryBean;

    private ProjektplanungScreen screen;

    public PlanningUnitsTreePanelLayout(final PlannedProject projekt, final ProjektplanungScreen screen) {
        this.screen = screen;
        this.projekt = projekt;
        bean = EJBFactory.getEjbInstance(PlanningUnitsTreePanelLayoutBean.class);
        baseDaoFactoryBean = bean.getDaoFactoryBean();

        messages = screen.getMessagesBundle();
        createDeleteButton();
        createAddButton();
        buildForm();
    }

    private void createAddButton() {
        addButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                final AddWindow window = new AddWindow(screen.getUi(),screen);
                window.show();
            }
        });
    }

    private void createDeleteButton() {
        deleteButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    final PlanningUnit planningUnit = (PlanningUnit) screen.getPlanningUnitsTree().getValue();
                    final PlanningUnit managedPlanningUnit = baseDaoFactoryBean.getPlanningUnitDAO().findByID
                            (planningUnit.getId());
                    if(managedPlanningUnit == null){
                        throw new PlatzhalterException();
                    }
                    if(managedPlanningUnit.getKindPlanningUnits() != null && !managedPlanningUnit.getKindPlanningUnits
                            ().isEmpty())
                        throw new Exception();
                    final PlanningUnit parentPlanningUnit = managedPlanningUnit.getParent();
                    projekt = baseDaoFactoryBean.getPlannedProjectDAO().findByID(projekt.getId());

                    if(parentPlanningUnit == null){
                        projekt.getPlanningUnits().remove(managedPlanningUnit);
                    }
                    else{
                        parentPlanningUnit.getKindPlanningUnits().remove(managedPlanningUnit);
                    }
                    baseDaoFactoryBean.getPlanningUnitDAO().remove(managedPlanningUnit);
                    for(PlanningUnit pu : projekt.getPlanningUnits()){
                        logger.info(pu.getPlanningUnitName()+": "+pu.getKindPlanningUnits());
                        for(PlanningUnit pu1 : pu.getKindPlanningUnits()){
                            logger.info("\t"+pu1.getPlanningUnitName()+": "+pu1.getKindPlanningUnits());
                        }
                    }

                    final MainUI ui = screen.getUi();
                    ui.setWorkingArea(new ProjektplanungScreen(ui));
                }catch (final PlatzhalterException e){
                    Notification.show(messages.getString("planning_placeholder"));
                } catch (final Exception e) {
                    e.printStackTrace();
                    Notification.show(messages.getString("planning_nodelete"));
                }
            }
        });
    }

    protected void buildForm() {

        buttonLayout.addComponent(addButton);
        buttonLayout.addComponent(deleteButton);

        leftLayout.addComponent(buttonLayout);
        leftLayout.addComponent(screen.getPlanningUnitsTree());
        addComponent(leftLayout);
    }

    private void printchildren(PlanningUnit planningUnitFromDB) {
        for(PlanningUnit pu : planningUnitFromDB.getKindPlanningUnits()){
            logger.info("children of "+pu+": "+pu.getKindPlanningUnits());
            printchildren(pu);
        }
    }

}
