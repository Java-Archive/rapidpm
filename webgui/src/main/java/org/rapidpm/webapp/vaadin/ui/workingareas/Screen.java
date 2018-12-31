package org.rapidpm.webapp.vaadin.ui.workingareas;

import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.AbstractView;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;

import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Marco Ebbinghaus
 * Date: 03.08.12
 * Time: 11:07
 * To change this template use File | Settings | File Templates.
 */
public abstract class Screen extends RapidPanel implements Internationalizationable,Componentssetable {

    protected ResourceBundle messagesBundle;

    public Screen(){
        this.messagesBundle = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);
//        this.setStyleName(Reindeer.PANEL_LIGHT);
//        //this.addStyleName("medsizemargin");
//        this.setSizeFull();
    }

    public Screen getScreen() {
        return this;
    }

    public ResourceBundle getMessagesBundle() {
        return messagesBundle;
    }

}
