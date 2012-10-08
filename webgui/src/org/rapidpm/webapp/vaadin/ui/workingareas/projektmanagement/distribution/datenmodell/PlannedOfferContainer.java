package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.distribution.datenmodell;

import com.vaadin.data.util.BeanItemContainer;
import org.rapidpm.persistence.prj.projectmanagement.planning.finance.PlannedOffer;

import java.io.Serializable;
import java.util.List;


/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 08.10.2012
 * Time: 10:40
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlannedOfferContainer extends BeanItemContainer<PlannedOffer> implements
        Serializable {

    public PlannedOfferContainer() throws IllegalArgumentException {
        super(PlannedOffer.class);
    }


    public void fill(final List<PlannedOffer> plannedOfferBeans){
        for(final PlannedOffer plannedOfferBean : plannedOfferBeans){
            addBean(plannedOfferBean);
        }
    }
}
