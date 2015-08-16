package org.rapidpm.persistence;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 11.01.12
 * Time: 14:27
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.bewegungsdaten.RegistrationDAO;
import org.rapidpm.persistence.prj.bewegungsdaten.RegistrationStatusDAO;
import org.rapidpm.persistence.prj.bewegungsdaten.anfragen.KontaktAnfrageDAO;
import org.rapidpm.persistence.prj.bewegungsdaten.anfragen.ProjektanfrageDAO;
import org.rapidpm.persistence.prj.book.BuchDAO;
import org.rapidpm.persistence.prj.book.BuchKapitelDAO;
import org.rapidpm.persistence.prj.book.kommentar.BuchKapitelKommentarDAO;
import org.rapidpm.persistence.prj.book.kommentar.BuchKommentarDAO;
import org.rapidpm.persistence.prj.book.kommentar.BuchSeitenKommentarDAO;
import org.rapidpm.persistence.prj.projectmanagement.ProjectDAO;
import org.rapidpm.persistence.prj.projectmanagement.ProjectNameDAO;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProjectDAO;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitDAO;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElementDAO;
import org.rapidpm.persistence.prj.stammdaten.address.*;
import org.rapidpm.persistence.prj.stammdaten.kommunikation.*;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.*;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroupDAO;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.metadata.AusbildungseinheitDAO;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.metadata.VerwaltungseinheitDAO;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.metadata.WirtschaftseinheitDAO;
import org.rapidpm.persistence.prj.stammdaten.person.*;
import org.rapidpm.persistence.prj.stammdaten.web.WebDomainDAO;
import org.rapidpm.persistence.prj.stammdaten.web.WebDomainKlassifizierungDAO;
import org.rapidpm.persistence.prj.stammdaten.web.WebDomainMetaDataDAO;
import org.rapidpm.persistence.prj.textelement.TextElementDAO;
import org.rapidpm.persistence.system.security.*;
import org.rapidpm.persistence.system.security.berechtigungen.BerechtigungDAO;
import org.rapidpm.persistence.system.security.berechtigungen.RolleDAO;

public class DaoFactory {
    private static final Logger logger = Logger.getLogger(DaoFactory.class);
    private OrientGraph orientDB;

    public DaoFactory(final String databaseName) {
       orientDB = new OrientGraph("plocal:orient/"+databaseName);
       //orientDB = new OrientGraph("remote:localhost/"+databaseName, "root", "admin");
    }

    public OrientGraph getOrientDB() {
        return orientDB;
    }

    public void setOrientDB(OrientGraph orientDB) {
        this.orientDB = orientDB;
    }

    // pkg logging

    //pkg security

    public BenutzerGruppeDAO getBenutzerGruppeDAO() {
        return new BenutzerGruppeDAO(orientDB);
    }

    public BenutzerWebapplikationDAO getBenutzerWebapplikationDAO() {
        return new BenutzerWebapplikationDAO(orientDB);
    }

    public BenutzerDAO getBenutzerDAO() {
        return new BenutzerDAO(orientDB);
    }

    public MandantengruppeDAO getMandantengruppeDAO() {
        return new MandantengruppeDAO(orientDB);
    }

    public PlannedProjectDAO getPlannedProjectDAO() {
        return new PlannedProjectDAO(orientDB);
    }


    public NewPasswdRequestDAO getNewPasswdRequestDAO() {
        return new NewPasswdRequestDAO(orientDB);
    }

    public PlanningUnitElementDAO getPlanningUnitElementDAO() {
        return new PlanningUnitElementDAO(orientDB);
    }

    public RolleDAO getRolleDAO() {
        return new RolleDAO(orientDB);
    }

    public BerechtigungDAO getBerechtigungDAO() {
        return new BerechtigungDAO(orientDB);
    }

    //pkg webapp
    //

    //pkg webapp

    public OrganisationseinheitDAO getOrganisationseinheitDAO() {
        return new OrganisationseinheitDAO(orientDB);
    }


    public WebDomainDAO getWebDomainDAO() {
        return new WebDomainDAO(orientDB);
    }

    // pkg address
    //    public AdressDAO getAdressDAO(){}

    public AddressKlassifizierungDAO getAddressKlassifizierungDAO() {
        return new AddressKlassifizierungDAO(orientDB);
    }

    public AdresseDAO getAdresseDAO() {
        return new AdresseDAO(orientDB);
    }


    public StateDAO getStateDAO() {
        return new StateDAO(orientDB);
    }

    public StateKlassifizierungDAO getStateKlassifizierungDAO() {
        return new StateKlassifizierungDAO(orientDB);
    }

    public LandDAO getLandDAO() {
        return new LandDAO(orientDB);
    }


    //book
    public BuchDAO getBuchDAO() {
        return new BuchDAO(orientDB);
    }

    public BuchKapitelDAO getBuchKapitelDAO() {
        return new BuchKapitelDAO(orientDB);
    }

    public BuchKommentarDAO getBuchKommentarDAO() {
        return new BuchKommentarDAO(orientDB);
    }

    public BuchKapitelKommentarDAO getBuchKapitelKommentarDAO() {
        return new BuchKapitelKommentarDAO(orientDB);
    }

    public BuchSeitenKommentarDAO getBuchSeitenKommentarDAO() {
        return new BuchSeitenKommentarDAO(orientDB);
    }



    public ProjectDAO getProjectDAO() {
        return new ProjectDAO(orientDB);
    }

    public ProjectNameDAO getProjectNameDAO() {
        return new ProjectNameDAO(orientDB);
    }



    //pkg Kommunikation

    public KommunikationsServiceDAO getKommunikationsServiceDAO() {
        return new KommunikationsServiceDAO(orientDB);
    }

    public KommunikationsServiceKlassifizierungDAO getKommunikationsServiceKlassifizierungDAO() {
        return new KommunikationsServiceKlassifizierungDAO(orientDB);
    }

    public KommunikationsServiceUIDDAO getKommunikationServiceUIDDAO() {
        return new KommunikationsServiceUIDDAO(orientDB);
    }

    public KommunikationsServiceUIDPartDAO getKommunikationServiceUIDPartDAO() {
        return new KommunikationsServiceUIDPartDAO(orientDB);
    }

    public KommunikationsServiceUIDPartKlassifikationDAO getKommunikationsServiceUIDPartKlassifikationDAO() {
        return new KommunikationsServiceUIDPartKlassifikationDAO(orientDB);
    }


    //pkg msgCenter


    //pkg intern
    public RessourceGroupDAO getRessourceGroupDAO(){
        return new RessourceGroupDAO(orientDB);
    }

    //pkg organisationseinheiten

    public BrancheDAO getBrancheDAO() {
        return new BrancheDAO(orientDB);
    }

    public BrancheAssocDAO getBranchenAssocDAO() {
        return new BrancheAssocDAO(orientDB);
    }

    public BranchenKlassifizierungDAO getBranchenKlassifizierungDAO() {
        return new BranchenKlassifizierungDAO(orientDB);
    }

    public GesellschaftsformDAO getGesellschaftsformDAO() {
        return new GesellschaftsformDAO(orientDB);
    }

    public OrganisationseinheitMetaDataDAO getOrganisationseinheitMetaDataDAO() {
        return new OrganisationseinheitMetaDataDAO(orientDB);
    }

    public AusbildungseinheitDAO getAusbildungseinheitDAO() {
        return new AusbildungseinheitDAO(orientDB);
    }

    public VerwaltungseinheitDAO getVerwaltungseinheitDAO() {
        return new VerwaltungseinheitDAO(orientDB);
    }

    public WirtschaftseinheitDAO getWirtschaftseinheitDAO() {
        return new WirtschaftseinheitDAO(orientDB);
    }


    public TaetigkeitsfeldKlassifizierungDAO getTaetigkeitsklassifizierungDAO() {
        return new TaetigkeitsfeldKlassifizierungDAO(orientDB);
    }

    public TaetigkeitsfeldDAO getTaetigkeitsfeldDAO() {
        return new TaetigkeitsfeldDAO(orientDB);
    }

    public TaetigkeitsfeldAssocDAO getTaetigkeitsfeldAssocDAO() {
        return new TaetigkeitsfeldAssocDAO(orientDB);
    }

    public PositionDAO getPositionDAO() {
        return new PositionDAO(orientDB);
    }

    //pkg person

    public PersonDAO getPersonDAO() {
        return new PersonDAO(orientDB);
    }

    public PersonenNameDAO getPersonenNameDAO() {
        return new PersonenNameDAO(orientDB);
    }

    public AnredeDAO getAnredeDAO() {
        return new AnredeDAO(orientDB);
    }

    public GeschlechtDAO getGeschlechtDAO() {
        return new GeschlechtDAO(orientDB);
    }

    public NamensKlassifizierungDAO getNamensKlassifizierungDAO() {
        return new NamensKlassifizierungDAO(orientDB);
    }

    public TitelDAO getTitelDAO() {
        return new TitelDAO(orientDB);
    }


    //pgk web//webdomains

    public WebDomainKlassifizierungDAO getWebDomainKlassifizierungDAO() {
        return new WebDomainKlassifizierungDAO(orientDB);
    }

    public WebDomainMetaDataDAO getWebDomainMetaDataDAO() {
        return new WebDomainMetaDataDAO(orientDB);
    }

    public PlanningUnitDAO getPlanningUnitDAO(){
        return new PlanningUnitDAO(orientDB);
    }


    //pkg Bewegungsdaten
    public RegistrationDAO getRegistrationDAO() {
        return new RegistrationDAO(orientDB);
    }

    public RegistrationStatusDAO getRegistrationStatusDAO() {
        return new RegistrationStatusDAO(orientDB);
    }

    public KontaktAnfrageDAO getKontaktAnfrageDAO() {
        return new KontaktAnfrageDAO(orientDB);
    }

    public ProjektanfrageDAO getProjektanfrageDAO() {
        return new ProjektanfrageDAO(orientDB);
    }

    public TextElementDAO getTextElementDAO() {
        return new TextElementDAO((orientDB));
    }

}
