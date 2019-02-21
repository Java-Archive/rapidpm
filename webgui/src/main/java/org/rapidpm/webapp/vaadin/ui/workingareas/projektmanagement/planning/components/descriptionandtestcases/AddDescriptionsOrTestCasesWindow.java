package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.descriptionandtestcases;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.textelement.TextElement;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 02.01.13
 * Time: 22:28
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class AddDescriptionsOrTestCasesWindow extends RapidWindow {

    private TextField quantityField;
    private ComboBox comboBox;
    private Button okButton;
    private Button cancelButton;
    private HorizontalLayout buttonLayout;
    private final PlanningUnit selectedPlanningUnitInTree;
    private ResourceBundle messages;

    public AddDescriptionsOrTestCasesWindow(final PlanningUnit selectedPlanningUnitInTree, final ResourceBundle messages) {
        this.selectedPlanningUnitInTree = selectedPlanningUnitInTree;
        this.messages = messages;
        buttonLayout = new HorizontalLayout();
        quantityField = new TextField(messages.getString("planning_quantity"));
        //quantityField.addValidator(new IntegerRangeValidator("0-99", 1, 99)); Validatoren wohl zur Zeit nicht
        // funktionstüchtig. Errorsymbol wird selbst beim ersten laden des Fensters angezeigt. (http://dev.vaadin
        // .com/ticket/10561)
        quantityField.focus();
        quantityField.setWidth("40px");
        quantityField.setValue("1");
        comboBox = new ComboBox(messages.getString("planning_kind"), Arrays.asList(DescriptionTestcaseEnum.values()));
//        comboBox.setNullSelectionAllowed(false);
//        comboBox.setTextInputAllowed(false);
        comboBox.setValue(DescriptionTestcaseEnum.DESCRIPTION);
        okButton = new Button(messages.getString("ok"));
//        okButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        cancelButton = new Button(messages.getString("cancel"));
//        buttonLayout.addComponents(okButton, cancelButton);

        setButtonClickListeners();

        add(quantityField);
        add(comboBox);
        add(buttonLayout);

        doInternationalization();


    }

    private void setButtonClickListeners() {
//        cancelButton.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                close();
//            }
//        });
//        okButton.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                quantityField.commit();
//                //if(quantityField.isValid()){
//                try{
//                    createNewTextElements();
//                    close();
//                    ui.setWorkingArea(new ProjektplanungScreen(ui));
//                } catch(final NumberFormatException e){
//                    Notification.show("1-99!");
//                }
//                //} else {
//                //    System.out.println("."+quantityField.getValue()+".");
//                //}
//
//            }
//        });
    }

    private void createNewTextElements() {
        final Integer quantity = Integer.valueOf(quantityField.getValue());
        final List<TextElement> newTextElements = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            final TextElement textElement = new TextElement();
            if((comboBox.getValue()).equals(DescriptionTestcaseEnum.DESCRIPTION)){
                textElement.setBezeichnung(messages.getString("planning_descriptionNoTitle"));
                textElement.setText(messages.getString("planning_descriptionNoText"));
            } else {
                textElement.setBezeichnung(messages.getString("planning_testcaseNoTitle"));
                textElement.setText(messages.getString("planning_testcaseNoText"));
            }
            newTextElements.add(textElement);
        }
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        daoFactory.new Transaction() {
            @Override
            public void doTask() {
                final EntityManager entityManager = daoFactory.getEntityManager();
                for (final TextElement newTextElement : newTextElements) {
                    if((comboBox.getValue()).equals(DescriptionTestcaseEnum.DESCRIPTION)){
                       if(selectedPlanningUnitInTree.getDescriptions() == null)
                           selectedPlanningUnitInTree.setDescriptions(new ArrayList<TextElement>());
                        selectedPlanningUnitInTree.getDescriptions().add(newTextElement);
                    } else {
                        if(selectedPlanningUnitInTree.getTestcases() == null)
                            selectedPlanningUnitInTree.setTestcases(new ArrayList<TextElement>());
                        selectedPlanningUnitInTree.getTestcases().add(newTextElement);
                    }
                    entityManager.persist(newTextElement);
                }
                entityManager.flush();

            }
        }.execute();


    }

    private void doInternationalization() {
//        setText(messages.getString("add"));
    }
}
