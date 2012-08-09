package org.rapidpm.orm.prj.projectmanagement.execution.issuetracking;

import org.rapidpm.orm.EntityFactory;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: cernst
 * Date: 12.10.11
 * Time: 13:48
 */
public class IssueTimeUnitFactory extends EntityFactory<IssueTimeUnit> {
    public IssueTimeUnitFactory() {
        super(IssueTimeUnit.class);
    }

    @Override
    public IssueTimeUnit createRandomEntity() {
        final IssueTimeUnit issueTimeUnit = new IssueTimeUnit();
        issueTimeUnit.setComment(RND.nextSentence(6, 20, 5, 12));
        issueTimeUnit.setDatum(RND.nextDate());
        issueTimeUnit.setMinutes(RND.nextInt(5, 320));
//        issueTimeUnit.setWorker(); //TODO Benutzer
        return issueTimeUnit;
    }

    @Test
    public void testCreate() throws Exception {
        System.out.println("Braucht BENUTZER!!");
        System.out.println(createRandomEntity());
    }
}
