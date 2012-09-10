package org.rapidpm.logging;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.03.11
 * Time: 16:14
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.apache.log4j.Logger;

import javax.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Named;

@Named(value = "LogFactory")
@RequestScoped
@Default
public class LogFactory {


    public LogFactory() {
    }

    @Produces
    public Logger createLogger(InjectionPoint ip) {
        return Logger.getLogger(ip.getMember().getDeclaringClass().getName());
    }

}