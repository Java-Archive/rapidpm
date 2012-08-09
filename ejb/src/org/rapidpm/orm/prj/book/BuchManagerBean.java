package org.rapidpm.orm.prj.book;

import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.interceptor.LoggingInterceptor;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.orm.prj.book.kommentar.*;
import org.rapidpm.orm.prj.stammdaten.DaoFactoryBean;
import org.rapidpm.orm.system.logging.LogLevelEnum;
import org.rapidpm.orm.system.logging.LoggingEventEntry;
import org.rapidpm.orm.system.security.Benutzer;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 18:12
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */
@Stateless(name = "BuchManagerEJB")
@WebService(name = "BuchManagerWS")
public class BuchManagerBean {
    public BuchManagerBean() {

    }

    @Inject
    private transient Logger logger;


    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;
    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    @WebMethod(action = "loadBuchFor", operationName = "loadBuchFor")
    public
    @WebResult(name = "Buch")
    Buch loadBuchFor(final Long oid) {
        return createDummy();

    }

    private BuchDAO getBuchDAO() {
        return daoFactoryBean.getBuchDAO();
    }

    private BuchKapitelDAO getBuchKapitelDAO() {
        return daoFactoryBean.getBuchKapitelDAO();
    }

    private BuchSeiteDAO getBuchSeiteDAO() {
        return daoFactoryBean.getBuchSeiteDAO();
    }

    private BuchSeitenFussnoteDAO getBuchSeitenFussnoteDAO() {
        return daoFactoryBean.getBuchSeitenFussnoteDAO();
    }

    private BuchKommentarDAO getBuchKommentarDAO() {
        return daoFactoryBean.getBuchKommentarDAO();
    }

    private BuchKapitelKommentarDAO getBuchKapitelKommentarDAO() {
        return daoFactoryBean.getBuchKapitelKommentarDAO();
    }

    private BuchSeitenKommentarDAO getBuchSeitenKommentarDAO() {
        return daoFactoryBean.getBuchSeitenKommentarDAO();
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTXBuchSeitenKommentar")
    @WebResult(name = "BuchSeitenKommentarResult")
    BuchSeitenKommentarResult saveOrUpdateTXBuchSeitenKommentar(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                                @WebParam(name = "entity", mode = WebParam.Mode.IN) final BuchSeitenKommentar entity) {
        daoFactoryBean.saveOrUpdate(entity);
        return new BuchSeitenKommentarResult();
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTXBuchSeitenKommentar")
    @WebResult(name = "BuchSeitenKommentarResult")
    BuchSeitenKommentarResult removeTXBuchSeitenKommentar(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                          @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final BuchSeitenKommentarResult result = new BuchSeitenKommentarResult();
        final BuchSeitenKommentar byID = daoFactoryBean.getBuchSeitenKommentarDAO().findByID(oid);
        if (byID != null) {
            daoFactoryBean.remove(byID);
            final LoggingEventEntry eventEntry = logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "remove OK");
            result.getLoggingEventEntries().add(eventEntry);
        } else {

            result.getLoggingEventEntries().add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "oid falsch"));
            result.getLoggingEventEntries().add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "remove FAILED"));
        }
        return result;
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByIDBuchSeitenKommentar")
    @WebResult(name = "BuchSeitenKommentarResult")
    BuchSeitenKommentarResult findByIDBuchSeitenKommentar(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                          @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return createResult(getBuchSeitenKommentarDAO().findByID(oid));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDListBuchSeitenKommentar")
    @WebResult(name = "BuchSeitenKommentarResult")
    BuchSeitenKommentarResult loadWithOIDListBuchSeitenKommentar(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                                 @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResultBSK(getBuchSeitenKommentarDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntitiesBuchSeitenKommentar")
    @WebResult(name = "BuchSeitenKommentarResult")
    BuchSeitenKommentarResult loadAllEntitiesBuchSeitenKommentar(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResultBSK(getBuchSeitenKommentarDAO().loadAllEntities());
    }

    //BuchKapitelKommentarResult

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTXBuchKapitelKommentar")
    @WebResult(name = "BuchKapitelKommentarResult")
    BuchKapitelKommentarResult saveOrUpdateTXBuchKapitelKommentar(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                                  @WebParam(name = "entity", mode = WebParam.Mode.IN)
                                                                  final BuchKapitelKommentar entity) {
        daoFactoryBean.saveOrUpdate(entity);
        return new BuchKapitelKommentarResult();
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTXBuchKapitelKommentar")
    @WebResult(name = "BuchKapitelKommentarResult")
    BuchKapitelKommentarResult removeTXBuchKapitelKommentar(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                            @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final BuchKapitelKommentarResult result = new BuchKapitelKommentarResult();
        final BuchKapitelKommentar byID = daoFactoryBean.getBuchKapitelKommentarDAO().findByID(oid);
        if (byID != null) {
            daoFactoryBean.remove(byID);
            final LoggingEventEntry eventEntry = logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "remove OK");
            result.getLoggingEventEntries().add(eventEntry);
        } else {

            result.getLoggingEventEntries().add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "oid falsch"));
            result.getLoggingEventEntries().add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "remove FAILED"));
        }
        return result;
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByIDBuchKapitelKommentar")
    @WebResult(name = "BuchKapitelKommentarResult")
    BuchKapitelKommentarResult findByIDBuchKapitelKommentar(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                            @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return createResult(getBuchKapitelKommentarDAO().findByID(oid));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDListBuchKapitelKommentar")
    @WebResult(name = "BuchKapitelKommentarResult")
    BuchKapitelKommentarResult loadWithOIDListBuchKapitelKommentar(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                                   @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResultBKK(getBuchKapitelKommentarDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntitiesBuchKapitelKommentar")
    @WebResult(name = "BuchKapitelKommentarResult")
    BuchKapitelKommentarResult loadAllEntitiesBuchKapitelKommentar(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResultBKK(getBuchKapitelKommentarDAO().loadAllEntities());
    }

    // BuchKommentarResult
    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTXBuchKommentar")
    @WebResult(name = "BuchKommentarResult")
    BuchKommentarResult saveOrUpdateTXBuchKommentar(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                    @WebParam(name = "entity", mode = WebParam.Mode.IN) final BuchKommentar entity) {
        daoFactoryBean.saveOrUpdate(entity);
        return new BuchKommentarResult();
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTXBuchKommentar")
    @WebResult(name = "BuchKommentarResult")
    BuchKommentarResult removeTXBuchKommentar(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                              @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final BuchKommentarResult result = new BuchKommentarResult();
        final BuchKommentar byID = daoFactoryBean.getBuchKommentarDAO().findByID(oid);
        if (byID != null) {
            daoFactoryBean.remove(byID);
            final LoggingEventEntry eventEntry = logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "remove OK");
            result.getLoggingEventEntries().add(eventEntry);
        } else {

            result.getLoggingEventEntries().add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "oid falsch"));
            result.getLoggingEventEntries().add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "remove FAILED"));
        }
        return result;
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByIDBuchKommentar")
    @WebResult(name = "BuchKommentarResult")
    BuchKommentarResult findByIDBuchKommentar(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                              @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return createResult(getBuchKommentarDAO().findByID(oid));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDListBuchKommentar")
    @WebResult(name = "BuchKommentarResult")
    BuchKommentarResult loadWithOIDListBuchKommentar(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                     @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResultBK(getBuchKommentarDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntitiesBuchKommentar")
    @WebResult(name = "BuchKommentarResult")
    BuchKommentarResult loadAllEntitiesBuchKommentar(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResultBK(getBuchKommentarDAO().loadAllEntities());
    }


    // BuchSeitenFussnoteResult
    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTXBuchSeitenFussnote")
    @WebResult(name = "BuchSeitenFussnoteResult")
    BuchSeitenFussnoteResult saveOrUpdateTXBuchSeitenFussnote(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                              @WebParam(name = "entity", mode = WebParam.Mode.IN) final BuchSeitenFussnote entity) {
        daoFactoryBean.saveOrUpdate(entity);
        return new BuchSeitenFussnoteResult();
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTXBuchSeitenFussnote")
    @WebResult(name = "BuchSeitenFussnoteResult")
    BuchSeitenFussnoteResult removeTXBuchSeitenFussnote(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                        @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final BuchSeitenFussnoteResult result = new BuchSeitenFussnoteResult();
        final BuchSeitenFussnote byID = daoFactoryBean.getBuchSeitenFussnoteDAO().findByID(oid);
        if (byID != null) {
            daoFactoryBean.remove(byID);
            final LoggingEventEntry eventEntry = logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "remove OK");
            result.getLoggingEventEntries().add(eventEntry);
        } else {

            result.getLoggingEventEntries().add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "oid falsch"));
            result.getLoggingEventEntries().add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "remove FAILED"));
        }
        return result;
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByIDBuchSeitenFussnote")
    @WebResult(name = "BuchSeitenFussnoteResult")
    BuchSeitenFussnoteResult findByIDBuchSeitenFussnote(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                        @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return createResult(getBuchSeitenFussnoteDAO().findByID(oid));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDListBuchSeitenFussnote")
    @WebResult(name = "BuchSeitenFussnoteResult")
    BuchSeitenFussnoteResult loadWithOIDListBuchSeitenFussnote(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                               @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResultBSF(getBuchSeitenFussnoteDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntitiesBuchSeitenFussnote")
    @WebResult(name = "BuchSeitenFussnoteResult")
    BuchSeitenFussnoteResult loadAllEntitiesBuchSeitenFussnote(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResultBSF(getBuchSeitenFussnoteDAO().loadAllEntities());
    }

    // BuchSeiteResult
    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTXBuchSeiten")
    @WebResult(name = "BuchSeiteResult")
    BuchSeiteResult saveOrUpdateTXBuchSeite(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                            @WebParam(name = "entity", mode = WebParam.Mode.IN) final BuchSeite entity) {
        daoFactoryBean.saveOrUpdate(entity);
        return new BuchSeiteResult();
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTXBuchSeite")
    @WebResult(name = "BuchSeiteResult")
    BuchSeiteResult removeTXBuchSeite(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final BuchSeiteResult result = new BuchSeiteResult();
        final BuchSeite byID = daoFactoryBean.getBuchSeiteDAO().findByID(oid);
        if (byID != null) {
            daoFactoryBean.remove(byID);
            final LoggingEventEntry eventEntry = logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "remove OK");
            result.getLoggingEventEntries().add(eventEntry);
        } else {

            result.getLoggingEventEntries().add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "oid falsch"));
            result.getLoggingEventEntries().add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "remove FAILED"));
        }
        return result;
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByIDBuchSeiten")
    @WebResult(name = "BuchSeiteResult")
    BuchSeiteResult findByIDBuchSeite(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return createResult(getBuchSeiteDAO().findByID(oid));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDListBuchSeite")
    @WebResult(name = "BuchSeiteResult")
    BuchSeiteResult loadWithOIDListBuchSeite(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                             @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResultBS(getBuchSeiteDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntitiesBuchSeite")
    @WebResult(name = "BuchSeiteResult")
    BuchSeiteResult loadAllEntitiesBuchSeite(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResultBS(getBuchSeiteDAO().loadAllEntities());
    }


    // BuchResult
    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTXBuch")
    @WebResult(name = "BuchResult")
    BuchResult saveOrUpdateTXBuch(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                  @WebParam(name = "entity", mode = WebParam.Mode.IN) final BuchSeite entity) {
        daoFactoryBean.saveOrUpdate(entity);
        return new BuchResult();
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTXBuch")
    @WebResult(name = "BuchResult")
    BuchResult removeTXBuch(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final BuchResult result = new BuchResult();
        final Buch byID = daoFactoryBean.getBuchDAO().findByID(oid);
        if (byID != null) {
            daoFactoryBean.remove(byID);
            final LoggingEventEntry eventEntry = logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "remove OK");
            result.getLoggingEventEntries().add(eventEntry);
        } else {

            result.getLoggingEventEntries().add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "oid falsch"));
            result.getLoggingEventEntries().add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "remove FAILED"));
        }
        return result;
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByIDBuch")
    @WebResult(name = "BuchResult")
    BuchResult findByIDBuch(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return createResult(getBuchDAO().findByID(oid));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDListBuch")
    @WebResult(name = "BuchResult")
    BuchResult loadWithOIDListBuch(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                   @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResultB(getBuchDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntitiesBuch")
    @WebResult(name = "BuchResult")
    BuchResult loadAllEntitiesBuch(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResultB(getBuchDAO().loadAllEntities());
    }


    private BuchSeitenFussnoteResult createResult(@NotNull BuchSeitenFussnote fussnote) {
        final BuchSeitenFussnoteResult result = new BuchSeitenFussnoteResult();
        //        for(final BuchSeitenFussnote b : fussnoten){
        result.getObjList().add(fussnote);
        //        }
        return result;
    }

    private BuchSeitenFussnoteResult createResultBSF(final List<BuchSeitenFussnote> fussnoten) {
        final BuchSeitenFussnoteResult result = new BuchSeitenFussnoteResult();
        result.getObjList().addAll(fussnoten);
        return result;
    }

    private BuchKommentarResult createResult(@NotNull BuchKommentar kommentar) {
        final BuchKommentarResult result = new BuchKommentarResult();
        //        for(final BuchKommentar b : kommentare){
        result.getObjList().add(kommentar);
        //        }
        return result;
    }

    private BuchKommentarResult createResultBK(final List<BuchKommentar> kommentar) {
        final BuchKommentarResult result = new BuchKommentarResult();
        result.getObjList().addAll(kommentar);
        return result;
    }

    private BuchKapitelKommentarResult createResult(@NotNull BuchKapitelKommentar kommentar) {
        final BuchKapitelKommentarResult result = new BuchKapitelKommentarResult();
        //        for(final BuchKapitelKommentar b : kommentare){
        result.getObjList().add(kommentar);
        //        }
        return result;
    }

    private BuchKapitelKommentarResult createResultBKK(final List<BuchKapitelKommentar> kommentar) {
        final BuchKapitelKommentarResult result = new BuchKapitelKommentarResult();
        result.getObjList().addAll(kommentar);
        return result;
    }

    private BuchSeitenKommentarResult createResult(@NotNull BuchSeitenKommentar kommentar) {
        final BuchSeitenKommentarResult result = new BuchSeitenKommentarResult();
        //        for(final BuchSeitenKommentar b : kommentare){
        result.getObjList().add(kommentar);
        //        }
        return result;
    }

    private BuchSeitenKommentarResult createResultBSK(final List<BuchSeitenKommentar> kommentar) {
        final BuchSeitenKommentarResult result = new BuchSeitenKommentarResult();
        result.getObjList().addAll(kommentar);
        return result;
    }

    private BuchSeiteResult createResult(@NotNull BuchSeite seite) {
        final BuchSeiteResult result = new BuchSeiteResult();
        //        for(final BuchSeite b : seiten){
        result.getObjList().add(seite);
        //        }
        return result;
    }

    private BuchSeiteResult createResultBS(final List<BuchSeite> seiten) {
        final BuchSeiteResult result = new BuchSeiteResult();
        result.getObjList().addAll(seiten);
        return result;
    }

    private BuchResult createResult(@NotNull Buch buch) {
        final BuchResult result = new BuchResult();
        //        for(final Buch b : buecher){
        result.getObjList().add(buch);
        //        }
        return result;
    }

    private BuchResult createResultB(final List<Buch> buecher) {
        final BuchResult result = new BuchResult();
        result.getObjList().addAll(buecher);
        return result;
    }


    public static class BuchResult extends BaseOrmResult<Buch> {
        //        private List<Buch> buchliste = new ArrayList<Buch>();
        //
        //        public List<Buch> getBuchliste(){
        //            return buchliste;
        //        }
        //
        //        public void setBuchliste(final List<Buch> buchliste){
        //            this.buchliste = buchliste;
        //        }

        public List<Buch> getObjList() {
            return objList;
        }

        public void setObjList(final List<Buch> objList) {
            this.objList = objList;
        }
    }

    public static class BuchSeiteResult extends BaseOrmResult<BuchSeite> {
        //        private List<BuchSeite> seiten = new ArrayList<BuchSeite>();
        //
        //        public List<BuchSeite> getSeiten(){
        //            return seiten;
        //        }
        //
        //        public void setSeiten(final List<BuchSeite> seiten){
        //            this.seiten = seiten;
        //        }

        public List<BuchSeite> getObjList() {
            return objList;
        }

        public void setObjList(final List<BuchSeite> objList) {
            this.objList = objList;
        }
    }

    public static class BuchSeitenFussnoteResult extends BaseOrmResult<BuchSeitenFussnote> {
        //        private List<BuchSeitenFussnote> fussnoten = new ArrayList<BuchSeitenFussnote>();
        //
        //        public List<BuchSeitenFussnote> getFussnoten(){
        //            return fussnoten;
        //        }
        //
        //        public void setFussnoten(final List<BuchSeitenFussnote> fussnoten){
        //            this.fussnoten = fussnoten;
        //        }

        public List<BuchSeitenFussnote> getObjList() {
            return objList;
        }

        public void setObjList(final List<BuchSeitenFussnote> objList) {
            this.objList = objList;
        }
    }

    public static class BuchKommentarResult extends BaseOrmResult<BuchKommentar> {
        //        private List<BuchKommentar> kommentare = new ArrayList<BuchKommentar>();
        //
        //        public List<BuchKommentar> getKommentare(){
        //            return kommentare;
        //        }
        //
        //        public void setKommentare(final List<BuchKommentar> kommentare){
        //            this.kommentare = kommentare;
        //        }

        public List<BuchKommentar> getObjList() {
            return objList;
        }

        public void setObjList(final List<BuchKommentar> objList) {
            this.objList = objList;
        }
    }

    public static class BuchKapitelKommentarResult extends BaseOrmResult<BuchKapitelKommentar> {
        //        private List<BuchKapitelKommentar> kommentare = new ArrayList<BuchKapitelKommentar>();
        //
        //        public List<BuchKapitelKommentar> getKommentare(){
        //            return kommentare;
        //        }
        //
        //        public void setKommentare(final List<BuchKapitelKommentar> kommentare){
        //            this.kommentare = kommentare;
        //        }

        public List<BuchKapitelKommentar> getObjList() {
            return objList;
        }

        public void setObjList(final List<BuchKapitelKommentar> objList) {
            this.objList = objList;
        }
    }

    public static class BuchSeitenKommentarResult extends BaseOrmResult<BuchSeitenKommentar> {
        //        private List<BuchSeitenKommentar> kommentare = new ArrayList<BuchSeitenKommentar>();
        //
        //        public List<BuchSeitenKommentar> getKommentare(){
        //            return kommentare;
        //        }
        //
        //        public void setKommentare(final List<BuchSeitenKommentar> kommentare){
        //            this.kommentare = kommentare;
        //        }

        public List<BuchSeitenKommentar> getObjList() {
            return objList;
        }

        public void setObjList(final List<BuchSeitenKommentar> objList) {
            this.objList = objList;
        }
    }


    private Buch createDummy() {

        final Buch buch = new Buch();

        final Benutzer benutzer = daoFactoryBean.getBenutzerDAO().loadBenutzer("sven.ruppert", "Netzwerk Draht");
        buch.setAutorenliste(new ArrayList<>(Arrays.asList(benutzer)));
        buch.setLeserliste(new ArrayList<>(Arrays.asList(benutzer)));

        buch.setSummary("Das ist das Summary..");
        buch.setTitel("Das ist der Titel");
        buch.setUntertitel("Das ist der Untertitel");
        buch.setVersion("Version 0001");

        final BuchKommentar buchKommentar = new BuchKommentar();
        buchKommentar.setDatum(new Date());
        buchKommentar.setKommentar("Ein Buchkommentar");
        buchKommentar.setKommentator(benutzer);
        final List<BuchKommentar> buchKommentarList = new ArrayList<>();
        buchKommentarList.add(buchKommentar);
        buch.setKommentarliste(buchKommentarList);


        final List<BuchKapitel> buchKapitelList = new ArrayList<>();
        buch.setBuchKapitelListe(buchKapitelList);


        final BuchKapitel buchKapitel = new BuchKapitel();
        buchKapitel.setFreigeschaltet(true);
        final BuchKapitelKommentar buchKapitelKommentar = new BuchKapitelKommentar();
        buchKapitelKommentar.setDatum(new Date());
        buchKapitelKommentar.setKommentar("BuchKapitelKommentar");
        buchKapitelKommentar.setKommentator(benutzer);

        final List<BuchKapitelKommentar> buchKapitelKommentarList = new ArrayList<>();
        buchKapitelKommentarList.add(buchKapitelKommentar);
        buchKapitel.setKapitelkommentarliste(buchKapitelKommentarList);

        final List<BuchSeite> buchSeiteList = new ArrayList<>();

        final BuchSeite buchSeite = new BuchSeite();

        final List<BuchAbsatz> buchAbsatzList = new ArrayList<>();
        final BuchAbsatz buchAbsatz = new BuchAbsatz();
        buchAbsatz.setAbsatznummer(1);
        buchAbsatz.setFreigeschaltet(true);

        final BuchAbsatzKommentar buchAbsatzKommentar = new BuchAbsatzKommentar();
        buchAbsatzKommentar.setDatum(new Date());
        buchAbsatzKommentar.setKommentar("blabla");
        buchAbsatzKommentar.setKommentator(benutzer);
        final List<BuchAbsatzKommentar> buchAbsatzKommentarList = new ArrayList<>();
        buchAbsatzKommentarList.add(buchAbsatzKommentar);

        buchAbsatz.setKommentarliste(buchAbsatzKommentarList);

        buchAbsatz.setText("Das ist der AbsatzText...");
        buchAbsatzList.add(buchAbsatz);
        buchSeite.setAbsatzliste(buchAbsatzList);

        buchSeite.setFreigeschaltet(true);
        final List<BuchSeitenFussnote> buchSeitenFussnoteListe = new ArrayList<>();

        final BuchSeitenFussnote buchSeitenFussnote = new BuchSeitenFussnote();
        buchSeitenFussnote.setFussnotentext("Fussnotentext");
        buchSeitenFussnote.setFussnotenzeichen("HJHJ");
        buchSeitenFussnoteListe.add(buchSeitenFussnote);
        buchSeite.setFusnotenliste(buchSeitenFussnoteListe);


        final List<BuchSeitenKommentar> buchSeitenKommentarListe = new ArrayList<>();
        final BuchSeitenKommentar buchSeitenKommentar = new BuchSeitenKommentar();
        buchSeitenKommentar.setDatum(new Date());
        buchSeitenKommentar.setKommentar("Das ist der Kommentar");
        buchSeitenKommentar.setKommentator(benutzer);
        buchSeitenKommentarListe.add(buchSeitenKommentar);

        buchSeite.setKommentarliste(buchSeitenKommentarListe);
        buchSeite.setSeitennummer(1);
        buchSeiteList.add(buchSeite);


        buchKapitel.setSeitenliste(buchSeiteList);
        buchKapitel.setUeberschrift("KapitelÜberschrift");
        buchKapitel.setKapitelnummer(1);
        buchKapitel.setUntertitel("Kapiteluntertitel..");

        buch.getBuchKapitelListe().add(buchKapitel);
        return buch;

    }


}
