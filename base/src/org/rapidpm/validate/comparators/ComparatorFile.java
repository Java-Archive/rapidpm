/*
 * Copyright (c) 2011 - RapidPM. All Rights Reserved.
 */

package org.rapidpm.validate.comparators;

import org.rapidpm.validate.Comparator;
import org.rapidpm.validate.ValidatorAnnotation;
import org.rapidpm.validate.annotations.ValidateFile;

import java.io.File;
import java.lang.annotation.Annotation;

/**
 * Comparator zur Prüfung der File-Eigenschaften.
 *
 * @author Christain Ernst
 */
@ValidatorAnnotation(ValidateFile.class)
public class ComparatorFile implements Comparator<File> {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean compare(final File value, final Annotation annotation) {
        final ValidateFile validate = (ValidateFile) annotation;

        switch (validate.can()) {
            case Execute:
                return value.canExecute();
            case Read:
                return value.canRead();
            case Write:
                return value.canWrite();
            default:
                return false;
        }
    }
}
