package org.rapidpm.persistence;

import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriority;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatus;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueType;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 18.10.12
 * Time: 13:44
 * To change this template use File | Settings | File Templates.
 */
public class InitializeDB {
    @Test
    public void createValues(){
        IssueStatus status = new IssueStatus();
        status.setStatusName("open");
        GraphDaoFactory.getInstance().getIssueStatusDAO().persist(status);

        status = new IssueStatus();
        status.setStatusName("closed");
        GraphDaoFactory.getInstance().getIssueStatusDAO().persist(status);

        status = new IssueStatus();
        status.setStatusName("resolved");
        GraphDaoFactory.getInstance().getIssueStatusDAO().persist(status);

        status = new IssueStatus();
        status.setStatusName("onhold");
        GraphDaoFactory.getInstance().getIssueStatusDAO().persist(status);

        status = new IssueStatus();
        status.setStatusName("inprogress");
        GraphDaoFactory.getInstance().getIssueStatusDAO().persist(status);


        IssuePriority priority = new IssuePriority();
        priority.setPriorityName("trivial");
        GraphDaoFactory.getInstance().getIssuePriorityDAO().persist(priority);

        priority = new IssuePriority();
        priority.setPriorityName("minor");
        GraphDaoFactory.getInstance().getIssuePriorityDAO().persist(priority);

        priority = new IssuePriority();
        priority.setPriorityName("major");
        GraphDaoFactory.getInstance().getIssuePriorityDAO().persist(priority);

        priority = new IssuePriority();
        priority.setPriorityName("critical");
        GraphDaoFactory.getInstance().getIssuePriorityDAO().persist(priority);

        priority = new IssuePriority();
        priority.setPriorityName("blocker");
        GraphDaoFactory.getInstance().getIssuePriorityDAO().persist(priority);


        IssueType type = new IssueType();
        type.setTypeName("bug");
        GraphDaoFactory.getInstance().getIssueTypeDAO().persist(type);

        type = new IssueType();
        type.setTypeName("task");
        GraphDaoFactory.getInstance().getIssueTypeDAO().persist(type);

        type = new IssueType();
        type.setTypeName("improvement");
        GraphDaoFactory.getInstance().getIssueTypeDAO().persist(type);

        type = new IssueType();
        type.setTypeName("newfunction");
        GraphDaoFactory.getInstance().getIssueTypeDAO().persist(type);
    }
}
