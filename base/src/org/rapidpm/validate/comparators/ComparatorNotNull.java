/*
 * Copyright (c) 2011 - NeoScio. All Rights Reserved.
 */

package org.rapidpm.validate.comparators;

import org.rapidpm.validate.Comparator;
import org.rapidpm.validate.ValidatorAnnotation;
import org.rapidpm.validate.annotations.ValidateNotNull;

import java.lang.annotation.Annotation;

/**
 * Comparator zur null-Wert Prüfung.
 *
 * @author Christain Ernst
 */
@ValidatorAnnotation(ValidateNotNull.class)
public class ComparatorNotNull implements Comparator<Object> {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean compare(final Object value, final Annotation annotation) {
        return value != null;
    }
}