package org.rapidpm.persistence.prj.projectmanagement.controlling;

import javax.persistence.Transient;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 07.11.12
 * Time: 15:17
 * To change this template use File | Settings | File Templates.
 */
public class BaseControllingunit {
    private ControllingUnitContainer<Double> internalCosts;
    private ControllingUnitContainer<Double> externalCosts;
    private ControllingUnitContainer<Integer> duration;
    private ControllingUnitContainer<String> status;

    public ControllingUnitContainer<Double> getInternalCosts() {
        return internalCosts;
    }

    public void setInternalCosts(ControllingUnitContainer<Double> internalCosts) {
        this.internalCosts = internalCosts;
    }

    public ControllingUnitContainer<Double> getExternalCosts() {
        return externalCosts;
    }

    public void setExternalCosts(ControllingUnitContainer<Double> externalCosts) {
        this.externalCosts = externalCosts;
    }

    public ControllingUnitContainer<Integer> getDuration() {
        return duration;
    }

    public void setDuration(ControllingUnitContainer<Integer> duration) {
        this.duration = duration;
    }

    public ControllingUnitContainer<String> getStatus() {
        return status;
    }

    public void setStatus(ControllingUnitContainer<String> status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BaseControllingunit{" +
                "internalCosts=" + internalCosts +
                ", externalCosts=" + externalCosts +
                ", duration=" + duration +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseControllingunit that = (BaseControllingunit) o;

        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (externalCosts != null ? !externalCosts.equals(that.externalCosts) : that.externalCosts != null)
            return false;
        if (internalCosts != null ? !internalCosts.equals(that.internalCosts) : that.internalCosts != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = internalCosts != null ? internalCosts.hashCode() : 0;
        result = 31 * result + (externalCosts != null ? externalCosts.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
