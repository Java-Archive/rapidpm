package org.rapidpm.persistence.prj.projectmanagement.execution;

import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
//import org.rapidpm.persistence.GraphDaoFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 14.11.12
 * Time: 17:45
 * To change this template use File | Settings | File Templates.
 */
public interface BaseDAOTest {
    final Long PROJECTID = 1L;
    final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
}
