package org.rapidpm.webapp.vaadin.ui.workingareas.zeiterfassung.model;

import com.vaadin.data.util.BeanItemContainer;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 26.10.12
 * Time: 10:14
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class UserWorkLogContainer extends BeanItemContainer<UserWorkLog> {

    public UserWorkLogContainer() {
        super(UserWorkLog.class);
    }

}
