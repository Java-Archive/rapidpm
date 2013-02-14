package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.rapidpm.persistence.DaoFactorySingelton;
//import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Identifier;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.NonVisible;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Simple;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.PersistInGraph;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 18.10.12
 * Time: 09:54
 * To change this template use File | Settings | File Templates.
 */
public class IssueComponent implements PersistInGraph {

    @Identifier
    private Long id;

    @Simple
    @NonVisible
    private Long projectid;

    @Simple
    private String componentName;

    public IssueComponent() {
        //empty on purpose
    }

    public IssueComponent(final Long projectid) {
        setProjectId(projectid);
    }

    public IssueComponent(final String componentName) {
        this.componentName = componentName;
    }


    public List<IssueBase> getConnectedIssues() {
        return DaoFactorySingelton.getInstance().getIssueComponentDAO().getConnectedIssues(this);
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

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(final String componentName) {
        this.componentName = componentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssueComponent that = (IssueComponent) o;

        if (componentName != null ? !componentName.equals(that.componentName) : that.componentName != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (projectid != null ? !projectid.equals(that.projectid) : that.projectid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (projectid != null ? projectid.hashCode() : 0);
        result = 31 * result + (componentName != null ? componentName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IssueComponent{" +
                "id=" + id +
                ", projectid=" + projectid +
                ", componentName='" + componentName + '\'' +
                '}';
    }

    @Override
    public String name() {
        return getComponentName();
    }
}
