package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Identifier;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Simple;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.PersistInGraph;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 18.10.12
 * Time: 09:54
 * To change this template use File | Settings | File Templates.
 */
public class IssueComponent implements PersistInGraph {

    @Identifier
    private Long id;

    @Simple
    private String componentName;

    public IssueComponent() {
        //empty on purpose
    }

    public IssueComponent(final String componentName) {
        this.componentName = componentName;
    }

    public List<IssueBase> getConnectedIssues() {
        return GraphDaoFactory.getIssueComponentDAO().getConnectedIssues(this);
    }

    public List<IssueBase> getConnectedIssuesFromProject(final Long projectId) {
        return GraphDaoFactory.getIssueComponentDAO().getConnectedIssuesFromProject(this, projectId);
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(final String componentName) {
        this.componentName = componentName;
    }

    @Override
    public String toString() {
        return "IssueComponent{" +
                "id=" + id +
                ", componentName='" + componentName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssueComponent component = (IssueComponent) o;

        if (componentName != null ? !componentName.equals(component.componentName) : component.componentName != null)
            return false;
        if (id != null ? !id.equals(component.id) : component.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (componentName != null ? componentName.hashCode() : 0);
        return result;
    }

    @Override
    public String name() {
        return getComponentName();
    }
}
