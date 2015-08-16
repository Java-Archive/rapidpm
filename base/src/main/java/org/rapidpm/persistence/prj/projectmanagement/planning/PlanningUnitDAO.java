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
import org.rapidpm.persistence.prj.textelement.TextElement;
import org.rapidpm.persistence.prj.textelement.TextElementDAO;
import org.rapidpm.persistence.system.security.Benutzer;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

import static org.rapidpm.persistence.Edges.*;

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
    public PlanningUnit createEntityFull(PlanningUnit temporaryPlanningUnit) throws NotYetImplementedException, InvalidKeyException, MissingNonOptionalPropertyException {

        final PlanningUnit persistedPlanningUnit = createEntityFlat(temporaryPlanningUnit);

        if(temporaryPlanningUnit.getParent() != null){
            addChildPlanningUnitToParentPlanningUnit(temporaryPlanningUnit.getParent(), persistedPlanningUnit);
        }

        if( temporaryPlanningUnit.getKindPlanningUnits() != null && !temporaryPlanningUnit.getKindPlanningUnits().isEmpty())
        {
            for (final PlanningUnit tempChildPlanningUnit : temporaryPlanningUnit.getKindPlanningUnits()) {
                tempChildPlanningUnit.setParent(persistedPlanningUnit);
                createEntityFull(tempChildPlanningUnit);
            }
        }

        final List<PlanningUnitElement> tempPlanningUnitElements = temporaryPlanningUnit.getPlanningUnitElementList();
        final List<TextElement> tempDescriptions = temporaryPlanningUnit.getDescriptions();
        final List<TextElement> tempTestCases = temporaryPlanningUnit.getTestcases();

        if(tempPlanningUnitElements == null){
            throw new MissingNonOptionalPropertyException("planningUnitElements");
        }
        final PlanningUnitElementDAO planningUnitElementDAO = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO();
        final List<PlanningUnitElement> persistedPUEs = new ArrayList<>();
        for (final PlanningUnitElement tempPlanningUnitElement : tempPlanningUnitElements) {
            persistedPUEs.add(planningUnitElementDAO.createEntityFull(tempPlanningUnitElement));
        }
        addPlanningUnitElementsToPlanningUnit(persistedPlanningUnit, persistedPUEs);
        persistedPlanningUnit.setPlanningUnitElementList(persistedPUEs);

        addPlanningUnitElementsToPlanningUnit(persistedPlanningUnit, persistedPUEs);
        persistedPlanningUnit.setPlanningUnitElementList(persistedPUEs);


        final TextElementDAO textElementDAO = DaoFactorySingleton.getInstance().getTextElementDAO();

        if (tempDescriptions != null) {
            final List<TextElement> persistedDescriptions = new ArrayList<>();
            for (final TextElement tempDescription : tempDescriptions) {
                persistedDescriptions.add(textElementDAO.createEntityFull(tempDescription));
            }
            addDescriptionsToPlanningUnit(persistedDescriptions, persistedPlanningUnit);
            persistedPlanningUnit.setDescriptions(persistedDescriptions);
        }
        if (tempTestCases != null) {
            final List<TextElement> persistedTestCases = new ArrayList<>();
            for (final TextElement tempTestCase : tempTestCases) {
                persistedTestCases.add(textElementDAO.createEntityFull(tempTestCase));
            }
            addTestCasesToPlanningUnit(persistedTestCases, persistedPlanningUnit);
            persistedPlanningUnit.setTestcases(persistedTestCases);
        }
        return persistedPlanningUnit;
    }

    @Override
    public PlanningUnit loadFull(PlanningUnit planningUnit) throws InvalidKeyException, NotYetImplementedException {
        if(planningUnit.getId() == null){
            throw new InvalidKeyException("Can't load details for PlanningUnit without ID");
        }
        planningUnit.setKindPlanningUnits(new ArrayList<>());
        planningUnit.setPlanningUnitElementList(new ArrayList<>());
        planningUnit.setDescriptions(new ArrayList<>());
        planningUnit.setTestcases(new ArrayList<>());
        final Iterable<Vertex> responsiblePerson = orientDB.command(new OCommandSQL("select expand( in('"+IS_RESPONSIBLE_FOR+"') ) from PlanningUnit where @rid = " + planningUnit.getId())).execute();
        final Iterable<Vertex> planningUnitElements = orientDB.command(new OCommandSQL("select expand( out('"+HAS+"') ) from PlanningUnit where @rid = " + planningUnit.getId())).execute();
        final Iterable<Vertex> childPlanningUnits = orientDB.command(new OCommandSQL("select expand( out('"+IS_FATHER_OF+"') ) from PlanningUnit where @rid = " + planningUnit.getId())).execute();
        final Iterable<Vertex> descriptions = orientDB.command(new OCommandSQL("select expand( out('"+HAS_DESCRIPTION+"') ) from PlanningUnit where @rid = " + planningUnit.getId())).execute();
        final Iterable<Vertex> testCases = orientDB.command(new OCommandSQL("select expand( out('"+HAS_TESTCASE+"') ) from PlanningUnit where @rid = " + planningUnit.getId())).execute();

        for (final Vertex planningUnitVertex : childPlanningUnits) {
            planningUnit.getKindPlanningUnits().add(new EntityUtils<>(PlanningUnit.class).convertVertexToEntity(planningUnitVertex));
        }
        // should only be one
        for (final Vertex responsiblePersonVertex : responsiblePerson) {
            if(responsiblePersonVertex != null && responsiblePersonVertex.getId() != null && !responsiblePersonVertex.getId().toString().startsWith("-")){
                planningUnit.setResponsiblePerson(new EntityUtils<>(Benutzer.class).convertVertexToEntity(responsiblePersonVertex));
            }
        }
        for (final Vertex planningUnitElementVertex : planningUnitElements) {
            PlanningUnitElement planningUnitElement = new EntityUtils<>(PlanningUnitElement.class).convertVertexToEntity(planningUnitElementVertex);
            planningUnitElement = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().loadFull(planningUnitElement);
            planningUnit.getPlanningUnitElementList().add(planningUnitElement);
        }
        for (final Vertex descriptionVertex : descriptions) {
            planningUnit.getDescriptions().add(new EntityUtils<>(TextElement.class).convertVertexToEntity(descriptionVertex));
        }
        for (final Vertex testCaseVertex : testCases) {
            planningUnit.getTestcases().add(new EntityUtils<>(TextElement.class).convertVertexToEntity(testCaseVertex));
        }
        return planningUnit;
    }

    public void addDescriptionToPlanningUnit(final TextElement description, final PlanningUnit planningUnit){
        final Vertex planningUnitVertex = orientDB.getVertex(planningUnit.getId());
        final Vertex descriptionVertex = orientDB.getVertex(description.getId());
        addEdgeFromVertexToVertex(planningUnitVertex, HAS_DESCRIPTION, descriptionVertex);
    }

    public void addDescriptionsToPlanningUnit(final List<TextElement> descriptions, final PlanningUnit planningUnit){
        for (final TextElement description : descriptions) {
            addDescriptionToPlanningUnit(description, planningUnit);
        }
    }

    public void addTestCaseToPlanningUnit(final TextElement testCase, final PlanningUnit planningUnit){
        final Vertex planningUnitVertex = orientDB.getVertex(planningUnit.getId());
        final Vertex testCaseVertex = orientDB.getVertex(testCase.getId());
        addEdgeFromVertexToVertex(planningUnitVertex, HAS_TESTCASE, testCaseVertex);
    }

    public void addTestCasesToPlanningUnit(final List<TextElement> testCases, final PlanningUnit planningUnit){
        for (final TextElement testCase : testCases) {
            addTestCaseToPlanningUnit(testCase, planningUnit);
        }
    }

    public void addChildPlanningUnitToParentPlanningUnit(final PlanningUnit parentPlanningUnit, final PlanningUnit childPlanningUnit){
        final Vertex parentPlanningUnitVertex = orientDB.getVertex(parentPlanningUnit.getId());
        final Vertex childPlanningUnitVertex = orientDB.getVertex(childPlanningUnit.getId());
        addEdgeFromVertexToVertex(parentPlanningUnitVertex, IS_FATHER_OF, childPlanningUnitVertex);
    }

    public void addPlanningUnitElementToPlanningUnit(final PlanningUnitElement planningUnitElement, final PlanningUnit planningUnit) {
        final Vertex planningUnitVertex = orientDB.getVertex(planningUnit.getId());
        final Vertex planningUnitElementVertex = orientDB.getVertex(planningUnitElement.getId());
        addEdgeFromVertexToVertex(planningUnitVertex, HAS, planningUnitElementVertex);
    }

    public void addResponsiblePersonToPlanningUnit(final Benutzer resonsiblePerson, final PlanningUnit planningUnit) {
        final Vertex responsiblePersonVertex = orientDB.getVertex(resonsiblePerson.getId());
        final Vertex planningUnitVertex = orientDB.getVertex(planningUnit.getId());
        addEdgeFromVertexToVertex(responsiblePersonVertex, IS_RESPONSIBLE_FOR, planningUnitVertex);
    }

    public void addPlanningUnitElementsToPlanningUnit(final List<PlanningUnitElement> planningUnitElements, final PlanningUnit planningUnit) {
        for (final PlanningUnitElement planningUnitElement : planningUnitElements) {
            addPlanningUnitElementToPlanningUnit(planningUnitElement, planningUnit);
        }
    }

    private void addPlanningUnitElementsToPlanningUnit(final PlanningUnit planningUnit, List<PlanningUnitElement> planningUnitElements){
        for (final PlanningUnitElement planningUnitElement : planningUnitElements) {
            addPlanningUnitElementToPlanningUnit(planningUnitElement, planningUnit);
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

    @Override
    public void deleteByIDFull(final String id) {
        final Iterable<Vertex> planningUnitElements = orientDB.command(new OCommandSQL("select expand( in('"+HAS+"') ) from PlanningUnitElement where @rid = " + id)).execute();
        for (final Vertex pueVertex : planningUnitElements) {
            DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().deleteByIDFull(pueVertex.getId().toString());
        }
        deleteByIDFlat(id);
        final Iterable<Vertex> childPlanningUnits = orientDB.command(new OCommandSQL("select expand( out('"+IS_FATHER_OF+"') ) from PlanningUnit where @rid = " + id)).execute();
        for (final Vertex childPlanningUnitVertex : childPlanningUnits) {
            deleteByIDFull(childPlanningUnitVertex.getId().toString());
        }

    }
}
