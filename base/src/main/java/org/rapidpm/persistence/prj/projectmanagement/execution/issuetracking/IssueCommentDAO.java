package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 06.07.11
 * Time: 12:06
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import java.security.InvalidKeyException;

public class IssueCommentDAO extends DAO<Long, IssueComment> {
    private static final Logger logger = Logger.getLogger(IssueCommentDAO.class);


    public IssueCommentDAO(final OrientGraph orientDB) {
        super(orientDB, IssueComment.class);
    }

    @Override
    public IssueComment loadFull(IssueComment entity) throws InvalidKeyException, NotYetImplementedException {
        throw new NotYetImplementedException();
    }
}