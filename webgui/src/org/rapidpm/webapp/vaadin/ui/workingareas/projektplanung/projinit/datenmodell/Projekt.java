package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell;

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
            if (planningUnitGroup.getPlanningUnitName().equals(itemId)){
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

    public ArrayList<PlanningUnitGroup> getPlanningUnitGroups() {
        return planningUnitGroups;
    }

    public ArrayList<String> getPlanningUnitGroupsNames() {
        ArrayList<String> result = new ArrayList<>();
        for (PlanningUnitGroup planningUnitGroup : planningUnitGroups) {
            result.add(planningUnitGroup.getPlanningUnitName());
        }
        return result;
    }

    public ArrayList<RessourceGroup> getRessourceGroups() {
        ArrayList<RessourceGroup> result = new ArrayList<>();
        for (final PlanningUnitGroup planningUnitGroup : planningUnitGroups) {
            for (final PlanningUnit planningUnit : planningUnitGroup.getPlanningUnitList()) {
                for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
                    if(!result.contains(planningUnitElement.getRessourceGroup()))
                        result.add(planningUnitElement.getRessourceGroup());
                }
            }
        }
        return result;
    }

    public void setPlanningUnitGroups(final ArrayList<PlanningUnitGroup> planningUnitGroups) {
        this.planningUnitGroups = planningUnitGroups;
    }


}
