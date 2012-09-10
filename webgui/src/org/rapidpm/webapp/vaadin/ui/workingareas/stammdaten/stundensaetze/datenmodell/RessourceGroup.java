package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell;

import java.io.Serializable;
import static org.rapidpm.Constants.*;

public class RessourceGroup implements Serializable {

    public static final String[] COLUMNS = new String[]{"name",
            "bruttoGehalt", "hoursPerWeek", "weeksPerYear", "hoursPerYear",
            "facturizable", "eurosPerHour", "externalEurosPerHour",
            "operativeEurosPerHour", "bruttoPerMonth", "planAnzahl",
            "sumPerMonth", "sumPerDay"};


    private String name ;
    private Double bruttoGehalt ;
    private Integer hoursPerWeek ;
    private Integer weeksPerYear ;
    private Integer hoursPerYear;
    private Double facturizable ;
    private Double eurosPerHour;
    private Double externalEurosPerHour ;
    private Double operativeEurosPerHour;
    private Double bruttoPerMonth;
    private Integer planAnzahl ;
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
        if (!(o instanceof RessourceGroup)) return false;

        RessourceGroup that = (RessourceGroup) o;

        if (bruttoGehalt != null ? !bruttoGehalt.equals(that.bruttoGehalt) : that.bruttoGehalt != null) return false;
        if (bruttoPerMonth != null ? !bruttoPerMonth.equals(that.bruttoPerMonth) : that.bruttoPerMonth != null)
            return false;
        if (eurosPerHour != null ? !eurosPerHour.equals(that.eurosPerHour) : that.eurosPerHour != null) return false;
        if (externalEurosPerHour != null ? !externalEurosPerHour.equals(that.externalEurosPerHour) : that.externalEurosPerHour != null)
            return false;
        if (facturizable != null ? !facturizable.equals(that.facturizable) : that.facturizable != null) return false;
        if (hoursPerWeek != null ? !hoursPerWeek.equals(that.hoursPerWeek) : that.hoursPerWeek != null) return false;
        if (hoursPerYear != null ? !hoursPerYear.equals(that.hoursPerYear) : that.hoursPerYear != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (operativeEurosPerHour != null ? !operativeEurosPerHour.equals(that.operativeEurosPerHour) : that.operativeEurosPerHour != null)
            return false;
        if (planAnzahl != null ? !planAnzahl.equals(that.planAnzahl) : that.planAnzahl != null) return false;
        if (sumPerDay != null ? !sumPerDay.equals(that.sumPerDay) : that.sumPerDay != null) return false;
        if (sumPerMonth != null ? !sumPerMonth.equals(that.sumPerMonth) : that.sumPerMonth != null) return false;
        if (weeksPerYear != null ? !weeksPerYear.equals(that.weeksPerYear) : that.weeksPerYear != null) return false;

        return true;
    }



    @Override
    public String toString() {
        return name;
    }
}
