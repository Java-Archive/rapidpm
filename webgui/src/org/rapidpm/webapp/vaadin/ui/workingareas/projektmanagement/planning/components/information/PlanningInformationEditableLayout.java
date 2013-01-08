package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.information;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.EditableLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all.PlanningUnitFieldGroup;

import java.util.*;

import static org.rapidpm.Constants.COMMIT_EXCEPTION_MESSAGE;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 30.08.12
 * Time: 09:15
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningInformationEditableLayout extends EditableLayout {

    //TODO Testcases managen
    private static final Logger logger = Logger.getLogger(PlanningInformationEditableLayout.class);

    private ResourceBundle messages;
    private List<AbstractField> fieldList;
    private PlanningUnitFieldGroup fieldGroup;

    public PlanningInformationEditableLayout(final PlanningUnit planningUnit, final ProjektplanungScreen screen,
                                           final Panel screenPanel) {
        super(screen, screenPanel);
        this.messages = screen.getMessagesBundle();

        fieldGroup = new PlanningUnitFieldGroup(screen, planningUnit);
        fieldList = fieldGroup.getFieldList();

        buildForm();

        cancelButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                final Iterator<Component> componentIterator = componentsLayout.getComponentIterator();
                fieldGroup.discard();
                while (componentIterator.hasNext()) {
                    final Component component = componentIterator.next();
                    if (component instanceof Field) {
                        component.setReadOnly(true);
                    }
                }
                buttonLayout.setVisible(false);
            }
        });

        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try{
                    fieldGroup.commit();
                    final BeanItem<PlanningUnit> beanItem = (BeanItem)fieldGroup.getItemDataSource();
                    final PlanningUnit editedPlanningUnit = beanItem.getBean();
                    final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
                    daoFactory.saveOrUpdateTX(editedPlanningUnit);
                    final MainUI ui = screen.getUi();
                    ui.setWorkingArea(new ProjektplanungScreen(ui));
                }catch (NullPointerException e){
                    logger.info(COMMIT_EXCEPTION_MESSAGE);
                }catch(Exception e){
                    logger.warn("Exception", e);
                }
            }
        });
    }

    @Override
    protected void buildForm() {
        //setReadOnly(true) veursacht javascriptexception seit beta10
        for(final AbstractField field : fieldList){
            field.setReadOnly(false);
            if(field instanceof AbstractSelect){
                ((ComboBox)field).setNullSelectionAllowed(false);
                ((ComboBox)field).setTextInputAllowed(false);
            }
        }
    }

    @Override
    protected void setLayout() {
        componentsLayout = new FormLayout();
    }
}
