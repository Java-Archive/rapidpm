package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.server.VaadinSession;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.descriptionandtestcases.DescriptionAndTestCasesFieldGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.details.PlanningDetailsEditableLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.information.PlanningInformationEditableLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all.PlanningUnitsTree;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.ressources.PlanningRessourcesEditableLayout;

import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Marco Ebbinghaus
 * Date: 24.08.12
 * Time: 13:35
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class TreeValueChangeListener implements ComponentEventListener<ItemClickEvent<PlanningUnit>> {

    private static final String RESSOURCE_GROUPS = "RessourceGroups";
    private static final Logger logger = Logger.getLogger(TreeValueChangeListener.class);

    private ProjektplanungScreen screen;
    private DaoFactory daoFactory;
    private Button deleteButton;
    private Button renameButton;

    public TreeValueChangeListener(final ProjektplanungScreen screen) {
        this.screen = screen;
        this.daoFactory = DaoFactorySingelton.getInstance();
    }

    public void setDeleteButton(final Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    public void setRenameButton(final Button renameButton){
        this.renameButton = renameButton;
    }

    @Override
    public void onComponentEvent(ItemClickEvent<PlanningUnit> planningUnitItemClickEvent) {
        final PlanningUnit selectedParentPlanningUnit = planningUnitItemClickEvent.getItem();
        if (selectedParentPlanningUnit != null) {
            System.out.println(selectedParentPlanningUnit.toString());
            final boolean hasChildren = selectedParentPlanningUnit.getKindPlanningUnits() != null && !selectedParentPlanningUnit.getKindPlanningUnits().isEmpty();
                if(deleteButton != null){
                    deleteButton.setEnabled(true);
                }
                if(renameButton != null){
                    renameButton.setEnabled(true);
                }
                final RapidPanel detailPanel = screen.getDetailsPanel();
                final RapidPanel mainPanel = screen.getMainPanel();
                final RapidPanel ressourcesPanel = screen.getRessourcesPanel();
                final RapidPanel descriptionsAndTestCasesPanel = screen.getRightColumn();
                detailPanel.removeAllComponents();
                mainPanel.removeAllComponents();
                ressourcesPanel.removeAllComponents();
                descriptionsAndTestCasesPanel.removeAllComponents();
                descriptionsAndTestCasesPanel.add(screen.getAddDescriptionOrTestCaseButton());


                detailPanel.add(new Label(selectedParentPlanningUnit.getPlanningUnitName()));
                mainPanel.setText(selectedParentPlanningUnit.getPlanningUnitName());
                ressourcesPanel.setText(RESSOURCE_GROUPS);
                final VerticalLayout detailsPanelComponentsLayout = new PlanningDetailsEditableLayout
                        (selectedParentPlanningUnit, screen, detailPanel);
                final VerticalLayout mainPanelLayout = new PlanningInformationEditableLayout(selectedParentPlanningUnit, screen);
                if(daoFactory.getPlanningUnitDAO().findByID(selectedParentPlanningUnit.getId()) != null){
                    final VerticalLayout ressourcesPanelLayout = new PlanningRessourcesEditableLayout(selectedParentPlanningUnit,
                            screen, ressourcesPanel, hasChildren);
                    ressourcesPanel.add(ressourcesPanelLayout);
                    final DescriptionAndTestCasesFieldGroup descriptionAndTestCasesFieldGroup = new
                            DescriptionAndTestCasesFieldGroup(screen, VaadinSession.getCurrent().getAttribute(ResourceBundle.class),
                            screen.getPlanningUnitsTree().getSelectedItems().iterator().next());
                    final RapidPanel descriptionTabFramePanel = new RapidPanel();
                    final RapidPanel testCaseTabFramePanel = new RapidPanel();
//                    descriptionTabFramePanel.setStyleName(Reindeer.PANEL_LIGHT);
//                    descriptionTabFramePanel.getContentLayout().setMargin(false);
//                    testCaseTabFramePanel.setStyleName(Reindeer.PANEL_LIGHT);
//                    testCaseTabFramePanel.getContentLayout().setMargin(false);
                    for (final RapidPanel descriptionEditableLayout : descriptionAndTestCasesFieldGroup
                            .getDescriptionRapidPanels()) {
                        descriptionTabFramePanel.add(descriptionEditableLayout);
                    }
                    for (final RapidPanel testCaseEditableLayout : descriptionAndTestCasesFieldGroup
                            .getTestcaseRapidPanels()) {
                        testCaseTabFramePanel.add(testCaseEditableLayout);
                    }
                    final Tabs tabSheet = new Tabs();
                    tabSheet.add(new Tab(VaadinSession.getCurrent().getAttribute(ResourceBundle.class).getString("planning_descriptions")));
                    tabSheet.add(new Tab(VaadinSession.getCurrent().getAttribute(ResourceBundle.class).getString("planning_testcases")));
                    screen.setTabs(tabSheet);
                    descriptionsAndTestCasesPanel.add(screen.getTabs());
                }
                detailPanel.removeAllComponents();
                detailPanel.add(detailsPanelComponentsLayout);
                mainPanel.add(mainPanelLayout);
        } else {
            if(deleteButton != null){
                deleteButton.setEnabled(false);
            }
            if(renameButton != null){
                renameButton.setEnabled(false);
            }
        }
    }
}
