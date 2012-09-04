package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.costs.logic;

import com.vaadin.data.util.HierarchicalContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.components.MyTreeTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.ProjektBean;

import static org.rapidpm.Constants.*;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 31.08.12
 * Time: 15:52
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class TreeTableFiller {

    private static final int WIDTH = 250;

    private HierarchicalContainer dataSource;
    private ProjektBean projektBean;
    private RessourceGroupsBean ressourceGroupsBean;
    private MyTreeTable treeTable;

    public TreeTableFiller(ProjektBean projektBean, RessourceGroupsBean ressourceGroupsBean, MyTreeTable treeTable, HierarchicalContainer dataSource) {
        this.dataSource = dataSource;
        this.projektBean = projektBean;
        this.ressourceGroupsBean = ressourceGroupsBean;
        this.treeTable = treeTable;
    }

    public void fill() {
        final CostsConverterAdder costsConverterAdder = new CostsConverterAdder();
        final TreeTableDataSourceFiller treeTableDataSourceFiller = new TreeTableDataSourceFiller(ressourceGroupsBean, projektBean, dataSource);
        treeTableDataSourceFiller.fill();
        treeTable.setContainerDataSource(this.dataSource);
        treeTable.setColumnCollapsible(AUFGABE_SPALTE, false);
        treeTable.setColumnWidth(AUFGABE_SPALTE, WIDTH);
        treeTable.setValue(null);
        costsConverterAdder.addConvertersTo(treeTable);
    }
}
