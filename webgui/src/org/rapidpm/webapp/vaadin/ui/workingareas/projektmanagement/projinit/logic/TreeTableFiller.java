package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic;

import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.ItemClickEvent;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TimesCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.AufwandProjInitScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTreeTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupsBean;

import java.util.Map;

import static org.rapidpm.Constants.AUFGABE_SPALTE;

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
    private AufwandProjInitScreen screen;

    public TreeTableFiller(final AufwandProjInitScreen screen, final ProjektBean projektBean,
                           final RessourceGroupsBean ressourceGroupsBean, final MyTreeTable treeTable,
                           final HierarchicalContainer dataSource) {
        this.dataSource = dataSource;
        this.projektBean = projektBean;
        this.ressourceGroupsBean = ressourceGroupsBean;
        this.treeTable = treeTable;
        this.screen = screen;
    }

    public void fill() {
        final TimesCalculator timesCalculator = new TimesCalculator(ressourceGroupsBean, projektBean);
        final TreeTableDataSourceFiller treeTableDataSourceFiller = new TreeTableDataSourceFiller(ressourceGroupsBean,
                projektBean, dataSource);
        timesCalculator.calculate();
        treeTableDataSourceFiller.fill();
        for(final Object listener : treeTable.getListeners(ItemClickEvent.ItemClickListener.class)){
            treeTable.removeListener((ItemClickEvent.ItemClickListener)listener);
        }
        treeTable.addListener(new TableItemClickListener(screen));
        treeTable.setContainerDataSource(this.dataSource);
        treeTable.setColumnCollapsible(AUFGABE_SPALTE, false);
        treeTable.setFooterVisible(true);
        final Map<RessourceGroup, DaysHoursMinutesItem> werteMap = timesCalculator.getAbsoluteWerte();
        for(final RessourceGroup ressourceGroup : werteMap.keySet()){
            treeTable.setColumnFooter(ressourceGroup.getName(), werteMap.get(ressourceGroup).toString());
        }
        treeTable.setColumnWidth(AUFGABE_SPALTE, WIDTH);
        treeTable.setValue(null);
    }
}
