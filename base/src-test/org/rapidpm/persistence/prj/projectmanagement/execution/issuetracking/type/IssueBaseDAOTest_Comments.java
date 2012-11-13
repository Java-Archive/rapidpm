package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComment;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 13.11.12
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */
public class IssueBaseDAOTest_Comments {
    private static Logger logger = Logger.getLogger(IssueBaseDAOTest_Comments.class);

    private final Long projectId = 1L;
    private final IssueBaseDAO dao = GraphDaoFactory.getIssueBaseDAO(projectId);



}
