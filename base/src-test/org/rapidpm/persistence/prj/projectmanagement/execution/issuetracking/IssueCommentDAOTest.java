package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.hibernate.type.ListType;
import org.junit.Test;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.execution.BaseDAOTest;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 15.11.12
 * Time: 10:37
 * To change this template use File | Settings | File Templates.
 */
public class IssueCommentDAOTest implements BaseDAOTest {

    private final IssueCommentDAO dao = daoFactory.getIssueCommentDAO();

    @Test
    public void addChangeDelete() {
        final IssueComment comment = new IssueComment();
        comment.setText("test");
        comment.setCreated(new Date());
        comment.setCreator(daoFactory.getBenutzerDAO().loadAllEntities().get(0));
        daoFactory.saveOrUpdateTX(comment);
        assertTrue(comment.getId() != null);
        assertEquals(comment, dao.findByID(comment.getId()));

        comment.setText("second_test");
        comment.setCreated(new Date());
        comment.setCreator(daoFactory.getBenutzerDAO().loadAllEntities().get(1));
        daoFactory.saveOrUpdateTX(comment);
        assertTrue(comment.getId() != null);
        assertEquals(comment, dao.findByID(comment.getId()));


        daoFactory.removeTX(comment);
        assertFalse(dao.loadAllEntities().contains(comment));
    }
}
