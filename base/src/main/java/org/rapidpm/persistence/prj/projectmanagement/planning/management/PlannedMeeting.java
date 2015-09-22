package org.rapidpm.persistence.prj.projectmanagement.planning.management;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: svenruppert
 * Date: 30.07.12
 * Time: 08:56
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class PlannedMeeting {


  @Id
  @TableGenerator(name = "PKGenPlannedMeeting", table = "pk_gen", pkColumnName = "gen_key",
      pkColumnValue = "PlannedMeeting_id", valueColumnName = "gen_value", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.TABLE,
      generator = "PKGenPlannedMeeting")
  private Long id;


  @Basic
  private String meetingName;
  @Basic private String description;
  @Basic private int count;          //Anzahl dieser Meetings im Verlaufe eines Projektes

  @Basic private int averageMinutes;     //Durchscnittliche Dauer eines Meetings.

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMeetingName() {
    return meetingName;
  }

  public void setMeetingName(String meetingName) {
    this.meetingName = meetingName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getAverageMinutes() {
    return averageMinutes;
  }

  public void setAverageMinutes(int averageMinutes) {
    this.averageMinutes = averageMinutes;
  }
}
