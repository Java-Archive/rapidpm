package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents;

import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroupDAO;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;

import java.util.Iterator;
import java.util.ResourceBundle;

public class AddRowWindow extends Window {
    public static final String HEIGHT = "400px";
    public static final String WIDTH = "400px";
    public static final int POSITION_X = 50;
    public static final int POSITION_Y = 100;

    private static final Logger logger = Logger.getLogger(AddRowWindow.class);

    private MainUI ui;

    private FormLayout formLayout = new FormLayout();
    private HorizontalLayout horizontalButtonLayout = new HorizontalLayout();
    private Button saveButton = new Button();
    private Button cancelButton = new Button();
    private RowFieldGroup fieldGroup;
    private ResourceBundle messages;

    public AddRowWindow(final MainUI ui, final StundensaetzeScreen screen) {
        this.ui = ui;
        messages = screen.getMessagesBundle();
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setPositionX(POSITION_X);
        setPositionY(POSITION_Y);

        final RessourceGroup row = new RessourceGroup();
        fieldGroup = new RowFieldGroup(row);

        fillFormLayout();
        addComponent(formLayout);

        horizontalButtonLayout.addComponent(saveButton);
        horizontalButtonLayout.addComponent(cancelButton);

        addComponent(horizontalButtonLayout);

        addListeners(row, ui, screen);
        doInternationalization();

    }

    private void fillFormLayout() {
        final AbstractTextField nameField = fieldGroup.getNameField();
        formLayout.addComponent(buildFieldWithValue(nameField));
        formLayout.addComponent(new Label("-----------"));
        for (AbstractTextField abstractTextField : fieldGroup.getFieldList()) {
            formLayout.addComponent(buildFieldWithValue(abstractTextField));
        }
    }

    private TextField buildFieldWithValue(AbstractTextField abstractTextField) {
        final TextField field = (TextField) abstractTextField;
        final String typeNameOfFieldProperty = field.getPropertyDataSource().getType().getSimpleName();
        field.setValue(DefaultValues.valueOf(typeNameOfFieldProperty).getDefaultValue());
        return field;
    }

    private void doInternationalization() {
        this.setCaption(messages.getString("stdsatz_addRessource"));
        saveButton.setCaption(messages.getString("save"));
        cancelButton.setCaption(messages.getString("cancel"));
    }

    private void addListeners(final RessourceGroup ressourceGroup, final MainUI ui, final StundensaetzeScreen screen) {
        final RessourceGroup row = ressourceGroup;
        saveButton.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                boolean allFilled = true;
                Iterator<Component> it = AddRowWindow.this.formLayout
                        .getComponentIterator();
                while (it.hasNext()) {
                    final Component component = it.next();
                    if (component instanceof AbstractField) {
                        if (((TextField) component).getValue() == null
                                || ((TextField) component).getValue().equals(""))
                            allFilled = false;
                    }
                }
                if (allFilled) {
                    try {
                        fieldGroup.commit();
                        final DaoFactoryBean baseDaoFactoryBean = screen.getStammdatenScreensBean().getDaoFactoryBean();
                        final RessourceGroupDAO ressourceGroupDAO = baseDaoFactoryBean.getRessourceGroupDAO();
                        //ressourceGroupDAO.saveOrUpdate(row);  TODO RPM-41
                        AddRowWindow.this.close();
                        screen.generateTableAndCalculate();
                    } catch (CommitException e) {
                        logger.warn(e);
                    }

                } else {
                    final Label lbl = new Label();
                    lbl.setValue(messages.getString("stdsatz_fillInAllFields"));
                    AddRowWindow.this.addComponent(lbl);
                }

            }

        });

        cancelButton.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                AddRowWindow.this.close();
            }
        });

    }

    public void show() {
        ui.addWindow(this);
    }
}
