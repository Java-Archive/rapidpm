package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell;

import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroup;

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
    private List<PlanningUnitGroup> planningUnitGroups = new ArrayList<>();
    private List<RessourceGroup> ressourceGroups;

    public PlanningUnitGroup getPlanningUnitGroup(final String itemId){
        PlanningUnitGroup result = null;
        for (PlanningUnitGroup planningUnitGroup : planningUnitGroups){
            if (planningUnitGroup.getPlanningUnitName().equals(itemId)){
                result = planningUnitGroup;
            }
        }
        return result;
    }

    //unelegant, aber funktional
    public void findPlanningUnitAndWriteReferenceInList(final List<PlanningUnit> planningUnits, final String itemId,
                                                        final List<PlanningUnit> resultUnitList){
        for (final PlanningUnit planningUnit : planningUnits) {
            if (planningUnit.getPlanningUnitElementName().equals(itemId)) {
                resultUnitList.add(planningUnit);
                return;
            } else {
                if (planningUnit.getKindPlanningUnits() != null && !planningUnit.getKindPlanningUnits().isEmpty()) {
                    findPlanningUnitAndWriteReferenceInList(planningUnit.getKindPlanningUnits(), itemId, resultUnitList);
                }
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

    public List<PlanningUnitGroup> getPlanningUnitGroups() {
        return planningUnitGroups;
    }

    public List<String> getPlanningUnitGroupsNames() {
        ArrayList<String> result = new ArrayList<>();
        for (PlanningUnitGroup planningUnitGroup : planningUnitGroups) {
            result.add(planningUnitGroup.getPlanningUnitName());
        }
        return result;
    }

    public void setPlanningUnitGroups(final ArrayList<PlanningUnitGroup> planningUnitGroups) {
        this.planningUnitGroups = planningUnitGroups;
    }

    public List<RessourceGroup> getRessourceGroups() {
        return ressourceGroups;
    }

    public void setRessourceGroups(List<RessourceGroup> ressourceGroups) {
        this.ressourceGroups = ressourceGroups;
    }
}
