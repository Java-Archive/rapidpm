package org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal;

import org.rapidpm.Constants;

/**
 *
 * User: svenruppert
 * Date: 30.07.12
 * Time: 06:54
 *
 * RessourceGroup, die Gruppierung der internen/externen Personalressourcen.
 * z.B.
 *  - Aushilfe
 *  - Werksstudent
 *  - Junior Developer
 *  - Developer
 *  - Seniordeveloper
 *  - PL
 *  - MPM
 *  - GF
 *
 */
public class RessourceGroup {

    public static final String NAME = "name";
    public static final String BRUTTOGEHALT = "bruttoGehalt";
    public static final String HOURS_PER_WEEK = "hoursPerWeek";
    public static final String WEEKS_PER_YEAR = "weeksPerYear";
    public static final String FACTURIZABLE = "facturizable";
    public static final String EXTERNAL_EUROS_PER_HOUR = "externalEurosPerHour";
    public static final String PLAN_ANZAHL = "planAnzahl";
    public static final String HOURS_PER_YEAR = "transientHoursPerYear";
    public static final String EUROS_PER_HOUR = "transientEurosPerHour";
    public static final String OPERATIVE_EUROS_PER_HOUR = "transientOperativeEurosPerHour";
    public static final String BRUTTO_PER_MONTH = "transientBruttoPerMonth";
    public static final String SUM_PER_MONTH = "transientSumPerMonth";
    public static final String SUM_PER_DAY = "transientSumPerDay";

    private String id;

    private String name;

    private Integer hoursPerWeek;
    private Integer weeksPerYear;
    private Integer planAnzahl;

    private Double facturizable;
    private Double externalEurosPerHour;
    private Double bruttoGehalt;

    /*evtl für spätere Verwendung*/
    //@Basic private String description;
    //@Basic private Date validFrom;
    //@Basic private Date validUntil;
    //@Basic private String currency; // USD / EUR / ..
    /*evtl für spätere Verwendung*/

    private transient Integer transientHoursPerYear;
    private transient Double transientEurosPerHour;
    private transient Double transientOperativeEurosPerHour;
    private transient Double transientBruttoPerMonth;
    private transient Double transientSumPerMonth;
    private transient Double transientSumPerDay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public Date getValidFrom() {
//        return validFrom;
//    }
//
//    public void setValidFrom(Date validFrom) {
//        this.validFrom = validFrom;
//    }
//
//    public Date getValidUntil() {
//        return validUntil;
//    }
//
//    public void setValidUntil(Date validUntil) {
//        this.validUntil = validUntil;
//    }
//
//    public String getCurrency() {
//        return currency;
//    }
//
//    public void setCurrency(String currency) {
//        this.currency = currency;
//    }

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


    public Double getFacturizable() {
        return facturizable;
    }

    public void setFacturizable(Double facturizable) {
        this.facturizable = facturizable;
    }

    public Double getExternalEurosPerHour() {
        return externalEurosPerHour;
    }

    public void setExternalEurosPerHour(Double externalEurosPerHour) {
        this.externalEurosPerHour = externalEurosPerHour;
    }

    public Integer getPlanAnzahl() {
        return planAnzahl;
    }

    public void setPlanAnzahl(Integer planAnzahl) {
        this.planAnzahl = planAnzahl;
    }

    public Integer getTransientHoursPerYear() {
        transientHoursPerYear = hoursPerWeek * weeksPerYear;
        return transientHoursPerYear;
    }

    public void setTransientHoursPerYear(Integer transientHoursPerYear) {
        this.transientHoursPerYear = transientHoursPerYear;
    }

    public Double getTransientEurosPerHour() {
        transientEurosPerHour = bruttoGehalt / (getTransientHoursPerYear() * facturizable);
        return transientEurosPerHour;
    }

    public void setTransientEurosPerHour(Double transientEurosPerHour) {
        this.transientEurosPerHour = transientEurosPerHour;
    }

    public Double getTransientOperativeEurosPerHour() {
        transientOperativeEurosPerHour = externalEurosPerHour - getTransientEurosPerHour();
        return transientOperativeEurosPerHour;
    }

    public void setTransientOperativeEurosPerHour(Double transientOperativeEurosPerHour) {
        this.transientOperativeEurosPerHour = transientOperativeEurosPerHour;
    }

    public Double getTransientBruttoPerMonth() {
        transientBruttoPerMonth = bruttoGehalt / Constants.MONTHS_PER_YEAR;
        return transientBruttoPerMonth;
    }

    public void setTransientBruttoPerMonth(Double transientBruttoPerMonth) {
        this.transientBruttoPerMonth = transientBruttoPerMonth;
    }

    public Double getTransientSumPerMonth() {
        transientSumPerMonth = getTransientBruttoPerMonth() * planAnzahl;
        return transientSumPerMonth;
    }

    public void setTransientSumPerMonth(Double transientSumPerMonth) {
        this.transientSumPerMonth = transientSumPerMonth;
    }

    public Double getTransientSumPerDay() {
        transientSumPerDay = getTransientSumPerMonth() / Constants.STD_WORKING_DAYS_PER_MONTH;
        return transientSumPerDay;
    }

    public void setTransientSumPerDay(Double transientSumPerDay) {
        this.transientSumPerDay = transientSumPerDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RessourceGroup that = (RessourceGroup) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "RessourceGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hoursPerWeek=" + hoursPerWeek +
                ", weeksPerYear=" + weeksPerYear +
                ", planAnzahl=" + planAnzahl +
                ", facturizable=" + facturizable +
                ", externalEurosPerHour=" + externalEurosPerHour +
                ", bruttoGehalt=" + bruttoGehalt +
                '}';
    }
}
