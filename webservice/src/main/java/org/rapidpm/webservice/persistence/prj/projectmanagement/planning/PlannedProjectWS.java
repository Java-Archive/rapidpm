package org.rapidpm.webservice.persistence.prj.projectmanagement.planning;

import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProjectDAO;
import org.rapidpm.webservice.mapping.FlatBaseWS;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Projekt Web-Service.
 */
@WebService(serviceName = "PlannedProjectWS")
public class PlannedProjectWS extends FlatBaseWS<PlannedProject, PlannedProjectDAO, FlatPlannedProject> {

    public PlannedProjectWS() {
        super(PlannedProject.class, FlatPlannedProject.class);
    }

    @Override
    protected PlannedProjectDAO getDao() {
        return daoFactory.getPlannedProjectDAO();
    }

    /**
     * Ermittelt das erste Projekt.
     *
     * @return Das erste Projekt oder <code>null</code>.
     */
    @WebMethod
    public FlatPlannedProject getFirstProject() {
        checkPermission(PERMISSION_SELECT);
        return toFlatEntity(dao.loadFirstProject());
    }
}
