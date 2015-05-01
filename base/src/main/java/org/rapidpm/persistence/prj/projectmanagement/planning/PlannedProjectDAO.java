package org.rapidpm.persistence.prj.projectmanagement.planning;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 12.09.12
 * Time: 13:24
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlannedProjectDAO extends DAO<Long, PlannedProject> {
    private static final Logger logger = Logger.getLogger(PlannedProjectDAO.class);

    public PlannedProjectDAO(final OrientGraph orientDB) {
        super(orientDB, PlannedProject.class);
    }

    public PlannedProject loadFirstProject() {
        final List<PlannedProject> plannedProjects = findAll();
        if(plannedProjects == null || plannedProjects.isEmpty()){
            final PlannedProject somethingWentWrongProject = new PlannedProject();
            somethingWentWrongProject.setProjektName("Chaos");
            return somethingWentWrongProject;
        } else {
            return plannedProjects.get(0);
        }
    }
}
