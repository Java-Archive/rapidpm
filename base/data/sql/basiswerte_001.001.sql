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
