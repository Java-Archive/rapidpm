package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks;

import org.rapidpm.webapp.vaadin.MainRoot;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;
import org.rapidpm.transience.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.components.ButtonComponent;
import org.rapidpm.transience.prj.projectmanagement.planning.ProjektBean;

public class DeleteRowLogic {
    private ButtonComponent button;
    private StundensaetzeScreen screen;
    private MainRoot root;

    public DeleteRowLogic(MainRoot root, StundensaetzeScreen screen, ButtonComponent button) {
        this.screen = screen;
        this.button = button;
        this.root = root;
    }

    public void execute() {
        final RessourceGroupsBean ressourceGroupsBean = root.getRessourceGroupsBean();
        final Object ressourceGroupFromTable = button.getItemId();
        ressourceGroupsBean.getRessourceGroups().remove(ressourceGroupFromTable);
        root.setPlanningUnitsBean(new ProjektBean(ressourceGroupsBean));
        screen.getFormLayout().setVisible(false);
        root.setWorkingArea(new StundensaetzeScreen(root));
    }
}
