package org.rapidpm.persistence.prj.projectmanagement.execution.issuetrackingFactories;

import org.junit.Test;
import org.rapidpm.persistence.EntityFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueType;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 12.10.12
 * Time: 08:54
 * To change this template use File | Settings | File Templates.
 */
public class IssueTypeFactory  extends EntityFactory<IssueType> {

    public IssueTypeFactory() {
        super(IssueType.class);
    }

    @Override
    public IssueType createRandomEntity() {
        return new IssueType(RND.nextWord(5, 12));
    }

    @Test
    public void testCreate() throws Exception {
        System.out.println(createRandomEntity());
    }
}
