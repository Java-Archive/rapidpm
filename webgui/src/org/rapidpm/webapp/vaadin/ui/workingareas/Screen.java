package org.rapidpm.webapp.vaadin.ui.workingareas;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import org.rapidpm.webapp.vaadin.MainUI;
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
    protected MainUI ui;

    public Screen(final MainUI ui){
        this.messagesBundle = ui.getResourceBundle();
        this.ui = ui;
        this.setStyleName(Reindeer.PANEL_LIGHT);
        //this.addStyleName("medsizemargin");
        this.setSizeFull();
    }

    public void activeVerticalFullScreenSize(final boolean b) {
        if(b){
            getContentLayout().setSizeFull();
        } else {
            getContentLayout().setSizeUndefined();
            getContentLayout().setWidth("100%");
        }

    }

    public Screen getScreen() {
        return this;
    }

    public ResourceBundle getMessagesBundle() {
        return messagesBundle;
    }

    public MainUI getUi() {
        return ui;
    }
}
