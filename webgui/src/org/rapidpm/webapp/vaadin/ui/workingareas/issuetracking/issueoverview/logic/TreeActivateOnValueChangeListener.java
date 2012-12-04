package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Tree;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 09.10.12
 * Time: 10:01
 * To change this template use File | Settings | File Templates.
 */
public class TreeActivateOnValueChangeListener implements Tree.ValueChangeListener {
    private static Logger logger = Logger.getLogger(TreeActivateOnValueChangeListener.class);

    private final Button[] buttonList;

    public TreeActivateOnValueChangeListener(final Button[] buttonList) {
        this.buttonList = buttonList;
    }

    @Override
    public void valueChange(Property.ValueChangeEvent event) {
        if (event.getProperty() != null && event.getProperty().getValue() != null)
            for (Button button : buttonList)
                button.setEnabled(true);
        else
            for (Button button : buttonList)
                button.setEnabled(false);
    }
}
