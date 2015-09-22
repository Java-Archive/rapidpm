package org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal;

import javax.persistence.*;
import java.util.Date;

/**
 * User: sven.ruppert
 * Date: 02.12.11
 * Time: 12:07
 */
@Entity
public class MitarbeiterGehalt {

  @Id
  @TableGenerator(name = "PKGenMitarbeiterGehalt", table = "pk_gen", pkColumnName = "gen_key",
      pkColumnValue = "MitarbeiterGehalt_id", valueColumnName = "gen_value", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.TABLE,
      generator = "PKGenMitarbeiterGehalt")
  private Long id;

  private Date startDate;
  private Date stopDate;
  private Float jahresgehalt;

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

  public Float getJahresgehalt() {
    return jahresgehalt;
  }

  public void setJahresgehalt(Float jahresgehalt) {
    this.jahresgehalt = jahresgehalt;
  }
}
