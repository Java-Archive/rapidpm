INSERT INTO
        mandantengruppe(id, mandantengruppe) VALUES (1, 'RapidPM');

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('Mandantengruppe_id', 1);


INSERT INTO
        benutzergruppe(id, gruppenname)VALUES (1, 'Administrators');
INSERT INTO
        benutzergruppe(id, gruppenname)VALUES (2, 'Users');

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('BenutzerGruppe_id', 3);


INSERT INTO
        benutzerwebapplikation(id, webappname)VALUES (1, 'RapidPM');

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('BenutzerWebapplikation_id', 1);


INSERT INTO
        benutzer(
                id, active, email, failedlogins, hidden, lastlogin, login, passwd,
                validfrom, validuntil, benutzergruppe_id, benutzerwebapplikation_id,
                mandantengruppe_id)
        VALUES (1, true , 'administrator@rapidpm.org', 0, false , '2012-11-22', 'administrator', 'geheim',
                '2012-11-22',
                '2013-11-22', 1, 1,1);

INSERT INTO
        benutzer(
                id, active, email, failedlogins, hidden, lastlogin, login, passwd,
                validfrom, validuntil, benutzergruppe_id, benutzerwebapplikation_id,
                mandantengruppe_id)
        VALUES (2, true , 'sven.ruppert@rapidpm.org', 0, false , '2012-11-22', 'sven.ruppert', 'geheim',
                '2012-11-22',
                '2013-11-22', 1, 1,1);

INSERT INTO pk_gen(gen_key, gen_value) VALUES ('Benutzer_id', 1);
