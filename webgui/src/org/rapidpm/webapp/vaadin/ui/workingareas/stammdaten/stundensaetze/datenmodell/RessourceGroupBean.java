package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell;

import org.rapidpm.Constants;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 12.09.12
 * Time: 14:04
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class RessourceGroupBean {

    private Integer hoursPerYear;
    private Double eurosPerHour;
    private Double operativeEurosPerHour;
    private Double bruttoPerMonth;
    private Double sumPerMonth;
    private Double sumPerDay;
    private RessourceGroup ressourceGroup;

    public Integer getHoursPerYear() {
        return hoursPerYear;
    }

    public void setHoursPerYear(Integer hoursPerYear) {
        this.hoursPerYear = hoursPerYear;
    }

    public Double getEurosPerHour() {
        return eurosPerHour;
    }

    public void setEurosPerHour(Double eurosPerHour) {
        this.eurosPerHour = eurosPerHour;
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

    public RessourceGroup getRessourceGroup() {
        return ressourceGroup;
    }

    public void setRessourceGroup(RessourceGroup ressourceGroup) {
        this.ressourceGroup = ressourceGroup;
    }

    public void calculate() {
       hoursPerYear = ressourceGroup.getHoursPerWeek() * ressourceGroup.getWeeksPerYear();
       eurosPerHour = ressourceGroup.getBruttoGehalt() / (hoursPerYear * ressourceGroup.getFacturizable());
       operativeEurosPerHour = ressourceGroup.getExternalEurosPerHour() - eurosPerHour;
       bruttoPerMonth = ressourceGroup.getBruttoGehalt() / Constants.MONTHS_PER_YEAR;
       sumPerMonth = bruttoPerMonth * ressourceGroup.getPlanAnzahl();
       sumPerDay = sumPerMonth / Constants.STD_WORKING_DAYS_PER_MONTH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RessourceGroupBean that = (RessourceGroupBean) o;

        if (bruttoPerMonth != null ? !bruttoPerMonth.equals(that.bruttoPerMonth) : that.bruttoPerMonth != null)
            return false;
        if (eurosPerHour != null ? !eurosPerHour.equals(that.eurosPerHour) : that.eurosPerHour != null) return false;
        if (hoursPerYear != null ? !hoursPerYear.equals(that.hoursPerYear) : that.hoursPerYear != null) return false;
        if (operativeEurosPerHour != null ? !operativeEurosPerHour.equals(that.operativeEurosPerHour) : that.operativeEurosPerHour != null)
            return false;
        if (ressourceGroup != null ? !ressourceGroup.equals(that.ressourceGroup) : that.ressourceGroup != null)
            return false;
        if (sumPerDay != null ? !sumPerDay.equals(that.sumPerDay) : that.sumPerDay != null) return false;
        if (sumPerMonth != null ? !sumPerMonth.equals(that.sumPerMonth) : that.sumPerMonth != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = hoursPerYear != null ? hoursPerYear.hashCode() : 0;
        result = 31 * result + (eurosPerHour != null ? eurosPerHour.hashCode() : 0);
        result = 31 * result + (operativeEurosPerHour != null ? operativeEurosPerHour.hashCode() : 0);
        result = 31 * result + (bruttoPerMonth != null ? bruttoPerMonth.hashCode() : 0);
        result = 31 * result + (sumPerMonth != null ? sumPerMonth.hashCode() : 0);
        result = 31 * result + (sumPerDay != null ? sumPerDay.hashCode() : 0);
        result = 31 * result + (ressourceGroup != null ? ressourceGroup.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RessourceGroupBean{" +
                "hoursPerYear=" + hoursPerYear +
                ", eurosPerHour=" + eurosPerHour +
                ", operativeEurosPerHour=" + operativeEurosPerHour +
                ", bruttoPerMonth=" + bruttoPerMonth +
                ", sumPerMonth=" + sumPerMonth +
                ", sumPerDay=" + sumPerDay +
                ", ressourceGroup=" + ressourceGroup +
                '}';
    }
}
