/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.orm.prj.stammdaten.kommunikation;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 19.09.11
 * Time: 00:40
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.ejb3.interceptor.LoggingInterceptor;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.orm.DaoFactoryBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.Collection;
import java.util.List;

@Stateless(name = "KommunikationsServiceUIDPartDAOEJB")
@WebService(name = "KommunikationsServiceUIDPartDAOWS")
public class KommunikationsServiceUIDPartDAOBean {
    //private static final Logger logger = Logger.getLogger(KommunikationsServiceUIDPartDAOBean.class);

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private
    CRUDExecuter<FlatKommunikationsServiceUIDPart, KommunikationsServiceUIDPart, KommunikationsServiceUIDPartResult>
            crudExecuter =
            new CRUDExecuter<FlatKommunikationsServiceUIDPart, KommunikationsServiceUIDPart, KommunikationsServiceUIDPartResult>(KommunikationsServiceUIDPartResult.class) {
                @Override
                protected KommunikationsServiceUIDPart flatType2Type(final FlatKommunikationsServiceUIDPart flatTypeEntity) {
                    final Long id = flatTypeEntity.getId();
                    final KommunikationsServiceUIDPart typeObj;
                    if (id == null || id == -1) {
                        typeObj = new KommunikationsServiceUIDPart();
                    } else {
                        typeObj = findByID(id);
                    }
                    typeObj.setKlassifizierung(daoFactoryBean.getKommunikationsServiceUIDPartKlassifikationDAO().findByID(flatTypeEntity.getKommunikationsServiceUIDPartKlassifizierungOID()));
                    typeObj.setOrderNr(flatTypeEntity.getOrderNr());
                    typeObj.setUidPart(flatTypeEntity.getUidPart());

                    return typeObj;
                }

                @Override
                protected DaoFactoryBean getDaoFactoryBean() {
                    return daoFactoryBean;
                }

                @Override
                protected KommunikationsServiceUIDPart findByID(final Long oid) {
                    return getEntityDAO().findByID(oid);
                }

                @Override
                protected LogEventEntryWriterBean getLogger() {
                    return logEventEntryWriterBean;
                }
            };

    private KommunikationsServiceUIDPartDAO getEntityDAO() {
        return daoFactoryBean.getKommunikationServiceUIDPartDAO();
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "KommunikationsServiceUIDPartResult")
    KommunikationsServiceUIDPartResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                      @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                      @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatKommunikationsServiceUIDPart entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "KommunikationsServiceUIDPartResult")
    KommunikationsServiceUIDPartResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                @WebParam(name = "entity", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "KommunikationsServiceUIDPartResult")
    KommunikationsServiceUIDPartResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final KommunikationsServiceUIDPart byID = getEntityDAO().findByID(oid);
        if (byID == null) {
            return new KommunikationsServiceUIDPartResult();
        } else {
            return createResult(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "KommunikationsServiceUIDPartResult")
    KommunikationsServiceUIDPartResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                       @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                       @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "KommunikationsServiceUIDPartResult")
    KommunikationsServiceUIDPartResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                       @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    private KommunikationsServiceUIDPartResult createResult(final KommunikationsServiceUIDPart... typeObj) {
        final KommunikationsServiceUIDPartResult result = new KommunikationsServiceUIDPartResult();
        for (final KommunikationsServiceUIDPart obj : typeObj) {
            result.getObjList().add(type2FlatType(obj));
        }
        return result;
    }

    private KommunikationsServiceUIDPartResult createResult(final Collection<KommunikationsServiceUIDPart> typeObj) {
        final KommunikationsServiceUIDPartResult result = new KommunikationsServiceUIDPartResult();
        for (final KommunikationsServiceUIDPart obj : typeObj) {
            result.getObjList().add(type2FlatType(obj));
        }
        return result;
    }

    private FlatKommunikationsServiceUIDPart type2FlatType(final KommunikationsServiceUIDPart t) {
        final FlatKommunikationsServiceUIDPart ft = new FlatKommunikationsServiceUIDPart();
        ft.setId(t.getId());
        ft.setKommunikationsServiceUIDPartKlassifizierungOID(t.getKlassifizierung().getId());
        ft.setOrderNr(t.getOrderNr());
        ft.setUidPart(t.getUidPart());
        return ft;
    }

    public static class FlatKommunikationsServiceUIDPart extends BaseFlatEntity {
        private Long id;
        private int orderNr;
        private String uidPart;
        private Long kommunikationsServiceUIDPartKlassifizierungOID;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getKommunikationsServiceUIDPartKlassifizierungOID() {
            return kommunikationsServiceUIDPartKlassifizierungOID;
        }

        public void setKommunikationsServiceUIDPartKlassifizierungOID(Long kommunikationsServiceUIDPartKlassifizierungOID) {
            this.kommunikationsServiceUIDPartKlassifizierungOID = kommunikationsServiceUIDPartKlassifizierungOID;
        }

        public int getOrderNr() {
            return orderNr;
        }

        public void setOrderNr(int orderNr) {
            this.orderNr = orderNr;
        }

        public String getUidPart() {
            return uidPart;
        }

        public void setUidPart(String uidPart) {
            this.uidPart = uidPart;
        }
    }

    public static class KommunikationsServiceUIDPartResult extends BaseOrmResult<FlatKommunikationsServiceUIDPart> {
        //private List<KommunikationsServiceUIDPart> objList = new ArrayList<KommunikationsServiceUIDPart>();

        public List<FlatKommunikationsServiceUIDPart> getObjList() {
            return objList;
        }

        public void setObjList(final List<FlatKommunikationsServiceUIDPart> objList) {
            this.objList = objList;
        }
    }


}


