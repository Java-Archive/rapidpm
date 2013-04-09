package org.rapidpm.search.builder;

import org.rapidpm.search.NoSearchPatternException.NoSearchPatternException;
import org.rapidpm.persistence.search.SearchPattern;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 09.04.13
 * Time: 09:39
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class QueryBuilder {

    private String userInput;
    private SearchPattern searchPattern;
    private String query;

    public QueryBuilder(final String userInput, final SearchPattern searchPattern){
        this.userInput = userInput;
        this.searchPattern = searchPattern;
    }

    public void buildQuery() throws NoSearchPatternException {
        if(userInput == null){
            userInput = new String("");
        }
        if(searchPattern == null){
            throw new NoSearchPatternException();
        } else {
            final String searchPatternContent = searchPattern.getContent();
            query = searchPatternContent.replaceAll("%USERINPUT%", userInput);
        }
    }

    public String getQuery() throws NoSearchPatternException{
        buildQuery();
        return query;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(final String userInput) {
        this.userInput = userInput;
    }

    public SearchPattern getSearchPattern() {
        return searchPattern;
    }

    public void setSearchPattern(final SearchPattern searchPattern) {
        this.searchPattern = searchPattern;
    }
}
