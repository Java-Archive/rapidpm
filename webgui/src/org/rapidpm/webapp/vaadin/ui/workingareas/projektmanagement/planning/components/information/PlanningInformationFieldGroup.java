package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.information;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase.STORYPOINTS;
import static org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase.TEXT;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 26.09.12
 * Time: 12:51
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningInformationFieldGroup extends FieldGroup {

    private List<AbstractField> fieldList = new ArrayList<>();
    private TextArea descriptionArea;
    private AbstractTextField storypointsField;

    private ResourceBundle messages;

    public PlanningInformationFieldGroup(final ResourceBundle messages, final IssueBase issueBase) {
        setItemDataSource(new BeanItem<>(issueBase));
        this.messages = messages;
        buildForm();
    }

    private void buildForm() {
        AbstractTextField field;
        TextArea area;
        for (final Object propertyId : getUnboundPropertyIds()) {
            final String spaltenName = propertyId.toString();
            switch(spaltenName){
                case(TEXT):
                    area = generateTextArea(messages.getString("planning_description"));
                    bind(area, propertyId);
                    descriptionArea = area;
                    fieldList.add(area);
                    break;
                case(STORYPOINTS):
                    field = generateField(messages.getString("planning_storypoints"));
                    bind(field, propertyId);
                    storypointsField = field;
                    fieldList.add(field);
                    break;
                default:
                    break;
            }
        }
    }

    public AbstractTextField generateField(String caption){
        final AbstractTextField textField = new TextField(caption);
        return textField;
    }

    public TextArea generateTextArea(String caption){
        final TextArea textArea = new TextArea(caption);
        textArea.setNullRepresentation(messages.getString("notexistent"));
        return textArea;
    }

    public List<AbstractField> getFieldList() {
        return fieldList;
    }

    public TextArea getDescriptionArea() {
        return descriptionArea;
    }

    public void setDescriptionArea(TextArea descriptionArea) {
        this.descriptionArea = descriptionArea;
    }

    public AbstractTextField getStorypointsField() {
        return storypointsField;
    }

    public void setStorypointsField(AbstractTextField storypointsField) {
        this.storypointsField = storypointsField;
    }
}
