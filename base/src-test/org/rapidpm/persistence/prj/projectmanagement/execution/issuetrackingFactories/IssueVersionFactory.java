package org.rapidpm.persistence.prj.projectmanagement.execution.issuetrackingFactories;

import org.junit.Test;
import org.rapidpm.persistence.EntityFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueVersion;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 06.11.12
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 */
public class IssueVersionFactory extends EntityFactory<IssueVersion> {

    public IssueVersionFactory() {
        super(IssueVersion.class);
    }

    @Override
    public IssueVersion createRandomEntity() {
        return new IssueVersion(RND.nextWord(5, 12));
    }

    @Test
    public void testCreate() throws Exception {
        System.out.println(createRandomEntity());
    }
}
