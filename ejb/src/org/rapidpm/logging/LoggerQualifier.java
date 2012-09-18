package org.rapidpm.logging;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright by Sven Ruppert // chef@sven-ruppert.de
 * <p/>
 * User: svenruppert
 * Date: 18.09.12
 * Time: 06:46
 * <p/>
 * Version:
 */
@Qualifier
@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
public @interface  LoggerQualifier {

}
