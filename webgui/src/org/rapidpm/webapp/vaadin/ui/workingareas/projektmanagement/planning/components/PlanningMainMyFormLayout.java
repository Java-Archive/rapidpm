package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components;

import com.vaadin.ui.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;

import java.util.ArrayList;
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

    private static final String STORYPOINTS = "Storypoints";
    private static final String BESCHREIBUNG = "Beschreibung";
    private static final String TESTCASE = "Testcase ";
    private TextArea descriptionTextArea;
    private ComboBox storyPointsComboBox;
    private ArrayList<TextArea> testCasesAreas = new ArrayList<>();

    public PlanningMainMyFormLayout(final IssueBase issueBase, final ProjektplanungScreen screen, final Panel screenPanel){
        super(issueBase, screen, screenPanel);
        buildDescriptionTextArea(issueBase);
        buildStoryPointsListSelect(issueBase);
        Integer testCaseCounter = 0;
        for(String testCase :issueBase.getTestcases()){
            buildTestCaseArea(++testCaseCounter, testCase);
        }
        buildForm();

        cancelButton.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                final String issueText = issueBase.getText();
                final Integer issueStoryPoints = issueBase.getStoryPoints();
                final Iterator<Component> componentIterator = componentsLayout.getComponentIterator();
                descriptionTextArea.setValue(issueText);
                storyPointsComboBox.setValue(issueStoryPoints);
                for(final TextArea testCaseArea : testCasesAreas){
                    for(final String testCase : issueBase.getTestcases()){
                        if(testCase.equals(testCaseArea.getCaption())){
                            testCaseArea.setValue(testCase);
                        }
                    }
                }
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
                final Iterator<Component> componentIterator = componentsLayout.getComponentIterator();
                issueBase.setText(descriptionAreaValue);
                issueBase.setStoryPoints(Integer.parseInt(storyPointsComboBoxValue));
                Integer index = 0;
                for(final TextArea testCaseArea : testCasesAreas){
                    issueBase.getTestcases().set(index, testCaseArea.getValue());
                    index++;
                }

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

    private void buildTestCaseArea(Integer testCaseCounter, String testCase) {
        final TextArea testCaseArea = new TextArea(TESTCASE +testCaseCounter, testCase);
        testCaseArea.setReadOnly(true);
        testCaseArea.setSizeFull();
        testCasesAreas.add(testCaseArea);
    }

    private void buildDescriptionTextArea(IssueBase issueBase) {
        descriptionTextArea = new TextArea(BESCHREIBUNG);
        descriptionTextArea.setValue(issueBase.getText());
        descriptionTextArea.setReadOnly(true);
        descriptionTextArea.setSizeFull();
    }

    private void buildStoryPointsListSelect(IssueBase issueBase) {
        final Integer[] storyPoints = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        storyPointsComboBox = new ComboBox(STORYPOINTS, Arrays.asList(storyPoints) );
        storyPointsComboBox.setValue(issueBase.getStoryPoints());
        storyPointsComboBox.setNullSelectionAllowed(false);
        storyPointsComboBox.setReadOnly(true);
    }

    @Override
    protected void buildForm(){
        componentsLayout.addComponent(descriptionTextArea);
        componentsLayout.addComponent(storyPointsComboBox);
        for(final TextArea testCaseArea : testCasesAreas){
            componentsLayout.addComponent(testCaseArea);
        }
    }


}
