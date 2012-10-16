package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.details.PlanningDetailsFieldGroupBean;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 26.09.12
 * Time: 12:51
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningUnitFieldGroup extends FieldGroup {

    private List<AbstractField> fieldList = new ArrayList<>();
    private TextField nameField;

    private ResourceBundle messages;
    private PlanningDetailsFieldGroupBean bean;
    private DaoFactoryBean baseDaoFactoryBean;

    public PlanningUnitFieldGroup(final ResourceBundle messages, final PlanningUnit thePlanningUnit) {
        this.messages = messages;
        bean = EJBFactory.getEjbInstance(PlanningDetailsFieldGroupBean.class);
        baseDaoFactoryBean = bean.getDaoFactoryBean();
        final PlanningUnit planningUnit = baseDaoFactoryBean.getPlanningUnitDAO().loadPlanningUnitByName
                (thePlanningUnit.getPlanningUnitName());
        setItemDataSource(new BeanItem<>(planningUnit));
        buildForm();
    }

    private void buildForm() {
        refreshEntities(baseDaoFactoryBean);
        for (final Object propertyId : getUnboundPropertyIds()) {
            final String spaltenName = propertyId.toString();
            switch(spaltenName){
                case(PlanningUnit.NAME):
                    nameField = new TextField(messages.getString("planning_name"));
                    bind(nameField, propertyId);
                    fieldList.add(nameField);
                    break;
                default:
                    break;
            }
        }
    }

    private void refreshEntities(final DaoFactoryBean baseDaoFactoryBean) {
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
        for(final Benutzer benutzer : baseDaoFactoryBean.getBenutzerDAO().loadAllEntities()){
            entityManager.refresh(benutzer);
        }
    }

    public List<AbstractField> getFieldList() {
        return fieldList;
    }

    public TextField getNameField() {
        return nameField;
    }

}
