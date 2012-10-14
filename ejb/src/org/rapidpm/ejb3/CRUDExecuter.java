package org.rapidpm.ejb3;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 17.03.11
 * Time: 15:18
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.system.logging.LogLevelEnum;
import org.rapidpm.persistence.system.logging.LoggingEntityEntry;
import org.rapidpm.persistence.system.logging.LoggingEventEntry;
import org.rapidpm.persistence.system.logging.LoggingEventParam;
import org.rapidpm.persistence.system.security.Benutzer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class CRUDExecuter<FT extends BaseFlatEntity, T, RT extends BaseOrmResult> {
    private static final Logger logger = Logger.getLogger(CRUDExecuter.class);

    //    private T byID;
    private Class<RT> resultTypeClass;
    private List<LoggingEventEntry> logEntries = new ArrayList<>();

    private Long lastOID = null;

    protected CRUDExecuter(final Class<RT> resultTypeClass) {
        this.resultTypeClass = resultTypeClass;
    }

    protected abstract T flatType2Type(final FT flatTypeEntity);

    protected abstract DaoFactory getDaoFactoryBean();

    protected abstract T findByID(final Long oid);

    protected abstract LogEventEntryWriterBean getLogger();

    private RT createResultType() {
        try {
            return resultTypeClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error(e);
        }
        return null;
    }

    public RT remove(final String sessionid, final Long uid, final Long oid) {
        final T byID = findByID(oid);
        final RT result = createResultType();
        if (byID != null) {
            lastOID = oid;
            getDaoFactoryBean().remove(byID);
            final LoggingEventEntry eventEntry = getLogger().createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "remove OK");
            result.getLoggingEventEntries().add(eventEntry);
            result.setValid(true);
        } else {
            lastOID = -1L;
            result.getLoggingEventEntries().add(getLogger().createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "oid falsch"));
            result.getLoggingEventEntries().add(getLogger().createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "remove FAILED"));
            result.setValid(false);
        }
        return result;

    }

    public RT saveOrUpdate(final String sessionid, final Long uid, final FT flatEntity) {
        final T entity = flatType2Type(flatEntity);
        final RT result = createResultType();
        if (entity == null) {
            final LoggingEventEntry eventEntry = getLogger().createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "saveOrUpdateTX", sessionid, "FAILED");
            final LoggingEventParam param = new LoggingEventParam();
            param.setParamName("flatType2Type");
            param.setParamValue("failed");
            eventEntry.getParameter().add(param);

            result.getLoggingEventEntries().add(eventEntry);
            result.setValid(false);
            lastOID = -1L;
        } else {

            final DaoFactory daoFactoryBean = getDaoFactoryBean();
            daoFactoryBean.saveOrUpdate(entity);
            final LoggingEventEntry eventEntry = getLogger().createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "saveOrUpdateTX", sessionid, "saveOrUpdate OK");
            result.getLoggingEventEntries().add(eventEntry);
            result.setValid(true);

            lastOID = daoFactoryBean.<T>getEntityUtils().getOIDFromEntity(entity);

            saveLogginEventEntry(uid, flatEntity, entity, daoFactoryBean);

            flatEntity.setId(lastOID);//falls OID durch persist gesetzt wurde..
            result.getGenericObjList().add(flatEntity); //alles per FlatEntity

        }
        for (final LoggingEventEntry logEntry : logEntries) {
            logEntry.setSessionID(sessionid);
        }

        result.getLoggingEventEntries().addAll(logEntries);
        logEntries.clear();
        return result;
    }

    private void saveLogginEventEntry(final Long uid, final FT flatEntity, final T entity, final DaoFactory daoFactoryBean) {
        final LoggingEntityEntry entry = new LoggingEntityEntry();
        final Benutzer byID = daoFactoryBean.getBenutzerDAO().findByID(uid);
        entry.setActor(byID);
        entry.setClassname(entity.getClass().getName());
        entry.setDate(new Date());

        final Long flatEntityId = flatEntity.getId();
        entry.setOid(flatEntityId);
        if (flatEntityId == null || flatEntityId == -1L) {
            entry.setAction(daoFactoryBean.getLogginEntityActionDAO().loadCreated());
        } else {
            entry.setAction(daoFactoryBean.getLogginEntityActionDAO().loadModified());

        }
        daoFactoryBean.saveOrUpdate(entry);
        entry.setOid(entry.getOid());
    }

    protected List<LoggingEventEntry> getLogEntries() {
        return logEntries;
    }

    public Long getLastOID() {
        return lastOID;
    }
}
