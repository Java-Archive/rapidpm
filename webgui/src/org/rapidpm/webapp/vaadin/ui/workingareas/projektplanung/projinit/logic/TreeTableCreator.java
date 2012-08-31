package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.logic;

import com.vaadin.data.util.HierarchicalContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.AufwandProjInitScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.components.MyTreeTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.ProjektBean;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 31.08.12
 * Time: 15:52
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class TreeTableCreator {

    private HierarchicalContainer dataSource;
    private ProjektBean projektBean;
    private RessourceGroupsBean ressourceGroupsBean;
    private MyTreeTable treeTable;
    private AufwandProjInitScreen screen;

    public TreeTableCreator(AufwandProjInitScreen screen, ProjektBean projektBean, RessourceGroupsBean ressourceGroupsBean, MyTreeTable treeTable, HierarchicalContainer dataSource) {
        this.dataSource = dataSource;
        this.projektBean = projektBean;
        this.ressourceGroupsBean = ressourceGroupsBean;
        this.treeTable = treeTable;
        this.screen = screen;
    }

    public void create() {
        TreeTableContainerFiller2 treeTableContainerFiller = new TreeTableContainerFiller2(ressourceGroupsBean, projektBean, dataSource);
        treeTableContainerFiller.fill();
        treeTable.addListener(new TableItemClickListener(screen));
        treeTable.setContainerDataSource(this.dataSource);
        treeTable.setColumnCollapsible("Aufgabe", false);
        treeTable.setColumnWidth("Aufgabe",250);
    }
}
