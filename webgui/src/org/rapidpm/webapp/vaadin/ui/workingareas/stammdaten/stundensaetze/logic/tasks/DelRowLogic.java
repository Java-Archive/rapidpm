package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks;

import com.vaadin.ui.Table;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ButtonComponent;

public class DelRowLogic {
    private ButtonComponent button;
    private StundensaetzeScreen screen;

    public DelRowLogic(final StundensaetzeScreen screen, final ButtonComponent button) {
        this.screen = screen;
        this.button = button;
    }

    public void execute() {
        final Table tabelle = screen.getTabelle();
        //final StammdatenScreensBean stammdatenScreensBean = screen.getStammdatenScreensBean();
        final RessourceGroup ressourceGroupFromTable = (RessourceGroup) button.getItemId();
        //final DaoFactoryBean baseDaoFactoryBean = stammdatenScreensBean.getDaoFactoryBean();
        //final RessourceGroupDAO ressourceGroupDAO = baseDaoFactoryBean.getRessourceGroupDAO();
        //ressourceGroupDAO.remove(ressourceGroupFromTable);
        tabelle.removeItem(ressourceGroupFromTable);
        tabelle.commit();
        screen.getFormLayout().setVisible(false);
        //screen.generateTableAndCalculate();
    }
}
