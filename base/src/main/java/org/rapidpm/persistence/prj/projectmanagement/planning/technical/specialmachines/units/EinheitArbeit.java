package org.rapidpm.persistence.prj.projectmanagement.planning.technical.specialmachines.units; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 01.12.11
 * Time: 10:25
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.rapidpm.persistence.prj.projectmanagement.planning.technical.specialmachines.stammdaten.Maschine;
import org.rapidpm.persistence.prj.stammdaten.person.Person;

public class EinheitArbeit {

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
