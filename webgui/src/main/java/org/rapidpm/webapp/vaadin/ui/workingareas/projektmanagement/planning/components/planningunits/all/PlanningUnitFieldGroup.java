package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import org.rapidpm.Constants;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.textelement.TextElement;
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
    private TextField storyPointsField;
    private TextField complexityField;
    private TextField orderNumberField;
    private ComboBox parentBox;
    private ComboBox responsibleBox;

    private PlanningUnit selectedPlanningUnit;

    private ProjektplanungScreen screen;
    private ResourceBundle messages;
    private DaoFactory daoFactory;

    public PlanningUnitFieldGroup(final ProjektplanungScreen screen){
        this.screen = screen;
        this.messages = screen.getMessagesBundle();
        daoFactory = DaoFactorySingleton.getInstance();
        final PlanningUnit planningUnit = new PlanningUnit();
        planningUnit.setTestcases(new ArrayList<TextElement>());
        planningUnit.setPlanningUnitElementList(new ArrayList<PlanningUnitElement>());
        setItemDataSource(new BeanItem<>(planningUnit));
        selectedPlanningUnit = (PlanningUnit) screen.getPlanningUnitSelect().getValue();
        buildForm();
    }

    public PlanningUnitFieldGroup(final ProjektplanungScreen screen, final PlanningUnit thePlanningUnit) {
        this.screen = screen;
        this.messages = screen.getMessagesBundle();
        daoFactory = DaoFactorySingleton.getInstance();
        selectedPlanningUnit = daoFactory.getPlanningUnitDAO().findByID(thePlanningUnit.getId(), true);
        if(selectedPlanningUnit == null){
            selectedPlanningUnit = thePlanningUnit;
        }
        setItemDataSource(new BeanItem<>(selectedPlanningUnit));
        buildForm();
    }

    private void buildForm() {
        final List<Benutzer> users = daoFactory.getBenutzerDAO().findAll();
        PlanningUnit planningUnitFromSelectComponent = (PlanningUnit) screen.getPlanningUnitSelect().getValue();
        final Set<PlanningUnit> managedPlanningUnits = getAllPlanningUnits(planningUnitFromSelectComponent);

        fieldList = new ArrayList<>();
        for (final Object propertyId : getUnboundPropertyIds()) {
            final String spaltenName = propertyId.toString();
            switch (spaltenName) {
                case (PlanningUnit.NAME):
                    nameField = new TextField(messages.getString("planning_name"));
                    nameField.setRequired(true);
                    nameField.setMaxLength(Constants.FIELDLENGTH_LONG_NAME);
                    bind(nameField, propertyId);
                    nameField.setNullRepresentation("");
                    fieldList.add(nameField);
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
                    parentBox.setNullSelectionAllowed(false);
                    parentBox.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
                    parentBox.setItemCaptionPropertyId(PlanningUnit.NAME);
                    parentBox.setTextInputAllowed(false);
                    bind(parentBox, propertyId);
                    final PlanningUnit selectedPlanningUnit = ((BeanItem<PlanningUnit>) getItemDataSource()).getBean();
                    parentBox.select(selectedPlanningUnit.getParent());
                    parentBox.setRequired(true);
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

    private Set<PlanningUnit> getAllPlanningUnits(final PlanningUnit parentPlanningUnit) {
        final Set<PlanningUnit> resultSet = new HashSet<>();
        getPlanningUnits(resultSet, parentPlanningUnit);
        return resultSet;
    }

    private void getPlanningUnits(final Set<PlanningUnit> resultSet, final PlanningUnit planningUnit) {
        resultSet.add(planningUnit);
        final List<PlanningUnit> kindPlanningUnits = planningUnit.getKindPlanningUnits();
        if(kindPlanningUnits != null && !kindPlanningUnits.isEmpty()){
            resultSet.addAll(kindPlanningUnits);
            for(PlanningUnit kindKindPlanningUnit : kindPlanningUnits){
                kindKindPlanningUnit = daoFactory.getPlanningUnitDAO().findByID(kindKindPlanningUnit.getId(), true);
                getPlanningUnits(resultSet, kindKindPlanningUnit);
            }
        }
    }

    public List<AbstractField> getFieldList() {
        return fieldList;
    }

    public TextField getNameField() {
        return nameField;
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
