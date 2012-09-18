package org.rapidpm.data;

import org.apache.log4j.Logger;
import org.rapidpm.logging.LoggerQualifier;
import org.rapidpm.persistence.system.logging.LoggingEventEntry;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 01.07.11
 * Time: 01:10
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */
public class LoggingResult {
    private List<LoggingEventEntry> loggingEventEntries = new ArrayList<>();

    @Inject @LoggerQualifier
    private transient Logger logger;

    public List<LoggingEventEntry> getLoggingEventEntries() {
        return loggingEventEntries;
    }

    public void setLoggingEventEntries(final List<LoggingEventEntry> loggingEventEntries) {
        this.loggingEventEntries = loggingEventEntries;
    }
}