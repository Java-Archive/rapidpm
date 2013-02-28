package org.rapidpm.webservice.mapping;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.util.List;

/**
 * Basisklasse für einfache Web-Services.
 *
 * @param <T>  Entity type.
 * @param <DT> DAO type.
 * @param <FT> Flat-Entity type.
 */
//@WebService
// public abstract class FlatBaseWS<T, DT extends DAO<Long, T>, FT extends FlatEntity<T>>
// FT extends FlatEntity<T> funktioniert nicht mit den Web Services...
public abstract class FlatBaseWS<T, DT extends DAO<Long, T>, FT extends FlatEntity> extends EntityMapper<T, FT> {
    private static final Logger logger = Logger.getLogger(FlatBaseWS.class);

    protected final DT dao;

    public FlatBaseWS(final Class<T> entityType, final Class<FT> flatEntityType) {
        super(entityType, flatEntityType);
        dao = getDao();
    }

    /**
     * Benötigt, um die korrekte DAO für die Entity-Klasse zu ermitteln.
     *
     * @return DAO zur Entity-Klasse.
     */
    protected abstract DT getDao();

    @Override
    protected T findEntityById(final Long id) {
        return dao.findByID(id);
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

    /**
     * Konvertiert das Flat-Entity-Objekt in ein Entity-Objekt und speichert es ab.
     *
     * @param flatEntity Flat-Entity-Objekt.
     * @return Die ID des gespeicherten Entity-Objekts.
     * @see #toEntity(FlatEntity)
     */
    @WebMethod
    public Long save(@WebParam(name = "entity") final FT flatEntity) {
        checkPermission(PERMISSION_UPDATE);
        final T entity = toEntity(flatEntity);
        daoFactory.saveOrUpdateTX(entity);
        final Long id = FlatEntity.entityUtils.getOIDFromEntity(entity);
        return id;
    }

    @WebMethod
    public boolean delete(@WebParam(name = "id") final Long id) {
        checkPermission(PERMISSION_DELETE);
        final T entity = dao.findByID(id);
        daoFactory.removeTX(entity);
        return true;
    }
}
