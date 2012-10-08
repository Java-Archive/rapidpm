package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProjectDAO;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.BaseUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic.PlanningCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic.TreeValueChangeListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.PlanningUnitBeanItemContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.04.12
 * Time: 09:43
 */
public class ProjektplanungScreen extends Screen {


    private static final String NAME = "name";
    private static final String ICON = "icon";

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


    public ProjektplanungScreen(BaseUI ui) {
        super(ui);

        final PlanningCalculator calculator = new PlanningCalculator(messagesBundle, this.projektBean,
                this.projektmanagementScreensBean);
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

        projektplanungScreenBean = EJBFactory.getEjbInstance(ProjektPlanungScreenBean.class);
        final DaoFactoryBean daoFactoryBean = projektplanungScreenBean.getDaoFactoryBean();
        final PlannedProjectDAO plannedProjectDAO = daoFactoryBean.getPlannedProjectDAO();
        final PlannedProject plannedProject = plannedProjectDAO.loadAllEntities().get(0);
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
            //treePanelTree.setItemIconPropertyId(ICON);
            treePanelTree.setImmediate(true);
            container.addBean(selectedPlanningUnit);
            final IssueBase issueBase = selectedPlanningUnit.getIssueBase();
            final String issueStatusName = issueBase.getIssueStatus().getStatusName();
            //planningUnitItem.getItemProperty(ICON).setValue(IssueStatusEnum.valueOf(issueStatusName).getIcon());
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
            treePanel.addComponent(treePanelTree);
        }
    }

    private void buildTree(List<PlanningUnit> planningUnits, PlanningUnit parentUnit) {
        for (final PlanningUnit planningUnit : planningUnits) {
            container.addBean(planningUnit);
            //treePanelTree.getItem(itemId).getItemProperty(NAME).setValue(planningUnit.getPlanningUnitName());
            //final String issueStatusName = planningUnit.getIssueBase().getIssueStatus().getStatusName();
            //treePanelTree.getItem(itemId).getItemProperty(ICON).setValue(IssueStatusEnum.valueOf(issueStatusName)
            //        .getIcon());
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
