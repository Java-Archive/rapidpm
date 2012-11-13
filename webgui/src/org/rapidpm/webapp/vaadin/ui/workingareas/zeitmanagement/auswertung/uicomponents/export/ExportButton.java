package org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.uicomponents.export;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 07.11.12
 * Time: 12:22
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class ExportButton extends Button {

    public ExportButton(){
        setCaption("Export");
        addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Notification.show("Export!!111einself");
            }
        });

    }
}
