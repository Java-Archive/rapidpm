package org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: svenruppert
 * Date: 30.07.12
 * Time: 08:06
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class MitarbeiterRessourceGroupAssoc {


    @Id
    @TableGenerator(name = "PKGenMitarbeiterRessourceGroupAssoc", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "MitarbeiterRessourceGroupAssoc_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PKGenMitarbeiterRessourceGroupAssoc")
    private Long id;

    private Date startDate;
    private Date stopDate;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private RessourceGroup ressourceGroup;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Mitarbeiter mitarbeiter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public RessourceGroup getRessourceGroup() {
        return ressourceGroup;
    }

    public void setRessourceGroup(RessourceGroup ressourceGroup) {
        this.ressourceGroup = ressourceGroup;
    }

    public Mitarbeiter getMitarbeiter() {
        return mitarbeiter;
    }

    public void setMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }
}
