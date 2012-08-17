package org.rapidpm.persistence;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 13.10.11
 * Time: 09:54
 */
public abstract class DataSetEntityFactory<T, E> extends EntityFactory<T> {

    protected final Set<E> dataSet = new HashSet<E>();

    public DataSetEntityFactory(final Class<T> clazz) {
        super(clazz);
    }

    public DataSetEntityFactory(final Class<T> clazz, final Collection<E> data) {
        super(clazz);
        dataSet.addAll(data);
    }

    @SafeVarargs()
    public DataSetEntityFactory(final Class<T> clazz, final E... data) {
        super(clazz);
        Collections.addAll(dataSet, data);
    }

    protected E getRandomDataSetElement() {
        return RND.nextElement(dataSet);
    }

}