package org.rapidpm.persistence.prj.projectmanagement.execution.issuetrackingFactories;

import org.rapidpm.persistence.EntityFactory;
import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatus;

/**
 * Created by IntelliJ IDEA.
 * User: cernst
 * Date: 12.10.11
 * Time: 13:45
 */
public class IssueStatusFactory extends EntityFactory<IssueStatus> {
    public IssueStatusFactory() {
        super(IssueStatus.class);
    }

    @Override
    public IssueStatus createRandomEntity() {
        return new IssueStatus(RND.nextWord(5, 12));
    }

    @Test
    public void testCreate() throws Exception {
        System.out.println(createRandomEntity());
    }
}
