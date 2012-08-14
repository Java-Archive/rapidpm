package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.logic;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.AufwandProjInitScreen;

public class SaveButtonClickListener implements ClickListener {
    private FieldGroup fieldGroup;
    private AufwandProjInitScreen screen;

    public SaveButtonClickListener(FieldGroup fieldGroup, AufwandProjInitScreen screen) {
        this.fieldGroup = fieldGroup;
        this.screen = screen;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        final ProjInitComputer computer = new ProjInitComputer(screen);
        try {
            fieldGroup.commit();
            screen.getTreeTable().setValue(null);
            if (fieldGroup.getFields().size() > 1) {
                computer.compute();
                computer.setValuesInScreen();
            }
            screen.getFormLayout().setVisible(false);
        } catch (CommitException e) {
            //tue nichts falls commit nicht erfolgreich war
        }
    }

}
