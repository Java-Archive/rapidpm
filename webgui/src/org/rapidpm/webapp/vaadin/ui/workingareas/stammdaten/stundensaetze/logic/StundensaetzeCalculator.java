package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;

import java.util.ArrayList;
import java.util.List;

import static org.rapidpm.Constants.KONSTANTE;
import static org.rapidpm.Constants.WORKINGHOURS_DAY;

public class StundensaetzeCalculator {
    public static final String GESAMTSUMMEN = "Gesamtsummen:";

    private List<RessourceGroup> containerBeans;

    private Double sumPerMonthTotal = 0.0;
    private Double sumPerDayTotal = 0.0;
    private Double betriebsStunde = 0.0;
    private Double betriebsWert = 0.0;

    public StundensaetzeCalculator(final Table tabelle) {
        containerBeans = new ArrayList<>();
        final BeanItemContainer<RessourceGroup> beanItemContainer = (BeanItemContainer<RessourceGroup>)tabelle
                .getContainerDataSource();
        for(final RessourceGroup ressourceGroup : beanItemContainer.getItemIds()){
            containerBeans.add(ressourceGroup);
        }
    }

    public void calculate() {
        for (final RessourceGroup ressourceGroupBean : containerBeans) {
            sumPerMonthTotal += ressourceGroupBean.getTransientSumPerMonth();
            sumPerDayTotal += ressourceGroupBean.getTransientSumPerDay();
        }
        betriebsStunde = sumPerDayTotal / WORKINGHOURS_DAY;
        betriebsWert = sumPerDayTotal / KONSTANTE;
    }

    public Double getBetriebsWert() {
        return betriebsWert;
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
