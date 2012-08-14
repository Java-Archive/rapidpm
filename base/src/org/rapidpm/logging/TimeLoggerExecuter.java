package org.rapidpm.logging; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 25.08.11
 * Time: 12:00
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.apache.log4j.Logger;

public abstract class TimeLoggerExecuter {
    private static final Logger logger = Logger.getLogger(TimeLoggerExecuter.class);


    public abstract void loggingTimeFrom();

    private long deltaTime = -1;

    private String loggingTimeMsgPrefix;
    private boolean printSOUT;

    public TimeLoggerExecuter(final String loggingTimeMsgPrefix, final Boolean printSOUT) {
        this.loggingTimeMsgPrefix = loggingTimeMsgPrefix;
        this.printSOUT = printSOUT;
    }

    public void execute() {
        final long start = System.nanoTime();

        loggingTimeFrom();

        final long stop = System.nanoTime();
        deltaTime = (stop - start);

        final String message = loggingTimeMsgPrefix + " logged DeltaTime [ns]= " + deltaTime;
        if (logger.isDebugEnabled()) {
            logger.debug(message);
        }

        if (printSOUT) {
            System.out.println(message);
        } else {
            //
        }

    }

    public long getDeltaTime() {
        return deltaTime;
    }
}
