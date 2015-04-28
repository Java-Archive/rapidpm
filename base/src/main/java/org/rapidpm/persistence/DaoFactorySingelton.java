package org.rapidpm.persistence;


import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import org.rapidpm.Constants;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: Nov 16, 2010
 * Time: 5:36:49 PM
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */
public class DaoFactorySingelton {
    private static DaoFactory daoFactoryFactory;

    public static DaoFactory getInstance() {

        return new DaoFactory("hallo");
    }

    private DaoFactorySingelton() {
    }
}