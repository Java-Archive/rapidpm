package org.rapidpm.persistence.prj.projectmanagement.planning.management;

import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;

import javax.persistence.*;
import java.util.List;

/**
 * User: svenruppert
 * Date: 30.07.12
 * Time: 09:03
 * <p>
 * Die Verbindung zwischen einem geplanten Meeting und den dafür benötigten
 * RessourcenGruppen.
 */
@Entity
public class PlannedMeetingRessourceGroupAssoc {

  @Id
  @TableGenerator(name = "PKGenPlannedMeetingRessourceGroupAssoc", table = "pk_gen", pkColumnName = "gen_key",
      pkColumnValue = "PlannedMeetingRessourceGroupAssoc_id", valueColumnName = "gen_value", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.TABLE,
      generator = "PKGenPlannedMeetingRessourceGroupAssoc")
  private Long id;


  @Basic private int counter;

  @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY) private PlannedMeeting plannedMeeting;
  @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY) private List<RessourceGroup> ressourceGroup;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getCounter() {
    return counter;
  }

  public void setCounter(int counter) {
    this.counter = counter;
  }

  public PlannedMeeting getPlannedMeeting() {
    return plannedMeeting;
  }

  public void setPlannedMeeting(PlannedMeeting plannedMeeting) {
    this.plannedMeeting = plannedMeeting;
  }

  public List<RessourceGroup> getRessourceGroup() {
    return ressourceGroup;
  }

  public void setRessourceGroup(List<RessourceGroup> ressourceGroup) {
    this.ressourceGroup = ressourceGroup;
  }
}
