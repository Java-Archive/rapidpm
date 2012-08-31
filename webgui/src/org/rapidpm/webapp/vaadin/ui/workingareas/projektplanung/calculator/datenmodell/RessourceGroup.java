package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell;

import org.rapidpm.webapp.vaadin.Constants;

import java.io.Serializable;

public class RessourceGroup implements Serializable {
    public static final String[] VISIBLECOLUMNS = new String[]{"Ressource",
            "Brutto-Jahresgehalt", "h / Woche", "Arbeitswochen / Jahr",
            "h / Jahr", "Fakturierbar", Constants.EUR + " / h", "extern" + Constants.EUR + " / h",
            "operativ" + Constants.EUR + " / h", "Brutto / Monat", "Plan-Anzahl", "Summe / Monat",
            "Summe / Tag"};
    public static final String[] COLUMNS = new String[]{"name",
            "bruttoGehalt", "hoursPerWeek", "weeksPerYear", "hoursPerYear",
            "facturizable", "eurosPerHour", "externalEurosPerHour",
            "operativeEurosPerHour", "bruttoPerMonth", "planAnzahl",
            "sumPerMonth", "sumPerDay"};

    private String name = "neue Ressource";
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RessourceGroup that = (RessourceGroup) o;
        if (!name.equals(that.name)) return false;
        return true;
    }

//    @Override
//    public int hashCode() {
//        int result = name.hashCode();
//        result = 31 * result + (bruttoGehalt != null ? bruttoGehalt.hashCode() : 0);
//        result = 31 * result + (hoursPerWeek != null ? hoursPerWeek.hashCode() : 0);
//        result = 31 * result + (weeksPerYear != null ? weeksPerYear.hashCode() : 0);
//        result = 31 * result + (hoursPerYear != null ? hoursPerYear.hashCode() : 0);
//        result = 31 * result + (facturizable != null ? facturizable.hashCode() : 0);
//        result = 31 * result + (eurosPerHour != null ? eurosPerHour.hashCode() : 0);
//        result = 31 * result + (externalEurosPerHour != null ? externalEurosPerHour.hashCode() : 0);
//        result = 31 * result + (operativeEurosPerHour != null ? operativeEurosPerHour.hashCode() : 0);
//        result = 31 * result + (bruttoPerMonth != null ? bruttoPerMonth.hashCode() : 0);
//        result = 31 * result + (planAnzahl != null ? planAnzahl.hashCode() : 0);
//        result = 31 * result + (sumPerMonth != null ? sumPerMonth.hashCode() : 0);
//        result = 31 * result + (sumPerDay != null ? sumPerDay.hashCode() : 0);
//        return result;
//    }

    @Override
    public String toString() {
        return "RessourceGroup{" +
                "name='" + name + '\'' +
                '}';
    }
}
