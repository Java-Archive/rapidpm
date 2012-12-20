package org.rapidpm.webservice.persistence.prj.projectmanagement.execution.issuetracking;

import org.apache.log4j.Logger;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * User: Alexander Vos
 * Date: 30.11.12
 * Time: 15:14
 */
@WebService(serviceName = "IssueTrackingWS")
public class IssueTrackingWS {
    private static final Logger logger = Logger.getLogger(IssueTrackingWS.class);

    // TODO
    @WebMethod(operationName = "dummy")
    public void dummy() {
    }
}
