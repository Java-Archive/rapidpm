package org.rapidpm.webservice.mapping;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.rapidpm.persistence.DAO;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Alexander Vos
 * Date: 03.12.12
 * Time: 13:53
 */
//@WebService
// public abstract class FlatBaseWS<T, DT extends DAO<Long, T>, FT extends FlatEntity<T>> {
// FT extends FlatEntity<T> funktioniert nicht mit den Web Services...
public abstract class FlatBaseWS<T, DT extends DAO<Long, T>, FT extends FlatEntity> {
    private static final Logger logger = Logger.getLogger(FlatBaseWS.class);

    protected static final String PERMISSION_SELECT = "select";
    protected static final String PERMISSION_UPDATE = "update";
    protected static final String PERMISSION_DELETE = "delete";

    private final Class<T> entityType;
    private final Class<FT> flatEntityType;
    protected final DaoFactory daoFactory;
    protected final DT dao;

    public FlatBaseWS(final Class<T> entityType, final Class<FT> flatEntityType) {
        this.entityType = entityType;
        this.flatEntityType = flatEntityType;
        daoFactory = DaoFactorySingelton.getInstance();
        dao = getDao();
    }

    protected abstract DT getDao();

    protected FT toFlatEntity(final T entity) {
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

    protected T toEntity(final FT flatEntity) {
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
            entity = dao.findByID(id);
            if (entity == null) {
                logger.error("Entity '" + entityType.getName() + "' with id " + id + " not found!");
                return null;
            }
        }
        flatEntity.toEntity(entity, daoFactory);
        return entity;
    }

    protected List<FT> toFlatEntityList(final Iterable<? extends T> entities) {
        final ArrayList<FT> flatEntityList = new ArrayList<>();
        for (final T entity : entities) {
            flatEntityList.add(toFlatEntity(entity));
        }
        return flatEntityList;
    }

    protected void checkPermission(final String permission) throws AuthorizationException {
        final Subject user = SecurityUtils.getSubject();
        final String className = entityType.getSimpleName();
        final String permissionString = className + ':' + permission;
        if (logger.isDebugEnabled()) {
            logger.debug("checkPermission(\"" + permission + "\"): " + permissionString);
        }
        // user.checkPermission(permissionString); // TODO Session not working yet...
    }

    @WebMethod
    public FT findById(@WebParam(name = "id") final Long id) {
        checkPermission(PERMISSION_SELECT);
        return toFlatEntity(dao.findByID(id));
    }

    @WebMethod
    public List<FT> findByIdList(@WebParam(name = "idList") final List<Long> idList) {
        checkPermission(PERMISSION_SELECT);
        final List<T> entityList = dao.loadWithOIDList(idList);
        return toFlatEntityList(entityList);
    }

    @WebMethod
    public List<FT> getAll() {
        checkPermission(PERMISSION_SELECT);
        final List<T> entityList = dao.loadAllEntities();
        return toFlatEntityList(entityList);
    }

    @WebMethod
    public void save(@WebParam(name = "entity") final FT flatEntity) {
        checkPermission(PERMISSION_UPDATE);
        final T entity = toEntity(flatEntity);
        daoFactory.saveOrUpdate(entity);
    }

    @WebMethod
    public void delete(@WebParam(name = "id") final Long id) {
        checkPermission(PERMISSION_DELETE);
        final T entity = dao.findByID(id);
        daoFactory.remove(entity);
    }
}
