package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic;

import com.vaadin.data.Property;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.descriptionandtestcases.DescriptionAndTestCasesFieldGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.details.PlanningDetailsEditableLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.information.PlanningInformationEditableLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.ressources.PlanningRessourcesEditableLayout;

/**
 * Created with IntelliJ IDEA.
 * User: Marco Ebbinghaus
 * Date: 24.08.12
 * Time: 13:35
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class TreeValueChangeListener implements Property.ValueChangeListener {

    private static final String RESSOURCE_GROUPS = "RessourceGroups";
    private static final Logger logger = Logger.getLogger(TreeValueChangeListener.class);

    private ProjektplanungScreen screen;
    private DaoFactory daoFactory;
    private Button deleteButton;
    private Button renameButton;

    public TreeValueChangeListener(final ProjektplanungScreen screen) {
        this.screen = screen;
        this.daoFactory = DaoFactorySingleton.getInstance();
    }

    @Override
    public void valueChange(final Property.ValueChangeEvent valueClickEvent) {
        if (valueClickEvent.getProperty().getValue() != null) {
            final Object selectedId = valueClickEvent.getProperty().getValue();
            System.out.println(selectedId.toString());
            final boolean hasChildren = ((Tree) valueClickEvent.getProperty()).hasChildren(selectedId);
            if (selectedId != null) {
                if(deleteButton != null){
                    deleteButton.setEnabled(true);
                }
                if(renameButton != null){
                    renameButton.setEnabled(true);
                }
                final PlanningUnit selectedParentPlanningUnit = (PlanningUnit) selectedId;
                final RapidPanel detailPanel = screen.getDetailsPanel();
                final RapidPanel mainPanel = screen.getMainPanel();
                final RapidPanel ressourcesPanel = screen.getRessourcesPanel();
                final RapidPanel descriptionsAndTestCasesPanel = screen.getRightColumn();
                detailPanel.removeAllComponents();
                mainPanel.removeAllComponents();
                ressourcesPanel.removeAllComponents();
                descriptionsAndTestCasesPanel.removeAllComponents();
                descriptionsAndTestCasesPanel.addComponent(screen.getAddDescriptionOrTestCaseButton());


                detailPanel.addComponent(new Label(selectedParentPlanningUnit.getPlanningUnitName()));
                mainPanel.setCaption(selectedParentPlanningUnit.getPlanningUnitName());
                ressourcesPanel.setCaption(RESSOURCE_GROUPS);
                final VerticalLayout detailsPanelComponentsLayout = new PlanningDetailsEditableLayout
                        (selectedParentPlanningUnit, screen, detailPanel);
                final VerticalLayout mainPanelLayout = new PlanningInformationEditableLayout(selectedParentPlanningUnit,
                        screen, mainPanel);
                if(daoFactory.getPlanningUnitDAO().findByID(selectedParentPlanningUnit.getId()) != null){
                    final VerticalLayout ressourcesPanelLayout = new PlanningRessourcesEditableLayout(selectedParentPlanningUnit,
                            screen, ressourcesPanel, hasChildren);
                    ressourcesPanel.addComponent(ressourcesPanelLayout);
                    final DescriptionAndTestCasesFieldGroup descriptionAndTestCasesFieldGroup = new
                            DescriptionAndTestCasesFieldGroup(screen, screen.getMessagesBundle(),
                            (PlanningUnit)screen.getPlanningUnitsTree().getValue());
                    final RapidPanel descriptionTabFramePanel = new RapidPanel();
                    final RapidPanel testCaseTabFramePanel = new RapidPanel();
                    descriptionTabFramePanel.setStyleName(Reindeer.PANEL_LIGHT);
                    descriptionTabFramePanel.getContentLayout().setMargin(false);
                    testCaseTabFramePanel.setStyleName(Reindeer.PANEL_LIGHT);
                    testCaseTabFramePanel.getContentLayout().setMargin(false);
                    for (final RapidPanel descriptionEditableLayout : descriptionAndTestCasesFieldGroup
                            .getDescriptionRapidPanels()) {
                        descriptionTabFramePanel.addComponent(descriptionEditableLayout);
                    }
                    for (final RapidPanel testCaseEditableLayout : descriptionAndTestCasesFieldGroup
                            .getTestcaseRapidPanels()) {
                        testCaseTabFramePanel.addComponent(testCaseEditableLayout);
                    }
                    final TabSheet tabSheet = new TabSheet();
                    tabSheet.addTab(descriptionTabFramePanel, screen.getMessagesBundle().getString("planning_descriptions"));
                    tabSheet.addTab(testCaseTabFramePanel, screen.getMessagesBundle().getString("planning_testcases"));
                    screen.setTabSheet(tabSheet);
                    descriptionsAndTestCasesPanel.addComponent(screen.getTabSheet());
                }
                detailPanel.removeAllComponents();
                detailPanel.addComponent(detailsPanelComponentsLayout);
                mainPanel.addComponent(mainPanelLayout);
            } else {
                logger.warn("nullselection");
            }
        } else {
            if(deleteButton != null){
                deleteButton.setEnabled(false);
            }
            if(renameButton != null){
                renameButton.setEnabled(false);
            }
        }
    }

    public void setDeleteButton(final Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    public void setRenameButton(final Button renameButton){
        this.renameButton = renameButton;
    }
}
