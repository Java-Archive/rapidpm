package org.rapidpm.persistence.prj.projectmanagement; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 29.04.11
 * Time: 00:33
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProjectName;

import javax.persistence.EntityManager;

public class ProjectNameDAO extends DAO<Long, PlannedProjectName> {
    private static final Logger logger = Logger.getLogger(ProjectNameDAO.class);


    public ProjectNameDAO(final EntityManager entityManager) {
        super(entityManager, PlannedProjectName.class);
    }


}
