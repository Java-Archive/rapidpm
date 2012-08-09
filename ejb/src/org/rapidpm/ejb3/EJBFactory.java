package org.rapidpm.ejb3;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert / Sven Ruppert
 * Date: 03.05.12
 * Time: 15:39
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EJBFactory {
    private static final Logger logger = Logger.getLogger(EJBFactory.class);

    public static <T> T getEjbInstance(final Class<T> clazz) {

        final Stateless stateless = clazz.getAnnotation(Stateless.class);
        if (stateless != null) {
            return getInstance(stateless.name());
        } else {
            // Stateful geht nicht mit context-lookup
//            final Stateful stateful = clazz.getAnnotation(Stateful.class);
//            if (stateful != null) {
//                return getInstance(stateful.name());
//            } else {
            logger.error("Klasse hat keine EJB Annotation . " + clazz.getCanonicalName());
            return null;
//            }
        }
    }

    private static <T> T getInstance(final String jndiName) {
        try {
            final Context ctx = new InitialContext();
            final T obj = (T) ctx.lookup("java:module/" + jndiName);
            return obj;
        } catch (NamingException e) {
            logger.error(e);
        }
        return null;
    }


}
