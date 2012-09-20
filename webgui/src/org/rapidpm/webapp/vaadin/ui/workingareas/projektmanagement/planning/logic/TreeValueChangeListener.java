package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.PlanningDetailsMyFormLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.PlanningMainMyFormLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.PlanningRessourcesMyFormLayout;

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
    private PlannedProject projekt;

    public TreeValueChangeListener(final ProjektplanungScreen screen, final PlannedProject projekt) {
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
                final BeanItem<PlanningUnit> selectedPlanningUnitBeanItem = (BeanItem) treePanelTree.getItem
                        (selectedId);
                final PlanningUnit selectedPlanningUnit = selectedPlanningUnitBeanItem.getBean();
                final Panel detailPanel = screen.getDetailPanel();
                final Panel mainPanel = screen.getMainPanel();
                final Panel ressourcesPanel = screen.getRessourcesPanel();

                detailPanel.removeAllComponents();
                mainPanel.removeAllComponents();
                ressourcesPanel.removeAllComponents();

                detailPanel.addComponent(new Label(selectedPlanningUnit.getPlanningUnitName()));
                mainPanel.setCaption(selectedPlanningUnit.getPlanningUnitName());
                ressourcesPanel.setCaption(RESSOURCE_GROUPS);

                final ArrayList<PlanningUnit> planningUnitList = new ArrayList<>();

                    final VerticalLayout detailsPanelComponentsLayout = new PlanningDetailsMyFormLayout
                            (selectedPlanningUnit.getIssueBase(), screen, detailPanel);
                    final VerticalLayout mainPanelLayout = new PlanningMainMyFormLayout(selectedPlanningUnit.getIssueBase(),
                            screen, mainPanel);
                    final VerticalLayout ressourcesPanelLayout = new PlanningRessourcesMyFormLayout(selectedPlanningUnit,
                            screen, ressourcesPanel, hasChildren);
                    detailPanel.addComponent(detailsPanelComponentsLayout);
                    mainPanel.addComponent(mainPanelLayout);
                    ressourcesPanel.addComponent(ressourcesPanelLayout);
                
            } else {
                logger.warn("nullselection");
            }
        }

    }
}
