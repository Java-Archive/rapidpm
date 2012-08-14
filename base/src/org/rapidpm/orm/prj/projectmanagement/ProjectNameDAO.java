package org.rapidpm.orm.prj.projectmanagement; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 29.04.11
 * Time: 00:33
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.rapidpm.orm.BaseDaoFactory;
import org.rapidpm.orm.prj.projectmanagement.planning.PlannedProjectName;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;

public class ProjectNameDAO extends BaseDaoFactory.BaseDAO<Long, PlannedProjectName> {
    private static final Logger logger = Logger.getLogger(ProjectNameDAO.class);


    public ProjectNameDAO(final EntityManager entityManager) {
        super(entityManager, PlannedProjectName.class);
    }


}
