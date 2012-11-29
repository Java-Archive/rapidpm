package org.rapidpm.webapp.vaadin.ui.workingareas.controlling;

import com.vaadin.ui.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.componnents.tabsheet.ControllingDataTabSheet;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 05.11.12
 * Time: 11:19
 * To change this template use File | Settings | File Templates.
 */
public class ControllingScreen extends Screen {

    private final HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
//    private final Tree projectStructureTree;
    private ControllingDataTabSheet controllingTabSheet;
    private  final VerticalLayout testButtonLayout;

    public ControllingScreen(final MainUI ui) {
        super(ui);

//        projectStructureTree = new Tree("Projectstrutcture");
        controllingTabSheet = new ControllingDataTabSheet();

        testButtonLayout = new VerticalLayout();
        initTestButtons();

        splitPanel.setSizeFull();
        splitPanel.setSplitPosition(40, Unit.PERCENTAGE);

//        splitPanel.setFirstComponent(projectStructureTree);
        splitPanel.setFirstComponent(testButtonLayout);
        splitPanel.setSecondComponent(controllingTabSheet);

        splitPanel.setHeight(String.valueOf(ui.getHeight()));

        setComponents();
    }

    private void initTestButtons() {
        Button plannedProjectButton = new Button("PlannedProjectDemoDaten");
        plannedProjectButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                controllingTabSheet.setSelectedControllingClass(PlannedProject.class);
            }
        });
        testButtonLayout.addComponent(plannedProjectButton);

        Button planningUnitButton = new Button("PlanningUnit");
        planningUnitButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                controllingTabSheet.setSelectedControllingClass(PlanningUnit.class);
            }
        });
        testButtonLayout.addComponent(planningUnitButton);

        Button issueBaseButton = new Button("IssueBase");
        issueBaseButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                controllingTabSheet.setSelectedControllingClass(IssueBase.class);
            }
        });
        testButtonLayout.addComponent(issueBaseButton);
    }

    @Override
    public void setComponents() {
        addComponent(splitPanel);
    }

    @Override
    public void doInternationalization() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
