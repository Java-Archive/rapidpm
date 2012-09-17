package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks;

import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroupDAO;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.StammdatenScreensBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ButtonComponent;

public class DelRowLogic {
    private ButtonComponent button;
    private StundensaetzeScreen screen;

    public DelRowLogic(final StundensaetzeScreen screen, final ButtonComponent button) {
        this.screen = screen;
        this.button = button;
    }

    public void execute() {
        final StammdatenScreensBean stammdatenScreensBean = screen.getStammdatenScreensBean();
        final RessourceGroupBean ressourceGroupBeanFromTable = (RessourceGroupBean) button.getItemId();
        final DaoFactoryBean baseDaoFactoryBean = stammdatenScreensBean.getDaoFactoryBean();
        final RessourceGroupDAO ressourceGroupDAO = baseDaoFactoryBean.getRessourceGroupDAO();
        final RessourceGroup ressourceGroupFromNestedBean = ressourceGroupBeanFromTable.getRessourceGroup();
        //ressourceGroupDAO.remove(ressourceGroupFromNestedBean); TODO RPM-41
        screen.getFormLayout().setVisible(false);
        screen.generateTableAndCalculate();
    }
}
