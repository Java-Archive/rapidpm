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
        ressourcegroup(id, name, costperhour)
        VALUES (1, 'GF', 125.0);

INSERT INTO
        ressourcegroup(id, name, costperhour)
        VALUES (2, 'Multiprojekt Manager', 125.0);

INSERT INTO
        ressourcegroup(id, name, costperhour)
        VALUES (3, 'Projektleiter / Scrum Master', 75.0);

INSERT INTO
        ressourcegroup(id, name, costperhour)
        VALUES (4, 'Senior Projektmitarbeiter', 65.0);

INSERT INTO
        ressourcegroup(id, name, costperhour)
        VALUES (5, 'Junior Projektmitarbeiter', 45.0);

INSERT INTO
        ressourcegroup(id, name, costperhour)
        VALUES (6, 'Aushilfe / stud. Projektmitarbeiter', 25.0);

INSERT INTO
        ressourcegroup(id, name, costperhour)
        VALUES (6, 'externe Ressource', 90.0);

INSERT INTO
        ressourcegroup(id, name, costperhour)
        VALUES (6, 'backoffice', 0.0);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('RessourceGroup_id', 6);
