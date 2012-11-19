package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.details.PlanningDetailsEditableLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.information.PlanningInformationEditableLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all.PlanningUnitsTree;
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
    private PlannedProject projekt;
    private DaoFactory daoFactory;

    public TreeValueChangeListener(final ProjektplanungScreen screen, final PlannedProject projekt) {
        this.screen = screen;
        this.projekt = projekt;
        this.daoFactory = DaoFactorySingelton.getInstance();
    }

    @Override
    public void valueChange(final Property.ValueChangeEvent valueClickEvent) {
        if (valueClickEvent.getProperty().getValue() != null) {
            final Object selectedId = valueClickEvent.getProperty().getValue();
            final boolean hasChildren = ((Tree) valueClickEvent.getProperty()).hasChildren(selectedId);
            if (selectedId != null) {
                final PlanningUnitsTree tree = screen.getPlanningUnitsTree();
                final BeanItem<PlanningUnit> selectedPlanningUnitBeanItem = (BeanItem) tree.getItem
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
                final VerticalLayout detailsPanelComponentsLayout = new PlanningDetailsEditableLayout
                        (selectedPlanningUnit, screen, detailPanel);
                final VerticalLayout mainPanelLayout = new PlanningInformationEditableLayout(selectedPlanningUnit,
                        screen, mainPanel);
                if(daoFactory.getPlanningUnitDAO().findByID(selectedPlanningUnit.getId()) != null){
                    final VerticalLayout ressourcesPanelLayout = new PlanningRessourcesEditableLayout(selectedPlanningUnit,
                            screen, ressourcesPanel, hasChildren);
                    ressourcesPanel.addComponent(ressourcesPanelLayout);
                }
                detailPanel.removeAllComponents();
                detailPanel.addComponent(detailsPanelComponentsLayout);
                mainPanel.addComponent(mainPanelLayout);

            } else {
                logger.warn("nullselection");
            }
        }

    }
}
