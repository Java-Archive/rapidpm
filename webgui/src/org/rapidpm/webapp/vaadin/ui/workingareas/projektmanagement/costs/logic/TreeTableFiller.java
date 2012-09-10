package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic;

import com.vaadin.data.util.HierarchicalContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTreeTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupsBean;

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

    private static final int WIDTH = 250;

    private HierarchicalContainer dataSource;
    private ProjektBean projektBean;
    private RessourceGroupsBean ressourceGroupsBean;
    private MyTreeTable treeTable;
    private ResourceBundle messages;

    public TreeTableFiller(final ResourceBundle bundle, final ProjektBean projektBean,
                           final RessourceGroupsBean ressourceGroupsBean,
                           final MyTreeTable treeTable, final HierarchicalContainer dataSource) {
        this.messages = bundle;
        this.dataSource = dataSource;
        this.projektBean = projektBean;
        this.ressourceGroupsBean = ressourceGroupsBean;
        this.treeTable = treeTable;
    }

    public void fill() {
        for(final Object id : treeTable.getContainerPropertyIds()){
            treeTable.setConverter(id, null);
        }
        final DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);
        final CostsCalculator costsCalculator = new CostsCalculator(projektBean, messages);
        final CostsConverterAdder costsConverterAdder = new CostsConverterAdder(messages);
        final TreeTableDataSourceFiller treeTableDataSourceFiller = new TreeTableDataSourceFiller
                (messages, ressourceGroupsBean, projektBean, dataSource);
        costsCalculator.calculate();
        treeTableDataSourceFiller.fill();
        treeTable.setContainerDataSource(this.dataSource);
        treeTable.setColumnCollapsible(messages.getString("aufgabe"), false);
        treeTable.setColumnWidth(messages.getString("aufgabe"), WIDTH);
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
