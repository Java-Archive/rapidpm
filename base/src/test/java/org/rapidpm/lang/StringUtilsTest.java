package org.rapidpm.lang;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * StringUtils Tester.
 *
 * @author Sven Ruppert
 * @version 1.0
 * @since <pre>07/14/2010</pre>
 */
public class StringUtilsTest {
    //public StringUtilsTest(String name) {

    public StringUtilsTest() {
        //super(name); JUnit3
    }

    @BeforeEach
    public void setUp() throws Exception {
        //super.setUp(); JUnit3
    }

    @AfterEach
    public void tearDown() throws Exception {
        //super.tearDown(); JUnit3
    }

    /**
     * Method: cleanString(final String content)
     */
    @Test
    public void testCleanString() throws Exception {
        final
        String
                content =
                "\n" + "watch.ing: solar module testers,  solar module testers,\n"
                        + "toy inspectors and IT specialists  inspectors and IT specialists\n"
                        + "show their stuff their stuff\n" + "contact\n"
                        + "C U S T O M E R  M A G A Z I N E  O F  T H E  T Ü V  R H E I N L A N D  G R O U PCUSTOMER MAGAZINE OF THE TÜV RHEINLAND GROUP\n"
                        + "ABOVE THE CLOUDS THE CLOUDS\n" + "Climate protection at  protection at\n" + "Cargolux\n"
                        + "ON THE ROAD THE ROAD \n \t \t \t \t \t                                                    \t"
                        + "100 years of driver’s  years of driver’\n" + "licenses\n" + "I S S U E  2 . 0 9ISSUE 2.09\n" + "02\n"
                        + "Trends & Innovation04 14 20Markets & ExpertiseTechnology & Safety\n" + "Focus: Subway Construction";

        final StringUtils su = new StringUtils();
        final String s = su.cleanString(content);
        int i = s.split("\n").length;
        assertEquals(1, i);
        i = s.split("\r").length;
        assertEquals(1, i);

    }

}
