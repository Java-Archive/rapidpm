package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 18.10.12
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */
public class IssueTestCaseDAO extends DAO<Long, IssueTestCase> {
    private static final Logger logger = Logger.getLogger(IssueTestCaseDAO.class);

    public IssueTestCaseDAO(final EntityManager entityManager) {
        super(entityManager, IssueTestCase.class);
    }
}
