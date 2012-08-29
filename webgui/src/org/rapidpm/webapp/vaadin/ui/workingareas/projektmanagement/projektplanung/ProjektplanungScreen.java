package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projektplanung;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projektplanung.enums.IssueStati;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projektplanung.logic.TreeItemClickListener;
import org.rapidpm.transience.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.transience.prj.projectmanagement.planning.PlanningUnitGroup;
import org.rapidpm.transience.prj.projectmanagement.planning.ProjektBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.04.12
 * Time: 09:43
 */
public class ProjektplanungScreen extends HorizontalSplitPanel {


    private final VerticalLayout menuLayout;
    private final Panel projektPanel;
    private final Panel treePanel;
    private final Panel detailPanel;
    private final ListSelect projektSelect;
    private final VerticalLayout mainLayout;
    private final ProjektBean projektBean;
    private Tree treePanelTree;


    public ProjektplanungScreen(ProjektBean cont) {
        this.projektBean = cont;
        setSizeFull();
        setSplitPosition(40, Unit.PERCENTAGE);

        menuLayout = new VerticalLayout();
        menuLayout.setSpacing(true);
        addComponent(menuLayout);

        projektPanel = new Panel("Projekt");
        menuLayout.addComponent(projektPanel);
        treePanel = new Panel();
        menuLayout.addComponent(treePanel);
        detailPanel = new Panel("Details");
        menuLayout.addComponent(detailPanel);

        mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        addComponent(mainLayout);


//        final List<String> listenWerte = Arrays.asList("Projektmanagement",
//                "Zeitplanung",
//                "Technische Planung",
//                "Inbetriebnahme",
//                "Abnahme",
//                "Gewährleistung",
//                "Dokumentation",
//                "Schulung");
        final ArrayList<String> listenWerteArrayList = projektBean.getProjekt().getPlanningUnitGroupsNames();
//        listenWerteArrayList.addAll(listenWerte);
        projektSelect = new ListSelect(null, listenWerteArrayList);

        projektSelect.setNullSelectionAllowed(false);
        projektSelect.setImmediate(true);
        projektPanel.getContent().addComponent(projektSelect);
        projektSelect.addListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(final Property.ValueChangeEvent valueChangeEvent) {
                final String value = (String) valueChangeEvent.getProperty().getValue();
                mainLayout.removeAllComponents();
                treePanel.getContent().removeAllComponents();
                detailPanel.getContent().removeAllComponents();
                treePanel.setCaption(value);
                fillTreePanel(value);
                mainLayout.addComponent(new Label(value));
                detailPanel.setCaption("Details");
            }

        });

    }

    public void fillTreePanel(String planningGroupName) {

        treePanel.removeAllComponents();
        treePanelTree = new Tree();
        PlanningUnitGroup planningUnitGroup = null;
        for (final PlanningUnitGroup pug : projektBean.getProjekt().getPlanningUnitGroups()) {
            if (pug.getPlanningUnitGroupName().equals(planningGroupName)) {
                planningUnitGroup = pug;
            }
        }

        if (planningUnitGroup != null) {
            treePanelTree.addContainerProperty("name", String.class, "");
            treePanelTree.addContainerProperty("icon", Resource.class, null);
            treePanelTree.setItemCaptionPropertyId("name");
            treePanelTree.setItemIconPropertyId("icon");
            treePanelTree.setImmediate(true);
            final Object planningUnitGroupId = treePanelTree.addItem();
            final Item planningUnitGroupItem = treePanelTree.getItem(planningUnitGroupId);
            planningUnitGroupItem.getItemProperty("name").setValue(planningUnitGroup.getPlanningUnitGroupName());
            final String issueStatusName = planningUnitGroup.getIssueBase().getIssueStatus().getStatusName();
            planningUnitGroupItem.getItemProperty("icon").setValue(IssueStati.valueOf(issueStatusName).getIcon());
            if (planningUnitGroup.getPlanningUnitList() != null && !planningUnitGroup.getPlanningUnitList().isEmpty()) {
                treePanelTree.setChildrenAllowed(planningUnitGroupId, true);
            } else {
                treePanelTree.setChildrenAllowed(planningUnitGroupId, false);
            }

            buildTree(planningUnitGroup.getPlanningUnitList(), planningUnitGroupId);
            treePanelTree.expandItemsRecursively(planningUnitGroupId);
            treePanelTree.addListener(new TreeItemClickListener(this, projektBean.getProjekt()));
            treePanel.addComponent(treePanelTree);
        } //else if (planningGroupName.equals("Technische Planung")) {
          //  showTechnischePlanung();
        //}
    }

    private void buildTree(List<PlanningUnit> planningUnits, Object parentId) {
        for (PlanningUnit planningUnit : planningUnits) {
            final Object itemId = treePanelTree.addItem();
            treePanelTree.getItem(itemId).getItemProperty("name").setValue(planningUnit.getPlanningUnitName());
            final String issueStatusName = planningUnit.getIssueBase().getIssueStatus().getStatusName();
            treePanelTree.getItem(itemId).getItemProperty("icon").setValue(IssueStati.valueOf(issueStatusName).getIcon());
            treePanelTree.setParent(itemId, parentId);
            if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
                treePanelTree.setChildrenAllowed(itemId, false);
            } else {
                buildTree(planningUnit.getKindPlanningUnits(), itemId);
            }
        }
    }

//    private void showTechnischePlanung() {
//        final Projekt projekt = createDemoData();
//        final ComponentContainer treePanelContent = treePanel.getContent();
//        treePanelContent.removeAllComponents();
//        final Tree tree = new Tree();
//        tree.addContainerProperty("obj", Teil.class, null);
//        tree.addContainerProperty("name", String.class, "");
//        tree.addContainerProperty("icon", Resource.class, null);
//        tree.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
//        tree.setItemCaptionPropertyId("name");
//        tree.setItemIconPropertyId("icon");
//        tree.setMultiSelect(true);
//        tree.setImmediate(true);
//        final Object projektId = tree.addItem();
//        final Item projektItem = tree.getItem(projektId);
//        projektItem.getItemProperty("obj").setValue(projekt);
//        projektItem.getItemProperty("name").setValue(projekt.getName());
//        projektItem.getItemProperty("icon").setValue(projekt.getStatus().getIcon());
//        tree.setChildrenAllowed(projektId, true);
//        for (final Baugruppe baugruppe : projekt.getBaugruppen()) {
//            final Object baugruppeId = tree.addItem();
//            final Item baugruppeItem = tree.getItem(baugruppeId);
//            baugruppeItem.getItemProperty("obj").setValue(baugruppe);
//            baugruppeItem.getItemProperty("name").setValue(baugruppe.getName());
//            baugruppeItem.getItemProperty("icon").setValue(baugruppe.getStatus().getIcon());
//            tree.setParent(baugruppeId, projektId);
//            tree.setChildrenAllowed(baugruppeId, true);
//            for (final Einzelteil einzelteil : baugruppe.getEinzelteile()) {
//                final Object einzelteilId = tree.addItem();
//                final Item einzelteilItem = tree.getItem(einzelteilId);
//                einzelteilItem.getItemProperty("obj").setValue(einzelteil);
//                einzelteilItem.getItemProperty("name").setValue(einzelteil.getName());
//                einzelteilItem.getItemProperty("icon").setValue(einzelteil.getStatus().getIcon());
//                tree.setParent(einzelteilId, baugruppeId);
//                tree.setChildrenAllowed(einzelteilId, false);
//            }
//        }
//        tree.expandItemsRecursively(projektId);
//        treePanelContent.addComponent(tree);
//
//        tree.addListener(new Property.ValueChangeListener() {
//            @Override
//            public void valueChange(final Property.ValueChangeEvent valueChangeEvent) {
//                final Set<?> selectedIds = (Set<?>) valueChangeEvent.getProperty().getValue();
//                if (selectedIds != null && !selectedIds.isEmpty()) {
//                    final Object firstId = selectedIds.iterator().next();
//                    final Teil teil = (Teil) tree.getItem(firstId).getItemProperty("obj").getValue();
//                    setTeil(teil);
//                }
//            }
//        });
//
//        tree.addActionHandler(new Action.Handler() {
//            private final Action COMPARE_ACTION = new Action("Vergleichen");
//
//            @Override
//            public Action[] getActions(final Object target, final Object sender) {
//                return new Action[]{COMPARE_ACTION};
//            }
//
//            @Override
//            public void handleAction(final Action action, final Object sender, final Object target) {
//                if (action == COMPARE_ACTION) {
//                    final Set<?> selectedIds = (Set<?>) tree.getValue();
//                    if (selectedIds != null && selectedIds.size() >= 2) { // nur wenn mindestens 2 Teile ausgewählt wurden
//                        mainLayout.removeAllComponents();
//                        final HorizontalLayout hl = new HorizontalLayout();
//                        mainLayout.addComponent(hl);
//                        hl.setSpacing(true);
//                        Class<?> teilClass = null;
//                        final Iterator<?> idIterator = selectedIds.iterator();
//                        for (final Object itemId : selectedIds) {
//                            final Item item = tree.getItem(itemId);
//                            final Teil teil = (Teil) item.getItemProperty("obj").getValue();
//                            if (teilClass == null) {
//                                // erstes Teil
//                                teilClass = teil.getClass();
//                            } else if (teilClass != teil.getClass()) {
//                                Notification.show("Es können nur Teile vom selben Typ miteinander verglichen werden.", Notification.TYPE_WARNING_MESSAGE);
////                                getApplication().getMainWindow().showNotification("Fehler", "Es können nur Teile vom selben Typ miteinander verglichen werden.", Window.Notification.TYPE_WARNING_MESSAGE);
//                                return;
//                            } else {
//                                // selbe Typen
//                            }
//                            hl.addComponent(createTeilLayout(teil));
//                        }
//                    }
//                }
//            }
//        });
//    }

//    private void setTeil(final Teil teil) {
//        detailPanel.setCaption(teil.getName());
//        final ComponentContainer detailPanelContent = detailPanel.getContent();
//        detailPanelContent.removeAllComponents();
//
//        detailPanel.addComponent(new Label(teil.getClass().getSimpleName()));
//
//        final FormLayout formLayout = new FormLayout();
//
//        final ComboBox statusComboBox = new ComboBox("Status", new BeanItemContainer<>(Teil.Status.class, Arrays.asList(Teil.Status.values())));
//        statusComboBox.setItemIconPropertyId("icon");
//        statusComboBox.setNullSelectionAllowed(false);
//        statusComboBox.select(teil.getStatus());
//        formLayout.addComponent(statusComboBox);
//
//        final ComboBox priorityComboBox = new ComboBox("Priorität", new BeanItemContainer<>(Teil.Priority.class, Arrays.asList(Teil.Priority.values())));
//        priorityComboBox.setItemIconPropertyId("icon");
//        priorityComboBox.setNullSelectionAllowed(false);
//        priorityComboBox.select(teil.getPriority());
//        formLayout.addComponent(priorityComboBox);
//
//        final ComboBox schwierigkeitsgradComboBox = new ComboBox("Schwierigkeitsgrad", Arrays.asList(Teil.Schwierigkeitsgrad.values()));
//        schwierigkeitsgradComboBox.setNullSelectionAllowed(false);
//        schwierigkeitsgradComboBox.select(teil.getSchwierigkeitsgrad());
//        formLayout.addComponent(schwierigkeitsgradComboBox);
//
//        final ComboBox planerComboBox = new ComboBox("Planer", Arrays.asList("Max Mustermann", "Hugo", "Siggi"));
//        planerComboBox.select(teil.getPlaner());
//        formLayout.addComponent(planerComboBox);
//
//        final DateField createdDateField = new DateField("Erstellt", teil.getDateCreated());
//        createdDateField.setDateFormat(Constants.DATE_FORMAT.toPattern());
//        formLayout.addComponent(createdDateField);
//
//        final DateField updatedDateField = new DateField("Aktualisiert", teil.getDateUpdated());
//        updatedDateField.setDateFormat(Constants.DATE_FORMAT.toPattern());
//        formLayout.addComponent(updatedDateField);
//
//        detailPanel.addComponent(formLayout);
//
//        final TextArea commentTextArea = new TextArea("Kommentar", teil.getComment());
//        detailPanel.addComponent(commentTextArea);
//
//        mainLayout.removeAllComponents();
//        mainLayout.addComponent(createTeilLayout(teil));
//
//    }

//    private ComponentContainer createTeilLayout(final Teil teil) {
//        final Table personalTable = new Table("Personal", new BeanItemContainer<>(Personal.class, Arrays.asList(
//                new Personal("Max Mustermann", "arbeiten", 1.0f, 2),
//                new Personal("Hugo", "arbeiten", 2.0f, 4),
//                new Personal("Siggi", "arbeiten", 3.0f, 5)
//        )));
//        personalTable.setVisibleColumns(Personal.VISIBLE_COLUMNS);
//        personalTable.setColumnHeaders(Personal.COLUMN_NAMES);
//        personalTable.setSelectable(true);
//        personalTable.setColumnCollapsingAllowed(true);
//        personalTable.setColumnReorderingAllowed(true);
//
//        final Table materialTable = new Table("Material", new BeanItemContainer<>(Material.class, Arrays.asList(
//                new Material("Seitenleiste 3x50mm", 2, "Blech", new BigDecimal(12.34), "Lieferant A"),
//                new Material("Schrauben M3", 23, "Stahl", new BigDecimal(0.10), "Lieferant B")
//        )));
//        materialTable.setVisibleColumns(Material.VISIBLE_COLUMNS);
//        materialTable.setColumnHeaders(Material.COLUMN_NAMES);
//        materialTable.setSelectable(true);
//        materialTable.setColumnCollapsingAllowed(true);
//        materialTable.setColumnReorderingAllowed(true);
//
//        final Table zukaufTable = new Table("Zukauf", new BeanItemContainer<>(Zukauf.class, Arrays.asList(
//                new Zukauf("Teil A", "Siggi"),
//                new Zukauf("Teil B", "Hugo"),
//                new Zukauf("Teil C", "Hugo"),
//                new Zukauf("Teil D", "Max Mustermann"),
//                new Zukauf("Teil E", "Siggi")
//        )));
//        zukaufTable.setVisibleColumns(Zukauf.VISIBLE_COLUMNS);
//        zukaufTable.setColumnHeaders(Zukauf.COLUMN_NAMES);
//        zukaufTable.setSelectable(true);
//        zukaufTable.setColumnCollapsingAllowed(true);
//        zukaufTable.setColumnReorderingAllowed(true);
//
//        final ComponentContainer teilLayout = new VerticalLayout();
//        final Label teilLabel = new Label(teil.getName());
//        teilLabel.setStyleName("teil-caption");
//        teilLayout.addComponent(teilLabel);
//        final VerticalLayout vl = new VerticalLayout();
//        vl.addComponent(personalTable);
//        vl.addComponent(materialTable);
//        vl.addComponent(zukaufTable);
//        teilLayout.addComponent(vl);
//
//        return teilLayout;
//    }
//
//    private Projekt createDemoData() {
//        final Projekt projekt = new Projekt("Beispielprojekt");
//        final Baugruppe baugruppe1 = projekt.addBaugruppe("Baugruppe 1");
//        baugruppe1.setPlaner("Hugo");
//        baugruppe1.setPriority(Teil.Priority.Critical);
//        baugruppe1.setSchwierigkeitsgrad(Teil.Schwierigkeitsgrad.Schwer);
//        baugruppe1.setComment("Hugo war hier!");
//        baugruppe1.addEinzelteil("B1: Einzelteil A");
//        baugruppe1.addEinzelteil("B1: Einzelteil B").setStatus(Teil.Status.InProgress);
//        final Baugruppe baugruppe2 = projekt.addBaugruppe("Baugruppe 2");
//        baugruppe2.addEinzelteil("B2: Einzelteil A").setStatus(Teil.Status.Resolved);
//        return projekt;
//    }

    public ListSelect getProjektSelect() {
        return projektSelect;
    }

    public Tree getTreePanelTree() {
        return treePanelTree;
    }

    public Panel getDetailPanel() {
        return detailPanel;
    }

    public Panel getProjektPanel() {
        return projektPanel;
    }

    public Panel getTreePanel() {
        return treePanel;
    }

    public ProjektBean getProjektBean() {
        return projektBean;
    }
}
