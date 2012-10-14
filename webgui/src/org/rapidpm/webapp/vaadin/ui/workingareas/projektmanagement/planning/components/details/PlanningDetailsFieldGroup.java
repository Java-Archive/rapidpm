package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.details;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriority;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatus;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.system.security.Benutzer;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase.*;
import static org.rapidpm.Constants.*;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 26.09.12
 * Time: 12:51
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningDetailsFieldGroup extends FieldGroup {

    private List<AbstractField> fieldList = new ArrayList<>();

    private AbstractSelect responsiblePersonBox;
    private TextField orderNumberField;
    private TextField complexityField;
    private TextField storyPointsField;

    private ResourceBundle messages;
    private PlanningDetailsFieldGroupBean bean;
    private DaoFactoryBean baseDaoFactoryBean;

    public PlanningDetailsFieldGroup(final ResourceBundle messages, final PlanningUnit planningUnit) {
        setItemDataSource(new BeanItem<>(planningUnit));
        this.messages = messages;
        bean = EJBFactory.getEjbInstance(PlanningDetailsFieldGroupBean.class);
        baseDaoFactoryBean = bean.getDaoFactoryBean();
        buildForm();
    }

    private void buildForm() {
        refreshEntities(baseDaoFactoryBean);
        final List<IssueStatus> statusList = baseDaoFactoryBean.getIssueStatusDAO().loadAllEntities();
        final List<Benutzer> users = baseDaoFactoryBean.getBenutzerDAO().loadAllEntities();
        AbstractSelect box;
        DateField dateField;
        for (final Object propertyId : getUnboundPropertyIds()) {
            final String spaltenName = propertyId.toString();
            switch(spaltenName){
//                case(PlanningUnit.RESPONSIBLE):
//                    box = generateBox(messages.getString("planning_responsible"),
//                        new BeanItemContainer<>(Benutzer.class, users),Benutzer.LOGIN);
//                    bind(box, propertyId);
//                    responsiblePersonBox = box;
//                    fieldList.add(box);
//                    break;
//                case(PlanningUnit.ORDERNUMBER):
//                    orderNumberField = new TextField(messages.getString("planning_ordernumber"));
//                    bind(orderNumberField, propertyId);
//                    fieldList.add(orderNumberField);
//                    break;
//                case(PlanningUnit.COMPLEXITY):
//                    complexityField = new TextField(messages.getString("planning_complexity"));
//                    bind(complexityField, propertyId);
//                    fieldList.add(complexityField);
//                    break;
//                case(PlanningUnit.STORYPTS):
//                    storyPointsField = new TextField(messages.getString("planning_storypoints"));
//                    bind(storyPointsField, propertyId);
//                    fieldList.add(storyPointsField);
//                    break;
                default:
                    break;
            }
        }
    }

    public AbstractSelect generateBox(final String caption, final BeanItemContainer container, final String itemCaptionPropertyId){
        final AbstractSelect box = new ComboBox(caption,container);
        box.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        box.setItemCaptionPropertyId(itemCaptionPropertyId);
        return box;
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

    public AbstractSelect getResponsiblePersonBox() {
        return responsiblePersonBox;
    }

    public TextField getComplexityField() {
        return complexityField;
    }

    public TextField getOrderNumberField() {
        return orderNumberField;
    }

    public TextField getStoryPointsField() {
        return storyPointsField;
    }
}
