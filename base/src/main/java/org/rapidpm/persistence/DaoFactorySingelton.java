package org.rapidpm.persistence;


import org.rapidpm.Constants;

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
        return daoFactoryFactory;
    }

    private DaoFactorySingelton() {
        if(Constants.INTEGRATION_TEST){
            daoFactoryFactory = new DaoFactory(Constants.PERSISTENCE_UNIT_NAME_TEST);
        }else{
            daoFactoryFactory = new DaoFactory(Constants.PERSISTENCE_UNIT_NAME_PROD);
        }

    }
}