package org.rapidpm.orm;

import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.orm.system.logging.LogLevelEnum;
import org.rapidpm.orm.system.logging.LoggingEventEntry;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractOneToManyConnectExecutor<D extends BaseDaoFactory, E, C, RT extends BaseOrmResult<? extends BaseFlatEntity>> {

    //    abstract class OneToManyConnectExecutor<E, C, RT extends BaseOrmResult<? extends BaseFlatEntity>> {
    private final BaseDaoFactory.BaseDAO<Long, E> entityDAO;
    private final BaseDaoFactory.BaseDAO<Long, C> entityCollectionDAO;
    private final D daoFactory;

    public AbstractOneToManyConnectExecutor(final D daoFactory,
                                            final BaseDaoFactory.BaseDAO<Long, E> entityDAO,
                                            final BaseDaoFactory.BaseDAO<Long, C> entityCollectionDAO) {
        this.entityDAO = entityDAO;
        this.entityCollectionDAO = entityCollectionDAO;
        this.daoFactory = daoFactory;
    }

    public abstract List<E> getCollection(C entityCollection);

    public abstract RT getResult(C entityCollection);

    public RT execute(final String methodName, final LogEventEntryWriterBean logEventEntryWriterBean,
                      final String sessionID, final Long entityOID, final Long entityCollectionOID) {
        boolean valid = false;
        final List<LoggingEventEntry> loggingEventEntries = new ArrayList<LoggingEventEntry>();
        final C collectionEnity = entityCollectionDAO.findByID(entityCollectionOID);
        if (collectionEnity != null) {
            try {
                final List<E> collection = getCollection(collectionEnity);
//                    final List<E> collection = collectionEnity;
                entityDAO.connectEntity(entityOID, collection);
                daoFactory.saveOrUpdate(collectionEnity);
                valid = true;
                loggingEventEntries.add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO,
                        methodName, sessionID, entityDAO.entityClass.getSimpleName() + " " + entityOID + " wurde erfolgreich mit " +
                        entityCollectionDAO.entityClass.getSimpleName() + " " + entityCollectionOID + " verknüpft"));
            } catch (Exception e) {
                valid = e instanceof BaseDaoFactory.BaseDAO.AlreadyConnectedException; // QUEST gültig, wenn bereits verknüpft?
                loggingEventEntries.add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO,
                        methodName, sessionID, e.getLocalizedMessage()));
            }
        } else {
            loggingEventEntries.add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO,
                    methodName, sessionID, "Es existiert keine Entität vom Typ " + entityCollectionDAO.entityClass.getSimpleName() +
                    " mit der OID " + entityCollectionOID));
        }
        final RT result = getResult(collectionEnity);
        result.setValid(valid);
        result.getLoggingEventEntries().addAll(loggingEventEntries);
        return result;
    }
}
//}
