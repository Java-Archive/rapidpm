package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components;

import com.vaadin.ui.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;

import java.util.*;

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
    private ArrayList<TextArea> testCasesAreas = new ArrayList<>();
    private ResourceBundle messages;

    public PlanningMainMyFormLayout(final IssueBase issueBase, final ProjektplanungScreen screen, final Panel screenPanel) {
        super(issueBase, screen, screenPanel);
        this.messages = screen.getMessagesBundle();
        buildDescriptionTextArea(issueBase);
        buildStoryPointsListSelect(issueBase);
        Integer testCaseCounter = 0;
        final List<String> testcases = issueBase.getTestcases();
        for (final String testCase : testcases) {
            final int counter = ++testCaseCounter;
            buildTestCaseArea(counter, testCase);
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
                for (final TextArea testCaseArea : testCasesAreas) {
                    for (final String testCase : testcases) {
                        if (testCase.equals(testCaseArea.getCaption())) {
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
                final String descriptionAreaValue = descriptionTextArea.getValue();
                final String storyPointsComboBoxValue = storyPointsComboBox.getValue().toString();
                final Iterator<Component> componentIterator = componentsLayout.getComponentIterator();
                issueBase.setText(descriptionAreaValue);
                issueBase.setStoryPoints(Integer.parseInt(storyPointsComboBoxValue));
                Integer index = 0;
                for (final TextArea testCaseArea : testCasesAreas) {
                    testcases.set(index, testCaseArea.getValue());
                    index++;
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
    }

    private void buildTestCaseArea(final Integer testCaseCounter, final String testCase) {
        final TextArea testCaseArea = new TextArea(messages.getString("planning_testcase") + testCaseCounter,
                testCase);
        testCaseArea.setReadOnly(true);
        testCaseArea.setSizeFull();
        testCasesAreas.add(testCaseArea);
    }

    private void buildDescriptionTextArea(final IssueBase issueBase) {
        descriptionTextArea = new TextArea(messages.getString("planning_description"));
        descriptionTextArea.setValue(issueBase.getText());
        descriptionTextArea.setReadOnly(true);
        descriptionTextArea.setSizeFull();
    }

    private void buildStoryPointsListSelect(final IssueBase issueBase) {
        final Integer[] storyPoints = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; //TODO diese Liste muss in der DB
        // pro Mandant konfigurierbar sein
        storyPointsComboBox = new ComboBox(messages.getString("planning_storypoints"), Arrays.asList(storyPoints));
        storyPointsComboBox.setValue(issueBase.getStoryPoints());
        storyPointsComboBox.setNullSelectionAllowed(false);
        storyPointsComboBox.setReadOnly(true);
    }

    @Override
    protected void buildForm() {
        componentsLayout.addComponent(descriptionTextArea);
        componentsLayout.addComponent(storyPointsComboBox);
        for (final TextArea testCaseArea : testCasesAreas) {
            componentsLayout.addComponent(testCaseArea);
        }
    }


}
