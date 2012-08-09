/*
 * Copyright (c) 2011 - NeoScio. All Rights Reserved.
 */

package org.rapidpm.validate;

import org.rapidpm.lang.PackageClassLoader;
import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Registry für die Validator-Comparatoren.
 *
 * @author Christain Ernst
 */
public class ValidatorComparatorRegistry {
    private static final Logger logger = Logger.getLogger(ValidatorComparatorRegistry.class);

    private static final Map<Class<? extends Annotation>, Class<? extends Comparator>> comparatorMap = new HashMap<>();
    private static final List<String> pkgNameList = new ArrayList<>();

    static {
        final Package comparatorPackage = ValidatorComparatorRegistry.class.getPackage();
        pkgNameList.add(comparatorPackage.getName() + ".comparators");
        final PackageClassLoader packageClassLoader = new PackageClassLoader();
        for (final String packageName : pkgNameList) {
            final List<Class> classes = packageClassLoader.getClasses(packageName);
            for (final Class aClass : classes) {
                final Annotation annotation = aClass.getAnnotation(ValidatorAnnotation.class);
                if (annotation != null) {
                    final ValidatorAnnotation responsibleFor = (ValidatorAnnotation) annotation;
                    final Class classResponsibleFor = responsibleFor.value();
                    comparatorMap.put(classResponsibleFor, aClass);
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Klasse '" + aClass.getSimpleName() + "' ohne ValidatorAnnotation-Annotation " + ValidatorAnnotation.class.getSimpleName());
                    }
                }
            }
        }
    }

    /**
     * Prüft das Vorhandensein eines bestimmten Validator-Comparators.
     *
     * @param clazz Klasse
     * @return {@code true} wenn Validator-Comparator enthalten ist
     *         {@code false} wenn nicht
     */
    public static boolean hasValidatorComparatorFor(final Class clazz) {
        return comparatorMap.containsKey(clazz);
    }

    /**
     * Gibt alle Validator-Comparatoren zurück.
     *
     * @return Validator-Comparatoren
     */
    public static List<Comparator> getValidatorComparators() {
        final List<Comparator> comparator = new ArrayList<>();
        for (final Map.Entry<Class<? extends Annotation>, Class<? extends Comparator>> classEntry : comparatorMap.entrySet()) {
            try {
                comparator.add(classEntry.getValue().newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error(e);
            }
        }

        return comparator;
    }

    /**
     * Gibt Validator-Comparator für eine bestimmte Klasse zurück.
     *
     * @param clazz Klasse
     * @return Validator-Comparator
     */
    public static Comparator getValidatorComparatorFor(final Class clazz) {
        Comparator comparator = null;
        if (comparatorMap.containsKey(clazz)) {
            try {
                comparator = comparatorMap.get(clazz).newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error(e);
            }
        } else {
            logger.error("Kein Comparator für diese Klasse '" + clazz.getSimpleName() + "' registriert: " + clazz.getSimpleName());
        }

        return comparator;
    }
}