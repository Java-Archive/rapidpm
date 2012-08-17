/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.tools;/**
 * RapidPM
 * @author svenruppert
 * @since 22.09.2008
 * Time: 17:32:05
 * This Source Code is part of the RapidPM - www.rapidpm.org  project.
 * please contact sven.ruppert@web.de
 *
 */

import org.apache.log4j.Logger;

public class UnicodeFormatter {
    private static final Logger logger = Logger.getLogger(UnicodeFormatter.class);


    public static String byteToHex(final byte b) {
        // Returns hex String representation of byte b
        final char[] hexDigit = {
                '0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        final char[] array = {hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f]};
        return new String(array);
    }

    public static String charToHex(final char c) {
        // Returns hex String representation of char c
        final byte hi = (byte) (c >>> 8);
        final byte lo = (byte) (c & 0xff);
        return byteToHex(hi) + byteToHex(lo);
    }

}