package org.rapidpm.persistence.prj.projectmanagement; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 29.04.11
 * Time: 00:33
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProjectName;

import java.security.InvalidKeyException;

public class ProjectNameDAO extends DAO<Long, PlannedProjectName> {
    private static final Logger logger = Logger.getLogger(ProjectNameDAO.class);


    public ProjectNameDAO(final OrientGraph orientDB) {
        super(orientDB, PlannedProjectName.class);
    }


    @Override
    public PlannedProjectName loadFull(PlannedProjectName entity) throws InvalidKeyException, NotYetImplementedException {
        throw new NotYetImplementedException();
    }

    @Override
    public PlannedProjectName createEntityFull(PlannedProjectName entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
        throw new NotYetImplementedException();
    }
}
