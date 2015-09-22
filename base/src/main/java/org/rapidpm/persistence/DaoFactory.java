package org.rapidpm.persistence;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 11.01.12
 * Time: 14:27
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
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
  //private final GraphDatabaseService graphDb = GraphDBFactory.getInstance().getGraphDBService();
  private OrientGraphFactory factory;

  public DaoFactory(final String databaseName) {
    factory = new OrientGraphFactory("plocal:orient/" + databaseName);
    factory.getTx().shutdown(); // AUTO-CREATE THE GRAPH IF NOT EXISTS
    factory.setupPool(1, 10);
//       orientDB = new OrientGraph("remote:localhost/"+databaseName, "root", "admin"){
//           @Override
//           public OrientVertex getVertex(Object id) {
//               OrientVertex vertex = super.getVertex(id);
//               vertex.reload();
//               return vertex;
//           }
//
//           @Override
//           public Iterable<Vertex> getVerticesOfClass(String iClassName) {
//               Iterable<Vertex> verticesOfClass = super.getVerticesOfClass(iClassName);
//               for (Vertex vertexOfClass : verticesOfClass) {
//                   ((OrientVertex)vertexOfClass).reload();
//               }
//               return verticesOfClass;
//           }
//       };
  }

  public BenutzerGruppeDAO getBenutzerGruppeDAO() {
    return new BenutzerGruppeDAO(getOrientDB());
  }

  // pkg logging

  private OrientGraph getOrientDB() {
    return factory.getTx();
  }

  public long countVertices(){
    return getOrientDB().countVertices();
  }

  public BenutzerWebapplikationDAO getBenutzerWebapplikationDAO() {
    return new BenutzerWebapplikationDAO(getOrientDB());
  }

  public BenutzerDAO getBenutzerDAO() {
    return new BenutzerDAO(getOrientDB());
  }

  public MandantengruppeDAO getMandantengruppeDAO() {
    return new MandantengruppeDAO(getOrientDB());
  }

  public PlannedProjectDAO getPlannedProjectDAO() {
    return new PlannedProjectDAO(getOrientDB());
  }


  public NewPasswdRequestDAO getNewPasswdRequestDAO() {
    return new NewPasswdRequestDAO(getOrientDB());
  }

  public PlanningUnitElementDAO getPlanningUnitElementDAO() {
    return new PlanningUnitElementDAO(getOrientDB());
  }

  public RolleDAO getRolleDAO() {
    return new RolleDAO(getOrientDB());
  }

  public BerechtigungDAO getBerechtigungDAO() {
    return new BerechtigungDAO(getOrientDB());
  }


  public OrganisationseinheitDAO getOrganisationseinheitDAO() {
    return new OrganisationseinheitDAO(getOrientDB());
  }


  public WebDomainDAO getWebDomainDAO() {
    return new WebDomainDAO(getOrientDB());
  }

  // pkg address

  public AddressKlassifizierungDAO getAddressKlassifizierungDAO() {
    return new AddressKlassifizierungDAO(getOrientDB());
  }

  public AdresseDAO getAdresseDAO() {
    return new AdresseDAO(getOrientDB());
  }


  public StateDAO getStateDAO() {
    return new StateDAO(getOrientDB());
  }

  public StateKlassifizierungDAO getStateKlassifizierungDAO() {
    return new StateKlassifizierungDAO(getOrientDB());
  }

  public LandDAO getLandDAO() {
    return new LandDAO(getOrientDB());
  }


  //book
  public BuchDAO getBuchDAO() {
    return new BuchDAO(getOrientDB());
  }

  public BuchKapitelDAO getBuchKapitelDAO() {
    return new BuchKapitelDAO(getOrientDB());
  }

  public BuchKommentarDAO getBuchKommentarDAO() {
    return new BuchKommentarDAO(getOrientDB());
  }

  public BuchKapitelKommentarDAO getBuchKapitelKommentarDAO() {
    return new BuchKapitelKommentarDAO(getOrientDB());
  }

  public BuchSeitenKommentarDAO getBuchSeitenKommentarDAO() {
    return new BuchSeitenKommentarDAO(getOrientDB());
  }


  public ProjectDAO getProjectDAO() {
    return new ProjectDAO(getOrientDB());
  }

  public ProjectNameDAO getProjectNameDAO() {
    return new ProjectNameDAO(getOrientDB());
  }


  //pkg Kommunikation

  public KommunikationsServiceDAO getKommunikationsServiceDAO() {
    return new KommunikationsServiceDAO(getOrientDB());
  }

  public KommunikationsServiceKlassifizierungDAO getKommunikationsServiceKlassifizierungDAO() {
    return new KommunikationsServiceKlassifizierungDAO(getOrientDB());
  }

  public KommunikationsServiceUIDDAO getKommunikationServiceUIDDAO() {
    return new KommunikationsServiceUIDDAO(getOrientDB());
  }

  public KommunikationsServiceUIDPartDAO getKommunikationServiceUIDPartDAO() {
    return new KommunikationsServiceUIDPartDAO(getOrientDB());
  }

  public KommunikationsServiceUIDPartKlassifikationDAO getKommunikationsServiceUIDPartKlassifikationDAO() {
    return new KommunikationsServiceUIDPartKlassifikationDAO(getOrientDB());
  }

  //pkg intern
  public RessourceGroupDAO getRessourceGroupDAO() {
    return new RessourceGroupDAO(getOrientDB());
  }

  //pkg organisationseinheiten

  public BrancheDAO getBrancheDAO() {
    return new BrancheDAO(getOrientDB());
  }

  public BrancheAssocDAO getBranchenAssocDAO() {
    return new BrancheAssocDAO(getOrientDB());
  }

  public BranchenKlassifizierungDAO getBranchenKlassifizierungDAO() {
    return new BranchenKlassifizierungDAO(getOrientDB());
  }

  public GesellschaftsformDAO getGesellschaftsformDAO() {
    return new GesellschaftsformDAO(getOrientDB());
  }

  public OrganisationseinheitMetaDataDAO getOrganisationseinheitMetaDataDAO() {
    return new OrganisationseinheitMetaDataDAO(getOrientDB());
  }

  public AusbildungseinheitDAO getAusbildungseinheitDAO() {
    return new AusbildungseinheitDAO(getOrientDB());
  }

  public VerwaltungseinheitDAO getVerwaltungseinheitDAO() {
    return new VerwaltungseinheitDAO(getOrientDB());
  }

  public WirtschaftseinheitDAO getWirtschaftseinheitDAO() {
    return new WirtschaftseinheitDAO(getOrientDB());
  }


  public TaetigkeitsfeldKlassifizierungDAO getTaetigkeitsklassifizierungDAO() {
    return new TaetigkeitsfeldKlassifizierungDAO(getOrientDB());
  }

  public TaetigkeitsfeldDAO getTaetigkeitsfeldDAO() {
    return new TaetigkeitsfeldDAO(getOrientDB());
  }

  public TaetigkeitsfeldAssocDAO getTaetigkeitsfeldAssocDAO() {
    return new TaetigkeitsfeldAssocDAO(getOrientDB());
  }

  public PositionDAO getPositionDAO() {
    return new PositionDAO(getOrientDB());
  }

  //pkg person

  public PersonDAO getPersonDAO() {
    return new PersonDAO(getOrientDB());
  }

  public PersonenNameDAO getPersonenNameDAO() {
    return new PersonenNameDAO(getOrientDB());
  }

  public AnredeDAO getAnredeDAO() {
    return new AnredeDAO(getOrientDB());
  }

  public GeschlechtDAO getGeschlechtDAO() {
    return new GeschlechtDAO(getOrientDB());
  }

  public NamensKlassifizierungDAO getNamensKlassifizierungDAO() {
    return new NamensKlassifizierungDAO(getOrientDB());
  }

  public TitelDAO getTitelDAO() {
    return new TitelDAO(getOrientDB());
  }


  //pgk web//webdomains

  public WebDomainKlassifizierungDAO getWebDomainKlassifizierungDAO() {
    return new WebDomainKlassifizierungDAO(getOrientDB());
  }

  public WebDomainMetaDataDAO getWebDomainMetaDataDAO() {
    return new WebDomainMetaDataDAO(getOrientDB());
  }

  public PlanningUnitDAO getPlanningUnitDAO() {
    return new PlanningUnitDAO(getOrientDB());
  }


  //pkg Bewegungsdaten
  public RegistrationDAO getRegistrationDAO() {
    return new RegistrationDAO(getOrientDB());
  }

  public RegistrationStatusDAO getRegistrationStatusDAO() {
    return new RegistrationStatusDAO(getOrientDB());
  }

  public KontaktAnfrageDAO getKontaktAnfrageDAO() {
    return new KontaktAnfrageDAO(getOrientDB());
  }

  public ProjektanfrageDAO getProjektanfrageDAO() {
    return new ProjektanfrageDAO(getOrientDB());
  }

  public TextElementDAO getTextElementDAO() {
    return new TextElementDAO((getOrientDB()));
  }

}
