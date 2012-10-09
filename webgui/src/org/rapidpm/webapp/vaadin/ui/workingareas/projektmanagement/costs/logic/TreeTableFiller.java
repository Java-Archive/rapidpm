package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic;

import com.vaadin.data.util.HierarchicalContainer;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.CostsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTreeTable;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.DECIMAL_FORMAT;
import static org.rapidpm.Constants.EUR;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 31.08.12
 * Time: 15:52
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class TreeTableFiller {

    private static final int WIDTH = 200;

    private HierarchicalContainer dataSource;
    private CostsScreen screen;
    private MyTreeTable treeTable;
    private ResourceBundle messages;

    public TreeTableFiller(final ResourceBundle bundle, final CostsScreen screen, final MyTreeTable treeTable,
                           final HierarchicalContainer dataSource) {
        this.messages = bundle;
        this.dataSource = dataSource;
        this.treeTable = treeTable;
        this.screen = screen;
    }

    public void fill() {
        for(final Object id : treeTable.getContainerPropertyIds()){
            treeTable.setConverter(id, null);
        }
        final DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);
        final CostsCalculator costsCalculator = new CostsCalculator(messages);
        final CostsConverterAdder costsConverterAdder = new CostsConverterAdder(messages);
        final TreeTableDataSourceFiller treeTableDataSourceFiller = new TreeTableDataSourceFiller(screen, messages,
                dataSource);
        costsCalculator.calculate();
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
        final Map<RessourceGroup, Double> werteMap = costsCalculator.getRessourceGroupsCostsMap();
        for(final RessourceGroup ressourceGroup : werteMap.keySet()){

            final String ressourceGroupKostenString = format.format(werteMap.get(ressourceGroup))+EUR;
            treeTable.setColumnFooter(ressourceGroup.getName(), ressourceGroupKostenString);
        }
        treeTable.setValue(null);
        costsConverterAdder.addConvertersTo(treeTable);
    }
}
