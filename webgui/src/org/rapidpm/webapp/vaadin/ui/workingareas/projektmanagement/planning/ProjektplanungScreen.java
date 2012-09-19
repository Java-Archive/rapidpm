package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Resource;
import com.vaadin.ui.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.BaseUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.IssueStatusEnum;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.PlanningUnitGroupPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic.PlanningCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic.TreeValueChangeListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.Projekt;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;

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
    private final PlanningUnitGroupPanel planningUnitGroupPanel;
    private final Panel treePanel;
    private final Panel detailPanel;
    private final ListSelect projektSelect;
    private final VerticalLayout mainLayout;
    private Tree treePanelTree;


    public ProjektplanungScreen(BaseUI ui) {
        super(ui);

        final PlanningCalculator calculator = new PlanningCalculator(messagesBundle, this.projektBean,
                this.projektmanagementScreensBean);
//        calculator.calculate();
        splitPanel = new HorizontalSplitPanel();
        splitPanel.setSizeFull();
        splitPanel.setSplitPosition(40, Unit.PERCENTAGE);


        treePanel = new Panel();
        detailPanel = new Panel();



        mainPanel = new Panel();
        ressourcesPanel = new Panel();
        ressourcesPanel.setSizeFull();

        mainLayout = new VerticalLayout();
        mainLayout.setSpacing(true);
        mainLayout.addComponent(ressourcesPanel);
        mainLayout.addComponent(mainPanel);




        final Integer currentProjectIndex = projektBean.getCurrentProjectIndex();
        final Projekt projekt = projektBean.getProjekte().get(currentProjectIndex);
        //final List<String> listenWerteArrayList = projekt.getPlanningUnitGroupsNames();


        //projektSelect = new ListSelect(null, listenWerteArrayList);
        projektSelect = new ListSelect(null, new BeanItemContainer<>(PlanningUnitGroup.class,
                projekt.getPlanningUnitGroups()));
        projektSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        projektSelect.setItemCaptionPropertyId(PlanningUnitGroup.NAME);

        menuLayout = new VerticalLayout();
        menuLayout.setSpacing(true);


        splitPanel.addComponent(menuLayout);
        splitPanel.addComponent(mainLayout);

        projektSelect.setNullSelectionAllowed(false);
        projektSelect.setImmediate(true);

        final List<?> ids = (List<?>) projektSelect.getItemIds();
        projektSelect.setValue(ids.get(0));
        planningUnitGroupPanel = new PlanningUnitGroupPanel(ui, this, projekt, projektSelect);

        projektSelect.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(final Property.ValueChangeEvent valueChangeEvent) {
                final PlanningUnitGroup selectedPlanningUnitGroup = (PlanningUnitGroup) valueChangeEvent.getProperty().getValue();
                treePanel.getContent().removeAllComponents();
                detailPanel.getContent().removeAllComponents();
                final String planningUnitGroupName = selectedPlanningUnitGroup.getPlanningUnitGroupName();
                treePanel.setCaption(planningUnitGroupName);
                fillTreePanel(planningUnitGroupName, projekt);
                treePanelTree.select(planningUnitGroupName);
                planningUnitGroupPanel.setSelectedPlanningUnitGroup(selectedPlanningUnitGroup);
            }

        });

        menuLayout.addComponent(planningUnitGroupPanel);
        menuLayout.addComponent(treePanel);
        menuLayout.addComponent(detailPanel);
        doInternationalization();
        setComponents();

    }

    @Override
    public void doInternationalization() {
        detailPanel.setCaption(messagesBundle.getString("details"));
    }

    public void fillTreePanel(String planningGroupName, Projekt projekt) {

        treePanel.removeAllComponents();
        treePanelTree = new Tree();
        PlanningUnitGroup planningUnitGroup = null;
        for (final PlanningUnitGroup pug : projekt.getPlanningUnitGroups()) {
            if (pug.getPlanningUnitGroupName().equals(planningGroupName)) {
                planningUnitGroup = pug;
            }
        }

        if (planningUnitGroup != null) {
            treePanelTree.addContainerProperty(NAME, String.class, "");
            treePanelTree.addContainerProperty(ICON, Resource.class, null);
            treePanelTree.setItemCaptionPropertyId(NAME);
            treePanelTree.setItemIconPropertyId(ICON);
            treePanelTree.setImmediate(true);
            final String itemId = planningUnitGroup.getPlanningUnitGroupName();
            final Item planningUnitGroupItem = treePanelTree.addItem(itemId);

            planningUnitGroupItem.getItemProperty(NAME).setValue(itemId);
            final IssueBase issueBase = planningUnitGroup.getIssueBase();
            final String issueStatusName = issueBase.getIssueStatus().getStatusName();
            planningUnitGroupItem.getItemProperty(ICON).setValue(IssueStatusEnum.valueOf(issueStatusName).getIcon());
            if (planningUnitGroup.getPlanningUnitList() != null && !planningUnitGroup.getPlanningUnitList().isEmpty()) {
                treePanelTree.setChildrenAllowed(itemId, true);
            } else {
                treePanelTree.setChildrenAllowed(itemId, false);
            }

            buildTree(planningUnitGroup.getPlanningUnitList(), itemId);
            treePanelTree.expandItemsRecursively(itemId);
            treePanelTree.addValueChangeListener(new TreeValueChangeListener(this, projekt));
            treePanel.addComponent(treePanelTree);
        } //else if (planningGroupName.equals("Technische Planung")) {
        //  showTechnischePlanung();
        //}
    }

    private void buildTree(List<PlanningUnit> planningUnits, Object parentId) {
        for (final PlanningUnit planningUnit : planningUnits) {
            final Object itemId = treePanelTree.addItem();
            treePanelTree.getItem(itemId).getItemProperty(NAME).setValue(planningUnit.getPlanningUnitName());
            final String issueStatusName = planningUnit.getIssueBase().getIssueStatus().getStatusName();
            treePanelTree.getItem(itemId).getItemProperty(ICON).setValue(IssueStatusEnum.valueOf(issueStatusName).getIcon());
            treePanelTree.setParent(itemId, parentId);
            if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
                treePanelTree.setChildrenAllowed(itemId, false);
            } else {
                buildTree(planningUnit.getKindPlanningUnits(), itemId);
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

    public Panel getPlanningUnitGroupPanel() {
        return planningUnitGroupPanel;
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
