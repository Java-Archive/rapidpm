package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell;

import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Marco
 * Date: 17.08.12
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */
public class Projekt {
    private long projektId;
    private String projektName;
    private List<PlanningUnit> planningUnits = new ArrayList<>();

    public PlanningUnit getPlanningUnit(final String itemId) {
        PlanningUnit result = null;
        for (final PlanningUnit planningUnit : planningUnits) {
            if (planningUnit.getPlanningUnitName().equals(itemId)) {
                result = planningUnit;
            }
        }
        return result;
    }

    //REFAC unelegant, aber funktional
    public void findPlanningUnitAndWriteReferenceInList(final List<PlanningUnit> planningUnits, final String itemId,
                                                        final List<PlanningUnit> resultUnitList) {
        for (final PlanningUnit planningUnit : planningUnits) {
            if (planningUnit.getPlanningUnitName().equals(itemId)) {
                resultUnitList.add(planningUnit);
                return;
            }
            if (planningUnit.getKindPlanningUnits() != null && !planningUnit.getKindPlanningUnits().isEmpty()) {
                findPlanningUnitAndWriteReferenceInList(planningUnit.getKindPlanningUnits(), itemId, resultUnitList);
            }
        }
    }


    public long getProjektId() {
        return projektId;
    }

    public void setProjektId(long projektId) {
        this.projektId = projektId;
    }

    public String getProjektName() {
        return projektName;
    }

    public void setProjektName(String projektName) {
        this.projektName = projektName;
    }

    public List<PlanningUnit> getPlanningUnits() {
        return planningUnits;
    }

    public List<String> getPlanningUnitsNames() {
        final List<String> result = new ArrayList<>();
        for (final PlanningUnit planningUnit : planningUnits) {
            result.add(planningUnit.getPlanningUnitName());
        }
        return result;
    }

    public void setPlanningUnits(final ArrayList<PlanningUnit> planningUnits) {
        this.planningUnits = planningUnits;
    }
}
