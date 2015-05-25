package org.rapidpm.persistence.prj.projectmanagement.planning;

import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;
import org.rapidpm.persistence.EntityUtils;

import java.security.InvalidKeyException;
import java.util.HashSet;
import java.util.List;

import static org.rapidpm.persistence.Edges.CONSISTS_OF;

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

    public void addPlanningUnitToProject(final PlannedProject plannedProject, final PlanningUnit planningUnit){
        final Vertex plannedProjectVertex = orientDB.getVertex(plannedProject.getId());
        final Vertex planningUnitVertex = orientDB.getVertex(planningUnit.getId());
        addEdgeFromVertexToVertex(plannedProjectVertex, CONSISTS_OF, planningUnitVertex);
    }

    @Override
    public PlannedProject loadFull(final PlannedProject projekt) throws InvalidKeyException {
        if(projekt.getId() == null){
            throw new InvalidKeyException("Can't load details for Project without ID");
        }
        projekt.setPlanningUnits(new HashSet<>());
        final Iterable<Vertex> planningUnits = orientDB.command(new OCommandSQL("select expand( out('"+CONSISTS_OF+"') ) from PlannedProject where @rid = " + projekt.getId())).execute();
        for (final Vertex planningUnitVertex : planningUnits) {
            projekt.getPlanningUnits().add(new EntityUtils<>(PlanningUnit.class).convertVertexToEntity(planningUnitVertex));
        }
        return projekt;
    }
}
