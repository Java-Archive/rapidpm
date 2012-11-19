package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell;

import com.vaadin.data.util.BeanItemContainer;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;

import java.io.Serializable;
import java.util.List;


/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 27.08.12
 * Time: 09:03
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class RessourceGroupContainer extends BeanItemContainer<RessourceGroup> implements
        Serializable {

    public RessourceGroupContainer() throws IllegalArgumentException {
        super(RessourceGroup.class);
    }

    public void fill(final List<RessourceGroup> ressourceGroupBeans){
        for(final RessourceGroup ressourceGroupBean : ressourceGroupBeans){
            addBean(ressourceGroupBean);
        }
    }
}
