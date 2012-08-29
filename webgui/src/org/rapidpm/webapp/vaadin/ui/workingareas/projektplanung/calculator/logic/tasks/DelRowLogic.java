package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.logic.tasks;

import org.rapidpm.webapp.vaadin.MainRoot;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.CalculatorScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.uicomponents.ButtonComponent;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.ProjektBean;

public class DelRowLogic {
    private ButtonComponent button;
    private CalculatorScreen screen;
    private MainRoot root;

    public DelRowLogic(MainRoot root, CalculatorScreen screen, ButtonComponent button) {
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
        root.setWorkingArea(new CalculatorScreen(root));
    }
}
