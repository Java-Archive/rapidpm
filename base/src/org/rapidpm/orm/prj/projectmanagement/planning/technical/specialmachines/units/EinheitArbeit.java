package org.rapidpm.orm.prj.projectmanagement.planning.technical.specialmachines.units; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 01.12.11
 * Time: 10:25
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.rapidpm.orm.prj.projectmanagement.planning.technical.specialmachines.stammdaten.Maschine;
import org.rapidpm.orm.prj.stammdaten.person.Person;
import org.apache.log4j.Logger;

public class EinheitArbeit {
    private static final Logger logger = Logger.getLogger(EinheitArbeit.class);

    /**
     * durchführende Person
     */
    private Person person;

    /**
     * Was macht die Person
     */
    private String taetigkeit;

    /**
     * Wieviel Zeit in Minuten ist f diesen Arbeitsgang geplant
     */
    private long plannedWorkMinutes;


    /**
     * Wie oft muss dieser Arbeitsgang wiederholt werden?
     */
    private int counter;


    /**
     * verwendete Maschine
     */
    private Maschine maschine;


}
