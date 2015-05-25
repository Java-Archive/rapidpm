package org.rapidpm.persistence;

import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
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

import java.util.Arrays;

/**
 * Created by Marco on 28.04.2015.
 */
public class StartDataCreator {
    
    public static void run(){

        Mandantengruppe mandantengruppe = new Mandantengruppe();
        mandantengruppe.setMandantengruppe("RapidPM");
        //db.save(mandantengruppe);
        mandantengruppe = DaoFactorySingleton.getInstance().getMandantengruppeDAO().createEntity(mandantengruppe);

        BenutzerGruppe benutzerGruppe1 = new BenutzerGruppe();
        benutzerGruppe1.setGruppenname("User");
        //db.save(benutzerGruppe1);
        benutzerGruppe1 = DaoFactorySingleton.getInstance().getBenutzerGruppeDAO().createEntity(benutzerGruppe1);

        BenutzerGruppe benutzerGruppe2 = new BenutzerGruppe();
        benutzerGruppe2.setGruppenname("'Anonymous");
        //db.save(benutzerGruppe2);
        benutzerGruppe2 = DaoFactorySingleton.getInstance().getBenutzerGruppeDAO().createEntity(benutzerGruppe2);

        BenutzerGruppe benutzerGruppe3 = new BenutzerGruppe();
        benutzerGruppe3.setGruppenname("'Admin");
        //db.save(benutzerGruppe3);
        benutzerGruppe3 = DaoFactorySingleton.getInstance().getBenutzerGruppeDAO().createEntity(benutzerGruppe3);


        BenutzerWebapplikation benutzerWebapplikation = new BenutzerWebapplikation();
        benutzerWebapplikation.setWebappName("RapidPM");
        //db.save(benutzerWebapplikation);
        BenutzerWebapplikation benutzerWebapplikation1 = new BenutzerWebapplikation();
        benutzerWebapplikation1.setWebappName("'RapidPMBeta'");
        //db.save(benutzerWebapplikation1);
        benutzerWebapplikation = DaoFactorySingleton.getInstance().getBenutzerWebapplikationDAO().createEntity(benutzerWebapplikation);
        benutzerWebapplikation1 = DaoFactorySingleton.getInstance().getBenutzerWebapplikationDAO().createEntity(benutzerWebapplikation1);


        Benutzer benutzer1 = new Benutzer();
        benutzer1.setActive(true);
        benutzer1.setEmail("nobody@rapidpm.org");
        benutzer1.setFailedLogins(0);
        benutzer1.setHidden(false);
        benutzer1.setLogin("<not assigned>");
        benutzer1.setPasswd("geheim");
        //db.save(benutzer1);
        benutzer1 = DaoFactorySingleton.getInstance().getBenutzerDAO().createEntity(benutzer1);

        Benutzer benutzer2 = new Benutzer();
        benutzer2.setActive(true);
        benutzer2.setEmail("sven.ruppert@rapidpm.org");
        benutzer2.setFailedLogins(0);
        benutzer2.setHidden(false);
        benutzer2.setLogin("sven.ruppert");
        benutzer2.setPasswd("geheim");
        //db.save(benutzer2);
        benutzer2 = DaoFactorySingleton.getInstance().getBenutzerDAO().createEntity(benutzer2);

        Benutzer benutzer3 = new Benutzer();
        benutzer3.setActive(true);
        benutzer3.setEmail("marco.ebbinghaus@rapidpm.org");
        benutzer3.setFailedLogins(0);
        benutzer3.setHidden(false);
        benutzer3.setLogin("marco.ebbinghaus");
        benutzer3.setPasswd("geheim");
        //db.save(benutzer3);
        benutzer3 = DaoFactorySingleton.getInstance().getBenutzerDAO().createEntity(benutzer3);

        RessourceGroup ressourceGroup1 = new RessourceGroup();
        ressourceGroup1.setName("GF");
        ressourceGroup1.setHoursPerWeek(40);
        ressourceGroup1.setWeeksPerYear(50);
        ressourceGroup1.setPlanAnzahl(1);
        ressourceGroup1.setFacturizable(0.5);
        ressourceGroup1.setExternalEurosPerHour(125.0);
        ressourceGroup1.setBruttoGehalt(200000.0);
        //db.save(ressourceGroup1);
        ressourceGroup1 = DaoFactorySingleton.getInstance().getRessourceGroupDAO().createEntity(ressourceGroup1);

        RessourceGroup ressourceGroup2 = new RessourceGroup();
        ressourceGroup2.setName("Multiprojekt Manager");
        ressourceGroup2.setHoursPerWeek(40);
        ressourceGroup2.setWeeksPerYear(46);
        ressourceGroup2.setPlanAnzahl(1);
        ressourceGroup2.setFacturizable(0.5);
        ressourceGroup2.setExternalEurosPerHour(125.0);
        ressourceGroup2.setBruttoGehalt(83000.0);
        //db.save(ressourceGroup2);
        ressourceGroup2 = DaoFactorySingleton.getInstance().getRessourceGroupDAO().createEntity(ressourceGroup2);

        RessourceGroup ressourceGroup3 = new RessourceGroup();
        ressourceGroup3.setName("Projektleiter / Scrum Master");
        ressourceGroup3.setHoursPerWeek(40);
        ressourceGroup3.setWeeksPerYear(46);
        ressourceGroup3.setPlanAnzahl(1);
        ressourceGroup3.setFacturizable(0.75);
        ressourceGroup3.setExternalEurosPerHour(75.0);
        ressourceGroup3.setBruttoGehalt(72000.0);
        //db.save(ressourceGroup3);
        ressourceGroup3 = DaoFactorySingleton.getInstance().getRessourceGroupDAO().createEntity(ressourceGroup3);

        RessourceGroup ressourceGroup4 = new RessourceGroup();
        ressourceGroup4.setName("Senior Projektmitarbeiter");
        ressourceGroup4.setHoursPerWeek(40);
        ressourceGroup4.setWeeksPerYear(46);
        ressourceGroup4.setPlanAnzahl(1);
        ressourceGroup4.setFacturizable(0.75);
        ressourceGroup4.setExternalEurosPerHour(65.0);
        ressourceGroup4.setBruttoGehalt(72000.0);
        //db.save(ressourceGroup4);
        ressourceGroup4 = DaoFactorySingleton.getInstance().getRessourceGroupDAO().createEntity(ressourceGroup4);

        RessourceGroup ressourceGroup5 = new RessourceGroup();
        ressourceGroup5.setName("Senior Projektmitarbeiter");
        ressourceGroup5.setHoursPerWeek(40);
        ressourceGroup5.setWeeksPerYear(46);
        ressourceGroup5.setPlanAnzahl(1);
        ressourceGroup5.setFacturizable(0.75);
        ressourceGroup5.setExternalEurosPerHour(45.0);
        ressourceGroup5.setBruttoGehalt(45000.0);
        //db.save(ressourceGroup5);
        ressourceGroup5 = DaoFactorySingleton.getInstance().getRessourceGroupDAO().createEntity(ressourceGroup5);

        RessourceGroup ressourceGroup6 = new RessourceGroup();
        ressourceGroup6.setName("Aushilfe / stud. Projektmitarbeiter");
        ressourceGroup6.setHoursPerWeek(40);
        ressourceGroup6.setWeeksPerYear(46);
        ressourceGroup6.setPlanAnzahl(1);
        ressourceGroup6.setFacturizable(0.75);
        ressourceGroup6.setExternalEurosPerHour(25.0);
        ressourceGroup6.setBruttoGehalt(17000.0);
        //db.save(ressourceGroup6);
        ressourceGroup6 = DaoFactorySingleton.getInstance().getRessourceGroupDAO().createEntity(ressourceGroup6);

        RessourceGroup ressourceGroup7 = new RessourceGroup();
        ressourceGroup7.setName("externe Ressource");
        ressourceGroup7.setHoursPerWeek(40);
        ressourceGroup7.setWeeksPerYear(46);
        ressourceGroup7.setPlanAnzahl(1);
        ressourceGroup7.setFacturizable(0.8);
        ressourceGroup7.setExternalEurosPerHour(90.0);
        ressourceGroup7.setBruttoGehalt(125000.0);
        //db.save(ressourceGroup7);
        ressourceGroup7 = DaoFactorySingleton.getInstance().getRessourceGroupDAO().createEntity(ressourceGroup7);

        RessourceGroup ressourceGroup8 = new RessourceGroup();
        ressourceGroup8.setName("Backoffice");
        ressourceGroup8.setHoursPerWeek(20);
        ressourceGroup8.setWeeksPerYear(46);
        ressourceGroup8.setPlanAnzahl(1);
        ressourceGroup8.setFacturizable(0.01);
        ressourceGroup8.setExternalEurosPerHour(0.0);
        ressourceGroup8.setBruttoGehalt(62000.0);
        //db.save(ressourceGroup8);
        ressourceGroup8 = DaoFactorySingleton.getInstance().getRessourceGroupDAO().createEntity(ressourceGroup8);

        PlannedProject plannedProject1 = new PlannedProject();
        plannedProject1.setActive(true);
        plannedProject1.setFakturierbar(true);
        plannedProject1.setInfo("erstes testprojekt");
        plannedProject1.setProjektName("Projekt Nr 1");
        plannedProject1.setProjektToken("PRO1");
        plannedProject1.setExternalDailyRate(750.0);
        plannedProject1.setHoursPerWorkingDay(8);
        //db.save(plannedProject1);
        plannedProject1 = DaoFactorySingleton.getInstance().getPlannedProjectDAO().createEntity(plannedProject1);

        PlannedProject plannedProject2 = new PlannedProject();
        plannedProject2.setActive(true);
        plannedProject2.setFakturierbar(true);
        plannedProject2.setInfo("zweites testprojekt");
        plannedProject2.setProjektName("Projekt Nr 2");
        plannedProject2.setProjektToken("PRO2");
        plannedProject2.setExternalDailyRate(750.0);
        plannedProject2.setHoursPerWorkingDay(9);
        //db.save(plannedProject2);
        plannedProject2 = DaoFactorySingleton.getInstance().getPlannedProjectDAO().createEntity(plannedProject2);

        PlannedProject plannedProject3 = new PlannedProject();
        plannedProject3.setActive(true);
        plannedProject3.setFakturierbar(true);
        plannedProject3.setInfo("drittes testprojekt");
        plannedProject3.setProjektName("Projekt Nr 3");
        plannedProject3.setProjektToken("PRO3");
        plannedProject3.setExternalDailyRate(750.0);
        plannedProject3.setHoursPerWorkingDay(8);
        //db.save(plannedProject3);
        plannedProject3 = DaoFactorySingleton.getInstance().getPlannedProjectDAO().createEntity(plannedProject3);

        PlannedProject plannedProject4 = new PlannedProject();
        plannedProject4.setActive(true);
        plannedProject4.setFakturierbar(true);
        plannedProject4.setInfo("viertes testprojekt");
        plannedProject4.setProjektName("Projekt Nr 4");
        plannedProject4.setProjektToken("PRO4");
        plannedProject4.setExternalDailyRate(750.0);
        plannedProject4.setHoursPerWorkingDay(9);
        //db.save(plannedProject4);
        plannedProject4 = DaoFactorySingleton.getInstance().getPlannedProjectDAO().createEntity(plannedProject4);

        Anrede anrede1 = new Anrede();
        anrede1.setAnrede("Herr");
        //db.save(anrede1);
        anrede1 = DaoFactorySingleton.getInstance().getAnredeDAO().createEntity(anrede1);
        Anrede anrede2 = new Anrede();
        anrede2.setAnrede("Frau");
        //db.save(anrede2);
        anrede2 = DaoFactorySingleton.getInstance().getAnredeDAO().createEntity(anrede2);

        Geschlecht geschlecht = new Geschlecht();
        geschlecht.setGeschlecht("männlich");
        //db.save(geschlecht);
        geschlecht = DaoFactorySingleton.getInstance().getGeschlechtDAO().createEntity(geschlecht);
        Geschlecht geschlecht2 = new Geschlecht();
        geschlecht2.setGeschlecht("weiblich");
        //db.save(geschlecht2);
        geschlecht2 = DaoFactorySingleton.getInstance().getGeschlechtDAO().createEntity(geschlecht2);
        //db.sa
        //db.commit();

        PlanningUnitElement planningUnitElement1 = new PlanningUnitElement();
        planningUnitElement1.setPlannedMinutes(300);
        planningUnitElement1.setRessourceGroup(ressourceGroup1);
        planningUnitElement1 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement1);

        PlanningUnitElement planningUnitElement2 = new PlanningUnitElement();
        planningUnitElement2.setPlannedMinutes(400);
        planningUnitElement2.setRessourceGroup(ressourceGroup2);
        planningUnitElement2 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement2);

        PlanningUnitElement planningUnitElement3 = new PlanningUnitElement();
        planningUnitElement3.setPlannedMinutes(200);
        planningUnitElement3.setRessourceGroup(ressourceGroup3);
        planningUnitElement3 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement3);

        PlanningUnitElement planningUnitElement4 = new PlanningUnitElement();
        planningUnitElement4.setPlannedMinutes(150);
        planningUnitElement4.setRessourceGroup(ressourceGroup4);
        planningUnitElement4 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement4);

        PlanningUnitElement planningUnitElement5 = new PlanningUnitElement();
        planningUnitElement5.setPlannedMinutes(800);
        planningUnitElement5.setRessourceGroup(ressourceGroup5);
        planningUnitElement5 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement5);

        PlanningUnitElement planningUnitElement6 = new PlanningUnitElement();
        planningUnitElement6.setPlannedMinutes(340);
        planningUnitElement6.setRessourceGroup(ressourceGroup6);
        planningUnitElement6 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement6);

        PlanningUnitElement planningUnitElement7 = new PlanningUnitElement();
        planningUnitElement7.setPlannedMinutes(220);
        planningUnitElement7.setRessourceGroup(ressourceGroup7);
        planningUnitElement7 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement7);

        PlanningUnitElement planningUnitElement8 = new PlanningUnitElement();
        planningUnitElement8.setPlannedMinutes(800);
        planningUnitElement8.setRessourceGroup(ressourceGroup8);
        planningUnitElement8 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement8);

        PlanningUnitElement planningUnitElement9 = new PlanningUnitElement();
        planningUnitElement9.setPlannedMinutes(320);
        planningUnitElement9.setRessourceGroup(ressourceGroup1);
        planningUnitElement9 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement9);

        PlanningUnitElement planningUnitElement10 = new PlanningUnitElement();
        planningUnitElement10.setPlannedMinutes(150);
        planningUnitElement10.setRessourceGroup(ressourceGroup2);
        planningUnitElement10 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement10);

        PlanningUnitElement planningUnitElement11 = new PlanningUnitElement();
        planningUnitElement11.setPlannedMinutes(100);
        planningUnitElement11.setRessourceGroup(ressourceGroup3);
        planningUnitElement11 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement11);

        PlanningUnitElement planningUnitElement12 = new PlanningUnitElement();
        planningUnitElement12.setPlannedMinutes(300);
        planningUnitElement12.setRessourceGroup(ressourceGroup4);
        planningUnitElement12 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement12);

        PlanningUnitElement planningUnitElement13 = new PlanningUnitElement();
        planningUnitElement13.setPlannedMinutes(250);
        planningUnitElement13.setRessourceGroup(ressourceGroup5);
        planningUnitElement13 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement13);

        PlanningUnitElement planningUnitElement14 = new PlanningUnitElement();
        planningUnitElement14.setPlannedMinutes(730);
        planningUnitElement14.setRessourceGroup(ressourceGroup6);
        planningUnitElement14 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement14);

        PlanningUnitElement planningUnitElement15 = new PlanningUnitElement();
        planningUnitElement15.setPlannedMinutes(600);
        planningUnitElement15.setRessourceGroup(ressourceGroup7);
        planningUnitElement15 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement15);

        PlanningUnitElement planningUnitElement16 = new PlanningUnitElement();
        planningUnitElement16.setPlannedMinutes(210);
        planningUnitElement16.setRessourceGroup(ressourceGroup8);
        planningUnitElement16 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement16);

        PlanningUnitElement planningUnitElement17 = new PlanningUnitElement();
        planningUnitElement17.setPlannedMinutes(400);
        planningUnitElement17.setRessourceGroup(ressourceGroup1);
        planningUnitElement17 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement17);

        PlanningUnitElement planningUnitElement18 = new PlanningUnitElement();
        planningUnitElement18.setPlannedMinutes(90);
        planningUnitElement18.setRessourceGroup((ressourceGroup2));
        planningUnitElement18 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement18);

        PlanningUnitElement planningUnitElement19 = new PlanningUnitElement();
        planningUnitElement19.setPlannedMinutes(110);
        planningUnitElement19.setRessourceGroup((ressourceGroup3));
        planningUnitElement19 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement19);

        PlanningUnitElement planningUnitElement20 = new PlanningUnitElement();
        planningUnitElement20.setPlannedMinutes(111);
        planningUnitElement20.setRessourceGroup((ressourceGroup4));
        planningUnitElement20 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement20);

        PlanningUnitElement planningUnitElement21 = new PlanningUnitElement();
        planningUnitElement21.setPlannedMinutes(112);
        planningUnitElement21.setRessourceGroup((ressourceGroup5));
        planningUnitElement21 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement21);

        
        PlanningUnitElement planningUnitElement22 = new PlanningUnitElement();
        planningUnitElement22.setPlannedMinutes(666);
        planningUnitElement22.setRessourceGroup((ressourceGroup6));
        planningUnitElement22 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement22);

        PlanningUnitElement planningUnitElement23 = new PlanningUnitElement();
        planningUnitElement23.setPlannedMinutes(1337);
        planningUnitElement23.setRessourceGroup((ressourceGroup7));
        planningUnitElement23 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement23);

        PlanningUnitElement planningUnitElement24 = new PlanningUnitElement();
        planningUnitElement24.setPlannedMinutes(450);
        planningUnitElement24.setRessourceGroup((ressourceGroup8));
        planningUnitElement24 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement24);

        PlanningUnitElement planningUnitElement25 = new PlanningUnitElement();
        planningUnitElement25.setPlannedMinutes(320);
        planningUnitElement25.setRessourceGroup((ressourceGroup1));
        planningUnitElement25 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement25);


        PlanningUnitElement planningUnitElement26 = new PlanningUnitElement();
        planningUnitElement26.setPlannedMinutes(300);
        planningUnitElement26.setRessourceGroup((ressourceGroup2));
        planningUnitElement26 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement26);

        PlanningUnitElement planningUnitElement27 = new PlanningUnitElement();
        planningUnitElement27.setPlannedMinutes(400);
        planningUnitElement27.setRessourceGroup((ressourceGroup3));
        planningUnitElement27 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement27);

        PlanningUnitElement planningUnitElement28 = new PlanningUnitElement();
        planningUnitElement28.setPlannedMinutes(500);
        planningUnitElement28.setRessourceGroup((ressourceGroup4));
        planningUnitElement28 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement28);

        PlanningUnitElement planningUnitElement29 = new PlanningUnitElement();
        planningUnitElement29.setPlannedMinutes(100);
        planningUnitElement29.setRessourceGroup((ressourceGroup5));
        planningUnitElement29 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement29);

        PlanningUnitElement planningUnitElement30 = new PlanningUnitElement();
        planningUnitElement30.setPlannedMinutes(200);
        planningUnitElement30.setRessourceGroup((ressourceGroup6));
        planningUnitElement30 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement30);

        PlanningUnitElement planningUnitElement31 = new PlanningUnitElement();
        planningUnitElement31.setPlannedMinutes(300);
        planningUnitElement31.setRessourceGroup((ressourceGroup7));
        planningUnitElement31 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement31);

        PlanningUnitElement planningUnitElement32 = new PlanningUnitElement();
        planningUnitElement32.setPlannedMinutes(350);
        planningUnitElement32.setRessourceGroup((ressourceGroup8));
        planningUnitElement32 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement32);

        PlanningUnitElement planningUnitElement33 = new PlanningUnitElement();
        planningUnitElement33.setPlannedMinutes(410);
        planningUnitElement33.setRessourceGroup((ressourceGroup1));
        planningUnitElement33 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement33);

        PlanningUnitElement planningUnitElement34 = new PlanningUnitElement();
        planningUnitElement34.setPlannedMinutes(450);
        planningUnitElement34.setRessourceGroup((ressourceGroup2));
        planningUnitElement34 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement34);

        PlanningUnitElement planningUnitElement35 = new PlanningUnitElement();
        planningUnitElement35.setPlannedMinutes(430);
        planningUnitElement35.setRessourceGroup((ressourceGroup3));
        planningUnitElement35 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement35);

        PlanningUnitElement planningUnitElement36 = new PlanningUnitElement();
        planningUnitElement36.setPlannedMinutes(200);
        planningUnitElement36.setRessourceGroup((ressourceGroup4));
        planningUnitElement36 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement36);

        PlanningUnitElement planningUnitElement37 = new PlanningUnitElement();
        planningUnitElement37.setPlannedMinutes(150);
        planningUnitElement37.setRessourceGroup((ressourceGroup5));
        planningUnitElement37 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement37);

        PlanningUnitElement planningUnitElement38 = new PlanningUnitElement();
        planningUnitElement38.setPlannedMinutes(200);
        planningUnitElement38.setRessourceGroup((ressourceGroup6));
        planningUnitElement38 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement38);

        PlanningUnitElement planningUnitElement39 = new PlanningUnitElement();
        planningUnitElement39.setPlannedMinutes(220);
        planningUnitElement39.setRessourceGroup((ressourceGroup7));
        planningUnitElement39 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement39);

        PlanningUnitElement planningUnitElement40 = new PlanningUnitElement();
        planningUnitElement40.setPlannedMinutes(410);
        planningUnitElement40.setRessourceGroup((ressourceGroup8));
        planningUnitElement40 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement40);

        PlanningUnitElement planningUnitElement41 = new PlanningUnitElement();
        planningUnitElement41.setPlannedMinutes(500);
        planningUnitElement41.setRessourceGroup((ressourceGroup1));
        planningUnitElement41 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement41);

        PlanningUnitElement planningUnitElement42 = new PlanningUnitElement();
        planningUnitElement42.setPlannedMinutes(600);
        planningUnitElement42.setRessourceGroup((ressourceGroup2));
        planningUnitElement42 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement42);

        PlanningUnitElement planningUnitElement43 = new PlanningUnitElement();
        planningUnitElement43.setPlannedMinutes(200);
        planningUnitElement43.setRessourceGroup((ressourceGroup3));
        planningUnitElement43 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement43);

        PlanningUnitElement planningUnitElement44 = new PlanningUnitElement();
        planningUnitElement44.setPlannedMinutes(150);
        planningUnitElement44.setRessourceGroup((ressourceGroup4));
        planningUnitElement44 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement44);

        PlanningUnitElement planningUnitElement45 = new PlanningUnitElement();
        planningUnitElement45.setPlannedMinutes(100);
        planningUnitElement45.setRessourceGroup((ressourceGroup5));
        planningUnitElement45 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement45);

        PlanningUnitElement planningUnitElement46 = new PlanningUnitElement();
        planningUnitElement46.setPlannedMinutes(60);
        planningUnitElement46.setRessourceGroup((ressourceGroup6));
        planningUnitElement46 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement46);

        PlanningUnitElement planningUnitElement47 = new PlanningUnitElement();
        planningUnitElement47.setPlannedMinutes(90);
        planningUnitElement47.setRessourceGroup((ressourceGroup7));
        planningUnitElement47 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement47);

        PlanningUnitElement planningUnitElement48 = new PlanningUnitElement();
        planningUnitElement48.setPlannedMinutes(180);
        planningUnitElement48.setRessourceGroup((ressourceGroup8));
        planningUnitElement48 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement48);

        PlanningUnitElement planningUnitElement49 = new PlanningUnitElement();
        planningUnitElement49.setPlannedMinutes(210);
        planningUnitElement49.setRessourceGroup((ressourceGroup1));
        planningUnitElement49 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement49);

        PlanningUnitElement planningUnitElement50 = new PlanningUnitElement();
        planningUnitElement50.setPlannedMinutes(120);
        planningUnitElement50.setRessourceGroup((ressourceGroup2));
        planningUnitElement50 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement50);

        PlanningUnitElement planningUnitElement51 = new PlanningUnitElement();
        planningUnitElement51.setPlannedMinutes(300);
        planningUnitElement51.setRessourceGroup((ressourceGroup3));
        planningUnitElement51 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement51);
        
        PlanningUnitElement planningUnitElement52 = new PlanningUnitElement();
        planningUnitElement52.setPlannedMinutes(360);
        planningUnitElement52.setRessourceGroup((ressourceGroup4));
        planningUnitElement52 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement52);

        PlanningUnitElement planningUnitElement53 = new PlanningUnitElement();
        planningUnitElement53.setPlannedMinutes(420);
        planningUnitElement53.setRessourceGroup((ressourceGroup5));
        planningUnitElement53 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement53);

        PlanningUnitElement planningUnitElement54 = new PlanningUnitElement();
        planningUnitElement54.setPlannedMinutes(440);
        planningUnitElement54.setRessourceGroup((ressourceGroup6));
        planningUnitElement54 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement54);

        PlanningUnitElement planningUnitElement55 = new PlanningUnitElement();
        planningUnitElement55.setPlannedMinutes(460);
        planningUnitElement55.setRessourceGroup((ressourceGroup7));
        planningUnitElement55 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement55);

        PlanningUnitElement planningUnitElement56 = new PlanningUnitElement();
        planningUnitElement56.setPlannedMinutes(150);
        planningUnitElement56.setRessourceGroup((ressourceGroup8));
        planningUnitElement56 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement56);


        PlanningUnitElement planningUnitElement57 = new PlanningUnitElement();
        planningUnitElement57.setPlannedMinutes(300);
        planningUnitElement57.setRessourceGroup((ressourceGroup1));
        planningUnitElement57 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement57);

        PlanningUnitElement planningUnitElement58 = new PlanningUnitElement();
        planningUnitElement58.setPlannedMinutes(400);
        planningUnitElement58.setRessourceGroup((ressourceGroup2));
        planningUnitElement58 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement58);

        PlanningUnitElement planningUnitElement59 = new PlanningUnitElement();
        planningUnitElement59.setPlannedMinutes(200);
        planningUnitElement59.setRessourceGroup((ressourceGroup3));
        planningUnitElement59 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement59);

        PlanningUnitElement planningUnitElement60 = new PlanningUnitElement();
        planningUnitElement60.setPlannedMinutes(150);
        planningUnitElement60.setRessourceGroup((ressourceGroup4));
        planningUnitElement60 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement60);

        PlanningUnitElement planningUnitElement61 = new PlanningUnitElement();
        planningUnitElement61.setPlannedMinutes(800);
        planningUnitElement61.setRessourceGroup((ressourceGroup5));
        planningUnitElement61 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement61);

        PlanningUnitElement planningUnitElement62 = new PlanningUnitElement();
        planningUnitElement62.setPlannedMinutes(340);
        planningUnitElement62.setRessourceGroup((ressourceGroup6));
        planningUnitElement62 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement62);

        PlanningUnitElement planningUnitElement63 = new PlanningUnitElement();
        planningUnitElement63.setPlannedMinutes(220);
        planningUnitElement63.setRessourceGroup((ressourceGroup7));
        planningUnitElement63 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement63);

        PlanningUnitElement planningUnitElement64 = new PlanningUnitElement();
        planningUnitElement64.setPlannedMinutes(800);
        planningUnitElement64.setRessourceGroup((ressourceGroup8));
        planningUnitElement64 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement64);

        PlanningUnitElement planningUnitElement65 = new PlanningUnitElement();
        planningUnitElement65.setPlannedMinutes(320);
        planningUnitElement65.setRessourceGroup((ressourceGroup1));
        planningUnitElement65 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement65);

        PlanningUnitElement planningUnitElement66 = new PlanningUnitElement();
        planningUnitElement66.setPlannedMinutes(150);
        planningUnitElement66.setRessourceGroup((ressourceGroup2));
        planningUnitElement66 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement66);

        PlanningUnitElement planningUnitElement67 = new PlanningUnitElement();
        planningUnitElement67.setPlannedMinutes(100);
        planningUnitElement67.setRessourceGroup((ressourceGroup3));
        planningUnitElement67 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement67);

        PlanningUnitElement planningUnitElement68 = new PlanningUnitElement();
        planningUnitElement68.setPlannedMinutes(300);
        planningUnitElement68.setRessourceGroup((ressourceGroup4));
        planningUnitElement68 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement68);

        PlanningUnitElement planningUnitElement69 = new PlanningUnitElement();
        planningUnitElement69.setPlannedMinutes(250);
        planningUnitElement69.setRessourceGroup((ressourceGroup5));
        planningUnitElement69 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement69);

        PlanningUnitElement planningUnitElement70 = new PlanningUnitElement();
        planningUnitElement70.setPlannedMinutes(730);
        planningUnitElement70.setRessourceGroup((ressourceGroup6));
        planningUnitElement70 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement70);

        PlanningUnitElement planningUnitElement71 = new PlanningUnitElement();
        planningUnitElement71.setPlannedMinutes(600);
        planningUnitElement71.setRessourceGroup((ressourceGroup7));
        planningUnitElement71 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement71);

        PlanningUnitElement planningUnitElement72 = new PlanningUnitElement();
        planningUnitElement72.setPlannedMinutes(210);
        planningUnitElement72.setRessourceGroup((ressourceGroup8));
        planningUnitElement72 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement72);

        PlanningUnitElement planningUnitElement73 = new PlanningUnitElement();
        planningUnitElement73.setPlannedMinutes(400);
        planningUnitElement73.setRessourceGroup((ressourceGroup1));
        planningUnitElement73 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement73);

        PlanningUnitElement planningUnitElement74 = new PlanningUnitElement();
        planningUnitElement74.setPlannedMinutes(90);
        planningUnitElement74.setRessourceGroup((ressourceGroup2));
        planningUnitElement74 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement74);

        PlanningUnitElement planningUnitElement75 = new PlanningUnitElement();
        planningUnitElement75.setPlannedMinutes(110);
        planningUnitElement75.setRessourceGroup((ressourceGroup3));
        planningUnitElement75 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement75);

        PlanningUnitElement planningUnitElement76 = new PlanningUnitElement();
        planningUnitElement76.setPlannedMinutes(111);
        planningUnitElement76.setRessourceGroup((ressourceGroup4));
        planningUnitElement76 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement76);

        PlanningUnitElement planningUnitElement77 = new PlanningUnitElement();
        planningUnitElement77.setPlannedMinutes(112);
        planningUnitElement77.setRessourceGroup((ressourceGroup5));
        planningUnitElement77 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement77);


        PlanningUnitElement planningUnitElement78 = new PlanningUnitElement();
        planningUnitElement78.setPlannedMinutes(666);
        planningUnitElement78.setRessourceGroup((ressourceGroup6));
        planningUnitElement78 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement78);

        PlanningUnitElement planningUnitElement79 = new PlanningUnitElement();
        planningUnitElement79.setPlannedMinutes(1337);
        planningUnitElement79.setRessourceGroup((ressourceGroup7));
        planningUnitElement79 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement79);

        PlanningUnitElement planningUnitElement80 = new PlanningUnitElement();
        planningUnitElement80.setPlannedMinutes(450);
        planningUnitElement80.setRessourceGroup((ressourceGroup8));
        planningUnitElement80 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement80);

        PlanningUnitElement planningUnitElement81 = new PlanningUnitElement();
        planningUnitElement81.setPlannedMinutes(320);
        planningUnitElement81.setRessourceGroup((ressourceGroup1));
        planningUnitElement81 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement81);


        PlanningUnitElement planningUnitElement82 = new PlanningUnitElement();
        planningUnitElement82.setPlannedMinutes(300);
        planningUnitElement82.setRessourceGroup((ressourceGroup2));
        planningUnitElement82 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement82);

        PlanningUnitElement planningUnitElement83 = new PlanningUnitElement();
        planningUnitElement83.setPlannedMinutes(400);
        planningUnitElement83.setRessourceGroup((ressourceGroup3));
        planningUnitElement83 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement83);

        PlanningUnitElement planningUnitElement84 = new PlanningUnitElement();
        planningUnitElement84.setPlannedMinutes(500);
        planningUnitElement84.setRessourceGroup((ressourceGroup4));
        planningUnitElement84 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement84);

        PlanningUnitElement planningUnitElement85 = new PlanningUnitElement();
        planningUnitElement85.setPlannedMinutes(100);
        planningUnitElement85.setRessourceGroup((ressourceGroup5));
        planningUnitElement85 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement85);

        PlanningUnitElement planningUnitElement86 = new PlanningUnitElement();
        planningUnitElement86.setPlannedMinutes(200);
        planningUnitElement86.setRessourceGroup((ressourceGroup6));
        planningUnitElement86 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement86);

        PlanningUnitElement planningUnitElement87 = new PlanningUnitElement();
        planningUnitElement87.setPlannedMinutes(300);
        planningUnitElement87.setRessourceGroup((ressourceGroup7));
        planningUnitElement87 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement87);

        PlanningUnitElement planningUnitElement88 = new PlanningUnitElement();
        planningUnitElement88.setPlannedMinutes(350);
        planningUnitElement88.setRessourceGroup((ressourceGroup8));
        planningUnitElement88 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement88);

        PlanningUnitElement planningUnitElement89 = new PlanningUnitElement();
        planningUnitElement89.setPlannedMinutes(410);
        planningUnitElement89.setRessourceGroup((ressourceGroup1));
        planningUnitElement89 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement89);

        PlanningUnitElement planningUnitElement90 = new PlanningUnitElement();
        planningUnitElement90.setPlannedMinutes(450);
        planningUnitElement90.setRessourceGroup((ressourceGroup2));
        planningUnitElement90 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement90);

        PlanningUnitElement planningUnitElement91 = new PlanningUnitElement();
        planningUnitElement91.setPlannedMinutes(430);
        planningUnitElement91.setRessourceGroup((ressourceGroup3));
        planningUnitElement91 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement91);

        PlanningUnitElement planningUnitElement92 = new PlanningUnitElement();
        planningUnitElement92.setPlannedMinutes(200);
        planningUnitElement92.setRessourceGroup((ressourceGroup4));
        planningUnitElement92 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement92);

        PlanningUnitElement planningUnitElement93 = new PlanningUnitElement();
        planningUnitElement93.setPlannedMinutes(150);
        planningUnitElement93.setRessourceGroup((ressourceGroup5));
        planningUnitElement93 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement93);

        PlanningUnitElement planningUnitElement94 = new PlanningUnitElement();
        planningUnitElement94.setPlannedMinutes(200);
        planningUnitElement94.setRessourceGroup((ressourceGroup6));
        planningUnitElement94 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement94);

        PlanningUnitElement planningUnitElement95 = new PlanningUnitElement();
        planningUnitElement95.setPlannedMinutes(220);
        planningUnitElement95.setRessourceGroup((ressourceGroup7));
        planningUnitElement95 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement95);

        PlanningUnitElement planningUnitElement96 = new PlanningUnitElement();
        planningUnitElement96.setPlannedMinutes(410);
        planningUnitElement96.setRessourceGroup((ressourceGroup8));

        planningUnitElement96 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement96);

        PlanningUnitElement planningUnitElement97 = new PlanningUnitElement();
        planningUnitElement97.setPlannedMinutes(500);
        planningUnitElement97.setRessourceGroup((ressourceGroup1));
        planningUnitElement97 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement97);

        PlanningUnitElement planningUnitElement98 = new PlanningUnitElement();
        planningUnitElement98.setPlannedMinutes(600);
        planningUnitElement98.setRessourceGroup((ressourceGroup2));
        planningUnitElement98 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement98);

        PlanningUnitElement planningUnitElement99 = new PlanningUnitElement();
        planningUnitElement99.setPlannedMinutes(200);
        planningUnitElement99.setRessourceGroup((ressourceGroup3));
        planningUnitElement99 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement99);

        PlanningUnitElement planningUnitElement100 = new PlanningUnitElement();
        planningUnitElement100.setPlannedMinutes(150);
        planningUnitElement100.setRessourceGroup((ressourceGroup4));
        planningUnitElement100 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement100);

        PlanningUnitElement planningUnitElement101 = new PlanningUnitElement();
        planningUnitElement101.setPlannedMinutes(100);
        planningUnitElement101.setRessourceGroup((ressourceGroup5));
        planningUnitElement101 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement101);

        PlanningUnitElement planningUnitElement102 = new PlanningUnitElement();
        planningUnitElement102.setPlannedMinutes(60);
        planningUnitElement102.setRessourceGroup((ressourceGroup6));
        planningUnitElement102 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement102);

        PlanningUnitElement planningUnitElement103 = new PlanningUnitElement();
        planningUnitElement103.setPlannedMinutes(90);
        planningUnitElement103.setRessourceGroup((ressourceGroup7));
        planningUnitElement103 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement103);

        PlanningUnitElement planningUnitElement104 = new PlanningUnitElement();
        planningUnitElement104.setPlannedMinutes(180);
        planningUnitElement104.setRessourceGroup((ressourceGroup8));
        planningUnitElement104 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement104);

        PlanningUnitElement planningUnitElement105 = new PlanningUnitElement();
        planningUnitElement105.setPlannedMinutes(210);
        planningUnitElement105.setRessourceGroup((ressourceGroup1));
        planningUnitElement105 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement105);

        PlanningUnitElement planningUnitElement106 = new PlanningUnitElement();
        planningUnitElement106.setPlannedMinutes(120);
        planningUnitElement106.setRessourceGroup((ressourceGroup2));
        planningUnitElement106 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement106);

        PlanningUnitElement planningUnitElement107 = new PlanningUnitElement();
        planningUnitElement107.setPlannedMinutes(300);
        planningUnitElement107.setRessourceGroup((ressourceGroup3));
        planningUnitElement107 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement107);

        PlanningUnitElement planningUnitElement108 = new PlanningUnitElement();
        planningUnitElement108.setPlannedMinutes(360);
        planningUnitElement108.setRessourceGroup((ressourceGroup4));
        planningUnitElement108 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement108);

        PlanningUnitElement planningUnitElement109 = new PlanningUnitElement();
        planningUnitElement109.setPlannedMinutes(420);
        planningUnitElement109.setRessourceGroup((ressourceGroup5));
        planningUnitElement109 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement109);

        PlanningUnitElement planningUnitElement110 = new PlanningUnitElement();
        planningUnitElement110.setPlannedMinutes(440);
        planningUnitElement110.setRessourceGroup((ressourceGroup6));
        planningUnitElement110 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement110);

        PlanningUnitElement planningUnitElement111 = new PlanningUnitElement();
        planningUnitElement111.setPlannedMinutes(460);
        planningUnitElement111.setRessourceGroup((ressourceGroup7));
        planningUnitElement111 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement111);

        PlanningUnitElement planningUnitElement112 = new PlanningUnitElement();
        planningUnitElement112.setPlannedMinutes(150);
        planningUnitElement112.setRessourceGroup((ressourceGroup8));
        planningUnitElement112 = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().createEntity(planningUnitElement112);

        TextElement description1 = new TextElement();
        description1.setBezeichnung("erste beschreibung");
        description1.setText("Dies hier stellt die erste beschreibung dar.");
        description1 = DaoFactorySingleton.getInstance().getTextElementDAO().createEntity(description1);

        TextElement description2 = new TextElement();
        description2.setBezeichnung("die zweite beschreibung");
        description2.setText("Dies hier stellt die zweite tolle beschreibung dar.");
        description2 = DaoFactorySingleton.getInstance().getTextElementDAO().createEntity(description2);

        TextElement description3 = new TextElement();
        description3.setBezeichnung("dritte beschreibung");
        description3.setText("Dies hier stellt die dritte beschreibung dar..");
        description3 = DaoFactorySingleton.getInstance().getTextElementDAO().createEntity(description3);

        TextElement description4 = new TextElement();
        description4.setBezeichnung("die vierte beschreibung");
        description4.setText("Dies hier stellt die vierte tolle beschreibung dar.");
        description4 = DaoFactorySingleton.getInstance().getTextElementDAO().createEntity(description4);

        TextElement testcase1 = new TextElement();
        testcase1.setBezeichnung("der 1. testcase");
        testcase1.setText("Dies hier stellt den ersten tollen Testcase dar.");
        testcase1 = DaoFactorySingleton.getInstance().getTextElementDAO().createEntity(testcase1);

        TextElement testcase2 = new TextElement();
        testcase2.setBezeichnung("der 2. tolle testcase");
        testcase2.setText("Dies hier stellt den zweiten tollen Testcase dar.");
        testcase2 = DaoFactorySingleton.getInstance().getTextElementDAO().createEntity(testcase2);

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



        planningUnit1 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntity(planningUnit1);
        planningUnit2 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntity(planningUnit2);
        planningUnit3 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntity(planningUnit3);
        planningUnit4 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntity(planningUnit4);
        planningUnit5 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntity(planningUnit5);
        planningUnit6 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntity(planningUnit6);
        planningUnit7 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntity(planningUnit7);
        planningUnit8 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntity(planningUnit8);
        planningUnit9 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntity(planningUnit9);
        planningUnit10 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntity(planningUnit10);
        planningUnit11 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntity(planningUnit11);
        planningUnit12 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntity(planningUnit12);
        planningUnit13 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntity(planningUnit13);
        planningUnit14 = DaoFactorySingleton.getInstance().getPlanningUnitDAO().createEntity(planningUnit14);

        DaoFactorySingleton.getInstance().getPlannedProjectDAO().addPlanningUnitToProject(plannedProject1, planningUnit1);
        DaoFactorySingleton.getInstance().getPlannedProjectDAO().addPlanningUnitToProject(plannedProject1, planningUnit8);
        DaoFactorySingleton.getInstance().getPlannedProjectDAO().addPlanningUnitToProject(plannedProject1, planningUnit13);
        DaoFactorySingleton.getInstance().getPlannedProjectDAO().addPlanningUnitToProject(plannedProject1, planningUnit14);

        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit1, planningUnit2);
        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit1, planningUnit5);
        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit1, planningUnit6);
        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit1, planningUnit7);
        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit2, planningUnit3);
        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit2, planningUnit4);
        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit8, planningUnit9);
        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit8, planningUnit12);
        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit9, planningUnit10);
        DaoFactorySingleton.getInstance().getPlanningUnitDAO().addChildPlanningUnitToParentPlanningUnit(planningUnit9, planningUnit11);
    }
}
