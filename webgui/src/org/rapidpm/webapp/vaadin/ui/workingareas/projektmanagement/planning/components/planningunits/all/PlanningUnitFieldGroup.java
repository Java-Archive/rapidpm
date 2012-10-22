package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
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
 * User: Marco Ebbinghaus
 * Date: 26.09.12
 * Time: 12:51
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningUnitFieldGroup extends FieldGroup {

    public static final Integer STANDARDVALUE = 1;

    private List<AbstractField> fieldList = new ArrayList<>();
    private TextField nameField;
    private TextArea descriptionArea;
    private TextField storyPointsField;
    private TextField complexityField;
    private TextField orderNumberField;
    private ComboBox parentBox;
    private ComboBox responsibleBox;

    private ResourceBundle messages;
    private PlanningDetailsFieldGroupBean bean;
    private DaoFactoryBean baseDaoFactoryBean;
    private boolean isNew;

    public PlanningUnitFieldGroup(final ResourceBundle messages){
        this.messages = messages;
        bean = EJBFactory.getEjbInstance(PlanningDetailsFieldGroupBean.class);
        baseDaoFactoryBean = bean.getDaoFactoryBean();
        final PlanningUnit planningUnit = new PlanningUnit();
        planningUnit.setPlanningUnitElementList(new ArrayList<PlanningUnitElement>());
        setItemDataSource(new BeanItem<>(planningUnit));
        isNew = true;
        buildForm();
    }

    public PlanningUnitFieldGroup(final ResourceBundle messages, final PlanningUnit thePlanningUnit) {
        this.messages = messages;
        bean = EJBFactory.getEjbInstance(PlanningDetailsFieldGroupBean.class);
        baseDaoFactoryBean = bean.getDaoFactoryBean();
        final PlanningUnit planningUnit = baseDaoFactoryBean.getPlanningUnitDAO().findByID(thePlanningUnit.getId());
        isNew = false;
        setItemDataSource(new BeanItem<>(planningUnit));
        buildForm();
    }

    private void buildForm() {
        final List<Benutzer> users = baseDaoFactoryBean.getBenutzerDAO().loadAllEntities();
        final List<PlanningUnit> planningUnits = baseDaoFactoryBean.getPlanningUnitDAO().loadAllEntities();
        fieldList = new ArrayList<>();
        for (final Object propertyId : getUnboundPropertyIds()) {
            final String spaltenName = propertyId.toString();
            switch (spaltenName) {
                case (PlanningUnit.NAME):
                    nameField = new TextField(messages.getString("planning_name"));
                    nameField.setRequired(true);
                    bind(nameField, propertyId);
                    fieldList.add(nameField);
                    break;
                case (PlanningUnit.DESCPRIPTION):
                    descriptionArea = new TextArea(messages.getString("planning_description"));
                    descriptionArea.setNullRepresentation(messages.getString("planning_nodescription"));
                    bind(descriptionArea, propertyId);
                    fieldList.add(descriptionArea);
                    break;
                case (PlanningUnit.STORYPTS):
                    storyPointsField = new TextField(messages.getString("planning_storypoints"));
                    storyPointsField.setRequired(true);
                    bind(storyPointsField, propertyId);
                    fieldList.add(storyPointsField);
                    break;
                case (PlanningUnit.COMPLEXITY):
                    complexityField = new TextField(messages.getString("planning_complexity"));
                    complexityField.setRequired(true);
                    bind(complexityField, propertyId);
                    fieldList.add(complexityField);
                    break;
                case (PlanningUnit.ORDERNUMBER):
                    orderNumberField = new TextField(messages.getString("planning_ordernumber"));
                    bind(orderNumberField, propertyId);
                    fieldList.add(orderNumberField);
                    break;
                case (PlanningUnit.PARENT):
                    final PlanningUnit nullPlanningUnit = new PlanningUnit();
                    nullPlanningUnit.setPlanningUnitName(messages.getString("planning_noparent"));
                    parentBox = new ComboBox(messages.getString("planning_parent"),
                            new BeanItemContainer<>(PlanningUnit.class, planningUnits));
                    parentBox.setNullSelectionAllowed(true);
                    parentBox.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
                    parentBox.setItemCaptionPropertyId(PlanningUnit.NAME);
                    parentBox.setTextInputAllowed(false);
                    bind(parentBox, propertyId);
                    parentBox.addItem(nullPlanningUnit);
                    parentBox.setNullSelectionItemId(nullPlanningUnit);
                    fieldList.add(parentBox);
                    break;
                case (PlanningUnit.RESPONSIBLE):
                    responsibleBox = new ComboBox(messages.getString("planning_responsible"),
                            new BeanItemContainer<>(Benutzer.class, users));
                    responsibleBox.setNullSelectionAllowed(false);
                    responsibleBox.setTextInputAllowed(false);
                    responsibleBox.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
                    responsibleBox.setItemCaptionPropertyId(Benutzer.LOGIN);
                    bind(responsibleBox, propertyId);
                    fieldList.add(responsibleBox);
                    break;
                default:
                    break;
            }
        }
    }

    public List<AbstractField> getFieldList() {
        return fieldList;
    }

    public TextField getNameField() {
        return nameField;
    }

    public TextArea getDescriptionArea() {
        return descriptionArea;
    }

    public TextField getStoryPointsField() {
        return storyPointsField;
    }

    public TextField getComplexityField() {
        return complexityField;
    }

    public TextField getOrderNumberField() {
        return orderNumberField;
    }

    public ComboBox getParentBox() {
        return parentBox;
    }

    public ComboBox getResponsibleBox() {
        return responsibleBox;
    }
}
