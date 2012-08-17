package org.rapidpm.logging;

import org.rapidpm.persistence.system.logging.LogLevelEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author RapidPM
 * @version This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 13.01.2010
 *        Time: 16:45:31
 */

//import org.apache.log4j.Logger;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

public class LoggerRegistry {
    // private static final Logger logger = Logger.getLogger(LoggerRegistry.class);
    // public static final Log logger = LogFactory.getLog(LoggerRegistry.class);


    private Map<Class, LogLevelEnum> registry = new HashMap<>();

    public void addClassForLogging(final Class clazz, final LogLevelEnum logLevel) {
        registry.put(clazz, logLevel);
    }

    public LogLevelEnum getLogLevelForClass(final Class clazz) {
        final LogLevelEnum result;
        if (registry.containsKey(clazz)) {
            result = registry.get(clazz);
        } else {
            result = LogLevelEnum.NOT_LOGGING;
        }
        return result;
    }


}