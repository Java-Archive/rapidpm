package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import org.rapidpm.Constants;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;

import java.util.*;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 26.09.12
 * Time: 12:51
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningUnitFieldGroup extends FieldGroup {

    private List<AbstractField> fieldList = new ArrayList<>();
    private TextField nameField;
    private RichTextArea descriptionArea;
    private TextField storyPointsField;
    private TextField complexityField;
    private TextField orderNumberField;
    private ComboBox parentBox;
    private ComboBox responsibleBox;

    private PlanningUnit selectedPlanningUnit;

    private ProjektplanungScreen screen;
    private ResourceBundle messages;
    private PlanningUnitFieldGroupBean bean;
    private DaoFactoryBean baseDaoFactoryBean;

    public PlanningUnitFieldGroup(final ProjektplanungScreen screen){
        this.screen = screen;
        this.messages = screen.getMessagesBundle();
        bean = EJBFactory.getEjbInstance(PlanningUnitFieldGroupBean.class);
        baseDaoFactoryBean = bean.getDaoFactoryBean();
        final PlanningUnit planningUnit = new PlanningUnit();
        planningUnit.setPlanningUnitElementList(new ArrayList<PlanningUnitElement>());
        setItemDataSource(new BeanItem<>(planningUnit));
        selectedPlanningUnit = (PlanningUnit) screen.getPlanningUnitSelect().getValue();
        buildForm();
    }

    public PlanningUnitFieldGroup(final ProjektplanungScreen screen, final PlanningUnit thePlanningUnit) {
        this.screen = screen;
        this.messages = screen.getMessagesBundle();
        bean = EJBFactory.getEjbInstance(PlanningUnitFieldGroupBean.class);
        baseDaoFactoryBean = bean.getDaoFactoryBean();
        selectedPlanningUnit = baseDaoFactoryBean.getPlanningUnitDAO().findByID(thePlanningUnit.getId());
        if(selectedPlanningUnit == null){
            selectedPlanningUnit = thePlanningUnit;
        }
        setItemDataSource(new BeanItem<>(selectedPlanningUnit));
        buildForm();
    }

    private void buildForm() {
        final List<Benutzer> users = baseDaoFactoryBean.getBenutzerDAO().loadAllEntities();
        final List<PlanningUnit> planningUnits = selectedPlanningUnit.getKindPlanningUnits();
        final Set<PlanningUnit> managedPlanningUnits = new HashSet<>();
        for(final PlanningUnit planningUnit : planningUnits){
           managedPlanningUnits.add(baseDaoFactoryBean.getPlanningUnitDAO().findByID(planningUnit.getId()));
        }

        fieldList = new ArrayList<>();
        for (final Object propertyId : getUnboundPropertyIds()) {
            final String spaltenName = propertyId.toString();
            switch (spaltenName) {
                case (PlanningUnit.NAME):
                    nameField = new TextField(messages.getString("planning_name"));
                    nameField.setRequired(true);
                    nameField.setMaxLength(Constants.FIELDLENGTH_LONG_NAME);
                    bind(nameField, propertyId);
                    fieldList.add(nameField);
                    break;
                case (PlanningUnit.DESCPRIPTION):
                    descriptionArea = new RichTextArea(messages.getString("planning_description"));
                    descriptionArea.setNullRepresentation(messages.getString("planning_nodescription"));
                    bind(descriptionArea, propertyId);
                    fieldList.add(descriptionArea);
                    break;
                case (PlanningUnit.STORYPTS):
                    storyPointsField = new TextField(messages.getString("planning_storypoints"));
                    storyPointsField.setRequired(true);
                    storyPointsField.setMaxLength(Constants.FIELDLENGTH_SMALL_NUMBER);
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
                    orderNumberField.setRequired(true);
                    bind(orderNumberField, propertyId);
                    fieldList.add(orderNumberField);
                    break;
                case (PlanningUnit.PARENT):
                    final PlanningUnit nullPlanningUnit = new PlanningUnit();
                    nullPlanningUnit.setPlanningUnitName(messages.getString("planning_noparent"));
                    parentBox = new ComboBox(messages.getString("planning_parent"),
                            new BeanItemContainer<>(PlanningUnit.class, managedPlanningUnits));
                    parentBox.setNullSelectionAllowed(true);
                    parentBox.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
                    parentBox.setItemCaptionPropertyId(PlanningUnit.NAME);
                    parentBox.setTextInputAllowed(false);
                    bind(parentBox, propertyId);
                    parentBox.addItem(nullPlanningUnit);
                    parentBox.addItem(selectedPlanningUnit);
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
                    responsibleBox.setRequired(true);
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

    public RichTextArea getDescriptionArea() {
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
