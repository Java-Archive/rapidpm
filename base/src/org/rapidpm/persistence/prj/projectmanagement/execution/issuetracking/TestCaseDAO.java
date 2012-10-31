package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 18.10.12
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */
public class TestCaseDAO extends DAO<Long, TestCase> {
    private static final Logger logger = Logger.getLogger(TestCaseDAO.class);

    public TestCaseDAO(final EntityManager entityManager) {
        super(entityManager, TestCase.class);
    }
}
