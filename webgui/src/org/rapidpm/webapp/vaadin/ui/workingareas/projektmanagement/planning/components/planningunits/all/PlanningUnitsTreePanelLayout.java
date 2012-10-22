package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all;

import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.details.PlanningDetailsFieldGroup;

import java.util.List;
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
    private VerticalLayout rightLayout = new VerticalLayout();

    private HorizontalLayout buttonLayout = new HorizontalLayout();
    private PlanningUnitEditableLayout planningUnitEditableLayout;
    private PlannedProject projekt;

    private Button addButton = new Button("+");
    private Button deleteButton = new Button("-");
    private List<AbstractField> fieldList;
    private PlanningDetailsFieldGroup fieldGroup;
    private ResourceBundle messages;
    private PlanningUnitsTreePanelLayoutBean bean;
    private DaoFactoryBean baseDaoFactoryBean;

    private ProjektplanungScreen screen;

    public PlanningUnitsTreePanelLayout(final PlannedProject projekt, final ProjektplanungScreen screen) {
        //super(screen);
        this.screen = screen;
        this.projekt = projekt;
        bean = EJBFactory.getEjbInstance(PlanningUnitsTreePanelLayoutBean.class);
        baseDaoFactoryBean = bean.getDaoFactoryBean();

        messages = screen.getMessagesBundle();
        createEditableLayout(screen);
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
                    if(managedPlanningUnit.getKindPlanningUnits() != null && !managedPlanningUnit.getKindPlanningUnits
                            ().isEmpty())
                        throw new Exception();
                    final PlanningUnit parentPlanningUnit = managedPlanningUnit.getParent();
                    parentPlanningUnit.getKindPlanningUnits().remove(managedPlanningUnit);
                    baseDaoFactoryBean.saveOrUpdate(parentPlanningUnit);
                    baseDaoFactoryBean.getPlanningUnitDAO().remove(managedPlanningUnit);
                    baseDaoFactoryBean.getEntityManager().refresh(parentPlanningUnit);
                    projekt = baseDaoFactoryBean.getPlannedProjectDAO().findByID(projekt.getId());
                    baseDaoFactoryBean.saveOrUpdate(projekt);
                    //baseDaoFactoryBean.getEntityManager().refresh(projekt);
                    for(PlanningUnit pu : projekt.getPlanningUnits()){
                        System.out.println(pu.getPlanningUnitName()+": "+pu.getKindPlanningUnits());
                        for(PlanningUnit pu1 : pu.getKindPlanningUnits()){
                            System.out.println("\t"+pu1.getPlanningUnitName()+": "+pu1.getKindPlanningUnits());
                        }
                    }
                    final MainUI ui = screen.getUi();
                    ui.setWorkingArea(new ProjektplanungScreen(ui));
                } catch (final Exception e) {
                    e.printStackTrace();
                    Notification.show(messages.getString("planning_nodelete"));
                }

            }
        });
    }

    public void createEditableLayout(ProjektplanungScreen screen) {
        final PlanningUnit planningUnitFromTree = (PlanningUnit) screen.getPlanningUnitsTree().getValue();
        final PlanningUnit planningUnitFromDB = baseDaoFactoryBean.getPlanningUnitDAO().findByID
                (planningUnitFromTree.getId());
        planningUnitEditableLayout =  new PlanningUnitEditableLayout(planningUnitFromDB, screen, screen.getTreePanel());
    }

    protected void buildForm() {

        buttonLayout.addComponent(addButton);
        buttonLayout.addComponent(deleteButton);

        leftLayout.addComponent(buttonLayout);
        leftLayout.addComponent(screen.getPlanningUnitsTree());

        rightLayout.addComponent(planningUnitEditableLayout);

        addComponent(leftLayout);
        addComponent(rightLayout);

//        for(final AbstractField field : fieldList){
//            field.setReadOnly(true);
//            if(field instanceof AbstractSelect){
//                ((ComboBox)field).setNullSelectionAllowed(false);
//                ((ComboBox)field).setTextInputAllowed(false);
//            }
//        }
//        for(final Field<?> field : fieldGroup.getFields()){
//            componentsLayout.addComponent(field);
//        }
    }

    public PlanningUnitEditableLayout getPlanningUnitEditableLayout() {
        return planningUnitEditableLayout;
    }

    public void setPlanningUnitEditableLayout(PlanningUnitEditableLayout planningUnitEditableLayout) {
        this.planningUnitEditableLayout = planningUnitEditableLayout;
    }

    public VerticalLayout getRightLayout() {
        return rightLayout;
    }
}
