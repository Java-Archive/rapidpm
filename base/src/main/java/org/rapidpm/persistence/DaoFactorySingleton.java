package org.rapidpm.persistence;


/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: Nov 16, 2010
 * Time: 5:36:49 PM
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */
public class DaoFactorySingleton {
    private static DaoFactory daoFactory;

    public static DaoFactory getInstance() {
        if(daoFactory == null){
            daoFactory = new DaoFactory("RapidPM");
        }
        return daoFactory;
    }

    private DaoFactorySingleton() {
    }
}