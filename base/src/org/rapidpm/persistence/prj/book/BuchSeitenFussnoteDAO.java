package org.rapidpm.persistence.prj.book;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 18:04
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class BuchSeitenFussnoteDAO extends DAO<Long, BuchSeitenFussnote> {
    private static final Logger logger = Logger.getLogger(BuchSeitenFussnoteDAO.class);


    public BuchSeitenFussnoteDAO(final EntityManager entityManager) {
        super(entityManager, BuchSeitenFussnote.class);
    }
}
