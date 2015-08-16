package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic;

import com.vaadin.data.util.HierarchicalContainer;
import org.rapidpm.Constants;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.CostsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTreeTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic.TreeTableDataSourceFiller;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic.TreeTableValue;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.*;
import static org.rapidpm.Constants.DECIMAL_FORMAT;
import static org.rapidpm.Constants.EUR;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 31.08.12
 * Time: 15:52
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class TreeTableFiller {

    private static final int WIDTH = 200;

    private HierarchicalContainer dataSource;
    private CostsScreen screen;
    private TreeTableValue cellValue;
    private MyTreeTable treeTable;
    private ResourceBundle messages;

    public TreeTableFiller(final ResourceBundle bundle, final CostsScreen screen, final MyTreeTable treeTable,
                           final HierarchicalContainer dataSource, TreeTableValue cellValue) {
        this.messages = bundle;
        this.dataSource = dataSource;
        this.treeTable = treeTable;
        this.screen = screen;
        this.cellValue = cellValue;
    }

    public void fill() {
        for(final Object id : treeTable.getContainerPropertyIds()){
            treeTable.setConverter(id, null);
        }
        final CostsConverterAdder costsConverterAdder = new CostsConverterAdder(messages);
        final TreeTableDataSourceFiller treeTableDataSourceFiller = new TreeTableDataSourceFiller(screen, messages, dataSource, cellValue);
        treeTableDataSourceFiller.fill();
        treeTable.setContainerDataSource(this.dataSource);
        final String aufgabeColumn = messages.getString("aufgabe");
        for(final Object propertyId : treeTable.getContainerPropertyIds()){
            if(propertyId.equals(aufgabeColumn)){
                treeTable.setColumnCollapsible(aufgabeColumn, false);
                treeTable.setColumnWidth(aufgabeColumn, WIDTH);
            } else {
                treeTable.setColumnExpandRatio(propertyId, 1);
            }
        }
        treeTable.setFooterVisible(true);
        fillFooter();
        treeTable.setValue(null);
        costsConverterAdder.addConvertersTo(treeTable);
    }

    private void fillFooter() {
        final Map<RessourceGroup, Double> ressourceGroupCostsMap = new HashMap<>();
        final PlannedProject plannedProject = DaoFactorySingleton.getInstance().getPlannedProjectDAO().findByID(screen.getUi().getCurrentProject().getId(), true);
        final List<PlanningUnit> topLevelPlanningUnits = plannedProject.getTopLevelPlanningUnits();
        for (PlanningUnit topLevelPlanningUnit : topLevelPlanningUnits) {
            topLevelPlanningUnit = DaoFactorySingleton.getInstance().getPlanningUnitDAO().findByID(topLevelPlanningUnit.getId(), true);
            for (PlanningUnitElement planningUnitElement : topLevelPlanningUnit.getPlanningUnitElementList()) {
                if(!ressourceGroupCostsMap.containsKey(planningUnitElement.getRessourceGroup())){
                    ressourceGroupCostsMap.put(planningUnitElement.getRessourceGroup(), 0.0);
                }
                final int minutes = planningUnitElement.getPlannedMinutes();
                final double hoursFromMinutes = (double) minutes / MINS_HOUR;
                final Float externalEurosPerHour = planningUnitElement.getRessourceGroup().getExternalEurosPerHour();
                ressourceGroupCostsMap.put(planningUnitElement.getRessourceGroup(), ressourceGroupCostsMap.get(planningUnitElement.getRessourceGroup()) + hoursFromMinutes * externalEurosPerHour);
            }
        }
        DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);
        for (final RessourceGroup ressourceGroup : ressourceGroupCostsMap.keySet()) {
            treeTable.setColumnFooter(ressourceGroup.getName(), format.format(ressourceGroupCostsMap.get(ressourceGroup)) + EUR);
        }
    }

}
