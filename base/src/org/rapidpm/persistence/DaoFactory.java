package org.rapidpm.persistence;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 11.01.12
 * Time: 14:27
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.bewegungsdaten.RegistrationDAO;
import org.rapidpm.persistence.prj.bewegungsdaten.RegistrationStatusDAO;
import org.rapidpm.persistence.prj.bewegungsdaten.anfragen.KontaktAnfrageDAO;
import org.rapidpm.persistence.prj.bewegungsdaten.anfragen.ProjektanfrageDAO;
import org.rapidpm.persistence.prj.bewegungsdaten.msgcenter.MessageDAO;
import org.rapidpm.persistence.prj.bewegungsdaten.msgcenter.msg.PersonalMessageDAO;
import org.rapidpm.persistence.prj.book.BuchDAO;
import org.rapidpm.persistence.prj.book.BuchKapitelDAO;
import org.rapidpm.persistence.prj.book.BuchSeiteDAO;
import org.rapidpm.persistence.prj.book.BuchSeitenFussnoteDAO;
import org.rapidpm.persistence.prj.book.kommentar.BuchKapitelKommentarDAO;
import org.rapidpm.persistence.prj.book.kommentar.BuchKommentarDAO;
import org.rapidpm.persistence.prj.book.kommentar.BuchSeitenKommentarDAO;
import org.rapidpm.persistence.prj.projectmanagement.ProjectDAO;
import org.rapidpm.persistence.prj.projectmanagement.ProjectNameDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueCommentDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriorityDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatusDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueTimeUnitDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;
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
import org.rapidpm.persistence.rohdaten.OntologieConnectionDAO;
import org.rapidpm.persistence.rohdaten.OntologieDAO;
import org.rapidpm.persistence.rohdaten.OntologieEntryDAO;
import org.rapidpm.persistence.system.logging.LogginEntityActionDAO;
import org.rapidpm.persistence.system.logging.LogginEntityEntryDAO;
import org.rapidpm.persistence.system.logging.LoggingEventEntryDAO;
import org.rapidpm.persistence.system.security.*;
import org.rapidpm.persistence.system.security.berechtigungen.BerechtigungDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DaoFactory {
    private static final Logger logger = Logger.getLogger(DaoFactory.class);
    private BaseDAO.EntityUtils entityUtils;

    public DaoFactory(final String persistenceUnitName) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        this.entityManager = emf.createEntityManager();
    }

    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public DaoFactory() {
    }

    // pkg logging

    public LoggingEventEntryDAO getLoggingEventEntryDAO() {
        return new LoggingEventEntryDAO(getEntityManager());
    }

    public LogginEntityEntryDAO getLogginEntityEntryDAO() {
        return new LogginEntityEntryDAO(getEntityManager());
    }

    public LogginEntityActionDAO getLogginEntityActionDAO() {
        return new LogginEntityActionDAO(getEntityManager());
    }

    //pkg security

    public BenutzerGruppeDAO getBenutzerGruppeDAO() {
        return new BenutzerGruppeDAO(getEntityManager());
    }

    public BenutzerWebapplikationDAO getBenutzerWebapplikationDAO() {
        return new BenutzerWebapplikationDAO(getEntityManager());
    }

    public BenutzerDAO getBenutzerDAO() {
        return new BenutzerDAO(getEntityManager());
    }

    public MandantengruppeDAO getMandantengruppeDAO() {
        return new MandantengruppeDAO(getEntityManager());
    }


    public NewPasswdRequestDAO getNewPasswdRequestDAO() {
        return new NewPasswdRequestDAO(getEntityManager());
    }


    public BerechtigungDAO getBerechtigungDAO() {
        return new BerechtigungDAO(getEntityManager());
    }

    //pkg webapp


    public OntologieDAO getOntologieDAO() {
        return new OntologieDAO(getEntityManager());
    }

    public OntologieConnectionDAO getOntologieConnectionDAO() {
        return new OntologieConnectionDAO(getEntityManager());
    }

    public OntologieEntryDAO getOntologieEntryDAO() {
        return new OntologieEntryDAO(getEntityManager());
    }

    //pkg webapp

    public OrganisationseinheitDAO getOrganisationseinheitDAO() {
        return new OrganisationseinheitDAO(getEntityManager());
    }


    public WebDomainDAO getWebDomainDAO() {
        return new WebDomainDAO(getEntityManager());
    }

    // pkg address
    //    public AdressDAO getAdressDAO(){}

    public AddressKlassifizierungDAO getAddressKlassifizierungDAO() {
        return new AddressKlassifizierungDAO(getEntityManager());
    }

    public AdresseDAO getAdresseDAO() {
        return new AdresseDAO(getEntityManager());
    }


    public StateDAO getStateDAO() {
        return new StateDAO(getEntityManager());
    }

    public StateKlassifizierungDAO getStateKlassifizierungDAO() {
        return new StateKlassifizierungDAO(getEntityManager());
    }

    public LandDAO getLandDAO() {
        return new LandDAO(getEntityManager());
    }


    //book
    public BuchDAO getBuchDAO() {
        return new BuchDAO(getEntityManager());
    }

    public BuchKapitelDAO getBuchKapitelDAO() {
        return new BuchKapitelDAO(getEntityManager());
    }

    public BuchSeiteDAO getBuchSeiteDAO() {
        return new BuchSeiteDAO(getEntityManager());
    }

    public BuchSeitenFussnoteDAO getBuchSeitenFussnoteDAO() {
        return new BuchSeitenFussnoteDAO(getEntityManager());
    }

    public BuchKommentarDAO getBuchKommentarDAO() {
        return new BuchKommentarDAO(getEntityManager());
    }

    public BuchKapitelKommentarDAO getBuchKapitelKommentarDAO() {
        return new BuchKapitelKommentarDAO(getEntityManager());
    }

    public BuchSeitenKommentarDAO getBuchSeitenKommentarDAO() {
        return new BuchSeitenKommentarDAO(getEntityManager());
    }


    //IssueTracking

    public IssueBaseDAO getIssueBaseDAO() {
        return new IssueBaseDAO(getEntityManager());
    }

    public IssueCommentDAO getIssueCommentDAO() {
        return new IssueCommentDAO(getEntityManager());
    }

    public ProjectDAO getProjectDAO() {
        return new ProjectDAO(getEntityManager());
    }

    public IssueTimeUnitDAO getTimeUnitDAO() {
        return new IssueTimeUnitDAO(getEntityManager());
    }

    public IssuePriorityDAO getIssuePriorityDAO() {
        return new IssuePriorityDAO(getEntityManager());
    }

    public IssueStatusDAO getIssueStatusDAO() {
        return new IssueStatusDAO(getEntityManager());
    }

    public ProjectNameDAO getProjectNameDAO() {
        return new ProjectNameDAO(getEntityManager());
    }

    public IssueTimeUnitDAO getIssueTimeUnitDAO() {
        return new IssueTimeUnitDAO(getEntityManager());
    }


    //pkg Kommunikation

    public KommunikationsServiceDAO getKommunikationsServiceDAO() {
        return new KommunikationsServiceDAO(getEntityManager());
    }

    public KommunikationsServiceKlassifizierungDAO getKommunikationsServiceKlassifizierungDAO() {
        return new KommunikationsServiceKlassifizierungDAO(getEntityManager());
    }

    public KommunikationsServiceUIDDAO getKommunikationServiceUIDDAO() {
        return new KommunikationsServiceUIDDAO(getEntityManager());
    }

    public KommunikationsServiceUIDPartDAO getKommunikationServiceUIDPartDAO() {
        return new KommunikationsServiceUIDPartDAO(getEntityManager());
    }

    public KommunikationsServiceUIDPartKlassifikationDAO getKommunikationsServiceUIDPartKlassifikationDAO() {
        return new KommunikationsServiceUIDPartKlassifikationDAO(getEntityManager());
    }


    //pkg msgCenter

    public MessageDAO getMessageDAO() {
        return new MessageDAO(getEntityManager());
    }

    public PersonalMessageDAO getPersonalMessageDAO() {
        return new PersonalMessageDAO(getEntityManager());
    }

    //pkg intern
    public RessourceGroupDAO getRessourceGroupDAO(){
        return new RessourceGroupDAO(getEntityManager());
    }

    //pkg organisationseinheiten

    public BrancheDAO getBrancheDAO() {
        return new BrancheDAO(getEntityManager());
    }

    public BrancheAssocDAO getBranchenAssocDAO() {
        return new BrancheAssocDAO(getEntityManager());
    }

    public BranchenKlassifizierungDAO getBranchenKlassifizierungDAO() {
        return new BranchenKlassifizierungDAO(getEntityManager());
    }

    public GesellschaftsformDAO getGesellschaftsformDAO() {
        return new GesellschaftsformDAO(getEntityManager());
    }

    public OrganisationseinheitMetaDataDAO getOrganisationseinheitMetaDataDAO() {
        return new OrganisationseinheitMetaDataDAO(getEntityManager());
    }

    public AusbildungseinheitDAO getAusbildungseinheitDAO() {
        return new AusbildungseinheitDAO(getEntityManager());
    }

    public VerwaltungseinheitDAO getVerwaltungseinheitDAO() {
        return new VerwaltungseinheitDAO(getEntityManager());
    }

    public WirtschaftseinheitDAO getWirtschaftseinheitDAO() {
        return new WirtschaftseinheitDAO(getEntityManager());
    }


    public TaetigkeitsfeldKlassifizierungDAO getTaetigkeitsklassifizierungDAO() {
        return new TaetigkeitsfeldKlassifizierungDAO(getEntityManager());
    }

    public TaetigkeitsfeldDAO getTaetigkeitsfeldDAO() {
        return new TaetigkeitsfeldDAO(getEntityManager());
    }

    public TaetigkeitsfeldAssocDAO getTaetigkeitsfeldAssocDAO() {
        return new TaetigkeitsfeldAssocDAO(getEntityManager());
    }

    public PositionDAO getPositionDAO() {
        return new PositionDAO(getEntityManager());
    }

    //pkg person

    public PersonDAO getPersonDAO() {
        return new PersonDAO(getEntityManager());
    }

    public PersonenNameDAO getPersonenNameDAO() {
        return new PersonenNameDAO(getEntityManager());
    }

    public AnredeDAO getAnredeDAO() {
        return new AnredeDAO(getEntityManager());
    }

    public GeschlechtDAO getGeschlechtDAO() {
        return new GeschlechtDAO(getEntityManager());
    }

    public NamensKlassifizierungDAO getNamensKlassifizierungDAO() {
        return new NamensKlassifizierungDAO(getEntityManager());
    }

    public TitelDAO getTitelDAO() {
        return new TitelDAO(getEntityManager());
    }


    //pgk web//webdomains

    public WebDomainKlassifizierungDAO getWebDomainKlassifizierungDAO() {
        return new WebDomainKlassifizierungDAO(getEntityManager());
    }

    public WebDomainMetaDataDAO getWebDomainMetaDataDAO() {
        return new WebDomainMetaDataDAO(getEntityManager());
    }


    //pkg Bewegungsdaten
    public RegistrationDAO getRegistrationDAO() {
        return new RegistrationDAO(getEntityManager());
    }

    public RegistrationStatusDAO getRegistrationStatusDAO() {
        return new RegistrationStatusDAO(getEntityManager());
    }

    public KontaktAnfrageDAO getKontaktAnfrageDAO() {
        return new KontaktAnfrageDAO(getEntityManager());
    }

    public ProjektanfrageDAO getProjektanfrageDAO() {
        return new ProjektanfrageDAO(getEntityManager());
    }

    public <T> void remove(T entity) {
        getEntityManager().remove(entity);
    }

    public <T> void saveOrUpdate(T entity) {
        getEntityManager().persist(entity);
    }

    public BaseDAO.EntityUtils getEntityUtils() {
        return entityUtils;
    }
}
