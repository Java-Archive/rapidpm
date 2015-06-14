package org.rapidpm.persistence;

import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.prj.stammdaten.person.Anrede;
import org.rapidpm.persistence.prj.stammdaten.person.Geschlecht;
import org.rapidpm.persistence.prj.textelement.TextElement;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.BenutzerGruppe;
import org.rapidpm.persistence.system.security.BenutzerWebapplikation;
import org.rapidpm.persistence.system.security.Mandantengruppe;

import java.security.InvalidKeyException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Marco on 28.04.2015.
 */
public class StartDataCreator {
    
    public static void run() throws MissingNonOptionalPropertyException, InvalidKeyException, NotYetImplementedException {

        BenutzerWebapplikation webapplikation = new BenutzerWebapplikation();
        webapplikation.setWebappName("RapidPMWebApp");

        Mandantengruppe mandantengruppe = new Mandantengruppe();
        mandantengruppe.setMandantengruppe("RapidPM");

        BenutzerGruppe benutzerGruppe1 = new BenutzerGruppe();
        benutzerGruppe1.setGruppenname("User");

        BenutzerGruppe benutzerGruppe2 = new BenutzerGruppe();
        benutzerGruppe2.setGruppenname("Anonymous");

        BenutzerGruppe benutzerGruppe3 = new BenutzerGruppe();
        benutzerGruppe3.setGruppenname("Admin");

        Benutzer benutzer1 = new Benutzer();
        benutzer1.setActive(true);
        benutzer1.setEmail("nobody@rapidpm.org");
        benutzer1.setFailedLogins(0);
        benutzer1.setHidden(false);
        benutzer1.setLogin("<not assigned>");
        benutzer1.setPasswd("geheim");
        benutzer1.setMandantengruppe(mandantengruppe);
        benutzer1.setBenutzerWebapplikation(webapplikation);
        benutzer1.setBenutzerGruppe(benutzerGruppe1);
        benutzer1 = DaoFactorySingleton.getInstance().getBenutzerDAO().createEntityFull(benutzer1);

        webapplikation = benutzer1.getBenutzerWebapplikation();
        mandantengruppe = benutzer1.getMandantengruppe();

        Benutzer benutzer2 = new Benutzer();
        benutzer2.setActive(true);
        benutzer2.setEmail("sven.ruppert@rapidpm.org");
        benutzer2.setFailedLogins(0);
        benutzer2.setHidden(false);
        benutzer2.setLogin("sven.ruppert");
        benutzer2.setPasswd("geheim");
        benutzer2.setMandantengruppe(mandantengruppe);
        benutzer2.setBenutzerWebapplikation(webapplikation);
        benutzer2.setBenutzerGruppe(benutzerGruppe2);
        benutzer2 = DaoFactorySingleton.getInstance().getBenutzerDAO().createEntityFull(benutzer2);

        Benutzer benutzer3 = new Benutzer();
        benutzer3.setActive(true);
        benutzer3.setEmail("marco.ebbinghaus@rapidpm.org");
        benutzer3.setFailedLogins(0);
        benutzer3.setHidden(false);
        benutzer3.setLogin("marco.ebbinghaus");
        benutzer3.setPasswd("geheim");
        benutzer3.setMandantengruppe(mandantengruppe);
        benutzer3.setBenutzerWebapplikation(webapplikation);
        benutzer3.setBenutzerGruppe(benutzerGruppe3);
        benutzer3 = DaoFactorySingleton.getInstance().getBenutzerDAO().createEntityFull(benutzer3);

        RessourceGroup ressourceGroup1 = new RessourceGroup();
        ressourceGroup1.setName("GF");
        ressourceGroup1.setHoursPerWeek(40);
        ressourceGroup1.setWeeksPerYear(50);
        ressourceGroup1.setPlanAnzahl(1);
        ressourceGroup1.setFacturizable(0.5);
        ressourceGroup1.setExternalEurosPerHour(125.0);
        ressourceGroup1.setBruttoGehalt(200000.0);
        ressourceGroup1 = DaoFactorySingleton.getInstance().getRessourceGroupDAO().createEntityFlat(ressourceGroup1);

        RessourceGroup ressourceGroup2 = new RessourceGroup();
        ressourceGroup2.setName("Multiprojekt Manager");
        ressourceGroup2.setHoursPerWeek(40);
        ressourceGroup2.setWeeksPerYear(46);
        ressourceGroup2.setPlanAnzahl(1);
        ressourceGroup2.setFacturizable(0.5);
        ressourceGroup2.setExternalEurosPerHour(125.0);
        ressourceGroup2.setBruttoGehalt(83000.0);
        ressourceGroup2 = DaoFactorySingleton.getInstance().getRessourceGroupDAO().createEntityFlat(ressourceGroup2);

        RessourceGroup ressourceGroup3 = new RessourceGroup();
        ressourceGroup3.setName("Projektleiter / Scrum Master");
        ressourceGroup3.setHoursPerWeek(40);
        ressourceGroup3.setWeeksPerYear(46);
        ressourceGroup3.setPlanAnzahl(1);
        ressourceGroup3.setFacturizable(0.75);
        ressourceGroup3.setExternalEurosPerHour(75.0);
        ressourceGroup3.setBruttoGehalt(72000.0);
        ressourceGroup3 = DaoFactorySingleton.getInstance().getRessourceGroupDAO().createEntityFlat(ressourceGroup3);

        RessourceGroup ressourceGroup4 = new RessourceGroup();
        ressourceGroup4.setName("Senior Projektmitarbeiter");
        ressourceGroup4.setHoursPerWeek(40);
        ressourceGroup4.setWeeksPerYear(46);
        ressourceGroup4.setPlanAnzahl(1);
        ressourceGroup4.setFacturizable(0.75);
        ressourceGroup4.setExternalEurosPerHour(65.0);
        ressourceGroup4.setBruttoGehalt(72000.0);
        ressourceGroup4 = DaoFactorySingleton.getInstance().getRessourceGroupDAO().createEntityFlat(ressourceGroup4);

        RessourceGroup ressourceGroup5 = new RessourceGroup();
        ressourceGroup5.setName("Senior Projektmitarbeiter");
        ressourceGroup5.setHoursPerWeek(40);
        ressourceGroup5.setWeeksPerYear(46);
        ressourceGroup5.setPlanAnzahl(1);
        ressourceGroup5.setFacturizable(0.75);
        ressourceGroup5.setExternalEurosPerHour(45.0);
        ressourceGroup5.setBruttoGehalt(45000.0);
        ressourceGroup5 = DaoFactorySingleton.getInstance().getRessourceGroupDAO().createEntityFlat(ressourceGroup5);

        RessourceGroup ressourceGroup6 = new RessourceGroup();
        ressourceGroup6.setName("Aushilfe / stud. Projektmitarbeiter");
        ressourceGroup6.setHoursPerWeek(40);
        ressourceGroup6.setWeeksPerYear(46);
        ressourceGroup6.setPlanAnzahl(1);
        ressourceGroup6.setFacturizable(0.75);
        ressourceGroup6.setExternalEurosPerHour(25.0);
        ressourceGroup6.setBruttoGehalt(17000.0);
        ressourceGroup6 = DaoFactorySingleton.getInstance().getRessourceGroupDAO().createEntityFlat(ressourceGroup6);

        RessourceGroup ressourceGroup7 = new RessourceGroup();
        ressourceGroup7.setName("externe Ressource");
        ressourceGroup7.setHoursPerWeek(40);
        ressourceGroup7.setWeeksPerYear(46);
        ressourceGroup7.setPlanAnzahl(1);
        ressourceGroup7.setFacturizable(0.8);
        ressourceGroup7.setExternalEurosPerHour(90.0);
        ressourceGroup7.setBruttoGehalt(125000.0);
        ressourceGroup7 = DaoFactorySingleton.getInstance().getRessourceGroupDAO().createEntityFlat(ressourceGroup7);

        RessourceGroup ressourceGroup8 = new RessourceGroup();
        ressourceGroup8.setName("Backoffice");
        ressourceGroup8.setHoursPerWeek(20);
        ressourceGroup8.setWeeksPerYear(46);
        ressourceGroup8.setPlanAnzahl(1);
        ressourceGroup8.setFacturizable(0.01);
        ressourceGroup8.setExternalEurosPerHour(0.0);
        ressourceGroup8.setBruttoGehalt(62000.0);
        ressourceGroup8 = DaoFactorySingleton.getInstance().getRessourceGroupDAO().createEntityFlat(ressourceGroup8);

        PlannedProject plannedProject1 = new PlannedProject();
        plannedProject1.setActive(true);
        plannedProject1.setFakturierbar(true);
        plannedProject1.setInfo("erstes testprojekt");
        plannedProject1.setProjektName("Projekt Nr 1");
        plannedProject1.setProjektToken("PRO1");
        plannedProject1.setExternalDailyRate(750.0);
        plannedProject1.setHoursPerWorkingDay(8);
        plannedProject1.setResponsiblePerson(benutzer2);
        plannedProject1.setCreator(benutzer2);

        PlannedProject plannedProject2 = new PlannedProject();
        plannedProject2.setActive(true);
        plannedProject2.setFakturierbar(true);
        plannedProject2.setInfo("zweites testprojekt");
        plannedProject2.setProjektName("Projekt Nr 2");
        plannedProject2.setProjektToken("PRO2");
        plannedProject2.setExternalDailyRate(750.0);
        plannedProject2.setHoursPerWorkingDay(9);

        PlannedProject plannedProject3 = new PlannedProject();
        plannedProject3.setActive(true);
        plannedProject3.setFakturierbar(true);
        plannedProject3.setInfo("drittes testprojekt");
        plannedProject3.setProjektName("Projekt Nr 3");
        plannedProject3.setProjektToken("PRO3");
        plannedProject3.setExternalDailyRate(750.0);
        plannedProject3.setHoursPerWorkingDay(8);

        PlannedProject plannedProject4 = new PlannedProject();
        plannedProject4.setActive(true);
        plannedProject4.setFakturierbar(true);
        plannedProject4.setInfo("viertes testprojekt");
        plannedProject4.setProjektName("Projekt Nr 4");
        plannedProject4.setProjektToken("PRO4");
        plannedProject4.setExternalDailyRate(750.0);
        plannedProject4.setHoursPerWorkingDay(9);

        Anrede anrede1 = new Anrede();
        anrede1.setAnrede("Herr");
        anrede1 = DaoFactorySingleton.getInstance().getAnredeDAO().createEntityFlat(anrede1);
        Anrede anrede2 = new Anrede();
        anrede2.setAnrede("Frau");
        anrede2 = DaoFactorySingleton.getInstance().getAnredeDAO().createEntityFlat(anrede2);

        Geschlecht geschlecht = new Geschlecht();
        geschlecht.setGeschlecht("männlich");
        geschlecht = DaoFactorySingleton.getInstance().getGeschlechtDAO().createEntityFlat(geschlecht);
        Geschlecht geschlecht2 = new Geschlecht();
        geschlecht2.setGeschlecht("weiblich");
        geschlecht2 = DaoFactorySingleton.getInstance().getGeschlechtDAO().createEntityFlat(geschlecht2);

        PlanningUnitElement planningUnitElement1 = new PlanningUnitElement();
        planningUnitElement1.setPlannedMinutes(0);
        planningUnitElement1.setRessourceGroup(ressourceGroup1);


        PlanningUnitElement planningUnitElement2 = new PlanningUnitElement();
        planningUnitElement2.setPlannedMinutes(0);
        planningUnitElement2.setRessourceGroup(ressourceGroup2);


        PlanningUnitElement planningUnitElement3 = new PlanningUnitElement();
        planningUnitElement3.setPlannedMinutes(0);
        planningUnitElement3.setRessourceGroup(ressourceGroup3);


        PlanningUnitElement planningUnitElement4 = new PlanningUnitElement();
        planningUnitElement4.setPlannedMinutes(0);
        planningUnitElement4.setRessourceGroup(ressourceGroup4);


        PlanningUnitElement planningUnitElement5 = new PlanningUnitElement();
        planningUnitElement5.setPlannedMinutes(0);
        planningUnitElement5.setRessourceGroup(ressourceGroup5);


        PlanningUnitElement planningUnitElement6 = new PlanningUnitElement();
        planningUnitElement6.setPlannedMinutes(0);
        planningUnitElement6.setRessourceGroup(ressourceGroup6);


        PlanningUnitElement planningUnitElement7 = new PlanningUnitElement();
        planningUnitElement7.setPlannedMinutes(0);
        planningUnitElement7.setRessourceGroup(ressourceGroup7);


        PlanningUnitElement planningUnitElement8 = new PlanningUnitElement();
        planningUnitElement8.setPlannedMinutes(0);
        planningUnitElement8.setRessourceGroup(ressourceGroup8);


        PlanningUnitElement planningUnitElement9 = new PlanningUnitElement();
        planningUnitElement9.setPlannedMinutes(0);
        planningUnitElement9.setRessourceGroup(ressourceGroup1);


        PlanningUnitElement planningUnitElement10 = new PlanningUnitElement();
        planningUnitElement10.setPlannedMinutes(0);
        planningUnitElement10.setRessourceGroup(ressourceGroup2);


        PlanningUnitElement planningUnitElement11 = new PlanningUnitElement();
        planningUnitElement11.setPlannedMinutes(0);
        planningUnitElement11.setRessourceGroup(ressourceGroup3);


        PlanningUnitElement planningUnitElement12 = new PlanningUnitElement();
        planningUnitElement12.setPlannedMinutes(0);
        planningUnitElement12.setRessourceGroup(ressourceGroup4);


        PlanningUnitElement planningUnitElement13 = new PlanningUnitElement();
        planningUnitElement13.setPlannedMinutes(0);
        planningUnitElement13.setRessourceGroup(ressourceGroup5);


        PlanningUnitElement planningUnitElement14 = new PlanningUnitElement();
        planningUnitElement14.setPlannedMinutes(0);
        planningUnitElement14.setRessourceGroup(ressourceGroup6);


        PlanningUnitElement planningUnitElement15 = new PlanningUnitElement();
        planningUnitElement15.setPlannedMinutes(0);
        planningUnitElement15.setRessourceGroup(ressourceGroup7);


        PlanningUnitElement planningUnitElement16 = new PlanningUnitElement();
        planningUnitElement16.setPlannedMinutes(0);
        planningUnitElement16.setRessourceGroup(ressourceGroup8);


        PlanningUnitElement planningUnitElement17 = new PlanningUnitElement();
        planningUnitElement17.setPlannedMinutes(0);
        planningUnitElement17.setRessourceGroup(ressourceGroup1);


        PlanningUnitElement planningUnitElement18 = new PlanningUnitElement();
        planningUnitElement18.setPlannedMinutes(0);
        planningUnitElement18.setRessourceGroup((ressourceGroup2));


        PlanningUnitElement planningUnitElement19 = new PlanningUnitElement();
        planningUnitElement19.setPlannedMinutes(0);
        planningUnitElement19.setRessourceGroup((ressourceGroup3));


        PlanningUnitElement planningUnitElement20 = new PlanningUnitElement();
        planningUnitElement20.setPlannedMinutes(0);
        planningUnitElement20.setRessourceGroup((ressourceGroup4));


        PlanningUnitElement planningUnitElement21 = new PlanningUnitElement();
        planningUnitElement21.setPlannedMinutes(0);
        planningUnitElement21.setRessourceGroup((ressourceGroup5));


        PlanningUnitElement planningUnitElement22 = new PlanningUnitElement();
        planningUnitElement22.setPlannedMinutes(0);
        planningUnitElement22.setRessourceGroup((ressourceGroup6));


        PlanningUnitElement planningUnitElement23 = new PlanningUnitElement();
        planningUnitElement23.setPlannedMinutes(0);
        planningUnitElement23.setRessourceGroup((ressourceGroup7));


        PlanningUnitElement planningUnitElement24 = new PlanningUnitElement();
        planningUnitElement24.setPlannedMinutes(0);
        planningUnitElement24.setRessourceGroup((ressourceGroup8));


        PlanningUnitElement planningUnitElement25 = new PlanningUnitElement();
        planningUnitElement25.setPlannedMinutes(0);
        planningUnitElement25.setRessourceGroup((ressourceGroup1));


        PlanningUnitElement planningUnitElement26 = new PlanningUnitElement();
        planningUnitElement26.setPlannedMinutes(0);
        planningUnitElement26.setRessourceGroup((ressourceGroup2));


        PlanningUnitElement planningUnitElement27 = new PlanningUnitElement();
        planningUnitElement27.setPlannedMinutes(0);
        planningUnitElement27.setRessourceGroup((ressourceGroup3));


        PlanningUnitElement planningUnitElement28 = new PlanningUnitElement();
        planningUnitElement28.setPlannedMinutes(0);
        planningUnitElement28.setRessourceGroup((ressourceGroup4));


        PlanningUnitElement planningUnitElement29 = new PlanningUnitElement();
        planningUnitElement29.setPlannedMinutes(0);
        planningUnitElement29.setRessourceGroup((ressourceGroup5));


        PlanningUnitElement planningUnitElement30 = new PlanningUnitElement();
        planningUnitElement30.setPlannedMinutes(0);
        planningUnitElement30.setRessourceGroup((ressourceGroup6));


        PlanningUnitElement planningUnitElement31 = new PlanningUnitElement();
        planningUnitElement31.setPlannedMinutes(0);
        planningUnitElement31.setRessourceGroup((ressourceGroup7));


        PlanningUnitElement planningUnitElement32 = new PlanningUnitElement();
        planningUnitElement32.setPlannedMinutes(0);
        planningUnitElement32.setRessourceGroup((ressourceGroup8));


        PlanningUnitElement planningUnitElement33 = new PlanningUnitElement();
        planningUnitElement33.setPlannedMinutes(0);
        planningUnitElement33.setRessourceGroup((ressourceGroup1));


        PlanningUnitElement planningUnitElement34 = new PlanningUnitElement();
        planningUnitElement34.setPlannedMinutes(0);
        planningUnitElement34.setRessourceGroup((ressourceGroup2));


        PlanningUnitElement planningUnitElement35 = new PlanningUnitElement();
        planningUnitElement35.setPlannedMinutes(0);
        planningUnitElement35.setRessourceGroup((ressourceGroup3));


        PlanningUnitElement planningUnitElement36 = new PlanningUnitElement();
        planningUnitElement36.setPlannedMinutes(0);
        planningUnitElement36.setRessourceGroup((ressourceGroup4));


        PlanningUnitElement planningUnitElement37 = new PlanningUnitElement();
        planningUnitElement37.setPlannedMinutes(0);
        planningUnitElement37.setRessourceGroup((ressourceGroup5));


        PlanningUnitElement planningUnitElement38 = new PlanningUnitElement();
        planningUnitElement38.setPlannedMinutes(0);
        planningUnitElement38.setRessourceGroup((ressourceGroup6));


        PlanningUnitElement planningUnitElement39 = new PlanningUnitElement();
        planningUnitElement39.setPlannedMinutes(0);
        planningUnitElement39.setRessourceGroup((ressourceGroup7));


        PlanningUnitElement planningUnitElement40 = new PlanningUnitElement();
        planningUnitElement40.setPlannedMinutes(0);
        planningUnitElement40.setRessourceGroup((ressourceGroup8));


        PlanningUnitElement planningUnitElement41 = new PlanningUnitElement();
        planningUnitElement41.setPlannedMinutes(0);
        planningUnitElement41.setRessourceGroup((ressourceGroup1));


        PlanningUnitElement planningUnitElement42 = new PlanningUnitElement();
        planningUnitElement42.setPlannedMinutes(0);
        planningUnitElement42.setRessourceGroup((ressourceGroup2));


        PlanningUnitElement planningUnitElement43 = new PlanningUnitElement();
        planningUnitElement43.setPlannedMinutes(0);
        planningUnitElement43.setRessourceGroup((ressourceGroup3));


        PlanningUnitElement planningUnitElement44 = new PlanningUnitElement();
        planningUnitElement44.setPlannedMinutes(0);
        planningUnitElement44.setRessourceGroup((ressourceGroup4));


        PlanningUnitElement planningUnitElement45 = new PlanningUnitElement();
        planningUnitElement45.setPlannedMinutes(0);
        planningUnitElement45.setRessourceGroup((ressourceGroup5));


        PlanningUnitElement planningUnitElement46 = new PlanningUnitElement();
        planningUnitElement46.setPlannedMinutes(0);
        planningUnitElement46.setRessourceGroup((ressourceGroup6));


        PlanningUnitElement planningUnitElement47 = new PlanningUnitElement();
        planningUnitElement47.setPlannedMinutes(0);
        planningUnitElement47.setRessourceGroup((ressourceGroup7));


        PlanningUnitElement planningUnitElement48 = new PlanningUnitElement();
        planningUnitElement48.setPlannedMinutes(0);
        planningUnitElement48.setRessourceGroup((ressourceGroup8));


        PlanningUnitElement planningUnitElement49 = new PlanningUnitElement();
        planningUnitElement49.setPlannedMinutes(0);
        planningUnitElement49.setRessourceGroup((ressourceGroup1));


        PlanningUnitElement planningUnitElement50 = new PlanningUnitElement();
        planningUnitElement50.setPlannedMinutes(0);
        planningUnitElement50.setRessourceGroup((ressourceGroup2));


        PlanningUnitElement planningUnitElement51 = new PlanningUnitElement();
        planningUnitElement51.setPlannedMinutes(0);
        planningUnitElement51.setRessourceGroup((ressourceGroup3));


        PlanningUnitElement planningUnitElement52 = new PlanningUnitElement();
        planningUnitElement52.setPlannedMinutes(0);
        planningUnitElement52.setRessourceGroup((ressourceGroup4));


        PlanningUnitElement planningUnitElement53 = new PlanningUnitElement();
        planningUnitElement53.setPlannedMinutes(0);
        planningUnitElement53.setRessourceGroup((ressourceGroup5));


        PlanningUnitElement planningUnitElement54 = new PlanningUnitElement();
        planningUnitElement54.setPlannedMinutes(0);
        planningUnitElement54.setRessourceGroup((ressourceGroup6));


        PlanningUnitElement planningUnitElement55 = new PlanningUnitElement();
        planningUnitElement55.setPlannedMinutes(0);
        planningUnitElement55.setRessourceGroup((ressourceGroup7));


        PlanningUnitElement planningUnitElement56 = new PlanningUnitElement();
        planningUnitElement56.setPlannedMinutes(0);
        planningUnitElement56.setRessourceGroup((ressourceGroup8));


        PlanningUnitElement planningUnitElement57 = new PlanningUnitElement();
        planningUnitElement57.setPlannedMinutes(0);
        planningUnitElement57.setRessourceGroup((ressourceGroup1));


        PlanningUnitElement planningUnitElement58 = new PlanningUnitElement();
        planningUnitElement58.setPlannedMinutes(0);
        planningUnitElement58.setRessourceGroup((ressourceGroup2));


        PlanningUnitElement planningUnitElement59 = new PlanningUnitElement();
        planningUnitElement59.setPlannedMinutes(0);
        planningUnitElement59.setRessourceGroup((ressourceGroup3));


        PlanningUnitElement planningUnitElement60 = new PlanningUnitElement();
        planningUnitElement60.setPlannedMinutes(0);
        planningUnitElement60.setRessourceGroup((ressourceGroup4));


        PlanningUnitElement planningUnitElement61 = new PlanningUnitElement();
        planningUnitElement61.setPlannedMinutes(0);
        planningUnitElement61.setRessourceGroup((ressourceGroup5));


        PlanningUnitElement planningUnitElement62 = new PlanningUnitElement();
        planningUnitElement62.setPlannedMinutes(0);
        planningUnitElement62.setRessourceGroup((ressourceGroup6));


        PlanningUnitElement planningUnitElement63 = new PlanningUnitElement();
        planningUnitElement63.setPlannedMinutes(0);
        planningUnitElement63.setRessourceGroup((ressourceGroup7));


        PlanningUnitElement planningUnitElement64 = new PlanningUnitElement();
        planningUnitElement64.setPlannedMinutes(0);
        planningUnitElement64.setRessourceGroup((ressourceGroup8));


        PlanningUnitElement planningUnitElement65 = new PlanningUnitElement();
        planningUnitElement65.setPlannedMinutes(0);
        planningUnitElement65.setRessourceGroup((ressourceGroup1));


        PlanningUnitElement planningUnitElement66 = new PlanningUnitElement();
        planningUnitElement66.setPlannedMinutes(0);
        planningUnitElement66.setRessourceGroup((ressourceGroup2));


        PlanningUnitElement planningUnitElement67 = new PlanningUnitElement();
        planningUnitElement67.setPlannedMinutes(0);
        planningUnitElement67.setRessourceGroup((ressourceGroup3));


        PlanningUnitElement planningUnitElement68 = new PlanningUnitElement();
        planningUnitElement68.setPlannedMinutes(0);
        planningUnitElement68.setRessourceGroup((ressourceGroup4));


        PlanningUnitElement planningUnitElement69 = new PlanningUnitElement();
        planningUnitElement69.setPlannedMinutes(0);
        planningUnitElement69.setRessourceGroup((ressourceGroup5));


        PlanningUnitElement planningUnitElement70 = new PlanningUnitElement();
        planningUnitElement70.setPlannedMinutes(0);
        planningUnitElement70.setRessourceGroup((ressourceGroup6));


        PlanningUnitElement planningUnitElement71 = new PlanningUnitElement();
        planningUnitElement71.setPlannedMinutes(0);
        planningUnitElement71.setRessourceGroup((ressourceGroup7));


        PlanningUnitElement planningUnitElement72 = new PlanningUnitElement();
        planningUnitElement72.setPlannedMinutes(0);
        planningUnitElement72.setRessourceGroup((ressourceGroup8));


        PlanningUnitElement planningUnitElement73 = new PlanningUnitElement();
        planningUnitElement73.setPlannedMinutes(0);
        planningUnitElement73.setRessourceGroup((ressourceGroup1));


        PlanningUnitElement planningUnitElement74 = new PlanningUnitElement();
        planningUnitElement74.setPlannedMinutes(0);
        planningUnitElement74.setRessourceGroup((ressourceGroup2));


        PlanningUnitElement planningUnitElement75 = new PlanningUnitElement();
        planningUnitElement75.setPlannedMinutes(0);
        planningUnitElement75.setRessourceGroup((ressourceGroup3));


        PlanningUnitElement planningUnitElement76 = new PlanningUnitElement();
        planningUnitElement76.setPlannedMinutes(0);
        planningUnitElement76.setRessourceGroup((ressourceGroup4));


        PlanningUnitElement planningUnitElement77 = new PlanningUnitElement();
        planningUnitElement77.setPlannedMinutes(0);
        planningUnitElement77.setRessourceGroup((ressourceGroup5));


        PlanningUnitElement planningUnitElement78 = new PlanningUnitElement();
        planningUnitElement78.setPlannedMinutes(0);
        planningUnitElement78.setRessourceGroup((ressourceGroup6));


        PlanningUnitElement planningUnitElement79 = new PlanningUnitElement();
        planningUnitElement79.setPlannedMinutes(0);
        planningUnitElement79.setRessourceGroup((ressourceGroup7));


        PlanningUnitElement planningUnitElement80 = new PlanningUnitElement();
        planningUnitElement80.setPlannedMinutes(0);
        planningUnitElement80.setRessourceGroup((ressourceGroup8));


        PlanningUnitElement planningUnitElement81 = new PlanningUnitElement();
        planningUnitElement81.setPlannedMinutes(0);
        planningUnitElement81.setRessourceGroup((ressourceGroup1));


        PlanningUnitElement planningUnitElement82 = new PlanningUnitElement();
        planningUnitElement82.setPlannedMinutes(0);
        planningUnitElement82.setRessourceGroup((ressourceGroup2));


        PlanningUnitElement planningUnitElement83 = new PlanningUnitElement();
        planningUnitElement83.setPlannedMinutes(0);
        planningUnitElement83.setRessourceGroup((ressourceGroup3));


        PlanningUnitElement planningUnitElement84 = new PlanningUnitElement();
        planningUnitElement84.setPlannedMinutes(0);
        planningUnitElement84.setRessourceGroup((ressourceGroup4));


        PlanningUnitElement planningUnitElement85 = new PlanningUnitElement();
        planningUnitElement85.setPlannedMinutes(0);
        planningUnitElement85.setRessourceGroup((ressourceGroup5));


        PlanningUnitElement planningUnitElement86 = new PlanningUnitElement();
        planningUnitElement86.setPlannedMinutes(0);
        planningUnitElement86.setRessourceGroup((ressourceGroup6));


        PlanningUnitElement planningUnitElement87 = new PlanningUnitElement();
        planningUnitElement87.setPlannedMinutes(0);
        planningUnitElement87.setRessourceGroup((ressourceGroup7));


        PlanningUnitElement planningUnitElement88 = new PlanningUnitElement();
        planningUnitElement88.setPlannedMinutes(0);
        planningUnitElement88.setRessourceGroup((ressourceGroup8));


        PlanningUnitElement planningUnitElement89 = new PlanningUnitElement();
        planningUnitElement89.setPlannedMinutes(0);
        planningUnitElement89.setRessourceGroup((ressourceGroup1));


        PlanningUnitElement planningUnitElement90 = new PlanningUnitElement();
        planningUnitElement90.setPlannedMinutes(0);
        planningUnitElement90.setRessourceGroup((ressourceGroup2));


        PlanningUnitElement planningUnitElement91 = new PlanningUnitElement();
        planningUnitElement91.setPlannedMinutes(0);
        planningUnitElement91.setRessourceGroup((ressourceGroup3));


        PlanningUnitElement planningUnitElement92 = new PlanningUnitElement();
        planningUnitElement92.setPlannedMinutes(0);
        planningUnitElement92.setRessourceGroup((ressourceGroup4));


        PlanningUnitElement planningUnitElement93 = new PlanningUnitElement();
        planningUnitElement93.setPlannedMinutes(0);
        planningUnitElement93.setRessourceGroup((ressourceGroup5));


        PlanningUnitElement planningUnitElement94 = new PlanningUnitElement();
        planningUnitElement94.setPlannedMinutes(0);
        planningUnitElement94.setRessourceGroup((ressourceGroup6));


        PlanningUnitElement planningUnitElement95 = new PlanningUnitElement();
        planningUnitElement95.setPlannedMinutes(0);
        planningUnitElement95.setRessourceGroup((ressourceGroup7));


        PlanningUnitElement planningUnitElement96 = new PlanningUnitElement();
        planningUnitElement96.setPlannedMinutes(0);
        planningUnitElement96.setRessourceGroup((ressourceGroup8));


        PlanningUnitElement planningUnitElement97 = new PlanningUnitElement();
        planningUnitElement97.setPlannedMinutes(0);
        planningUnitElement97.setRessourceGroup((ressourceGroup1));


        PlanningUnitElement planningUnitElement98 = new PlanningUnitElement();
        planningUnitElement98.setPlannedMinutes(0);
        planningUnitElement98.setRessourceGroup((ressourceGroup2));


        PlanningUnitElement planningUnitElement99 = new PlanningUnitElement();
        planningUnitElement99.setPlannedMinutes(0);
        planningUnitElement99.setRessourceGroup((ressourceGroup3));


        PlanningUnitElement planningUnitElement100 = new PlanningUnitElement();
        planningUnitElement100.setPlannedMinutes(0);
        planningUnitElement100.setRessourceGroup((ressourceGroup4));


        PlanningUnitElement planningUnitElement101 = new PlanningUnitElement();
        planningUnitElement101.setPlannedMinutes(0);
        planningUnitElement101.setRessourceGroup((ressourceGroup5));


        PlanningUnitElement planningUnitElement102 = new PlanningUnitElement();
        planningUnitElement102.setPlannedMinutes(0);
        planningUnitElement102.setRessourceGroup((ressourceGroup6));


        PlanningUnitElement planningUnitElement103 = new PlanningUnitElement();
        planningUnitElement103.setPlannedMinutes(0);
        planningUnitElement103.setRessourceGroup((ressourceGroup7));


        PlanningUnitElement planningUnitElement104 = new PlanningUnitElement();
        planningUnitElement104.setPlannedMinutes(0);
        planningUnitElement104.setRessourceGroup((ressourceGroup8));


        PlanningUnitElement planningUnitElement105 = new PlanningUnitElement();
        planningUnitElement105.setPlannedMinutes(0);
        planningUnitElement105.setRessourceGroup((ressourceGroup1));


        PlanningUnitElement planningUnitElement106 = new PlanningUnitElement();
        planningUnitElement106.setPlannedMinutes(0);
        planningUnitElement106.setRessourceGroup((ressourceGroup2));


        PlanningUnitElement planningUnitElement107 = new PlanningUnitElement();
        planningUnitElement107.setPlannedMinutes(0);
        planningUnitElement107.setRessourceGroup((ressourceGroup3));


        PlanningUnitElement planningUnitElement108 = new PlanningUnitElement();
        planningUnitElement108.setPlannedMinutes(0);
        planningUnitElement108.setRessourceGroup((ressourceGroup4));


        PlanningUnitElement planningUnitElement109 = new PlanningUnitElement();
        planningUnitElement109.setPlannedMinutes(0);
        planningUnitElement109.setRessourceGroup((ressourceGroup5));


        PlanningUnitElement planningUnitElement110 = new PlanningUnitElement();
        planningUnitElement110.setPlannedMinutes(0);
        planningUnitElement110.setRessourceGroup((ressourceGroup6));


        PlanningUnitElement planningUnitElement111 = new PlanningUnitElement();
        planningUnitElement111.setPlannedMinutes(0);
        planningUnitElement111.setRessourceGroup((ressourceGroup7));


        PlanningUnitElement planningUnitElement112 = new PlanningUnitElement();
        planningUnitElement112.setPlannedMinutes(0);
        planningUnitElement112.setRessourceGroup((ressourceGroup8));


        TextElement description1 = new TextElement();
        description1.setBezeichnung("erste beschreibung");
        description1.setText("Dies hier stellt die erste beschreibung dar.");

        TextElement description2 = new TextElement();
        description2.setBezeichnung("die zweite beschreibung");
        description2.setText("Dies hier stellt die zweite tolle beschreibung dar.");

        TextElement description3 = new TextElement();
        description3.setBezeichnung("dritte beschreibung");
        description3.setText("Dies hier stellt die dritte beschreibung dar..");

        TextElement description4 = new TextElement();
        description4.setBezeichnung("die vierte beschreibung");
        description4.setText("Dies hier stellt die vierte tolle beschreibung dar.");

        TextElement testcase1 = new TextElement();
        testcase1.setBezeichnung("der 1. testcase");
        testcase1.setText("Dies hier stellt den ersten tollen Testcase dar.");

        TextElement testcase2 = new TextElement();
        testcase2.setBezeichnung("der 2. tolle testcase");
        testcase2.setText("Dies hier stellt den zweiten tollen Testcase dar.");

        PlanningUnit planningUnit1 = new PlanningUnit();
        planningUnit1.setOrderNumber(1);
        planningUnit1.setPlanningUnitName("Vorbereitungen");
        planningUnit1.setKomplexitaet(1);
        planningUnit1.setPlanningUnitElementList(Arrays.asList(planningUnitElement1, planningUnitElement2, planningUnitElement3, planningUnitElement4, planningUnitElement5, planningUnitElement6, planningUnitElement7, planningUnitElement8));
        planningUnit1.setDescriptions(Arrays.asList(description1, description2, description3, description4));
        planningUnit1.setTestcases(Arrays.asList(testcase1, testcase2));
        planningUnit1.setEstimatedStoryPoints(1);

        PlanningUnit planningUnit2 = new PlanningUnit();
        planningUnit2.setOrderNumber(2);
        planningUnit2.setPlanningUnitName("Erstkontakt vor Ort");
        planningUnit2.setKomplexitaet(1);
        planningUnit2.setEstimatedStoryPoints(2);
        planningUnit2.setPlanningUnitElementList(Arrays.asList(planningUnitElement9, planningUnitElement10, planningUnitElement11, planningUnitElement12, planningUnitElement13, planningUnitElement14, planningUnitElement15, planningUnitElement16));

        PlanningUnit planningUnit3 = new PlanningUnit();
        planningUnit3.setOrderNumber(3);
        planningUnit3.setPlanningUnitName("Person A kontaktieren");
        planningUnit3.setKomplexitaet(1);
        planningUnit3.setEstimatedStoryPoints(3);
        planningUnit3.setPlanningUnitElementList(Arrays.asList(planningUnitElement17, planningUnitElement18, planningUnitElement19, planningUnitElement20, planningUnitElement21, planningUnitElement22, planningUnitElement23, planningUnitElement24));

        PlanningUnit planningUnit4 = new PlanningUnit();
        planningUnit4.setOrderNumber(4);
        planningUnit4.setPlanningUnitName("Person B kontaktieren");
        planningUnit4.setKomplexitaet(1);
        planningUnit4.setEstimatedStoryPoints(4);
        planningUnit4.setPlanningUnitElementList(Arrays.asList(planningUnitElement25, planningUnitElement26, planningUnitElement27, planningUnitElement28, planningUnitElement29, planningUnitElement30, planningUnitElement31, planningUnitElement32));

        PlanningUnit planningUnit5 = new PlanningUnit();
        planningUnit5.setOrderNumber(5);
        planningUnit5.setPlanningUnitName("Gesprächsvorbereitung");
        planningUnit5.setKomplexitaet(1);
        planningUnit5.setEstimatedStoryPoints(5);
        planningUnit5.setPlanningUnitElementList(Arrays.asList(planningUnitElement33, planningUnitElement34, planningUnitElement35, planningUnitElement36, planningUnitElement37, planningUnitElement38, planningUnitElement39, planningUnitElement40));

        PlanningUnit planningUnit6 = new PlanningUnit();
        planningUnit6.setOrderNumber(6);
        planningUnit6.setPlanningUnitName("Präsentation");
        planningUnit6.setKomplexitaet(1);
        planningUnit6.setEstimatedStoryPoints(6);
        planningUnit6.setPlanningUnitElementList(Arrays.asList(planningUnitElement41, planningUnitElement42, planningUnitElement43, planningUnitElement44, planningUnitElement45, planningUnitElement46, planningUnitElement47, planningUnitElement48));

        PlanningUnit planningUnit7 = new PlanningUnit();
        planningUnit7.setOrderNumber(7);
        planningUnit7.setPlanningUnitName("Gesprächsbestätigung");
        planningUnit7.setKomplexitaet(1);
        planningUnit7.setEstimatedStoryPoints(7);
        planningUnit7.setPlanningUnitElementList(Arrays.asList(planningUnitElement49, planningUnitElement50, planningUnitElement51, planningUnitElement52, planningUnitElement53, planningUnitElement54, planningUnitElement55, planningUnitElement56));

        PlanningUnit planningUnit8 = new PlanningUnit();
        planningUnit8.setOrderNumber(8);
        planningUnit8.setPlanningUnitName("Vorbereitungsarbeiten");
        planningUnit8.setKomplexitaet(1);
        planningUnit8.setEstimatedStoryPoints(8);
        planningUnit8.setPlanningUnitElementList(Arrays.asList(planningUnitElement57, planningUnitElement58, planningUnitElement59, planningUnitElement60, planningUnitElement61, planningUnitElement62, planningUnitElement63, planningUnitElement64));

        PlanningUnit planningUnit9 = new PlanningUnit();
        planningUnit9.setOrderNumber(9);
        planningUnit9.setPlanningUnitName("Vorbereitung der Maschinen");
        planningUnit9.setKomplexitaet(1);
        planningUnit9.setEstimatedStoryPoints(9);
        planningUnit9.setPlanningUnitElementList(Arrays.asList(planningUnitElement65, planningUnitElement66, planningUnitElement67, planningUnitElement68, planningUnitElement69, planningUnitElement70, planningUnitElement71, planningUnitElement72));

        PlanningUnit planningUnit10 = new PlanningUnit();
        planningUnit10.setOrderNumber(10);
        planningUnit10.setPlanningUnitName("Vorbereitung von Maschine 1");
        planningUnit10.setKomplexitaet(1);
        planningUnit10.setEstimatedStoryPoints(10);
        planningUnit10.setPlanningUnitElementList(Arrays.asList(planningUnitElement73, planningUnitElement74, planningUnitElement75, planningUnitElement76, planningUnitElement77, planningUnitElement78, planningUnitElement79, planningUnitElement80));

        PlanningUnit planningUnit11 = new PlanningUnit();
        planningUnit11.setOrderNumber(11);
        planningUnit11.setPlanningUnitName("Vorbereitung von Maschine 2");
        planningUnit11.setKomplexitaet(1);
        planningUnit11.setEstimatedStoryPoints(11);
        planningUnit11.setPlanningUnitElementList(Arrays.asList(planningUnitElement81, planningUnitElement82, planningUnitElement83, planningUnitElement84, planningUnitElement85, planningUnitElement86, planningUnitElement87, planningUnitElement88));

        PlanningUnit planningUnit12 = new PlanningUnit();
        planningUnit12.setOrderNumber(12);
        planningUnit12.setPlanningUnitName("Vorbereitung der Werkzeuge");
        planningUnit12.setKomplexitaet(1);
        planningUnit12.setEstimatedStoryPoints(12);
        planningUnit12.setPlanningUnitElementList(Arrays.asList(planningUnitElement89, planningUnitElement90, planningUnitElement91, planningUnitElement92, planningUnitElement93, planningUnitElement94, planningUnitElement95, planningUnitElement96));

        PlanningUnit planningUnit13 = new PlanningUnit();
        planningUnit13.setOrderNumber(13);
        planningUnit13.setPlanningUnitName("Durchführung");
        planningUnit13.setKomplexitaet(1);
        planningUnit13.setEstimatedStoryPoints(13);
        planningUnit13.setPlanningUnitElementList(Arrays.asList(planningUnitElement97, planningUnitElement98, planningUnitElement99, planningUnitElement100, planningUnitElement101, planningUnitElement102, planningUnitElement103, planningUnitElement104));

        PlanningUnit planningUnit14 = new PlanningUnit();
        planningUnit14.setOrderNumber(14);
        planningUnit14.setPlanningUnitName("Beendigung");
        planningUnit14.setKomplexitaet(1);
        planningUnit14.setEstimatedStoryPoints(14);
        planningUnit14.setPlanningUnitElementList(Arrays.asList(planningUnitElement105, planningUnitElement106, planningUnitElement107, planningUnitElement108, planningUnitElement109, planningUnitElement110, planningUnitElement111, planningUnitElement112));

        planningUnit1.setKindPlanningUnits(Arrays.asList(planningUnit2, planningUnit5, planningUnit6, planningUnit7));
        planningUnit2.setKindPlanningUnits(Arrays.asList(planningUnit3, planningUnit4));
        planningUnit8.setKindPlanningUnits(Arrays.asList(planningUnit9, planningUnit12));
        planningUnit9.setKindPlanningUnits(Arrays.asList(planningUnit10, planningUnit11));

        planningUnit2.setParent(planningUnit1);
        planningUnit5.setParent(planningUnit1);
        planningUnit6.setParent(planningUnit1);
        planningUnit7.setParent(planningUnit1);
        planningUnit3.setParent(planningUnit2);
        planningUnit4.setParent(planningUnit2);
        planningUnit9.setParent(planningUnit8);
        planningUnit12.setParent(planningUnit8);
        planningUnit10.setParent(planningUnit9);
        planningUnit11.setParent(planningUnit9);

//        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit1, planningUnit2);
//        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit1, planningUnit5);
//        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit1, planningUnit6);
//        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit1, planningUnit7);
//        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit2, planningUnit3);
//        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit2, planningUnit4);
//        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit8, planningUnit9);
//        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit8, planningUnit12);
//        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit9, planningUnit10);
//        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit9, planningUnit11);

//        planningUnit1 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntityFlat(planningUnit1);
//        planningUnit2 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntityFlat(planningUnit2);
//        planningUnit3 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntityFlat(planningUnit3);
//        planningUnit4 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntityFlat(planningUnit4);
//        planningUnit5 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntityFlat(planningUnit5);
//        planningUnit6 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntityFlat(planningUnit6);
//        planningUnit7 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntityFlat(planningUnit7);
//        planningUnit8 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntityFlat(planningUnit8);
//        planningUnit9 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntityFlat(planningUnit9);
//        planningUnit10 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntityFlat(planningUnit10);
//        planningUnit11 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntityFlat(planningUnit11);
//        planningUnit12 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntityFlat(planningUnit12);
//        planningUnit13 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntityFlat(planningUnit13);
//        planningUnit14 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntityFlat(planningUnit14);
//
//        DaoFactorySingleton.getInstance().getPlannedProjectDAO().addPlanningUnitToProject(planningUnit1, plannedProject1);
//        DaoFactorySingleton.getInstance().getPlannedProjectDAO().addPlanningUnitToProject(planningUnit8, plannedProject1);
//        DaoFactorySingleton.getInstance().getPlannedProjectDAO().addPlanningUnitToProject(planningUnit13, plannedProject1);
//        DaoFactorySingleton.getInstance().getPlannedProjectDAO().addPlanningUnitToProject(planningUnit14, plannedProject1);
//
//        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit1, planningUnit2);
//        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit1, planningUnit5);
//        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit1, planningUnit6);
//        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit1, planningUnit7);
//        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit2, planningUnit3);
//        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit2, planningUnit4);
//        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit8, planningUnit9);
//        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit8, planningUnit12);
//        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit9, planningUnit10);
//        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit9, planningUnit11);

        plannedProject1.setPlanningUnits(Arrays.asList(planningUnit1, planningUnit8, planningUnit13, planningUnit14));
        plannedProject1 = DaoFactorySingleton.getInstance().getPlannedProjectDAO().createEntityFull(plannedProject1);
//        plannedProject2 = DaoFactorySingleton.getInstance().getPlannedProjectDAO().createEntityFlat(plannedProject2);
//        plannedProject3 = DaoFactorySingleton.getInstance().getPlannedProjectDAO().createEntityFlat(plannedProject3);
//        plannedProject4 = DaoFactorySingleton.getInstance().getPlannedProjectDAO().createEntityFlat(plannedProject4);

        final List<PlanningUnit> allPlanningUnits = DaoFactorySingleton.getInstance().getPlanningUnitDAO().findAll();
        for (PlanningUnit planningUnit : allPlanningUnits) {
            DaoFactorySingleton.getInstance().getPlanningUnitDAO().addResponsiblePersonToPlanningUnit(benutzer1, planningUnit);
        }
    }
}
