package org.rapidpm.search.model;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 09.04.13
 * Time: 08:35
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class QueryResult {

    private String title;
    private String snippet;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(final String snippet) {
        this.snippet = snippet;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }
}
