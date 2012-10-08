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
        VALUES (1, true , 'sven.ruppert@rapidpm.org', 0, false , '2012-09-11', 'sven.ruppert', 'geheim',
                '2012-09-11',
                '2013-09-11', 1, 1,1);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('Benutzer_id', 1);

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

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('RessourceGroup_id', 8);





INSERT INTO
        plannedproject(id, active, fakturierbar, info,projektname,creator_id,mandantengruppe_id,responsibleperson_id)
        VALUES (1, true, true, 'erstes testprojekt', 'Projekt Nr 1', 1, 1,1);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('PlannedProject_id', 1);

INSERT INTO
        planningstatus(id, name, ordernumber)
        VALUES (1, 'planningstatus 1',1);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('PlanningStatus_id', 1);

INSERT INTO
        issuepriority(id, prio, priorityname)
        VALUES (1, 1 ,'Minor');

INSERT INTO
        issuepriority(id, prio, priorityname)
        VALUES (2, 2 ,'Trivial');

INSERT INTO
        issuepriority(id, prio, priorityname)
        VALUES (3, 3 ,'Major');

INSERT INTO
        issuepriority(id, prio, priorityname)
        VALUES (4, 4 ,'Critical');

INSERT INTO
        issuepriority(id, prio, priorityname)
        VALUES (5, 5 ,'Blocker');

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('IssuePriority_id', 5);

INSERT INTO
        issuestatus(id, statusname)
        VALUES (1, 'OnHold');

INSERT INTO
        issuestatus(id, statusname)
        VALUES (2, 'Closed');

INSERT INTO
        issuestatus(id, statusname)
        VALUES (3, 'Resolved');

INSERT INTO
        issuestatus(id, statusname)
        VALUES (4, 'InProgress');

INSERT INTO
        issuestatus(id, statusname)
        VALUES (5, 'Open');

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('IssueStatus_id', 5);

INSERT INTO
        issuebase(id,storypoints,issueassignee_id,issuepriority_id,issuereporter_id,issuestatus_id)
        VALUES (1,6,1,1,1,1);

INSERT INTO
        issuebase(id,storypoints,issueassignee_id,issuepriority_id,issuereporter_id,issuestatus_id)
        VALUES (2,6,1,4,1,1);

INSERT INTO
        issuebase(id,storypoints,issueassignee_id,issuepriority_id,issuereporter_id,issuestatus_id)
        VALUES (3,3,1,3,1,2);

INSERT INTO
        issuebase(id,storypoints,issueassignee_id,issuepriority_id,issuereporter_id,issuestatus_id)
        VALUES (4,5,1,3,1,3);

INSERT INTO
        issuebase(id,storypoints,issueassignee_id,issuepriority_id,issuereporter_id,issuestatus_id)
        VALUES (5,3,1,2,1,4);

INSERT INTO
        issuebase(id,storypoints,issueassignee_id,issuepriority_id,issuereporter_id,issuestatus_id)
        VALUES (6,1,1,1,1,3);

INSERT INTO
        issuebase(id,storypoints,issueassignee_id,issuepriority_id,issuereporter_id,issuestatus_id)
        VALUES (7,8,1,4,1,2);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('IssueBase_id', 7);

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

INSERT INTO planningunit(id,ordernumber,planningunitname,issuebase_id,parent_id,planningstatus_id,responsibleperson_id)
VALUES (1,1,'Vorbereitungen',1,null,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,issuebase_id,parent_id,planningstatus_id,responsibleperson_id)
VALUES (2,2,'Erstkontakt vor Ort',2,1,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,issuebase_id,parent_id,planningstatus_id,responsibleperson_id)
VALUES (3,3,'Person A kontaktieren',3,2,1,1);

INSERT INTO planningunit(id,ordernumber,planningunitname,issuebase_id,parent_id,planningstatus_id,responsibleperson_id)
VALUES (4,4,'Person B kontaktieren',4,2,1,2);

INSERT INTO planningunit(id,ordernumber,planningunitname,issuebase_id,parent_id,planningstatus_id,responsibleperson_id)
VALUES (5,5,'Gesprächsvorbereitung',5,1,1,2);

INSERT INTO planningunit(id,ordernumber,planningunitname,issuebase_id,parent_id,planningstatus_id,responsibleperson_id)
VALUES (6,6,'Präsentation',6,1,1,2);

INSERT INTO planningunit(id,ordernumber,planningunitname,issuebase_id,parent_id,planningstatus_id,responsibleperson_id)
VALUES (7,7,'Gesprächsbestätigung',7,1,1,2);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('PlanningUnit_id', 7);

INSERT INTO plannedproject_planningunit(plannedproject_id,planningunits_id)
VALUES (1, 1);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('PlannedProject_PlanningUnit_id', 1);

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

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('PlanningUnit_PlanningUnit_id', 6);

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

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('PlanningUnitElement_id', 56);

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





