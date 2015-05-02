package org.rapidpm.persistence;

import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.prj.stammdaten.person.Anrede;
import org.rapidpm.persistence.prj.stammdaten.person.Geschlecht;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.BenutzerGruppe;
import org.rapidpm.persistence.system.security.BenutzerWebapplikation;
import org.rapidpm.persistence.system.security.Mandantengruppe;
/**
 * Created by Marco on 28.04.2015.
 */
public class StartDataCreator {
    
    public static void run(OObjectDatabaseTx db){

        Mandantengruppe mandantengruppe = new Mandantengruppe();
        mandantengruppe.setMandantengruppe("RapidPM");
        db.save(mandantengruppe);

        BenutzerGruppe benutzerGruppe1 = new BenutzerGruppe();
        benutzerGruppe1.setGruppenname("User");
        db.save(benutzerGruppe1);
        BenutzerGruppe benutzerGruppe2 = new BenutzerGruppe();
        benutzerGruppe2.setGruppenname("'Anonymous");
        db.save(benutzerGruppe2);
        BenutzerGruppe benutzerGruppe3 = new BenutzerGruppe();
        benutzerGruppe3.setGruppenname("'Admin");
        db.save(benutzerGruppe3);

        BenutzerWebapplikation benutzerWebapplikation = new BenutzerWebapplikation();
        benutzerWebapplikation.setWebappName("RapidPM");
        db.save(benutzerWebapplikation);
        BenutzerWebapplikation benutzerWebapplikation1 = new BenutzerWebapplikation();
        benutzerWebapplikation1.setWebappName("'RapidPMBeta'");
        db.save(benutzerWebapplikation1);


        Benutzer benutzer1 = new Benutzer();
        benutzer1.setActive(true);
        benutzer1.setEmail("nobody@rapidpm.org");
        benutzer1.setFailedLogins(0);
        benutzer1.setHidden(false);
        benutzer1.setLogin("<not assigned>");
        benutzer1.setPasswd("geheim");
        db.save(benutzer1);

        Benutzer benutzer2 = new Benutzer();
        benutzer2.setActive(true);
        benutzer2.setEmail("sven.ruppert@rapidpm.org");
        benutzer2.setFailedLogins(0);
        benutzer2.setHidden(false);
        benutzer2.setLogin("sven.ruppert");
        benutzer2.setPasswd("geheim");
        db.save(benutzer2);

        Benutzer benutzer3 = new Benutzer();
        benutzer3.setActive(true);
        benutzer3.setEmail("marco.ebbinghaus@rapidpm.org");
        benutzer3.setFailedLogins(0);
        benutzer3.setHidden(false);
        benutzer3.setLogin("marco.ebbinghaus");
        benutzer3.setPasswd("geheim");
        db.save(benutzer3);

        RessourceGroup ressourceGroup1 = new RessourceGroup();
        ressourceGroup1.setName("GF");
        ressourceGroup1.setHoursPerWeek(40);
        ressourceGroup1.setWeeksPerYear(50);
        ressourceGroup1.setPlanAnzahl(1);
        ressourceGroup1.setFacturizable(0.5);
        ressourceGroup1.setExternalEurosPerHour(125.0);
        ressourceGroup1.setBruttoGehalt(200000.0);
        db.save(ressourceGroup1);

        RessourceGroup ressourceGroup2 = new RessourceGroup();
        ressourceGroup2.setName("Multiprojekt Manager");
        ressourceGroup2.setHoursPerWeek(40);
        ressourceGroup2.setWeeksPerYear(46);
        ressourceGroup2.setPlanAnzahl(1);
        ressourceGroup2.setFacturizable(0.5);
        ressourceGroup2.setExternalEurosPerHour(125.0);
        ressourceGroup2.setBruttoGehalt(83000.0);
        db.save(ressourceGroup2);

        RessourceGroup ressourceGroup3 = new RessourceGroup();
        ressourceGroup3.setName("Projektleiter / Scrum Master");
        ressourceGroup3.setHoursPerWeek(40);
        ressourceGroup3.setWeeksPerYear(46);
        ressourceGroup3.setPlanAnzahl(1);
        ressourceGroup3.setFacturizable(0.75);
        ressourceGroup3.setExternalEurosPerHour(75.0);
        ressourceGroup3.setBruttoGehalt(72000.0);
        db.save(ressourceGroup3);

        RessourceGroup ressourceGroup4 = new RessourceGroup();
        ressourceGroup4.setName("Senior Projektmitarbeiter");
        ressourceGroup4.setHoursPerWeek(40);
        ressourceGroup4.setWeeksPerYear(46);
        ressourceGroup4.setPlanAnzahl(1);
        ressourceGroup4.setFacturizable(0.75);
        ressourceGroup4.setExternalEurosPerHour(65.0);
        ressourceGroup4.setBruttoGehalt(72000.0);
        db.save(ressourceGroup4);

        RessourceGroup ressourceGroup5 = new RessourceGroup();
        ressourceGroup5.setName("Senior Projektmitarbeiter");
        ressourceGroup5.setHoursPerWeek(40);
        ressourceGroup5.setWeeksPerYear(46);
        ressourceGroup5.setPlanAnzahl(1);
        ressourceGroup5.setFacturizable(0.75);
        ressourceGroup5.setExternalEurosPerHour(45.0);
        ressourceGroup5.setBruttoGehalt(45000.0);
        db.save(ressourceGroup5);

        RessourceGroup ressourceGroup6 = new RessourceGroup();
        ressourceGroup6.setName("Aushilfe / stud. Projektmitarbeiter");
        ressourceGroup6.setHoursPerWeek(40);
        ressourceGroup6.setWeeksPerYear(46);
        ressourceGroup6.setPlanAnzahl(1);
        ressourceGroup6.setFacturizable(0.75);
        ressourceGroup6.setExternalEurosPerHour(25.0);
        ressourceGroup6.setBruttoGehalt(17000.0);
        db.save(ressourceGroup6);

        RessourceGroup ressourceGroup7 = new RessourceGroup();
        ressourceGroup7.setName("externe Ressource");
        ressourceGroup7.setHoursPerWeek(40);
        ressourceGroup7.setWeeksPerYear(46);
        ressourceGroup7.setPlanAnzahl(1);
        ressourceGroup7.setFacturizable(0.8);
        ressourceGroup7.setExternalEurosPerHour(90.0);
        ressourceGroup7.setBruttoGehalt(125000.0);
        db.save(ressourceGroup7);

        RessourceGroup ressourceGroup8 = new RessourceGroup();
        ressourceGroup8.setName("Backoffice");
        ressourceGroup8.setHoursPerWeek(20);
        ressourceGroup8.setWeeksPerYear(46);
        ressourceGroup8.setPlanAnzahl(1);
        ressourceGroup8.setFacturizable(0.01);
        ressourceGroup8.setExternalEurosPerHour(0.0);
        ressourceGroup8.setBruttoGehalt(62000.0);
        db.save(ressourceGroup8);

        PlannedProject plannedProject1 = new PlannedProject();
        plannedProject1.setActive(true);
        plannedProject1.setFakturierbar(true);
        plannedProject1.setInfo("erstes testprojekt");
        plannedProject1.setProjektName("Projekt Nr 1");
        plannedProject1.setProjektToken("PRO1");
        plannedProject1.setExternalDailyRate(750.0);
        plannedProject1.setHoursPerWorkingDay(8);
        db.save(plannedProject1);

        PlannedProject plannedProject2 = new PlannedProject();
        plannedProject2.setActive(true);
        plannedProject2.setFakturierbar(true);
        plannedProject2.setInfo("zweites testprojekt");
        plannedProject2.setProjektName("Projekt Nr 2");
        plannedProject2.setProjektToken("PRO2");
        plannedProject2.setExternalDailyRate(750.0);
        plannedProject2.setHoursPerWorkingDay(9);
        db.save(plannedProject2);

        PlannedProject plannedProject3 = new PlannedProject();
        plannedProject3.setActive(true);
        plannedProject3.setFakturierbar(true);
        plannedProject3.setInfo("drittes testprojekt");
        plannedProject3.setProjektName("Projekt Nr 3");
        plannedProject3.setProjektToken("PRO3");
        plannedProject3.setExternalDailyRate(750.0);
        plannedProject3.setHoursPerWorkingDay(8);
        db.save(plannedProject3);

        PlannedProject plannedProject4 = new PlannedProject();
        plannedProject4.setActive(true);
        plannedProject4.setFakturierbar(true);
        plannedProject4.setInfo("viertes testprojekt");
        plannedProject4.setProjektName("Projekt Nr 4");
        plannedProject4.setProjektToken("PRO4");
        plannedProject4.setExternalDailyRate(750.0);
        plannedProject4.setHoursPerWorkingDay(9);
        db.save(plannedProject4);

        Anrede anrede1 = new Anrede();
        anrede1.setAnrede("Herr");
        db.save(anrede1);
        Anrede anrede2 = new Anrede();
        anrede2.setAnrede("Frau");
        db.save(anrede2);

        Geschlecht geschlecht = new Geschlecht();
        geschlecht.setGeschlecht("männlich");
        db.save(geschlecht);
        Geschlecht geschlecht2 = new Geschlecht();
        geschlecht2.setGeschlecht("weiblich");
        db.save(geschlecht2);

        db.commit();
    }
}
