package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.ui.EditableRapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.ProjectAdministrationScreen;

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
    private ProjectAdministrationScreen screen;
//    private CurrentProjectPanelBean bean;


    public CurrentProjectEditableRapidPanel(final ResourceBundle messagesBundle){
        super(messagesBundle);
//        bean = EJBFactory.getEjbInstance(CurrentProjectPanelBean.class);
//        final DaoFactoryBean baseDaoFactoryBean = bean.getDaoFactoryBean();
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();

//        setText(messagesBundle.getString("project_currentproject"));
        final List<PlannedProject> projects = daoFactory.getPlannedProjectDAO().loadAllEntities();
        if(projects.isEmpty()){
            add(new Label(messagesBundle.getString("project_noprojects")));
        } else {
            currentProjectBox = new ComboBox<>("", projects);
//            currentProjectBox.setNullSelectionAllowed(false);
//            currentProjectBox.setTextInputAllowed(false);
//            currentProjectBox.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
//            currentProjectBox.setItemCaptionPropertyId(PlannedProject.NAME);
            currentProjectBox.setItemLabelGenerator(PlannedProject::getProjektName);
            final VaadinSession session = VaadinSession.getCurrent();
            final PlannedProject currentProject = session.getAttribute(PlannedProject.class);
            if(currentProject != null){
                currentProjectBox.setValue(currentProject);
            }
            buttonsLayout.add(saveButton);
            buttonsLayout.add(cancelButton);
            activate(false);

//            cancelButton.addClickListener(new Button.ClickListener() {
//                @Override
//                public void buttonClick(Button.ClickEvent event) {
//                    activate(false);
//                }
//            });
//            saveButton.addClickListener(new Button.ClickListener() {
//                @Override
//                public void buttonClick(Button.ClickEvent event) {
//                    session.setAttribute(PlannedProject.class, (PlannedProject)currentProjectBox.getValue());
//                    activate(false);
//                }
//            });
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
        add(currentProjectBox);
        add(buttonsLayout);
    }
}
