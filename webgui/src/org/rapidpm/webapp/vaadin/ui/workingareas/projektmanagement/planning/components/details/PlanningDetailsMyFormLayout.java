package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.details;

import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.MyFormLayout;

import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.COMMIT_EXCEPTION_MESSAGE;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 25.08.12
 * Time: 18:50
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */

public class PlanningDetailsMyFormLayout extends MyFormLayout {

    private static final Logger logger = Logger.getLogger(PlanningDetailsMyFormLayout.class);

    private List<AbstractField> fieldList;
    private PlanningDetailsFieldGroup fieldGroup;
    private ResourceBundle messages;

    public PlanningDetailsMyFormLayout(final IssueBase issueBase, final ProjektplanungScreen screen, final Panel screenPanel) {
        super(screen, screenPanel);
        messages = screen.getMessagesBundle();

        fieldGroup = new PlanningDetailsFieldGroup(messages, issueBase);
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
        componentsLayout.addComponent(fieldGroup.getStatusBox());
        componentsLayout.addComponent(fieldGroup.getPriorityBox());
        componentsLayout.addComponent(fieldGroup.getReporterBox());
        componentsLayout.addComponent(fieldGroup.getAssigneeBox());
        componentsLayout.addComponent(fieldGroup.getPlannedField());
        componentsLayout.addComponent(fieldGroup.getResolvedField());
        componentsLayout.addComponent(fieldGroup.getClosedField());
    }


}
