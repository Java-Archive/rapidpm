package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 12.10.12
 * Time: 08:22
 * To change this template use File | Settings | File Templates.
 */
public class IssueType {

    public static final String NAME = "typeName";
    public static final String ID = "id";

    @Id
    @TableGenerator(name = "PKGenIssueType", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "IssueType_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenIssueType")
    private Long id;

    private String typeName;

    public IssueType(final String typeName) {
        this.typeName = typeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "IssueType{" +
                "typeName='" + typeName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssueType issueType = (IssueType) o;

        if (typeName != null ? !typeName.equals(issueType.typeName) : issueType.typeName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return typeName != null ? typeName.hashCode() : 0;
    }
}
