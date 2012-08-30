package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.logic;

import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ProjektplanungScreen;

import java.util.Arrays;
import java.util.Iterator;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 30.08.12
 * Time: 09:15
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningMainMyFormLayout extends MyFormLayout {

    private TextArea descriptionTextArea;
    private ComboBox storyPointsComboBox;

    public PlanningMainMyFormLayout(final IssueBase issueBase, final ProjektplanungScreen screen, final Panel screenPanel){
        super(issueBase, screen, screenPanel);
        buildDescriptionTextArea(issueBase);
        buildStoryPointsListSelect(issueBase);
        buildForm();

        cancelButton.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                final String issueText = issueBase.getText();
                final Integer issueStoryPoints = issueBase.getStoryPoints();
                final Iterator<Component> componentIterator = componentsLayout.getComponentIterator();
                descriptionTextArea.setValue(issueText);
                storyPointsComboBox.setValue(issueStoryPoints);
                while (componentIterator.hasNext()) {
                    final Component component = componentIterator.next();
                    if (component instanceof Field) {
                        component.setReadOnly(true);
                    }
                }
                buttonLayout.setVisible(false);
            }
        });

        saveButton.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                final String descriptionAreaValue = descriptionTextArea.getValue().toString();
                final String storyPointsComboBoxValue = storyPointsComboBox.getValue().toString();
                //final String planningUnitGroupListSelection = screen.getProjektSelect().getValue().toString();
                final Iterator<Component> componentIterator = componentsLayout.getComponentIterator();
                issueBase.setText(descriptionAreaValue);
                issueBase.setStoryPoints(Integer.parseInt(storyPointsComboBoxValue));

                //screen.fillTreePanel(planningUnitGroupListSelection);

                while(componentIterator.hasNext()){
                    final Component component = componentIterator.next();
                    if( component instanceof Field){
                        component.setReadOnly(true);
                    }
                }
                buttonLayout.setVisible(false);
            }
        });
    }

    private void buildDescriptionTextArea(IssueBase issueBase) {
        descriptionTextArea = new TextArea("Beschreibung");
        descriptionTextArea.setValue(issueBase.getText());
        descriptionTextArea.setReadOnly(true);
        descriptionTextArea.setSizeFull();
    }

    private void buildStoryPointsListSelect(IssueBase issueBase) {
        final Integer[] storyPoints = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        storyPointsComboBox = new ComboBox("Storypoints", Arrays.asList(storyPoints) );
        storyPointsComboBox.setValue(issueBase.getStoryPoints());
        storyPointsComboBox.setNullSelectionAllowed(false);
        storyPointsComboBox.setReadOnly(true);
    }

    @Override
    protected void buildForm(){
        componentsLayout.addComponent(descriptionTextArea);
        componentsLayout.addComponent(storyPointsComboBox);
    }


}
