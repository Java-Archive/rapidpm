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


    private String projektToken;
    private String projektName;
    private boolean active;
    private boolean fakturierbar;
    private String info;
    private Float externalDailyRate;
    private Integer hoursPerWorkingDay;
    private transient List<PlannedProjectName> plannedProjectName;
    private transient Mandantengruppe mandantengruppe;
    private transient List<PlanningUnit> planningUnits;
    private transient Benutzer creator;
    private transient Benutzer responsiblePerson;
    private transient List<PlannedTravel> plannedTravelList;
    private transient List<PlannedOffer> plannedOfferList;
    private transient Buch specification;
    private transient Buch testCases;

    public PlannedProject(){}

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

    public List<PlannedProjectName> getPlannedProjectName() {
        return plannedProjectName;
    }

    public void setPlannedProjectName(final List<PlannedProjectName> plannedProjectName) {
        this.plannedProjectName = plannedProjectName;
    }

    public Benutzer getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(final Benutzer responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public List<PlanningUnit> getTopLevelPlanningUnits() {
        return planningUnits;
    }

    public void setPlanningUnits(List<PlanningUnit> planningUnits) {
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

    public Float getExternalDailyRate() {
        return externalDailyRate;
    }

    public void setExternalDailyRate(Float externalDailyRate) {
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
        return "PlannedProject{" +
                "id='" + id + '\'' +
                ", projektName='" + projektName + '\'' +
                ", projektToken='" + projektToken + '\'' +
                ", info='" + info + '\'' +
                ", active=" + active +
                ", fakturierbar=" + fakturierbar +
                ", externalDailyRate=" + externalDailyRate +
                ", hoursPerWorkingDay=" + hoursPerWorkingDay +
                '}';
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
