package org.rapidpm.orm.prj.projectmanagement.execution.issuetracking;

import org.rapidpm.orm.EntityFactory;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: cernst
 * Date: 12.10.11
 * Time: 13:56
 */
public class IssueCommentFactory extends EntityFactory<IssueComment> {
    public IssueCommentFactory() {
        super(IssueComment.class);
    }

    @Override
    public IssueComment createRandomEntity() {
        final IssueComment issueComment = new IssueComment();
        issueComment.setCreated(RND.nextDate());
//        issueComment.setCreator(); // TODO Benutzer
        issueComment.setTxt(RND.nextSentence(5, 20, 5, 12));
        return issueComment;
    }

    @Test
    public void testCreate() throws Exception {
        System.out.println(createRandomEntity());
        System.out.println("Braucht BENUTZER!!");
    }
}
