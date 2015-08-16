package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components;

import com.vaadin.data.util.BeanItem;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.EditableLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ProjektFieldGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.AufwandProjInitScreen;

import static org.rapidpm.Constants.COMMIT_EXCEPTION_MESSAGE;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 05.02.13
 * Time: 12:59
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class HoursPerWorkingDayEditableLayout extends EditableLayout {

    private ProjektFieldGroup fieldGroup;
    private ComboBox hoursBox;
    private PlannedProject project;

    private static final Logger logger = Logger.getLogger(HoursPerWorkingDayEditableLayout.class);
    public HoursPerWorkingDayEditableLayout(final Screen screen, final Panel screenPanel) {
        super(screen, screenPanel);
        final VaadinSession session = screen.getUi().getSession();
        project = session.getAttribute(PlannedProject.class);
        fieldGroup = new ProjektFieldGroup(project, messages);
        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try{
                    final BeanItem<PlannedProject> beanItem = (BeanItem)fieldGroup.getItemDataSource();
                    final PlannedProject editedPlannedProject = beanItem.getBean();
                    final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
                    fieldGroup.commit();
//                    daoFactory.saveOrUpdateTX(editedPlannedProject);
                    final MainUI ui = screen.getUi();
                    ui.setWorkingArea(new AufwandProjInitScreen(ui));
                }catch (final NullPointerException e){
                    logger.info(COMMIT_EXCEPTION_MESSAGE);
                }catch(final Exception e){
                    logger.warn("Exception", e);
                }

            }
        });
        cancelButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                fieldGroup.discard();
                hoursBox = (ComboBox) fieldGroup.getField(PlannedProject.HOURSPERWORKINGDAY);
                hoursBox.setReadOnly(true);
                componentsLayout.addComponent(hoursBox);
                hoursBox.setReadOnly(true);
                buttonLayout.setVisible(false);
            }
        });
        buildForm();
    }

    @Override
    protected void buildForm() {
        hoursBox = (ComboBox) fieldGroup.getField(PlannedProject.HOURSPERWORKINGDAY);
        hoursBox.setReadOnly(true);
        componentsLayout.addComponent(hoursBox);
    }

    @Override
    protected void setLayout() {
        componentsLayout = new FormLayout();
    }
}
