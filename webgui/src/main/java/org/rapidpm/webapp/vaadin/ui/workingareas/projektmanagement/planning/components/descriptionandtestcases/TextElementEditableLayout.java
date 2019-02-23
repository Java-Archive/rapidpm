package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.descriptionandtestcases;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.rapidpm.Constants;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.textelement.TextElement;
import org.rapidpm.webapp.vaadin.ui.EditableLayout;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;

import javax.persistence.EntityManager;
import java.util.Iterator;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 14.12.12
 * Time: 11:57
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class TextElementEditableLayout extends EditableLayout {

    private Button deleteButton = new Button("-");
    private TextField bezeichnungField;
    private TextArea textElementTextArea;
    private final PlanningUnit selectedPlanningUnit;
    private ProjektplanungScreen screen;

    public TextElementEditableLayout(final PlanningUnit selectedPlanningUnit, final ProjektplanungScreen screen, final TextElement textElement){
        super();
        this.selectedPlanningUnit = selectedPlanningUnit;
        this.screen = screen;
        bezeichnungField = new TextField();
        bezeichnungField.setValue(textElement.getBezeichnung());
        bezeichnungField.setReadOnly(true);
        bezeichnungField.setWidth("100%");
        textElementTextArea = new TextArea("", textElement.getText());
        textElementTextArea.setSizeUndefined();
        textElementTextArea.setWidth("100%");
        buildForm();
//        textElementTextArea.addReadOnlyStatusChangeListener(new Property.ReadOnlyStatusChangeListener() {
//            @Override
//            public void readOnlyStatusChange(Property.ReadOnlyStatusChangeEvent event) {
//                final RichTextArea textArea = (RichTextArea) event.getProperty();
//                if (textArea.isReadOnly()) {
//                    textArea.setSizeUndefined();
//                    textArea.setWidth("100%");
//                } else {
//                    textArea.setHeight(Constants.TEXTAREA_WRITABLE_HEIGHT);
//                }
//            }
//        });
        saveButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> event) {
                Notification.show("TO BE DONE!");
            }
        });
//        saveButton.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                updateTextElement(textElement);
//            }
//        });
        cancelButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            final Iterator<Component> componentIterator = componentsLayout.getChildren().iterator();
                    textElementTextArea.setValue(textElement.getText());
                    bezeichnungField.setValue(textElement.getBezeichnung());
                    while (componentIterator.hasNext()) {
                        final Component component = componentIterator.next();
                        if (component instanceof AbstractField) {
                            ((AbstractField) component).setReadOnly(true);
                        }
                    }
                    buttonLayout.setVisible(false);
        });
        deleteButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> Notification.show("TO BE DONE!"));
//        deleteButton.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                final ResourceBundle messagesBundle = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);
////                ConfirmDialog.show(screen.getUi(), messagesBundle.getString("confirm"),
////                        messagesBundle.getString("project_confirmdelete"), messagesBundle.getString("ok"),
////                        messagesBundle.getString("cancel"),
////                        new ConfirmDialog.Listener() {
////                            @Override
////                            public void onClose(ConfirmDialog dialog) {
////                                if (dialog.isConfirmed()) {
//                                    deleteTextElement(textElement);
////                                }
////                            }
////                        });
//            }
//        });

    }

    private void updateTextElement(final TextElement textElement) {
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        if(selectedPlanningUnit.getDescriptions().contains(textElement)){
            for (final TextElement description : selectedPlanningUnit.getDescriptions()) {
                if(description.equals(textElement)){
                    description.setBezeichnung(bezeichnungField.getValue());
                    description.setText(textElementTextArea.getValue());
                    daoFactory.new Transaction() {
                        @Override
                        public void doTask() {
                            final EntityManager entityManager = daoFactory.getEntityManager();
                            entityManager.merge(textElement);
                        }
                    }.execute();
                }
            }
        } else if(selectedPlanningUnit.getTestcases().contains(textElement)) {
            for (final TextElement testcase : selectedPlanningUnit.getTestcases()) {
                if(testcase.equals(textElement)){
                    testcase.setBezeichnung(bezeichnungField.getValue());
                    testcase.setText(textElementTextArea.getValue());
                    daoFactory.new Transaction() {
                        @Override
                        public void doTask() {
                            final EntityManager entityManager = daoFactory.getEntityManager();
                            entityManager.merge(textElement);
                        }
                    }.execute();
                }
            }
        }
        daoFactory.new Transaction() {
            @Override
            public void doTask() {
                final EntityManager entityManager = daoFactory.getEntityManager();
                entityManager.persist(selectedPlanningUnit);
                entityManager.flush();
                entityManager.refresh(selectedPlanningUnit);
            }
        }.execute();
//        screen.getUi().setWorkingArea(new ProjektplanungScreen(screen.getUi()));
    }

    private void deleteTextElement(final TextElement textElement) {
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        if(selectedPlanningUnit.getDescriptions().contains(textElement)){
            selectedPlanningUnit.getDescriptions().remove(textElement);
        } else if(selectedPlanningUnit.getTestcases().contains(textElement)) {
            selectedPlanningUnit.getTestcases().remove(textElement);
        }
        daoFactory.new Transaction() {
            @Override
            public void doTask() {
                final EntityManager entityManager = daoFactory.getEntityManager();
                entityManager.merge(selectedPlanningUnit);
                entityManager.remove(textElement);
                entityManager.flush();
                entityManager.refresh(selectedPlanningUnit);
            }
        }.execute();
//        screen.getUi().setWorkingArea(new ProjektplanungScreen(screen.getUi()));
    }


    @Override
    protected void buildForm() {
        componentsLayout.setSizeFull();
        componentsLayout.add(bezeichnungField);
        componentsLayout.add(deleteButton);
        componentsLayout.add(textElementTextArea);
//        ((VerticalLayout)componentsLayout).setComponentAlignment(textElementTextArea, Alignment.TOP_CENTER);
        textElementTextArea.setReadOnly(true);
    }

    @Override
    protected void setLayout() {
//        componentsLayout = new VerticalLayout();
    }
}
