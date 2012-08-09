/*
 * Copyright (c) 2009. This is part of the NeoScio Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm;
/**
 * NeoScio
 * @author svenruppert
 * @since 15.01.2009
 * Time: 18:10:20
 * This Source Code is part of the RapidPM - www.rapidpm.org  project.
 * please contact sven.ruppert@web.de
 *
 */

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.Assert.*;

public class RegExTester {
    private static final Logger logger = Logger.getLogger(RegExTester.class);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test001() {
        final String input = "www.un.org\n" +
                "www.heise.de/ \n" +
                "http://www.heise.de/\n" +
                "http://www.a.de\n" +
                "http://www.a.com";
        final Pattern p = Pattern.compile("^((http|dns):\\/\\/|)[^\\/]*\\.de(\\/.*|)");
        final Matcher m = p.matcher(input);

        while (m.find()) {
            System.out.println(input.substring(m.start(), m.end()));
        }
    }

    @Test
    public void test002() {
        final String input = "Das ist das Haus von Nikolaus .. und das ist eine Schnecke";
        final Pattern p = Pattern.compile("das");
        final Matcher m = p.matcher(input);
        while (m.find()) {
            final String foundToken = input.substring(m.start(), m.end());
            assertNotNull(foundToken);
            assertEquals("das", foundToken);
        }
    }

    @Test
    public void test003() {
        final String input = "Das ist das Haus von Nikolaus .. und das ist eine Schnecke";
        final Pattern p = Pattern.compile("(?i)Hau\\S*");
        final Matcher m = p.matcher(input);
        while (m.find()) {
            final String foundToken = input.substring(m.start(), m.end());
            System.out.println("foundToken = " + foundToken);
            assertNotNull(foundToken);
            assertEquals("Haus", foundToken);
        }
    }

    @Test
    public void test004() {
        final String input = "Das ist das Haus von Nikolaus .. und dasX ist eine Schnecke";
        final Pattern p = Pattern.compile("(?i)da\\S*");
        final Matcher m = p.matcher(input);
        boolean foundSomething = false;
        while (m.find()) {
            foundSomething = true;
            final String foundToken = input.substring(m.start(), m.end());
            assertNotNull(foundToken);
            assertTrue(foundToken.toLowerCase().startsWith("da"));
            System.out.println("foundToken = " + foundToken);
        }
        assertTrue(foundSomething);
    }

    @Test
    public void test005() {
        final String input = "Das ist das +Haus von (Nikolaus) .. und dasX ist eine Schnecke";
        final Pattern p = Pattern.compile("[^a-zA-Z0-9\\s]");
        final Matcher m = p.matcher(input);
        boolean foundSomething = false;
        while (m.find()) {
            foundSomething = true;
            final String foundToken = input.substring(m.start(), m.end());
            assertNotNull(foundToken);
            final boolean b = StringUtils.isAlphanumericSpace(foundToken);
            System.out.println("b = " + b);
            System.out.println("foundToken = " + foundToken);
            assertFalse(b);
        }
        assertTrue(foundSomething);
    }

    @Test
    public void test006() {
        final String input = "Das ist das +Haus von (Nikolaus) .. und dasX ist eine Schnecke";
        final String regExPrepare = "[^a-zA-Z0-9\\s]";
        String resultString = input.replaceAll(regExPrepare, " ");

        final Pattern p = Pattern.compile("(?i)da\\S*");
        final Matcher m = p.matcher(input);
        while (m.find()) {
            final String foundToken = input.substring(m.start(), m.end());
            resultString = resultString.replaceAll(foundToken, "b-" + foundToken + "-b");
        }
        System.out.println("resultString = " + resultString);


    }


/*
    public static void main(String[] args) {
        test001();
        test002();
        String str = " Hal[lo D«u Nuss.  (Das isât 'alles";
        final String[] strings = str.split(" ");
        for (String string : strings) {
            //System.out.println(string.replaceAll("\\.", "-"));
            System.out.println("-> " + string.replaceAll("â", "-"));  //â //« //[
        }
        final String s = str.replaceAll("\\s+", "-");
        System.out.println("s = " + s);

    }
*/


}