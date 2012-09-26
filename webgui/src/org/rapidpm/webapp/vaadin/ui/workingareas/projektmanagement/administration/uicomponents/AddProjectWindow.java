package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroupDAO;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.ProjektmanagementScreensBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.ProjectAdministrationScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.Projekt;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;

import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 18.09.12
 * Time: 15:45
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class AddProjectWindow extends Window{

    public static final String HEIGHT = "400px";
    public static final String WIDTH = "400px";
    public static final int POSITION_X = 50;
    public static final int POSITION_Y = 100;

    private static final Logger logger = Logger.getLogger(AddProjectWindow.class);

    private MainUI ui;

    private FormLayout formLayout = new FormLayout();
    private HorizontalLayout horizontalButtonLayout = new HorizontalLayout();
    private Button saveButton = new Button();
    private Button cancelButton = new Button();
    private ProjektFieldGroup fieldGroup;
    private ResourceBundle messages;
    public ProjectAdministrationScreen screen;

    public AddProjectWindow(final MainUI ui, final ProjectAdministrationScreen screen) {
        this.ui = ui;
        this.screen = screen;
        messages = screen.getMessagesBundle();
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setPositionX(POSITION_X);
        setPositionY(POSITION_Y);

        final Projekt projekt = new Projekt();
        fieldGroup = new ProjektFieldGroup(projekt);

        fillFormLayout();
        addComponent(formLayout);

        horizontalButtonLayout.addComponent(saveButton);
        horizontalButtonLayout.addComponent(cancelButton);

        addComponent(horizontalButtonLayout);

        addListeners(projekt, ui);
        doInternationalization();

    }

    private void fillFormLayout() {
        for (final Object propertyId : fieldGroup.getBoundPropertyIds()) {
            TextField field = (TextField)fieldGroup.getField(propertyId);
            field.setReadOnly(false);
            formLayout.addComponent(field);
        }
    }

    private void doInternationalization() {
        this.setCaption(messages.getString("pm_addproject"));
        saveButton.setCaption(messages.getString("saveOrUpdate"));
        cancelButton.setCaption(messages.getString("cancel"));
    }

    private void addListeners(Projekt proj, final MainUI ui) {
        final Projekt projekt = proj;
        saveButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                boolean allFilled = true;
                Iterator<Component> it = AddProjectWindow.this.formLayout
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
                        final ProjektBean projektBean = screen.getProjektBean();
                        final ProjektmanagementScreensBean projektmanagementScreensBean = screen.getProjektmanagementScreensBean();
                        final DaoFactoryBean baseDaoFactoryBean = projektmanagementScreensBean.getDaoFactoryBean();
                        final RessourceGroupDAO ressourceGroupDAO = baseDaoFactoryBean.getRessourceGroupDAO();
                        final List<RessourceGroup> ressourceGroups = ressourceGroupDAO.loadAllEntities();
                        projektBean.addNewProject(ressourceGroups, projekt.getProjektName(), projekt.getProjektId());
                        AddProjectWindow.this.close();
                        ui.setWorkingArea(new ProjectAdministrationScreen(ui));
                    } catch (FieldGroup.CommitException e) {
                        logger.warn(e);
                    }

                } else {
                    final Label lbl = new Label();
                    lbl.setValue(messages.getString("stdsatz_fillInAllFields"));
                    AddProjectWindow.this.addComponent(lbl);
                }

            }

        });

        cancelButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                AddProjectWindow.this.close();
            }
        });

    }

    public void show() {
        ui.addWindow(this);
    }
}
