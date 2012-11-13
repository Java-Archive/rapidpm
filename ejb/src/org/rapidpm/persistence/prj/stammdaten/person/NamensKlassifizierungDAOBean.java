package org.rapidpm.persistence.prj.stammdaten.person; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 23.03.11
 * Time: 21:10
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.logging.LoggerQualifier;
import org.rapidpm.persistence.DaoFactoryBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.Collection;
import java.util.List;

@Stateless(name = "NamensKlassifizierungDAOEJB")
@WebService(name = "NamensKlassifizierungDAOWS")
public class NamensKlassifizierungDAOBean {
    //private static final Logger logger = Logger.getLogger(NamensKlassifizierungDAOBean.class);

    @Inject @LoggerQualifier
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private
    CRUDExecuter<FlatNamensKlassifizierung, NamensKlassifizierung, NamensKlassifizierungResult>
            crudExecuter =
            new CRUDExecuter<FlatNamensKlassifizierung, NamensKlassifizierung, NamensKlassifizierungResult>(NamensKlassifizierungResult.class) {
                @Override
                protected NamensKlassifizierung flatType2Type(final FlatNamensKlassifizierung flatTypeEntity) {
                    final Long id = flatTypeEntity.getId();
                    final NamensKlassifizierung typeObj;
                    if (id == null || id == -1 || id == 0) {
                        typeObj = new NamensKlassifizierung();
                    } else {
                        typeObj = findByID(id);
                    }
                    typeObj.setKlassifizierung(flatTypeEntity.getKlassifizierung());

                    return typeObj;
                }

                @Override
                protected DaoFactoryBean getDaoFactoryBean() {
                    return daoFactoryBean;
                }

                @Override
                protected NamensKlassifizierung findByID(final Long oid) {
                    return getEntityDAO().findByID(oid);
                }

                @Override
                protected LogEventEntryWriterBean getLogger() {
                    return logEventEntryWriterBean;
                }
            };

    private NamensKlassifizierungDAO getEntityDAO() {
        return daoFactoryBean.getNamensKlassifizierungDAO();
    }



    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "NamensKlassifizierungResult")
    NamensKlassifizierungResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                               @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatNamensKlassifizierung entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }


    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "NamensKlassifizierungResult")
    NamensKlassifizierungResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }


    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "NamensKlassifizierungResult")
    NamensKlassifizierungResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final NamensKlassifizierung byID = getEntityDAO().findByID(oid);
        if (byID == null) {
            return new NamensKlassifizierungResult();
        } else {
            return createResult(byID);
        }
    }


    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "NamensKlassifizierungResult")
    NamensKlassifizierungResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }


    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "NamensKlassifizierungResult")
    NamensKlassifizierungResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }


    public
    @WebMethod(operationName = "loadNamensKlassifizierungVorname")
    @WebResult(name = "NamensKlassifizierungResult")
    NamensKlassifizierungResult loadNamensKlassifizierungVorname(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadNamensKlassifizierungVorname());
    }


    public
    @WebMethod(operationName = "loadNamensKlassifizierungNachname")
    @WebResult(name = "NamensKlassifizierungResult")
    NamensKlassifizierungResult loadNamensKlassifizierungNachname(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadNamensKlassifizierungNachname());
    }


    public
    @WebMethod(operationName = "loadNamensKlassifizierungGeburtsname")
    @WebResult(name = "NamensKlassifizierungResult")
    NamensKlassifizierungResult loadNamensKlassifizierungGeburtsname(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadNamensKlassifizierungGeburtsname());
    }

    private NamensKlassifizierungResult createResult(final NamensKlassifizierung... typeObj) {
        final NamensKlassifizierungResult result = new NamensKlassifizierungResult();
        for (final NamensKlassifizierung obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }

    private NamensKlassifizierungResult createResult(final Collection<NamensKlassifizierung> typeObj) {
        final NamensKlassifizierungResult result = new NamensKlassifizierungResult();
        for (final NamensKlassifizierung obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }


    public static class FlatNamensKlassifizierung extends BaseFlatEntity {
        private String klassifizierung;

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof FlatNamensKlassifizierung)) {
                return false;
            }

            final FlatNamensKlassifizierung that = (FlatNamensKlassifizierung) o;

            if (klassifizierung != null ? !klassifizierung.equals(that.klassifizierung) : that.klassifizierung != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return klassifizierung != null ? klassifizierung.hashCode() : 0;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatNamensKlassifizierung");
            sb.append("{klassifizierung='").append(klassifizierung).append('\'');
            sb.append('}');
            return sb.toString();
        }

        public String getKlassifizierung() {
            return klassifizierung;
        }

        public void setKlassifizierung(final String klassifizierung) {
            this.klassifizierung = klassifizierung;
        }
    }

    public static class NamensKlassifizierungResult extends BaseOrmResult<NamensKlassifizierung> {
        //        private List<NamensKlassifizierung> objList = new ArrayList<NamensKlassifizierung>();
        //
        //        public List<NamensKlassifizierung> getObjList(){
        //            return objList;
        //        }
        //
        //        public void setObjList(final List<NamensKlassifizierung> objList){
        //            this.objList = objList;
        //        }

        public List<NamensKlassifizierung> getObjList() {
            return objList;
        }

        public void setObjList(final List<NamensKlassifizierung> objList) {
            this.objList = objList;
        }
    }


}


