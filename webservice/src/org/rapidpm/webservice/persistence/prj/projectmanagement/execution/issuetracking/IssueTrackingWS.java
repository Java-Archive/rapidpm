package org.rapidpm.webservice.persistence.prj.projectmanagement.execution.issuetracking;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;
import org.rapidpm.webservice.mapping.EntityMapper;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
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

    private final EntityMapper<IssueBase, FlatIssueBase> issueBaseMapper = new EntityMapper<IssueBase, FlatIssueBase>(IssueBase.class, FlatIssueBase.class) {
        @Override
        protected IssueBase findEntityById(final Long id) {
            return issueBaseDAO.findByID(id);
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
    public void saveIssue(@WebParam(name = "issueBase") final FlatIssueBase flatIssueBase) {
        issueBaseMapper.checkPermission(EntityMapper.PERMISSION_UPDATE);
        final IssueBase issueBase = issueBaseMapper.toEntity(flatIssueBase);
        issueBaseDAO.persist(issueBase);
    }

    @WebMethod
    public void deleteIssue(@WebParam(name = "id") final Long id) {
        issueBaseMapper.checkPermission(EntityMapper.PERMISSION_DELETE);
        final IssueBase issueBase = issueBaseDAO.findByID(id);
        issueBaseDAO.delete(issueBase);
    }

    @WebMethod
    public List<FlatIssueBase> getSubIssues(@WebParam(name = "issueId") final Long issueId) {
        issueBaseMapper.checkPermission(EntityMapper.PERMISSION_SELECT);
        final IssueBase issueBase = issueBaseDAO.findByID(issueId);
        final List<IssueBase> subIssues = issueBaseDAO.getSubIssuesOf(issueBase);
        return issueBaseMapper.toFlatEntityList(subIssues);
    }

    @WebMethod
    public List<FlatIssueBase> getTopLevelIssues(@WebParam(name = "projectId") final Long projectId) {
        issueBaseMapper.checkPermission(EntityMapper.PERMISSION_SELECT);
        final List<IssueBase> issueBaseList = issueBaseDAO.loadTopLevelEntities(projectId);
        return issueBaseMapper.toFlatEntityList(issueBaseList);
    }
}
