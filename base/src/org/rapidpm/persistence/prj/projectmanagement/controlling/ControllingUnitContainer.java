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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ControllingUnitContainer that = (ControllingUnitContainer) o;

        if (Double.compare(that.remainingRelative, remainingRelative) != 0) return false;
        if (Double.compare(that.usedRelative, usedRelative) != 0) return false;
        if (actualAbsolute != null ? !actualAbsolute.equals(that.actualAbsolute) : that.actualAbsolute != null)
            return false;
        if (cumulativeDateToControllingValueMap != null ? !cumulativeDateToControllingValueMap.equals(that.cumulativeDateToControllingValueMap) : that.cumulativeDateToControllingValueMap != null)
            return false;
        if (plannedAbsolutte != null ? !plannedAbsolutte.equals(that.plannedAbsolutte) : that.plannedAbsolutte != null)
            return false;
        if (remainingAbsolute != null ? !remainingAbsolute.equals(that.remainingAbsolute) : that.remainingAbsolute != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = plannedAbsolutte != null ? plannedAbsolutte.hashCode() : 0;
        result = 31 * result + (actualAbsolute != null ? actualAbsolute.hashCode() : 0);
        result = 31 * result + (remainingAbsolute != null ? remainingAbsolute.hashCode() : 0);
        temp = usedRelative != +0.0d ? Double.doubleToLongBits(usedRelative) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = remainingRelative != +0.0d ? Double.doubleToLongBits(remainingRelative) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (cumulativeDateToControllingValueMap != null ? cumulativeDateToControllingValueMap.hashCode() : 0);
        return result;
    }
}
