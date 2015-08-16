package org.rapidpm.persistence.prj.projectmanagement;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.Edges;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitDAO;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.BenutzerDAO;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 31.08.2010
 *        Time: 21:18:43
 */

public class ProjectDAO extends DAO<Long, PlannedProject> {

    private static final Logger logger = Logger.getLogger(ProjectDAO.class);

    public ProjectDAO(final OrientGraph orientDB) {
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
        for (final PlanningUnit temporaryPlanningUnit : temporaryPlanningUnits) {
            persistedPlannedProject.getTopLevelPlanningUnits().add(planningUnitDAO.createEntityFull(temporaryPlanningUnit));
        }

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
        persistedPlannedProject.setCreator(persistedCreationUser);

        return persistedPlannedProject;
    }

    public List<PlannedProject> loadProjectsFor(final Boolean active, final String mandantengruppe) {
//        return orientDB.createQuery("from Project  p where p.active=:active and p.mandantengruppe.mandantengruppe=:mandantengruppe", PlannedProject.class).setParameter("active", active).setParameter("mandantengruppe", mandantengruppe).getResultList();
        //        return createWhereClause().eq("active", active).eq("mandantengruppe.mandantengruppe", mandantengruppe).findList();
        //        final String sql = "select id from project p \n" +
        //                " where p.mandantengruppe_id = (" + mandantengruppeID(mandantengruppe) + ") \n" +
        //                and("bi.active", active.toString());
        //        return createQuery(sql).findList();
        return null;
    }

    public List<PlannedProject> loadProjectsFor(final String mandantengruppe) {
//        return orientDB.createQuery("from Project  p where p.mandantengruppe.mandantengruppe=:mandantengruppe", PlannedProject.class).setParameter("mandantengruppe", mandantengruppe).getResultList();
        //        return createWhereClause().eq("mandantengruppe.mandantengruppe", mandantengruppe).findList();

        //        final String sql = "select id from project p \n" +
        //                " where p.mandantengruppe_id = (" + mandantengruppeID(mandantengruppe) + ") \n";
        //       return createQuery(sql).findList();
        return null;
    }

    @Override
    public PlannedProject loadFull(PlannedProject entity) throws InvalidKeyException, NotYetImplementedException {
        throw new NotYetImplementedException();
    }

    public void addPlanningUnitToProject(final PlanningUnit newPlanningUnit, final PlannedProject project) {
        final Vertex projectVertex = orientDB.getVertex(project.getId());
        final Vertex newPlanningUnitVertex = orientDB.getVertex(newPlanningUnit.getId());
        addEdgeFromVertexToVertex(projectVertex, Edges.CONSISTS_OF, newPlanningUnitVertex);
    }

    //    public PlannedProject loadProjectFor(final String mandantengruppe, final String prjName){
    //        final TypedQuery<PlannedProject> typedQuery = orientDB.createQuery(
    //                "from PlannedProject  p where p.mandantengruppe.mandantengruppe=:mandantengruppe and p.projectName=:prjName",
    //                PlannedProject.class).setParameter("mandantengruppe", mandantengruppe).setParameter("prjName", prjName);
    //        return getSingleResultOrNull(typedQuery);
    //        //        return createWhereClause().eq("projectName", prjName).eq("mandantengruppe.mandantengruppe", mandantengruppe).findUnique();
    //        //        final String sql = "select id from project p \n" +
    //        //                " where p.mandantengruppe_id = (" + mandantengruppeID(mandantengruppe) + ") \n" +
    //        //                and("bi.project_name", prjName);
    //        //        return createQuery(sql).findUnique();
    //
    //    }

}
