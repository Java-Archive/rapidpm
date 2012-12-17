package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.impl.planningunit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.controlling.ControllingUnitContainer;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.AbstractPlanningUnitControllingUnitContainerCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.impl.issue.IssueDurationCalulatorTest;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project.IssueBaseDemoDaten;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project.PlannedProjectDemoDaten;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project.PlanningUnitDemoDaten;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 03.12.12
 * Time: 16:24
 * To change this template use File | Settings | File Templates.
 */
public class PlanningUnitDurationCalculatorTest  extends BasePlanningUnitCalculatorTest {

    @Test
    public void testNullParameter(){
    }

    @Test
    public void testCalculationWithNoSubPlanningUnits(){}

    @Test
    public void testCalculationWithNoSubIssues(){}

    @Test
    public void testCalculationWithSubPlanningUnitListNull(){}

    @Test
    public void testCalculationWithSubIssueListNull(){}


}
