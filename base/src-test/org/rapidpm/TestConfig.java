package org.rapidpm;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 29.09.11
 * Time: 12:04
 */
public class TestConfig {
    private static boolean autoCleanUp = true;

    public static boolean isAutoCleanUp() {
        return autoCleanUp;
    }

    public static void setAutoCleanUp(final boolean autoCleanUp) {
        TestConfig.autoCleanUp = autoCleanUp;
    }
}