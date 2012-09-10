package org.rapidpm.persistence;

import org.rapidpm.RandomGenerator;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.10.11
 * Time: 16:11
 */
public abstract class EntityFactory<T> {
    private static final Logger logger = Logger.getLogger(EntityFactory.class);

    protected static final RandomGenerator RND = RandomGenerator.getInstance();
    protected static final char INDEX_SEPARATOR = '_';

    protected final Class<T> clazz;
    protected int index;

    public EntityFactory(final Class<T> clazz) {
        this.clazz = clazz;
        init();
    }

    protected void init() {
        index = RND.nextInt(Integer.MAX_VALUE / 2); // System.nanoTime();
    }

    public abstract T createRandomEntity();

    public T createDefaultEntity() {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            logger.error(e);
        } catch (IllegalAccessException e) {
            logger.error(e);
        }
        return null;
    }

    public List<T> createRandomEntityList(final int count) {
        final List<T> entityList = new ArrayList<T>(count);
        for (int i = 0; i < count; i++) {
            entityList.add(createRandomEntity());
        }
        return entityList;
    }

    public List<T> createRandomEntityList(final int minCount, final int maxCount) {
        return createRandomEntityList(RND.nextInt(minCount, maxCount));
    }

    protected int nextIndex() {
        return ++index;
    }

    protected String combineStringWithNextIndex(final String str) {
        return str + INDEX_SEPARATOR + nextIndex();
    }

    protected String combineRandomStringWithNextIndex(final Collection<String> stringCollection) {
        return combineStringWithNextIndex(RND.nextElement(stringCollection));
    }
}
