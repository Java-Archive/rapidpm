/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.persistence.prj.stammdaten.organisationseinheit; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 24.03.11
 * Time: 08:42
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.ejb3.interceptor.LoggingInterceptor;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.stammdaten.person.Person;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Stateless(name = "PositionDAOEJB")
@WebService(name = "PositionDAOWS")
public class PositionDAOBean {
    //private static final Logger logger = Logger.getLogger(PositionDAOBean.class);

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private CRUDExecuter<FlatPosition, Position, PositionResult> crudExecuter = new CRUDExecuter<FlatPosition, Position, PositionResult>(PositionResult.class) {
        @Override
        protected Position flatType2Type(final FlatPosition flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final Position typeObj;
            if (id == null || id == -1 || id == 0) {
                typeObj = new Position();
            } else {
                typeObj = findByID(id);
            }
            typeObj.setBeschreibung(flatTypeEntity.getBeschreibung());
            //            typeObj.setCreated(flatTypeEntity.getCreated());
            //            typeObj.setModified(flatTypeEntity.getModified());
            typeObj.setName(flatTypeEntity.getName());
            typeObj.setOrganisationseinheit(daoFactoryBean.getOrganisationseinheitDAO().findByID(flatTypeEntity.getOrganisationseinheitOID()));
            typeObj.setSince(flatTypeEntity.getSince());
            typeObj.setUntil(flatTypeEntity.getUntil());
            final Person person = daoFactoryBean.getPersonDAO().findByID(flatTypeEntity.getPersonOID());
            typeObj.setPerson(person);
            return typeObj;
        }


        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected Position findByID(final Long oid) {
            return getEntityDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    private PositionDAO getEntityDAO() {
        return daoFactoryBean.getPositionDAO();
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadPositionen4Orgeinheit")
    @WebResult(name = "PositionResult")
    PositionResult loadPositionen4Orgeinheit(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                             @WebParam(name = "organisationseinheitOID", mode = WebParam.Mode.IN) final long orgeinheitOID) {

        return createResult(daoFactoryBean.getPositionDAO().loadPositionForOrgeinheit(orgeinheitOID));

    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "PositionResult")
    PositionResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                  @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatPosition entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "PositionResult")
    PositionResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "PositionResult")
    PositionResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final Position byID = getEntityDAO().findByID(oid);
        if (byID == null) {
            return new PositionResult();
        } else {
            return createResult(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "PositionResult")
    PositionResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                   @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "PositionResult")
    PositionResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    private PositionResult createResult(final Position... typeObj) {
        final PositionResult result = new PositionResult();
        for (final Position obj : typeObj) {
            result.getObjList().add(type2FlatType(obj));
        }
        return result;
    }

    private PositionResult createResult(final Collection<Position> typeObj) {
        final PositionResult result = new PositionResult();
        for (final Position obj : typeObj) {
            result.getObjList().add(type2FlatType(obj));
        }
        return result;
    }

    private FlatPosition type2FlatType(final Position position) {
        final FlatPosition flatPosition = new FlatPosition();
        flatPosition.setBeschreibung(position.getBeschreibung());
        //        flatPosition.setCreated(position.getCreated());
        flatPosition.setId(position.getId());
        //        flatPosition.setModified(position.getModified());
        flatPosition.setName(position.getName());
        flatPosition.setSince(position.getSince());
        flatPosition.setUntil(position.getUntil());

        final Person person = position.getPerson();
        flatPosition.setPersonOID(person.getId());

        flatPosition.setOrganisationseinheitOID(position.getOrganisationseinheit().getId());
        return flatPosition;
    }

    public static class FlatPosition extends BaseFlatEntity {

        private String name;
        private Date since;
        private Date until;
        private String beschreibung;
        private Long organisationseinheitOID;

        private Long personOID;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof FlatPosition)) return false;

            FlatPosition that = (FlatPosition) o;

            if (beschreibung != null ? !beschreibung.equals(that.beschreibung) : that.beschreibung != null)
                return false;
            if (!name.equals(that.name)) return false;
            if (!organisationseinheitOID.equals(that.organisationseinheitOID)) return false;
            if (personOID != null ? !personOID.equals(that.personOID) : that.personOID != null) return false;
            if (since != null ? !since.equals(that.since) : that.since != null) return false;
            if (until != null ? !until.equals(that.until) : that.until != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + (since != null ? since.hashCode() : 0);
            result = 31 * result + (until != null ? until.hashCode() : 0);
            result = 31 * result + (beschreibung != null ? beschreibung.hashCode() : 0);
            result = 31 * result + organisationseinheitOID.hashCode();
            result = 31 * result + (personOID != null ? personOID.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatPosition");
            sb.append("{beschreibung='").append(beschreibung).append('\'');
            sb.append(", name='").append(name).append('\'');
            //            sb.append(", created=").append(created);
            //            sb.append(", modified=").append(modified);
            sb.append(", since=").append(since);
            sb.append(", until=").append(until);
            sb.append(", organisationseinheitOID=").append(organisationseinheitOID);
            sb.append(", personOID=").append(personOID);
            sb.append('}');
            return sb.toString();
        }

        public String getBeschreibung() {
            return beschreibung;
        }

        public void setBeschreibung(final String beschreibung) {
            this.beschreibung = beschreibung;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public Long getOrganisationseinheitOID() {
            return organisationseinheitOID;
        }

        public void setOrganisationseinheitOID(final Long organisationseinheitOID) {
            this.organisationseinheitOID = organisationseinheitOID;
        }

        public Long getPersonOID() {
            return personOID;
        }

        public void setPersonOID(Long personOID) {
            this.personOID = personOID;
        }

        public Date getSince() {
            return since;
        }

        public void setSince(final Date since) {
            this.since = since;
        }

        public Date getUntil() {
            return until;
        }

        public void setUntil(final Date until) {
            this.until = until;
        }
    }

    public static class PositionResult extends BaseOrmResult<FlatPosition> {

        public List<FlatPosition> getObjList() {
            return objList;
        }

        public void setObjList(final List<FlatPosition> objList) {
            this.objList = objList;
        }
    }


}


