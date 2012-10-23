package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.information;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.EditableLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.details.PlanningDetailsEditableLayoutBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all.PlanningUnitFieldGroup;

import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.COMMIT_EXCEPTION_MESSAGE;

/**
 * Created with IntelliJ IDEA.
 * User: Marco Ebbinghaus
 * Date: 16.10.12
 * Time: 13:11
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningInformationEditableLayout extends EditableLayout {

    private static final Logger logger = Logger.getLogger(PlanningInformationEditableLayout.class);

    private List<AbstractField> fieldList;
    private PlanningUnitFieldGroup fieldGroup;
    private ResourceBundle messages;

    private PlanningInformationEditableLayoutBean bean;
    private DaoFactoryBean baseDaoFactoryBean;

    public PlanningInformationEditableLayout(final PlanningUnit planningUnit, final ProjektplanungScreen screen,
                                             final Panel screenPanel) {
        super(screen, screenPanel);
        messages = screen.getMessagesBundle();
        bean = EJBFactory.getEjbInstance(PlanningInformationEditableLayoutBean.class);
        baseDaoFactoryBean = bean.getDaoFactoryBean();

        PlanningUnit planningUnitFromDB = baseDaoFactoryBean.getPlanningUnitDAO().findByID(planningUnit.getId());
        //final List<PlanningUnit> planningUnitList = baseDaoFactoryBean.getPlanningUnitDAO().loadAllEntities();
        if(planningUnitFromDB == null){
            //if(planningUnitList != null && !planningUnitList.isEmpty()) {
            //    planningUnitFromDB = planningUnitList.get(0);
            //} else {
                planningUnitFromDB = screen.getTempPlanningUnit();
            //}
        }
        printchildren(planningUnitFromDB);
        //baseDaoFactoryBean.getEntityManager().refresh(planningUnitFromDB);


        fieldGroup = new PlanningUnitFieldGroup(screen, planningUnitFromDB);
        fieldList = fieldGroup.getFieldList();

        buildForm();
        cancelButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                final Iterator<Component> componentIterator = componentsLayout.getComponentIterator();
                fieldGroup.discard();
                while (componentIterator.hasNext()) {
                    final Component component = componentIterator.next();
                    if (component instanceof Field) {
                        component.setReadOnly(true);
                    }
                }
                buttonLayout.setVisible(false);
            }
        });
        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try{
                    fieldGroup.commit();

                    final BeanItem<PlanningUnit> planningUnitBeanItem = (BeanItem<PlanningUnit>)fieldGroup
                            .getItemDataSource();
                    final PlanningUnit changedPlanningUnit = planningUnitBeanItem.getBean();
//                    PlanningUnit pu = baseDaoFactoryBean.getPlanningUnitDAO().findByID(planningUnit.getId());
//                    pu.setPlanningUnitName(changedPlanningUnit.getPlanningUnitName());
                    baseDaoFactoryBean.saveOrUpdate(changedPlanningUnit);
                    final MainUI ui = screen.getUi();
                    ui.setWorkingArea(new ProjektplanungScreen(ui));
                }catch (NullPointerException e){
                    logger.info(COMMIT_EXCEPTION_MESSAGE);
                }catch(Exception e){
                    logger.warn("Exception", e);
                }
            }
        });
    }

    private void printchildren(PlanningUnit planningUnitFromDB) {
        for(PlanningUnit pu : planningUnitFromDB.getKindPlanningUnits()){
            System.out.println("children of "+pu+": "+pu.getKindPlanningUnits());
            printchildren(pu);
        }
    }

    @Override
    protected void buildForm() {
        for(final AbstractField field : fieldList){
            field.setReadOnly(true);
            if(field instanceof AbstractSelect){
                ((ComboBox)field).setNullSelectionAllowed(false);
                ((ComboBox)field).setTextInputAllowed(false);
            }
        }
        componentsLayout.addComponent(fieldGroup.getDescriptionArea());
        componentsLayout.addComponent(fieldGroup.getStoryPointsField());
        componentsLayout.addComponent(fieldGroup.getComplexityField());
        componentsLayout.addComponent(fieldGroup.getOrderNumberField());
        componentsLayout.addComponent(new Label("//TODO Testcases"));
    }
}
