INSERT INTO
        mandantengruppe(id, mandantengruppe) VALUES (1, 'RapidPM');

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('Mandantengruppe_id', 1);

INSERT INTO
        benutzergruppe(id, gruppenname)VALUES (1, 'User');
INSERT INTO
        benutzergruppe(id, gruppenname)VALUES (2, 'Anonymous');
INSERT INTO
        benutzergruppe(id, gruppenname)VALUES (3, 'Admin');

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('BenutzerGruppe_id', 3);

INSERT INTO
        benutzerwebapplikation(id, webappname)VALUES (1, 'RapidPM');
INSERT INTO
        benutzerwebapplikation(id, webappname)VALUES (2, 'RapidPMBeta');

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('BenutzerWebapplikation_id', 2);

INSERT INTO
        benutzer(
                id, active, email, failedlogins, hidden, lastlogin, login, passwd,
                validfrom, validuntil, benutzergruppe_id, benutzerwebapplikation_id,
                mandantengruppe_id)
        VALUES (1, true , 'nobody@rapidpm.org', 0, false , '2012-09-11', '<not assigned>', 'geheim',
                '2012-09-11',
                '2013-09-11', 1, 1,1);

INSERT INTO
        benutzer(
                id, active, email, failedlogins, hidden, lastlogin, login, passwd,
                validfrom, validuntil, benutzergruppe_id, benutzerwebapplikation_id,
                mandantengruppe_id)
        VALUES (2, true , 'sven.ruppert@rapidpm.org', 0, false , '2012-09-11', 'sven.ruppert', 'geheim',
                '2012-09-11',
                '2013-09-11', 1, 1,1);

INSERT INTO
        benutzer(
                id, active, email, failedlogins, hidden, lastlogin, login, passwd,
                validfrom, validuntil, benutzergruppe_id, benutzerwebapplikation_id,
                mandantengruppe_id)
        VALUES (3, true , 'daniel.macdonald@rapidpm.org', 0, false , '2012-09-11', 'daniel.macdonald', 'geheim',
                '2012-09-11',
                '2013-09-11', 1, 1,1);

INSERT INTO
        benutzer(
                id, active, email, failedlogins, hidden, lastlogin, login, passwd,
                validfrom, validuntil, benutzergruppe_id, benutzerwebapplikation_id,
                mandantengruppe_id)
        VALUES (4, true , 'alvin.schiller@rapidpm.org', 0, false , '2012-09-11', 'alvin.schiller', 'geheim',
                '2012-09-11',
                '2013-09-11', 1, 1,1);

INSERT INTO
        benutzer(
                id, active, email, failedlogins, hidden, lastlogin, login, passwd,
                validfrom, validuntil, benutzergruppe_id, benutzerwebapplikation_id,
                mandantengruppe_id)
        VALUES (5, true , 'marco.ebbinghaus@rapidpm.org', 0, false , '2012-09-11',
        'Marco Ebbinghaus.ebbinghaus', 'geheim',
                '2012-09-11',
                '2013-09-11', 1, 1,1);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('Benutzer_id', 6);

INSERT INTO
        ressourcegroup(id, name, hoursPerWeek, weeksPerYear, planAnzahl, facturizable, externalEurosPerHour, bruttoGehalt)
        VALUES (1, 'GF', 40, 50, 1, 0.5, 125.0,200000.0);

INSERT INTO
        ressourcegroup(id, name, hoursPerWeek, weeksPerYear, planAnzahl, facturizable, externalEurosPerHour, bruttoGehalt)
        VALUES (2, 'Multiprojekt Manager', 40, 46, 1, 0.5, 125.0,83000.0);

INSERT INTO
        ressourcegroup(id, name, hoursPerWeek, weeksPerYear, planAnzahl, facturizable, externalEurosPerHour, bruttoGehalt)
        VALUES (3, 'Projektleiter / Scrum Master', 40, 46, 1, 0.75, 75.0,72000.0);

INSERT INTO
        ressourcegroup(id, name, hoursPerWeek, weeksPerYear, planAnzahl, facturizable, externalEurosPerHour, bruttoGehalt)
        VALUES (4, 'Senior Projektmitarbeiter', 40, 46, 1, 0.75, 65.0,72000.0);

INSERT INTO
        ressourcegroup(id, name, hoursPerWeek, weeksPerYear, planAnzahl, facturizable, externalEurosPerHour, bruttoGehalt)
        VALUES (5, 'Junior Projektmitarbeiter', 40, 46, 1, 0.75, 45.0,45000.0);

INSERT INTO
        ressourcegroup(id, name, hoursPerWeek, weeksPerYear, planAnzahl, facturizable, externalEurosPerHour, bruttoGehalt)
        VALUES (6, 'Aushilfe / stud. Projektmitarbeiter', 40, 46, 1, 0.75, 25.0,17000.0);

INSERT INTO
        ressourcegroup(id, name, hoursPerWeek, weeksPerYear, planAnzahl, facturizable, externalEurosPerHour, bruttoGehalt)
        VALUES (7, 'externe Ressource', 40, 46, 1, 0.8, 90.0,125000.0);

INSERT INTO
        ressourcegroup(id, name, hoursPerWeek, weeksPerYear, planAnzahl, facturizable, externalEurosPerHour, bruttoGehalt)
        VALUES (8, 'Backoffice', 20, 46, 1, 0.01, 0.0,62000.0);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('RessourceGroup_id', 9);


INSERT INTO
        plannedproject(id, active, fakturierbar, info,projektname,creator_id,mandantengruppe_id,responsibleperson_id)
        VALUES (1, true, true, 'erstes testprojekt', 'Projekt Nr 1', 1, 1,1);

INSERT INTO
        plannedproject(id, active, fakturierbar, info,projektname,creator_id,mandantengruppe_id,responsibleperson_id)
        VALUES (2, true, true, 'zweites testprojekt', 'Projekt Nr 2', 1, 1,1);

INSERT INTO
        plannedproject(id, active, fakturierbar, info,projektname,creator_id,mandantengruppe_id,responsibleperson_id)
        VALUES (3, true, true, 'drittes testprojekt', 'Projekt Nr 3', 1, 1,1);

INSERT INTO
        plannedproject(id, active, fakturierbar, info,projektname,creator_id,mandantengruppe_id,responsibleperson_id)
        VALUES (4, true, true, 'viertes testprojekt', 'Projekt Nr 4', 1, 1,1);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('PlannedProject_id', 5);

INSERT INTO
        planningstatus(id, name, ordernumber)
        VALUES (1, 'planningstatus 1',1);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('PlanningStatus_id', 1);

INSERT INTO
        issuepriority(id, prio, priorityname, priorityfilename)
        VALUES (1, 1 ,'Minor', 'priority_minor.gif');

INSERT INTO
        issuepriority(id, prio, priorityname, priorityfilename)
        VALUES (2, 2 ,'Trivial', 'priority_trivial.gif');

INSERT INTO
        issuepriority(id, prio, priorityname, priorityfilename)
        VALUES (3, 3 ,'Major', 'priority_major.gif');

INSERT INTO
        issuepriority(id, prio, priorityname, priorityfilename)
        VALUES (4, 4 ,'Critical', 'priority_critical.gif');

INSERT INTO
        issuepriority(id, prio, priorityname, priorityfilename)
        VALUES (5, 5 ,'Blocker', 'priority_blocker.gif');

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('IssuePriority_id', 5);

INSERT INTO
        issuestatus(id, statusname, statusfilename)
        VALUES (1, 'OnHold', 'status_onhold.gif');

INSERT INTO
        issuestatus(id, statusname, statusfilename)
        VALUES (2, 'Closed', 'status_closed.gif');

INSERT INTO
        issuestatus(id, statusname, statusfilename)
        VALUES (3, 'Resolved', 'status_resolved.gif');

INSERT INTO
        issuestatus(id, statusname, statusfilename)
        VALUES (4, 'InProgress', 'status_inprogress.gif');

INSERT INTO
        issuestatus(id, statusname, statusfilename)
        VALUES (5, 'Open', 'status_open.gif');

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('IssueStatus_id', 5);

INSERT INTO anrede(id,anrede)
VALUES (1,'Herr');

INSERT INTO anrede(id,anrede)
VALUES (2,'Frau');

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('Anrede_id', 2);

INSERT INTO geschlecht(id,geschlecht)
VALUES (1,'männlich');

INSERT INTO geschlecht(id,geschlecht)
VALUES (2,'weiblich');

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('Geschlecht_id', 2);

INSERT INTO person(id,anrede_id,geschlecht_id)
VALUES (1,1,1);

INSERT INTO person(id,anrede_id,geschlecht_id)
VALUES (2,1,1);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('Person_id', 2);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (1,1,'Änderungsdienst',1,1,null,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (2,2,'Projektübersicht',2,1,null,1,3);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (3,3,'Vertragsinhalt',3,1,2,1,4);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (4,4,'Auftragsvereinbarung',4,1,3,1,2);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (5,5,'Kundenanschrift / Einsatzort(e)',5,1,3,1,2);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (6,6,'Projektziele grob (Aufgabenstellung)',6,1,2,1,2);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (7,7,'Abgrenzung',7,1,2,1,2);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (8,8,'Abhängigkeiten',8,1,2,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (9,9,'Terminrahmen',9,1,2,1,2);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (10,10,'Projektteam(s)',10,1,2,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (11,11,'Reporting & Kommunikation',11,1,2,1,3);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (12,12,'Abnahmekriterien',12,1,2,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (13,13,'Changerequests',13,1,2,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (14,14,'IST-Zustand',14,1,null,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (15,15,'Mengengerüst',14,2,14,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (16,16,'Darstellung heutiger Prozess',14,3,14,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (17,17,'Abzubildende Geschäftsvorfälle',14,4,null,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (18,18,'Formen',14,5,17,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (19,19,'Eingang des Belegguts',14,6,17,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (20,20,'Konzeption & Customizing',14,5,null,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (21,21,'Basisinstallation',14,5,20,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (22,22,'Systemvorbereitung / Systemempfehlung',14,5,21,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (23,23,'Inhalte der Basisinstallation',14,5,21,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (24,24,'Konsolidierung bestehender Archive',14,5,20,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (25,25,'Darstellung IST-Situation',14,5,24,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (26,26,'Dokumentation, Schulung und Testphase',14,5,null,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (27,27,'Server',14,5,null,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (28,28,'Rechtlicher Hinweis',14,5,null,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (29,29,'Projektfreigabe',14,5,null,1,1);


INSERT INTO pk_gen(gen_key, gen_value) VALUES ('PlanningUnit_id', 30);

INSERT INTO planningunit_testcases(planningunit_id,testcases)
VALUES (21, 'Testcase 1');

INSERT INTO planningunit_testcases(planningunit_id,testcases)
VALUES (21, 'Testcase 2');

INSERT INTO planningunit_testcases(planningunit_id,testcases)
VALUES (21, 'Testcase 3');

INSERT INTO planningunit_testcases(planningunit_id,testcases)
VALUES (21, 'Testcase 4');

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('PlanningUnit_id', 5);

INSERT INTO plannedproject_planningunit(plannedproject_id,planningunits_id)
VALUES (1, 1);

INSERT INTO plannedproject_planningunit(plannedproject_id,planningunits_id)
VALUES (1, 2);

INSERT INTO plannedproject_planningunit(plannedproject_id,planningunits_id)
VALUES (1,14);

INSERT INTO plannedproject_planningunit(plannedproject_id,planningunits_id)
VALUES (1, 17);

INSERT INTO plannedproject_planningunit(plannedproject_id,planningunits_id)
VALUES (1, 20);

INSERT INTO plannedproject_planningunit(plannedproject_id,planningunits_id)
VALUES (1, 26);

INSERT INTO plannedproject_planningunit(plannedproject_id,planningunits_id)
VALUES (1, 27);

INSERT INTO plannedproject_planningunit(plannedproject_id,planningunits_id)
VALUES (1, 28);

INSERT INTO plannedproject_planningunit(plannedproject_id,planningunits_id)
VALUES (1, 29);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('PlannedProject_PlanningUnit_id', 10);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (2, 3);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (3, 4);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (3, 5);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (2, 6);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (2, 7);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (2, 8);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (2, 9);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (2, 10);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (2, 11);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (2, 12);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (2, 13);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (14, 15);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (14, 16);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (17, 18);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (17, 19);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (20, 21);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (21, 22);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (21, 23);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (20, 24);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (24, 25);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('PlanningUnit_PlanningUnit_id', 11);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (1, 3, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (2, 1, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (3, 2, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (4, 4, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (5, 5, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (6, 2, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (7, 1, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (8, 3, 10, 30, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (9, 2, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (10, 3, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (11, 5, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (12, 2, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (13, 2, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (14, 3, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (15, 4, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (16, 5, 10, 30, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (17, 2, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (18, 3, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (19, 5, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (20, 2, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (21, 2, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (22, 3, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (23, 4, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (24, 5, 10, 30, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (25, 2, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (26, 3, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (27, 5, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (28, 2, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (29, 2, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (30, 3, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (31, 4, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (32, 5, 10, 30, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (33, 2, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (34, 3, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (35, 5, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (36, 2, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (37, 2, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (38, 3, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (39, 4, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (40, 5, 10, 30, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (41, 2, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (42, 3, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (43, 5, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (44, 2, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (45, 2, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (46, 3, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (47, 4, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (48, 5, 10, 30, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (49, 2, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (50, 3, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (51, 5, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (52, 2, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (53, 2, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (54, 3, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (55, 4, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (56, 5, 10, 30, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (57, 3, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (58, 1, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (59, 2, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (60, 4, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (61, 5, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (62, 2, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (63, 1, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (64, 3, 10, 30, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (65, 2, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (66, 3, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (67, 5, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (68, 2, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (69, 2, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (70, 3, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (71, 4, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (72, 5, 10, 30, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (73, 2, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (74, 3, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (75, 5, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (76, 2, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (77, 2, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (78, 3, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (79, 4, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (80, 5, 10, 30, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (81, 2, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (82, 3, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (83, 5, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (84, 2, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (85, 2, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (86, 3, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (87, 4, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (88, 5, 10, 30, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (89, 2, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (90, 3, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (91, 5, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (92, 2, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (93, 2, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (94, 3, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (95, 4, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (96, 5, 10, 30, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (97, 2, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (98, 3, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (99, 5, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (100, 2, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (101, 2, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (102, 3, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (103, 4, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (104, 5, 10, 30, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (105, 2, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (106, 3, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (107, 5, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (108, 2, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (109, 2, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (110, 3, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (111, 4, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (112, 5, 10, 30, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (113, 2, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (114, 3, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (115, 5, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (116, 2, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (117, 2, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (118, 3, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (119, 4, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (120, 5, 10, 30, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (121, 2, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (122, 3, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (123, 5, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (124, 2, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (125, 2, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (126, 3, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (127, 4, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (128, 5, 10, 30, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (129, 2, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (130, 3, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (131, 5, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (132, 2, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (133, 2, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (134, 3, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (135, 4, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (136, 5, 10, 30, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (137, 2, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (138, 3, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (139, 5, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (140, 2, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (141, 2, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (142, 3, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (143, 4, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (144, 5, 10, 30, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (145, 2, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (146, 3, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (147, 5, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (148, 2, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (149, 2, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (150, 3, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (151, 4, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (152, 5, 10, 30, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (153, 2, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (154, 3, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (155, 5, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (156, 2, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (157, 2, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (158, 3, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (159, 4, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (160, 5, 10, 30, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (161, 3, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (162, 1, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (163, 2, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (164, 4, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (165, 5, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (166, 2, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (167, 1, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (168, 3, 10, 30, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (169, 2, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (170, 3, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (171, 5, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (172, 2, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (173, 2, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (174, 3, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (175, 4, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (176, 5, 10, 30, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (177, 2, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (178, 3, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (179, 5, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (180, 2, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (181, 2, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (182, 3, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (183, 4, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (184, 5, 10, 30, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (185, 2, 4, 20, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (186, 3, 3, 10, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (187, 5, 11, 5, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (188, 2, 10, 0, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (189, 2, 15, 50, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (190, 3, 1, 30, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (191, 4, 5, 20, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (192, 4, 5, 20, 8);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (193, 5, 10, 30, 1);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (194, 2, 4, 20, 2);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (195, 3, 3, 10, 3);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (196, 5, 11, 5, 4);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (197, 2, 10, 0, 5);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (198, 2, 15, 50, 6);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (199, 3, 1, 30, 7);

INSERT INTO planningunitelement(id,planneddays,plannedhours,plannedminutes,ressourcegroup_id)
VALUES (200, 4, 5, 20, 8);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('PlanningUnitElement_id', 201);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (1, 1);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (1, 2);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (1, 3);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (1, 4);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (1, 5);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (1, 6);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (1, 7);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (1, 8);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (2, 9);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (2, 10);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (2, 11);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (2, 12);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (2, 13);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (2, 14);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (2, 15);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (2, 16);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (3, 17);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (3, 18);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (3, 19);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (3, 20);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (3, 21);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (3, 22);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (3, 23);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (3, 24);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (4, 25);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (4, 26);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (4, 27);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (4, 28);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (4, 29);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (4, 30);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (4, 31);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (4, 32);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (5, 33);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (5, 34);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (5, 35);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (5, 36);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (5, 37);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (5, 38);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (5, 39);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (5, 40);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (6, 41);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (6, 42);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (6, 43);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (6, 44);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (6, 45);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (6, 46);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (6, 47);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (6, 48);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (7, 49);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (7, 50);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (7, 51);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (7, 52);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (7, 53);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (7, 54);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (7, 55);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (7, 56);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (8, 57);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (8, 58);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (8, 59);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (8, 60);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (8, 61);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (8, 62);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (8, 63);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (8, 64);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (9, 65);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (9, 66);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (9, 67);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (9, 68);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (9, 69);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (9, 70);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (9, 71);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (9, 72);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (10, 73);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (10, 74);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (10, 75);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (10, 76);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (10, 77);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (10, 78);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (10, 79);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (10, 80);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (11, 81);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (11, 82);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (11, 83);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (11, 84);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (11, 85);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (11, 86);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (11, 87);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (11, 88);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (12, 89);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (12, 90);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (12, 91);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (12, 92);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (12, 93);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (12, 94);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (12, 95);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (12, 96);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (13, 97);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (13, 98);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (13, 99);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (13, 100);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (13, 101);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (13, 102);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (13, 103);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (13, 104);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (14, 105);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (14, 106);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (14, 107);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (14, 108);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (14, 109);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (14, 110);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (14, 111);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (14, 112);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (15, 113);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (15, 114);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (15, 115);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (15, 116);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (15, 117);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (15, 118);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (15, 119);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (15, 120);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (16, 121);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (16, 122);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (16, 123);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (16, 124);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (16, 125);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (16, 126);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (16, 127);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (16, 128);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (17, 129);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (17, 130);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (17, 131);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (17, 132);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (17, 133);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (17, 134);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (17, 135);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (17, 136);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (18, 137);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (18, 138);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (18, 139);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (18, 140);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (18, 141);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (18, 142);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (18, 143);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (18, 144);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (19, 145);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (19, 146);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (19, 147);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (19, 148);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (19, 149);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (19, 150);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (19, 151);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (19, 152);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (20, 153);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (20, 154);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (20, 155);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (20, 156);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (20, 157);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (20, 158);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (20, 159);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (20, 160);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (21, 161);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (21, 162);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (21, 163);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (21, 164);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (21, 165);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (21, 166);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (21, 167);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (21, 168);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (22, 169);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (22, 170);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (22, 171);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (22, 172);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (22, 173);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (22, 174);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (22, 175);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (22, 176);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (23, 177);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (23, 178);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (23, 179);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (23, 180);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (23, 181);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (23, 182);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (23, 183);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (23, 184);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (24, 185);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (24, 186);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (24, 187);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (24, 188);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (24, 189);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (24, 190);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (24, 191);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (24, 192);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (25, 193);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (25, 194);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (25, 195);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (25, 196);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (25, 197);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (25, 198);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (25, 199);

INSERT INTO planningunit_planningunitelement (planningunit_id, planningunitelementlist_id)
VALUES (25, 200);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('PlanningUnit_PlanningUnitElement_id', 201);