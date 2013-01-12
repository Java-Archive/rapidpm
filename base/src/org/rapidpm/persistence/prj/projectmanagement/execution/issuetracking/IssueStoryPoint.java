package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

//import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Identifier;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Simple;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.PersistInGraph;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 05.11.12
 * Time: 10:13
 * To change this template use File | Settings | File Templates.
 */
public class IssueStoryPoint implements PersistInGraph {

    @Identifier
    private Long id;

    @Simple
    private Long projectid;

    @Simple
    private Integer storypoint;

    public IssueStoryPoint() {
        //empty on purpose
    }

    public IssueStoryPoint(Integer storypoint) {
        setStorypoint(storypoint);
    }


    public List<IssueBase> getConnectedIssuesFromProject(final Long projectId) {
        return DaoFactorySingelton.getInstance().getIssueStoryPointDAO().getConnectedIssuesFromProject(this, projectId);
    }

    public Long getId() {
        return id;
    }

    private void setId(final Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectid;
    }

    public void setProjectId(final Long projectid) {
        this.projectid = projectid;
    }

    public Integer getStorypoint() {
        return storypoint;
    }

    public void setStorypoint(final Integer storypoint) {
        this.storypoint = storypoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssueStoryPoint that = (IssueStoryPoint) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (storypoint != null ? !storypoint.equals(that.storypoint) : that.storypoint != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (storypoint != null ? storypoint.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IssueStoryPoint{" +
                "id=" + id +
                ", storypoint=" + storypoint +
                '}';
    }

    @Override
    public String name() {
        return getStorypoint().toString();
    }
}
