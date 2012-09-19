package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Panel;
import org.rapidpm.webapp.vaadin.BaseUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Componentssetable;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.Projekt;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 19.09.12
 * Time: 09:43
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningUnitGroupPanel extends Panel implements Internationalizationable, Componentssetable{

    private HorizontalLayout buttonsLayout = new HorizontalLayout();
    private Button addButton = new Button();
    private Button deleteButton = new Button();

    private Projekt projekt;
    private ListSelect listSelect;
    private BaseUI ui;
    private ProjektplanungScreen screen;

    public PlanningUnitGroupPanel(BaseUI theui, ProjektplanungScreen scr, Projekt projekt, ListSelect listSelect){
        this.ui = theui;
        this.screen = scr;
        this.listSelect = listSelect;
        this.projekt = projekt;
        setCaption(projekt.getProjektName());
        setComponents();
        doInternationalization();
        addButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                final AddPlanningUnitGroupWindow window = new AddPlanningUnitGroupWindow(ui, screen);
                window.show();
            }
        });
        buttonsLayout.addComponent(addButton);
        buttonsLayout.addComponent(deleteButton);
    }

    @Override
    public void setComponents() {
        addComponent(buttonsLayout);
        addComponent(listSelect);
    }

    @Override
    public void doInternationalization() {
        addButton.setCaption("+");
        deleteButton.setCaption("-");
    }

    public void setSelectedPlanningUnitGroup(PlanningUnitGroup selectedPlanningUnitGroup) {
        if(selectedPlanningUnitGroup == null){
            deleteButton.setVisible(false);
        } else {
            deleteButton.setVisible(true);
        }
    }
}
