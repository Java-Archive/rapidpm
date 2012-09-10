package org.rapidpm.persistence.prj.stammdaten.address;

import org.apache.log4j.Logger;
import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.ejb3.interceptor.LoggingInterceptor;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.persistence.DaoFactoryBean;

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
 * User: sven.ruppert
 * Date: 03.03.11
 * Time: 21:18
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */
@Stateless(name = "AdresseDAOEJB")
@WebService(name = "AdresseDAOWS")
public class AdresseDAOBean {
    @Inject
    private transient Logger logger;

    public AdresseDAOBean() {
    }

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;


    private AdresseDAO getAdresseDAO() {
        return daoFactoryBean.getAdresseDAO();
    }

    private CRUDExecuter<FlatAdresse, Adresse, AdresseResult> crudExecuter = new CRUDExecuter<FlatAdresse, Adresse, AdresseResult>(AdresseResult.class) {

        @Override
        protected Adresse flatType2Type(final FlatAdresse flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final Adresse byID;
            if (id == null || id == -1 || id == 0) {
                byID = new Adresse();
            } else {
                byID = findByID(id);
            }

            byID.setOrtsname(flatTypeEntity.getOrtsname());
            byID.setPlz(flatTypeEntity.getPlz());
            byID.setStrasse(flatTypeEntity.getStrasse());
            byID.setHausnummer(flatTypeEntity.getHausnummer());
            byID.setKlassifizierung(daoFactoryBean.getAddressKlassifizierungDAO().findByID(flatTypeEntity.getAdressKlassifizierungOID()));
            byID.setNotiz(flatTypeEntity.getNotiz());
            byID.setGrosskundenPLZ(flatTypeEntity.isGrosskundenplz());
            byID.setState(daoFactoryBean.getStateDAO().findByID(flatTypeEntity.getStateOID()));
            return byID;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected Adresse findByID(final Long oid) {
            return daoFactoryBean.getAdresseDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "AdresseResult")
    AdresseResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                 @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatAdresse entity) {
        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "AdresseResult")
    AdresseResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "AdresseResult")
    AdresseResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final Adresse byID = getAdresseDAO().findByID(oid);
        if (byID == null) {
            return new AdresseResult();
        } else {
            return create(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "AdresseResult")
    AdresseResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                  @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return create(getAdresseDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "AdresseResult")
    AdresseResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return create(getAdresseDAO().loadAllEntities());
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "getAdressenForPerson")
    @WebResult(name = "AdresseResult")
    AdresseResult getAdressenForPerson(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                       @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                       @WebParam(name = "personOID", mode = WebParam.Mode.IN) final Long personOID) {
        return create(getAdresseDAO().getAdressenForPerson(personOID));
    }


    private AdresseResult create(final Adresse... adressen) {
        final AdresseResult result = new AdresseResult();
        for (Adresse adresse : adressen) {
            result.getObjList().add(type2FlatType(adresse));
        }
        return result;
    }

    private AdresseResult create(final Collection<Adresse> adressen) {
        final AdresseResult result = new AdresseResult();
        for (Adresse adresse : adressen) {
            result.getObjList().add(type2FlatType(adresse));
        }
        return result;
    }

    private FlatAdresse type2FlatType(final Adresse a) {
        final FlatAdresse ft = new FlatAdresse();
        ft.setGrosskundenplz(a.getGrosskundenPLZ());
        ft.setHausnummer(a.getHausnummer());
        final AdressKlassifizierung klassifizierung = a.getKlassifizierung();
        if (klassifizierung != null) {
            ft.setAdressKlassifizierungOID(klassifizierung.getId());
        } else {
        }
        ft.setNotiz(a.getNotiz());
        ft.setOrtsname(a.getOrtsname());
        ft.setPlz(a.getPlz());
        final State state = a.getState();
        if (state != null) {
            ft.setStateOID(state.getId());
        } else {
        }
        ft.setStrasse(a.getStrasse());
        ft.setId(a.getId());

        return ft;
    }


    public static class FlatAdresse extends BaseFlatEntity {
        private String strasse;
        private String hausnummer;
        private String notiz;
        private String ortsname;
        private Long adressKlassifizierungOID;
        private String plz;
        private boolean grosskundenplz;
        private Long stateOID;


        public boolean isGrosskundenplz() {
            return grosskundenplz;
        }

        public void setGrosskundenplz(final boolean grosskundenplz) {
            this.grosskundenplz = grosskundenplz;
        }

        public String getHausnummer() {
            return hausnummer;
        }

        public void setHausnummer(final String hausnummer) {
            this.hausnummer = hausnummer;
        }

        public Long getAdressKlassifizierungOID() {
            return adressKlassifizierungOID;
        }

        public void setAdressKlassifizierungOID(final Long adressKlassifizierungOID) {
            this.adressKlassifizierungOID = adressKlassifizierungOID;
        }

        public String getNotiz() {
            return notiz;
        }

        public void setNotiz(final String notiz) {
            this.notiz = notiz;
        }

        public String getOrtsname() {
            return ortsname;
        }

        public void setOrtsname(final String ortsname) {
            this.ortsname = ortsname;
        }

        public String getPlz() {
            return plz;
        }

        public void setPlz(final String plz) {
            this.plz = plz;
        }

        public Long getStateOID() {
            return stateOID;
        }

        public void setStateOID(final Long stateOID) {
            this.stateOID = stateOID;
        }

        public String getStrasse() {
            return strasse;
        }

        public void setStrasse(final String strasse) {
            this.strasse = strasse;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof FlatAdresse)) {
                return false;
            }

            final FlatAdresse that = (FlatAdresse) o;

            if (grosskundenplz != that.grosskundenplz) {
                return false;
            }
            if (hausnummer != null ? !hausnummer.equals(that.hausnummer) : that.hausnummer != null) {
                return false;
            }
            if (adressKlassifizierungOID != null ? !adressKlassifizierungOID.equals(that.adressKlassifizierungOID) : that.adressKlassifizierungOID != null) {
                return false;
            }
            if (notiz != null ? !notiz.equals(that.notiz) : that.notiz != null) {
                return false;
            }
            if (ortsname != null ? !ortsname.equals(that.ortsname) : that.ortsname != null) {
                return false;
            }
            if (plz != null ? !plz.equals(that.plz) : that.plz != null) {
                return false;
            }
            if (stateOID != null ? !stateOID.equals(that.stateOID) : that.stateOID != null) {
                return false;
            }
            if (strasse != null ? !strasse.equals(that.strasse) : that.strasse != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = 0;
            result = 31 * result + (strasse != null ? strasse.hashCode() : 0);
            result = 31 * result + (hausnummer != null ? hausnummer.hashCode() : 0);
            result = 31 * result + (notiz != null ? notiz.hashCode() : 0);
            result = 31 * result + (ortsname != null ? ortsname.hashCode() : 0);
            result = 31 * result + (adressKlassifizierungOID != null ? adressKlassifizierungOID.hashCode() : 0);
            result = 31 * result + (plz != null ? plz.hashCode() : 0);
            result = 31 * result + (grosskundenplz ? 1 : 0);
            result = 31 * result + (stateOID != null ? stateOID.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatAdresse");
            sb.append("{grosskundenplz=").append(grosskundenplz);
            sb.append(", strasse='").append(strasse).append('\'');
            sb.append(", hausnummer='").append(hausnummer).append('\'');
            sb.append(", notiz='").append(notiz).append('\'');
            sb.append(", ortsname='").append(ortsname).append('\'');
            sb.append(", adressKlassifizierungOID='").append(adressKlassifizierungOID).append('\'');
            sb.append(", plz='").append(plz).append('\'');
            sb.append(", stateOID=").append(stateOID);
            sb.append('}');
            return sb.toString();
        }
    }

    public static class AdresseResult extends BaseOrmResult<FlatAdresse> {
        //        private List<Adresse> klassifizierungen = new ArrayList<Adresse>();
        //
        //
        //        public List<Adresse> getKlassifizierungen(){
        //            return klassifizierungen;
        //        }
        //
        //        public void setKlassifizierungen(List<Adresse> klassifizierungen){
        //            this.klassifizierungen = klassifizierungen;
        //        }

        public List<FlatAdresse> getObjList() {
            return objList;
        }

        public void setObjList(final List<FlatAdresse> objList) {
            this.objList = objList;
        }
    }
}
