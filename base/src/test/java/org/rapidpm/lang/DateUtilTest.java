package org.rapidpm.lang;

import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.Constants;
import org.apache.log4j.BasicConfigurator;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * DateUtil Tester.
 *
 * @author Sven Ruppert
 * @version 1.0
 * @since <pre>09/02/2010</pre>
 */
public class DateUtilTest {
    //public DateUtilTest(String name) {

    public DateUtilTest() {
        //super(name); JUnit3
    }

    @Before
    public void setUp() throws Exception {
        //super.setUp(); JUnit3
    }

    @After
    public void tearDown() throws Exception {
        //super.tearDown(); JUnit3
    }

    @Test
    public void testNextVersion() throws Exception {
        final String nextVersion = DateUtil.createNextVersion();
        //assertNotNull(nextVersion);
        System.out.println("nextVersion = " + nextVersion);

        // aNN
    }

    /**
     * Method: createNextVersions()
     */
    @Test
    public void testCreateNextVersions() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: createVersions(final DateTime startDate, final DateTime stopDate)
     */
    @Test
    public void testCreateVersions() throws Exception {
        final DateTime startDate = new DateTime(2010, 8, 31, 0, 0, 0, 0);
        final DateTime stopDate = new DateTime(2010, 9, 30, 0, 0, 0, 0);
        final List<String> versions = DateUtil.createVersions(startDate, stopDate);
        Assert.assertEquals("2010-09-10",versions.get(0));
        Assert.assertEquals("2010-09-17",versions.get(1));
        Assert.assertEquals("2010-09-24",versions.get(2));
        Assert.assertEquals("2010-10-01",versions.get(3));
    }

    /**
     * Method: dateTime2String(Date date)
     */
    @Test
    public void testDateTime2String() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: date2String(Date date)
     */
    @Test
    public void testDate2String() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: string2DateTime(String string)
     */
    @Test
    public void testString2DateTime() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: string2Date(String string)
     */
    @org.junit.Test
    public void testString2Date() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getISO8601DateFormat()
     */
    @org.junit.Test
    public void testGetISO8601DateFormat() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getPlainDateFormat()
     */
    @org.junit.Test
    public void testGetPlainDateFormat() throws Exception {
        //TODO: Test goes here...
    }



    @org.junit.Test
    public void testUpdateTimestamp() throws Exception {
        BasicConfigurator.configure();
        final SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS);
        final String dateStr = "2011-11-11-11-11-11";
        final Date date = dateFormat.parse(dateStr);

        assertEquals("/etc/Export_" + dateStr + ".dat", DateUtil.updateTimestamp("/etc/Export.dat", date));
        assertEquals("/etc/Export_" + dateStr + ".dat", DateUtil.updateTimestamp("/etc/Export_2000-01-01-12-00-00.dat", date));
        assertEquals("Export " + dateStr, DateUtil.updateTimestamp("Export 2000-01-01-12-00-00", date));
        assertEquals("f" + dateStr, DateUtil.updateTimestamp("f2000-01-01-12-00-00", date));
        assertEquals("/etc/Export_20000101120000_" + dateStr + ".dat", DateUtil.updateTimestamp("/etc/Export_20000101120000.dat", date));
    }
}