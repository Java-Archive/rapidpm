package org.rapidpm.persistence;

/**
 * Created by Marco on 23.05.2015.
 */
public class Edges {

    // PlannedProject
    public static final String CONSISTS_OF = "consistsOf";

    // PlanningUnit
    public static final String IS_FATHER_OF = "isFatherOf";
    public static final String HAS = "has";
    public static final String HAS_DESCRIPTION = "hasDescription";
    public static final String HAS_TESTCASE = "hasTestCase";

    // PlanningUnitElement
    public static final String VALID_FOR = "validFor";

    // Benutzer
    public static final String CREATED = "created";
    public static final String IS_RESPONSIBLE_FOR_PROJECT = "isResponsibleForProject";
    public static final String IS_RESPONSIBLE_FOR_PLANNINGUNIT = "isResponsibleForPlanningUnit";
    public static final String BELONGS_TO = "belongsTo"; // UserGroup
    public static final String IS_PART_OF = "isPartOf"; // Mandantengruppe
    public static final String BELONGS_TO_WEBAPP = "belongsToWebapp"; // Webapplication
}
