package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits;

import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.details.PlanningDetailsFieldGroup;

import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.COMMIT_EXCEPTION_MESSAGE;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 25.08.12
 * Time: 18:50
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */

public class PlanningUnitsPanelLayout extends HorizontalLayout {

    private static final Logger logger = Logger.getLogger(PlanningUnitsPanelLayout.class);

    private VerticalLayout leftLayout = new VerticalLayout();
    private VerticalLayout rightLayout = new VerticalLayout();

    private HorizontalLayout buttonLayout = new HorizontalLayout();
    private PlanningUnitEditableLayout planningUnitEditableLayout;

    private Button addButton = new Button("+");
    private Button deleteButton = new Button("-");
    private List<AbstractField> fieldList;
    private PlanningDetailsFieldGroup fieldGroup;
    private ResourceBundle messages;


    private ProjektplanungScreen screen;

    public PlanningUnitsPanelLayout(final ProjektplanungScreen screen) {
        super(screen);
        this.screen = screen;
        messages = screen.getMessagesBundle();
        buildForm();
    }

    public void buildEditableLayout(){
        planningUnitEditableLayout = new PlanningUnitEditableLayout((PlanningUnit) screen.getPlanningUnitSelect()
                .getValue(), screen, screen.getPlanningUnitPanel());
    }

    protected void buildForm() {

        buttonLayout.addComponent(addButton);
        buttonLayout.addComponent(deleteButton);

        leftLayout.addComponent(buttonLayout);
        leftLayout.addComponent(screen.getPlanningUnitSelect());

        rightLayout.addComponent(planningUnitEditableLayout);


        addComponent(leftLayout);
        addComponent(rightLayout);

//        for(final AbstractField field : fieldList){
//            field.setReadOnly(true);
//            if(field instanceof AbstractSelect){
//                ((ComboBox)field).setNullSelectionAllowed(false);
//                ((ComboBox)field).setTextInputAllowed(false);
//            }
//        }
//        for(final Field<?> field : fieldGroup.getFields()){
//            componentsLayout.addComponent(field);
//        }
    }

}
