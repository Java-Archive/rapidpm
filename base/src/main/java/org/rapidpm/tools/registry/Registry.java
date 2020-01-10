/*
 * Copyright (c) 2011 - RapidPM. All Rights Reserved.
 */

package org.rapidpm.tools.registry;


import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Die globale Registry verwaltet alle Typ-Registry-Instanzen als Singleton-Objekte.
 *
 * @author Alexander Vos
 */
public final class Registry {

    // verwaltet alle konkreten Registries als Singleton-Objekte
    private static final Map<Class<? extends AbstractRegistry<?, ?>>, AbstractRegistry<?, ?>> registryMap =
            new HashMap<>();
//    private static final ClassToInstanceMap<AbstractRegistry<?, ?>> registryMap = MutableClassToInstanceMap.create();

    /**
     * Gibt die Typ-Registry-Instanz zurück oder legt eine neue an.
     *
     * @param registryClass Registry-Klasse.
     * @param <A>           Annototationstyp.
     * @param <T>           Rückgabetyp der Registry.
     * @return Instanz der Registry-Klasse oder <code>null</code>, wenn ein Fehler auftritt.
     * @see #get(Class, Class)
     */
    public static <A extends Annotation, T> AbstractRegistry<A, T> get(final Class<? extends AbstractRegistry<A, T>> registryClass) {
        @SuppressWarnings("unchecked")
        AbstractRegistry<A, T> registry = (AbstractRegistry<A, T>) registryMap.get(registryClass); // erfolgreicher Cast ist garantiert!
        if (registry == null) {
            try {
                registry = registryClass.newInstance();
                registryMap.put(registryClass, registry);
            } catch (InstantiationException | IllegalAccessException e) {
            }
        }
        return registry;
    }

    /**
     * Hilfsmethode für direkten Zugriff auf eine Klasse innerhalb einer konkreten Registry-Inbstanz.
     *
     * @param registryClass Registry-Klasse.
     * @param clazz         Klasse innerhalb der Registry.
     * @param <A>           Annototationstyp.
     * @param <T>           Rückgabetyp der Registry.
     * @return Instanz der Klasse innerhalb der Registry oder <code>null</code>, wenn ein Fehler auftritt.
     * @see #get(Class)
     */
    public static <A extends Annotation, T> T get(final Class<? extends AbstractRegistry<A, T>> registryClass, final Class<?> clazz) {
        final AbstractRegistry<A, T> registry = get(registryClass);
        return registry != null ? registry.getClassFor(clazz) : null;
    }

    private Registry() {
    }
}