package org.rapidpm.persistence.prj.stammdaten.person; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.04.11
 * Time: 21:22
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.logging.LoggerQualifier;
import org.rapidpm.persistence.AbstractOneToManyConnectExecutor;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.stammdaten.address.Adresse;
import org.rapidpm.persistence.prj.stammdaten.kommunikation.KommunikationsServiceUID;
import org.rapidpm.persistence.prj.stammdaten.web.WebDomain;
import org.rapidpm.persistence.system.security.Benutzer;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Stateless(name = "PersonDAOEJB")
@WebService(name = "PersonDAOWS")
public class PersonDAOBean {
    //private static final Logger logger = Logger.getLogger(PersonDAOBean.class);

    @Inject @LoggerQualifier
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private CRUDExecuter<FlatPerson, Person, PersonResult> crudExecuter = new CRUDExecuter<FlatPerson, Person, PersonResult>(PersonResult.class) {
        @Override
        protected Person flatType2Type(final FlatPerson flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final Person typeObj;
            if (id == null || id == -1) {
                typeObj = new Person();
            } else {
                typeObj = findByID(id);
            }
            final List<Long> namenOIDs = flatTypeEntity.getNamenOIDs();
            typeObj.setNamen(daoFactoryBean.getPersonenNameDAO().loadWithOIDList(namenOIDs));

            final List<Long> titelOIDs = flatTypeEntity.getTitelOIDs();
            final List<Titel> titelList = daoFactoryBean.getTitelDAO().loadWithOIDList(titelOIDs);
            typeObj.setTitel(titelList);

            final Long anredeOID = flatTypeEntity.getAnredeOID();
            typeObj.setAnrede(daoFactoryBean.getAnredeDAO().findByID(anredeOID));


            typeObj.setBenutzer(daoFactoryBean.getBenutzerDAO().loadWithOIDList(flatTypeEntity.getBenutzerOIDs()));
            typeObj.setGeburtsdatum(flatTypeEntity.getGeburtsdatum());

            final List<Long> adressenOIDs = flatTypeEntity.getAdressenOIDs();
            typeObj.setAdressen(daoFactoryBean.getAdresseDAO().loadWithOIDList(adressenOIDs));

            typeObj.setGeschlecht(daoFactoryBean.getGeschlechtDAO().findByID(flatTypeEntity.getGeschlechtOID()));
            typeObj.setId(flatTypeEntity.getId());

            final List<Long> kommunikationsServiceUIDOIDs = flatTypeEntity.getKommunikationsServiceUIDOIDs();
            typeObj.setKommunikationsServiceUIDs(daoFactoryBean.getKommunikationServiceUIDDAO().loadWithOIDList(kommunikationsServiceUIDOIDs));

            final List<Long> webDomainOIDs = flatTypeEntity.getWebDomainOIDs();
            typeObj.setWebdomains(daoFactoryBean.getWebDomainDAO().loadWithOIDList(webDomainOIDs));


            return typeObj;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected Person findByID(final Long oid) {
            return getEntityDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    private final CRUDExecuter<FlatPersonStammdaten, Person, PersonResult> stammdatenExecuter = new CRUDExecuter<FlatPersonStammdaten, Person, PersonResult>(PersonResult.class) {
        @Override
        protected Person flatType2Type(final FlatPersonStammdaten personStammdaten) {
            final Long id = personStammdaten.getId();
            final Person person;
            if (id == null || id < 0L) {
                person = new Person();
            } else {
                person = getEntityDAO().findByID(id);
            }
            person.setAnrede(daoFactoryBean.getAnredeDAO().findByID(personStammdaten.getAnredeOID()));
            person.setGeschlecht(daoFactoryBean.getGeschlechtDAO().findByID(personStammdaten.getGeschlechtOID()));
            // bisherige Namen löschen
            List<PersonenName> personenNamen = person.getNamen();
            if (personenNamen != null) {
                final PersonenNameDAO personenNameDAO = daoFactoryBean.getPersonenNameDAO();
                for (final PersonenName personenName : personenNamen) {
                    personenNameDAO.remove(personenName);
                }
            }
            // neue Namen eintragen
            personenNamen = new ArrayList<>();
            final NamensKlassifizierungDAO namensKlassifizierungDAO = daoFactoryBean.getNamensKlassifizierungDAO();
            personenNamen.addAll(convertStringListToPersonenNameList(personStammdaten.getVornamen(), namensKlassifizierungDAO.loadNamensKlassifizierungVorname()));
            personenNamen.addAll(convertStringListToPersonenNameList(personStammdaten.getNachnamen(), namensKlassifizierungDAO.loadNamensKlassifizierungNachname()));
            personenNamen.addAll(convertStringListToPersonenNameList(personStammdaten.getGeburtsnamen(), namensKlassifizierungDAO.loadNamensKlassifizierungGeburtsname()));
            person.setNamen(personenNamen);
            // bisherige Titel löschen
            final List<Titel> titelList = person.getTitel();
            if (titelList != null) {
                final TitelDAO titelDAO = daoFactoryBean.getTitelDAO();
                for (final Titel titel : titelList) {
                    titelDAO.remove(titel);
                }
            }
            person.setTitel(new ArrayList<Titel>());

            // neue Titel eintragen
            final List<String> personStammdatenTitel = personStammdaten.getTitel();
            if (personStammdatenTitel != null) {
                for (int titelNr = 0; titelNr < personStammdatenTitel.size(); titelNr++) {
                    final String titelStr = personStammdatenTitel.get(titelNr);
                    final Titel titel = new Titel();
                    titel.setTitel(titelStr);
                    titel.setTitelNr(titelNr);
                    person.getTitel().add(titel);
                }
            } else {
                //
            }
            person.setTitel(titelList);
            final Date geburtsdatum = personStammdaten.getGeburtsdatum();
            person.setGeburtsdatum(geburtsdatum);
            return person;
        }

        private List<PersonenName> convertStringListToPersonenNameList(final List<String> namen, final NamensKlassifizierung namensKlassifizierung) {
            final List<PersonenName> personenNamen = new ArrayList<>();
            if (namen != null) {
                for (int nameNr = 0; nameNr < namen.size(); nameNr++) {
                    final PersonenName personenName = new PersonenName();
                    personenName.setName(namen.get(nameNr));
                    personenName.setReihenfolge(nameNr);
                    personenName.setNamensKlassifizierung(namensKlassifizierung);
                    personenNamen.add(personenName);
                }
            } else {
                //
            }
            return personenNamen;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected Person findByID(final Long oid) {
            return getEntityDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    private PersonDAO getEntityDAO() {
        return daoFactoryBean.getPersonDAO();
    }



    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "PersonResult")
    PersonResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatPerson entity) {


        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }


    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "PersonResult")
    PersonResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }


    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "PersonResult")
    PersonResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final Person byID = getEntityDAO().findByID(oid);
        if (byID == null) {
            return new PersonResult();
        } else {
            return createResult(byID);
        }
    }


    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "PersonResult")
    PersonResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                 @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }


    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "PersonResult")
    PersonResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }



    public
    @WebMethod(operationName = "loadPersonByOrgeinheit")
    @WebResult(name = "PersonResult")
    PersonResult loadPersonByOrgeinheit(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                        @WebParam(name = "orgeinheitOID", mode = WebParam.Mode.IN) final Long oid) {
        final List<Person> persons = getEntityDAO().loadPersonenByOrgeinheit(oid);
        return createResult(persons);
    }



    public
    @WebMethod(operationName = "loadPersonByMandantengruppeOID")
    @WebResult(name = "PersonResult")
    PersonResult loadPersonByMandantengruppeOID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                @WebParam(name = "mandantengruppeOID", mode = WebParam.Mode.IN) final Long mandantengruppeOID) {
        final List<Person> persons = getEntityDAO().loadPersonByMandantengruppe(mandantengruppeOID);
        return createResult(persons);
    }


    public
    @WebMethod(operationName = "loadPersonByMandantengruppe")
    @WebResult(name = "PersonResult")
    PersonResult loadPersonByMandantengruppe(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                             @WebParam(name = "mandantengruppe", mode = WebParam.Mode.IN) final String mandantengruppe) {
        final List<Person> persons = getEntityDAO().loadPersonByMandantengruppe(mandantengruppe);
        return createResult(persons);
    }



    public
    @WebMethod(operationName = "loadPersonByBenutzer")
    @WebResult(name = "PersonResult")
    PersonResult loadPersonByBenutzer(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                      @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                      @WebParam(name = "benutzerOID", mode = WebParam.Mode.IN) final Long benutzerOID) {
        final Person person = getEntityDAO().loadPersonByBenutzer(benutzerOID);
        return person != null ? createResult(person) : createResult();
    }



    public
    @WebMethod(operationName = "saveStammdaten")
    @WebResult(name = "PersonResult")
    PersonResult saveStammdaten(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                @WebParam(name = "personStammdaten", mode = WebParam.Mode.IN) final FlatPersonStammdaten personStammdaten) {

        return stammdatenExecuter.saveOrUpdate(sessionid, uid, personStammdaten);
    }



    public
    @WebMethod(operationName = "connectBenutzer")
    @WebResult(name = "PersonResult")
    PersonResult connectBenutzer(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                 @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                 @WebParam(name = "personOID", mode = WebParam.Mode.IN) final Long personOID,
                                 @WebParam(name = "benutzerOID", mode = WebParam.Mode.IN) final Long benutzerOID) {

        return new AbstractOneToManyConnectExecutor<DaoFactory, Benutzer, Person, PersonResult>(
                daoFactoryBean, daoFactoryBean.getBenutzerDAO(), getEntityDAO()) {

            @Override
            public List<Benutzer> getCollection(final Person person) {
                return person.getBenutzer();
            }

            @Override
            public PersonResult getResult(final Person person) {
                return person != null ? createResult(person) : createResult();
            }
        }.execute("connectBenutzer", logEventEntryWriterBean, sessionid, benutzerOID, personOID);
    }


    public
    @WebMethod(operationName = "connectAdresse")
    @WebResult(name = "PersonResult")
    PersonResult connectAdresse(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                @WebParam(name = "personOID", mode = WebParam.Mode.IN) final Long personOID,
                                @WebParam(name = "adresseOID", mode = WebParam.Mode.IN) final Long adresseOID) {
        return new AbstractOneToManyConnectExecutor<DaoFactory, Adresse, Person, PersonResult>(
                daoFactoryBean, daoFactoryBean.getAdresseDAO(), getEntityDAO()) {

            @Override
            public List<Adresse> getCollection(final Person person) {
                return person.getAdressen();
            }

            @Override
            public PersonResult getResult(final Person person) {
                return person != null ? createResult(person) : createResult();
            }
        }.execute("connectAdresse", logEventEntryWriterBean, sessionid, adresseOID, personOID);
    }


    public
    @WebMethod(operationName = "connectKommunikationsServiceUID")
    @WebResult(name = "PersonResult")
    PersonResult connectKommunikationsServiceUID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                 @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                 @WebParam(name = "personOID", mode = WebParam.Mode.IN) final Long personOID,
                                                 @WebParam(name = "kommunikationsServiceUIDOID", mode = WebParam.Mode.IN) final Long kommunikationsServiceUIDOID) {
        return new AbstractOneToManyConnectExecutor<DaoFactory, KommunikationsServiceUID, Person, PersonResult>(
                daoFactoryBean, daoFactoryBean.getKommunikationServiceUIDDAO(), getEntityDAO()) {

            @Override
            public List<KommunikationsServiceUID> getCollection(final Person person) {
                return person.getKommunikationsServiceUIDs();
            }

            @Override
            public PersonResult getResult(final Person person) {
                return person != null ? createResult(person) : createResult();
            }
        }.execute("connectKommunikationsServiceUID", logEventEntryWriterBean, sessionid, kommunikationsServiceUIDOID, personOID);
    }


    private FlatPerson type2FlatType(final Person person) {
        final FlatPerson fp = new FlatPerson();

        for (final Adresse adresse : person.getAdressen()) {
            fp.getAdressenOIDs().add(adresse.getId());
        }

        for (final Benutzer benutzer : person.getBenutzer()) {
            fp.getBenutzerOIDs().add(benutzer.getId());
        }


        final Anrede anrede = person.getAnrede();
        if (anrede != null) {
            fp.setAnredeOID(anrede.getId());
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Anrede nicht vorhanden f Person " + person.getId());
            }
        }

        fp.setGeburtsdatum(person.getGeburtsdatum());
        fp.setGeschlechtOID(person.getGeschlecht().getId());
        fp.setId(person.getId());
        final List<KommunikationsServiceUID> kommunikationsServiceUIDs = person.getKommunikationsServiceUIDs();
        kommunikationsServiceUIDs.size();
        for (final KommunikationsServiceUID kommunikationsServiceUID : kommunikationsServiceUIDs) {
            fp.getKommunikationsServiceUIDOIDs().add(kommunikationsServiceUID.getId());
        }
        final List<PersonenName> namen = person.getNamen();
        namen.size();
        for (final PersonenName personenName : namen) {
            fp.getNamenOIDs().add(personenName.getId());
        }
        final List<Titel> titel = person.getTitel();
        titel.size();
        for (final Titel t : titel) {
            fp.getTitelOIDs().add(t.getId());
        }
        final List<WebDomain> webdomains = person.getWebdomains();
        webdomains.size();
        for (final WebDomain webdomain : webdomains) {
            fp.getWebDomainOIDs().add(webdomain.getId());
        }
        return fp;
    }

    private PersonResult createResult(final Person... typeObj) {
        final PersonResult result = new PersonResult();
        for (final Person obj : typeObj) {
            result.getObjList().add(type2FlatType(obj));
        }
        return result;
    }

    private PersonResult createResult(final Collection<Person> typeObj) {
        final PersonResult result = new PersonResult();
        for (final Person obj : typeObj) {
            result.getObjList().add(type2FlatType(obj));
        }
        return result;
    }


    public static class FlatPerson extends BaseFlatEntity {
        private Date geburtsdatum;
        private Long geschlechtOID;
        private List<Long> adressenOIDs = new ArrayList<>();
        private Long anredeOID;
        private List<Long> benutzerOIDs = new ArrayList<>();
        private List<Long> kommunikationsServiceUIDOIDs = new ArrayList<>();
        private List<Long> namenOIDs = new ArrayList<>();
        private List<Long> titelOIDs = new ArrayList<>();
        private List<Long> webDomainOIDs = new ArrayList<>();

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof FlatPerson)) {
                return false;
            }

            final FlatPerson that = (FlatPerson) o;

            if (adressenOIDs != null ? !adressenOIDs.equals(that.adressenOIDs) : that.adressenOIDs != null) {
                return false;
            }
            if (anredeOID != null ? !anredeOID.equals(that.anredeOID) : that.anredeOID != null) {
                return false;
            }
            if (benutzerOIDs != null ? !benutzerOIDs.equals(that.benutzerOIDs) : that.benutzerOIDs != null) {
                return false;
            }
            if (geburtsdatum != null ? !geburtsdatum.equals(that.geburtsdatum) : that.geburtsdatum != null) {
                return false;
            }
            if (geschlechtOID != null ? !geschlechtOID.equals(that.geschlechtOID) : that.geschlechtOID != null) {
                return false;
            }
            if (kommunikationsServiceUIDOIDs != null ? !kommunikationsServiceUIDOIDs.equals(that.kommunikationsServiceUIDOIDs) : that.kommunikationsServiceUIDOIDs != null) {
                return false;
            }
            if (namenOIDs != null ? !namenOIDs.equals(that.namenOIDs) : that.namenOIDs != null) {
                return false;
            }
            if (titelOIDs != null ? !titelOIDs.equals(that.titelOIDs) : that.titelOIDs != null) {
                return false;
            }
            if (webDomainOIDs != null ? !webDomainOIDs.equals(that.webDomainOIDs) : that.webDomainOIDs != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = geburtsdatum != null ? geburtsdatum.hashCode() : 0;
            result = 31 * result + (geschlechtOID != null ? geschlechtOID.hashCode() : 0);
            result = 31 * result + (adressenOIDs != null ? adressenOIDs.hashCode() : 0);
            result = 31 * result + (anredeOID != null ? anredeOID.hashCode() : 0);
            result = 31 * result + (benutzerOIDs != null ? benutzerOIDs.hashCode() : 0);
            result = 31 * result + (kommunikationsServiceUIDOIDs != null ? kommunikationsServiceUIDOIDs.hashCode() : 0);
            result = 31 * result + (namenOIDs != null ? namenOIDs.hashCode() : 0);
            result = 31 * result + (titelOIDs != null ? titelOIDs.hashCode() : 0);
            result = 31 * result + (webDomainOIDs != null ? webDomainOIDs.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatPerson");
            sb.append("{adressenOIDs=").append(adressenOIDs);
            sb.append(", geburtsdatum='").append(geburtsdatum).append('\'');
            sb.append(", geschlechtOID=").append(geschlechtOID);
            sb.append(", anrede=").append(anredeOID);
            sb.append(", benutzerOIDs=").append(benutzerOIDs);
            sb.append(", kommunikationsServiceUIDs=").append(kommunikationsServiceUIDOIDs);
            sb.append(", namenOIDs=").append(namenOIDs);
            sb.append(", titel=").append(titelOIDs);
            sb.append(", webDomainOIDs=").append(webDomainOIDs);
            sb.append('}');
            return sb.toString();
        }

        public List<Long> getAdressenOIDs() {
            return adressenOIDs;
        }

        public void setAdressenOIDs(final List<Long> adressenOIDs) {
            this.adressenOIDs = adressenOIDs;
        }

        public Long getAnredeOID() {
            return anredeOID;
        }

        public void setAnredeOID(final Long anredeOID) {
            this.anredeOID = anredeOID;
        }

        public List<Long> getBenutzerOIDs() {
            return benutzerOIDs;
        }

        public void setBenutzerOIDs(final List<Long> benutzerOIDs) {
            this.benutzerOIDs = benutzerOIDs;
        }

        public Date getGeburtsdatum() {
            return geburtsdatum;
        }

        public void setGeburtsdatum(final Date geburtsdatum) {
            this.geburtsdatum = geburtsdatum;
        }

        public Long getGeschlechtOID() {
            return geschlechtOID;
        }

        public void setGeschlechtOID(final Long geschlechtOID) {
            this.geschlechtOID = geschlechtOID;
        }

        public List<Long> getKommunikationsServiceUIDOIDs() {
            return kommunikationsServiceUIDOIDs;
        }

        public void setKommunikationsServiceUIDOIDs(final List<Long> kommunikationsServiceUIDOIDs) {
            this.kommunikationsServiceUIDOIDs = kommunikationsServiceUIDOIDs;
        }

        public List<Long> getNamenOIDs() {
            return namenOIDs;
        }

        public void setNamenOIDs(final List<Long> namenOIDs) {
            this.namenOIDs = namenOIDs;
        }

        public List<Long> getTitelOIDs() {
            return titelOIDs;
        }

        public void setTitelOIDs(final List<Long> titelOIDs) {
            this.titelOIDs = titelOIDs;
        }

        public List<Long> getWebDomainOIDs() {
            return webDomainOIDs;
        }

        public void setWebDomainOIDs(final List<Long> webDomainOIDs) {
            this.webDomainOIDs = webDomainOIDs;
        }
    }

    public static class FlatPersonStammdaten extends BaseFlatEntity {
        private Long anredeOID;
        private Long geschlechtOID;
        private List<String> vornamen;
        private List<String> nachnamen;
        private List<String> geburtsnamen;
        private List<String> titel;
        private Date geburtsdatum;

        public Long getAnredeOID() {
            return anredeOID;
        }

        public void setAnredeOID(final Long anredeOID) {
            this.anredeOID = anredeOID;
        }

        public Long getGeschlechtOID() {
            return geschlechtOID;
        }

        public void setGeschlechtOID(final Long geschlechtOID) {
            this.geschlechtOID = geschlechtOID;
        }

        public List<String> getVornamen() {
            return vornamen;
        }

        public void setVornamen(final List<String> vornamen) {
            this.vornamen = vornamen;
        }

        public List<String> getNachnamen() {
            return nachnamen;
        }

        public void setNachnamen(final List<String> nachnamen) {
            this.nachnamen = nachnamen;
        }

        public List<String> getGeburtsnamen() {
            return geburtsnamen;
        }

        public void setGeburtsnamen(final List<String> geburtsnamen) {
            this.geburtsnamen = geburtsnamen;
        }

        public List<String> getTitel() {
            return titel;
        }

        public void setTitel(final List<String> titel) {
            this.titel = titel;
        }

        public Date getGeburtsdatum() {
            return geburtsdatum;
        }

        public void setGeburtsdatum(final Date geburtsdatum) {
            this.geburtsdatum = geburtsdatum;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            final FlatPersonStammdaten that = (FlatPersonStammdaten) o;

            if (anredeOID != null ? !anredeOID.equals(that.anredeOID) : that.anredeOID != null) return false;
            if (geburtsdatum != null ? !geburtsdatum.equals(that.geburtsdatum) : that.geburtsdatum != null)
                return false;
            if (geburtsnamen != null ? !geburtsnamen.equals(that.geburtsnamen) : that.geburtsnamen != null)
                return false;
            if (geschlechtOID != null ? !geschlechtOID.equals(that.geschlechtOID) : that.geschlechtOID != null)
                return false;
            if (nachnamen != null ? !nachnamen.equals(that.nachnamen) : that.nachnamen != null) return false;
            if (titel != null ? !titel.equals(that.titel) : that.titel != null) return false;
            if (vornamen != null ? !vornamen.equals(that.vornamen) : that.vornamen != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = anredeOID != null ? anredeOID.hashCode() : 0;
            result = 31 * result + (geschlechtOID != null ? geschlechtOID.hashCode() : 0);
            result = 31 * result + (vornamen != null ? vornamen.hashCode() : 0);
            result = 31 * result + (nachnamen != null ? nachnamen.hashCode() : 0);
            result = 31 * result + (geburtsnamen != null ? geburtsnamen.hashCode() : 0);
            result = 31 * result + (titel != null ? titel.hashCode() : 0);
            result = 31 * result + (geburtsdatum != null ? geburtsdatum.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatNewPerson");
            sb.append("{anredeOID=").append(anredeOID);
            sb.append(", geschlechtOID=").append(geschlechtOID);
            sb.append(", vornamen=").append(vornamen);
            sb.append(", nachnamen=").append(nachnamen);
            sb.append(", geburtsnamen=").append(geburtsnamen);
            sb.append(", titel=").append(titel);
            sb.append(", geburtsdatum=").append(geburtsdatum);
            sb.append('}');
            return sb.toString();
        }
    }

    public static class PersonResult extends BaseOrmResult<FlatPerson> {
        //        private List<FlatPerson> objList = new ArrayList<FlatPerson>();
        //
        //        public List<FlatPerson> getObjList(){
        //            return objList;
        //        }
        //
        //        public void setObjList(final List<FlatPerson> objList){
        //            this.objList = objList;
        //        }

        public List<FlatPerson> getObjList() {
            return objList;
        }

        public void setObjList(final List<FlatPerson> objList) {
            this.objList = objList;
        }
    }


}

