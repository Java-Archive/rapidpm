package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatus;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic.PlanningCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic.TreeValueChangeListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.PlanningUnitBeanItemContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;

import javax.persistence.EntityManager;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.04.12
 * Time: 09:43
 */
public class ProjektplanungScreen extends Screen {

    private HorizontalSplitPanel splitPanel;
    private final VerticalLayout menuLayout;
    private Panel mainPanel;
    private Panel ressourcesPanel;
    private final Panel planningUnitPanel;
    private final Panel treePanel;
    private final Panel detailPanel;
    private final ListSelect projektSelect;
    private final VerticalLayout mainLayout;
    private Tree treePanelTree;
    private ProjektPlanungScreenBean projektplanungScreenBean;
    private final PlanningUnitBeanItemContainer container = new PlanningUnitBeanItemContainer();


    public ProjektplanungScreen(MainUI ui) {
        super(ui);

        projektplanungScreenBean = EJBFactory.getEjbInstance(ProjektPlanungScreenBean.class);
        final DaoFactoryBean daoFactoryBean = projektplanungScreenBean.getDaoFactoryBean();
        final PlannedProject plannedProject = daoFactoryBean.getPlannedProjectDAO().loadAllEntities().get(0);

        final PlanningCalculator calculator = new PlanningCalculator(messagesBundle);
        calculator.calculate();
        splitPanel = new HorizontalSplitPanel();
        splitPanel.setSizeFull();
        splitPanel.setSplitPosition(40, Unit.PERCENTAGE);

        planningUnitPanel = new Panel();
        treePanel = new Panel();
        detailPanel = new Panel();

        menuLayout = new VerticalLayout();
        menuLayout.setSpacing(true);
        menuLayout.addComponent(planningUnitPanel);
        menuLayout.addComponent(treePanel);
        menuLayout.addComponent(detailPanel);

        mainPanel = new Panel();
        ressourcesPanel = new Panel();
        ressourcesPanel.setSizeFull();

        mainLayout = new VerticalLayout();
        mainLayout.setSpacing(true);
        mainLayout.addComponent(ressourcesPanel);
        mainLayout.addComponent(mainPanel);

        splitPanel.addComponent(menuLayout);
        splitPanel.addComponent(mainLayout);


        final List<PlanningUnit> planningUnitList = plannedProject.getPlanningUnits();

        planningUnitPanel.setCaption(plannedProject.getProjektName());
        projektSelect = new ListSelect(null, new BeanItemContainer<>(PlanningUnit.class,planningUnitList));

        projektSelect.setNullSelectionAllowed(false);
        projektSelect.setImmediate(true);
        projektSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        projektSelect.setItemCaptionPropertyId(PlanningUnit.NAME);
        planningUnitPanel.getContent().addComponent(projektSelect);
        projektSelect.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(final Property.ValueChangeEvent valueChangeEvent) {
                final PlanningUnit value = (PlanningUnit) valueChangeEvent.getProperty().getValue();
                treePanel.getContent().removeAllComponents();
                detailPanel.getContent().removeAllComponents();
                treePanel.setCaption(value.getPlanningUnitName());
                fillTreePanel(value, plannedProject);
                treePanelTree.select(value);
            }

        });
        final List<?> ids = (List<?>) projektSelect.getItemIds();
        projektSelect.setValue(ids.get(0));
        doInternationalization();
        setComponents();

    }

    @Override
    public void doInternationalization() {
        detailPanel.setCaption(messagesBundle.getString("details"));
    }

    public void fillTreePanel(PlanningUnit selectedPlanningUnit, PlannedProject projekt) {

        treePanel.removeAllComponents();
        treePanelTree = new Tree();


        if (selectedPlanningUnit != null) {
            treePanelTree.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            treePanelTree.setItemCaptionPropertyId(PlanningUnit.NAME);
            treePanelTree.setImmediate(true);
            container.addBean(selectedPlanningUnit);
//            if (selectedPlanningUnit.getKindPlanningUnits() != null && !selectedPlanningUnit.getKindPlanningUnits().isEmpty()) {
//                treePanelTree.setChildrenAllowed(itemId, true);
//            } else {
//                treePanelTree.setChildrenAllowed(itemId, false);
//            }

            buildTree(selectedPlanningUnit.getKindPlanningUnits(), selectedPlanningUnit);
            treePanelTree.expandItemsRecursively(selectedPlanningUnit);
            treePanelTree.addValueChangeListener(new TreeValueChangeListener(this, projekt));
            treePanelTree.setContainerDataSource(container);
            final Iterator iterator = treePanelTree.rootItemIds().iterator();
            treePanelTree.expandItemsRecursively(iterator.next());
            //TODO Ordner in Constants, Filename in db
            for(final Object itemId : treePanelTree.getVisibleItemIds()){
                final PlanningUnit planningUnit = (PlanningUnit) itemId;
                final IssueBase planningUnitIssueBase = planningUnit.getIssueBase();
                final IssueStatus issueStatus = planningUnitIssueBase.getStatus();
                final String iconPfad = ("images/status_"+issueStatus.getStatusName()+".gif").toLowerCase();
                treePanelTree.setItemIcon(itemId, new ThemeResource(iconPfad));
            }
            treePanel.addComponent(treePanelTree);
        }
    }

    private void buildTree(List<PlanningUnit> planningUnits, PlanningUnit parentUnit) {
        for (final PlanningUnit planningUnit : planningUnits) {
            container.addBean(planningUnit);
            container.setParent(planningUnit, parentUnit);
            if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
                //treePanelTree.setChildrenAllowed(itemId, false);
            } else {
                buildTree(planningUnit.getKindPlanningUnits(), planningUnit);
            }
        }
    }

    @Override
    public void setComponents() {
        addComponent(splitPanel);
    }

    public ListSelect getProjektSelect() {
        return projektSelect;
    }

    public Tree getTreePanelTree() {
        return treePanelTree;
    }

    public Panel getDetailPanel() {
        return detailPanel;
    }

    public Panel getPlanningUnitPanel() {
        return planningUnitPanel;
    }

    public Panel getTreePanel() {
        return treePanel;
    }

    public ProjektBean getProjektBean() {
        return projektBean;
    }

    public VerticalLayout getMainLayout() {
        return mainLayout;
    }

    public Panel getMainPanel() {
        return mainPanel;
    }

    public Panel getRessourcesPanel() {
        return ressourcesPanel;
    }


}
