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

package org.rapidpm.validate;

import java.lang.annotation.*;

/**
 * Annotation für Validatoren.
 *
 * @author Christain Ernst
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidatorAnnotation {
    /**
     * Klasse des Validators.
     *
     * @return Klasse
     */
    Class<? extends Annotation> value();
}
