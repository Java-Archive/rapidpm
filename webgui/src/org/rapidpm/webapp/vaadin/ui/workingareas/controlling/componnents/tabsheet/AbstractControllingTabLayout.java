package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.componnents.tabsheet;

import com.vaadin.ui.VerticalLayout;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 16.11.12
 * Time: 14:50
 * To change this template use File | Settings | File Templates.
 */
public class AbstractControllingTabLayout extends VerticalLayout{

    private final String caption;

    public AbstractControllingTabLayout(String caption) {
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }
}


