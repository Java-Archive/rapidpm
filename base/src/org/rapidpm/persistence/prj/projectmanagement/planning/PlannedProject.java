package org.rapidpm.persistence.prj.projectmanagement.planning;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.book.Buch;
import org.rapidpm.persistence.prj.projectmanagement.planning.finance.PlannedOffer;
import org.rapidpm.persistence.prj.projectmanagement.planning.management.travel.PlannedTravel;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.Mandantengruppe;

import javax.persistence.*;
import java.util.List;

@Entity
public class PlannedProject {
    private static final Logger logger = Logger.getLogger(PlannedProject.class);

    public static final String ID = "id";
    public static final String NAME = "projektName";

    @Id
    @TableGenerator(name = "PKGenPlannedProject", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "PlannedProject_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenPlannedProject")
    private Long id;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Mandantengruppe mandantengruppe;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PlannedProjectName> plannedProjectName;

    @Basic
    private String projektName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlanningUnit> planningUnits;

    @Basic
    private boolean active;
    @Basic
    private boolean fakturierbar;

    @Basic
    @Column(columnDefinition = "TEXT")
    private String info;


    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Benutzer creator;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Benutzer responsiblePerson;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<PlannedTravel> plannedTravelList;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<PlannedOffer> plannedOfferList;


    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Buch specification;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Buch testCases;



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

        if (active != that.active) return false;
        if (fakturierbar != that.fakturierbar) return false;
        if (creator != null ? !creator.equals(that.creator) : that.creator != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (info != null ? !info.equals(that.info) : that.info != null) return false;
        if (mandantengruppe != null ? !mandantengruppe.equals(that.mandantengruppe) : that.mandantengruppe != null)
            return false;
        if (plannedOfferList != null ? !plannedOfferList.equals(that.plannedOfferList) : that.plannedOfferList != null)
            return false;
        if (plannedProjectName != null ? !plannedProjectName.equals(that.plannedProjectName) : that.plannedProjectName != null)
            return false;
        if (plannedTravelList != null ? !plannedTravelList.equals(that.plannedTravelList) : that.plannedTravelList != null)
            return false;
        if (planningUnits != null ? !planningUnits.equals(that.planningUnits) : that.planningUnits != null)
            return false;
        if (projektName != null ? !projektName.equals(that.projektName) : that.projektName != null) return false;
        if (responsiblePerson != null ? !responsiblePerson.equals(that.responsiblePerson) : that.responsiblePerson != null)
            return false;
        if (specification != null ? !specification.equals(that.specification) : that.specification != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (mandantengruppe != null ? mandantengruppe.hashCode() : 0);
        result = 31 * result + (plannedProjectName != null ? plannedProjectName.hashCode() : 0);
        result = 31 * result + (projektName != null ? projektName.hashCode() : 0);
        result = 31 * result + (planningUnits != null ? planningUnits.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (fakturierbar ? 1 : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (responsiblePerson != null ? responsiblePerson.hashCode() : 0);
        result = 31 * result + (plannedTravelList != null ? plannedTravelList.hashCode() : 0);
        result = 31 * result + (plannedOfferList != null ? plannedOfferList.hashCode() : 0);
        result = 31 * result + (specification != null ? specification.hashCode() : 0);
        return result;
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

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
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

    public List<PlanningUnit> getPlanningUnits() {
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
}
