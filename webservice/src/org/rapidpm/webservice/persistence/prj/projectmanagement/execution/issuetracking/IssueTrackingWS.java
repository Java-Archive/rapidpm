package org.rapidpm.webservice.persistence.prj.projectmanagement.execution.issuetracking;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.NotFoundException;
import org.rapidpm.lang.StringUtils;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;
import org.rapidpm.webservice.mapping.EntityMapper;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Alexander Vos
 * Date: 30.11.12
 * Time: 15:14
 */
@WebService(serviceName = "IssueTrackingWS")
public class IssueTrackingWS {
    private static final Logger logger = Logger.getLogger(IssueTrackingWS.class);

    private final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
    private final IssueBaseDAO issueBaseDAO = daoFactory.getIssueBaseDAO();
    private final IssueCommentDAO issueCommentDAO = daoFactory.getIssueCommentDAO();

    private final EntityMapper<IssueBase, FlatIssueBase> issueBaseMapper = new EntityMapper<IssueBase, FlatIssueBase>(IssueBase.class, FlatIssueBase.class) {
        @Override
        protected IssueBase findEntityById(final Long id) {
            return issueBaseDAO.findByID(id);
        }
    };

    private final EntityMapper<IssueComment, FlatIssueComment> issueCommentMapper = new EntityMapper<IssueComment,
            FlatIssueComment>(IssueComment.class, FlatIssueComment.class) {
        @Override
        protected IssueComment findEntityById(final Long id) {
            return issueCommentDAO.findByID(id);
        }
    };

    @WebMethod
    public List<IssuePriority> getAllIssuePriorities(@WebParam(name = "projectId") final Long projectId) {
        // TODO check permission
        final IssuePriorityDAO issuePriorityDAO = daoFactory.getIssuePriorityDAO();
        return issuePriorityDAO.loadAllEntities(projectId);
    }

    @WebMethod
    public List<IssueStatus> getAllIssueStatuses(@WebParam(name = "projectId") final Long projectId) {
        // TODO check permission
        final IssueStatusDAO issueStatusDAO = daoFactory.getIssueStatusDAO();
        return issueStatusDAO.loadAllEntities(projectId);
    }

    @WebMethod
    public List<IssueType> getAllIssueTypes(@WebParam(name = "projectId") final Long projectId) {
        // TODO check permission
        final IssueTypeDAO issueTypeDAO = daoFactory.getIssueTypeDAO();
        return issueTypeDAO.loadAllEntities(projectId);
    }

    @WebMethod
    public List<IssueVersion> getAllIssueVersions(@WebParam(name = "projectId") final Long projectId) {
        // TODO check permission
        final IssueVersionDAO issueVersionDAO = daoFactory.getIssueVersionDAO();
        return issueVersionDAO.loadAllEntities(projectId);
    }

    @WebMethod
    public FlatIssueBase findIssueById(@WebParam(name = "id") final Long id) {
        issueBaseMapper.checkPermission(EntityMapper.PERMISSION_SELECT);
        final IssueBase issueBase = issueBaseDAO.findByID(id);
        return issueBaseMapper.toFlatEntity(issueBase);
    }

    @WebMethod
    public List<FlatIssueBase> findIssuesByIdList(@WebParam(name = "idList") final List<Long> idList) {
        issueBaseMapper.checkPermission(EntityMapper.PERMISSION_SELECT);
        final List<IssueBase> issueBaseList = new ArrayList<>();
        for (final Long id : idList) {
            final IssueBase issueBase = issueBaseDAO.findByID(id);
            issueBaseList.add(issueBase);
        }
        return issueBaseMapper.toFlatEntityList(issueBaseList);
    }

    @WebMethod
    public Object getIssueAttribute(@WebParam(name = "id") final Long id,
                                    @WebParam(name = "name") final String name) {
        issueBaseMapper.checkPermission(EntityMapper.PERMISSION_SELECT);
        final IssueBase issueBase = issueBaseDAO.findByID(id);
        if (issueBase != null) {
            final String getterName = "get" + StringUtils.firstCharUpper(name);
            final Method getter;
            try {
                getter = IssueBase.class.getMethod(getterName);
            } catch (NoSuchMethodException e) {
                logger.error("getter '" + getterName + "' not found", e);
                return null;
            }
            try {
                return getter.invoke(issueBase);
            } catch (IllegalAccessException | InvocationTargetException e) {
                logger.error("getter '" + getterName + "' could not be invoked", e);
            }
        }
        return null;
    }

    @WebMethod
    public Long saveIssue(@WebParam(name = "issue") final FlatIssueBase flatIssue) {
        issueBaseMapper.checkPermission(EntityMapper.PERMISSION_UPDATE);
        if (flatIssue != null) {
            final IssueBase issueBase = issueBaseMapper.toEntity(flatIssue);
            issueBaseDAO.persist(issueBase);
            return issueBase.getId();
        }
        return null;
    }

    @WebMethod
    public boolean deleteIssue(@WebParam(name = "id") final Long id) {
        issueBaseMapper.checkPermission(EntityMapper.PERMISSION_DELETE);
        final IssueBase issueBase;
        try {
            issueBase = issueBaseDAO.findByID(id);
        } catch (NotFoundException e) {
            return false;
        }
        return issueBaseDAO.delete(issueBase);
    }

    @WebMethod
    public List<FlatIssueBase> getSubIssues(@WebParam(name = "issueId") final Long issueId) {
        issueBaseMapper.checkPermission(EntityMapper.PERMISSION_SELECT);
        final IssueBase issueBase = issueBaseDAO.findByID(issueId);
        final List<IssueBase> subIssues = issueBaseDAO.getSubIssuesOf(issueBase);
        return issueBaseMapper.toFlatEntityList(subIssues);
    }

    @WebMethod
    public Long addSubIssue(@WebParam(name = "issueId") final Long issueId,
                            @WebParam(name = "subIssue") final FlatIssueBase flatIssue) {
        issueBaseMapper.checkPermission(EntityMapper.PERMISSION_UPDATE);
        final IssueBase issue = issueBaseDAO.findByID(issueId);
        final IssueBase subIssue = issueBaseMapper.toEntity(flatIssue);
        if (issue != null && subIssue != null) {
            subIssue.setProjectId(issue.getProjectId()); // erforderlich oder übernimmt die DAO das?
            issueBaseDAO.persist(subIssue);
            if (issue.addSubIssue(subIssue)) {
                issueBaseDAO.persist(issue);
                return subIssue.getId();
            }
        }
        return null;
    }

    @WebMethod
    public List<FlatIssueBase> getTopLevelIssues(@WebParam(name = "projectId") final Long projectId) {
        issueBaseMapper.checkPermission(EntityMapper.PERMISSION_SELECT);
        final List<IssueBase> issueBaseList = issueBaseDAO.loadTopLevelEntities(projectId);
        return issueBaseMapper.toFlatEntityList(issueBaseList);
    }

    @WebMethod
    public Long addOrChangeComment(@WebParam(name = "issueId") final Long issueId,
                                   @WebParam(name = "comment") final FlatIssueComment flatComment) {
        issueCommentMapper.checkPermission(EntityMapper.PERMISSION_UPDATE);
        final IssueBase issueBase = issueBaseDAO.findByID(issueId);
        if (issueBase != null && flatComment != null) {
            final IssueComment comment = issueCommentMapper.toEntity(flatComment);
            if (issueBase.addOrChangeComment(comment)) {
                issueBaseDAO.persist(issueBase);
                return comment.getId();
            }
        }
        return null;
    }

    @WebMethod
    public boolean deleteComment(@WebParam(name = "issueId") final Long issueId,
                                 @WebParam(name = "commentId") final Long commentId) {
        issueCommentMapper.checkPermission(EntityMapper.PERMISSION_DELETE);
        final IssueBase issueBase;
        try {
            issueBase = issueBaseDAO.findByID(issueId);
        } catch (NotFoundException e) {
            return false;
        }
        final IssueComment comment;
        try {
            comment = issueCommentDAO.findByID(commentId);
        } catch (NotFoundException e) {
            return false;
        }
        if (issueBase.removeComment(comment)) {
            issueBaseDAO.persist(issueBase);
            return true;
        }
        return false;
    }
}
