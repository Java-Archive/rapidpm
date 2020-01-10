package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.information;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.EditableLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all.PlanningUnitFieldGroup;

import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.COMMIT_EXCEPTION_MESSAGE;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 30.08.12
 * Time: 09:15
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningInformationEditableLayout extends EditableLayout {

    //TODO Testcases managen
    private ResourceBundle messages;
    private List<AbstractField> fieldList;
    private PlanningUnitFieldGroup fieldGroup;

    public PlanningInformationEditableLayout(final PlanningUnit planningUnit, final ProjektplanungScreen screen) {
        this.messages = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);

        fieldGroup = new PlanningUnitFieldGroup(screen, planningUnit);
        fieldList = fieldGroup.getFieldList();

        buildForm();
        cancelButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            final Iterator<Component> componentIterator = componentsLayout.getChildren().iterator();
                fieldGroup.readBean(fieldGroup.getBean());
            while (componentIterator.hasNext()) {
                final Component component = componentIterator.next();
                if (component instanceof AbstractField) {
                    ((AbstractField)component).setReadOnly(true);
                }
            }
            buttonLayout.setVisible(false);
        });

        saveButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            try{
                final PlanningUnit editedPlanningUnit = fieldGroup.getBean();
                fieldGroup.writeBean(editedPlanningUnit);
                final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
                daoFactory.saveOrUpdateTX(editedPlanningUnit);
            }catch (NullPointerException e){
//                logger.info(COMMIT_EXCEPTION_MESSAGE);
            }catch(Exception e){
//                logger.warn("Exception", e);
            }
        });
    }

    @Override
    protected void buildForm() {
        //setReadOnly(true) veursacht javascriptexception seit beta10
        for(final AbstractField field : fieldList){
            field.setReadOnly(false);
//            if(field instanceof AbstractSelect){
//                ((ComboBox)field).setNullSelectionAllowed(false);
//                ((ComboBox)field).setTextInputAllowed(false);
//            }
        }
    }

    @Override
    protected void setLayout() {
        componentsLayout = new FormLayout();
    }
}
