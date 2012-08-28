/*
 * Copyright (c) 2011 - RapidPM. All Rights Reserved.
 */

package org.rapidpm.tools;

/**
 * Formatiert eine Speichereinheit in Byte als String-Repräsentation.
 *
 * @author Alexander Vos
 */
public class MemoryFormatter {
    private MemoryFormatter() {
    }

    /**
     * Formatiert eine Speichereinheit in Byte als String-Repräsentation.
     *
     * @param bytes Die zu formatierende Speichereinheit.
     * @return Formatierte Speichereinheit.
     */
    public static String format(final long bytes) {
        if (bytes < 1024L) {
            return String.valueOf(bytes);
        } else if (bytes < 1048576L) {
            return (bytes >> 10) + "K";
        } else if (bytes < 1073741824L) {
            return (bytes >> 20) + "M";
        } else if (bytes < 1099511627776L) {
            return (bytes >> 30) + "G";
        } else {
            return String.valueOf(bytes);
        }
    }
}