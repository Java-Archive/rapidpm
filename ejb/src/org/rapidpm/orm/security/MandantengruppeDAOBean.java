package org.rapidpm.orm.security;

import org.apache.log4j.Logger;
import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.ejb3.interceptor.LoggingInterceptor;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.orm.DaoFactoryBean;
import org.rapidpm.orm.system.security.Mandantengruppe;
import org.rapidpm.orm.system.security.MandantengruppeDAO;

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

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 21.02.11
 * Time: 17:23
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */
@Stateless(name = "MandantengruppeDAOEJB")
@WebService(name = "MandantengruppeDAOWS")
public class MandantengruppeDAOBean {
    public MandantengruppeDAOBean() {
    }

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private CRUDExecuter<FlatMandantengruppe, Mandantengruppe, MandantengruppeResult> crudExecuter = new CRUDExecuter<FlatMandantengruppe, Mandantengruppe, MandantengruppeResult>(MandantengruppeResult.class) {

        @Override
        protected Mandantengruppe flatType2Type(final FlatMandantengruppe flatTypeEntity) {
            final Mandantengruppe type = new Mandantengruppe();
            type.setId(flatTypeEntity.getId());
            type.setMandantengruppe(flatTypeEntity.getMandantengruppe());
            return type;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected Mandantengruppe findByID(final Long oid) {
            return daoFactoryBean.getMandantengruppeDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    private MandantengruppeResult createResult(final Mandantengruppe mandantengruppe) {
        final MandantengruppeResult result = new MandantengruppeResult();
        if (mandantengruppe != null) {
            result.getObjList().add(mandantengruppe);
        } else {

        }
        return result;
    }

    private MandantengruppeResult createResult(final Collection<Mandantengruppe> mandantengruppeListe) {
        final MandantengruppeResult result = new MandantengruppeResult();
        result.getObjList().addAll(mandantengruppeListe);
        return result;
    }


    private MandantengruppeDAO getMandantengruppeDAO() {
        return daoFactoryBean.getMandantengruppeDAO();
    }

    @WebMethod(operationName = "loadRevisionFor")
    @Interceptors(LoggingInterceptor.class)
    public
    @WebResult(name = "MandantengruppeResult")
    MandantengruppeResult loadRevisionFor(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                          @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final List<Mandantengruppe> list = getMandantengruppeDAO().loadAllRevisionsFor(oid);
        return createResult(list);
    }


    @WebMethod(operationName = "loadMandantengruppe")
    @Interceptors(LoggingInterceptor.class)
    public
    @WebResult(name = "MandantengruppeResult")
    MandantengruppeResult loadMandantengruppe(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                              @WebParam(name = "mandantengruppenName", mode = WebParam.Mode.IN) final String mandantengruppenName) {
        final Mandantengruppe mandantengruppe = getMandantengruppeDAO().loadMandantengruppe(mandantengruppenName);
        return createResult(mandantengruppe);
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "MandantengruppeResult")
    MandantengruppeResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                         @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatMandantengruppe entity) {
        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "MandantengruppeResult")
    MandantengruppeResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {

        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "MandantengruppeResult")
    MandantengruppeResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final Mandantengruppe byID = getMandantengruppeDAO().findByID(oid);
        if (byID == null) {
            return new MandantengruppeResult();
        } else {
            return createResult(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "MandantengruppeResult")
    MandantengruppeResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                          @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getMandantengruppeDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "MandantengruppeResult")
    MandantengruppeResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getMandantengruppeDAO().loadAllEntities());
    }

    public static class FlatMandantengruppe extends BaseFlatEntity {
        private String mandantengruppe;

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof FlatMandantengruppe)) {
                return false;
            }

            final FlatMandantengruppe that = (FlatMandantengruppe) o;

            if (mandantengruppe != null ? !mandantengruppe.equals(that.mandantengruppe) : that.mandantengruppe != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return mandantengruppe != null ? mandantengruppe.hashCode() : 0;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatMandantengruppe");
            sb.append("{mandantengruppe='").append(mandantengruppe).append('\'');
            sb.append('}');
            return sb.toString();
        }

        public String getMandantengruppe() {
            return mandantengruppe;
        }

        public void setMandantengruppe(final String mandantengruppe) {
            this.mandantengruppe = mandantengruppe;
        }
    }

    public static class MandantengruppeResult extends BaseOrmResult<Mandantengruppe> {
        //        private List<Mandantengruppe> mandantengruppen = new ArrayList<Mandantengruppe>();
        //
        //
        //        public List<Mandantengruppe> getMandantengruppen(){
        //            return mandantengruppen;
        //        }
        //
        //        public void setMandantengruppen(List<Mandantengruppe> mandantengruppen){
        //            this.mandantengruppen = mandantengruppen;
        //        }

        public List<Mandantengruppe> getObjList() {
            return objList;
        }

        public void setObjList(final List<Mandantengruppe> objList) {
            this.objList = objList;
        }
    }


}
