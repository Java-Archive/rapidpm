/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.persistence.prj.stammdaten.organisationseinheit; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 18.03.11
 * Time: 19:35
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.ejb3.interceptor.LoggingInterceptor;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.logging.LoggerQualifier;
import org.rapidpm.persistence.*;
import org.rapidpm.persistence.prj.stammdaten.address.Adresse;
import org.rapidpm.persistence.prj.stammdaten.kommunikation.KommunikationsServiceUID;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.gesellschaftsformen.Gesellschaftsform;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.metadata.Ausbildungseinheit;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.metadata.Verwaltungseinheit;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.metadata.Wirtschaftseinheit;
import org.rapidpm.persistence.prj.stammdaten.web.WebDomain;
import org.rapidpm.persistence.system.logging.LogLevelEnum;
import org.rapidpm.persistence.system.security.Mandantengruppe;
import org.rapidpm.persistence.system.security.MandantengruppeDAO;

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
import java.util.List;

@Stateless(name = "OrganisationseinheitDAOEJB")
@WebService(name = "OrganisationseinheitDAOWS")
public class OrganisationseinheitDAOBean {
    //private static final Logger logger = Logger.getLogger(OrganisationseinheitDAOBean.class);

    @Inject @LoggerQualifier
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private CRUDExecuter<FlatOrganisationseinheit, Organisationseinheit, OrganisationseinheitResult> crudExecuter = new CRUDExecuter<FlatOrganisationseinheit, Organisationseinheit, OrganisationseinheitResult>(OrganisationseinheitResult.class) {
        @Override
        protected Organisationseinheit flatType2Type(final FlatOrganisationseinheit flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final Organisationseinheit typeObj;
            if (id == null || id == -1 || id == 0) {
                typeObj = new Organisationseinheit();
            } else {
                typeObj = findByID(id);
            }
            typeObj.setActive(flatTypeEntity.isActive());
            typeObj.setAdressen(daoFactoryBean.getAdresseDAO().loadWithOIDList(flatTypeEntity.getAdressenOIDs()));

            typeObj.setBrancheAssocs(daoFactoryBean.getBranchenAssocDAO().loadWithOIDList(flatTypeEntity.getBrancheAssocOIDs()));
            typeObj.setBvdepId(flatTypeEntity.getBvdepId());
            //            typeObj.setCreated(flatTypeEntity.getCreated());
            typeObj.setGesellschaftsform(daoFactoryBean.getGesellschaftsformDAO().findByID(flatTypeEntity.getGesellschaftsformOID()));
            typeObj.setGruendungsdatum(flatTypeEntity.getGruendungsdatum());
            typeObj.setKommunikationsServiceUIDs(daoFactoryBean.getKommunikationServiceUIDDAO().loadWithOIDList(flatTypeEntity.getKommunikationsServiceUIDOIDs()));

            //            typeObj.setMandantengruppen(new ArrayList<Mandantengruppe>());
            //            final List<String> mandantengruppen = flatTypeEntity.getMandantengruppen();
            //            for(final String mandantengruppe : mandantengruppen){
            //                final Mandantengruppe m = daoFactoryBean.getMandantengruppeDAO().loadMandantengruppe(mandantengruppe);
            //                typeObj.getMandantengruppen().add(m);
            //            }


            final MandantengruppeDAO mandantengruppeDAO = daoFactoryBean.getMandantengruppeDAO();
            final Mandantengruppe m = mandantengruppeDAO.findByID(flatTypeEntity.getMandantengruppeOID());
            typeObj.setMandantengruppe(m);

            final List<Long> metadataOIDs = flatTypeEntity.getMetadataOIDs();
            typeObj.setMetadata(daoFactoryBean.getOrganisationseinheitMetaDataDAO().loadWithOIDList(metadataOIDs));
            //            typeObj.setModified(flatTypeEntity.getModified());
            typeObj.setOrganisationsName(flatTypeEntity.getOrganisationsName());
            //typeObj.setPositionen(daoFactoryBean.getPositionDAO().loadWithOIDList(flatTypeEntity.getPositionOIDs()));

            //            final TaetigkeitsfeldDAO taetigkeitsfeldDAO = daoFactoryBean.getTaetigkeitsfeldDAO();
            final TaetigkeitsfeldAssocDAO taetigkeitsfeldAssocDAO = daoFactoryBean.getTaetigkeitsfeldAssocDAO();
            //            typeObj.setTaetigkeitsfelder(taetigkeitsfeldDAO.loadWithOIDList(flatTypeEntity.getTaetigkeitsfeldOIDs()));
            typeObj.setTaetigkeitsfeldAssocs(taetigkeitsfeldAssocDAO.loadWithOIDList(flatTypeEntity.getTaetigkeitsfelAssocOIDs()));

            typeObj.setAusbildungseinheit(daoFactoryBean.getAusbildungseinheitDAO().findByID(flatTypeEntity.getAusbildungseinheitOID()));
            typeObj.setVerwaltungseinheit(daoFactoryBean.getVerwaltungseinheitDAO().findByID(flatTypeEntity.getVerwaltungseinheitOID()));
            typeObj.setWirtschaftseinheit(daoFactoryBean.getWirtschaftseinheitDAO().findByID(flatTypeEntity.getWirtschaftseinheitOID()));

            typeObj.setWebdomains(typeObj.getWebdomains());
            return typeObj;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected Organisationseinheit findByID(final Long oid) {
            return getEntityDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    public OrganisationseinheitDAOBean() {
    }

    private OrganisationseinheitDAO getEntityDAO() {
        return daoFactoryBean.getOrganisationseinheitDAO();
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "OrganisationseinheitResult")
    OrganisationseinheitResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                              @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatOrganisationseinheit entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "OrganisationseinheitResult")
    OrganisationseinheitResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "OrganisationseinheitResult")
    OrganisationseinheitResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final Organisationseinheit byID = getEntityDAO().findByID(oid, true);
        if (byID == null) {
            return new OrganisationseinheitResult();
        } else {
            return createResult(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "OrganisationseinheitResult")
    OrganisationseinheitResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                               @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids, true));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "OrganisationseinheitResult")
    OrganisationseinheitResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities(true));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadOrganisationseinheitenForMandantengruppe")
    @WebResult(name = "OrganisationseinheitResult")
    OrganisationseinheitResult loadOrganisationseinheitenForMandantengruppe(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                                            @WebParam(name = "mandantengruppeOID", mode = WebParam.Mode.IN) final Long oid) {
        return createResult(getEntityDAO().loadOrganisationseinheitenForMandantengruppe(oid, true));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "connectAdresse")
    @WebResult(name = "OrganisationseinheitResult")
    OrganisationseinheitResult connectAdresse(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                              @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                              @WebParam(name = "organisationseinheitOID", mode = WebParam.Mode.IN) final Long organisationseinheitOID,
                                              @WebParam(name = "adresseOID", mode = WebParam.Mode.IN) final Long adresseOID) {
        return new AbstractOneToManyConnectExecutor<DaoFactory, Adresse, Organisationseinheit, OrganisationseinheitResult>(
                daoFactoryBean, daoFactoryBean.getAdresseDAO(), getEntityDAO()) {

            @Override
            public List<Adresse> getCollection(final Organisationseinheit organisationseinheit) {
                return organisationseinheit.getAdressen();
            }

            @Override
            public OrganisationseinheitResult getResult(final Organisationseinheit organisationseinheit) {
                return organisationseinheit != null ? createResult(organisationseinheit) : createResult();
            }
        }.execute("connectAdresse", logEventEntryWriterBean, sessionid, adresseOID, organisationseinheitOID);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "connectWebDomain")
    @WebResult(name = "OrganisationseinheitResult")
    OrganisationseinheitResult connectWebDomain(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                @WebParam(name = "organisationseinheitOID", mode = WebParam.Mode.IN) final Long organisationseinheitOID,
                                                @WebParam(name = "webDomainOID", mode = WebParam.Mode.IN) final Long webDomainOID) {
        return new AbstractOneToManyConnectExecutor<DaoFactory, WebDomain, Organisationseinheit, OrganisationseinheitResult>(daoFactoryBean, daoFactoryBean.getWebDomainDAO(), getEntityDAO()) {

            @Override
            public List<WebDomain> getCollection(final Organisationseinheit organisationseinheit) {
                return organisationseinheit.getWebdomains();
            }

            @Override
            public OrganisationseinheitResult getResult(final Organisationseinheit organisationseinheit) {
                return organisationseinheit != null ? createResult(organisationseinheit) : createResult();
            }
        }.execute("connectWebDomain", logEventEntryWriterBean, sessionid, webDomainOID, organisationseinheitOID);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "connectKommunikationsServiceUID")
    @WebResult(name = "OrganisationseinheitResult")
    OrganisationseinheitResult connectKommunikationsServiceUID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                               @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                               @WebParam(name = "organisationseinheitOID", mode = WebParam.Mode.IN) final Long organisationseinheitOID,
                                                               @WebParam(name = "kommunikationsServiceUIDOID", mode = WebParam.Mode.IN) final Long kommunikationsServiceUIDOID) {
        return new AbstractOneToManyConnectExecutor<DaoFactory, KommunikationsServiceUID, Organisationseinheit, OrganisationseinheitResult>(
                daoFactoryBean, daoFactoryBean.getKommunikationServiceUIDDAO(), getEntityDAO()) {

            @Override
            public List<KommunikationsServiceUID> getCollection(final Organisationseinheit organisationseinheit) {
                return organisationseinheit.getKommunikationsServiceUIDs();
            }

            @Override
            public OrganisationseinheitResult getResult(final Organisationseinheit organisationseinheit) {
                return organisationseinheit != null ? createResult(organisationseinheit) : createResult();
            }
        }.execute("connectKommunikationsServiceUID", logEventEntryWriterBean, sessionid, kommunikationsServiceUIDOID, organisationseinheitOID);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "connectGesellschaftsform")
    @WebResult(name = "OrganisationseinheitResult")
    OrganisationseinheitResult connectGesellschaftsform(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                        @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                        @WebParam(name = "organisationseinheitOID", mode = WebParam.Mode.IN) final Long organisationseinheitOID,
                                                        @WebParam(name = "gesellschaftsformOID", mode = WebParam.Mode.IN) final Long gesellschaftsformOID) {
        return new AbstractOneToOneConnectExecutor<DaoFactory, Gesellschaftsform, Organisationseinheit, OrganisationseinheitResult>(
                daoFactoryBean, daoFactoryBean.getGesellschaftsformDAO(), getEntityDAO()) {

            @Override
            public void connect(final Organisationseinheit organisationseinheit, final Gesellschaftsform gesellschaftsform) {
                organisationseinheit.setGesellschaftsform(gesellschaftsform);
            }

            @Override
            public OrganisationseinheitResult getResult(final Organisationseinheit organisationseinheit) {
                return organisationseinheit != null ? createResult(organisationseinheit) : createResult();
            }
        }.execute("connectGesellschaftsform", logEventEntryWriterBean, sessionid, gesellschaftsformOID, organisationseinheitOID);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "connectBrancheAssoc")
    @WebResult(name = "OrganisationseinheitResult")
    OrganisationseinheitResult connectBrancheAssoc(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                   @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                   @WebParam(name = "organisationseinheitOID", mode = WebParam.Mode.IN) final Long organisationseinheitOID,
                                                   @WebParam(name = "brancheAssocOID", mode = WebParam.Mode.IN) final Long brancheAssocOID) {
        return new AbstractOneToManyConnectExecutor<DaoFactory, BrancheAssoc, Organisationseinheit, OrganisationseinheitResult>(
                daoFactoryBean, daoFactoryBean.getBranchenAssocDAO(), getEntityDAO()) {

            @Override
            public List<BrancheAssoc> getCollection(final Organisationseinheit organisationseinheit) {
                return organisationseinheit.getBrancheAssocs();
            }

            @Override
            public OrganisationseinheitResult getResult(final Organisationseinheit organisationseinheit) {
                return organisationseinheit != null ? createResult(organisationseinheit) : createResult();
            }
        }.execute("connectBrancheAssoc", logEventEntryWriterBean, sessionid, brancheAssocOID, organisationseinheitOID);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "connectAusbildungseinheit")
    @WebResult(name = "OrganisationseinheitResult")
    OrganisationseinheitResult connectAusbildungseinheit(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                         @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                         @WebParam(name = "organisationseinheitOID", mode = WebParam.Mode.IN) final Long organisationseinheitOID,
                                                         @WebParam(name = "ausbildungseinheitOID", mode = WebParam.Mode.IN) final Long ausbildungseinheitOID) {
        return new AbstractOneToOneConnectExecutor<DaoFactory, Ausbildungseinheit, Organisationseinheit, OrganisationseinheitResult>(
                daoFactoryBean, daoFactoryBean.getAusbildungseinheitDAO(), getEntityDAO()) {

            @Override
            public void connect(final Organisationseinheit organisationseinheit, final Ausbildungseinheit ausbildungseinheit) {
                organisationseinheit.setAusbildungseinheit(ausbildungseinheit);
            }

            @Override
            public OrganisationseinheitResult getResult(final Organisationseinheit organisationseinheit) {
                return organisationseinheit != null ? createResult(organisationseinheit) : createResult();
            }
        }.execute("connectAusbildungseinheit", logEventEntryWriterBean, sessionid, ausbildungseinheitOID, organisationseinheitOID);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "connectTaetigkeitsfeldAssoc")
    @WebResult(name = "OrganisationseinheitResult")
    OrganisationseinheitResult connectTaetigkeitsfeldAssoc(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                           @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                           @WebParam(name = "organisationseinheitOID", mode = WebParam.Mode.IN) final Long organisationseinheitOID,
                                                           @WebParam(name = "taetigkeitsfeldAssocOID", mode = WebParam.Mode.IN) final Long taetigkeitsfeldAssocOID) {
        return new AbstractOneToManyConnectExecutor<DaoFactory, TaetigkeitsfeldAssoc, Organisationseinheit, OrganisationseinheitResult>(
                daoFactoryBean, daoFactoryBean.getTaetigkeitsfeldAssocDAO(), getEntityDAO()) {

            @Override
            public List<TaetigkeitsfeldAssoc> getCollection(final Organisationseinheit organisationseinheit) {
                return organisationseinheit.getTaetigkeitsfeldAssocs();
            }

            @Override
            public OrganisationseinheitResult getResult(final Organisationseinheit organisationseinheit) {
                return organisationseinheit != null ? createResult(organisationseinheit) : createResult();
            }
        }.execute("connectTaetigkeitsfeldAssoc", logEventEntryWriterBean, sessionid, taetigkeitsfeldAssocOID, organisationseinheitOID);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "connectVerwaltungseinheit")
    @WebResult(name = "OrganisationseinheitResult")
    OrganisationseinheitResult connectVerwaltungseinheit(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                         @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                         @WebParam(name = "organisationseinheitOID", mode = WebParam.Mode.IN) final Long organisationseinheitOID,
                                                         @WebParam(name = "verwaltungseinheitOID", mode = WebParam.Mode.IN) final Long verwaltungseinheitOID) {
        return new AbstractOneToOneConnectExecutor<DaoFactory, Verwaltungseinheit, Organisationseinheit, OrganisationseinheitResult>(
                daoFactoryBean, daoFactoryBean.getVerwaltungseinheitDAO(), getEntityDAO()) {

            @Override
            public void connect(final Organisationseinheit organisationseinheit, final Verwaltungseinheit verwaltungseinheit) {
                organisationseinheit.setVerwaltungseinheit(verwaltungseinheit);
            }

            @Override
            public OrganisationseinheitResult getResult(final Organisationseinheit organisationseinheit) {
                return organisationseinheit != null ? createResult(organisationseinheit) : createResult();
            }
        }.execute("connectVerwaltungseinheit", logEventEntryWriterBean, sessionid, verwaltungseinheitOID, organisationseinheitOID);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "connectPerson")
    @WebResult(name = "OrganisationseinheitResult")
    OrganisationseinheitResult connectPerson(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                             @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                             @WebParam(name = "organisationseinheitOID", mode = WebParam.Mode.IN) final Long organisationseinheitOID,
                                             @WebParam(name = "personOID", mode = WebParam.Mode.IN) final Long personOID,
                                             @WebParam(name = "positionOID", mode = WebParam.Mode.IN) final Long positionOID) {
        final Organisationseinheit organisationseinheit = getEntityDAO().findByID(organisationseinheitOID);
        if (organisationseinheit != null) {
            throw new RuntimeException("not yet implemented.. AbstractOneToOneConnectExecutor<BaseDAO,Person, Organisationseinheit,OrganisationseinheitResult>");
//            return new AbstractOneToOneConnectExecutor<BaseDAO,Person, Organisationseinheit,OrganisationseinheitResult>(
//                    daoFactoryBean, daoFactoryBean.getPersonDAO(), getEntityDAO()) {
//
//                @Override
//                public void connect(Organisationseinheit entity, Person connectEntity) {
//                    entity.set
//                }
//
//                @Override
//                public OrganisationseinheitResult getResult(Organisationseinheit entity) {
//                    return null;  //To change body of implemented methods use File | Settings | File Templates.
//                }
//            }.execute("connectPerson", logEventEntryWriterBean, sessionid, personOID, positionOID);
//            return new AbstractOneToManyConnectExecutor<BaseDAO,Person, Organisationseinheit,OrganisationseinheitResult>(
//                    daoFactoryBean.getPersonDAO(), daoFactoryBean.getPositionDAO()) {
//
//                @Override
//                public List<Person> getCollection(final Position position) {
//                    position.setOrganisationseinheit(organisationseinheit); // REFAC
//                    return position.getPersonen();
//                }
//
//                @Override
//                public OrganisationseinheitResult getResult(final Position position) {
//                    return createResult(organisationseinheit);
//                }
//            }.execute("connectPerson", logEventEntryWriterBean, sessionid, personOID, positionOID);
        } else {
            final OrganisationseinheitResult result = createResult();
            result.setValid(false);
            result.getLoggingEventEntries().add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "connectPerson",
                    sessionid, "Es existiert keine Organisationseinheit mit der OID " + organisationseinheitOID));
            return result;
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "connectWirtschaftseinheit")
    @WebResult(name = "OrganisationseinheitResult")
    OrganisationseinheitResult connectWirtschaftseinheit(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                         @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                         @WebParam(name = "organisationseinheitOID", mode = WebParam.Mode.IN) final Long organisationseinheitOID,
                                                         @WebParam(name = "wirtschaftseinheitOID", mode = WebParam.Mode.IN) final Long wirtschaftseinheitOID) {
        return new AbstractOneToOneConnectExecutor<DaoFactory, Wirtschaftseinheit, Organisationseinheit, OrganisationseinheitResult>(
                daoFactoryBean, daoFactoryBean.getWirtschaftseinheitDAO(), getEntityDAO()) {

            @Override
            public void connect(final Organisationseinheit organisationseinheit, final Wirtschaftseinheit wirtschaftseinheit) {
                organisationseinheit.setWirtschaftseinheit(wirtschaftseinheit);
            }

            @Override
            public OrganisationseinheitResult getResult(final Organisationseinheit organisationseinheit) {
                return organisationseinheit != null ? createResult(organisationseinheit) : createResult();
            }
        }.execute("connectWirtschaftseinheit", logEventEntryWriterBean, sessionid, wirtschaftseinheitOID, organisationseinheitOID);
    }


    private OrganisationseinheitResult createResult(final Organisationseinheit... typeObj) {
        final OrganisationseinheitResult result = new OrganisationseinheitResult();
        for (final Organisationseinheit obj : typeObj) {
            result.getObjList().add(type2FlatType(obj));
        }
        return result;
    }

    private OrganisationseinheitResult createResult(final Collection<Organisationseinheit> typeObj) {
        final OrganisationseinheitResult result = new OrganisationseinheitResult();
        for (final Organisationseinheit obj : typeObj) {
            result.getObjList().add(type2FlatType(obj));
        }
        return result;
    }

    private FlatOrganisationseinheit type2FlatType(final Organisationseinheit o) {
        final FlatOrganisationseinheit ft = new FlatOrganisationseinheit();
        ft.setActive(o.isActive());

        final BaseDAO.EntityUtils entityUtils = daoFactoryBean.getEntityUtils();
        ft.setAdressenOIDs(entityUtils.getOIDs(o.getAdressen()));

        final Ausbildungseinheit ausbildungseinheit = o.getAusbildungseinheit();
        if (ausbildungseinheit == null) {

        } else {
            ft.setAusbildungseinheitOID(ausbildungseinheit.getId());
        }
        ft.setBrancheAssocOIDs(entityUtils.getOIDs(o.getBrancheAssocs()));

        ft.setBvdepId(o.getBvdepId());
        final Gesellschaftsform gesellschaftsform = o.getGesellschaftsform();
        if (gesellschaftsform == null) {

        } else {
            ft.setGesellschaftsformOID(gesellschaftsform.getId());
        }
        ft.setGruendungsdatum(o.getGruendungsdatum());
        ft.setKommunikationsServiceUIDOIDs(entityUtils.getOIDs(o.getKommunikationsServiceUIDs()));

        final Mandantengruppe mandantengruppe = o.getMandantengruppe();
        if (mandantengruppe == null) {
        } else {
            ft.setMandantengruppeOID(mandantengruppe.getId());
        }
        final List<OrganisationseinheitMetaData> metadata = o.getMetadata();
        if (metadata != null) {
            for (final OrganisationseinheitMetaData organisationseinheitMetaData : metadata) {
                ft.getMetadataOIDs().add(organisationseinheitMetaData.getId());

            }

        } else {
        }
        ft.setOrganisationsName(o.getOrganisationsName());
        //        ft.setPositionOIDs(DAO.getOIDs(o.get));

        ft.setTaetigkeitsfelAssocOIDs(entityUtils.getOIDs(o.getTaetigkeitsfeldAssocs()));

        final Verwaltungseinheit verwaltungseinheit = o.getVerwaltungseinheit();
        if (verwaltungseinheit == null) {
        } else {
            ft.setVerwaltungseinheitOID(verwaltungseinheit.getId());
        }

        final List<WebDomain> webdomains = o.getWebdomains();
        if (webdomains != null) {
            for (final WebDomain webdomain : webdomains) {

                ft.getWebDomainOIDs().add(webdomain.getId());
            }
        } else {
        }

        final Wirtschaftseinheit wirtschaftseinheit = o.getWirtschaftseinheit();
        if (wirtschaftseinheit == null) {
        } else {
            ft.setWirtschaftseinheitOID(wirtschaftseinheit.getId());
        }
        ft.setId(o.getId());

        return ft;
    }


    public static class FlatOrganisationseinheit extends BaseFlatEntity {
        private String organisationsName;
        private String gruendungsdatum;
        private String bvdepId;
        private boolean active;
        private List<Long> taetigkeitsfelAssocOIDs = new ArrayList<>();
        private List<Long> positionOIDs = new ArrayList<>();
        private Long mandantengruppeOID;
        private List<Long> brancheAssocOIDs = new ArrayList<>();
        private List<Long> kommunikationsServiceUIDOIDs = new ArrayList<>();
        private List<Long> metadataOIDs = new ArrayList<>();
        private Long gesellschaftsformOID;
        private List<Long> adressenOIDs = new ArrayList<>();
        private List<Long> webDomainOIDs = new ArrayList<>();
        private Long ausbildungseinheitOID;
        private Long verwaltungseinheitOID;
        private Long wirtschaftseinheitOID;

        public FlatOrganisationseinheit() {
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(final boolean active) {
            this.active = active;
        }

        public List<Long> getAdressenOIDs() {
            return adressenOIDs;
        }

        public void setAdressenOIDs(final List<Long> adressenOIDs) {
            this.adressenOIDs = adressenOIDs;
        }

        public Long getAusbildungseinheitOID() {
            return ausbildungseinheitOID;
        }

        public void setAusbildungseinheitOID(final Long ausbildungseinheitOID) {
            this.ausbildungseinheitOID = ausbildungseinheitOID;
        }

        public List<Long> getBrancheAssocOIDs() {
            return brancheAssocOIDs;
        }

        public void setBrancheAssocOIDs(final List<Long> brancheAssocOIDs) {
            this.brancheAssocOIDs = brancheAssocOIDs;
        }

        public String getBvdepId() {
            return bvdepId;
        }

        public void setBvdepId(final String bvdepId) {
            this.bvdepId = bvdepId;
        }

        //        public Date getCreated(){
        //            return created;
        //        }
        //
        //        public void setCreated(final Date created){
        //            this.created = created;
        //        }

        public Long getGesellschaftsformOID() {
            return gesellschaftsformOID;
        }

        public void setGesellschaftsformOID(final Long gesellschaftsformOID) {
            this.gesellschaftsformOID = gesellschaftsformOID;
        }

        public String getGruendungsdatum() {
            return gruendungsdatum;
        }

        public void setGruendungsdatum(final String gruendungsdatum) {
            this.gruendungsdatum = gruendungsdatum;
        }

        public List<Long> getKommunikationsServiceUIDOIDs() {
            return kommunikationsServiceUIDOIDs;
        }

        public void setKommunikationsServiceUIDOIDs(final List<Long> kommunikationsServiceUIDOIDs) {
            this.kommunikationsServiceUIDOIDs = kommunikationsServiceUIDOIDs;
        }

        //        public List<String> getMandantengruppen(){
        //            return mandantengruppen;
        //        }
        //
        //        public void setMandantengruppen(final List<String> mandantengruppen){
        //            this.mandantengruppen = mandantengruppen;
        //        }


        public Long getMandantengruppeOID() {
            return mandantengruppeOID;
        }

        public void setMandantengruppeOID(final Long mandantengruppeOID) {
            this.mandantengruppeOID = mandantengruppeOID;
        }

        public List<Long> getMetadataOIDs() {
            return metadataOIDs;
        }

        public void setMetadataOIDs(final List<Long> metadataOIDs) {
            this.metadataOIDs = metadataOIDs;
        }

        //        public Date getModified(){
        //            return modified;
        //        }
        //
        //        public void setModified(final Date modified){
        //            this.modified = modified;
        //        }

        public String getOrganisationsName() {
            return organisationsName;
        }

        public void setOrganisationsName(final String organisationsName) {
            this.organisationsName = organisationsName;
        }

        public List<Long> getPositionOIDs() {
            return positionOIDs;
        }

        public void setPositionOIDs(final List<Long> positionOIDs) {
            this.positionOIDs = positionOIDs;
        }

        public List<Long> getTaetigkeitsfelAssocOIDs() {
            return taetigkeitsfelAssocOIDs;
        }

        public void setTaetigkeitsfelAssocOIDs(final List<Long> taetigkeitsfelAssocOIDs) {
            this.taetigkeitsfelAssocOIDs = taetigkeitsfelAssocOIDs;
        }

        public Long getVerwaltungseinheitOID() {
            return verwaltungseinheitOID;
        }

        public void setVerwaltungseinheitOID(final Long verwaltungseinheitOID) {
            this.verwaltungseinheitOID = verwaltungseinheitOID;
        }

        public List<Long> getWebDomainOIDs() {
            return webDomainOIDs;
        }

        public void setWebDomainOIDs(final List<Long> webDomainOIDs) {
            this.webDomainOIDs = webDomainOIDs;
        }

        public Long getWirtschaftseinheitOID() {
            return wirtschaftseinheitOID;
        }

        public void setWirtschaftseinheitOID(final Long wirtschaftseinheitOID) {
            this.wirtschaftseinheitOID = wirtschaftseinheitOID;
        }
    }

    public static class OrganisationseinheitResult extends BaseOrmResult<FlatOrganisationseinheit> {

        public OrganisationseinheitResult() {
        }

        public List<FlatOrganisationseinheit> getObjList() {
            return objList;
        }

        public void setObjList(final List<FlatOrganisationseinheit> objList) {
            this.objList = objList;
        }
    }


}



