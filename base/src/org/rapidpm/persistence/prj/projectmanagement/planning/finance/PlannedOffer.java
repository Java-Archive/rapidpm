package org.rapidpm.persistence.prj.projectmanagement.planning.finance;

import org.rapidpm.persistence.prj.stammdaten.person.Person;

import javax.persistence.*;
import java.util.Date;

/**
 * User: svenruppert
 * Date: 30.07.12
 * Time: 08:25
 * <p/>
 * Betriebswirtschaftliche Eckdaten der Projektplanung,
 * Risiko, Verhandlungsaufschlag und co.
 */
@Entity
public class PlannedOffer {

    @Id
    @TableGenerator(name = "PKGenPlannedOffer", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "PlannedOffer_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PKGenPlannedOffer")
    private Long id;

    @Basic private Date planningDate;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Person responsiblePerson;

    @Basic private float riskPercentageAddition;           //Risikoaufschlag
    @Basic private float verhandlungsPercentageAddition;   //VerhandlungsAufschlag
    @Basic private float warrantyPercentageAddition;       //Gewaehrleistungsaufschlag
    @Basic private float operativePercentageAddition;      //operatives Ergebnis geplant.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPlanningDate() {
        return planningDate;
    }

    public void setPlanningDate(Date planningDate) {
        this.planningDate = planningDate;
    }

    public Person getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(Person responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public float getRiskPercentageAddition() {
        return riskPercentageAddition;
    }

    public void setRiskPercentageAddition(float riskPercentageAddition) {
        this.riskPercentageAddition = riskPercentageAddition;
    }

    public float getVerhandlungsPercentageAddition() {
        return verhandlungsPercentageAddition;
    }

    public void setVerhandlungsPercentageAddition(float verhandlungsPercentageAddition) {
        this.verhandlungsPercentageAddition = verhandlungsPercentageAddition;
    }

    public float getWarrantyPercentageAddition() {
        return warrantyPercentageAddition;
    }

    public void setWarrantyPercentageAddition(float warrantyPercentageAddition) {
        this.warrantyPercentageAddition = warrantyPercentageAddition;
    }

    public float getOperativePercentageAddition() {
        return operativePercentageAddition;
    }

    public void setOperativePercentageAddition(float operativePercentageAddition) {
        this.operativePercentageAddition = operativePercentageAddition;
    }
}
