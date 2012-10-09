package org.rapidpm.webapp.vaadin.ui.workingareas;

import com.vaadin.ui.VerticalLayout;
import org.rapidpm.webapp.vaadin.MainUI;

import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Marco
 * Date: 03.08.12
 * Time: 11:07
 * To change this template use File | Settings | File Templates.
 */
public abstract class Screen extends VerticalLayout implements Internationalizationable,Componentssetable {

    protected ResourceBundle messagesBundle;
    protected MainUI ui;

    public Screen(final MainUI ui){
        this.messagesBundle = ui.getResourceBundle();
        this.ui = ui;
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
