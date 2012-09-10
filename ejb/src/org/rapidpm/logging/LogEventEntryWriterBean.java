package org.rapidpm.logging;

import org.rapidpm.Constants;
import org.rapidpm.orm.DaoFactoryBean;
import org.rapidpm.orm.system.logging.LogLevelEnum;
import org.rapidpm.orm.system.logging.LoggingEventEntry;
import org.rapidpm.orm.system.logging.LoggingEventParam;
import org.rapidpm.orm.system.security.Benutzer;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 31.12.10
 * Time: 15:43
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */
@Stateless(name = "LogEventEntryWriterEJB")
public class LogEventEntryWriterBean {

    private final SimpleDateFormat formatDay = new SimpleDateFormat(Constants.YYYY_MM_DD, Locale.GERMANY);
    private final SimpleDateFormat formatDayHour = new SimpleDateFormat(Constants.YYYY_MM_DD_HH, Locale.GERMANY);
    private final SimpleDateFormat formatDayHourMinute = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS, Locale.GERMANY);

    @EJB(beanName = "DaoFactoryEJB")
    private transient DaoFactoryBean daoFactory;


    public LogEventEntryWriterBean() {
        super();
    }

    public LoggingEventEntry createLoggingEventEntry(final LogLevelEnum loglevel, final String methodname, final String sessionid, final String loggingmessage) {
        final LoggingEventEntry loggingEventEntry = new LoggingEventEntry();
        loggingEventEntry.setClassName(this.getClass().getName());
        loggingEventEntry.setLogLevel(loglevel.name());
        loggingEventEntry.setLoggingMessage(loggingmessage);
        loggingEventEntry.setMethodName(methodname);
        loggingEventEntry.setTimestamp(new Date());
        loggingEventEntry.setSessionID(sessionid);
        loggingEventEntry.setParameter(new HashSet<LoggingEventParam>());
        return loggingEventEntry;
    }


    public void writeLogEvents(final LoggingEventEntry... eventEntry) {
        for (final LoggingEventEntry loggingEventEntry : eventEntry) {
            daoFactory.saveOrUpdate(loggingEventEntry);
        }
    }

    public void writeLogEvents(final Benutzer benutzer, final LoggingEventEntry... eventEntry) {
        for (final LoggingEventEntry loggingEventEntry : eventEntry) {
            loggingEventEntry.setBenutzer(benutzer);
            daoFactory.saveOrUpdate(loggingEventEntry);
        }
    }

    public void writeLogEvents(final Collection<LoggingEventEntry> eventEntry) {
        for (final LoggingEventEntry loggingEventEntry : eventEntry) {
            daoFactory.saveOrUpdate(loggingEventEntry);
        }
    }

    public void writeLogEvents(final Collection<LoggingEventEntry> eventEntry, final Benutzer benutzer) {
        for (final LoggingEventEntry loggingEventEntry : eventEntry) {
            loggingEventEntry.setBenutzer(benutzer);
            daoFactory.saveOrUpdate(loggingEventEntry);
        }
    }

}
