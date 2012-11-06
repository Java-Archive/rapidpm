package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 16.10.12
 * Time: 16:45
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class TestCase {


    @TableGenerator(name = "PKGenTestCase", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "TestCase_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PKGenTestCase")
    @Id
    private Long id;

    @Basic
    private String text;


    public TestCase() {
        //empty on purpose
    }

    public TestCase(final String text) {
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
        return "TestCase{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestCase testCase = (TestCase) o;

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
