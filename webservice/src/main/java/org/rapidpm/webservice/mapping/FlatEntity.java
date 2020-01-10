package org.rapidpm.webservice.mapping;

import org.rapidpm.persistence.DAO;
import org.rapidpm.persistence.DaoFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Basisklasse für "flache" Entities. Flache Entities sollten eine möglichst flache Objekthirarchie haben
 * (nicht tief verschachtelt), z.B. wird zu einem Benutzer nur die Benutzer-ID gespeichert und nicht
 * eine Referenz auf das Benutzer-Objekt. Außerdem können diese Objekte nur eine Teilsicht darstellen und
 * nicht alle Attribute übernehmen. Flache Objekte sind einfacher zu serialisieren (übers Netzwerk etc.).
 *
 * @param <T> Entity type.
 * @see EntityMapper
 */
public abstract class FlatEntity<T> {
    protected static final DAO.EntityUtils entityUtils = new DAO.EntityUtils();

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Konvertiert ein Entity-Objekt zu einer flachen Objekthirarchie.
     *
     * @param entity Das Entity-Objekt (Quelle, sollte nicht manipuliert werden).
     */
    public abstract void fromEntity(T entity);

    /**
     * Konvertiert dieses Objekt zurück in das original Entity-Objekt.
     * <p>Note: <b>Die ID wird von außen gesetzt und muss nicht erneut ermittelt werden.</b></p>
     *
     * @param entity     Das Zielobjekt.
     * @param daoFactory Die DAO-Factory kann zum Abfragen der DB für bestimmt Objekte benutzt werden.
     */
    public abstract void toEntity(T entity, DaoFactory daoFactory);

    /**
     * Ermittelt die ID von einem Entity-Objekt.
     *
     * @param entity Das Entity-Objekt.
     * @return Die ID des Entity-Objekts oder <code>null</code>, wenn das Objekt kein Entity-Objekt ist
     *         oder keine ID besitzt.
     */
    protected static Long getId(final Object entity) {
        if (entity != null) {
            return entityUtils.getOIDFromEntity(entity);
        }
        return null;
    }

    /**
     * Ermittelt ein Set von IDs anhand von einem mehreren Entity-Objekten.
     *
     * @param entities Die Entity-Objekte.
     * @return Set der IDs der Entity-Objekte.
     */
    protected static Set<Long> entitiesToIds(final Iterable<?> entities) {
        final Set<Long> idSet = new HashSet<>();
        for (final Object entity : entities) {
            if (entity != null) {
                final Long id = entityUtils.getOIDFromEntity(entity);
                if (id != null && id > 0L) {
                    idSet.add(id);
                } else {
                }
            } else {
            }
        }
        return idSet;
    }
}
