/*
 * Copyright (c) 2011 - RapidPM. All Rights Reserved.
 */

package org.rapidpm.validate.annotations;

import java.lang.annotation.*;

/**
 * Annotation zur null-Wert Prüfung.
 *
 * @author Christain Ernst
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateNotNull {
}