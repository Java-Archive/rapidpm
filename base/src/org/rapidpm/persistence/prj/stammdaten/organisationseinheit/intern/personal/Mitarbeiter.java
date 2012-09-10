package org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal;

import org.rapidpm.persistence.prj.stammdaten.person.Person;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * User: sven.ruppert
 * Date: 02.12.11
 * Time: 11:45
 * <p/>
 * Mitarbeiterstammdaten, Personalangaben.
 */
@Entity()
public class Mitarbeiter {

    @Id
    @TableGenerator(name = "PKGenMitarbeiter", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "Mitarbeiter_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PKGenMitarbeiter")
    private Long id;


    @Basic
    private String mitarbeiternummer;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Person person;
    @Basic
    private Date einstellungsDatum;
    @Basic
    private Date austrittsDatum;
    @Basic
    private int workHoursPerWeek;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<MitarbeiterComment> comments;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<MitarbeiterUrlaub> mitarbeiterUrlaubsListe;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<MitarbeiterGehalt> mitarbeiterGehaltsListe;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)  //TODO prüfen ob das richtig ist
    private List<MitarbeiterRessourceGroupAssoc> mitarbeiterRessourceGroupAssocListe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMitarbeiternummer() {
        return mitarbeiternummer;
    }

    public void setMitarbeiternummer(String mitarbeiternummer) {
        this.mitarbeiternummer = mitarbeiternummer;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getEinstellungsDatum() {
        return einstellungsDatum;
    }

    public void setEinstellungsDatum(Date einstellungsDatum) {
        this.einstellungsDatum = einstellungsDatum;
    }

    public Date getAustrittsDatum() {
        return austrittsDatum;
    }

    public void setAustrittsDatum(Date austrittsDatum) {
        this.austrittsDatum = austrittsDatum;
    }

    public int getWorkHoursPerWeek() {
        return workHoursPerWeek;
    }

    public void setWorkHoursPerWeek(int workHoursPerWeek) {
        this.workHoursPerWeek = workHoursPerWeek;
    }

    public List<MitarbeiterComment> getComments() {
        return comments;
    }

    public void setComments(List<MitarbeiterComment> comments) {
        this.comments = comments;
    }

    public List<MitarbeiterUrlaub> getMitarbeiterUrlaubsListe() {
        return mitarbeiterUrlaubsListe;
    }

    public void setMitarbeiterUrlaubsListe(List<MitarbeiterUrlaub> mitarbeiterUrlaubsListe) {
        this.mitarbeiterUrlaubsListe = mitarbeiterUrlaubsListe;
    }

    public List<MitarbeiterGehalt> getMitarbeiterGehaltsListe() {
        return mitarbeiterGehaltsListe;
    }

    public void setMitarbeiterGehaltsListe(List<MitarbeiterGehalt> mitarbeiterGehaltsListe) {
        this.mitarbeiterGehaltsListe = mitarbeiterGehaltsListe;
    }

    public List<MitarbeiterRessourceGroupAssoc> getMitarbeiterRessourceGroupAssocListe() {
        return mitarbeiterRessourceGroupAssocListe;
    }

    public void setMitarbeiterRessourceGroupAssocListe(List<MitarbeiterRessourceGroupAssoc> mitarbeiterRessourceGroupAssocListe) {
        this.mitarbeiterRessourceGroupAssocListe = mitarbeiterRessourceGroupAssocListe;
    }
}
