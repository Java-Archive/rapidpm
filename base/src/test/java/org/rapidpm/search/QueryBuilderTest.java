package org.rapidpm.search;

import org.rapidpm.persistence.search.SearchPattern;
import org.rapidpm.search.NoSearchPatternException.NoSearchPatternException;
import org.rapidpm.search.builder.QueryBuilder;

import static org.junit.Assert.assertTrue;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 09.04.13
 * Time: 11:07
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class QueryBuilderTest {

    @org.junit.Test
    public void simplePositiveBuilderTest() throws NoSearchPatternException{
        final String userInput = "300";
        final String patternContent = "title:BMW AND %USERINPUT% AND content:*new*";
        final String expectedResultString = "title:BMW AND 300 AND content:*new*";
        final SearchPattern searchPattern = new SearchPattern();
        searchPattern.setContent(patternContent);
        final QueryBuilder builder = new QueryBuilder(userInput, searchPattern);
        final String resultString = builder.getQuery();
        assertTrue(resultString.equals(expectedResultString));
    }

    @org.junit.Test
    public void throwExceptionBuilderTest(){
        final SearchPattern searchPattern = null;
        final QueryBuilder builder = new QueryBuilder(null, searchPattern);
        try{
            final String resultString = builder.getQuery();
        } catch(final NoSearchPatternException ex){
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }
}
