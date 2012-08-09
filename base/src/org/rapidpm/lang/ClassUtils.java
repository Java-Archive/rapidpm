/*
 * Copyright (c) 2011 - NeoScio. All Rights Reserved.
 */

package org.rapidpm.lang;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Hilfsklasse für Klassen.
 *
 * @author Alexander Vos
 */
public class ClassUtils {
    private static final Logger logger = Logger.getLogger(ClassUtils.class);

    /**
     * Ermittelt den primitiven Datentyp einer boxed Klasse (z.B. <code>Integer -> int</code>).
     *
     * @param boxedType Boxed Klasse.
     * @return Der primitive Datentyp zur boxed Klasse oder der Datentyp der boxed Klasse,
     *         falls keine gültige boxed Klasse übergeben wird.
     * @see #primitive2boxed(Class)
     */
    public static Class boxed2primitive(final Class boxedType) {
        try {
            return (Class) boxedType.getField("TYPE").get(null);
        } catch (IllegalAccessException | ClassCastException | NoSuchFieldException e) {
            logger.error(e);
        }
        logger.warn(boxedType + " is not a boxed type");
        return boxedType;
    }

    private static final Map<Class, Class> primitiveMap = new HashMap<Class, Class>() {{
        put(byte.class, Byte.class);
        put(short.class, Short.class);
        put(int.class, Integer.class);
        put(long.class, Long.class);
        put(float.class, Float.class);
        put(double.class, Double.class);
        put(char.class, Character.class);
        put(boolean.class, Boolean.class);
        put(void.class, Void.class);
    }};

    /**
     * Ermittelt den boxed Datentyp einer primitiven Klasse (z.B. <code>int -> Integer</code>).
     *
     * @param primitiveType Primitive Klasse.
     * @return Der boxed Datentyp zur primitiven Klasse oder der Datentyp der boxed Klasse,
     *         falls keine gültige primitive Klasse übergeben wird.
     * @see #boxed2primitive(Class)
     */
    public static Class primitive2boxed(final Class primitiveType) {
        final Class boxedType = primitiveMap.get(primitiveType);
        if (boxedType != null) {
            return boxedType;
        } else {
            logger.warn(primitiveType + " is not a primitive type");
            return primitiveType;
        }
    }

    /**
     * Prüft ob die angegebene Klasse ein bestimmtes Interface implementiert (rekursiv).
     *
     * @param clazz          Die zu prüfende Klasse.
     * @param interfaceClass Das zu implementierende Interface.
     * @return <code>true</code> wenn das Interface implementiert wird; sonst <code>false</code>.
     */
    public static boolean implementsInterface(Class<?> clazz, final Class<?> interfaceClass) {
        if (interfaceClass != null && interfaceClass.isInterface()) {
            while (clazz != null && clazz != Object.class) {
                final Class<?>[] interfaces = clazz.getInterfaces();
                for (final Class<?> anInterface : interfaces) {
                    if (anInterface == interfaceClass) {
                        return true;
                    }
                }
                clazz = clazz.getSuperclass();
            }
        }
        return false;
    }

    public static boolean extendsClass(Class<?> clazz, final Class<?> superclass) {
        if (superclass != null) {
            while (clazz != null) {
                clazz = clazz.getSuperclass();
                if (clazz == superclass) {
                    return true;
                }
            }
        }
        return false;
    }

    private ClassUtils() {
    }
}