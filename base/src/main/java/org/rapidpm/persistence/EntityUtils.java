package org.rapidpm.persistence;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            final Constructor<?> constructor = entityClass.getConstructor();
            E entity = (E) constructor.newInstance();
            for (final String property : vertex.getPropertyKeys()) {
                for (final Field field : entityClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    if (!Modifier.isTransient(field.getModifiers()) &&
                            !Modifier.isStatic(field.getModifiers()) &&
                            field.getName().equals(property)) {
                        if(field.getType() == Date.class){
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date value = null;
                            try {
                                value = formatter.parse(vertex.getProperty(property).toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            field.set(entity, value);
                        } else {
                            field.set(entity, (vertex.getProperty(property)));
                        }
                        break;
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

    public OrientVertex convertEntityToTransientVertex(final E entity) {
        try {
            final OrientVertex vertex = new OrientVertex();
            for (final Field field : entityClass.getDeclaredFields()) {
                field.setAccessible(true);
                if (!Modifier.isTransient(field.getModifiers()) &&
                        !Modifier.isStatic(field.getModifiers()) &&
                        field.get(entity) != null) {
                    if(!field.getName().equals("id")){
                        vertex.setProperty(field.getName(), field.get(entity));
                    }
                }
            }
            return vertex;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Object> convertEntityToKeyValueMap(final E entity) {
        try {
            final Map<String, Object> keyValueMap = new HashMap<>();
            for (final Field field : entityClass.getDeclaredFields()) {
                field.setAccessible(true);
                if (!Modifier.isTransient(field.getModifiers()) &&
                        !Modifier.isStatic(field.getModifiers()) &&
                        field.get(entity) != null) {
                    if(field.getType() == Date.class) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String value = null;
                        value = formatter.format(field.get(entity));
                        keyValueMap.put(field.getName(), value);
                    } else {
                        keyValueMap.put(field.getName(), field.get(entity));
                    }
                }
            }
            return keyValueMap;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
