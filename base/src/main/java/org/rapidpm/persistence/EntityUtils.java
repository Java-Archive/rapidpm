package org.rapidpm.persistence;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marco on 23.05.2015.
 */
public class EntityUtils<E> {

    private Class<E> entityClass;

    public EntityUtils(final Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    public List<E> convertVerticesToEntities(Iterable<Vertex> entityVertices) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        final List<E> entities = new ArrayList<>();
        for (final Vertex entityVertex : entityVertices) {
            entities.add(convertVertexToEntity(entityVertex));
        }
        return entities;
    }

    // Booya!!!
    public E convertVertexToEntity(final Vertex vertex) {
        try {
            Constructor<?> constructor = entityClass.getConstructor();
            E entity = (E) constructor.newInstance();
            for (final String property : vertex.getPropertyKeys()) {
                for (final Field field : entityClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    if(field.getName().equals(property)){
                        try {
                            field.set(entity, vertex.getProperty(property));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            final Field idField = entityClass.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(entity, vertex.getId().toString());
            return entity;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }
}
