/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@rapidpm.org
 */

package org.rapidpm.persistence.security;

import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.data.LoggingResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.ejb3.interceptor.LoggingInterceptor;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.persistence.prj.stammdaten.DaoFactoryBean;
import org.rapidpm.persistence.system.logging.LogLevelEnum;
import org.rapidpm.persistence.system.logging.LoggingEventEntry;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.BenutzerDAO;
import org.rapidpm.persistence.system.security.Mandantengruppe;
import org.rapidpm.persistence.system.security.berechtigungen.Berechtigung;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 21.02.11
 * Time: 15:46
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */
@Stateless(name = "BenutzerDAOEJB")
@WebService(name = "BenutzerDAOWS")
public class BenutzerDAOBean {
    public BenutzerDAOBean() {
    }

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private BenutzerDAO getBenutzerDAO() {
        return daoFactoryBean.getBenutzerDAO();
    }

    private CRUDExecuter<FlatBenutzer, Benutzer, BenutzerResult> crudExecuter = new CRUDExecuter<FlatBenutzer, Benutzer, BenutzerResult>(BenutzerResult.class) {
        @Override
        public Benutzer flatType2Type(final FlatBenutzer flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final Benutzer b;
            if (id == null || id == -1 || id == 0) {
                b = new Benutzer();
            } else {
                b = findByID(id);
            }

            b.setActive(flatTypeEntity.getActive());
            b.setBenutzerGruppe(daoFactoryBean.getBenutzerGruppeDAO().findByID(flatTypeEntity.getBenutzerGruppeOID()));
            b.setBenutzerWebapplikation(daoFactoryBean.getBenutzerWebapplikationDAO().findByID(flatTypeEntity.getBenutzerWebapplikationOID()));
            //            b.setCreated(flatTypeEntity.getCreated());
//            b.setFailedLogins(flatTypeEntity.getFailedLogins());
            b.setHidden(flatTypeEntity.getHidden());
//            b.setLastLogin(flatTypeEntity.getLastLogin());
            b.setLogin(flatTypeEntity.getLogin());
            b.setMandantengruppe(daoFactoryBean.getMandantengruppeDAO().findByID(flatTypeEntity.getMandantengruppeOID()));
            //            b.setModified(flatTypeEntity.getModified());
            b.setPasswd(flatTypeEntity.getPasswd());
            b.setValidFrom(flatTypeEntity.getValidFrom());
            b.setValidUntil(flatTypeEntity.getValidUntil());

            final List<Long> berechtigungen = flatTypeEntity.getBerechtigungOIDs();
            final List<Berechtigung> berechtingunsObjList = new ArrayList<>();
            for (final Long berechtigung : berechtigungen) {
                berechtingunsObjList.add(daoFactoryBean.getBerechtigungDAO().findByID(berechtigung));
            }
            b.setBerechtigungen(berechtingunsObjList);

//            b.setPerson(daoFactoryBean.getPersonDAO().findByID(flatTypeEntity.getPersonOID()));
            return b;

        }

        @Override
        protected Benutzer findByID(final Long oid) {
            return daoFactoryBean.getBenutzerDAO().findByID(oid);
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };


    private BenutzerResult createResult(final Benutzer benutzer) {
        final BenutzerResult result = new BenutzerResult();
        if (benutzer != null) {
            result.getObjList().add(type2FlatType(benutzer));
        } else {

        }
        return result;
    }

    private BenutzerResult createResult(final Collection<Benutzer> benutzerListe) {
        final BenutzerResult result = new BenutzerResult();
        for (final Benutzer benutzer : benutzerListe) {
            result.getObjList().add(type2FlatType(benutzer));
        }
        return result;
    }

    private FlatBenutzer type2FlatType(final Benutzer b) {
        final FlatBenutzer ft = new FlatBenutzer();
        ft.setPasswd(b.getPasswd());
        ft.setValidFrom(b.getValidFrom());
        ft.setValidUntil(b.getValidUntil());
        ft.setActive(b.getActive());
        ft.setBenutzerGruppeOID(b.getBenutzerGruppe().getId());
        ft.setBenutzerWebapplikationOID(b.getBenutzerWebapplikation().getId());

        final List<Berechtigung> berechtigungen = b.getBerechtigungen();
        if (berechtigungen != null) {
            for (final Berechtigung berechtigung : berechtigungen) {
                ft.getBerechtigungOIDs().add(berechtigung.getId());
            }
        }
//        ft.setFailedLogins(b.getFailedLogins());
        ft.setHidden(b.getHidden());
//        ft.setLastLogin(b.getLastLogin());
        ft.setLogin(b.getLogin());
        ft.setMandantengruppeOID(b.getMandantengruppe().getId());


//        final Person person = b.getPerson();
//        if (person != null) {
//            ft.setPersonOID(person.getId());
//        } else {
//        }


        ft.setId(b.getId());
        return ft;
    }

    @WebMethod(operationName = "loadRevisionFor")
    @Interceptors(LoggingInterceptor.class)
    public
    @WebResult(name = "BenutzerResult")
    BenutzerResult loadRevisionFor(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final List<Benutzer> list = getBenutzerDAO().loadAllRevisionsFor(oid);
        return createResult(list);
    }

    @WebMethod(operationName = "loadBenutzerForLogin")
    @Interceptors(LoggingInterceptor.class)
    public
    @WebResult(name = "BenutzerResult")
    BenutzerResult loadBenutzerForLogin(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                        @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                        @WebParam(name = "login", mode = WebParam.Mode.IN) final String login) {
        final List<Benutzer> benutzerListe = getBenutzerDAO().loadBenutzerForLogin(login);
        return createResult(benutzerListe);
    }

    @WebMethod(operationName = "loadBenutzer")
    @Interceptors(LoggingInterceptor.class)
    public
    @WebResult(name = "BenutzerResult")
    BenutzerResult loadBenutzer(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                @WebParam(name = "login", mode = WebParam.Mode.IN) final String login,
                                @WebParam(name = "mandantengruppeOID", mode = WebParam.Mode.IN) final Long mandantengruppe) {
        final Benutzer benutzer = getBenutzerDAO().loadBenutzer(login, mandantengruppe);
        return createResult(benutzer);
    }

    @WebMethod(operationName = "loadBenutzerByMandantenGruppe")
    @Interceptors(LoggingInterceptor.class)
    public
    @WebResult(name = "BenutzerResult")
    BenutzerResult loadBenutzerByMandantenGruppe(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                 @WebParam(name = "mandantengruppeOID", mode = WebParam.Mode.IN) final Long mandantengruppe, @WebParam(name = "hidden", mode = WebParam.Mode.IN) final boolean hidden) {
        final List<Benutzer> benutzerListe = getBenutzerDAO().loadBenutzerByMandantenGruppe(mandantengruppe, hidden);
        return createResult(benutzerListe);
    }

    @WebMethod(operationName = "loadBenutzerByMandantenGruppeActiveHidden")
    @Interceptors(LoggingInterceptor.class)
    public
    @WebResult(name = "BenutzerResult")
    BenutzerResult loadBenutzerByMandantenGruppe(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                 @WebParam(name = "mandantengruppeOID", mode = WebParam.Mode.IN) final Long mandantengruppe, @WebParam(name = "hidden", mode = WebParam.Mode.IN) final boolean hidden,
                                                 @WebParam(name = "active", mode = WebParam.Mode.IN) final Boolean active) {
        final List<Benutzer> benutzerListe = getBenutzerDAO().loadBenutzerByMandantenGruppe(mandantengruppe, hidden, active);
        return createResult(benutzerListe);

    }

    @WebMethod(operationName = "loadBenutzerByMandantenGruppeActive")
    @Interceptors(LoggingInterceptor.class)
    public
    @WebResult(name = "BenutzerResult")
    BenutzerResult aktiveBenutzerByMandantenGruppe(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                   @WebParam(name = "mandantengruppeOID", mode = WebParam.Mode.IN) final Long mandantengruppe, @WebParam(name = "active", mode = WebParam.Mode.IN) final Boolean aktiv) {
        final List<Benutzer> benutzerListe = getBenutzerDAO().aktiveBenutzerByMandantenGruppe(mandantengruppe, aktiv);
        return createResult(benutzerListe);

    }

    @WebMethod(operationName = "loadBenutzerByWebapp")
    @Interceptors(LoggingInterceptor.class)
    public
    @WebResult(name = "BenutzerResult")
    BenutzerResult loadBenutzer(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "login", mode = WebParam.Mode.IN) final String login,
                                @WebParam(name = "passwd", mode = WebParam.Mode.IN) final String passwd, @WebParam(name = "webappNameOID", mode = WebParam.Mode.IN) final Long webappName) {
        final Benutzer benutzer = getBenutzerDAO().loadBenutzer(login, passwd, webappName);
        return createResult(benutzer);

    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebResult(name = "BenutzerResult")
    BenutzerResult loadBenutzerInclSystemBenutzer(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                  @WebParam(name = "login", mode = WebParam.Mode.IN) final String login, @WebParam(name = "passwd", mode = WebParam.Mode.IN) final String passwd,
                                                  @WebParam(name = "webappNameOID", mode = WebParam.Mode.IN) final Long webappName) {
        final Benutzer benutzer = getBenutzerDAO().loadBenutzerInclSystemBenutzer(login, passwd, webappName);
        return createResult(benutzer);

    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebResult(name = "BenutzerResult")
    BenutzerResult loadAnonymousBenutzer(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                         @WebParam(name = "webappNameOID", mode = WebParam.Mode.IN) final Long webappName) {
        final Benutzer benutzer = getBenutzerDAO().loadAnonymousBenutzer(webappName);
        return createResult(benutzer);
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebResult(name = "CheckIfBenutzerLoginIsAvailableResult")
    CheckIfBenutzerLoginIsAvailableResult checkIfBenutzerLoginIsAvailable(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                                          @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                                          @WebParam(name = "mandantengruppeOID", mode = WebParam.Mode.IN) final Long mandantengruppeOID,
                                                                          @WebParam(name = "wishedLogin", mode = WebParam.Mode.IN) final String wishedLogin) {
        final Mandantengruppe byID = daoFactoryBean.getMandantengruppeDAO().findByID(mandantengruppeOID);
        final CheckIfBenutzerLoginIsAvailableResult result = new CheckIfBenutzerLoginIsAvailableResult();
        if (byID != null) {
            final boolean b = getBenutzerDAO().checkIfBenutzerLoginIsAvailable(wishedLogin, byID.getMandantengruppe());
            result.setAvailable(b);
        } else {
            result.setAvailable(false);
        }
        return result;
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "checkIfBenutzerLoginIsActiveAndValid")
    @WebResult(name = "CheckIfBenutzerLoginIsActiveAndValidResult")
    CheckIfBenutzerLoginIsActiveAndValidResult checkIfBenutzerLoginIsActiveAndValid(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                                                    @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                                                    @WebParam(name = "benutzerOID", mode = WebParam.Mode.IN) final Long benutzerOID) {
        final Benutzer benutzer = getBenutzerDAO().findByID(benutzerOID);
        final CheckIfBenutzerLoginIsActiveAndValidResult result = new CheckIfBenutzerLoginIsActiveAndValidResult();
        if (benutzer != null) {
            result.setActive(benutzer.getActive());
            final Date now = new Date();
            result.setValid(now.after(benutzer.getValidFrom()) && now.before(benutzer.getValidUntil()));
        } else {
            result.getLoggingEventEntries().add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO,
                    "checkIfBenutzerLoginIsActiveAndValid", sessionid, "Es existiert kein Benutzer mit der OID " + benutzerOID));
        }
        return result;
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "BenutzerResult")
    BenutzerResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                  @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatBenutzer entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "BenutzerResult")
    BenutzerResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "BenutzerResult")
    BenutzerResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final Benutzer byID = getBenutzerDAO().findByID(oid);
        if (byID == null) {
            return new BenutzerResult();
        } else {
            return createResult(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "BenutzerResult")
    BenutzerResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                   @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getBenutzerDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "BenutzerResult")
    BenutzerResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getBenutzerDAO().loadAllEntities());
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadBenutzerByEmail")
    @WebResult(name = "BenutzerResult")
    BenutzerResult loadBenutzerByEmail(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                       @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                       @WebParam(name = "email", mode = WebParam.Mode.IN) final String email) {
        return createResult(getBenutzerDAO().loadBenutzerByEmail(email));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadBenutzerByEmailAndMandantengruppeOID")
    @WebResult(name = "BenutzerResult")
    BenutzerResult loadBenutzerByEmail(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                       @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                       @WebParam(name = "email", mode = WebParam.Mode.IN) final String email,
                                       @WebParam(name = "mandantengruppeOID", mode = WebParam.Mode.IN) final Long mandantengruppeOID) {
        return createResult(getBenutzerDAO().loadBenutzerByEmail(email, mandantengruppeOID));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadBenutzerByEmailAndMandantengruppe")
    @WebResult(name = "BenutzerResult")
    BenutzerResult loadBenutzerByEmail(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                       @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                       @WebParam(name = "email", mode = WebParam.Mode.IN) final String email,
                                       @WebParam(name = "mandantengruppe", mode = WebParam.Mode.IN) final String mandantengruppe) {
        return createResult(getBenutzerDAO().loadBenutzerByEmail(email, mandantengruppe));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "changePasswd")
    @WebResult(name = "BenutzerResult")
    BenutzerResult changePasswd(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                @WebParam(name = "benutzerOID", mode = WebParam.Mode.IN) final Long benutzerOID,
                                @WebParam(name = "passwd", mode = WebParam.Mode.IN) final String passwd) {
        boolean valid = false;
        final Benutzer benutzer = getBenutzerDAO().findByID(benutzerOID);
        final List<LoggingEventEntry> loggingEventEntries = new ArrayList<>();
        if (benutzer != null) {
            if (passwd != null && !passwd.isEmpty()) {
                benutzer.setPasswd(passwd);
                daoFactoryBean.saveOrUpdate(benutzer);
                valid = true;
                loggingEventEntries.add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO,
                        "changePasswd", sessionid, "Das Passwort wurde erfolgreich geändert."));
            } else {
                loggingEventEntries.add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO,
                        "changePasswd", sessionid, "Das Passwort '" + passwd + "' ist ungültig."));
            }
        } else {
            loggingEventEntries.add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO,
                    "changePasswd", sessionid, "Es wurde kein Benutzer mit der OID " + benutzerOID + " gefunden."));
        }
        final BenutzerResult result = createResult(benutzer);
        result.setValid(valid);
        result.getLoggingEventEntries().addAll(loggingEventEntries);
        return result;
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "changeLogin")
    @WebResult(name = "BenutzerResult")
    BenutzerResult changeLogin(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                               @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                               @WebParam(name = "benutzerOID", mode = WebParam.Mode.IN) final Long benutzerOID,
                               @WebParam(name = "login", mode = WebParam.Mode.IN) final String login) {
        boolean valid = false;
        final BenutzerDAO benutzerDAO = getBenutzerDAO();
        final Benutzer benutzer = benutzerDAO.findByID(benutzerOID);
        final List<LoggingEventEntry> loggingEventEntries = new ArrayList<>();
        if (benutzer != null) {
            if (login != null && !login.isEmpty()) {
                if (!login.equals(benutzer.getLogin())) {
                    final Benutzer benutzerLogin = benutzerDAO.loadBenutzer(login, benutzer.getMandantengruppe().getId());
                    if (benutzerLogin == null) {
                        benutzer.setLogin(login);
                        daoFactoryBean.saveOrUpdate(benutzer);
                        valid = true;
                        loggingEventEntries.add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO,
                                "changeLogin", sessionid, "Das Login wurde erfolgreich geändert."));
                    } else {
                        loggingEventEntries.add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO,
                                "changeLogin", sessionid, "Das Login '" + login + "' wird bereits von einem anderen Benutzer verwendet."));
                    }
                } else {
                    valid = true;
                    loggingEventEntries.add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO,
                            "changeLogin", sessionid, "Das Login ist unverändert."));
                }
            } else {
                loggingEventEntries.add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO,
                        "changeLogin", sessionid, "Das Login '" + login + "' ist ungültig."));
            }
        } else {
            loggingEventEntries.add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO,
                    "changeLogin", sessionid, "Es wurde kein Benutzer mit der OID " + benutzerOID + " gefunden."));
        }
        final BenutzerResult result = createResult(benutzer);
        result.setValid(valid);
        result.getLoggingEventEntries().addAll(loggingEventEntries);
        return result;
    }

    public static class FlatBenutzer extends BaseFlatEntity {
        private Boolean hidden;
        private String login;
        private String passwd;
        //        private Date lastLogin;
//        private Integer failedLogins;
        //        private Date created;
        private Boolean active;
        //        private Date modified;
        private Date validFrom;
        private Date validUntil;
        private Long mandantengruppeOID;
        private Long benutzerGruppeOID;
        private Long benutzerWebapplikationOID;
        //private Person person;
//        private Long personOID;
        private List<Long> berechtigungOIDs = new ArrayList<>();


        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatBenutzer");
            sb.append("{active=").append(active);
            sb.append(", hidden=").append(hidden);
            sb.append(", login='").append(login).append('\'');
            sb.append(", passwd='").append(passwd).append('\'');
            sb.append(", validFrom=").append(validFrom);
            sb.append(", validUntil=").append(validUntil);
            sb.append(", mandantengruppeOID=").append(mandantengruppeOID);
            sb.append(", benutzerGruppeOID=").append(benutzerGruppeOID);
            sb.append(", benutzerWebapplikationOID=").append(benutzerWebapplikationOID);
//            sb.append(", personOID=").append(personOID);
            sb.append(", berechtigungOIDs=").append(berechtigungOIDs);
            sb.append('}');
            return sb.toString();
        }

//        public Long getPersonOID() {
//            return personOID;
//        }
//
//        public void setPersonOID(Long personOID) {
//            this.personOID = personOID;
//        }

        public Boolean getActive() {
            return active;
        }

        public void setActive(final Boolean active) {
            this.active = active;
        }

        public Long getBenutzerGruppeOID() {
            return benutzerGruppeOID;
        }

        public void setBenutzerGruppeOID(final Long benutzerGruppeOID) {
            this.benutzerGruppeOID = benutzerGruppeOID;
        }

        public Long getBenutzerWebapplikationOID() {
            return benutzerWebapplikationOID;
        }

        public void setBenutzerWebapplikationOID(final Long benutzerWebapplikationOID) {
            this.benutzerWebapplikationOID = benutzerWebapplikationOID;
        }

        public List<Long> getBerechtigungOIDs() {
            return berechtigungOIDs;
        }

        public void setBerechtigungOIDs(final List<Long> berechtigungOIDs) {
            this.berechtigungOIDs = berechtigungOIDs;
        }

        public Boolean getHidden() {
            return hidden;
        }

        public void setHidden(final Boolean hidden) {
            this.hidden = hidden;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(final String login) {
            this.login = login;
        }

        public Long getMandantengruppeOID() {
            return mandantengruppeOID;
        }

        public void setMandantengruppeOID(final Long mandantengruppeOID) {
            this.mandantengruppeOID = mandantengruppeOID;
        }

        public String getPasswd() {
            return passwd;
        }

        public void setPasswd(final String passwd) {
            this.passwd = passwd;
        }

        public Date getValidFrom() {
            return validFrom;
        }

        public void setValidFrom(final Date validFrom) {
            this.validFrom = validFrom;
        }

        public Date getValidUntil() {
            return validUntil;
        }

        public void setValidUntil(final Date validUntil) {
            this.validUntil = validUntil;
        }
    }

    public static class BenutzerResult extends BaseOrmResult<FlatBenutzer> {
        public List<FlatBenutzer> getObjList() {
            return objList;
        }

        public void setObjList(final List<FlatBenutzer> objList) {
            this.objList = objList;
        }
    }

    public static class CheckIfBenutzerLoginIsAvailableResult extends LoggingResult {
        private Boolean available;

        public Boolean getAvailable() {
            return available;
        }

        public void setAvailable(Boolean available) {
            this.available = available;
        }
    }

    public static class CheckIfBenutzerLoginIsActiveAndValidResult extends LoggingResult {
        private Boolean active;
        private Boolean valid;

        public Boolean getValid() {
            return valid;
        }

        public void setValid(final Boolean valid) {
            this.valid = valid;
        }

        public Boolean getActive() {
            return active;
        }

        public void setActive(final Boolean active) {
            this.active = active;
        }
    }
}
