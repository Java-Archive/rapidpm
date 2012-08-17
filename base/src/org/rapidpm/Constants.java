/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@rapidpm.org
 */

package org.rapidpm;

/**
 * RapidPM
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


    /**
     * For Development
     */
    public static final boolean NO_INTERNET = true;
    public static final String TAPESTRY_PRODUCTION_MODE = "false";

    public static final int TITEL_LENGTH = 80;
    public static final String TITLE = "title";
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
    //public static final String FTP = "ftp://";
    //public static final String FILE = "file://";
    //public static final String HDFS = "hdfs://";

    public static final String DEFAULT_CHARSET = "ISO-8859-1";

    public static final String URL_BLACKLIST_TXT = "url-blacklist.txt";

    //    public static final String DATA_BASE_DIR = '.' + PATH_SEP + "data";
    public static final String DATA_BASE_DIR = dataPath;


    public static final String DATA_WEBAPP_DB = DATA_BASE_DIR + PATH_SEP + "testdb.db";


    public static final String CONF_BASE_DIR = '.' + PATH_SEP + "conf";

    /**
     * Hier werden die Konstanten die im Index als Attributfeld verwendet werden definiert.
     */
    public static final String IDX_CHARSET = "charset";
    public static final String IDX_CLASSIFIED = "classified";
    public static final String IDX_CLUSTER = "cluster";
    public static final String IDX_COMPANY = "company";
    //    public static final String IDX_COMPANY_ID = "companyID";
    public static final String IDX_CONTENT = "content";
    public static final String IDX_CONTENT_PACKED = "content_packed";
    public static final String IDX_DOMAIN = "domain";
    public static final String IDX_HPTDOMAIN = "hptdomain";
    public static final String IDX_DATE = "date";
    public static final String IDX_INDEXED = "indexed";
    public static final String IDX_MODIFIED = "modified";
    public static final String IDX_SOURCE = "source";
    public static final String IDX_URL = "url";
    public static final String IDX_KEYWORDS = "keywords";
    public static final String IDX_BOOST = "boost";
    public static final String IDX_USER = "user";
    public static final String IDX_MANDANTEGRUPPE = "mandantengruppe";
    public static final String IDX_SUCHMODUL = "suchmodul";
    public static final String IDX_SUCHMODULFILTER = "suchmodulfilter";
    public static final String IDX_SUCHMODULFILTEROIDS = "filterOIDS";
    public static final String IDX_SUBJECT = "subject";
    public static final String IDX_SCORE = "score";
    public static final String IDX_BERECHTIGUNG = "berechtigung";
    public static final String IDX_TITLE = "title";
    public static final String IDX_LINK = "link";
    public static final String IDX_LINKS = "links";
    public static final String IDX_OID = "oid";
    public static final String IDX_ID = "id";
    public static final String IDX_IDXVERSION = "idxversion";
    public static final String IDX_DESCRIPTION = "description";
    public static final String IDX_AUTHOR = "author";
    public static final String IDX_ROBOTS = "robots";
    public static final String IDX_GEO_REGION = "geo.region";
    public static final String IDX_GEO_PLACENAME = "geo.placename";
    public static final String IDX_GEO_POSITION = "geo.position";
    public static final String IDX_GEO_LAT = "geo:lat";
    public static final String IDX_GOOGLE_SITE_VERIFICATION = "google-site-verification";
    public static final String IDX_PAGE_TOPIC = "page-topic";
    public static final String IDX_PAGE_TYPE = "page-type";
    public static final String IDX_PAGE_TITLE = "page-title";
    public static final String IDX_PUBLISHER = "publisher";
    public static final String IDX_WARC_IP_ADRESS = "warc-ip-address";
    public static final String IDX_REVISIT_AFTER = "revisit-after";
    public static final String IDX_VERSION = "idxVersion";


    public static final String INDEX = "index";


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

    public static final int MAX_DATAVOLUME_FOR_READING = 20 * 1024 * 1024;

    public static final String HTTP_SOLR_CORE0 = "http://prod.RapidPM.de:8983/solr/core0";
    public static final String HTTP_SOLR_CORE1 = "http://prod.RapidPM.de:8983/solr/core1";

    //    public static final String ORM_VIEW_TMP_DIR = "D:\\opt\\jboss-as-7.1.1.Final\\standalone\\tmp";
    public static final String ORM_VIEW_TMP_DIR = "/opt/jboss/7.1.1/standalone/tmp";

}
