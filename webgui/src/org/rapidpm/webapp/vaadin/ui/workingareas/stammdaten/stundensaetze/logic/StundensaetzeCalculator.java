package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupBean;

import java.util.ArrayList;
import java.util.List;

import static org.rapidpm.Constants.KONSTANTE;
import static org.rapidpm.Constants.WORKINGHOURS_DAY;

public class StundensaetzeCalculator {
    public static final String GESAMTSUMMEN = "Gesamtsummen:";

    private List<RessourceGroupBean> containerBeans;

    private Double sumPerMonthTotal = 0.0;
    private Double sumPerDayTotal = 0.0;
    private Double betriebsStunde = 0.0;
    private Double betriebsWert = 0.0;

    public StundensaetzeCalculator(final Table tabelle) {
        containerBeans = new ArrayList<>();
        final BeanItemContainer<RessourceGroupBean> nestedBeanContainer = (BeanItemContainer<RessourceGroupBean>)tabelle
                .getContainerDataSource();
        for(final RessourceGroupBean ressourceGroupBean : nestedBeanContainer.getItemIds()){
            containerBeans.add(ressourceGroupBean);
        }
    }

    public void calculate() {
        for (final RessourceGroupBean ressourceGroupBean : containerBeans) {
            sumPerMonthTotal += ressourceGroupBean.getSumPerMonth();
            sumPerDayTotal += ressourceGroupBean.getSumPerDay();
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
