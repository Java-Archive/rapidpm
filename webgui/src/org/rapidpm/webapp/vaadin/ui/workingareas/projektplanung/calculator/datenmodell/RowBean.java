package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell;

import org.rapidpm.webapp.vaadin.Constants;

import java.io.Serializable;

public class RowBean implements Serializable {
    public static final String[] VISIBLECOLUMNS = new String[]{"Ressource",
            "Brutto-Jahresgehalt", "h / Woche", "Arbeitswochen / Jahr",
            "h / Jahr", "Fakturierbar", Constants.EUR + " / h", "extern" + Constants.EUR + " / h",
            "operativ" + Constants.EUR + " / h", "Brutto / Monat", "Plan-Anzahl", "Summe / Monat",
            "Summe / Tag"};
    public static final String[] COLUMNS = new String[]{"ressource",
            "bruttoGehalt", "hoursPerWeek", "weeksPerYear", "hoursPerYear",
            "facturizable", "eurosPerHour", "externalEurosPerHour",
            "operativeEurosPerHour", "bruttoPerMonth", "planAnzahl",
            "sumPerMonth", "sumPerDay"};

    private String ressource = "neue Ressource";
    private Double bruttoGehalt = 100000.0;
    private Integer hoursPerWeek = 40;
    private Integer weeksPerYear = 46;
    private Integer hoursPerYear;
    private Double facturizable = 0.5;
    private Double eurosPerHour;
    private Double externalEurosPerHour = 125.0;
    private Double operativeEurosPerHour;
    private Double bruttoPerMonth;
    private Integer planAnzahl = 1;
    private Double sumPerMonth;
    private Double sumPerDay;

    public String getRessource() {
        return ressource;
    }

    public void setRessource(String ressource) {
        this.ressource = ressource;
    }

    public Double getBruttoGehalt() {
        return bruttoGehalt;
    }

    public void setBruttoGehalt(Double bruttoGehalt) {
        this.bruttoGehalt = bruttoGehalt;
    }

    public Integer getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(Integer hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public Integer getWeeksPerYear() {
        return weeksPerYear;
    }

    public void setWeeksPerYear(Integer weeksPerYear) {
        this.weeksPerYear = weeksPerYear;
    }

    public Integer getHoursPerYear() {
        return hoursPerYear;
    }

    public void setHoursPerYear(Integer hoursPerYear) {
        this.hoursPerYear = hoursPerYear;
    }

    public Double getFacturizable() {
        return facturizable;
    }

    public void setFacturizable(Double facturizable) {
        this.facturizable = facturizable;
    }

    public Double getEurosPerHour() {
        return eurosPerHour;
    }

    public void setEurosPerHour(Double eurosPerHour) {
        this.eurosPerHour = eurosPerHour;
    }

    public Double getExternalEurosPerHour() {
        return externalEurosPerHour;
    }

    public void setExternalEurosPerHour(Double externalEurosPerHour) {
        this.externalEurosPerHour = externalEurosPerHour;
    }

    public Double getOperativeEurosPerHour() {
        return operativeEurosPerHour;
    }

    public void setOperativeEurosPerHour(Double operativeEurosPerHour) {
        this.operativeEurosPerHour = operativeEurosPerHour;
    }

    public Double getBruttoPerMonth() {
        return bruttoPerMonth;
    }

    public void setBruttoPerMonth(Double bruttoPerMonth) {
        this.bruttoPerMonth = bruttoPerMonth;
    }

    public Integer getPlanAnzahl() {
        return planAnzahl;
    }

    public void setPlanAnzahl(Integer planAnzahl) {
        this.planAnzahl = planAnzahl;
    }

    public Double getSumPerMonth() {
        return sumPerMonth;
    }

    public void setSumPerMonth(Double sumPerMonth) {
        this.sumPerMonth = sumPerMonth;
    }

    public Double getSumPerDay() {
        return sumPerDay;
    }

    public void setSumPerDay(Double sumPerDay) {
        this.sumPerDay = sumPerDay;
    }

}
