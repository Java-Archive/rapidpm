package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 19.09.12
 * Time: 10:17
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.BaseUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.Projekt;

import java.util.Iterator;
import java.util.ResourceBundle;
public class AddPlanningUnitGroupWindow extends Window {

    public static final String HEIGHT = "400px";
    public static final String WIDTH = "400px";
    public static final int POSITION_X = 50;
    public static final int POSITION_Y = 100;

    private static final Logger logger = Logger.getLogger(AddPlanningUnitGroupWindow.class);

    private BaseUI ui;

    private FormLayout formLayout = new FormLayout();
    private HorizontalLayout horizontalButtonLayout = new HorizontalLayout();
    private Button saveButton = new Button();
    private Button cancelButton = new Button();
    private PlanningUnitGroupFieldGroup fieldGroup;
    private ResourceBundle messages;
    private ProjektplanungScreen screen;


    public AddPlanningUnitGroupWindow(final BaseUI ui, final ProjektplanungScreen screen) {
        this.ui = ui;
        this.screen = screen;
        messages = screen.getMessagesBundle();
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setPositionX(POSITION_X);
        setPositionY(POSITION_Y);

        final Projekt projekt = screen.getProjektBean().getProjekte().get
                (screen.getProjektBean().getCurrentProjectIndex());
        final PlanningUnitGroup planningUnitGroup = new PlanningUnitGroup(projekt.getRessourceGroups());
        fieldGroup = new PlanningUnitGroupFieldGroup(planningUnitGroup);

        fillFormLayout();
        addComponent(formLayout);

        horizontalButtonLayout.addComponent(saveButton);
        horizontalButtonLayout.addComponent(cancelButton);

        addComponent(horizontalButtonLayout);

        addListeners(planningUnitGroup, ui);
        doInternationalization();

    }

    private void fillFormLayout() {
        for (final Object propertyId : fieldGroup.getBoundPropertyIds()) {
            final TextField field = (TextField)fieldGroup.getField(propertyId);
            field.setReadOnly(false);
            formLayout.addComponent(field);
        }
    }

    private void doInternationalization() {
        this.setCaption(messages.getString("planning_addpug"));
        saveButton.setCaption(messages.getString("save"));
        cancelButton.setCaption(messages.getString("cancel"));
    }

    private void addListeners(PlanningUnitGroup pug, final BaseUI ui) {
        final PlanningUnitGroup planningUnitGroup = pug;
        saveButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                boolean allFilled = true;
                Iterator<Component> it = AddPlanningUnitGroupWindow.this.formLayout
                        .getComponentIterator();
                while (it.hasNext()) {
                    final Component component = it.next();
                    if (component instanceof AbstractField) {
                        if (((TextField) component).getValue() == null
                                || ((TextField) component).getValue().equals(""))
                            allFilled = false;
                    }
                }
                if (allFilled) {
                    try {
                        fieldGroup.commit();
                        final Projekt projekt = screen.getProjektBean().getProjekte().get
                                (screen.getProjektBean().getCurrentProjectIndex());
//                        final ProjektmanagementScreensBean projektmanagementScreensBean = screen.getProjektmanagementScreensBean();
//                        final DaoFactoryBean baseDaoFactoryBean = projektmanagementScreensBean.getDaoFactoryBean();
//                        final RessourceGroupDAO ressourceGroupDAO = baseDaoFactoryBean.getRessourceGroupDAO();
//                        final List<RessourceGroup> ressourceGroups = ressourceGroupDAO.loadAllEntities();
                        projekt.getPlanningUnitGroups().add(planningUnitGroup);
                        AddPlanningUnitGroupWindow.this.close();
                        ui.setWorkingArea(new ProjektplanungScreen(ui));
                    } catch (FieldGroup.CommitException e) {
                        logger.warn(e);
                    }

                } else {
                    final Label lbl = new Label();
                    lbl.setValue(messages.getString("fillInAllFields"));
                    AddPlanningUnitGroupWindow.this.addComponent(lbl);
                }

            }

        });

        cancelButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                AddPlanningUnitGroupWindow.this.close();
            }
        });

    }

    public void show() {
        ui.addWindow(this);
    }
}
