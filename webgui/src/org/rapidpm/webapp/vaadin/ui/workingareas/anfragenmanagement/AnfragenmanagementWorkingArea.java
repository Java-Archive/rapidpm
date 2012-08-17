package org.rapidpm.webapp.vaadin.ui.workingareas.anfragenmanagement;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import org.rapidpm.persistence.prj.bewegungsdaten.anfragen.Projektanfrage;
import org.rapidpm.persistence.prj.bewegungsdaten.anfragen.ProjektanfrageDAO;
import org.rapidpm.persistence.prj.stammdaten.DaoFactoryBean;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 26.04.12
 * Time: 12:14
 */

public class AnfragenmanagementWorkingArea extends VerticalLayout {

    public AnfragenmanagementWorkingArea() {
        final DaoFactoryBean daoFactoryBean = null; // TODO AnfragenmanagementWorkingAreaBean
        final ProjektanfrageDAO projektanfrageDAO = daoFactoryBean.getProjektanfrageDAO();

        final BeanItemContainer<Projektanfrage> dataSource = new BeanItemContainer<>(Projektanfrage.class);
        final List<Projektanfrage> projektanfrageList = projektanfrageDAO.loadAllEntities();
        dataSource.addAll(projektanfrageList);

        final Table table = new Table("Projektanfragen", dataSource);
        table.setVisibleColumns(ProjektanfrageUI.VISIBLE_COLUMNS);
        table.setColumnHeaders(ProjektanfrageUI.COLUMN_NAMES);
        table.setSelectable(true);
        table.setColumnCollapsingAllowed(true);
        table.setColumnReorderingAllowed(true);
        addComponent(table);
    }

}
