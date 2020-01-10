/*
 * Copyright (c) 2011 - RapidPM. All Rights Reserved.
 */

package org.rapidpm.tools.registry;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.rapidpm.lang.ClassUtils;
import org.rapidpm.lang.PackageClassLoader;

import java.lang.annotation.Annotation;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Abstrakte Basisklasse für globale Typ-Registries.
 *
 * @param <A> Annototationstyp.
 * @param <T> Rückgabetyp der Registry.
 * @author Alexander Vos
 */
public abstract class AbstractRegistry<A extends Annotation, T> {
    private static final PackageClassLoader packageClassLoader = new PackageClassLoader();

    /**
     * Klassenmap.
     */
    protected final Map<Class<?>, Class<? extends T>> classMap = Maps.newHashMap();
    /**
     * Packagenamen zum Suchen nach Klassen.
     */
    protected final Set<String> pkgNameSet = Sets.newHashSet();

    /**
     * Standardobjekt für die Rückgabe bei einem Fehlerfall.
     */
    protected T defaultObject;

    /**
     * Erstellt eine Typ-Registry.
     *
     * @param annotationClass    Annototationstyp.
     * @param defaultObject      Standardobjekt für die Rückgabe bei einem Fehlerfall (kann <code>null</code> sein).
     * @param scanCurrentPackage <code>true</code>, wenn das Package der konkreten Registry rekursiv durchsucht werden soll.
     * @param packageNames       Weitere Packetnamen zum Suchen nach Klassen.
     * @throws NullPointerException Wenn <tt>annotationClass</tt> <code>null</code> ist.
     * @see #AbstractRegistry(Class, Object)
     * @see #AbstractRegistry(Class)
     */
    public AbstractRegistry(final Class<A> annotationClass, final T defaultObject,
                            final boolean scanCurrentPackage, final String... packageNames) {
        Preconditions.checkNotNull(annotationClass);
        if (scanCurrentPackage) {
            pkgNameSet.add(getClass().getPackage().getName());
        }
        pkgNameSet.addAll(Arrays.asList(packageNames));
        init(annotationClass);
        this.defaultObject = defaultObject;
    }

    /**
     * Erstellt eine Typ-Registry und durchsucht das Package der konkreten Registry rekursiv nach Klassen.
     *
     * @param annotationClass Annototationstyp.
     * @param defaultObject   Standardobjekt für die Rückgabe bei einem Fehlerfall (kann <code>null</code> sein).
     * @throws NullPointerException Wenn <tt>annotationClass</tt> <code>null</code> ist.
     * @see #AbstractRegistry(Class, Object, boolean, String...)
     * @see #AbstractRegistry(Class)
     */
    public AbstractRegistry(final Class<A> annotationClass, final T defaultObject) {
        this(annotationClass, defaultObject, true);
    }

    /**
     * Erstellt eine Typ-Registry ohne Standardobjekt und durchsucht das Package der konkreten Registry rekursiv nach Klassen.
     *
     * @param annotationClass Annototationstyp.
     * @throws NullPointerException Wenn <tt>annotationClass</tt> <code>null</code> ist.
     * @see #AbstractRegistry(Class, Object, boolean, String...)
     * @see #AbstractRegistry(Class, Object)
     */
    public AbstractRegistry(final Class<A> annotationClass) {
        this(annotationClass, null, true);
    }

    /**
     * Muss die zuständige Klasse der Annotation <tt>annotation</tt> zurück geben.
     *
     * @param annotation Annotation.
     * @return Zuständige Klasse der Annotation.
     */
    protected abstract Class<?> getClass(A annotation);

    /**
     * Initialisiert die Typ-Registry.
     *
     * @param annotationClass Annototationstyp.
     */
    public void init(final Class<A> annotationClass) {
        for (final String packageName : pkgNameSet) {
            final List<Class> classes = packageClassLoader.getClasses(packageName);
            for (final Class clazz : classes) {
                final Annotation annotation = clazz.getAnnotation(annotationClass);
                if (annotation != null) {
                    classMap.put(getClass(annotationClass.cast(annotation)), clazz);
                } else {
                }
            }
        }
    }

    /**
     * Gibt die Klassenmap dieser Typ-Registry zurück.
     *
     * @return Klassenmap dieser Typ-Registry.
     * @see #getClasses()
     * @see #getValues()
     * @see #getClassFor(Class)
     */
    public Map<Class<?>, Class<? extends T>> getClassMap() {
        return classMap;
    }

    /**
     * Gibt die Klassen dieser Typ-Registry zurück.
     *
     * @return Klassen dieser Typ-Registry.
     * @see #getClassMap()
     * @see #getValues()
     * @see #getClassFor(Class)
     */
    public Set<Class<?>> getClasses() {
        return classMap.keySet();
    }

    /**
     * Gibt die Klassen-Instanzen dieser Typ-Registry zurück.
     *
     * @return Klassenwerte dieser Typ-Registry.
     * @see #getClassMap()
     * @see #getClasses()
     * @see #getClassFor(Class)
     */
    public Collection<Class<? extends T>> getValues() {
        return classMap.values();
    }

    /**
     * Gibt das Standardobjekt dieser Typ-Registry zurück.
     *
     * @return Das Standardobjekt dieser Typ-Registry.
     * @see #setDefaultObject(Object)
     */
    public T getDefaultObject() {
        return defaultObject;
    }

    /**
     * Setzt das Standardobjekt dieser Typ-Registry.
     *
     * @param defaultObject Das Standardobjekt (kann <code>null</code> sein).
     * @see #getDefaultObject()
     */
    public void setDefaultObject(final T defaultObject) {
        this.defaultObject = defaultObject;
    }

    /**
     * Prüft, ob diese Typ-Registry die angegebene Klassen-Instanz besitzt.
     *
     * @param clazz Typklasse.
     * @return <code>true</code>, wenn diese Typ-Registry die angegebene Klasse besitzt, sonst <code>false</code>.
     * @see #getClassMap()
     * @see #getClasses()
     * @see #getClassFor(Class)
     */
    public boolean hasClassFor(final Class<?> clazz) {
        if (classMap.containsKey(clazz)) {
            return true;
        } else if (clazz.isPrimitive()) {
            final Class boxedType = ClassUtils.primitive2boxed(clazz);
            return classMap.containsKey(boxedType);
        }
        return false;
    }

    /**
     * Gibt die Klassen-Instanz der angegebenen Klasse dieser Typ-Registry zurück.
     *
     * @param clazz Typklasse.
     * @return Die Klassen-Instanz oder <code>null</code>, wenn die Klasse nicht existiert.
     * @see #getClassMap()
     * @see #getClasses()
     * @see #hasClassFor(Class)
     */
    public T getClassFor(final Class<?> clazz) {
        T result = defaultObject;
        Class<? extends T> aClass = classMap.get(clazz);
        if (aClass == null && clazz.isPrimitive()) {
            final Class boxedType = ClassUtils.primitive2boxed(clazz);
            aClass = classMap.get(boxedType);
        }
        if (aClass != null) {
            try {
                result = aClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
            }
        } else {
        }
        return result;
    }
}