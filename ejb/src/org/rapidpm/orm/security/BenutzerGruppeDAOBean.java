package org.rapidpm.orm.security;

import org.apache.log4j.Logger;
import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.ejb3.interceptor.LoggingInterceptor;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.orm.DaoFactoryBean;
import org.rapidpm.orm.system.security.BenutzerGruppe;
import org.rapidpm.orm.system.security.BenutzerGruppeDAO;

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
 * Time: 16:54
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */
@Stateless(name = "BenutzerGruppeDAOEJB")
@WebService(name = "BenutzerGruppeDAOWS")
public class BenutzerGruppeDAOBean {
    public BenutzerGruppeDAOBean() {
    }

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;
    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private BenutzerGruppeDAO getBenutzerGruppeDAO() {
        return daoFactoryBean.getBenutzerGruppeDAO();
    }

    private CRUDExecuter<FlatBenutzerGruppe, BenutzerGruppe, BenutzerGruppeResult> crudExecuter = new CRUDExecuter<FlatBenutzerGruppe, BenutzerGruppe, BenutzerGruppeResult>(BenutzerGruppeResult.class) {

        @Override
        protected BenutzerGruppe flatType2Type(final FlatBenutzerGruppe flatTypeEntity) {
            final BenutzerGruppe type = new BenutzerGruppe();
            type.setId(flatTypeEntity.getId());
            type.setGruppenname(flatTypeEntity.getGruppenname());
            return type;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected BenutzerGruppe findByID(final Long oid) {
            return daoFactoryBean.getBenutzerGruppeDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };


    private BenutzerGruppeResult createResult(BenutzerGruppe... benutzerGruppen) {
        final BenutzerGruppeResult result = new BenutzerGruppeResult();
        for (final BenutzerGruppe b : benutzerGruppen) {
            result.getObjList().add(b);
        }
        return result;
    }

    private BenutzerGruppeResult createResult(final Collection<BenutzerGruppe> benuzterListe) {
        final BenutzerGruppeResult result = new BenutzerGruppeResult();
        result.getObjList().addAll(benuzterListe);
        return result;
    }

    @WebMethod(operationName = "loadRevisionFor")
    @Interceptors(LoggingInterceptor.class)
    public
    @WebResult(name = "BenutzerGruppeResult")
    BenutzerGruppeResult loadRevisionFor(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final List<BenutzerGruppe> list = getBenutzerGruppeDAO().loadAllRevisionsFor(oid);
        return createResult(list);
    }


    @WebMethod(operationName = "loadBenutzerGruppeByName")
    @Interceptors(LoggingInterceptor.class)
    public
    @WebResult(name = "BenutzerGruppeResult")
    BenutzerGruppeResult loadBenutzerGruppeByName(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                  @WebParam(name = "benutzerGruppenName", mode = WebParam.Mode.IN) final String benutzerGruppenName) {
        final BenutzerGruppe benutzerGruppe = getBenutzerGruppeDAO().loadBenutzerGruppeByName(benutzerGruppenName);
        return createResult(benutzerGruppe);
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "BenutzerGruppeResult")
    BenutzerGruppeResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                        @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatBenutzerGruppe entity) {
        return crudExecuter.saveOrUpdate(sessionid, uid, entity);

    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "BenutzerGruppeResult")
    BenutzerGruppeResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);

    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "BenutzerGruppeResult")
    BenutzerGruppeResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final BenutzerGruppe byID = getBenutzerGruppeDAO().findByID(oid);
        if (byID == null) {
            return new BenutzerGruppeResult();
        } else {
            return createResult(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "BenutzerGruppeResult")
    BenutzerGruppeResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                         @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getBenutzerGruppeDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "BenutzerGruppeResult")
    BenutzerGruppeResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getBenutzerGruppeDAO().loadAllEntities());
    }

    public static class FlatBenutzerGruppe extends BaseFlatEntity {
        private String gruppenname;

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof FlatBenutzerGruppe)) {
                return false;
            }

            final FlatBenutzerGruppe that = (FlatBenutzerGruppe) o;

            if (gruppenname != null ? !gruppenname.equals(that.gruppenname) : that.gruppenname != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return gruppenname != null ? gruppenname.hashCode() : 0;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatBenutzerGruppe");
            sb.append("{gruppenname='").append(gruppenname).append('\'');
            sb.append('}');
            return sb.toString();
        }

        public String getGruppenname() {
            return gruppenname;
        }

        public void setGruppenname(final String gruppenname) {
            this.gruppenname = gruppenname;
        }
    }

    public static class BenutzerGruppeResult extends BaseOrmResult<BenutzerGruppe> {
        //        private List<BenutzerGruppe> benutzerGruppenListe = new ArrayList<BenutzerGruppe>();
        //
        //        public List<BenutzerGruppe> getBenutzerGruppenListe(){
        //            return benutzerGruppenListe;
        //        }
        //
        //        public void setBenutzerGruppenListe(final List<BenutzerGruppe> benutzerGruppenListe){
        //            this.benutzerGruppenListe = benutzerGruppenListe;
        //        }

        public List<BenutzerGruppe> getObjList() {
            return objList;
        }

        public void setObjList(final List<BenutzerGruppe> objList) {
            this.objList = objList;
        }
    }

}
