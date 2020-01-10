package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.ui.EditableRapidPanel;

import java.util.List;
import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 18.09.12
 * Time: 09:12
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class CurrentProjectEditableRapidPanel extends EditableRapidPanel {


    private ComboBox<PlannedProject> currentProjectBox;
//    private CurrentProjectPanelBean bean;


    public CurrentProjectEditableRapidPanel(final ResourceBundle messagesBundle){
        super(messagesBundle);
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();

//        setText(messagesBundle.getString("project_currentproject"));
        final List<PlannedProject> projects = daoFactory.getPlannedProjectDAO().loadAllEntities();
        if(projects.isEmpty()){
            add(new Label(messagesBundle.getString("project_noprojects")));
        } else {
            currentProjectBox = new ComboBox<>(messagesBundle.getString("project_currentproject"), projects);
            currentProjectBox.setItemLabelGenerator(PlannedProject::getProjektName);
            formLayout.add(currentProjectBox);
            final VaadinSession session = VaadinSession.getCurrent();
            final PlannedProject currentProject = session.getAttribute(PlannedProject.class);
            if(currentProject != null){
                currentProjectBox.setValue(currentProject);
            }
            buttonsLayout.add(saveButton);
            buttonsLayout.add(cancelButton);
            activate(false);

            cancelButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> activate(false));
            saveButton.addClickListener(event -> {
                session.setAttribute(PlannedProject.class, currentProjectBox.getValue());
                activate(false);
            });
            setComponents();
        }

    }

    @Override
    public void activate(boolean b) {
        buttonsLayout.setVisible(b);
        currentProjectBox.setReadOnly(!b);
    }

    @Override
    public void doInternationalization() {
        cancelButton.setText(messagesBundle.getString("cancel"));
        saveButton.setText(messagesBundle.getString("save"));
    }

    @Override
    public void setComponents() {
        add(formLayout);
        add(buttonsLayout);
    }
}
