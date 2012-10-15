package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement;

import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;

import static org.rapidpm.Constants.HOURS_DAY;
import static org.rapidpm.Constants.MINS_HOUR;

/**
 * Created with IntelliJ IDEA.
 * User: Marco
 * Date: 19.08.12
 * Time: 11:11
 * To change this template use File | Settings | File Templates.
 */
public class DaysHoursMinutesItem {
    private Integer days;
    private Integer hours;
    private Integer minutes;

    public DaysHoursMinutesItem() {
        days = 0;
        hours = 0;
        minutes = 0;
    }

    public DaysHoursMinutesItem(final PlanningUnitElement planningUnitElement) {
        days = planningUnitElement.getPlannedDays();
        hours = planningUnitElement.getPlannedHours();
        minutes = planningUnitElement.getPlannedMinutes();
    }

    public static void correctDaysHoursMinutesItem(final DaysHoursMinutesItem item) {
        final int hours = item.getMinutes() / MINS_HOUR;
        if (hours > 0) {
            item.setHours(item.getHours() + hours);
            item.setMinutes(item.getMinutes() - (hours * MINS_HOUR));
        }
        final int days = item.getHours() / HOURS_DAY;
        if (days > 0) {
            item.setDays(item.getDays() + days);
            item.setHours(item.getHours() - (days * HOURS_DAY));
        }
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
