package org.rapidpm.persistence.prj.bewegungsdaten.anfragen;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 26.04.12
 * Time: 11:56
 */
public class ProjektanfrageDAO extends DAO<Long, Projektanfrage> {

    public ProjektanfrageDAO(final OrientGraph orientDB) {
        super(orientDB, Projektanfrage.class);
    }

}
