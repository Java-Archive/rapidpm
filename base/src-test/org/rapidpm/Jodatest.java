package org.rapidpm;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 01.09.2010
 *        Time: 09:23:03
 */

public class Jodatest {
    private static final Logger logger = Logger.getLogger(Jodatest.class);


    public static void main(final String[] args) {
        for (int x = 1; x < 35; x++) {

            final DateTime now = new DateTime(2010, 9, x, 0, 0, 0, 0);
//        final DateTime now =  new DateTime();
            final int dayOfWeek = now.getDayOfWeek();
            System.out.println("dayOfWeek = " + dayOfWeek);
            final DateTime start = now.plusDays(((7 - dayOfWeek) - 2));
            final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
            for (int i = 0; i < 12; i++) {
                final DateTime nextWeek = start.plusWeeks(i);
                System.out.println("nextWeekVersion = " + nextWeek.toString(fmt));
            }
        }
    }
}