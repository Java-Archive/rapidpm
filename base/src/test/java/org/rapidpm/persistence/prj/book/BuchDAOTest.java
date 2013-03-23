package org.rapidpm.persistence.prj.book;

/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: 14.02.11
 * Time: 18:37
 * To change this template use File | Settings | File Templates.
 */

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.prj.BaseDAOTest;
import org.rapidpm.persistence.prj.book.kommentar.BuchKapitelKommentar;
import org.rapidpm.persistence.prj.book.kommentar.BuchKommentar;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BuchDAOTest extends BaseDAOTest {
    private static final Logger logger = Logger.getLogger(BuchDAOTest.class);

    @Test
    public void testBuch001() throws Exception {
        try {
            final EntityManager entityManager = daoFactory.getEntityManager();
            final EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            final BuchDAO buchDAO = daoFactory.getBuchDAO();

            final Buch buch = new Buch();

//            final Benutzer benutzer = daoFactory.getBenutzerDAO().loadBenutzer("sven.ruppert", "Netzwerk Draht");
//            buch.setAutorenliste(new ArrayList<Benutzer>(Arrays.asList(benutzer)));
//            buch.setLeserliste(new ArrayList<Benutzer>(Arrays.asList(benutzer)));

            buch.setSummary("Das ist das Summary..");
            buch.setTitel("Das ist der Titel");
            buch.setUntertitel("Das ist der Untertitel");
            buch.setVersion("Version 0001");

            final BuchKommentar buchKommentar = new BuchKommentar();
            buchKommentar.setDatum(new Date());
            buchKommentar.setKommentar("Ein Buchkommentar");
//            buchKommentar.setKommentator(benutzer);
            final List<BuchKommentar> buchKommentarList = new ArrayList<BuchKommentar>();
            buchKommentarList.add(buchKommentar);
            buch.setKommentarliste(buchKommentarList);


            final List<BuchKapitel> buchKapitelList = new ArrayList<BuchKapitel>();
            buch.setBuchKapitelListe(buchKapitelList);


            final BuchKapitel buchKapitel = new BuchKapitel();
            buchKapitel.setFreigeschaltet(true);
            final BuchKapitelKommentar buchKapitelKommentar = new BuchKapitelKommentar();
            buchKapitelKommentar.setDatum(new Date());
            buchKapitelKommentar.setKommentar("BuchKapitelKommentar");
//            buchKapitelKommentar.setKommentator(benutzer);

            final List<BuchKapitelKommentar> buchKapitelKommentarList = new ArrayList<BuchKapitelKommentar>();
            buchKapitelKommentarList.add(buchKapitelKommentar);
            buchKapitel.setKapitelkommentarliste(buchKapitelKommentarList);
//
//            final List<BuchSeite> buchSeiteList = new ArrayList<BuchSeite>();
//
//            final BuchSeite buchSeite = new BuchSeite();
//
//            final List<BuchAbsatz> buchAbsatzList = new ArrayList<BuchAbsatz>();
//            final BuchAbsatz buchAbsatz = new BuchAbsatz();
//            buchAbsatz.setAbsatznummer(1);
//            buchAbsatz.setFreigeschaltet(true);
//
//            final BuchAbsatzKommentar buchAbsatzKommentar = new BuchAbsatzKommentar();
//            buchAbsatzKommentar.setDatum(new Date());
//            buchAbsatzKommentar.setKommentar("blabla");
////            buchAbsatzKommentar.setKommentator(benutzer);
//            final List<BuchAbsatzKommentar> buchAbsatzKommentarList = new ArrayList<BuchAbsatzKommentar>();
//            buchAbsatzKommentarList.add(buchAbsatzKommentar);
//
//            buchAbsatz.setKommentarliste(buchAbsatzKommentarList);
//
//            buchAbsatz.setText("Das ist der AbsatzText...");
//            buchAbsatzList.add(buchAbsatz);
//            buchSeite.setAbsatzliste(buchAbsatzList);
//
//            buchSeite.setFreigeschaltet(true);
//            final List<BuchSeitenFussnote> buchSeitenFussnoteListe = new ArrayList<BuchSeitenFussnote>();
//
//            final BuchSeitenFussnote buchSeitenFussnote = new BuchSeitenFussnote();
//            buchSeitenFussnote.setFussnotentext("Fussnotentext");
//            buchSeitenFussnote.setFussnotenzeichen("HJHJ");
//            buchSeitenFussnoteListe.add(buchSeitenFussnote);
//            buchSeite.setFusnotenliste(buchSeitenFussnoteListe);
//
//
//            final List<BuchSeitenKommentar> buchSeitenKommentarListe = new ArrayList<BuchSeitenKommentar>();
//            final BuchSeitenKommentar buchSeitenKommentar = new BuchSeitenKommentar();
//            buchSeitenKommentar.setDatum(new Date());
//            buchSeitenKommentar.setKommentar("Das ist der Kommentar");
////            buchSeitenKommentar.setKommentator(benutzer);
//            buchSeitenKommentarListe.add(buchSeitenKommentar);
//
//            buchSeite.setKommentarliste(buchSeitenKommentarListe);
//            buchSeite.setSeitennummer(1);
//            buchSeiteList.add(buchSeite);
//
//
//            buchKapitel.setSeitenliste(buchSeiteList);
//            buchKapitel.setUeberschrift("KapitelÜberschrift");
//            buchKapitel.setKapitelnummer(1);
//            buchKapitel.setUntertitel("Kapiteluntertitel..");

            buch.getBuchKapitelListe().add(buchKapitel);


            entityManager.persist(buch);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("e = " + e);
        }
    }
}
