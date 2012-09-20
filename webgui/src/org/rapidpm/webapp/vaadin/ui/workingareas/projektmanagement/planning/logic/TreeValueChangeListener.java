package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic;

import com.vaadin.data.Property;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.PlanningDetailsMyFormLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.PlanningMainMyFormLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.PlanningRessourcesMyFormLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.Projekt;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Marco
 * Date: 24.08.12
 * Time: 13:35
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class TreeValueChangeListener implements Property.ValueChangeListener {

    private static final String RESSOURCE_GROUPS = "RessourceGroups";
    private static final Logger logger = Logger.getLogger(TreeValueChangeListener.class);

    private ProjektplanungScreen screen;
    private Projekt projekt;

    public TreeValueChangeListener(final ProjektplanungScreen screen, final Projekt projekt) {
        this.screen = screen;
        this.projekt = projekt;
    }

    @Override
    public void valueChange(final Property.ValueChangeEvent valueClickEvent) {
        if (valueClickEvent.getProperty().getValue() != null) {
            final Object selectedId = valueClickEvent.getProperty().getValue();
            final boolean hasChildren = ((Tree) valueClickEvent.getProperty()).hasChildren(selectedId);
            if (selectedId != null) {
                final Tree treePanelTree = screen.getTreePanelTree();
                final Object itemNameObject = treePanelTree.getItem(selectedId).getItemProperty("name").getValue();
                final String itemName = itemNameObject.toString();
                final Panel detailPanel = screen.getDetailPanel();
                final Panel mainPanel = screen.getMainPanel();
                final Panel ressourcesPanel = screen.getRessourcesPanel();

                detailPanel.removeAllComponents();
                mainPanel.removeAllComponents();
                ressourcesPanel.removeAllComponents();

                detailPanel.addComponent(new Label(itemName));
                mainPanel.setCaption(itemName);
                ressourcesPanel.setCaption(RESSOURCE_GROUPS);

                final ArrayList<PlanningUnit> planningUnitList = new ArrayList<>();

                boolean isGroup = false;
                for (final PlanningUnit planningUnit : projekt.getPlanningUnits()) {
                    if (planningUnit.getPlanningUnitName().equals(itemName)) {
                        isGroup = true;
                        final IssueBase issueBase = planningUnit.getIssueBase();
                        final VerticalLayout detailsPanelComponentsLayout = new PlanningDetailsMyFormLayout(issueBase,
                                screen, detailPanel);
                        final VerticalLayout mainPanelLayout = new PlanningMainMyFormLayout(issueBase, screen, mainPanel);
                        final VerticalLayout ressourcesPanelLayout = new PlanningRessourcesMyFormLayout(planningUnit,
                                screen, ressourcesPanel);
                        detailPanel.addComponent(detailsPanelComponentsLayout);
                        mainPanel.addComponent(mainPanelLayout);
                        ressourcesPanel.addComponent(ressourcesPanelLayout);
                        break;
                    }
                }
                if (!isGroup) {
                    for (final PlanningUnit planningUnit2 : projekt.getPlanningUnits()) {
                        projekt.findPlanningUnitAndWriteReferenceInList(planningUnit2.getKindPlanningUnits(), itemName,
                                planningUnitList);
                    }
                    final PlanningUnit selectedPlanningUnit = planningUnitList.get(0);
                    final VerticalLayout detailsPanelComponentsLayout = new PlanningDetailsMyFormLayout
                            (selectedPlanningUnit.getIssueBase(), screen, detailPanel);
                    final VerticalLayout mainPanelLayout = new PlanningMainMyFormLayout(selectedPlanningUnit.getIssueBase(),
                            screen, mainPanel);
                    final VerticalLayout ressourcesPanelLayout = new PlanningRessourcesMyFormLayout(selectedPlanningUnit,
                            screen, ressourcesPanel, hasChildren);
                    detailPanel.addComponent(detailsPanelComponentsLayout);
                    mainPanel.addComponent(mainPanelLayout);
                    ressourcesPanel.addComponent(ressourcesPanelLayout);
                }
            } else {
                logger.warn("nullselection");
            }
        }

    }
}
