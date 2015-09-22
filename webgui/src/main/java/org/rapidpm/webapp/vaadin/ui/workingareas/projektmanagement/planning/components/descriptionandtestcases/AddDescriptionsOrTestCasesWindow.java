package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.descriptionandtestcases;

import com.vaadin.data.Validator;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.textelement.TextElement;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;

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

  private final PlanningUnit selectedPlanningUnitInTree;
  private final MainUI ui;
  private TextField quantityField;
  private ComboBox comboBox;
  private Button okButton;
  private Button cancelButton;
  private HorizontalLayout buttonLayout;
  private ResourceBundle messages;

  public AddDescriptionsOrTestCasesWindow(final PlanningUnit selectedPlanningUnitInTree, final MainUI ui,
                                          final ResourceBundle messages) {
    this.selectedPlanningUnitInTree = selectedPlanningUnitInTree;
    this.ui = ui;
    this.messages = messages;
    buttonLayout = new HorizontalLayout();
    quantityField = new TextField(messages.getString("planning_quantity"));
    quantityField.setValue("1");
    quantityField.setConverter(new StringToIntegerConverter());
    quantityField.addValidator(new IntegerRangeValidator("0-99", 1, 99));
    quantityField.setValidationVisible(true);
    quantityField.validate();
    quantityField.focus();
    quantityField.setWidth("40px");
    quantityField.addBlurListener(blurEvent -> {
      try {
        quantityField.validate();
      } catch (Validator.InvalidValueException e) {
        // do nothing, error is shown in GUI
      }
    });

    comboBox = new ComboBox(messages.getString("planning_kind"), Arrays.asList(DescriptionTestcaseEnum.values()));
    comboBox.setNullSelectionAllowed(false);
    comboBox.setTextInputAllowed(false);
    comboBox.setValue(DescriptionTestcaseEnum.DESCRIPTION);
    okButton = new Button(messages.getString("ok"));
    okButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
    cancelButton = new Button(messages.getString("cancel"));
    buttonLayout.addComponents(okButton, cancelButton);

    setButtonClickListeners();

    addComponent(quantityField);
    addComponent(comboBox);
    addComponent(buttonLayout);

    doInternationalization();


  }

  private void setButtonClickListeners() {
    cancelButton.addClickListener(event -> close());
    okButton.addClickListener(event -> {
      quantityField.commit();
      if (quantityField.isValid()) {
        try {
          createNewTextElements();
          close();
          ui.setWorkingArea(new ProjektplanungScreen(ui));
        } catch (final NumberFormatException e) {
          Notification.show("1-99!");
        }
      } else {
        Notification.show("1-99!");
      }
    });
  }

  private void doInternationalization() {
    setCaption(messages.getString("add"));
  }

  private void createNewTextElements() {
    final Integer quantity = Integer.valueOf(quantityField.getValue());
    final List<TextElement> tempNewTextElements = new ArrayList<>();
    for (int i = 0; i < quantity; i++) {
      final TextElement textElement = new TextElement();
      if ((comboBox.getValue()).equals(DescriptionTestcaseEnum.DESCRIPTION)) {
        textElement.setBezeichnung(messages.getString("planning_descriptionNoTitle"));
        textElement.setText(messages.getString("planning_descriptionNoText"));
      } else {
        textElement.setBezeichnung(messages.getString("planning_testcaseNoTitle"));
        textElement.setText(messages.getString("planning_testcaseNoText"));
      }
      tempNewTextElements.add(textElement);
    }
    final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
    if ((comboBox.getValue()).equals(DescriptionTestcaseEnum.DESCRIPTION)) {
      for (TextElement newTextElement : tempNewTextElements) {
        if (selectedPlanningUnitInTree.getDescriptions() == null) {
          selectedPlanningUnitInTree.setDescriptions(new ArrayList<TextElement>());
        }
        newTextElement = daoFactory.getTextElementDAO().createEntityFlat(newTextElement);
        daoFactory.getPlanningUnitDAO().addDescriptionToPlanningUnit(newTextElement, selectedPlanningUnitInTree);
        selectedPlanningUnitInTree.getDescriptions().add(newTextElement);
      }
    } else {
      for (TextElement newTextElement : tempNewTextElements) {
        if (selectedPlanningUnitInTree.getTestcases() == null) {
          selectedPlanningUnitInTree.setTestcases(new ArrayList<TextElement>());
        }
        newTextElement = daoFactory.getTextElementDAO().createEntityFlat(newTextElement);
        daoFactory.getPlanningUnitDAO().addTestCaseToPlanningUnit(newTextElement, selectedPlanningUnitInTree);
        selectedPlanningUnitInTree.getTestcases().add(newTextElement);
      }
    }
  }
}
