package org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal;

import javax.persistence.*;
import java.util.Date;

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
@Entity
public class RessourceGroup {

    @Id
    @TableGenerator(name = "PKGenRessourceGroup", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "RessourceGroup_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenRessourceGroup")
    private Long id;

    @Basic private String name;

    @Basic private Integer hoursPerWeek;
    @Basic private Integer weeksPerYear;
    @Basic private Integer planAnzahl;

    @Basic private Double facturizable;
    @Basic private Double externalEurosPerHour;
    @Basic private Double bruttoGehalt;

    /*evtl für spätere Verwendung*/
    @Basic private String description;
    @Basic private Date validFrom;
    @Basic private Date validUntil;
    @Basic private String currency; // USD / EUR / ..
    /*evtl für spätere Verwendung*/


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RessourceGroup that = (RessourceGroup) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
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
