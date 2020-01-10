package org.rapidpm.logging; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 25.08.11
 * Time: 12:00
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */


public abstract class TimeLoggerExecuter {


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
