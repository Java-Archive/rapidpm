package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.details;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.EditableLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;

import java.util.ResourceBundle;

import static org.rapidpm.Constants.COMMIT_EXCEPTION_MESSAGE;

//import org.rapidpm.persistence.DaoFactoryBean;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 25.08.12
 * Time: 18:50
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */

public class PlanningDetailsEditableLayout extends EditableLayout {


    private PlanningDetailsFieldGroup fieldGroup;
    private ResourceBundle messages;

    public PlanningDetailsEditableLayout(final PlanningUnit planningUnit, final ProjektplanungScreen screen,
                                         final Component screenPanel) {
        super();
        messages = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);

        fieldGroup = new PlanningDetailsFieldGroup(messages, planningUnit);

        buildForm();

        cancelButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
            final PlanningUnit planningUnitFromDB = daoFactory.getPlanningUnitDAO().findByID(planningUnit.getId());
            daoFactory.getPlanningUnitDAO().refresh(planningUnitFromDB);
            fieldGroup.readBean(planningUnitFromDB);
            fieldGroup.setReadOnly(true);
            getButtonLayout().ifPresent(parent -> parent.getChildren().forEach(component -> component.setVisible(false)));
        });
        saveButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            try{
                final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
                final PlanningUnit planningUnitFromDB = daoFactory.getPlanningUnitDAO().findByID(planningUnit.getId());
                fieldGroup.writeBean(planningUnitFromDB);
                daoFactory.saveOrUpdateTX(planningUnitFromDB);
                fieldGroup.setReadOnly(true);
                getButtonLayout().ifPresent(parent -> parent.getChildren().forEach(component -> component.setVisible(false)));
            }catch (final NullPointerException e){
//                logger.info(COMMIT_EXCEPTION_MESSAGE);
            }catch(final Exception e){
//                logger.warn("Exception", e);
            }
        });
    }


    @Override
    protected void buildForm() {
//        for(final AbstractField field : fieldList){
//            field.setReadOnly(true);
//            if(field instanceof AbstractSelect){
//                ((ComboBox)field).setNullSelectionAllowed(false);
//                ((ComboBox)field).setTextInputAllowed(false);
//            }
//        }
        fieldGroup.getFieldForProperty(PlanningUnit.RESPONSIBLE).ifPresent(abstractField -> componentsLayout.add(abstractField));
        fieldGroup.getFieldForProperty(PlanningUnit.COMPLEXITY).ifPresent(abstractField -> componentsLayout.add(abstractField));
        fieldGroup.getFieldForProperty(PlanningUnit.ORDERNUMBER).ifPresent(abstractField -> componentsLayout.add(abstractField));
        fieldGroup.getFieldForProperty(PlanningUnit.STORYPTS).ifPresent(abstractField -> componentsLayout.add(abstractField));
    }

    @Override
    protected void setLayout() {
//        componentsLayout = new FormLayout();
    }


}
