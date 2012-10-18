package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all;

import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.details.PlanningDetailsFieldGroup;

import java.util.List;
import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
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

    private Button addButton = new Button("+");
    private Button deleteButton = new Button("-");
    private List<AbstractField> fieldList;
    private PlanningDetailsFieldGroup fieldGroup;
    private ResourceBundle messages;
    private PlanningUnitsTreePanelLayoutBean bean;
    private DaoFactoryBean baseDaoFactoryBean;

    private ProjektplanungScreen screen;

    public PlanningUnitsTreePanelLayout(final ProjektplanungScreen screen) {
        //super(screen);
        this.screen = screen;
        bean = EJBFactory.getEjbInstance(PlanningUnitsTreePanelLayoutBean.class);
        baseDaoFactoryBean = bean.getDaoFactoryBean();

        messages = screen.getMessagesBundle();
        createEditableLayout(screen);
        createDeleteButton();
        createAddButton();
        buildForm();
    }

    private void createAddButton() {
        //To change body of created methods use File | Settings | File Templates.
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
                    baseDaoFactoryBean.getPlanningUnitDAO().remove(managedPlanningUnit);
                    final MainUI ui = screen.getUi();
                    ui.setWorkingArea(new ProjektplanungScreen(ui));
                } catch (final Exception e) {
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

}
