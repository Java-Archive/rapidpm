package org.rapidpm.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.rapidpm.persistence.search.HttpSolrServerSingleton;
import org.rapidpm.persistence.search.SearchPattern;
import org.rapidpm.search.NoSearchPatternException.NoSearchPatternException;
import org.rapidpm.search.builder.QueryBuilder;

import static org.junit.Assert.assertTrue;
/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 15.04.13
 * Time: 13:02
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class SearchServiceTest {

    @org.junit.Test
    public void simplePositiveBuilderTest() throws NoSearchPatternException, SolrServerException {
        final String userInput = "Richter";
        final String patternContent = "title:%USERINPUT%";
        final SearchPattern searchPattern = new SearchPattern();
        searchPattern.setContent(patternContent);
        final QueryBuilder builder = new QueryBuilder(userInput, searchPattern);
        final String resultString = builder.getQuery();
        SolrQuery solrQuery = new SolrQuery(resultString);
        final QueryResponse queryResponse = HttpSolrServerSingleton.getInstance().query(solrQuery);
        final SolrDocumentList documentList = queryResponse.getResults();
        for(SolrDocument document : documentList){
            System.out.println(document.toString());
        }
        assertTrue(documentList.size() == 1);
    }


}
