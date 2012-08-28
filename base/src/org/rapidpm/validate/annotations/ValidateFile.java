/*
 * Copyright (c) 2011 - RapidPM. All Rights Reserved.
 */

package org.rapidpm.validate.annotations;

import java.lang.annotation.*;

/**
 * Annotation zur Prüfung der File-Eigenschaften.
 *
 * @author Christain Ernst
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateFile {
    /**
     * Mögliche File-Eigenschaften.
     */
    enum Can {
        /**
         * Ausführbar
         */
        Execute,
        /**
         * Lesbar
         */
        Read,
        /**
         * Beschreibbar
         */
        Write
    }

    /**
     * Zu überprüfende Eigenschaft.
     *
     * @return Zu überprüfende Eigenschaft.
     */
    Can can();
}
