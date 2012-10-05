/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm;

/**
 * NeoScio
 * @author svenruppert
 * @since 05.08.2008
 * Time: 17:42:55
 * This Source Code is part of the RapidPM - www.rapidpm.org  project.
 * please contact sven.ruppert@web.de
 *
 */

import org.apache.log4j.Logger;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class Constants {
    private static final Logger logger = Logger.getLogger(Constants.class);

    public static final boolean DEBUG = true;


    private static final String dataPath;

    static {
        final File opt_data_mac_sru = new File("/Volumes/RAID_01/opt_data/");
        if (opt_data_mac_sru.exists()) {
            dataPath = opt_data_mac_sru.getAbsolutePath();
        } else {
            final File opt_data = new File("/opt_data/");
            dataPath = opt_data.getAbsolutePath();
        }
//        System.out.println("dataPath = " + dataPath);
    }


    public static final String URL = "url";

    public static final String PATH_SEP = "/";

    public static final String PROTOCOL_IDENTIFIER = "://";
    public static final String[] DEFAULT_PROTOCOLS = {"http", "https", "ftp", "file", "hdfs"};
    //public static final String HTTP = "http://";
    public static final String HTTP = DEFAULT_PROTOCOLS[0] + PROTOCOL_IDENTIFIER;
    public static final String HTTPS = DEFAULT_PROTOCOLS[1] + PROTOCOL_IDENTIFIER;
    public static final String FTP = DEFAULT_PROTOCOLS[2] + PROTOCOL_IDENTIFIER;
    public static final String FILE = DEFAULT_PROTOCOLS[3] + PROTOCOL_IDENTIFIER;
    public static final String HDFS = DEFAULT_PROTOCOLS[4] + PROTOCOL_IDENTIFIER;

    public static final String DATA_BASE_DIR = dataPath;




    public static final String MAIL = "mail";
    public static final String WEB = "web";
    public static final String DOWNLOAD = "download";
    public static final String DOWNLOAD_BASE_DIR = DATA_BASE_DIR + PATH_SEP + DOWNLOAD;

    public static final String MAIL_ATTACHEMENT_FOR = "attachement_for_";
    //    public static final String WARC_GZ = "warc.gz";
    public static final char LINE_BREAK = '\n';
    public static final int MAX_RESULTS = 1000;

    public static final String TRENNZEICHEN = " -#-#-#- ";


    public static final String GESCHLECHT_M = "männlich";
    public static final String GESCHLECHT_W = "weiblich";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH = "yyyy-MM-dd-HH";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd-HH-mm";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd-HH-mm-ss";
    public static final DecimalFormat DECIMALFORMAT_6_STELLEN = new DecimalFormat("######");
    public static final DecimalFormat FLOATFORMAT_3_NACHKOMMA_STELLEN = new DecimalFormat(",###");


    public static final int MAX_RESULT_DOCUMENTS = 100;

    public static final String DATA_DIR = "../data";
    public static final String DATA_CONTENT = DATA_DIR + "/content";

    public static final String PERSISTENCE_UNIT_NAME_TEST = "junitORMJDBC";

//    public static final String HTTP_SOLR_CORE0 = "http://prod.neoscio.de:8983/solr/core0";
//    public static final String HTTP_SOLR_CORE1 = "http://prod.neoscio.de:8983/solr/core1";

    //    public static final String ORM_VIEW_TMP_DIR = "D:\\opt\\jboss-as-7.1.1.Final\\standalone\\tmp";
    public static final String ORM_VIEW_TMP_DIR = "/opt/jboss/7.1.1/standalone/tmp";

    public static final String DECIMAL_FORMAT = "0.00";

    public static final Integer WORKINGHOURS_DAY = 8;
    public static final Integer KONSTANTE = 750;
    public static final String EUR = " \u20AC";
    public static final Integer HOURS_DAY = 24;
    public static final Integer MINS_HOUR = 60;
    public static final Integer MINS_DAY = HOURS_DAY * MINS_HOUR;
    public static final Double STD_ANTEILE = 1.0 / 60.0;
    public static final Integer MONTHS_PER_YEAR = 12;
    public static final Double STD_WORKING_DAYS_PER_MONTH = 20.0;

    public static final String MESSAGESBUNDLE ="MessagesBundle";

    public static final String IMAGE_LOGO = "images/logo_sru.png";

    public static final String IMAGES_DIRECTION = "images/";

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public static final String DAYSHOURSMINUTES_REGEX = "[0-9]{1,}:[012]{1}[0-9]{1}:[012345]{1}[0-9]{1}";

    public static final String DAYSHOURSMINUTES_VALIDATOR_EXCEPTION_MESSAGE = "Format: [d*]d:hh:mm! (hh = 23 max., mm = 59 max.)";
    public static final String COMMIT_EXCEPTION_MESSAGE = "ungueltiger Wert in min. einem Feld.";
}
