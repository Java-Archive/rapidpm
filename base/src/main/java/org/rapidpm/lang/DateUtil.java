package org.rapidpm.lang;

import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.rapidpm.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Provides utility methods for handling dates.
 */
public class DateUtil {

    public static String createNextVersion() {
        final DateTime now = new DateTime();
        final int dayOfWeek = now.getDayOfWeek();
        final DateTime start = now.plusDays(((7 - dayOfWeek) - 2));
        final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        return start.toString(fmt);
    }

    public static List<String> createNextVersions() {
        final List<String> versionList = new ArrayList<>();
        final DateTime now = new DateTime();
        final int dayOfWeek = now.getDayOfWeek();
        final DateTime start = now.plusDays(((7 - dayOfWeek) - 2));
        final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        for (int i = 0; i < 12; i++) {
            final DateTime nextWeek = start.plusWeeks(i);
            versionList.add(nextWeek.toString(fmt));
        }
        return versionList;
    }

    public static List<String> createVersions(final DateTime startDate, final DateTime stopDate) {
        final List<String> versionList = new ArrayList<>();
        final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");

        final int dayOfWeek = startDate.getDayOfWeek();
        final DateTime start = startDate.plusDays(((7 - dayOfWeek) - 2));

        DateTime actual = start;
        while (actual.isBefore(stopDate)) {
            final DateTime nextWeek = actual.plusWeeks(1);
            versionList.add(nextWeek.toString(fmt));
            actual = nextWeek;
        }
        return versionList;
    }


    private static DateFormat fullDateFormat = null;
    private static DateFormat plainDateFormat = null;

    /**
     * Format the given date in a good dateTime format: ISO-8601, using the T separator and the - and :
     * seperators accordingly. Example: 2003-01-22T17:00:00.
     *
     * @param date A date instance.
     * @return A String containing the date in ISO-8601 format.
     * @see #string2DateTime(String)
     */
    public static String dateTime2String(final Date date) {
        return getISO8601DateFormat().format(date);
    }

    /**
     * Format the given date in a good date format: ISO-8601, using the - seperators accordingly. Example:
     * 2003-01-22
     *
     * @param date A date instance.
     * @return A String containing the date in ISO-8601 format.
     * @see #string2Date(String)
     */
    public static String date2String(final Date date) {
        return getPlainDateFormat().format(date);
    }

    /**
     * Parses the given string as a Date using the same date format as dateTime2String.
     *
     * @param string A String in ISO-8601 format.
     * @return A Date instance with a timestamp obtained from the specified String.
     * @throws ParseException when the specified string did not conform to the ISO-8601 standard.
     * @see #dateTime2String(Date)
     */
    public static Date string2DateTime(final String string) throws ParseException {
        return getISO8601DateFormat().parse(string);
    }

    /**
     * Parses the given string as a Date using the same date format as date2String.
     *
     * @param string A String in ISO-8601 date format.
     * @return A Date instance with the date obtained from the specified String.
     * @throws ParseException when the specified string did not conform to the ISO-8601 standard.
     * @see #date2String(Date)
     */
    public static Date string2Date(final String string) throws ParseException {
        return getPlainDateFormat().parse(string);
    }

    /**
     * Returns a statically shared DateFormat that uses the ISO-8601 format, which is used by
     * XSD-DATETIME.
     *
     * @return the DateFormat
     */
    public static DateFormat getISO8601DateFormat() {
        if (fullDateFormat == null) {
            fullDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        }
        return fullDateFormat;
    }

    /**
     * Returns a statically shared DateFormat that uses the ISO-8601 format, which is used by
     * XSD-DATE
     *
     * @return the DateFormat
     */
    public static DateFormat getPlainDateFormat() {
        if (plainDateFormat == null) {
            plainDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        return plainDateFormat;
    }

    /**
     * Aktualisiert den Zeitstempel eines Dateinames oder hängt neuen hinten an.
     *
     * @param fileName   Dateiname mit oder ohne Zeitstempel.
     * @param date       Der neue Zeitstempel.
     * @param dateFormat Datumsformat.
     * @return Dateiname mit aktualisiertem Zeitstempel.
     */
    public static String updateTimestamp(final String fileName, final Date date, final DateFormat dateFormat) {
        final String fullPath = FilenameUtils.getFullPath(fileName);
        final String extension = FilenameUtils.getExtension(fileName);
        String baseName = FilenameUtils.getBaseName(fileName);

        final String dateFormated = dateFormat.format(date);
        final int length = baseName.length();
        final int beginIndex = length - dateFormated.length();

        // prüfen, ob der Dateinamen bereits ein Zeitstempel enthält
        boolean replaceTimestamp = false;
        if (beginIndex >= 0) {
            final String oldTimestamp = baseName.substring(beginIndex, length);
            try {
                dateFormat.parse(oldTimestamp);
                // gültiger Zeitstempel => ersetzen
                replaceTimestamp = true;
            } catch (ParseException e) {
                // Text im Dateinamen ist kein gültiger Zeitstempel => anhängen
            }
        } else {
            // kein Zeitstempel im Dateinamen => anhängen
        }
        if (replaceTimestamp) {
            baseName = baseName.substring(0, beginIndex) + dateFormated;
        } else {
            baseName += '_' + dateFormated;
        }
        final String updatedFileName;
        if (extension != null && !extension.isEmpty()) {
            updatedFileName = fullPath + baseName + '.' + extension;
        } else {
            updatedFileName = fullPath + baseName;
        }
        return updatedFileName;
    }

    /**
     * Aktualisiert den Zeitstempel eines Dateinames oder hängt neuen hinten an
     * unter Verwendung des Standard-Datumsformats).
     *
     * @param fileName Dateiname mit oder ohne Zeitstempel.
     * @param date     Der neue Zeitstempel.
     * @return Dateiname mit aktualisiertem Zeitstempel.
     */
    public static String updateTimestamp(final String fileName, final Date date) {
        return updateTimestamp(fileName, date, new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS));
    }

    /**
     * Aktualisiert den Zeitstempel eines Dateinames oder hängt neuen hinten an
     * unter Verwendung der aktuellen Zeit und des Standard-Datumsformats.
     *
     * @param fileName Dateiname mit oder ohne Zeitstempel.
     * @return Dateiname mit aktualisiertem Zeitstempel.
     */
    public static String updateTimestamp(final String fileName) {
        return updateTimestamp(fileName, new Date());
    }
}
