package org.rapidpm.persistence.prj.projectmanagement.planning;

import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;
import org.rapidpm.persistence.Edges;
import org.rapidpm.persistence.EntityUtils;
import org.rapidpm.persistence.prj.textelement.TextElement;

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
public class PlanningUnitDAO extends DAO<Long, PlanningUnit> {
    private static final Logger logger = Logger.getLogger(PlanningUnitDAO.class);

    public PlanningUnitDAO(final OrientGraph orientDB) {
        super(orientDB, PlanningUnit.class);
    }

    @Override
    public PlanningUnit createEntity(PlanningUnit entity) {
        final List<PlanningUnitElement> planningUnitElements = entity.getPlanningUnitElementList();
        final List<TextElement> descriptions = entity.getDescriptions();
        final List<TextElement> testCases = entity.getTestcases();
        entity = super.createEntity(entity);
        addPlanningUnitElementsToPlanningUnit(entity, planningUnitElements);
        if(descriptions != null){
            for (final TextElement textElement : descriptions) {
                addDescriptionToPlanningUnit(entity, textElement);
            }
        }
        if(testCases != null) {
            for (final TextElement textElement : testCases) {
                addTestCaseToPlanningUnit(entity, textElement);
            }
        }
        return entity;
    }

    @Override
    public PlanningUnit loadFull(PlanningUnit planningUnit) throws InvalidKeyException, NotYetImplementedException {
        if(planningUnit.getId() == null){
            throw new InvalidKeyException("Can't load details for PlanningUnit without ID");
        }
        planningUnit.setKindPlanningUnits(new HashSet<>());
        planningUnit.setPlanningUnitElementList(new ArrayList<>());
        planningUnit.setDescriptions(new ArrayList<>());
        planningUnit.setTestcases(new ArrayList<>());
        final Iterable<Vertex> planningUnitElements = orientDB.command(new OCommandSQL("select expand( out('"+HAS+"') ) from PlanningUnit where @rid = " + planningUnit.getId())).execute();
        final Iterable<Vertex> childPlanningUnits = orientDB.command(new OCommandSQL("select expand( out('"+IS_FATHER_OF+"') ) from PlanningUnit where @rid = " + planningUnit.getId())).execute();
        final Iterable<Vertex> descriptions = orientDB.command(new OCommandSQL("select expand( out('"+HAS_DESCRIPTION+"') ) from PlanningUnit where @rid = " + planningUnit.getId())).execute();
        final Iterable<Vertex> testCases = orientDB.command(new OCommandSQL("select expand( out('"+HAS_TESTCASE+"') ) from PlanningUnit where @rid = " + planningUnit.getId())).execute();
        for (final Vertex planningUnitVertex : childPlanningUnits) {
            planningUnit.getKindPlanningUnits().add(new EntityUtils<>(PlanningUnit.class).convertVertexToEntity(planningUnitVertex));
        }
        for (final Vertex planningUnitElementVertex : planningUnitElements) {
            planningUnit.getPlanningUnitElementList().add(new EntityUtils<>(PlanningUnitElement.class).convertVertexToEntity(planningUnitElementVertex));
        }
        for (final Vertex descriptionVertex : descriptions) {
            planningUnit.getDescriptions().add(new EntityUtils<>(TextElement.class).convertVertexToEntity(descriptionVertex));
        }
        for (final Vertex testCaseVertex : testCases) {
            planningUnit.getTestcases().add(new EntityUtils<>(TextElement.class).convertVertexToEntity(testCaseVertex));
        }
        return planningUnit;
    }

    public void addDescriptionToPlanningUnit(final PlanningUnit planningUnit, final TextElement description){
        final Vertex planningUnitVertex = orientDB.getVertex(planningUnit.getId());
        final Vertex descriptionVertex = orientDB.getVertex(description.getId());
        addEdgeFromVertexToVertex(planningUnitVertex, HAS_DESCRIPTION, descriptionVertex);
    }

    public void addTestCaseToPlanningUnit(final PlanningUnit planningUnit, final TextElement testCase){
        final Vertex planningUnitVertex = orientDB.getVertex(planningUnit.getId());
        final Vertex testCaseVertex = orientDB.getVertex(testCase.getId());
        addEdgeFromVertexToVertex(planningUnitVertex, HAS_TESTCASE, testCaseVertex);
    }

    public void addChildPlanningUnitToParentPlanningUnit(final PlanningUnit parentPlanningUnit, final PlanningUnit childPlanningUnit){
        final Vertex parentPlanningUnitVertex = orientDB.getVertex(parentPlanningUnit.getId());
        final Vertex childPlanningUnitVertex = orientDB.getVertex(childPlanningUnit.getId());
        addEdgeFromVertexToVertex(parentPlanningUnitVertex, IS_FATHER_OF, childPlanningUnitVertex);
    }

    private void addPlanningUnitElementToPlanningUnit(final PlanningUnit planningUnit, final PlanningUnitElement planningUnitElement){
        final Vertex planningUnitVertex = orientDB.getVertex(planningUnit.getId());
        final Vertex planningUnitElementVertex = orientDB.getVertex(planningUnitElement.getId());
        addEdgeFromVertexToVertex(planningUnitVertex, HAS, planningUnitElementVertex);
    }

    private void addPlanningUnitElementsToPlanningUnit(final PlanningUnit planningUnit, List<PlanningUnitElement> planningUnitElements){
        final Vertex planningUnitVertex = orientDB.getVertex(planningUnit.getId());
        for (final PlanningUnitElement planningUnitElement : planningUnitElements) {
            final Vertex planningUnitElementVertex = orientDB.getVertex(planningUnitElement.getId());
            addEdgeFromVertexToVertex(planningUnitVertex, HAS, planningUnitElementVertex);
        }
    }

    public PlanningUnit loadPlanningUnitByName(final String planningUnitName) {
//        final TypedQuery<PlanningUnit> typedQuery = orientDB.createQuery("from PlanningUnit pu "
//                + "where pu.planningUnitName=:planningUnitName ", PlanningUnit.class).setParameter("planningUnitName", planningUnitName);
//        final PlanningUnit singleResultOrNull = getSingleResultOrNull(typedQuery);
//        return singleResultOrNull;
        return null;
    }

    public List<PlanningUnit> loadAllPlanningUnitsWithoutParents() {
//        final TypedQuery<PlanningUnit> typedQuery = orientDB.createQuery("from PlanningUnit pu "
//                + "where pu.parent=null ", PlanningUnit.class);
//        final List<PlanningUnit> resultList = typedQuery.getResultList();
//        return resultList;
        return null;
    }


}
