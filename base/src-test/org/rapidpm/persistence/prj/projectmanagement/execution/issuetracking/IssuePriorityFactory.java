package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.rapidpm.persistence.EntityFactory;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: cernst
 * Date: 12.10.11
 * Time: 13:39
 */
public class IssuePriorityFactory extends EntityFactory<IssuePriority> {
    public IssuePriorityFactory() {
        super(IssuePriority.class);
    }

    @Override
    public IssuePriority createRandomEntity() {
        return new IssuePriority(RND.nextInt(1, 10), RND.nextWord(5, 12));
    }

    @Test
    public void testCreate() throws Exception {
        System.out.println(createRandomEntity());
    }
}
