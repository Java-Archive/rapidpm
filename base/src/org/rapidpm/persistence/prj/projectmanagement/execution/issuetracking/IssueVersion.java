package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

//import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Identifier;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.NonVisible;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Simple;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.PersistInGraph;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 05.11.12
 * Time: 10:14
 * To change this template use File | Settings | File Templates.
 */
public class IssueVersion implements PersistInGraph{

    @Identifier
    private Long id;

    @Simple
    @NonVisible
    private Long projectid;

    @Simple
    private String versionName;

    public IssueVersion() {
        //empty on purpose
    }

    public IssueVersion(final String versionName) {
        setVersionName(versionName);
    }

    public IssueVersion(final Long projectid) {
        setProjectId(projectid);
    }

    public List<IssueBase> getConnectedIssuesFromProject(final Long projectId) {
        return DaoFactorySingelton.getInstance().getIssueVersionDAO().getConnectedIssuesFromProject(this, projectId);
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

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(final String versionName) {
        this.versionName = versionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssueVersion that = (IssueVersion) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (projectid != null ? !projectid.equals(that.projectid) : that.projectid != null) return false;
        if (versionName != null ? !versionName.equals(that.versionName) : that.versionName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (projectid != null ? projectid.hashCode() : 0);
        result = 31 * result + (versionName != null ? versionName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IssueVersion{" +
                "id=" + id +
                ", projectid=" + projectid +
                ", versionName='" + versionName + '\'' +
                '}';
    }

    @Override
    public String name() {
        return getVersionName();
    }
}
