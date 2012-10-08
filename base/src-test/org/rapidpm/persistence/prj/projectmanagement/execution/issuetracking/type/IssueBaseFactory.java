package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type;

import org.rapidpm.persistence.EntityFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: cernst
 * Date: 12.10.11
 * Time: 14:00
 */
public class IssueBaseFactory extends EntityFactory<IssueBase> {
    public IssueBaseFactory() {
        super(IssueBase.class);
    }

    @Override
    public IssueBase createRandomEntity() {
        final IssueBase issueBase = new IssueBase();
//        issueBase.setAssignee(); // TODO Benutzer
        issueBase.setComments(new ArrayList<IssueComment>() {{
            final IssueCommentFactory factory = new IssueCommentFactory();
            add(factory.createRandomEntity());
            add(factory.createRandomEntity());
        }});
        issueBase.setDueDate_closed(RND.nextDate());
        issueBase.setDueDate_planned(RND.nextDate());
        issueBase.setDueDate_resolved(RND.nextDate());
//        issueBase.setEuro(RND.nextInt(500, 10000));
//        issueBase.setFakturierbar(RND.nextBoolean());
        issueBase.setIssuePriority(new IssuePriorityFactory().createRandomEntity());
        issueBase.setIssueStatus(new IssueStatusFactory().createRandomEntity());
        //issueBase.setIssueTimeUnitEstimated(new IssueTimeUnitFactory().createRandomEntity());
        //issueBase.setIssueTimeUnitsUsed(new ArrayList<IssueTimeUnit>() {{
//            final IssueTimeUnitFactory factory = new IssueTimeUnitFactory();
//            add(factory.createRandomEntity());
//            add(factory.createRandomEntity());
//        }});
//        issueBase.setReporter(); // TODO Benutzer
        issueBase.setSummary(RND.nextSentence(5, 20, 5, 12));
        issueBase.setText(RND.nextSentence(8, 25, 5, 12));
        issueBase.setVersion(String.valueOf(RND.nextDigit()));

        return issueBase;
    }

    @Test
    public void testCreate() throws Exception {
        System.out.println(createRandomEntity());
        System.out.println("Braucht BENUTZER!!");
    }
}
