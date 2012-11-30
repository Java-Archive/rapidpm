package org.rapidpm.persistence.prj.stammdaten.organisationseinheit; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 18.03.11
 * Time: 04:00
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.logging.LoggerQualifier;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.gesellschaftsformen.Gesellschaftsform;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.Collection;
import java.util.List;

@Stateless(name = "GesellschaftsformDAOEJB")
@WebService(name = "GesellschaftsformDAOWS")
public class GesellschaftsformDAOBean {
    //private static final Logger logger = Logger.getLogger(GesellschaftsformDAOBean.class);

    @Inject @LoggerQualifier
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private CRUDExecuter<FlatGesellschaftsform, Gesellschaftsform, GesellschaftsformResult> crudExecuter = new CRUDExecuter<FlatGesellschaftsform, Gesellschaftsform, GesellschaftsformResult>(GesellschaftsformResult.class) {
        @Override
        protected Gesellschaftsform flatType2Type(final FlatGesellschaftsform flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final Gesellschaftsform typeObj;
            if (id == null || id == -1 || id == 0) {
                typeObj = new Gesellschaftsform();
            } else {
                typeObj = findByID(id);
            }
            typeObj.setAbkuerzung(flatTypeEntity.getAbkuerzung());
            typeObj.setBezeichnung(flatTypeEntity.getBezeichnung());
            typeObj.setKapitalgesellschaft(flatTypeEntity.isKapitalgesellschaft());
            typeObj.setPersonengesellschaft(flatTypeEntity.isPersonengesellschaft());
            typeObj.setNichtkapitalistischeKoerperschaft(flatTypeEntity.isNichtkapitalistischeKoerperschaft());

            return typeObj;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected Gesellschaftsform findByID(final Long oid) {
            return getEntityDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    private GesellschaftsformDAO getEntityDAO() {
        return daoFactoryBean.getGesellschaftsformDAO();
    }



    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "GesellschaftsformResult")
    GesellschaftsformResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                           @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatGesellschaftsform entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }


    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "GesellschaftsformResult")
    GesellschaftsformResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }


    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "GesellschaftsformResult")
    GesellschaftsformResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final Gesellschaftsform byID = getEntityDAO().findByID(oid);
        if (byID == null) {
            return new GesellschaftsformResult();
        } else {
            return createResult(byID);
        }
    }


    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "GesellschaftsformResult")
    GesellschaftsformResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                            @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }


    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "GesellschaftsformResult")
    GesellschaftsformResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    private GesellschaftsformResult createResult(final Gesellschaftsform... typeObj) {
        final GesellschaftsformResult result = new GesellschaftsformResult();
        for (final Gesellschaftsform obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }

    private GesellschaftsformResult createResult(final Collection<Gesellschaftsform> typeObj) {
        final GesellschaftsformResult result = new GesellschaftsformResult();
        for (final Gesellschaftsform obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }


    public static class FlatGesellschaftsform extends BaseFlatEntity {
        private boolean kapitalgesellschaft;
        private boolean personengesellschaft;
        private boolean nichtkapitalistischeKoerperschaft;
        private String bezeichnung;
        private String abkuerzung;

        public boolean isKapitalgesellschaft() {
            return kapitalgesellschaft;
        }

        public void setKapitalgesellschaft(final boolean kapitalgesellschaft) {
            this.kapitalgesellschaft = kapitalgesellschaft;
        }

        public boolean isPersonengesellschaft() {
            return personengesellschaft;
        }

        public void setPersonengesellschaft(final boolean personengesellschaft) {
            this.personengesellschaft = personengesellschaft;
        }

        public boolean isNichtkapitalistischeKoerperschaft() {
            return nichtkapitalistischeKoerperschaft;
        }

        public void setNichtkapitalistischeKoerperschaft(final boolean nichtkapitalistischeKoerperschaft) {
            this.nichtkapitalistischeKoerperschaft = nichtkapitalistischeKoerperschaft;
        }

        public String getBezeichnung() {
            return bezeichnung;
        }

        public void setBezeichnung(final String bezeichnung) {
            this.bezeichnung = bezeichnung;
        }

        public String getAbkuerzung() {
            return abkuerzung;
        }

        public void setAbkuerzung(final String abkuerzung) {
            this.abkuerzung = abkuerzung;
        }
    }

    public static class GesellschaftsformResult extends BaseOrmResult<Gesellschaftsform> {
        //        private List<Gesellschaftsform> objList = new ArrayList<Gesellschaftsform>();
        //
        //        public List<Gesellschaftsform> getObjList(){
        //            return objList;
        //        }
        //
        //        public void setObjList(final List<Gesellschaftsform> objList){
        //            this.objList = objList;
        //        }

        public List<Gesellschaftsform> getObjList() {
            return objList;
        }

        public void setObjList(final List<Gesellschaftsform> objList) {
            this.objList = objList;
        }
    }


}


