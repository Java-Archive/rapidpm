package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.ui.EditableLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ProjektFieldGroup;

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

    public HoursPerWorkingDayEditableLayout() {
        final VaadinSession session = VaadinSession.getCurrent();
        project = session.getAttribute(PlannedProject.class);
        fieldGroup = new ProjektFieldGroup(project, messages);
//        saveButton.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                try{
//                    final BeanItem<PlannedProject> beanItem = (BeanItem)fieldGroup.getItemDataSource();
//                    final PlannedProject editedPlannedProject = beanItem.getBean();
//                    final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
//                    fieldGroup.commit();
//                    daoFactory.saveOrUpdateTX(editedPlannedProject);
//                    final MainUI ui = screen.getUi();
//                    ui.setWorkingArea(new AufwandProjInitScreen(ui));
//                }catch (final NullPointerException e){
//                    logger.info(COMMIT_EXCEPTION_MESSAGE);
//                }catch(final Exception e){
//                    logger.warn("Exception", e);
//                }
//
//            }
//        });
//        cancelButton.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                fieldGroup.discard();
//                hoursBox = (ComboBox) fieldGroup.getField(PlannedProject.HOURSPERWORKINGDAY);
//                hoursBox.setReadOnly(true);
//                componentsLayout.add(hoursBox);
//                hoursBox.setReadOnly(true);
//                buttonLayout.setVisible(false);
//            }
//        });
        buildForm();
    }

    @Override
    protected void buildForm() {
//        hoursBox = (ComboBox) fieldGroup.ge(PlannedProject.HOURSPERWORKINGDAY);
//        hoursBox.setReadOnly(true);
//        componentsLayout.add(hoursBox);
    }

    @Override
    protected void setLayout() {
        componentsLayout = new FormLayout();
    }
}
