/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.persistence.prj.stammdaten.kommunikation;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 19.09.11
 * Time: 00:43
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class KommunikationsServiceUIDPartDAO extends DAO<Long, KommunikationsServiceUIDPart> {


    public KommunikationsServiceUIDPartDAO(final EntityManager entityManager) {
        super(entityManager, KommunikationsServiceUIDPart.class);
    }
}
