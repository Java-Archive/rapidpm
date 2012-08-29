package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell;

import com.vaadin.data.util.BeanItemContainer;
import org.rapidpm.transience.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 27.08.12
 * Time: 09:03
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class RessourceGroupsBeanItemContainer extends BeanItemContainer<RessourceGroup> implements
        Serializable {

    public RessourceGroupsBeanItemContainer() throws IllegalArgumentException {
        super(RessourceGroup.class);
    }

    public void fill(ArrayList<RessourceGroup> ressourceGroups){
        for(RessourceGroup ressourceGroup : ressourceGroups){
            addBean(ressourceGroup);
        }
    }
}
