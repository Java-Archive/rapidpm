/*
 * Copyright (c) 2011 - NeoScio. All Rights Reserved.
 */

package org.rapidpm.validate;

import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Validator um Felder eines bestimmten Objekts zu validieren.
 *
 * @author Christain Ernst
 */
public class Validator {
    private static final Logger logger = Logger.getLogger(Validator.class);

    /**
     * Validation mit Meldung über Exception.
     *
     * @param validate Zu validierendes Objekt.
     * @throws ValidationException Exception bei fehlgeschlagener Validierung.
     */
    public static void validationAlert(final Object validate) throws ValidationException {
        if (!Validator.validate(validate)) {
            final Class<?> aClass = validate.getClass();
            throw new ValidationException("The use of an not valid object from '" + aClass.getSimpleName() + "' is not allowed!");
        } else {
            // Alles inordnung
        }
    }

    /**
     * Validierung des Objekts.
     *
     * @param validate Zu valideriendes Objekt.
     * @return Validierungsergebnis
     */
    public static boolean validate(final Object validate) {
        final Class<?> clazz = validate.getClass();
        final String name = clazz.getSimpleName();
        final Field[] fields = clazz.getDeclaredFields();
        for (final Field field : fields) {
            // Annotationen der Variable
            final Annotation[] annotations = field.getAnnotations();
            for (final Annotation annotation : annotations) {
                try {
                    final Class<? extends Annotation> aClass = annotation.annotationType();
                    // Überprüfen, ob sich in der Registry ein Comparartor für die Annotation befindet
                    if (ValidatorComparatorRegistry.hasValidatorComparatorFor(aClass)) {
                        final Comparator comparator = ValidatorComparatorRegistry.getValidatorComparatorFor(aClass);
                        // Zugriff auf private Felder über Reflection erlauben
                        field.setAccessible(true);
                        // Prüfen, ob die Variable den Anforderungen der Annotation entspricht
                        if (!comparator.compare(field.get(validate), annotation)) {
                            logger.error('@' + aClass.getSimpleName() +
                                    ": The Field '" + field.getName() + "' of '" + name + "' is not Valid!");
                            return false;
                        }
                    } else {
                        if (logger.isDebugEnabled()) {
                            logger.debug("No Comparator found for: @" + aClass.getSimpleName());
                        }
                    }
                } catch (IllegalAccessException e) {
                    logger.error(e);
                }
            }
        }

        return true;
    }
}