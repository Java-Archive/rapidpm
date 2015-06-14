package org.rapidpm.persistence.prj.projectmanagement.planning;

import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.Edges;
import org.rapidpm.persistence.EntityUtils;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroupDAO;
import org.rapidpm.persistence.prj.textelement.TextElement;
import org.rapidpm.persistence.system.security.Benutzer;

import javax.persistence.EntityManager;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.rapidpm.persistence.Edges.*;
import static org.rapidpm.persistence.Edges.CONSISTS_OF;
import static org.rapidpm.persistence.Edges.IS_FATHER_OF;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 12.09.12
 * Time: 13:24
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningUnitElementDAO extends DAO<Long, PlanningUnitElement> {
    private static final Logger logger = Logger.getLogger(PlanningUnitElementDAO.class);

    public PlanningUnitElementDAO(final OrientGraph orientDB) {
        super(orientDB, PlanningUnitElement.class);
    }

    @Override
    public PlanningUnitElement createEntityFull(PlanningUnitElement tempPUE) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {

        final PlanningUnitElement persistedPUE = createEntityFlat(tempPUE);

        final RessourceGroup ressourceGroup = tempPUE.getRessourceGroup();

        final RessourceGroupDAO ressourceGroupDAO = DaoFactorySingleton.getInstance().getRessourceGroupDAO();
        if(ressourceGroup == null){
            throw new MissingNonOptionalPropertyException("ressourceGroup");
        }
        RessourceGroup persistedRessourceGroup = null;
        if(ressourceGroup.getId() == null || ressourceGroup.getId().equals("")){
            persistedRessourceGroup = ressourceGroupDAO.createEntityFull(ressourceGroup);
        } else {
            persistedRessourceGroup = ressourceGroup;
        }
        addRessourceGroupToPlanningUnitElement(persistedRessourceGroup, persistedPUE);
        persistedPUE.setRessourceGroup(persistedRessourceGroup);

        return persistedPUE;
    }

    private void addRessourceGroupToPlanningUnitElement(final RessourceGroup ressourceGroup, final PlanningUnitElement planningUnitElement){
        final Vertex planningUnitElementVertex = orientDB.getVertex(planningUnitElement.getId());
        final Vertex ressourceGroupVertex = orientDB.getVertex(ressourceGroup.getId());
        addEdgeFromVertexToVertex(planningUnitElementVertex, VALID_FOR, ressourceGroupVertex);
    }

    @Override
    public PlanningUnitElement loadFull(PlanningUnitElement planningUnitElement) throws InvalidKeyException, NotYetImplementedException {
        if(planningUnitElement.getId() == null){
            throw new InvalidKeyException("Can't load details for PlanningUnit without ID");
        }
        final Iterable<Vertex> ressourceGroup = orientDB.command(new OCommandSQL("select expand( out('"+VALID_FOR+"') ) from PlanningUnitElement where @rid = " + planningUnitElement.getId())).execute();
        for (final Vertex ressourceGroupVertex : ressourceGroup) {
            planningUnitElement.setRessourceGroup(new EntityUtils<>(RessourceGroup.class).convertVertexToEntity(ressourceGroupVertex));
        }
        return planningUnitElement;
    }

    @Override
    public void deleteByIDFull(final String id) {
        deleteByIDFlat(id);
    }
}
