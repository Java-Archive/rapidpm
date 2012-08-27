package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.uicomponents;

import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.rapidpm.webapp.vaadin.MainRoot;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.CalculatorScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.ProjektBean;

import java.util.Iterator;

public class AddRowWindow extends Window {
    private MainRoot root;

    private GridLayout gridLayout = new GridLayout(2, 2);
    private HorizontalLayout horizontalButtonLayout = new HorizontalLayout();
    private Button saveButton = new Button("Save");
    private Button cancelButton = new Button("Cancel");
    private Table tabelle;
    private RowFieldGroup fieldGroup;

    public AddRowWindow(final MainRoot root, final CalculatorScreen screen) {
        this.root = root;
        tabelle = screen.getTabelle();
        setCaption("Ressource hinzufügen");
        setHeight("400px");
        setWidth("400px");
        setPositionX(50);
        setPositionY(100);

        final RessourceGroup row = new RessourceGroup();
        fieldGroup = new RowFieldGroup(row);

        for (Component comp : fieldGroup.getComponents()) {
            gridLayout.addComponent(comp);
        }
        addComponent(gridLayout);

        horizontalButtonLayout.addComponent(saveButton);
        horizontalButtonLayout.addComponent(cancelButton);

        addComponent(horizontalButtonLayout);

        addListeners(row, root, screen);

    }

    private void addListeners(final RessourceGroup ressourceGroup, final MainRoot root, final CalculatorScreen screen) {
        final RessourceGroup row = ressourceGroup;
        saveButton.addListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                boolean allFilled = true;
                Iterator<Component> it = AddRowWindow.this.gridLayout
                        .getComponentIterator();
                while (it.hasNext()) {
                    final Component component = it.next();
                    if (component instanceof AbstractField) {
                        if (((TextField) component).getValue() == null || ((TextField) component).getValue().equals(""))
                            allFilled = false;
                    }
                }
                if (allFilled) {
                    try {
                        fieldGroup.commit();
                        final RessourceGroupsBean ressourceGroupsBean = root.getRessourceGroupsBean();
                        ressourceGroupsBean.getRessourceGroups().add(row);
                        root.setPlanningUnitsBean(new ProjektBean(ressourceGroupsBean));
                        System.out.println(ressourceGroupsBean.getRessourceGroups());
                        AddRowWindow.this.close();
                        root.setWorkingArea(new CalculatorScreen(root));
                    } catch (CommitException e) {
                        // don't try to commit or to close window if commit
                        // fails
                    }

                } else {
                    AddRowWindow.this.addComponent(new Label(
                            "Alle Felder ausfuellen"));
                }

            }

        });

        cancelButton.addListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                AddRowWindow.this.close();
            }
        });

    }

    public void show() {
        root.addWindow(this);
    }
}
