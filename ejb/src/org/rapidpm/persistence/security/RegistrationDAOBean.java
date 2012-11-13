/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.persistence.security;

import org.apache.log4j.Logger;
import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.data.LoggingResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.logging.LoggerQualifier;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.bewegungsdaten.Registration;
import org.rapidpm.persistence.prj.bewegungsdaten.RegistrationDAO;
import org.rapidpm.persistence.prj.bewegungsdaten.RegistrationStatus;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.Taetigkeitsfeld;
import org.rapidpm.persistence.prj.stammdaten.person.Anrede;
import org.rapidpm.persistence.system.logging.LogLevelEnum;
import org.rapidpm.persistence.system.logging.LoggingEventEntry;
import org.rapidpm.persistence.system.security.BenutzerWebapplikation;
import org.rapidpm.persistence.system.security.Mandantengruppe;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.Collection;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 21.02.11
 * Time: 12:19
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */
@Stateless(name = "RegistrationDAOEJB")
@WebService(name = "RegistrationDAOWS")
public class RegistrationDAOBean {
    //private static final Logger logger = Logger.getLogger(RegistrationDAOBean.class);

    @Inject @LoggerQualifier
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    public RegistrationDAOBean() {
    }

    private CRUDExecuter<FlatRegistration, Registration, RegistrationResult> crudExecuter = new CRUDExecuter<FlatRegistration, Registration, RegistrationResult>(RegistrationResult.class) {
        @Override
        protected Registration flatType2Type(final FlatRegistration flatTypeEntity) {
            final Mandantengruppe mandantengruppeObj = daoFactoryBean.getMandantengruppeDAO().findByID(flatTypeEntity.getMandantengruppeOID());
            if (mandantengruppeObj != null) {
                final Long benutzerWebapplikation = flatTypeEntity.getBenutzerWebapplikationOID();
                final BenutzerWebapplikation benutzerWebapplikationObj = daoFactoryBean.getBenutzerWebapplikationDAO().findByID(benutzerWebapplikation);
                if (benutzerWebapplikationObj != null) {
                    final Long taetigkeitsfeld = flatTypeEntity.getTaetigkeitsfeldOID();
                    final Taetigkeitsfeld taetigkeitsfeldObj = daoFactoryBean.getTaetigkeitsfeldDAO().findByID(taetigkeitsfeld);
                    if (taetigkeitsfeldObj != null) {
                        final Long registrationStatus = flatTypeEntity.getRegistrationStatusOID();
                        final RegistrationStatus registrationStatusObj = daoFactoryBean.getRegistrationStatusDAO().findByID(registrationStatus);
                        if (registrationStatusObj != null) {
                            final Long anrede = flatTypeEntity.getAnredeOID();
                            Anrede anredeObj = daoFactoryBean.getAnredeDAO().findByID(anrede);
                            if (anredeObj != null) {
                                //
                            } else {
                                anredeObj = daoFactoryBean.getAnredeDAO().loadAnredeNothing();
                            }
                            final Long id = flatTypeEntity.getId();
                            Registration registration = findByID(id);
                            if (registration != null) {
                                //
                            } else {
                                registration = new Registration();
                            }

                            registration.setAnrede(anredeObj);
                            registration.setBenutzerWebapplikation(benutzerWebapplikationObj);
                            registration.setMandantengruppe(mandantengruppeObj);
                            registration.setTaetigkeitsfeld(taetigkeitsfeldObj);
                            registration.setRegistrationStatus(registrationStatusObj);

                            registration.setDurchwahl(flatTypeEntity.getDurchwahl());
                            registration.setEmail(flatTypeEntity.getEmail());
                            registration.setHausnr(flatTypeEntity.getHausnr());
                            registration.setLaendercode(flatTypeEntity.getLaendercode());
                            registration.setLogin(flatTypeEntity.getLogin());
                            registration.setNachname(flatTypeEntity.getNachname());
                            registration.setNummer(flatTypeEntity.getNummer());
                            registration.setOrt(flatTypeEntity.getOrt());
                            registration.setPlz(flatTypeEntity.getPlz());
                            registration.setPosition(flatTypeEntity.getPosition());
                            registration.setStrasse(flatTypeEntity.getStrasse());
                            registration.setTitel(flatTypeEntity.getTitel());
                            registration.setUnternehmen(flatTypeEntity.getUnternehmen());
                            registration.setVorname(flatTypeEntity.getVorname());
                            registration.setVorwahl(flatTypeEntity.getVorwahl());

                            return registration;
                        } else {
                            final LoggingEventEntry eventEntry = logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO,
                                    "flatType2Type",
                                    "",
                                    "kein " + "passender " + "Registrationsstatus:" + flatTypeEntity.getRegistrationStatusOID());
                            getLogEntries().add(eventEntry);
                        }
                    } else {
                        final LoggingEventEntry eventEntry = logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "flatType2Type", "", "kein " + "passendes " + "Taetigkeitsfeld:" + flatTypeEntity.getTaetigkeitsfeldOID());
                        getLogEntries().add(eventEntry);

                    }
                } else {
                    final LoggingEventEntry eventEntry = logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "flatType2Type", "", "keine " + "passende " + "WebApp:" + flatTypeEntity.getBenutzerWebapplikationOID());
                    getLogEntries().add(eventEntry);

                }
            } else {
                final LoggingEventEntry eventEntry = logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "flatType2Type", "", "keine " + "passende " + "Mandantengruppe:" + flatTypeEntity.getMandantengruppeOID());
                getLogEntries().add(eventEntry);

            }

            return null;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected Registration findByID(final Long oid) {
            return daoFactoryBean.getRegistrationDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    private RegistrationDAO getRegistrationDAO() {
        return daoFactoryBean.getRegistrationDAO();
    }

    private RegistrationResult createResult(final Registration r) {
        final RegistrationResult result = new RegistrationResult();
        if (r != null) {
            result.getObjList().add(r);
        } else {

        }
        return result;
    }

    private RegistrationResult createResult(final Collection<Registration> registrationen) {
        final RegistrationResult result = new RegistrationResult();
        result.getObjList().addAll(registrationen);
        return result;
    }

    @WebMethod(operationName = "loadRevisionFor")

    public
    @WebResult(name = "RegistrationResult")
    RegistrationResult loadRevisionFor(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final List<Registration> list = getRegistrationDAO().loadAllRevisionsFor(oid);
        return createResult(list);
    }


    @WebMethod(operationName = "checkIfLoginIsAvailable")

    public
    @WebResult(name = "CheckIfLoginIsAvailableResult")
    CheckIfLoginIsAvailableResult checkIfLoginIsAvailable(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                          @WebParam(name = "login", mode = WebParam.Mode.IN) final String login, @WebParam(name = "mandantengruppeOID", mode = WebParam.Mode.IN) final String mandantengruppe) {
        boolean b = getRegistrationDAO().checkIfLoginIsAvailable(login, mandantengruppe);
        final CheckIfLoginIsAvailableResult result = new CheckIfLoginIsAvailableResult();
        result.setAvailable(b);
        return result;
    }

    public static class CheckIfLoginIsAvailableResult extends LoggingResult {
        private Boolean available;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("CheckIfLoginIsAvailableResult");
            sb.append("{available=").append(available);
            sb.append('}');
            return sb.toString();
        }

        public Boolean getAvailable() {
            return available;
        }

        public void setAvailable(final Boolean available) {
            this.available = available;
        }
    }


    @WebMethod(operationName = "loadAllRegistrationForMandantengruppe")

    public
    @WebResult(name = "RegistrationResult")
    RegistrationResult loadAllRegistrationForMandantengruppe(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                             @WebParam(name = "mandantengruppeOID", mode = WebParam.Mode.IN)
                                                             final Long mandantengruppe) {
        final List<Registration> registrations = getRegistrationDAO().loadAllRegistrationForMandantengruppe(mandantengruppe);
        return createResult(registrations);
    }

    @WebMethod(operationName = "loadAllRegistrationForWebApp")

    public
    @WebResult(name = "RegistrationResult")
    RegistrationResult loadAllRegistrationForWebApp(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                    @WebParam(name = "webAppOID", mode = WebParam.Mode.IN) final Long webApp) {
        final List<Registration> registrations = getRegistrationDAO().loadAllRegistrationForWebApp(webApp);
        return createResult(registrations);
    }

    @WebMethod(operationName = "loadAllRegistrationForWebAppAndStatus")

    public
    @WebResult(name = "RegistrationResult")
    RegistrationResult loadAllRegistrationForWebApp(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                    @WebParam(name = "webAppOID", mode = WebParam.Mode.IN) final Long webApp, @WebParam(name = "statusOID", mode = WebParam.Mode.IN) final Long status) {
        final List<Registration> registrations = getRegistrationDAO().loadAllRegistrationForWebApp(webApp, status);
        return createResult(registrations);
    }

    @WebMethod(operationName = "loadAllRegistrationForWebAppAndStatuslist")

    public
    @WebResult(name = "RegistrationResult")
    RegistrationResult loadAllRegistrationForWebApp(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                    @WebParam(name = "webAppOID", mode = WebParam.Mode.IN) final Long webApp, @WebParam(name = "statusOIDs", mode = WebParam.Mode.IN) final List<Long> statusListe) {
        final List<Registration> registrations = getRegistrationDAO().loadAllRegistrationForWebApp(webApp, statusListe);
        return createResult(registrations);
    }

    @WebMethod(operationName = "loadAllRegistrationForWebAppWithoutAccepted")

    public
    @WebResult(name = "RegistrationResult")
    RegistrationResult loadAllRegistrationForWebAppWithoutAccepted(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                                   @WebParam(name = "webAppOID", mode = WebParam.Mode.IN) final Long webApp) {
        final List<Registration> registrations = getRegistrationDAO().loadAllRegistrationForWebAppWithoutAccepted(webApp);
        return createResult(registrations);
    }

    @WebMethod(operationName = "loadAllRegistrationForWebAppAndAproval")

    public
    @WebResult(name = "RegistrationResult")
    RegistrationResult loadAllRegistrationForWebAppAndAproval(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                              @WebParam(name = "webAppOID", mode = WebParam.Mode.IN) final Long webApp) {
        return createResult(getRegistrationDAO().loadAllRegistrationForWebAppAndAproval(webApp));
    }

    @WebMethod(operationName = "loadAllRegistrationWithoutAccepted")

    public
    @WebResult(name = "RegistrationResult")
    RegistrationResult loadAllRegistrationWithoutAccepted(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getRegistrationDAO().loadAllRegistrationWithoutAccepted());
    }

    @WebMethod(operationName = "loadAllRegistrationForAproval")

    public
    @WebResult(name = "RegistrationResult")
    RegistrationResult loadAllRegistrationForAproval(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getRegistrationDAO().loadAllRegistrationForAproval());
    }



    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "RegistrationResult")
    RegistrationResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                      @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatRegistration entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }


    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "RegistrationResult")
    RegistrationResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }


    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "RegistrationResult")
    RegistrationResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final Registration byID = getRegistrationDAO().findByID(oid);
        if (byID == null) {
            return new RegistrationResult();
        } else {
            return createResult(byID);
        }
    }


    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "RegistrationResult")
    RegistrationResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                       @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getRegistrationDAO().loadWithOIDList(oids));
    }


    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "RegistrationResult")
    RegistrationResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getRegistrationDAO().loadAllEntities());
    }


    public static class RegistrationResult extends BaseOrmResult<Registration> {
        //        private List<Registration> registrationen = new ArrayList<Registration>();
        //
        //        public List<Registration> getRegistrationen(){
        //            return registrationen;
        //        }
        //
        //        public void setRegistrationen(List<Registration> registrationen){
        //            this.registrationen = registrationen;
        //        }

        public List<Registration> getObjList() {
            return objList;
        }

        public void setObjList(final List<Registration> objList) {
            this.objList = objList;
        }
    }

    public static class FlatRegistration extends BaseFlatEntity {
        private String login;
        private String vorname;
        private String nachname;
        private String unternehmen;
        private String position;
        private String email;
        private String plz;
        private String ort;
        private String laendercode;
        private String vorwahl;
        private String nummer;
        private String durchwahl;
        private String titel;
        private String strasse;
        private String hausnr;


        private Long registrationStatusOID;
        private Long taetigkeitsfeldOID;
        private Long anredeOID;
        private Long mandantengruppeOID;
        private Long benutzerWebapplikationOID;

        public Long getAnredeOID() {
            return anredeOID;
        }

        public void setAnredeOID(final Long anredeOID) {
            this.anredeOID = anredeOID;
        }

        public Long getBenutzerWebapplikationOID() {
            return benutzerWebapplikationOID;
        }

        public void setBenutzerWebapplikationOID(final Long benutzerWebapplikationOID) {
            this.benutzerWebapplikationOID = benutzerWebapplikationOID;
        }

        public String getDurchwahl() {
            return durchwahl;
        }

        public void setDurchwahl(final String durchwahl) {
            this.durchwahl = durchwahl;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(final String email) {
            this.email = email;
        }

        public Long getTaetigkeitsfeldOID() {
            return taetigkeitsfeldOID;
        }

        public void setTaetigkeitsfeldOID(final Long taetigkeitsfeldOID) {
            this.taetigkeitsfeldOID = taetigkeitsfeldOID;
        }

        public String getHausnr() {
            return hausnr;
        }

        public void setHausnr(final String hausnr) {
            this.hausnr = hausnr;
        }

        public String getLaendercode() {
            return laendercode;
        }

        public void setLaendercode(final String laendercode) {
            this.laendercode = laendercode;
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

        public String getNachname() {
            return nachname;
        }

        public void setNachname(final String nachname) {
            this.nachname = nachname;
        }

        public String getNummer() {
            return nummer;
        }

        public void setNummer(final String nummer) {
            this.nummer = nummer;
        }

        public String getOrt() {
            return ort;
        }

        public void setOrt(final String ort) {
            this.ort = ort;
        }

        public String getPlz() {
            return plz;
        }

        public void setPlz(final String plz) {
            this.plz = plz;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(final String position) {
            this.position = position;
        }

        public Long getRegistrationStatusOID() {
            return registrationStatusOID;
        }

        public void setRegistrationStatusOID(final Long registrationStatusOID) {
            this.registrationStatusOID = registrationStatusOID;
        }

        public String getStrasse() {
            return strasse;
        }

        public void setStrasse(final String strasse) {
            this.strasse = strasse;
        }

        public String getTitel() {
            return titel;
        }

        public void setTitel(final String titel) {
            this.titel = titel;
        }

        public String getUnternehmen() {
            return unternehmen;
        }

        public void setUnternehmen(final String unternehmen) {
            this.unternehmen = unternehmen;
        }

        public String getVorname() {
            return vorname;
        }

        public void setVorname(final String vorname) {
            this.vorname = vorname;
        }

        public String getVorwahl() {
            return vorwahl;
        }

        public void setVorwahl(final String vorwahl) {
            this.vorwahl = vorwahl;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatRegistration");
            sb.append(", login='").append(login).append('\'');
            sb.append(", vorname='").append(vorname).append('\'');
            sb.append(", nachname='").append(nachname).append('\'');
            sb.append(", unternehmen='").append(unternehmen).append('\'');
            sb.append(", position='").append(position).append('\'');
            sb.append(", email='").append(email).append('\'');
            sb.append(", plz='").append(plz).append('\'');
            sb.append(", ort='").append(ort).append('\'');
            sb.append(", laendercode='").append(laendercode).append('\'');
            sb.append(", vorwahl='").append(vorwahl).append('\'');
            sb.append(", nummer='").append(nummer).append('\'');
            sb.append(", durchwahl='").append(durchwahl).append('\'');
            sb.append(", titel='").append(titel).append('\'');
            sb.append(", strasse='").append(strasse).append('\'');
            sb.append(", hausnr='").append(hausnr).append('\'');
            sb.append(", registrationStatus='").append(registrationStatusOID).append('\'');
            sb.append(", taetigkeitsfeld='").append(taetigkeitsfeldOID).append('\'');
            sb.append(", anredeOID='").append(anredeOID).append('\'');
            sb.append(", mandantengruppe='").append(mandantengruppeOID).append('\'');
            sb.append(", benutzerWebapplikationOID='").append(benutzerWebapplikationOID).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

}
