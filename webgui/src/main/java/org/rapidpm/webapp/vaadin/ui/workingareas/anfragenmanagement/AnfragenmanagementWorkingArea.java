package org.rapidpm.webapp.vaadin.ui.workingareas.anfragenmanagement;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.rapidpm.persistence.prj.bewegungsdaten.anfragen.Projektanfrage;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 26.04.12
 * Time: 12:14
 */

public class AnfragenmanagementWorkingArea extends VerticalLayout {

    public AnfragenmanagementWorkingArea() {
        //final DaoFactoryBean daoFactoryBean = null; // TODO AnfragenmanagementWorkingAreaBean
        //final ProjektanfrageDAO projektanfrageDAO = daoFactoryBean.getProjektanfrageDAO();

//        final BeanItemContainer<Projektanfrage> dataSource = new BeanItemContainer<>(Projektanfrage.class);
        //final List<Projektanfrage> projektanfrageList = projektanfrageDAO.loadAllEntities();
        //dataSource.addAll(projektanfrageList);

        final Grid table = new Grid();
//        table.setVisibleColumns(ProjektanfrageUI.VISIBLE_COLUMNS);
//        table.setColumnHeaders(ProjektanfrageUI.COLUMN_NAMES);
//        table.setSelectable(true);
//        table.setColumnCollapsingAllowed(true);
        table.setColumnReorderingAllowed(true);
        table.setSizeFull();
        add(table);
    }

}
