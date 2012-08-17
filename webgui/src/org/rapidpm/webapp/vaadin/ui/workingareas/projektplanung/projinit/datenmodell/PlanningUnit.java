package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell;

import java.util.List;

/**
 *
 * User: svenruppert
 * Date: 30.07.12
 * Time: 06:50
 *
 * Verbindung der PlanungsEinheit mit den Aufwänden der PlanungsRessourcen.
 *
 */

public class PlanningUnit {

    private Long id;
    private int orderNumber;
    private String planningUnitElementName;
    private List<PlanningUnitElement> planningUnitElementList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPlanningUnitElementName() {
        return planningUnitElementName;
    }

    public void setPlanningUnitElementName(String planningUnitElementName) {
        this.planningUnitElementName = planningUnitElementName;
    }

    public List<PlanningUnitElement> getPlanningUnitElementList() {
        return planningUnitElementList;
    }

    public void setPlanningUnitElementList(List<PlanningUnitElement> planningUnitElementList) {
        this.planningUnitElementList = planningUnitElementList;
    }
}
