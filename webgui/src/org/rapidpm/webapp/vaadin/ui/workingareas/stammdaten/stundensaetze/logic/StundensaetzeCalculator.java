package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Table;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;

import java.util.ArrayList;
import java.util.List;

public class StundensaetzeCalculator {
    public static final String GESAMTSUMMEN = "Gesamtsummen:";
    private final StundensaetzeScreen screen;

    private List<RessourceGroup> containerBeans;

    private Double sumPerMonthTotal = 0.0;
    private Double sumPerDayTotal = 0.0;
    private Double betriebsStunde = 0.0;
    private Double mindestManntage = 0.0;

    private PlannedProject currentProject;

    public StundensaetzeCalculator(final StundensaetzeScreen screen, final Table tabelle) {
        this.screen = screen;
        containerBeans = new ArrayList<>();
        final BeanItemContainer<RessourceGroup> beanItemContainer = (BeanItemContainer<RessourceGroup>)tabelle
                .getContainerDataSource();
        for(final RessourceGroup ressourceGroup : beanItemContainer.getItemIds()){
            containerBeans.add(ressourceGroup);
        }
    }

    public void calculate() {
        final VaadinSession session = screen.getUi().getSession();
        final PlannedProject currentProject = session.getAttribute(PlannedProject.class);
        for (final RessourceGroup ressourceGroupBean : containerBeans) {
            sumPerMonthTotal += ressourceGroupBean.getTransientSumPerMonth();
            sumPerDayTotal += ressourceGroupBean.getTransientSumPerDay();
        }
        betriebsStunde = sumPerDayTotal / currentProject.getHoursPerWorkingDay();
        mindestManntage = sumPerDayTotal / currentProject.getExternalDailyRate();
    }

    public Double getMindestManntage() {
        return mindestManntage;
    }

    public Double getBetriebsStunde() {
        return betriebsStunde;
    }

    public Double getSummeProTag() {
        return sumPerDayTotal;
    }

    public Double getSummeProMonat() {
        return sumPerMonthTotal;
    }
}
