package org.rapidpm.webservice.persistence.prj.projectmanagement.execution.issuetracking;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;
import org.rapidpm.webservice.mapping.EntityMapper;
import org.rapidpm.webservice.persistence.prj.projectmanagement.execution.issuetracking.type.FlatIssueBase;

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
    private IssueBaseDAO issueBaseDAO;

    private final EntityMapper<IssueBase, FlatIssueBase> issueBaseMapper = new EntityMapper<IssueBase, FlatIssueBase>(IssueBase.class, FlatIssueBase.class) {
        @Override
        protected IssueBase findEntityById(final Long id) {
            return issueBaseDAO.findByID(id);
        }
    };

    @WebMethod
    public FlatIssueBase findIssueById(@WebParam(name = "projectId") final Long projectId,
                                       @WebParam(name = "id") final Long id) {
        issueBaseMapper.checkPermission(EntityMapper.PERMISSION_SELECT);
        issueBaseDAO = daoFactory.getIssueBaseDAO(projectId);
        final IssueBase issueBase = issueBaseDAO.findByID(id);
        return issueBaseMapper.toFlatEntity(issueBase);
    }

    @WebMethod
    public List<FlatIssueBase> findIssuesByIdList(@WebParam(name = "projectId") final Long projectId,
                                                  @WebParam(name = "idList") final List<Long> idList) {
        issueBaseMapper.checkPermission(EntityMapper.PERMISSION_SELECT);
        issueBaseDAO = daoFactory.getIssueBaseDAO(projectId);
        final List<IssueBase> issueBaseList = new ArrayList<>();
        for (final Long id : idList) {
            final IssueBase issueBase = issueBaseDAO.findByID(id);
            issueBaseList.add(issueBase);
        }
        return issueBaseMapper.toFlatEntityList(issueBaseList);
    }

    @WebMethod
    public void saveIssue(@WebParam(name = "entity") final FlatIssueBase flatIssueBase) {
        issueBaseMapper.checkPermission(EntityMapper.PERMISSION_UPDATE);
        final Long projectId = flatIssueBase.getProjectId();
        issueBaseDAO = daoFactory.getIssueBaseDAO(projectId);
        final IssueBase issueBase = issueBaseMapper.toEntity(flatIssueBase);
        issueBaseDAO.persist(issueBase);
    }

    @WebMethod
    public void deleteIssue(@WebParam(name = "projectId") final Long projectId,
                            @WebParam(name = "id") final Long id) {
        issueBaseMapper.checkPermission(EntityMapper.PERMISSION_DELETE);
        issueBaseDAO = daoFactory.getIssueBaseDAO(projectId);
        final IssueBase issueBase = issueBaseDAO.findByID(id);
        issueBaseDAO.delete(issueBase);
    }

    @WebMethod
    public List<FlatIssueBase> getSubIssues(@WebParam(name = "projectId") final Long projectId,
                                            @WebParam(name = "issueId") final Long issueId) {
        issueBaseMapper.checkPermission(EntityMapper.PERMISSION_SELECT);
        issueBaseDAO = daoFactory.getIssueBaseDAO(projectId);
        final IssueBase issueBase = issueBaseDAO.findByID(issueId);
        final List<IssueBase> subIssues = issueBaseDAO.getSubIssuesOf(issueBase);
        return issueBaseMapper.toFlatEntityList(subIssues);
    }

    @WebMethod
    public List<FlatIssueBase> getTopLevelIssues(@WebParam(name = "projectId") final Long projectId) {
        issueBaseMapper.checkPermission(EntityMapper.PERMISSION_SELECT);
        issueBaseDAO = daoFactory.getIssueBaseDAO(projectId);
        final List<IssueBase> issueBaseList = issueBaseDAO.loadTopLevelEntities();
        return issueBaseMapper.toFlatEntityList(issueBaseList);
    }
}
