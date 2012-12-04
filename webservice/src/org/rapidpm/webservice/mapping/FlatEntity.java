package org.rapidpm.webservice.mapping;

import org.rapidpm.persistence.DaoFactory;

/**
 * User: Alexander Vos
 * Date: 03.12.12
 * Time: 13:43
 */
public abstract class FlatEntity<T> {
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
}
