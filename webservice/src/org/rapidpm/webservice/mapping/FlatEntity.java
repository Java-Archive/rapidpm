package org.rapidpm.webservice.mapping;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;
import org.rapidpm.persistence.DaoFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * User: Alexander Vos
 * Date: 03.12.12
 * Time: 13:43
 */
public abstract class FlatEntity<T> {
    private static final Logger logger = Logger.getLogger(FlatEntity.class);
    protected static final DAO.EntityUtils entityUtils = new DAO.EntityUtils();

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public abstract void fromEntity(T entity);

    /**
     * Note: <b>DON'T set the Entity ID.</b>
     *
     * @param entity
     * @param daoFactory
     */
    public abstract void toEntity(T entity, DaoFactory daoFactory);

    protected Set<Long> entitiesToIds(final Iterable<?> entities) {
        final Set<Long> idSet = new HashSet<>();
        for (final Object entity : entities) {
            if (entity != null) {
                final Long id = entityUtils.getOIDFromEntity(entity);
                if (id != null && id > 0L) {
                    idSet.add(id);
                } else {
                    logger.error("Entity has no valid ID: " + entity);
                }
            } else {
                logger.error("Entity is null.");
            }
        }
        return idSet;
    }
}
