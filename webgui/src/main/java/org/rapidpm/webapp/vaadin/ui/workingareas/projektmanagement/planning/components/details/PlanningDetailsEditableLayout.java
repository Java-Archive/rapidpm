package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.details;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.server.VaadinSession;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.EditableLayout;

import java.util.Iterator;
import java.util.List;
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

    private static final Logger logger = Logger.getLogger(PlanningDetailsEditableLayout.class);

    private List<AbstractField> fieldList;
    private PlanningDetailsFieldGroup fieldGroup;
    private ResourceBundle messages;

    public PlanningDetailsEditableLayout(final PlanningUnit planningUnit, final ProjektplanungScreen screen,
                                         final Component screenPanel) {
        super(null, screenPanel);
        messages = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);

        fieldGroup = new PlanningDetailsFieldGroup(messages, planningUnit);
//        fieldList = fieldGroup.getFieldList();

        buildForm();

//        cancelButton.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                final Iterator<Component> componentIterator = componentsLayout.iterator();
//                fieldGroup.discard();
//                while (componentIterator.hasNext()) {
//                    final Component component = componentIterator.next();
//                    if (component instanceof Field) {
//                        component.setReadOnly(true);
//                    }
//                }
//                buttonLayout.setVisible(false);
//            }
//        });
//        saveButton.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                try{
//                    fieldGroup.commit();
//                    final BeanItem<PlanningUnit> beanItem = (BeanItem)fieldGroup.getItemDataSource();
//                    final PlanningUnit editedPlanningUnit = beanItem.getBean();
//                    final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
//                    daoFactory.saveOrUpdateTX(editedPlanningUnit);
//                    final MainUI ui = screen.getUi();
//                    ui.setWorkingArea(new ProjektplanungScreen(ui));
//                }catch (final NullPointerException e){
//                    logger.info(COMMIT_EXCEPTION_MESSAGE);
//                }catch(final Exception e){
//                    logger.warn("Exception", e);
//                }
//            }
//        });
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
//        componentsLayout.add(fieldGroup.getField(PlanningUnit.RESPONSIBLE));
//        componentsLayout.add(fieldGroup.getField(PlanningUnit.COMPLEXITY));
//        componentsLayout.add(fieldGroup.getField(PlanningUnit.ORDERNUMBER));
//        componentsLayout.add(fieldGroup.getField(PlanningUnit.STORYPTS));
    }

    @Override
    protected void setLayout() {
        componentsLayout = new FormLayout();
    }


}
