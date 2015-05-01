package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
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
        final ResourceBundle messagesBundle = screen.getMessagesBundle();
        setCaption(messagesBundle.getString("planning_renamePlanningUnit"));
        fieldGroup = new PlanningUnitFieldGroup(screen, (PlanningUnit) screen.getPlanningUnitsTree().getValue());
        saveCancelButtonLeiste = new SaveCancelButtonLeiste() {
            @Override
            public void doInternationalization() {
                saveButton.setCaption(messagesBundle.getString("save"));
                cancelButton.setCaption(messagesBundle.getString("cancel"));
            }

            @Override
            public void setSaveButtonListener() {
                saveButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
                        try {
                            final PlanningUnit changedPlanningUnit = ((BeanItem<PlanningUnit>)fieldGroup
                                    .getItemDataSource()).getBean();
                            if(changedPlanningUnit.getId() == String.valueOf(ProjektplanungScreen.PLATZHALTER_ID)){
                                RenamePlanningUnitWindow.this.close();
                                Notification.show(messagesBundle.getString("planning_placeholder_rename"));
                            } else {
                                fieldGroup.getField(PlanningUnit.PARENT).setRequired(false);
                                fieldGroup.commit();
                                final PlanningUnit planningUnit = ((BeanItem<PlanningUnit>) fieldGroup.getItemDataSource()).getBean();
//                                daoFactory.saveOrUpdateTX(planningUnit);
                                RenamePlanningUnitWindow.this.close();
                                screen.getUi().setWorkingArea(new ProjektplanungScreen(screen.getUi()));
                            }
                        } catch (final FieldGroup.CommitException e) {
                            Notification.show(messagesBundle.getString("incompletedata"));
                        }
                    }
                });
            }

            @Override
            public void setCancelButtonListener() {
                cancelButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        RenamePlanningUnitWindow.this.close();
                    }
                });
            }
        };
        buildForm();
    }

    private void buildForm() {
        final TextField field = (TextField)fieldGroup.getField(PlanningUnit.NAME);
        field.selectAll();
        addComponent(field);
        addComponent(saveCancelButtonLeiste);
        getContentLayout().setComponentAlignment(saveCancelButtonLeiste, Alignment.BOTTOM_RIGHT);
    }
}
