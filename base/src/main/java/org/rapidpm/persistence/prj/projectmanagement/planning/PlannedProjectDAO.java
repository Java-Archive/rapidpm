package org.rapidpm.persistence.prj.projectmanagement.planning;

import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.EntityUtils;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.BenutzerDAO;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

import static org.rapidpm.persistence.Edges.*;
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

    @Override
    public PlannedProject createEntityFull(final PlannedProject tempPlannedProject) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {

        final PlannedProject persistedPlannedProject = createEntityFlat(tempPlannedProject);

        final List<PlanningUnit> temporaryPlanningUnits = tempPlannedProject.getTopLevelPlanningUnits();
        if(temporaryPlanningUnits == null){
            throw new MissingNonOptionalPropertyException("planningUnits");
        }

        final PlanningUnitDAO planningUnitDAO = DaoFactorySingleton.getInstance().getPlanningUnitDAO();
        persistedPlannedProject.setPlanningUnits(new ArrayList<>());
        final List<PlanningUnit> persistedPlanningUnits = new ArrayList<>();
        for (final PlanningUnit temporaryPlanningUnit : temporaryPlanningUnits) {
            persistedPlanningUnits.add(planningUnitDAO.createEntityFull(temporaryPlanningUnit));
        }
        addPlanningUnitsToProject(persistedPlanningUnits, persistedPlannedProject);
        persistedPlannedProject.setPlanningUnits(persistedPlanningUnits);

        final BenutzerDAO benutzerDAO = DaoFactorySingleton.getInstance().getBenutzerDAO();
        final Benutzer responsiblePerson = tempPlannedProject.getResponsiblePerson();
        if(responsiblePerson == null){
            throw new MissingNonOptionalPropertyException("responsiblePerson");
        }
        Benutzer persistedResponsiblePerson = null;
        if(responsiblePerson.getId() == null || responsiblePerson.getId().equals("")){
            persistedResponsiblePerson = benutzerDAO.createEntityFull(responsiblePerson);
        } else {
            persistedResponsiblePerson = responsiblePerson;
        }
        setResponsiblePersonForProject(persistedResponsiblePerson, persistedPlannedProject);
        persistedPlannedProject.setResponsiblePerson(persistedResponsiblePerson);

        final Benutzer creationUser = tempPlannedProject.getCreator();
        if(creationUser == null){
            throw new MissingNonOptionalPropertyException("creator");
        }
        Benutzer persistedCreationUser = null;
        if(creationUser.getId() == null || creationUser.getId().equals("")){
            persistedCreationUser = benutzerDAO.createEntityFull(creationUser);
        } else {
            persistedCreationUser = creationUser;
        }
        setCreatorForProject(persistedResponsiblePerson, persistedPlannedProject);
        persistedPlannedProject.setCreator(persistedCreationUser);

        return persistedPlannedProject;
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

    public void setCreatorForProject(final Benutzer creator, final PlannedProject project){
        final Vertex plannedProjectVertex = orientDB.getVertex(project.getId());
        final Vertex creatorVertex = orientDB.getVertex(creator.getId());
        addEdgeFromVertexToVertex(creatorVertex, CREATED, plannedProjectVertex);
    }

    public void setResponsiblePersonForProject(final Benutzer responsiblePerson, final PlannedProject project){
        final Vertex plannedProjectVertex = orientDB.getVertex(project.getId());
        final Vertex responsibleUserVertex = orientDB.getVertex(responsiblePerson.getId());
        addEdgeFromVertexToVertex(responsibleUserVertex, IS_RESPONSIBLE_FOR_PROJECT, plannedProjectVertex);
    }

    public void addPlanningUnitToProject(final PlanningUnit planningUnit, final PlannedProject plannedProject){
        final Vertex plannedProjectVertex = orientDB.getVertex(plannedProject.getId());
        final Vertex planningUnitVertex = orientDB.getVertex(planningUnit.getId());
        addEdgeFromVertexToVertex(plannedProjectVertex, CONSISTS_OF, planningUnitVertex);
    }

    public void addPlanningUnitsToProject(final List<PlanningUnit> planningUnits, final PlannedProject project){
        for (final PlanningUnit planningUnit : planningUnits) {
            addPlanningUnitToProject(planningUnit, project);
        }
    }

    @Override
    public PlannedProject loadFull(final PlannedProject projekt) throws InvalidKeyException {
        if(projekt.getId() == null){
            throw new InvalidKeyException("Can't load details for Project without ID");
        }
        projekt.setPlanningUnits(new ArrayList<>());
        final Iterable<Vertex> planningUnits = orientDB.command(new OCommandSQL("select expand( out('"+CONSISTS_OF+"') ) from PlannedProject where @rid = " + projekt.getId())).execute();
        for (final Vertex planningUnitVertex : planningUnits) {
            projekt.getTopLevelPlanningUnits().add(new EntityUtils<>(PlanningUnit.class).convertVertexToEntity(planningUnitVertex));
        }
        return projekt;
    }

    @Override
    public void deleteByIDFull(final String id) {
        deleteByIDFlat(id);
        final Iterable<Vertex> planningUnits = orientDB.command(new OCommandSQL("select expand( out('"+CONSISTS_OF+"') ) from PlannedProject where @rid = " + id)).execute();
        for (final Vertex planningUnitVertex : planningUnits) {
            DaoFactorySingleton.getInstance().getPlanningUnitDAO().deleteByIDFull(planningUnitVertex.getId().toString());
        }
    }
}
