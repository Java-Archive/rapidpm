package org.rapidpm.ejb3.interceptor;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 16.03.11
 * Time: 01:02
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */
@Inherited
@InterceptorBinding
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Logging {
}