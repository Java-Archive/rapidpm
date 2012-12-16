package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.descriptionandtestcases;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.RichTextArea;
import org.rapidpm.persistence.prj.textelement.TextElement;
import org.rapidpm.webapp.vaadin.ui.EditableLayout;
import org.rapidpm.webapp.vaadin.ui.EditableRapidPanel;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;

import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 14.12.12
 * Time: 11:57
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class DescriptionEditableLayout extends EditableLayout {

    private Button deleteButton = new Button("-");
    private RichTextArea descriptionTextArea;

    public DescriptionEditableLayout(ProjektplanungScreen screen, RapidPanel descriptionsPanel, final ResourceBundle messages, final TextElement description){
        super(screen, descriptionsPanel);
        setCaption(description.getBezeichnung());
        descriptionTextArea = new RichTextArea(description.getBezeichnung(), description.getText());
    }


    @Override
    protected void buildForm() {
        componentsLayout.addComponent(descriptionTextArea);
    }
}
