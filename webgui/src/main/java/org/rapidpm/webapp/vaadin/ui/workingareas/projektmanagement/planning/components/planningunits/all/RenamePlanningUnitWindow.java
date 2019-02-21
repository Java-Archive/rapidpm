package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;
import org.rapidpm.webapp.vaadin.ui.SaveCancelButtonLeiste;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;

import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 15.01.13
 * Time: 13:21
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class RenamePlanningUnitWindow extends RapidWindow {

    private PlanningUnitFieldGroup fieldGroup;
    private SaveCancelButtonLeiste saveCancelButtonLeiste;

    public RenamePlanningUnitWindow(final ProjektplanungScreen screen) {
        final ResourceBundle messagesBundle = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);
//        setText(messagesBundle.getString("planning_renamePlanningUnit"));
        fieldGroup = new PlanningUnitFieldGroup(screen, screen.getPlanningUnitsTree().getSelectedItems().iterator().next());
        saveCancelButtonLeiste = new SaveCancelButtonLeiste() {
            @Override
            public void doInternationalization() {
                saveButton.setText(messagesBundle.getString("save"));
                cancelButton.setText(messagesBundle.getString("cancel"));
            }

            @Override
            public void setSaveButtonListener() {
                saveButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
                    final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
                    try {
                        final PlanningUnit changedPlanningUnit = fieldGroup.getSelectedPlanningUnit();
                        fieldGroup.writeBean(changedPlanningUnit);
                        if(changedPlanningUnit.getId() == ProjektplanungScreen.PLATZHALTER_ID){
                            RenamePlanningUnitWindow.this.close();
                            Notification.show(messagesBundle.getString("planning_placeholder_rename"));
                        } else {
                            fieldGroup.getFieldForProperty(PlanningUnit.PARENT).ifPresent(abstractField -> abstractField.setRequiredIndicatorVisible(false));
                            fieldGroup.writeBean(changedPlanningUnit);
                            daoFactory.saveOrUpdateTX(changedPlanningUnit);
                            RenamePlanningUnitWindow.this.close();
//                            screen.getUi().setWorkingArea(new ProjektplanungScreen(screen.getUi()));
                        }
                    } catch (final ValidationException e) {
                        Notification.show(messagesBundle.getString("incompletedata"));
                    }
                });
            }

            @Override
            public void setCancelButtonListener() {
                cancelButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> RenamePlanningUnitWindow.this.close());
            }
        };
        buildForm();
    }

    private void buildForm() {
        fieldGroup.getFieldForProperty(PlanningUnit.NAME).ifPresent(this::add);
        add(saveCancelButtonLeiste);
        getContentLayout().setAlignSelf(FlexComponent.Alignment.END, saveCancelButtonLeiste);
    }
}
