package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement;

import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;

import static org.rapidpm.Constants.MINS_HOUR;

/**
 * Created with IntelliJ IDEA.
 * User: Marco Ebbinghaus
 * Date: 19.08.12
 * Time: 11:11
 * To change this template use File | Settings | File Templates.
 */
public class DaysHoursMinutesItem {
  private Integer days;
  private Integer hours;
  private Integer minutes;

  private Integer minutesTotal;
  private Integer hoursPerWorkingDay;

  public DaysHoursMinutesItem(final PlanningUnitElement planningUnitElement, final Integer hoursPerWorkingDay) {
    this.hoursPerWorkingDay = hoursPerWorkingDay;
    minutesTotal = planningUnitElement.getPlannedMinutes();
    final int newItemMinutes = minutesTotal % MINS_HOUR;
    final int firstStepHours = minutesTotal / MINS_HOUR;
    final int newItemHours = firstStepHours % hoursPerWorkingDay;
    final int newItemDays = firstStepHours / hoursPerWorkingDay;
    days = newItemDays;
    hours = newItemHours;
    minutes = newItemMinutes;
  }

  public DaysHoursMinutesItem(final String timeString, final Integer hoursPerWorkingDay) {
    final String[] daysHoursMinutes = timeString.split(":");
    days = Integer.parseInt(daysHoursMinutes[0]);
    hours = Integer.parseInt(daysHoursMinutes[1]);
    minutes = Integer.parseInt(daysHoursMinutes[2]);
    this.hoursPerWorkingDay = hoursPerWorkingDay;
  }

  public DaysHoursMinutesItem(final Integer minutes, final Integer hoursPerWorkingDay) {
    this.hoursPerWorkingDay = hoursPerWorkingDay;
    minutesTotal = minutes;
    final int newItemMinutes = minutesTotal % MINS_HOUR;
    final int firstStepHours = minutesTotal / MINS_HOUR;
    final int newItemHours = firstStepHours % hoursPerWorkingDay;
    final int newItemDays = firstStepHours / hoursPerWorkingDay;
    days = newItemDays;
    hours = newItemHours;
    this.minutes = newItemMinutes;
  }

  public Integer getMinutesFromDaysHoursMinutes() {
    return ((days * hoursPerWorkingDay * MINS_HOUR) + (hours * MINS_HOUR) + (minutes));
  }

  public int getTotalMinutes() {
    return minutesTotal;
  }


  public Integer getDays() {
    return days;
  }

  public void setDays(Integer days) {
    this.days = days;
  }

  public Integer getHours() {
    return hours;
  }

  public void setHours(Integer hours) {
    this.hours = hours;
  }

  public Integer getMinutes() {
    return minutes;
  }

  public void setMinutes(Integer minutes) {
    this.minutes = minutes;
  }

  @Override
  public String toString() {
    final String daysString;
    final String hoursString;
    final String minutesString;
    if (days >= 10) {
      daysString = String.valueOf(days);
    } else {
      daysString = "0" + days;
    }

    if (hours >= 10) {
      hoursString = String.valueOf(hours);
    } else {
      hoursString = "0" + hours;
    }

    if (minutes >= 10) {
      minutesString = String.valueOf(minutes);
    } else {
      minutesString = "0" + minutes;
    }
    return (daysString + ":" + hoursString + ":" + minutesString);
  }
}
