package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell;

import com.vaadin.data.util.BeanItemContainer;

import java.io.Serializable;
import java.util.List;


/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 27.08.12
 * Time: 09:03
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class RessourceGroupBeanContainer extends BeanItemContainer<RessourceGroupBean> implements
        Serializable {

    public RessourceGroupBeanContainer() throws IllegalArgumentException {
        super(RessourceGroupBean.class);
    }

    public void fill(final List<RessourceGroupBean> ressourceGroupBeans){
        for(final RessourceGroupBean ressourceGroupBean : ressourceGroupBeans){
            addBean(ressourceGroupBean);
        }
    }
}
