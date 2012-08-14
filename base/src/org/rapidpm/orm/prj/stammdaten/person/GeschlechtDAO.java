/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@rapidpm.org
 */

package org.rapidpm.orm.prj.stammdaten.person;

import org.rapidpm.Constants;
import org.rapidpm.orm.BaseDaoFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 20.12.11
 * Time: 15:14
 */
public class GeschlechtDAO extends BaseDaoFactory.BaseDAO<Long, org.rapidpm.orm.prj.stammdaten.person.Geschlecht> {

    public GeschlechtDAO(final EntityManager entityManager) {
        super(entityManager, org.rapidpm.orm.prj.stammdaten.person.Geschlecht.class);
    }

    public Geschlecht load(final String geschlechtTxt) {
        final TypedQuery<Geschlecht> typedQuery = entityManager.createQuery("from Geschlecht g where g.geschlecht=:geschlechtTxt",
                Geschlecht.class).setParameter("geschlechtTxt", geschlechtTxt);
        return getSingleResultOrNull(typedQuery);
    }

    public Geschlecht loadGeschlechtMaennlich() {
        return load(Constants.GESCHLECHT_M);
    }

    public Geschlecht loadGeschlechtWeiblich() {
        return load(Constants.GESCHLECHT_W);
    }

    public Geschlecht loadGeschlechtNothing() {
        return load("Nothing");
    }

}
