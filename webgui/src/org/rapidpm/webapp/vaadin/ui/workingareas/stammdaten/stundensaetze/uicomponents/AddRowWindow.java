package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents;

import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.rapidpm.webapp.vaadin.MainRoot;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;

import java.util.Iterator;
import java.util.ResourceBundle;

public class AddRowWindow extends Window {
    public static final String HEIGHT = "400px";
    public static final String WIDTH = "400px";
    public static final int POSITION_X = 50;
    public static final int POSITION_Y = 100;
    private MainRoot root;

    private GridLayout gridLayout = new GridLayout(2, 2);
    private HorizontalLayout horizontalButtonLayout = new HorizontalLayout();
    private Button saveButton = new Button();
    private Button cancelButton = new Button();
    private Table tabelle;
    private RowFieldGroup fieldGroup;
    private ResourceBundle messages;

    public AddRowWindow(final MainRoot root, final StundensaetzeScreen screen) {
        this.root = root;
        messages = screen.getMessagesBundle();
        tabelle = screen.getTabelle();
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setPositionX(POSITION_X);
        setPositionY(POSITION_Y);

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
        doInternationalization();

    }

    private void doInternationalization() {
        this.setCaption(messages.getString("stdsatz_addRessource"));
        saveButton.setCaption(messages.getString("save"));
        cancelButton.setCaption(messages.getString("cancel"));
    }

    private void addListeners(final RessourceGroup ressourceGroup, final MainRoot root, final StundensaetzeScreen screen) {
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
                        AddRowWindow.this.close();
                        root.setWorkingArea(new StundensaetzeScreen(root));
                    } catch (CommitException e) {
                        // don't try to commit or to close window if commit
                        // fails
                    }

                } else {
                    Label lbl = new Label();
                    lbl.setValue(messages.getString("stdsatz_fillInAllFields"));
                    AddRowWindow.this.addComponent(lbl);
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
