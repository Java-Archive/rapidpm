package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 23.11.12
 * Time: 13:15
 * To change this template use File | Settings | File Templates.
 */
public class TestCaseDemoDaten {

    private static final List<String> testCaseList_PlanningUnit1;
    private static final List<String> testCaseList_PlanningUnit1_1;
    private static final List<String> testCaseList_PlanningUnit1_2;
    private static final List<String> testCaseList_PlanningUnit2;
    static {
        testCaseList_PlanningUnit1 = new ArrayList<>();
        testCaseList_PlanningUnit1.add("Alle Unter PlanningUnits abgearbeitet");
        testCaseList_PlanningUnit1.add("Testcase für PlanningUnit 1");
        testCaseList_PlanningUnit1.add("Dieses oder jenes");
        testCaseList_PlanningUnit1.add("Alle eigenen Issues abgearbeitet.");

        testCaseList_PlanningUnit1_1 = new ArrayList<>();
        testCaseList_PlanningUnit1.add("A) ist möglich");
        testCaseList_PlanningUnit1.add("Alle Issues abgearbeitet");
        testCaseList_PlanningUnit1.add("Beispieltestcase für Fall B)");

        testCaseList_PlanningUnit1_2 = new ArrayList<>();
        testCaseList_PlanningUnit1.add("Planning Unit 1.2 abgearbeitet");
        testCaseList_PlanningUnit1.add("Alle Issues Abgearbeitet");
        testCaseList_PlanningUnit1.add("Testcas für Planning Unit 1.2");


        testCaseList_PlanningUnit2 = new ArrayList<>();
        testCaseList_PlanningUnit1.add("Testcase für Planning Unit 2");
        testCaseList_PlanningUnit1.add("Noch ein Testcase für Planning Unit 2");
    }

    public static List<String> getTestCaseList_PlanningUnit1() {
        return testCaseList_PlanningUnit1;
    }

    public static List<String> getTestCaseList_PlanningUnit1_1() {
        return testCaseList_PlanningUnit1_1;
    }

    public static List<String> getTestCaseList_PlanningUnit1_2() {
        return testCaseList_PlanningUnit1_2;
    }

    public static List<String> getTestCaseList_PlanningUnit2() {
        return testCaseList_PlanningUnit2;
    }
}
