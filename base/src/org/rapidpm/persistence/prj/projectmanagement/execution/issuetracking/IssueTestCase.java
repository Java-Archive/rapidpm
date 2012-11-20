package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 16.10.12
 * Time: 16:45
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class IssueTestCase {


    @TableGenerator(name = "PKGenIssueTestCase", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "IssueTestCase_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PKGenIssueTestCase")
    @Id
    private Long id;

    @Basic
    private String text;


    public IssueTestCase() {
        //empty on purpose
    }

    public IssueTestCase(final String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }


    @Override
    public String toString() {
        return "IssueTestCase{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssueTestCase testCase = (IssueTestCase) o;

        if (id != null ? !id.equals(testCase.id) : testCase.id != null) return false;
        if (text != null ? !text.equals(testCase.text) : testCase.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }
}
