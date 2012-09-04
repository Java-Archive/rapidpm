package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks;

import org.rapidpm.webapp.vaadin.MainRoot;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;
import org.rapidpm.webapp.vaadin.RessourceGroup;
import org.rapidpm.webapp.vaadin.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ButtonComponent;
import org.rapidpm.webapp.vaadin.ProjektBean;

import java.util.List;

public class DelRowLogic {
    private ButtonComponent button;
    private StundensaetzeScreen screen;
    private MainRoot root;

    public DelRowLogic(final MainRoot root, final StundensaetzeScreen screen, final ButtonComponent button) {
        this.screen = screen;
        this.button = button;
        this.root = root;
    }

    public void execute() {
        final RessourceGroupsBean ressourceGroupsBean = root.getRessourceGroupsBean();
        final RessourceGroup ressourceGroupFromTable = (RessourceGroup )button.getItemId();
        final List<RessourceGroup> ressourceGroups = ressourceGroupsBean.getRessourceGroups();
        ressourceGroups.remove(ressourceGroupFromTable);

        root.setPlanningUnitsBean(new ProjektBean(ressourceGroupsBean));
        screen.getFormLayout().setVisible(false);
        root.setWorkingArea(new StundensaetzeScreen(root));
    }
}
