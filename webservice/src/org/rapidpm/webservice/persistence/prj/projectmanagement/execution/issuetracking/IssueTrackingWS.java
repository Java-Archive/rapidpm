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
import java.util.List;

/**
 * User: Alexander Vos
 * Date: 30.11.12
 * Time: 15:14
 */
@WebService(serviceName = "IssueTrackingWS")
public class IssueTrackingWS {
    private static final Logger logger = Logger.getLogger(IssueTrackingWS.class);

    private IssueBaseDAO issueBaseDAO;

    private final EntityMapper<IssueBase, FlatIssueBase> issueBaseMapper = new EntityMapper<IssueBase, FlatIssueBase>(IssueBase.class, FlatIssueBase.class) {
        @Override
        protected IssueBase findEntityById(final Long id) {
            return issueBaseDAO.findByID(id);
        }
    };

    @WebMethod
    public List<FlatIssueBase> getTopLevelIssues(@WebParam(name = "projectId") final Long projectId) {
        issueBaseMapper.checkPermission(EntityMapper.PERMISSION_SELECT);
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        issueBaseDAO = daoFactory.getIssueBaseDAO(projectId);
        final List<IssueBase> issueBaseList = issueBaseDAO.loadTopLevelEntities();
        return issueBaseMapper.toFlatEntityList(issueBaseList);
    }
}
