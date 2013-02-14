package org.rapidpm.webservice.mapping;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.AuthorizationException;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Alexander Vos
 * Date: 12.12.12
 * Time: 17:18
 */
public abstract class EntityMapper<T, FT extends FlatEntity> {
    private static final Logger logger = Logger.getLogger(EntityMapper.class);

    public static final String PERMISSION_SELECT = "select";
    public static final String PERMISSION_UPDATE = "update";
    public static final String PERMISSION_DELETE = "delete";

    protected final Class<T> entityType;
    protected final Class<FT> flatEntityType;
    protected final DaoFactory daoFactory;

    public EntityMapper(final Class<T> entityType, final Class<FT> flatEntityType) {
        this.entityType = entityType;
        this.flatEntityType = flatEntityType;
        daoFactory = DaoFactorySingelton.getInstance();
    }

    public FT toFlatEntity(final T entity) {
        if (entity == null) {
            return null;
        }
        final FT flatEntity;
        try {
            flatEntity = flatEntityType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error(e.getMessage());
            return null;
        }
        flatEntity.fromEntity(entity);
        return flatEntity;
    }

    public T toEntity(final FT flatEntity) {
        if (flatEntity == null) {
            return null;
        }
        final T entity;
        final Long id = flatEntity.getId();
        if (id == null || id <= 0L) {
            try {
                entity = entityType.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error(e.getMessage());
                return null;
            }
        } else {
            entity = findEntityById(id);
            if (entity == null) {
                logger.error("Entity '" + entityType.getName() + "' with id " + id + " not found!");
                return null;
            }
        }
        flatEntity.toEntity(entity, daoFactory);
        return entity;
    }

    // TODO DAO interface for all DAOs!
    protected abstract T findEntityById(Long id);

    public List<FT> toFlatEntityList(final Iterable<? extends T> entities) {
        final List<FT> flatEntityList = new ArrayList<>();
        if (entities != null) {
            for (final T entity : entities) {
                final FT flatEntity = toFlatEntity(entity);
                if (flatEntity != null) {
                    flatEntityList.add(flatEntity);
                }
            }
        }
        return flatEntityList;
    }

    public List<T> toEntityList(final Iterable<? extends FT> flatEntities) {
        final List<T> entityList = new ArrayList<>();
        if (flatEntities != null) {
            for (final FT flatEntity : flatEntities) {
                final T entity = toEntity(flatEntity);
                if (entity != null) {
                    entityList.add(entity);
                }
            }
        }
        return entityList;
    }

    public void checkPermission(final String permission) throws AuthorizationException {
        final String className = entityType.getSimpleName();
        final String permissionString = className + ':' + permission;
        if (logger.isDebugEnabled()) {
            logger.debug("checkPermission(\"" + permission + "\"): " + permissionString);
        }
//        final Subject user = SecurityUtils.getSubject();
//        user.checkPermission(permissionString); // TODO Session not working yet...
    }
}
