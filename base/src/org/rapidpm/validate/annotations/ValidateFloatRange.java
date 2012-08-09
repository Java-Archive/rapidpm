/*
 * Copyright (C) 2011 RapidPM - www.rapidpm.org
 *
 * This file is part of the bachelor thesis: Development of a Prototype for Trend Detection
 * in German Patents using Dynamic Stop Word Filters
 *
 * Bachelorarbeit: Entwicklung eines Prototypen zur Trenderkennung
 * in deutschen Patenten unter Einsatz dynamischer Stoppwortfilter
 *
 * Fachhochschule Südwestfalen, Iserlohn
 * Fachbereich Informatik und Naturwissenschaften
 * Studiengang Angewandte Informatik
 */

package org.rapidpm.validate.annotations;

import java.lang.annotation.*;

/**
 * Annotation zur Prüfung des Float-Wertebereichs.
 *
 * @author Christain Ernst
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateFloatRange {
    /**
     * Startwert
     *
     * @return Startwert
     */
    float begin();

    /**
     * Endwert
     *
     * @return Endwert
     */
    float end();
}