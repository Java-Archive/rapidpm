package org.rapidpm;

/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: 09.01.11
 * Time: 06:52
 * To change this template use File | Settings | File Templates.
 */

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.system.security.*;
import org.rapidpm.persistence.system.security.berechtigungen.Berechtigung;
import org.rapidpm.persistence.system.security.berechtigungen.BerechtigungDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class DBMain {
    private static final Logger logger = Logger.getLogger(DBMain.class);

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("baseormJDBC");
        final EntityManager entityManager = emf.createEntityManager();
        final DaoFactory daoFactoryFactory = new DaoFactory();
        daoFactoryFactory.setEntityManager(entityManager);
        final EntityTransaction transaction = entityManager.getTransaction();
        try {
            Class.forName("org.postgresql.Driver");
            final String url = "jdbc:postgresql://ws.neoscio.de:5432/neoscio_dev";
            Properties props = new Properties();
            props.setProperty("user", "ruppert");
            props.setProperty("password", "FE90tz");
            final Connection conn = DriverManager.getConnection(url, props);
            convertBenutzer(daoFactoryFactory, conn);
//            convertSearchQuery(daoFactory, conn);
//            convertBranche(daoFactory, conn);
        } catch (SQLException e) {
            logger.error(e);
        } catch (Exception e) {
            logger.error(e);
        }
        transaction.begin();
        transaction.rollback();
        //transaction.commit();

    }

//    private static void convertBranche(final DAO daoFactory, final Connection conn) throws SQLException {
//        final Statement statement = conn.createStatement();
//        final ResultSet resultSet = statement.executeQuery("select * from branche");
//        while (resultSet.next()) {
//            final Branche branche = new Branche();
//            branche.setBeschreibung(resultSet.getString("wzversion"));
//            branche.setBranchenSchluessel(resultSet.getString("branchenschluessel"));
//            branche.setISIC(resultSet.getString("isic"));
//            branche.setWZVersion(resultSet.getString("wzversion"));
//            daoFactory.saveOrUpdate(branche);
//        }
//    }

//    private static void convertSearchQuery(final DAO DAO, final Connection conn) throws SQLException {
    //        final Statement statement = conn.createStatement();
    //        final SearchQueryDAO searchQueryDAO = DAO.getSearchQueryDAO();
    //        final ResultSet resultSet = statement.executeQuery("select * from search_query");
    //        while(resultSet.next()){
    //            final SearchQuery q = new SearchQuery();
    //            final String userlogin = resultSet.getString("userlogin");
    //            final BenutzerDAO benutzerDAO = DAO.getBenutzerDAO();
    //            final List<Benutzer> benutzerList = benutzerDAO.loadBenutzerForLogin(userlogin);
    //            if(benutzerList.size() == 1){
    //                q.setBenutzer(benutzerList.get(0));
    //            }else{
    //                //
    //            }
    //            q.setCreated(resultSet.getDate("created"));
    //            q.setDomains(resultSet.getString("domains"));
    //            q.setKeywords(resultSet.getString("keywords"));
    //            q.setMimeTypes(resultSet.getString("mime_types"));
    //            q.setShowSummary(resultSet.getBoolean("show_summary"));
    //            q.setSuchmaschinenmodul(resultSet.getString("suchmaschinenmodul"));
    //            q.setUserlogin(resultSet.getString("userlogin"));
    //            searchQueryDAO.persist(q);
    //        }
//    }

    //TODO noch nicht vollständig - Benutzermapping noch falsch bzw mehrdeutig
    private static void convertSearchQueries(DAO DAO, Connection conn) throws SQLException {
        //        final Statement statement = conn.createStatement();
        //        final BenutzerDAO benutzerDAO = DAO.getBenutzerDAO();
        //        final SearchQueryDAO searchQueryDAO = DAO.getSearchQueryDAO();
        //        final ResultSet resultSet = statement.executeQuery("select * from search_query");
        //        while(resultSet.next()){
        //            final SearchQuery q = new SearchQuery();
        //            final String userlogin = resultSet.getString("userlogin");
        //            final List<Benutzer> benutzerList = benutzerDAO.loadBenutzerForLogin(userlogin);
        //            if(benutzerList.size() == 1){
        //
        //                q.setBenutzer(benutzerList.get(0));
        //            }else{
        //                //
        //            }
        //            q.setCreated(resultSet.getDate("created"));
        //            q.setDomains(resultSet.getString("domains"));
        //            q.setKeywords(resultSet.getString("keywords"));
        //            q.setMimeTypes(resultSet.getString("mime_types"));
        //            q.setShowSummary(resultSet.getBoolean("show_summary"));
        //            q.setSuchmaschinenmodul(resultSet.getString("suchmaschinenmodul"));
        //            q.setUserlogin(resultSet.getString("userlogin"));
        //            searchQueryDAO.persist(q);
        //        }
    }

    private static void convertBenutzer(final DaoFactory daoFactoryFactory, final Connection conn) throws SQLException {
        final Statement statement = conn.createStatement();
        final
        ResultSet
                resultSet =
                statement.executeQuery("select b.login, b.active, b.hidden, b.passwd, b.valid_from, b.valid_until,bw.webapp_name, bg.gruppenname, " + "m.mandantengruppe from benutzer b , mandantengruppe m , benutzer_gruppe bg, " + "benutzer_webapplikation bw  where b.benutzer_gruppe_id = bg.id and  b.benutzer_webapplikation_id = bw.id   and b.mandantengruppe_id = m.id");

        final BenutzerWebapplikationDAO benutzerWebapplikationDAO = daoFactoryFactory.getBenutzerWebapplikationDAO();
        final BenutzerGruppeDAO benutzerGruppeDAO = daoFactoryFactory.getBenutzerGruppeDAO();
        final MandantengruppeDAO mandantengruppeDAO = daoFactoryFactory.getMandantengruppeDAO();
        final BenutzerDAO benutzerDAO = daoFactoryFactory.getBenutzerDAO();
        final BerechtigungDAO berechtigungDAO = daoFactoryFactory.getBerechtigungDAO();

        while (resultSet.next()) {
            final Benutzer benutzer = new Benutzer();
            //            benutzer.setCreated(new Date());
            //            benutzer.setModified(new Date());
            benutzer.setLastLogin(new Date());
            benutzer.setFailedLogins(0);
            benutzer.setLogin(resultSet.getString("login"));
            benutzer.setActive(resultSet.getBoolean("active"));
            benutzer.setHidden(resultSet.getBoolean("hidden"));
            benutzer.setPasswd(resultSet.getString("passwd"));
            benutzer.setValidFrom(resultSet.getDate("valid_from"));
            benutzer.setValidUntil(resultSet.getDate("valid_until"));

            final String webapp_name = resultSet.getString("webapp_name");
            final BenutzerWebapplikation webApp = benutzerWebapplikationDAO.loadBenutzerWebapplikation(webapp_name);
            final BenutzerGruppe benutzerGruppe = benutzerGruppeDAO.loadBenutzerGruppeByName(resultSet.getString("gruppenname"));
            final String mandantengruppe1 = resultSet.getString("mandantengruppe");
            final Mandantengruppe mandantengruppe = mandantengruppeDAO.loadMandantengruppe(mandantengruppe1);

            if (mandantengruppe == null) {
                System.out.println("mandantengruppe = " + mandantengruppe1);
            }
            if (webApp == null) {
                System.out.println("webapp_name = " + webapp_name);
            }


            benutzer.setBenutzerGruppe(benutzerGruppe);
            benutzer.setBenutzerWebapplikation(webApp);
            benutzer.setMandantengruppe(mandantengruppe);
            //
            //benutzer.setBerechtigungen(new ArrayList<Berechtigung>());
            if (mandantengruppe.getMandantengruppe().equals("NeoScioPortal")) {
                benutzer.getBerechtigungen().addAll(berechtigungDAO.loadAllEntities());
            } else {
                benutzer.getBerechtigungen().add(berechtigungDAO.loadBerechtigung(mandantengruppe.getMandantengruppe()));
            }


            daoFactoryFactory.saveOrUpdate(benutzer);
        }
    }

    private static class SQLBenutzer {

    }
}
