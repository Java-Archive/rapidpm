package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.descriptionandtestcases;

import com.vaadin.data.Property;
import com.vaadin.ui.*;
import org.rapidpm.Constants;
import org.rapidpm.persistence.prj.textelement.TextElement;
import org.rapidpm.webapp.vaadin.ui.EditableLayout;
import org.rapidpm.webapp.vaadin.ui.EditableRapidPanel;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;

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
    private RichTextArea descriptionTextArea;

    public TextElementEditableLayout(ProjektplanungScreen screen, RapidPanel descriptionsPanel, final ResourceBundle messages, final TextElement description){
        super(screen, descriptionsPanel);
        setCaption(description.getBezeichnung());
        descriptionTextArea = new RichTextArea("", description.getText());
        descriptionTextArea.setSizeUndefined();
        descriptionTextArea.setWidth("100%");
        buildForm();
        descriptionTextArea.addReadOnlyStatusChangeListener(new Property.ReadOnlyStatusChangeListener() {
            @Override
            public void readOnlyStatusChange(Property.ReadOnlyStatusChangeEvent event) {
                final RichTextArea textArea = (RichTextArea)event.getProperty();
                if(textArea.isReadOnly()){
                    textArea.setSizeUndefined();
                    textArea.setWidth("100%");
                } else {
                    textArea.setHeight(Constants.TEXTAREA_WRITABLE_HEIGHT);
                }
            }
        });
        cancelButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                        final Iterator<Component> componentIterator = componentsLayout.iterator();
                        while (componentIterator.hasNext()) {
                            final Component component = componentIterator.next();
                            if (component instanceof AbstractField) {
                                component.setReadOnly(true);
                            }
                        }
                        buttonLayout.setVisible(false);
            }
        });

    }


    @Override
    protected void buildForm() {
        componentsLayout.setSizeFull();
        componentsLayout.addComponent(descriptionTextArea);
        ((VerticalLayout)componentsLayout).setComponentAlignment(descriptionTextArea, Alignment.TOP_CENTER);
        descriptionTextArea.setReadOnly(true);
    }

    @Override
    protected void setLayout() {
        componentsLayout = new VerticalLayout();
    }
}
