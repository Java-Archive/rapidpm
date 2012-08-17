package org.rapidpm.persistence;

import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.persistence.system.logging.LogLevelEnum;
import org.rapidpm.persistence.system.logging.LoggingEventEntry;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractOneToOneConnectExecutor<D extends BaseDaoFactory, CE, E, RT extends BaseOrmResult<? extends BaseFlatEntity>> {


//    public AbstractOneToOneConnectExecutor(DaoFactoryBean daoFactoryBean) {
//        this.daoFactoryBean = daoFactoryBean;
//    }

    //    abstract class AbstractOneToOneConnectExecutor<CE, E, RT extends BaseOrmResult<? extends BaseFlatEntity>> {
    private final BaseDaoFactory.BaseDAO<Long, CE> connectEntityDAO;
    private final BaseDaoFactory.BaseDAO<Long, E> entityDAO;
    private final D daoFactoryBean;

    public AbstractOneToOneConnectExecutor(final D daoFactory,
                                           final BaseDaoFactory.BaseDAO<Long, CE> connectEntityDAO,
                                           final BaseDaoFactory.BaseDAO<Long, E> entityDAO) {
        this.connectEntityDAO = connectEntityDAO;
        this.entityDAO = entityDAO;
        this.daoFactoryBean = daoFactory;
    }

    public abstract void connect(E entity, CE connectEntity);

    public abstract RT getResult(E entity);

    public RT execute(final String methodName, final LogEventEntryWriterBean logEventEntryWriterBean,
                      final String sessionID, final Long connectEntityOID, final Long entityOID) {
        boolean valid = false;
        final List<LoggingEventEntry> loggingEventEntries = new ArrayList<LoggingEventEntry>();
        final E entity = entityDAO.findByID(entityOID);
        if (entity != null) {
            final CE connectEntity = connectEntityDAO.findByID(connectEntityOID);
            if (connectEntity != null) {
                connect(entity, connectEntity);
                daoFactoryBean.saveOrUpdate(entity);
                loggingEventEntries.add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO,
                        methodName, sessionID, connectEntityDAO.entityClass.getSimpleName() + " " + connectEntityOID + " wurde erfolgreich mit " +
                        entityDAO.entityClass.getSimpleName() + " " + entityOID + " verknüpft"));
                valid = true;
            } else {
                loggingEventEntries.add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO,
                        methodName, sessionID, "Es existiert keine Entität vom Typ " + connectEntityDAO.entityClass.getSimpleName() +
                        " mit der OID " + connectEntityOID));
            }
        } else {
            loggingEventEntries.add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO,
                    methodName, sessionID, "Es existiert keine Entität vom Typ " + entityDAO.entityClass.getSimpleName() +
                    " mit der OID " + entityOID));
        }
        final RT result = getResult(entity);
        result.setValid(valid);
        result.getLoggingEventEntries().addAll(loggingEventEntries);
        return result;
    }
}
//}
