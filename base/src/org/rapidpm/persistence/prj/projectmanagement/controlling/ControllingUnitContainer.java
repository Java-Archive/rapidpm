package org.rapidpm.persistence.prj.projectmanagement.controlling;

import javax.persistence.Transient;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 07.11.12
 * Time: 15:14
 * To change this template use File | Settings | File Templates.
 */
public class ControllingUnitContainer<T> {
    T plannedAbsolutte;
    T actualAbsolute;
    T remainingAbsolute;

    double usedRelative;
    double remainingRelative;

    Map<Date, T> cumulativeDateToControllingValueMap;

    public T getPlannedAbsolutte() {
        return plannedAbsolutte;
    }

    public void setPlannedAbsolutte(T plannedAbsolutte) {
        this.plannedAbsolutte = plannedAbsolutte;
    }

    public T getActualAbsolute() {
        return actualAbsolute;
    }

    public void setActualAbsolute(T actualAbsolute) {
        this.actualAbsolute = actualAbsolute;
    }

    public T getRemainingAbsolute() {
        return remainingAbsolute;
    }

    public void setRemainingAbsolute(T remainingAbsolute) {
        this.remainingAbsolute = remainingAbsolute;
    }

    public double getUsedRelative() {
        return usedRelative;
    }

    public void setUsedRelative(double usedRelative) {
        this.usedRelative = usedRelative;
    }

    public double getRemainingRelative() {
        return remainingRelative;
    }

    public void setRemainingRelative(double remainingRelative) {
        this.remainingRelative = remainingRelative;
    }

    public Map<Date, T> getCumulativeDateToControllingValueMap() {
        return cumulativeDateToControllingValueMap;
    }

    public void setCumulativeDateToControllingValueMap(Map<Date, T> cumulativeDateToControllingValueMap) {
        this.cumulativeDateToControllingValueMap = cumulativeDateToControllingValueMap;
    }

    @Override
    public String toString() {
        return "ControllingUnitContainer{" +
                "plannedAbsolutte=" + plannedAbsolutte +
                ", actualAbsolute=" + actualAbsolute +
                ", remainingAbsolute=" + remainingAbsolute +
                ", usedRelative=" + usedRelative +
                ", remainingRelative=" + remainingRelative +
                ", cumulativeDateToControllingValueMap=" + cumulativeDateToControllingValueMap +
                '}';
    }
}
