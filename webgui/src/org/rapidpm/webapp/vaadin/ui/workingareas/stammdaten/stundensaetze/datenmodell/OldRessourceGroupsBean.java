package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell;

import java.util.ArrayList;
import java.util.List;

/**
 * @deprecated wird noch in einigen Teilen des Programms verwendet, wird aber durch die persistente Entity-Klasse
 * ersetzt
 **/
public class OldRessourceGroupsBean {

    private List<OldRessourceGroup> oldRessourceGroups = new ArrayList<>();

    public OldRessourceGroupsBean(){
        fill();  //TODO indirekte Initialisierung ist meist nicht so gut..
    }

    private void fill() {
        final OldRessourceGroup oldRessourceGroup = new OldRessourceGroup();
        oldRessourceGroup.setName("GF");
        oldRessourceGroup.setBruttoGehalt(200000.0);
        oldRessourceGroup.setHoursPerWeek(40);
        oldRessourceGroup.setWeeksPerYear(50);
        oldRessourceGroup.setFacturizable(0.5);
        oldRessourceGroup.setExternalEurosPerHour(125.0);
        oldRessourceGroup.setPlanAnzahl(1);
        final OldRessourceGroup oldRessourceGroup2 = new OldRessourceGroup();
        oldRessourceGroup2.setName("Multiprojekt Manager");
        oldRessourceGroup2.setBruttoGehalt(83000.0);
        oldRessourceGroup2.setHoursPerWeek(40);
        oldRessourceGroup2.setWeeksPerYear(46);
        oldRessourceGroup2.setFacturizable(0.5);
        oldRessourceGroup2.setExternalEurosPerHour(125.0);
        oldRessourceGroup2.setPlanAnzahl(1);
        final OldRessourceGroup oldRessourceGroup3 = new OldRessourceGroup();
        oldRessourceGroup3.setName("Projektleiter / Scrum Master");
        oldRessourceGroup3.setBruttoGehalt(72000.0);
        oldRessourceGroup3.setHoursPerWeek(40);
        oldRessourceGroup3.setWeeksPerYear(46);
        oldRessourceGroup3.setFacturizable(0.75);
        oldRessourceGroup3.setExternalEurosPerHour(75.0);
        oldRessourceGroup3.setPlanAnzahl(1);
        final OldRessourceGroup oldRessourceGroup4 = new OldRessourceGroup();
        oldRessourceGroup4.setName("Senior Projektmitarbeiter");
        oldRessourceGroup4.setBruttoGehalt(72000.0);
        oldRessourceGroup4.setHoursPerWeek(40);
        oldRessourceGroup4.setWeeksPerYear(46);
        oldRessourceGroup4.setFacturizable(0.75);
        oldRessourceGroup4.setExternalEurosPerHour(65.0);
        oldRessourceGroup4.setPlanAnzahl(1);
        final OldRessourceGroup oldRessourceGroup5 = new OldRessourceGroup();
        oldRessourceGroup5.setName("Junior Projektmitarbeiter");
        oldRessourceGroup5.setBruttoGehalt(45000.0);
        oldRessourceGroup5.setHoursPerWeek(40);
        oldRessourceGroup5.setWeeksPerYear(46);
        oldRessourceGroup5.setFacturizable(0.75);
        oldRessourceGroup5.setExternalEurosPerHour(45.0);
        oldRessourceGroup5.setPlanAnzahl(1);
        final OldRessourceGroup oldRessourceGroup6 = new OldRessourceGroup();
        oldRessourceGroup6.setName("Aushilfe / stud. Projektmitarbeiter");
        oldRessourceGroup6.setBruttoGehalt(17000.0);
        oldRessourceGroup6.setHoursPerWeek(40);
        oldRessourceGroup6.setWeeksPerYear(46);
        oldRessourceGroup6.setFacturizable(0.75);
        oldRessourceGroup6.setExternalEurosPerHour(25.0);
        oldRessourceGroup6.setPlanAnzahl(1);
        final OldRessourceGroup oldRessourceGroup7 = new OldRessourceGroup();
        oldRessourceGroup7.setName("externe Ressource");
        oldRessourceGroup7.setBruttoGehalt(125000.0);
        oldRessourceGroup7.setHoursPerWeek(40);
        oldRessourceGroup7.setWeeksPerYear(46);
        oldRessourceGroup7.setFacturizable(0.80);
        oldRessourceGroup7.setExternalEurosPerHour(90.0);
        oldRessourceGroup7.setPlanAnzahl(1);
        final OldRessourceGroup oldRessourceGroup8 = new OldRessourceGroup();
        oldRessourceGroup8.setName("xxx");
        oldRessourceGroup8.setBruttoGehalt(1.0);
        oldRessourceGroup8.setHoursPerWeek(40);
        oldRessourceGroup8.setWeeksPerYear(46);
        oldRessourceGroup8.setFacturizable(0.50);
        oldRessourceGroup8.setExternalEurosPerHour(0.0);
        oldRessourceGroup8.setPlanAnzahl(1);
        final OldRessourceGroup oldRessourceGroup9 = new OldRessourceGroup();
        oldRessourceGroup9.setName("Backoffice");
        oldRessourceGroup9.setBruttoGehalt(62000.0);
        oldRessourceGroup9.setHoursPerWeek(20);
        oldRessourceGroup9.setWeeksPerYear(46);
        oldRessourceGroup9.setFacturizable(0.01);
        oldRessourceGroup9.setExternalEurosPerHour(0.0);
        oldRessourceGroup9.setPlanAnzahl(1);

        oldRessourceGroups.add(oldRessourceGroup);
        oldRessourceGroups.add(oldRessourceGroup2);
        oldRessourceGroups.add(oldRessourceGroup3);
        oldRessourceGroups.add(oldRessourceGroup4);
        oldRessourceGroups.add(oldRessourceGroup5);
        oldRessourceGroups.add(oldRessourceGroup6);
        oldRessourceGroups.add(oldRessourceGroup7);
        oldRessourceGroups.add(oldRessourceGroup8);
        oldRessourceGroups.add(oldRessourceGroup9);
    }

    public List<OldRessourceGroup> getOldRessourceGroups() {
        return oldRessourceGroups;
    }

    public void setOldRessourceGroups(final List<OldRessourceGroup> oldRessourceGroups) {
        this.oldRessourceGroups = oldRessourceGroups;
    }
}
