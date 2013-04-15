package org.rapidpm.persistence.search;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.rapidpm.Constants;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 15.04.13
 * Time: 13:10
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class HttpSolrServerSingleton {
        private static HttpSolrServer instance = new HttpSolrServer(Constants.SOLR_URL);

        private HttpSolrServerSingleton() {}

        public static HttpSolrServer getInstance() {
            return instance;
        }

}
