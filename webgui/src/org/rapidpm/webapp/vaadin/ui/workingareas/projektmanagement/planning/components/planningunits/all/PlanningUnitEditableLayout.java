package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.EditableLayout;

import javax.persistence.EntityManager;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.COMMIT_EXCEPTION_MESSAGE;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 16.10.12
 * Time: 13:11
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningUnitEditableLayout extends EditableLayout {

    private static final Logger logger = Logger.getLogger(PlanningUnitEditableLayout.class);

    private List<AbstractField> fieldList;
    private PlanningUnitFieldGroup fieldGroup;
    private ResourceBundle messages;

    private PlanningUnitEditableLayoutBean bean;
    private DaoFactoryBean baseDaoFactoryBean;

    public PlanningUnitEditableLayout(final PlanningUnit planningUnit, final ProjektplanungScreen screen,
                                         final Panel screenPanel) {
        super(screen, screenPanel);
        messages = screen.getMessagesBundle();
        bean = EJBFactory.getEjbInstance(PlanningUnitEditableLayoutBean.class);
        baseDaoFactoryBean = bean.getDaoFactoryBean();
        //refreshEntities(baseDaoFactoryBean);


        fieldGroup = new PlanningUnitFieldGroup(messages, planningUnit);
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
                    PlanningUnit pu = baseDaoFactoryBean.getPlanningUnitDAO().findByID(planningUnit.getId());
                    pu.setPlanningUnitName(changedPlanningUnit.getPlanningUnitName());
                    baseDaoFactoryBean.getEntityManager().refresh(pu);

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

    @Override
    protected void buildForm() {
        for(final AbstractField field : fieldList){
            field.setReadOnly(true);
            if(field instanceof AbstractSelect){
                ((ComboBox)field).setNullSelectionAllowed(false);
                ((ComboBox)field).setTextInputAllowed(false);
            }
        }
        for(final Field<?> field : fieldGroup.getFields()){
            componentsLayout.addComponent(field);
        }
        componentsLayout.addComponent(fieldGroup.getNameField());
    }

//    private void refreshEntities(final DaoFactoryBean baseDaoFactoryBean) {
//        final EntityManager entityManager = baseDaoFactoryBean.getEntityManager();
////        for(final PlannedProject plannedProject : baseDaoFactoryBean.getPlannedProjectDAO().loadAllEntities()){
////            entityManager.refresh(plannedProject);
////        }
//        for(final PlanningUnitElement planningUnitElement : baseDaoFactoryBean.getPlanningUnitElementDAO().loadAllEntities()){
//            entityManager.refresh(planningUnitElement);
//        }
//        for(final PlanningUnit planningUnit : baseDaoFactoryBean.getPlanningUnitDAO().loadAllEntities()){
//            entityManager.refresh(planningUnit);
//        }
//        for(final RessourceGroup ressourceGroup : baseDaoFactoryBean.getRessourceGroupDAO().loadAllEntities()){
//            entityManager.refresh(ressourceGroup);
//        }
//        for(final Benutzer benutzer : baseDaoFactoryBean.getBenutzerDAO().loadAllEntities()){
//            entityManager.refresh(benutzer);
//        }
//    }
}
