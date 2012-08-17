/*
 * Copyright (c) 2011 - RapidPM. All Rights Reserved.
 */

package org.rapidpm.validate.comparators;

import org.rapidpm.validate.Comparator;
import org.rapidpm.validate.ValidatorAnnotation;
import org.rapidpm.validate.annotations.ValidateFloatRange;

import java.lang.annotation.Annotation;

/**
 * Comparator zur Prüfung des Float-Wertebereichs.
 *
 * @author Christain Ernst
 */
@ValidatorAnnotation(ValidateFloatRange.class)
public class ComparatorFloatRange implements Comparator<Float> {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean compare(final Float value, final Annotation annotation) {
        final ValidateFloatRange validate = (ValidateFloatRange) annotation;
        return value >= validate.begin() && value <= validate.end();
    }
}