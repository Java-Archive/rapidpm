package org.rapidpm.webservice.persistence.prj.projectmanagement.planning;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProjectDAO;
import org.rapidpm.webservice.mapping.FlatBaseWS;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * User: Alexander Vos
 * Date: 03.12.12
 * Time: 12:45
 */
@WebService(serviceName = "PlannedProjectWS")
public class PlannedProjectWS extends FlatBaseWS<PlannedProject, PlannedProjectDAO, FlatPlannedProject> {
    private static final Logger logger = Logger.getLogger(PlannedProjectWS.class);

    public PlannedProjectWS() {
        super(DaoFactorySingelton.getInstance().getPlannedProjectDAO());
    }

    @Override
    protected FlatPlannedProject toFlatObject(final PlannedProject plannedProject) {
        if (plannedProject == null) {
            return null;
        }
        final FlatPlannedProject project = new FlatPlannedProject();
        project.setId(plannedProject.getId());
        project.setProjektName(plannedProject.getProjektName());
        return project;
    }

    @WebMethod
    public FlatPlannedProject getFirstProject() {
        final List<PlannedProject> plannedProjects = dao.loadAllEntities();
        if (plannedProjects != null && !plannedProjects.isEmpty()) {
            return toFlatObject(plannedProjects.get(0));
        }
        return null;
    }
}
