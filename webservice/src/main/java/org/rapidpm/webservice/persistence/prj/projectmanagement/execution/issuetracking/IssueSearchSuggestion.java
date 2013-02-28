package org.rapidpm.webservice.persistence.prj.projectmanagement.execution.issuetracking;

/**
 * Suchvorschläge für die Echtzeitsuche.
 *
 * @see IssueTrackingWS#search(Long, String, int)
 */
public class IssueSearchSuggestion {
    private Long id;
    private String token;
    private String summary;

    public IssueSearchSuggestion() {
    }

    public IssueSearchSuggestion(final Long id, final String token, final String summary) {
        this.id = id;
        this.token = token;
        this.summary = summary;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("IssueSearchSuggestion");
        sb.append("{id=").append(id);
        sb.append(", token='").append(token).append('\'');
        sb.append(", summary='").append(summary).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
