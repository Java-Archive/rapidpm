package org.rapidpm.logging;

import org.rapidpm.Constants;
import org.rapidpm.orm.system.logging.LoggingEventEntry;
import org.rapidpm.orm.system.logging.LoggingEventParam;
import org.rapidpm.orm.system.security.Benutzer;
import org.rapidpm.orm.system.security.BenutzerGruppe;
import org.rapidpm.orm.system.security.BenutzerWebapplikation;
import org.rapidpm.orm.system.security.Mandantengruppe;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 10.06.2010
 *        Time: 23:54:39
 */


public class LogEventEntryFileWriter {
    private static final Logger logger = Logger.getLogger(LogEventEntryFileWriter.class);

    private static final String LOGGING_DATA_DIR = Constants.DATA_BASE_DIR + Constants.PATH_SEP + "logging";

    private final SimpleDateFormat formatDay = new SimpleDateFormat(Constants.YYYY_MM_DD, Locale.GERMANY);
    private final SimpleDateFormat formatDayHour = new SimpleDateFormat(Constants.YYYY_MM_DD_HH, Locale.GERMANY);
    private final SimpleDateFormat formatDayHourMinute = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS, Locale.GERMANY);
    private static final int MAX_LOG_EVENT_ENTRIES = 10;
    private List<LoggingEventEntry> eventList = new LinkedList<>();

    private Semaphore eventListSemaphore = new Semaphore(1);

    static {
        BZip2CompressorOutputStream.chooseBlockSize((long) BZip2CompressorOutputStream.MAX_BLOCKSIZE);
    }

    //TODO skaliert mehr wenn jede Anwendung sein eigenen Writer hat..

    public void writeLogEvent(final LoggingEventEntry eventEntry) {
        try {
            eventListSemaphore.acquire();
            eventList.add(eventEntry);
            final int eventListSize = eventList.size();
            if (eventListSize >= MAX_LOG_EVENT_ENTRIES) {
                writeBzip2ContentFile();
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("LogEventEntry in die Liste gelegt .. NR : " + eventListSize);
                }
            }
            eventListSemaphore.release();
        } catch (InterruptedException e) {
            logger.error(e);
        }

    }

    private void writeBzip2ContentFile() {
        final File dataVerzeichnis = new File(LOGGING_DATA_DIR, formatDayHour.format(new Date()));
        if (dataVerzeichnis.exists()) {
            logger.debug("DataVerzeichnis existiert schon : " + dataVerzeichnis.getAbsolutePath());
        } else {
            dataVerzeichnis.mkdir();
        }
        final String filenNameContent = "Logging_File_" + formatDayHourMinute.format(new Date()) + ".txt.bz2";


        final File contentFile = new File(dataVerzeichnis, filenNameContent);
        try {
            final BZip2CompressorOutputStream bzippedOut = createBzip2FileOS(contentFile);
            final XMLOutputter outputter = new XMLOutputter();
            final Element rootElement = new Element("loggingevents");

            for (final LoggingEventEntry loggingEventEntry : eventList) {
                final Element element = new Element("logging_event");
                rootElement.addContent(element);
                createLogEventElementAttributes(loggingEventEntry, element);

                final Set<LoggingEventParam> loggingEventParamSet = loggingEventEntry.getParameter();
                for (final LoggingEventParam param : loggingEventParamSet) {
                    final String paramName = param.getParamName();
                    final String paramvalue = param.getParamValue();
                    final Element paramElement = new Element("param_element");
                    element.addContent(paramElement);
                    final Attribute aName = new Attribute("paramName", paramName);
                    paramElement.setAttribute(aName);

                    final Attribute paramValue = new Attribute("paramValue", paramvalue);
                    paramElement.setAttribute(paramValue);
                }
            }

            outputter.output(rootElement, bzippedOut);
            bzippedOut.flush();
            bzippedOut.close();
            eventList.clear();

        } catch (IOException e) {
            logger.error(e);
        }
    }

    private void createLogEventElementAttributes(final LoggingEventEntry loggingEventEntry, final Element element) {
        final Attribute aMethodName = new Attribute("methodname", loggingEventEntry.getMethodName());
        final Attribute aLogLevel = new Attribute("loglevel", loggingEventEntry.getLogLevel());
        final Attribute aSessionID = new Attribute("session_id", loggingEventEntry.getSessionID());
        final Date date = loggingEventEntry.getTimestamp();
        final Attribute aTimeStamp = new Attribute("timestamp", formatDayHourMinute.format(date));

        final Benutzer benutzer = loggingEventEntry.getBenutzer();
        final String login = benutzer.getLogin();
        final Attribute aLogin = new Attribute("login", login);

        final BenutzerGruppe benutzerGruppe = benutzer.getBenutzerGruppe();
        final String grpName = benutzerGruppe.getGruppenname();
        final Attribute aGrpName = new Attribute("grpName", grpName);

        final BenutzerWebapplikation webapplikation = benutzer.getBenutzerWebapplikation();
        final String webAppName = webapplikation.getWebappName();
        final Attribute aWebAppName = new Attribute("webAppName", webAppName);

        final Mandantengruppe mandantengruppe = benutzer.getMandantengruppe();
        final String mandantengruppenName = mandantengruppe.getMandantengruppe();
        final Attribute aMandantengruppe = new Attribute("mandantengruppe", mandantengruppenName);

        element.setAttribute(aMandantengruppe);
        element.setAttribute(aWebAppName);
        element.setAttribute(aGrpName);
        element.setAttribute(aLogin);
        element.setAttribute(aMethodName);
        element.setAttribute(aLogLevel);
        element.setAttribute(aSessionID);
        element.setAttribute(aTimeStamp);
    }

    private BZip2CompressorOutputStream createBzip2FileOS(final File file) throws IOException {
        final File bz2File = file;
        final BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(bz2File));
        final BZip2CompressorOutputStream bzippedOut = new BZip2CompressorOutputStream(outStream);
        return bzippedOut;
    }

}