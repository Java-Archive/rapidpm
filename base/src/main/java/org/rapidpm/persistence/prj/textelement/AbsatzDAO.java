package org.rapidpm.persistence.prj.textelement;

import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 14.12.12
 * Time: 10:10
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */

public class AbsatzDAO extends DAO<Long, Absatz> {

    public AbsatzDAO(final EntityManager entityManager) {
        super(entityManager, Absatz.class);
    }
}