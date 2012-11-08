package org.rapidpm.logging;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.03.11
 * Time: 16:14
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.apache.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Named;

@Named(value = "RPMLogFactory")
@RequestScoped
@Default
@LoggerQualifier
public class RPMLogFactory {


    public RPMLogFactory() {
    }

    @Produces
    public Logger createLogger(InjectionPoint ip) {
        return Logger.getLogger(ip.getMember().getDeclaringClass().getName());
    }

}