package org.rapidpm.persistence.prj.projectmanagement.planning;

import org.rapidpm.persistence.prj.projectmanagement.planning.finance.PlannedOffer;
import org.rapidpm.persistence.prj.projectmanagement.planning.management.travel.PlannedTravel;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.Mandantengruppe;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.ArrayList;
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
        if (o == null || getClass() != o.getClass()) return false;

        PlannedProject that = (PlannedProject) o;

        if (projektName != null ? !projektName.equals(that.projektName) : that.projektName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return projektName != null ? projektName.hashCode() : 0;
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
