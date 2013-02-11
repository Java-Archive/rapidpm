package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic;

import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.VaadinSession;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TimesCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.AufwandProjInitScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTreeTable;

import java.util.Map;
import java.util.ResourceBundle;

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
    private MyTreeTable treeTable;
    private AufwandProjInitScreen screen;
    private ResourceBundle messages;
    private PlannedProject currentProject;


    public TreeTableFiller(final ResourceBundle bundle, final AufwandProjInitScreen screen,
                           final MyTreeTable treeTable, final HierarchicalContainer dataSource) {
        this.messages = bundle;
        this.dataSource = dataSource;
        this.treeTable = treeTable;
        this.screen = screen;
    }

    public void fill() {
        final VaadinSession session = screen.getUi().getSession();
        currentProject = session.getAttribute(PlannedProject.class);
        final TimesCalculator timesCalculator = new TimesCalculator(screen);
        final TreeTableDataSourceFiller treeTableDataSourceFiller = new TreeTableDataSourceFiller(screen, messages,
                dataSource);
        timesCalculator.calculate();
        treeTableDataSourceFiller.fill();
        for(final Object listener : treeTable.getListeners(ItemClickEvent.ItemClickListener.class)){
            treeTable.removeItemClickListener((ItemClickEvent.ItemClickListener)listener);
        }
        treeTable.addItemClickListener(new TableItemClickListener(messages, screen));
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
        final Map<RessourceGroup, Integer> werteMap = timesCalculator.getAbsoluteWerte();
        for(final RessourceGroup ressourceGroup : werteMap.keySet()){
            final DaysHoursMinutesItem item = new DaysHoursMinutesItem(werteMap.get(ressourceGroup),
                    currentProject.getHoursPerWorkingDay());
            treeTable.setColumnFooter(ressourceGroup.getName(), item.toString());
        }
        treeTable.setValue(null);
    }
}
