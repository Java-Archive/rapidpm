package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.distribution.logic;

import org.rapidpm.Constants;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.finance.PlannedOffer;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TimesCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic.CostsCalculator;

import java.util.List;
import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 08.10.12
 * Time: 10:45
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlannedOfferCalculator {

    private List<PlannedOffer> plannedOffers;
    private PlannedProject plannedProject;
    private TimesCalculator timesCalculator;
    private PlannedOfferCalculatorBean bean;
    private DaoFactoryBean baseDaoFactoryBean;
    private double sumWithoutDistributionSpread;
    private double sumWithDistributionSpread;
    private PlannedOffer verhandlungsAufschlagOffer;
    private ResourceBundle messages;

    public PlannedOfferCalculator(final PlannedProject project, final ResourceBundle bundle) {
        this.plannedProject = project;
        this.messages = bundle;
        plannedOffers = plannedProject.getPlannedOfferList();
        bean = EJBFactory.getEjbInstance(PlannedOfferCalculatorBean.class);
        baseDaoFactoryBean = bean.getDaoFactoryBean();
        timesCalculator = new TimesCalculator(bundle);
        timesCalculator.calculate();
    }

    public void calculate(){
        for (final PlannedOffer offer : plannedOffers){
            if(offer.getName().equals("Verhandlungsaufschlag")){
                verhandlungsAufschlagOffer = offer;
            }
            if(offer.getName().equals("Aufwaende")){
                final CostsCalculator calculator = new CostsCalculator(messages);
                calculator.calculate();
                offer.setCosts(calculator.getTotalCostsExakt());
            }
        }
        for (final PlannedOffer offer : plannedOffers){
            calculateOffer(offer);
            sumWithDistributionSpread += offer.getCosts();
        }
        sumWithoutDistributionSpread = sumWithDistributionSpread - verhandlungsAufschlagOffer.getCosts();
    }

    public void calculateOffer(final PlannedOffer offer){
        final int neededMinutesForOffer = (int) (timesCalculator.getGesamtSummeInMin() * offer.getPercent());
        final DaysHoursMinutesItem item = new DaysHoursMinutesItem();
        item.setMinutes(neededMinutesForOffer);
        DaysHoursMinutesItem.correctDaysHoursMinutesItem(item);

        offer.setDaysHoursMinutesItem(item);
        if(!offer.getName().equals("Aufwaende"))
            offer.setCosts((neededMinutesForOffer/60) * offer.getEurosPerHour() * Constants.HOURS_DAY);

        if(sumWithDistributionSpread != 0.0){
            offer.setPercentageAllocationWithDistributionSpread((float) (offer.getCosts() / sumWithDistributionSpread));
        } else {
            offer.setPercentageAllocationWithDistributionSpread(0.0f);
        }
        if(sumWithoutDistributionSpread != 0.0){
            offer.setPercentageAllocationWithoutDistributionSpread((float) (offer.getCosts() /
                    sumWithoutDistributionSpread));
        } else {
            offer.setPercentageAllocationWithoutDistributionSpread(0.0f);
        }

    }

    public double getSumWithDistributionSpread() {
        return sumWithDistributionSpread;
    }

    public void setSumWithDistributionSpread(double sumWithDistributionSpread) {
        this.sumWithDistributionSpread = sumWithDistributionSpread;
    }

    public double getSumWithoutDistributionSpread() {
        return sumWithoutDistributionSpread;
    }

    public void setSumWithoutDistributionSpread(double sumWithoutDistributionSpread) {
        this.sumWithoutDistributionSpread = sumWithoutDistributionSpread;
    }
}
