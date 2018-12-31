package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.components;

import com.vaadin.flow.component.button.Button;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.CostsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic.TreeTableFiller;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTreeTable;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 05.09.12
 * Time: 08:47
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class UndoButton extends Button {

    private MyTreeTable treeTable;
//    private HierarchicalContainer dataSource;
    private CostsScreen screen;

    public UndoButton(final CostsScreen screen, final MyTreeTable treeTable, final String dataSource){
        this.screen = screen;
        this.treeTable = treeTable;
//        this.dataSource = dataSource;
//        this.addClickListener(this);
        this.setText("remove sortorder");
//        this.setStyleName("link");
    }

//    @Override
//    public void buttonClick(Button.ClickEvent event) {
//        final TreeTableFiller treeTableFiller = new TreeTableFiller(VaadinSession.getCurrent().getAttribute(ResourceBundle.class), screen, treeTable,
//                dataSource);
//        treeTableFiller.fill();
//        treeTable.markAsDirty();
//
//        this.setVisible(false);
//    }
}
