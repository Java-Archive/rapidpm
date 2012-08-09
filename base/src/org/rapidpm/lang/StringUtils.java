/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.lang;

/**
 * NeoScio
 * User: neoscio
 * Date: 21.09.2009
 * Time: 19:58:58
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@me.com
 */

//import org.apache.log4j.Logger;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Sammlung von String-Methoden die in der Verarbeitung von Webinhalten Verwendung finden.
 */
public class StringUtils {
    // private static final Logger logger = Logger.getLogger(StringUtils.class);
    // public static final Log logger = LogFactory.getLog(StringUtils.class);


    /**
     * Es wird der String bereinigt: alle Zeilenumbrueche weg (Windows Unix)
     * und alle n Leerzeichen auf ein Leerzeichen reduziert.
     * <p/>
     * Die uebergebene Instanz wird nicht veraendert.
     * <p/>
     * Es werden keine Zeichen entfernt die nicht in XML vorhanden sein sollen. Dieses muss
     * mit den XMLUtils gemacht werden.
     *
     * @param content
     * @return neue und bereinigte Insatnz.
     */
    public static String cleanString(final String content) {
        String tmp = "";
        tmp = content.replaceAll("(\\n|\\t|\\r|\\s+)", " ");
//        tmp = content.replaceAll("\n", " ");
//        tmp = tmp.replaceAll("\t", " ");
//        tmp = tmp.replaceAll("\r", " ");
//        tmp = tmp.replaceAll("\\s+", " ");
        //        tmp = org.apache.commons.lang.StringUtils.replace(content, "\n", " ");
        //        tmp = org.apache.commons.lang.StringUtils.replace(content, "\t", " ");
        //        tmp = org.apache.commons.lang.StringUtils.replace(tmp, "\r", " ");
        //        tmp = org.apache.commons.lang.StringUtils.replace(tmp, "\\s+", " ");
        return tmp;
    }

    /**
     * Substitute String "old" by String "new" in String "text" everywhere.
     *
     * @param olds The String to be substituted.
     * @param news The String containing the new content.
     * @param text The String in which the substitution is done.
     * @return The result String containing the substitutions; if no substitutions were made, the
     *         specified 'text' instance is returned.
     * @Deprecated use StringUtils from Commons.lang instead
     */
    public static String replace(final String olds, final String news, final String text) {
        if (olds == null || olds.length() == 0) {
            // nothing to substitute.
            return text;
        }
        if (text == null) {
            return null;
        }

        // search for any occurences of 'olds'.
        int oldsIndex = text.indexOf(olds);
        if (oldsIndex == -1) {
            // Nothing to substitute.
            return text;
        }

        // we're going to do some substitutions.
        final StringBuilder buffer = new StringBuilder(text.length());
        int prevIndex = 0;

        while (oldsIndex >= 0) {
            // first, add the text between the previous and the current occurence
            buffer.append(text.substring(prevIndex, oldsIndex));

            // then add the substition pattern
            buffer.append(news);

            // remember the index for the next loop
            prevIndex = oldsIndex + olds.length();

            // search for the next occurence
            oldsIndex = text.indexOf(olds, prevIndex);
        }

        // add the part after the last occurence
        buffer.append(text.substring(prevIndex));

        return buffer.toString();
    }

    /**
     * Computes the SHA1 hash for the given string.
     * <p/>
     * The code has been 'borrowed' from the mimedir-parser available from
     * http://ilrt.org/discovery/2003/02/cal/mimedir-parser/
     *
     * @param string The string for which we'd like to get the SHA1 hash.
     * @return The generated SHA1 hash
     */
    public static String sha1Hash(final String string) {
        try {
            return sha1Hash(string.getBytes());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Computes the SHA1 hash for the given byte array.
     *
     * @param bytes the byte array
     * @return SHA1 hash for the given byte array
     */
    public static String sha1Hash(final byte[] bytes) {
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(bytes);
            final byte[] digest = md.digest();
            final BigInteger integer = new BigInteger(1, digest);
            return integer.toString(16);
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Character.UnicodeBlock> getUnicodeBlocks(final String txt) {
        final Set<Character.UnicodeBlock> unicodeBlockSet = new HashSet<>();
        final char[] chars = txt.toCharArray();
        for (final char aChar : chars) {
            final Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(aChar);
            unicodeBlockSet.add(unicodeBlock);
        }
        return new ArrayList<>(unicodeBlockSet);
    }

    public static boolean containsISOControlls(final String txt) {
        final char[] chars = txt.toCharArray();
        for (final char aChar : chars) {
            final boolean isoControl = Character.isISOControl(aChar);
            if (isoControl) {
                return isoControl;
            } else {
                //
            }
        }
        return false;
    }

    public static boolean containsOnlyLatinChars(final String txt) {
        final char[] chars = txt.toCharArray();
        for (final char aChar : chars) {
            final boolean isoControl = Character.isISOControl(aChar);
            final Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(aChar);
            if (unicodeBlock != null && !isoControl && (unicodeBlock.equals(Character.UnicodeBlock.BASIC_LATIN) || unicodeBlock.equals(Character.UnicodeBlock.LATIN_EXTENDED_A)
                    //               || unicodeBlock.equals(Character.UnicodeBlock.LATIN_EXTENDED_A)
                    //               || unicodeBlock.equals(Character.UnicodeBlock.LATIN_EXTENDED_B)
                    //               || unicodeBlock.equals(Character.UnicodeBlock.LATIN_EXTENDED_ADDITIONAL)
                    //               &&  unicodeBlock.equals(Character.UnicodeBlock.LATIN_1_SUPPLEMENT)
            )

                    ) {
                //
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Konvertiert den ersten Buchstaben in einen Großbuchstaben.
     *
     * @param str String.
     * @return String mit Großbuchstaben als ersten Buchstaben.
     */
    public static String firstCharUpper(final String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        final char c = str.charAt(0);
        if (Character.isUpperCase(c)) {
            return str;
        }
        return Character.toUpperCase(c) + str.substring(1);
    }

}