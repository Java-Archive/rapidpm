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
        'marco.ebbinghaus', 'geheim',
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
        plannedproject(id, active, fakturierbar, info,projektname,projekttoken,creator_id,mandantengruppe_id,
        responsibleperson_id, externaldailyrate, hoursperworkingday)
        VALUES (1, true, true, 'erstes testprojekt', 'Projekt Nr 1','PRO1', 1, 1,1,750.0,8);

INSERT INTO
        plannedproject(id, active, fakturierbar, info,projektname,projekttoken,creator_id,mandantengruppe_id,
        responsibleperson_id, externaldailyrate, hoursperworkingday)
        VALUES (2, true, true, 'zweites testprojekt', 'Projekt Nr 2', 'PRO2', 1, 1,1,750.0, 9);

INSERT INTO
        plannedproject(id, active, fakturierbar, info,projektname,projekttoken,creator_id,mandantengruppe_id,
        responsibleperson_id, externaldailyrate, hoursperworkingday)
        VALUES (3, true, true, 'drittes testprojekt', 'Projekt Nr 3','PRO3', 1, 1,1,750.0, 8);

INSERT INTO
        plannedproject(id, active, fakturierbar, info,projektname,projekttoken,creator_id,mandantengruppe_id,
        responsibleperson_id, externaldailyrate, hoursperworkingday)
        VALUES (4, true, true, 'viertes testprojekt', 'Projekt Nr 4','PRO4', 1, 1,1,750.0, 9);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('PlannedProject_id', 5);


INSERT INTO
        planningstatus(id, name, ordernumber)
        VALUES (1, 'planningstatus 1',1);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('PlanningStatus_id', 1);


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
VALUES (1,1,'Vorbereitungen',1,1,null,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (2,2,'Erstkontakt vor Ort',2,1,1,1,3);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (3,3,'Person A kontaktieren',3,1,2,1,4);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (4,4,'Person B kontaktieren',4,1,2,1,2);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (5,5,'Gesprächsvorbereitung',5,1,1,1,2);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (6,6,'Präsentation',6,1,1,1,2);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (7,7,'Gesprächsbestätigung',7,1,1,1,2);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (8,8,'Vorbereitungsarbeiten',8,1,null,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (9,9,'Vorbereitung der Maschinen',9,1,8,1,2);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (10,10,'Vorbereitung von Maschine 1',10,1,9,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (11,11,'Vorbereitung von Maschine 2',11,1,9,1,3);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (12,12,'Vorbereitung der Werkzeuge',12,1,8,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (13,13,'Durchführung',13,1,null,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,estimatedStoryPoints,komplexitaet,parent_id,planningstatus_id,responsibleperson_id)
VALUES (14,14,'Beendigung',14,1,null,1,1);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('PlanningUnit_id', 15);

INSERT INTO textelement(id, bezeichnung, text) VALUES (1,'erste beschreibung',
                                                       'Dies hier stellt die erste beschreibung dar.');

INSERT INTO textelement(id, bezeichnung, text) VALUES (2,'die zweite beschreibung',
                                                       'Dies hier stellt die zweite tolle beschreibung dar.');

INSERT INTO textelement(id, bezeichnung, text) VALUES (3,'dritte beschreibung',
                                                       'Dies hier stellt die erste beschreibung dar.');

INSERT INTO textelement(id, bezeichnung, text) VALUES (4,'die vierte beschreibung',
                                                       'Dies hier stellt die zweite tolle beschreibung dar.');

INSERT INTO textelement(id, bezeichnung, text) VALUES (5,'der 1. testcase',
                                                       'Dies hier stellt den zweiten tollen Testcase dar.');

INSERT INTO textelement(id, bezeichnung, text) VALUES (6,'der 2. tolle testcase',
                                                       'Dies hier stellt den zweiten tollen Testcase dar.');

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('TextElement_id', 7);

INSERT INTO planningunit_description (planningunit_id, descriptions_id) VALUES (1,1);

INSERT INTO planningunit_description (planningunit_id, descriptions_id) VALUES (1,2);

INSERT INTO planningunit_description (planningunit_id, descriptions_id) VALUES (1,3);

INSERT INTO planningunit_description (planningunit_id, descriptions_id) VALUES (1,4);

INSERT INTO pk_gen (gen_key, gen_value) VALUES ('PlanningUnit_Description_id',5);

INSERT INTO planningunit_testcase (planningunit_id, testcases_id) VALUES (1,5);

INSERT INTO planningunit_testcase (planningunit_id, testcases_id) VALUES (1,6);

INSERT INTO pk_gen (gen_key, gen_value) VALUES ('PlanningUnit_TestCase_id',3);

INSERT INTO plannedproject_planningunit(plannedproject_id,planningunits_id)
VALUES (1, 1);

INSERT INTO plannedproject_planningunit(plannedproject_id,planningunits_id)
VALUES (2, 8);

INSERT INTO plannedproject_planningunit(plannedproject_id,planningunits_id)
VALUES (2, 13);

INSERT INTO plannedproject_planningunit(plannedproject_id,planningunits_id)
VALUES (2, 14);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('PlannedProject_PlanningUnit_id', 5);



INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (1, 2);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (1, 5);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (1, 6);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (1, 7);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (2, 3);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (2, 4);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (8, 9);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (8, 12);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (9, 10);

INSERT INTO planningunit_planningunit(planningunit_id,kindplanningunits_id)
VALUES (9, 11);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('PlanningUnit_PlanningUnit_id', 11);


INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (1, 300, 1);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (2, 400, 2);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (3, 200, 3);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (4, 150, 4);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (5, 800, 5);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (6, 340, 6);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (7, 220, 7);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (8, 800, 8);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (9, 320, 1);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (10, 150, 2);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (11, 100, 3);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (12, 300, 4);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (13, 250, 5);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (14, 730, 6);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (15, 600, 7);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (16, 210, 8);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (17, 400, 1);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (18, 90, 2);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (19, 110, 3);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (20, 111, 4);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (21, 112, 5);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (22, 666, 6);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (23, 1337, 7);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (24, 450, 8);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (25, 320, 1);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (26, 300, 2);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (27, 400, 3);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (28, 500, 4);
4
INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (29, 100, 5);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (30, 200, 6);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (31, 300, 7);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (32, 350, 8);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (33, 410, 1);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (34, 450, 2);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (35, 430, 3);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (36, 200, 4);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (37, 150, 5);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (38, 200, 6);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (39, 220, 7);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (40, 410, 8);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (41, 500, 1);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (42, 600, 2);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (43, 200, 3);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (44, 150, 4);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (45, 100, 5);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (46, 60, 6);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (47, 90, 7);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (48, 180, 8);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (49, 210, 1);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (50, 120, 2);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (51, 300, 3);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (52, 360, 4);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (53, 420, 5);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (54, 440, 6);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (55, 460, 7);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (56, 150, 8);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (57, 140, 1);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (58, 130, 2);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (59, 200, 3);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (60, 2000, 4);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (61, 140, 5);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (62, 240, 6);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (63, 340, 7);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (64, 390, 8);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (65, 380, 1);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (66, 310, 2);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (67, 230, 3);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (68, 100, 4);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (69, 110, 5);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (70, 115, 6);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (71, 120, 7);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (72, 125, 8);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (73, 130, 1);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (74, 140, 2);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (75, 150, 3);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (76, 160, 4);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (77, 180, 5);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (78, 200, 6);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (79, 150, 7);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (80, 170, 8);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (81, 190, 1);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (82, 90, 2);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (83, 80, 3);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (84, 70, 4);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (85, 70, 5);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (86, 70, 6);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (87, 20, 7);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (88, 10, 8);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (89, 110, 1);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (90, 140, 2);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (91, 120, 3);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (92, 150, 4);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (93, 100, 5);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (94, 95, 6);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (95, 230, 7);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (96, 235, 8);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (97, 400, 1);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (98, 380, 2);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (99, 390, 3);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (100, 370, 4);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (101, 250, 5);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (102, 333, 6);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (103, 210, 7);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (104, 150, 8);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (105, 160, 1);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (106, 180, 2);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (107, 555, 3);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (108, 500, 4);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (109, 160, 5);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (110, 180, 6);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (111, 190, 7);

INSERT INTO planningunitelement(id,plannedminutes,ressourcegroup_id)
VALUES (112, 200, 8);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('PlanningUnitElement_id', 113);


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

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('PlanningUnit_PlanningUnitElement_id', 113);

