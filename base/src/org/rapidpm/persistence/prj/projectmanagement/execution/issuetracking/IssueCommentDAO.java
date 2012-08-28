package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 06.07.11
 * Time: 12:06
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.rapidpm.persistence.BaseDaoFactory;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;

public class IssueCommentDAO extends BaseDaoFactory.BaseDAO<Long, IssueComment> {
    private static final Logger logger = Logger.getLogger(IssueCommentDAO.class);


    public IssueCommentDAO(final EntityManager entityManager) {
        super(entityManager, IssueComment.class);
    }

}
