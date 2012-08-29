package org.rapidpm.transience.prj.projectmanagement.planning;

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
    private ArrayList<PlanningUnitGroup> planningUnitGroups = new ArrayList<>();


    public PlanningUnitGroup getPlanningUnitGroup(String itemId){
        PlanningUnitGroup result = null;
        for (PlanningUnitGroup planningUnitGroup : planningUnitGroups){
            if (planningUnitGroup.getPlanningUnitGroupName().equals(itemId)){
                result = planningUnitGroup;
            }
        }
        return result;
    }

//    public PlanningUnit findPlanningUnitAndWriteReferenceInList(String itemId){
//        PlanningUnit result = null;
//        for (PlanningUnitGroup planningUnitGroup : planningUnitGroups) {
//           result = findPlanningUnitAndWriteReferenceInList(planningUnitGroup.getPlanningUnitList(), itemId);
//        }
//        return result;
//    }

    //unelegant, aber funktional
    public void findPlanningUnitAndWriteReferenceInList(List<PlanningUnit> planningUnits, String itemId, ArrayList<PlanningUnit> resultUnitList){
        for (PlanningUnit planningUnit : planningUnits) {

            if (planningUnit.getPlanningUnitName().equals(itemId)) {
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

    public ArrayList<PlanningUnitGroup> getPlanningUnitGroups() {
        return planningUnitGroups;
    }

    public ArrayList<String> getPlanningUnitGroupsNames() {
        ArrayList<String> result = new ArrayList<>();
        for (PlanningUnitGroup planningUnitGroup : planningUnitGroups) {
            result.add(planningUnitGroup.getPlanningUnitGroupName());
        }
        return result;
    }

    public void setPlanningUnitGroups(final ArrayList<PlanningUnitGroup> planningUnitGroups) {
        this.planningUnitGroups = planningUnitGroups;
    }


}
