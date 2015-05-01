package org.rapidpm.persistence.prj.book;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 18:02
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class BuchKapitelDAO extends DAO<Long, BuchKapitel> {
    private static final Logger logger = Logger.getLogger(BuchKapitelDAO.class);


    public BuchKapitelDAO(final OrientGraph orientDB) {
        super(orientDB, BuchKapitel.class);
    }
}
