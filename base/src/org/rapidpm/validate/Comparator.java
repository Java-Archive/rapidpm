/*
 * Copyright (c) 2011 - NeoScio. All Rights Reserved.
 */

package org.rapidpm.validate;

import java.lang.annotation.Annotation;

/**
 * Comparator Interface um die Überprüfung der Validierungsregeln zu implementieren.
 *
 * @param <T> Vergleichstyp
 * @author Christain Ernst
 */
public interface Comparator<T> {
    /**
     * Prüft ob der Wert den Anforderungen der Annotation entspricht.
     *
     * @param value      Zu prüfender Wert
     * @param annotation Validator Annotation
     * @return {@code true} wenn Anforderungen erfüllt sind
     *         {@code false} wenn nicht
     */
    public boolean compare(final T value, final Annotation annotation);
}