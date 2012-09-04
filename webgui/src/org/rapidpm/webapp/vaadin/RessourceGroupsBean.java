package org.rapidpm.webapp.vaadin;

import java.util.ArrayList;
import java.util.List;

public class RessourceGroupsBean {

    private List<RessourceGroup> ressourceGroups = new ArrayList<>();

    public RessourceGroupsBean(){
        fill();  //TODO indirekte Initialisierung ist meist nicht so gut..
    }

    private void fill() {
        final RessourceGroup ressourceGroup = new RessourceGroup();
        ressourceGroup.setName("GF");
        ressourceGroup.setBruttoGehalt(200000.0);
        ressourceGroup.setHoursPerWeek(40);
        ressourceGroup.setWeeksPerYear(50);
        ressourceGroup.setFacturizable(0.5);
        ressourceGroup.setExternalEurosPerHour(125.0);
        ressourceGroup.setPlanAnzahl(1);
        final RessourceGroup ressourceGroup2 = new RessourceGroup();
        ressourceGroup2.setName("Multiprojekt Manager");
        ressourceGroup2.setBruttoGehalt(83000.0);
        ressourceGroup2.setHoursPerWeek(40);
        ressourceGroup2.setWeeksPerYear(46);
        ressourceGroup2.setFacturizable(0.5);
        ressourceGroup2.setExternalEurosPerHour(125.0);
        ressourceGroup2.setPlanAnzahl(1);
        final RessourceGroup ressourceGroup3 = new RessourceGroup();
        ressourceGroup3.setName("Projektleiter / Scrum Master");
        ressourceGroup3.setBruttoGehalt(72000.0);
        ressourceGroup3.setHoursPerWeek(40);
        ressourceGroup3.setWeeksPerYear(46);
        ressourceGroup3.setFacturizable(0.75);
        ressourceGroup3.setExternalEurosPerHour(75.0);
        ressourceGroup3.setPlanAnzahl(1);
        final RessourceGroup ressourceGroup4 = new RessourceGroup();
        ressourceGroup4.setName("Senior Projektmitarbeiter");
        ressourceGroup4.setBruttoGehalt(72000.0);
        ressourceGroup4.setHoursPerWeek(40);
        ressourceGroup4.setWeeksPerYear(46);
        ressourceGroup4.setFacturizable(0.75);
        ressourceGroup4.setExternalEurosPerHour(65.0);
        ressourceGroup4.setPlanAnzahl(1);
        final RessourceGroup ressourceGroup5 = new RessourceGroup();
        ressourceGroup5.setName("Junior Projektmitarbeiter");
        ressourceGroup5.setBruttoGehalt(45000.0);
        ressourceGroup5.setHoursPerWeek(40);
        ressourceGroup5.setWeeksPerYear(46);
        ressourceGroup5.setFacturizable(0.75);
        ressourceGroup5.setExternalEurosPerHour(45.0);
        ressourceGroup5.setPlanAnzahl(1);
        final RessourceGroup ressourceGroup6 = new RessourceGroup();
        ressourceGroup6.setName("Aushilfe / stud. Projektmitarbeiter");
        ressourceGroup6.setBruttoGehalt(17000.0);
        ressourceGroup6.setHoursPerWeek(40);
        ressourceGroup6.setWeeksPerYear(46);
        ressourceGroup6.setFacturizable(0.75);
        ressourceGroup6.setExternalEurosPerHour(25.0);
        ressourceGroup6.setPlanAnzahl(1);
        final RessourceGroup ressourceGroup7 = new RessourceGroup();
        ressourceGroup7.setName("externe Ressource");
        ressourceGroup7.setBruttoGehalt(125000.0);
        ressourceGroup7.setHoursPerWeek(40);
        ressourceGroup7.setWeeksPerYear(46);
        ressourceGroup7.setFacturizable(0.80);
        ressourceGroup7.setExternalEurosPerHour(90.0);
        ressourceGroup7.setPlanAnzahl(1);
        final RessourceGroup ressourceGroup8 = new RessourceGroup();
        ressourceGroup8.setName("xxx");
        ressourceGroup8.setBruttoGehalt(1.0);
        ressourceGroup8.setHoursPerWeek(40);
        ressourceGroup8.setWeeksPerYear(46);
        ressourceGroup8.setFacturizable(0.50);
        ressourceGroup8.setExternalEurosPerHour(0.0);
        ressourceGroup8.setPlanAnzahl(1);
        final RessourceGroup ressourceGroup9 = new RessourceGroup();
        ressourceGroup9.setName("Backoffice");
        ressourceGroup9.setBruttoGehalt(62000.0);
        ressourceGroup9.setHoursPerWeek(20);
        ressourceGroup9.setWeeksPerYear(46);
        ressourceGroup9.setFacturizable(0.01);
        ressourceGroup9.setExternalEurosPerHour(0.0);
        ressourceGroup9.setPlanAnzahl(1);

        ressourceGroups.add(ressourceGroup);
        ressourceGroups.add(ressourceGroup2);
        ressourceGroups.add(ressourceGroup3);
        ressourceGroups.add(ressourceGroup4);
        ressourceGroups.add(ressourceGroup5);
        ressourceGroups.add(ressourceGroup6);
        ressourceGroups.add(ressourceGroup7);
        ressourceGroups.add(ressourceGroup8);
        ressourceGroups.add(ressourceGroup9);
    }

    public List<RessourceGroup> getRessourceGroups() {
        return ressourceGroups;
    }

    public void setRessourceGroups(final List<RessourceGroup> ressourceGroups) {
        this.ressourceGroups = ressourceGroups;
    }
}
