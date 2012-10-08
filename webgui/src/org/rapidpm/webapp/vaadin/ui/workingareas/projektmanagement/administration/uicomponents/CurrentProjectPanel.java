package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.Projekt;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;

import java.util.List;
import java.util.ResourceBundle;

import static org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ProjektFieldGroup.PROJEKT_NAME;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 18.09.12
 * Time: 09:12
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class CurrentProjectPanel extends EditablePanel {


    private ComboBox currentProjectBox;
    private ProjektBean projektBean;


    public CurrentProjectPanel(ResourceBundle messagesBundle, ProjektBean projBean){
        super(messagesBundle);
        this.projektBean = projBean;
        setCaption(messagesBundle.getString("pm_currentproject"));
        removeAllComponents();
        final List<Projekt> projektList = projektBean.getProjekte();
        if(projektList.isEmpty()){
            addComponent(new Label(messagesBundle.getString("pm_noprojects")));
        } else {
            currentProjectBox = new ComboBox("", new BeanItemContainer<>(Projekt.class, projektList));
            currentProjectBox.setNullSelectionAllowed(false);
            currentProjectBox.setTextInputAllowed(false);
            currentProjectBox.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            currentProjectBox.setItemCaptionPropertyId(PROJEKT_NAME);
            final Integer currentProjectIndex = projektBean.getCurrentProjectIndex();
            currentProjectBox.setValue(projektList.get(currentProjectIndex));
            buttonsLayout.addComponent(saveButton);
            buttonsLayout.addComponent(cancelButton);
            activate(false);

            cancelButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    activate(false);
                }
            });
            saveButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    projektBean.setCurrentProjectIndex(projektList.indexOf(currentProjectBox.getValue()));
                    activate(false);
                }
            });
            setComponents();
        }

    }

    @Override
    public void activate(boolean b) {
        buttonsLayout.setVisible(b);
        currentProjectBox.setReadOnly(!b);
    }

    @Override
    public void doInternationalization() {
        cancelButton.setCaption(messagesBundle.getString("cancel"));
        saveButton.setCaption(messagesBundle.getString("save"));
    }

    @Override
    public void setComponents() {
        addComponent(currentProjectBox);
        addComponent(buttonsLayout);
    }
}
