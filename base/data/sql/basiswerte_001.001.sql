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
        plannedproject(id, active, fakturierbar, info,projektname,projekttoken,creator_id,mandantengruppe_id,
        responsibleperson_id)
        VALUES (1, true, true, 'erstes testprojekt', 'Projekt Nr 1','PRO1', 1, 1,1);

INSERT INTO
        plannedproject(id, active, fakturierbar, info,projektname,projekttoken,creator_id,mandantengruppe_id,
        responsibleperson_id)
        VALUES (2, true, true, 'zweites testprojekt', 'Projekt Nr 2', 'PRO2', 1, 1,1);

INSERT INTO
        plannedproject(id, active, fakturierbar, info,projektname,projekttoken,creator_id,mandantengruppe_id,
        responsibleperson_id)
        VALUES (3, true, true, 'drittes testprojekt', 'Projekt Nr 3','PRO3', 1, 1,1);

INSERT INTO
        plannedproject(id, active, fakturierbar, info,projektname,projekttoken,creator_id,mandantengruppe_id,
        responsibleperson_id)
        VALUES (4, true, true, 'viertes testprojekt', 'Projekt Nr 4','PRO4', 1, 1,1);

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


INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (1, '2012-08-08', 'Comment 1', 4);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (2, '2012-08-14', 'Comment 2', 3);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (3, '2012-08-28', 'Comment 3', 2);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (4, '2012-09-02', 'Comment 4', 5);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (5, '2012-09-13', 'Comment 5', 5);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (6, '2012-09-20', 'Comment 6', 4);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (7, '2012-09-29', 'Comment 7', 4);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (8, '2012-10-19', 'Comment 8', 3);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (9, '2012-10-20', 'Comment 9', 3);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (10, '2012-11-11', 'Comment 10', 2);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (11, '2012-11-12', 'Comment 11', 2);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (12, '2012-11-13', 'Comment 12', 3);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (13, '2012-11-14', 'Comment 13', 2);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (14, '2012-11-15', 'Comment 14', 5);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (15, '2012-11-16', 'Comment 15', 4);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (16, '2012-09-29', 'Comment 16', 4);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (17, '2012-10-19', 'Comment 17', 5);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (18, '2012-10-20', 'Comment 18', 3);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (19, '2012-11-11', 'Comment 19', 2);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (20, '2012-11-12', 'Comment 20', 2);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (21, '2012-11-13', 'Comment 21', 3);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (22, '2012-11-14', 'Comment 22', 2);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (23, '2012-11-15', 'Comment 23', 5);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (24, '2012-11-16', 'Comment 24', 4);

INSERT INTO issuecomment (id, created, text, creator_id)
VALUES (25, '2012-09-29', 'Comment 25', 4);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('IssueComment_id', 26);

INSERT INTO issuetestcase (id, text)
VALUES (1, 'Testcase 1');

INSERT INTO issuetestcase (id, text)
VALUES (2, 'Testcase 2');

INSERT INTO issuetestcase (id, text)
VALUES (3, 'Testcase 3');

INSERT INTO issuetestcase (id, text)
VALUES (4, 'Testcase 4');

INSERT INTO issuetestcase (id, text)
VALUES (5, 'Testcase 5');

INSERT INTO issuetestcase (id, text)
VALUES (6, 'Testcase 6');

INSERT INTO issuetestcase (id, text)
VALUES (7, 'Testcase 7');

INSERT INTO issuetestcase (id, text)
VALUES (8, 'Testcase 8');

INSERT INTO issuetestcase (id, text)
VALUES (9, 'Testcase 9');

INSERT INTO issuetestcase (id, text)
VALUES (10, 'Testcase 10');

INSERT INTO issuetestcase (id, text)
VALUES (11, 'Testcase 11');

INSERT INTO issuetestcase (id, text)
VALUES (12, 'Testcase 12');

INSERT INTO issuetestcase (id, text)
VALUES (13, 'Testcase 13');

INSERT INTO issuetestcase (id, text)
VALUES (14, 'Testcase 14');

INSERT INTO issuetestcase (id, text)
VALUES (15, 'Testcase 15');

INSERT INTO issuetestcase (id, text)
VALUES (16, 'Testcase 16');

INSERT INTO issuetestcase (id, text)
VALUES (17, 'Testcase 17');

INSERT INTO issuetestcase (id, text)
VALUES (18, 'Testcase 18');

INSERT INTO issuetestcase (id, text)
VALUES (19, 'Testcase 19');

INSERT INTO issuetestcase (id, text)
VALUES (20, 'Testcase 20');

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('IssueTestCase_id', 21);
