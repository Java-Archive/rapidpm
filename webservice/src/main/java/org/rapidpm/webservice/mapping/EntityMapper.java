package org.rapidpm.webservice.mapping;

import org.apache.shiro.authz.AuthorizationException;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;

import java.util.ArrayList;
import java.util.List;

/**
 * Der Entity-Mapper dient zum Konvertieren von Entities in FlatEntities und andersrum von FlatEntities in Entities.
 *
 * @param <T>  Entity type.
 * @param <FT> FlatEntity type.
 * @see FlatBaseWS
 */
// public abstract class EntityMapper<T, FT extends FlatEntity<T>> {
public abstract class EntityMapper<T, FT extends FlatEntity> {

    /**
     * Berechtigung, ob der Benutzer Daten von dem Objekt abfragen darf.
     */
    public static final String PERMISSION_SELECT = "select";
    /**
     * Berechtigung, ob der Benutzer Daten von dem Objekt anlegen oder ändern darf.
     */
    public static final String PERMISSION_UPDATE = "update";
    /**
     * Berechtigung, ob der Benutzer Daten von dem Objekt löschen darf.
     */
    public static final String PERMISSION_DELETE = "delete";

    protected final Class<T> entityType;
    protected final Class<FT> flatEntityType;
    protected final DaoFactory daoFactory;

    /**
     * Erstellt einen Entity-Mapper.
     *
     * @param entityType     Typ der Entity-Objekte.
     * @param flatEntityType Typ der Flat-Entity-Objekte.
     */
    public EntityMapper(final Class<T> entityType, final Class<FT> flatEntityType) {
        this.entityType = entityType;
        this.flatEntityType = flatEntityType;
        daoFactory = DaoFactorySingelton.getInstance();
    }

    /**
     * Erzeugt ein neues FlatEntity-Objekt anhand der Entity.
     *
     * @param entity Die original Entity.
     * @return Die FlatEntity mit flacher Objekthirarchie oder <code>null</code>, wenn ein Fehler aufgetreten ist.
     * @see #toFlatEntityList(Iterable)
     * @see #toEntity(FlatEntity)
     */
    public FT toFlatEntity(final T entity) {
        if (entity == null) {
            return null;
        }
        final FT flatEntity;
        try {
            flatEntity = flatEntityType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
        flatEntity.fromEntity(entity);
        return flatEntity;
    }

    /**
     * Ermittelt ein bestehendes Entity-Objekt (anhand der Entity-ID) oder erstellt ein neues, wenn erforderlich.
     *
     * @param flatEntity Das FlatEntity-Objekt.
     * @return Das Entity-Objekt oder <code>null</code>, wenn ein Fehler aufgetreten ist.
     * @see #toEntityList(Iterable)
     * @see #toFlatEntity(Object)
     */
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
                return null;
            }
        } else {
            entity = findEntityById(id);
            if (entity == null) {
                return null;
            }
        }
        flatEntity.toEntity(entity, daoFactory);
        return entity;
    }

    /**
     * Ermittelt das Entity-Objekt anhand der ID.
     *
     * @param id ID der Entität.
     * @return Das Entity-Objekt oder <code>null</code>, wenn keine Entität mit der ID gefunden wurde.
     * @see org.rapidpm.persistence.DAO#findByID(Long)
     */
    // TODO DAO interface for all DAOs!
    protected abstract T findEntityById(Long id);

    /**
     * Konvertiert mehrere Entities in eine Liste von Flat-Entities.
     *
     * @param entities Entities.
     * @return Liste von Flat-Entities.
     * @see #toFlatEntity(Object)
     * @see #toEntityList(Iterable)
     */
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

    /**
     * Konvertiert mehrere Flat-Entities in eine Liste von Entities.
     *
     * @param flatEntities Flat-Entities.
     * @return Liste von Entities.
     * @see #toEntity(FlatEntity)
     * @see #toFlatEntityList(Iterable)
     */
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

    /**
     * Prüft, ob der aktuelle Benutzer die Berechtigung hat.
     *
     * @param permission Die zu prüfende Berechtigung.
     * @throws AuthorizationException Exception wird geworfen, wenn der Benutzer die Berechtigung <b>nicht</b> hat.
     * @see #PERMISSION_SELECT
     * @see #PERMISSION_UPDATE
     * @see #PERMISSION_DELETE
     */
    public void checkPermission(final String permission) throws AuthorizationException {
        final String className = entityType.getSimpleName();
        final String permissionString = className + ':' + permission;
//        final Subject user = SecurityUtils.getSubject();
//        user.checkPermission(permissionString); // TODO Session not working yet...
    }
}
