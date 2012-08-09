/*
 * Copyright (c) 2006 - 2008 Aduna.
 * All rights reserved.
 * 
 * Licensed under the Open Software License version 3.0.
 */
package org.rapidpm.lang;

import java.util.Arrays;

/**
 * Utility methods and classes for handling UTF (Unicode Transformation Format) streams.
 */
public class UtfUtil {

    // UTF Byte Order Marks (see http://www.unicode.org/faq/utf_bom.html#BOM):
    //
    // 00 00 FE FF: UTF-32, big-endian
    // FF FE 00 00: UTF-32, little-endian
    // FE FF: UTF-16, big-endian
    // FF FE UTF-16, little-endian
    // EF BB BF: UTF-8

    public static final byte[] UTF8_BOM = {(byte) 0xef, (byte) 0xbb, (byte) 0xbf};

    public static final byte[] UTF16BE_BOM = {(byte) 0xfe, (byte) 0xff};

    public static final byte[] UTF16LE_BOM = {(byte) 0xff, (byte) 0xfe};

    public static final byte[] UTF32BE_BOM = {(byte) 0x00, (byte) 0x00, (byte) 0xfe, (byte) 0xff};

    public static final byte[] UTF32LE_BOM = {(byte) 0xff, (byte) 0xfe, (byte) 0x00, (byte) 0x00};

    public static final int MAX_BOM_LENGTH = 4;

    /**
     * Determines whether the specified byte array starts with a UTF Byte Order Mark and, if so, returns the
     * matching BOM constant.
     *
     * @param bytes The byte array that is tested for the presence of a UTF Byte Order Mark.
     * @return The Byte Order Mark found at the start of the array (one of the constants in this class) or
     *         'null' when a Byte Order Mark could not be found.
     */
    public static byte[] findMatchingBOM(final byte[] bytes) {
        if (startsWith(bytes, UTF8_BOM)) {
            return UTF8_BOM;
        } else if (startsWith(bytes, UTF16BE_BOM)) {
            return UTF16BE_BOM;
        } else if (startsWith(bytes, UTF16LE_BOM)) {
            return UTF16LE_BOM;
        } else if (startsWith(bytes, UTF32BE_BOM)) {
            return UTF32BE_BOM;
        } else if (startsWith(bytes, UTF32LE_BOM)) {
            return UTF32LE_BOM;
        } else {
            return null;
        }
    }

    /**
     * Determines whether the specified byte array starts with the specific bytes.
     *
     * @param bytes      The array whose start is tested.
     * @param startBytes The byte array whose presence at the start of the array is tested.
     * @return 'true' when the array starts with the specified start bytes, 'false' otherwise.
     */
    private static boolean startsWith(final byte[] array, final byte[] startBytes) {
        if (array == null || startBytes == null || array.length < startBytes.length) {
            return false;
        }

        for (int i = 0; i < startBytes.length; i++) {
            if (array[i] != startBytes[i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the canonical charset name consistent with the specified Byte Order Mark.
     *
     * @param byteOrderMark A Unicode Byte Order Mark.
     * @return A canonical charset name, or null when the Byte Order Mark was not recognized.
     */
    public static String getCharsetName(final byte[] byteOrderMark) {
        if (Arrays.equals(byteOrderMark, UTF8_BOM)) {
            return "UTF-8";
        } else if (Arrays.equals(byteOrderMark, UTF16BE_BOM)) {
            return "UTF-16BE";
        } else if (Arrays.equals(byteOrderMark, UTF16LE_BOM)) {
            return "UTF-16LE";
        } else if (Arrays.equals(byteOrderMark, UTF32BE_BOM)) {
            // not seen on Windows platform, maybe it exists on UNIX?
            return "UTF-32BE";
        } else if (Arrays.equals(byteOrderMark, UTF32LE_BOM)) {
            // not seen on Windows platform, maybe it exists on UNIX?
            return "UTF-32LE";
        } else {
            return null;
        }
    }
}
