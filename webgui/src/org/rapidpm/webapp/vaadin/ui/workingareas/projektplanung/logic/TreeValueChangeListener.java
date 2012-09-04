package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.logic;

import com.vaadin.data.Property;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.PlanningUnitGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.Projekt;

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

    private ProjektplanungScreen screen;
    private Projekt projekt;

    public TreeValueChangeListener(ProjektplanungScreen screen, Projekt projekt){
        this.screen = screen;
        this.projekt = projekt;
    }

    @Override
    public void valueChange(final Property.ValueChangeEvent valueClickEvent) {
        if(valueClickEvent.getProperty().getValue() != null){
            final Object selectedId = valueClickEvent.getProperty().getValue();
            final boolean hasChildren = ((Tree)valueClickEvent.getProperty()).hasChildren(selectedId);
            VerticalLayout detailsPanelComponentsLayout = null;
            VerticalLayout mainPanelLayout = null;
            VerticalLayout ressourcesPanelLayout = null;
            PlanningUnit selectedPlanningUnit = new PlanningUnit();
            PlanningUnitGroup selectedPlanningUnitGroup = new PlanningUnitGroup(null);
            boolean isGroup = false;
            if (selectedId != null) {
                final Tree treePanelTree = screen.getTreePanelTree();
                final Object itemName = treePanelTree.getItem(selectedId).getItemProperty("name").getValue();
                final Panel detailPanel = screen.getDetailPanel();
                final Panel mainPanel = screen.getMainPanel();
                final Panel ressourcesPanel = screen.getRessourcesPanel();
                detailPanel.removeAllComponents();
                mainPanel.removeAllComponents();
                ressourcesPanel.removeAllComponents();
                detailPanel.addComponent(new Label(itemName.toString()));
                mainPanel.setCaption(itemName.toString());
                ressourcesPanel.setCaption(RESSOURCE_GROUPS);
                final ArrayList<PlanningUnit> planningUnitList = new ArrayList<>();
                for(final PlanningUnitGroup planningUnitGroup : projekt.getPlanningUnitGroups()){
                    if(planningUnitGroup.getPlanningUnitName().equals(itemName.toString())){
                        isGroup = true;
                        selectedPlanningUnitGroup = planningUnitGroup;
                        detailsPanelComponentsLayout = new PlanningDetailsMyFormLayout(selectedPlanningUnitGroup.getIssueBase(), screen, detailPanel);
                        mainPanelLayout = new PlanningMainMyFormLayout(selectedPlanningUnitGroup.getIssueBase(),screen,mainPanel);
                        ressourcesPanelLayout = new PlanningRessourcesMyFormLayout(selectedPlanningUnitGroup,screen,ressourcesPanel);
                    }
                }
                if(!isGroup){
                    for(final PlanningUnitGroup planningUnitGroup2 : projekt.getPlanningUnitGroups()){
                        projekt.findPlanningUnitAndWriteReferenceInList(planningUnitGroup2.getPlanningUnitList(), itemName.toString(), planningUnitList);
                    }
                    selectedPlanningUnit = planningUnitList.get(0);
                    detailsPanelComponentsLayout = new PlanningDetailsMyFormLayout(selectedPlanningUnit.getIssueBase(), screen, detailPanel);
                    mainPanelLayout = new PlanningMainMyFormLayout(selectedPlanningUnit.getIssueBase(),screen,mainPanel);
                    ressourcesPanelLayout = new PlanningRessourcesMyFormLayout(selectedPlanningUnit,screen,ressourcesPanel, hasChildren);
                }
                detailPanel.addComponent(detailsPanelComponentsLayout);
                mainPanel.addComponent(mainPanelLayout);
                ressourcesPanel.addComponent(ressourcesPanelLayout);
            }
        }

    }
}
