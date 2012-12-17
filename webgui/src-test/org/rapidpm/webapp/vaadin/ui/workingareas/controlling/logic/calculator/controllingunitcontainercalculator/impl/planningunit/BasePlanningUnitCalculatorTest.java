package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.impl.planningunit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.rapidpm.persistence.prj.projectmanagement.controlling.BaseControllingunit;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 14.12.12
 * Time: 14:08
 * To change this template use File | Settings | File Templates.
 */
public class BasePlanningUnitCalculatorTest {

    protected static PlanningUnit planningUnitWithSubPlanningUnitsAndSubIssues;
    protected static PlanningUnit planningUnitWithoutSubPlanningUnitWithSubIssues;
    protected static PlanningUnit planningUnitWithSubPlanningUnitsWithoztSubIssues;
    protected static PlanningUnit planningUNitWithNullSubPlanningUnitListAndSubIssues;
    protected static PlanningUnit planningUnitWithSubPlanningUnitAndNulllanningUnitList;

    @BeforeClass
    public static void persistDemoTestScenario(){

    }

    @AfterClass
    public static void deleteDemoTestScenario(){

    }
}
