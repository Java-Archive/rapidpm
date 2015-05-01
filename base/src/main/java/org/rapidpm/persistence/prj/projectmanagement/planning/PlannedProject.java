package org.rapidpm.persistence.prj.projectmanagement.planning;

import com.tinkerpop.blueprints.Vertex;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.book.Buch;
import org.rapidpm.persistence.prj.projectmanagement.planning.finance.PlannedOffer;
import org.rapidpm.persistence.prj.projectmanagement.planning.management.travel.PlannedTravel;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.Mandantengruppe;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;


public class PlannedProject implements Comparable<PlannedProject>{
    private static final Logger logger = Logger.getLogger(PlannedProject.class);

    public static final String ID = "id";
    public static final String ACTIVE = "active";
    public static final String NAME = "projektName";
    public static final String TOKEN = "projektToken";
    public static final String INFO = "info";
    public static final String FACT = "fakturierbar";
    public static final String EXTERNALDAILYRATE = "externalDailyRate";
    public static final String HOURSPERWORKINGDAY = "hoursPerWorkingDay";

    private String id;
    private Mandantengruppe mandantengruppe;
    private Set<PlannedProjectName> plannedProjectName;
    private String projektToken;
    private String projektName;
    private Set<PlanningUnit> planningUnits;
    private boolean active;
    private boolean fakturierbar;
    private String info;
    private Double externalDailyRate;
    private Integer hoursPerWorkingDay;
    private Benutzer creator;
    private Benutzer responsiblePerson;
    private List<PlannedTravel> plannedTravelList;
    private List<PlannedOffer> plannedOfferList;
    private Buch specification;
    private Buch testCases;

    public PlannedProject(){}

    public PlannedProject(Vertex plannedProjectVertex) {
        id = plannedProjectVertex.getId().toString();
        active = plannedProjectVertex.getProperty(ACTIVE);
        fakturierbar = plannedProjectVertex.getProperty(FACT);
        info = plannedProjectVertex.getProperty(INFO);
        projektName = plannedProjectVertex.getProperty(NAME);
        projektToken = plannedProjectVertex.getProperty(projektToken);
        externalDailyRate = plannedProjectVertex.getProperty(EXTERNALDAILYRATE);
        hoursPerWorkingDay = plannedProjectVertex.getProperty(HOURSPERWORKINGDAY);
    }

    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Buch getTestCases() {
        return testCases;
    }

    public void setTestCases(Buch testCases) {
        this.testCases = testCases;
    }

    public List<PlannedTravel> getPlannedTravelList() {
        return plannedTravelList;
    }

    public void setPlannedTravelList(List<PlannedTravel> plannedTravelList) {
        this.plannedTravelList = plannedTravelList;
    }

    public List<PlannedOffer> getPlannedOfferList() {
        return plannedOfferList;
    }

    public void setPlannedOfferList(List<PlannedOffer> plannedOfferList) {
        this.plannedOfferList = plannedOfferList;
    }

    public Buch getSpecification() {
        return specification;
    }

    public void setSpecification(Buch specification) {
        this.specification = specification;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public Benutzer getCreator() {
        return creator;
    }

    public void setCreator(final Benutzer creator) {
        this.creator = creator;
    }

    public boolean isFakturierbar() {
        return fakturierbar;
    }

    public void setFakturierbar(final boolean fakturierbar) {
        this.fakturierbar = fakturierbar;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(final String info) {
        this.info = info;
    }

    public Mandantengruppe getMandantengruppe() {
        return mandantengruppe;
    }

    public void setMandantengruppe(final Mandantengruppe mandantengruppe) {
        this.mandantengruppe = mandantengruppe;
    }

    public Set<PlannedProjectName> getPlannedProjectName() {
        return plannedProjectName;
    }

    public void setPlannedProjectName(final Set<PlannedProjectName> plannedProjectName) {
        this.plannedProjectName = plannedProjectName;
    }

    public Benutzer getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(final Benutzer responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public Set<PlanningUnit> getPlanningUnits() {
        return planningUnits;
    }

    public void setPlanningUnits(Set<PlanningUnit> planningUnits) {
        this.planningUnits = planningUnits;
    }

    public String getProjektName() {
        return projektName;
    }

    public void setProjektName(String projektName) {
        this.projektName = projektName;
    }

    public String getProjektToken() {
        return projektToken;
    }

    public void setProjektToken(String projektToken) {
        this.projektToken = projektToken;
    }

    public Double getExternalDailyRate() {
        return externalDailyRate;
    }

    public void setExternalDailyRate(Double externalDailyRate) {
        this.externalDailyRate = externalDailyRate;
    }

    public Integer getHoursPerWorkingDay() {
        return hoursPerWorkingDay;
    }

    public void setHoursPerWorkingDay(Integer hoursPerWorkingDay) {
        this.hoursPerWorkingDay = hoursPerWorkingDay;
    }

    @Override
    public int compareTo(PlannedProject o) {
        return 1;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("PlannedProject");
        sb.append("{active=").append(active);
        sb.append(", id=").append(id);
        sb.append(", mandantengruppe=").append(mandantengruppe);
        sb.append(", plannedProjectName=").append(plannedProjectName);
        sb.append(", fakturierbar=").append(fakturierbar);
        sb.append(", creator=").append(creator);
        sb.append(", responsiblePerson=").append(responsiblePerson);
        //        sb.append(", created=").append(created);
        sb.append(", info='").append(info).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlannedProject)) return false;

        PlannedProject that = (PlannedProject) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

}
