package org.rapidpm.persistence.prj.stammdaten.person;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import java.security.InvalidKeyException;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 20.02.2010
 *        Time: 17:12:33
 */


public class AnredeDAO extends DAO<Long, Anrede> {
    private static final Logger logger = Logger.getLogger(AnredeDAO.class);

    public AnredeDAO(final OrientGraph orientDB) {
        super(orientDB, Anrede.class);
    }


    //    public List<Anrede> loadAllEntities() {
    //        return super.loadAllEntities(Anrede.class);
    //    }

    public Anrede loadAnredeHerr() {
        return load("Herr");
        //        return (Anrede) orientDB.createNamedQuery("LoadAnredeHerr").getSingleResult();
    }

    public Anrede loadAnredeNothing() {
        return load("Nothing");
        //        return (Anrede) orientDB.createNamedQuery("LoadAnredeHerr").getSingleResult();
    }

    public Anrede load(final String anredeTxt) {
//        final TypedQuery<Anrede> typedQuery = orientDB.createQuery("from Anrede a where a.anrede=:anredeTxt", Anrede.class).setParameter(
//                "anredeTxt",
//                anredeTxt);
//        return getSingleResultOrNull(typedQuery);
        return null;
        //        return createWhereClause().eq("anrede", anredeTxt).findUnique();
        //        final Anrede result;
        //        final ObjectSet<Anrede> objSet = orientDB.query(new Predicate<Anrede>() {
        //            @Override
        //            public boolean match(final Anrede anrede) {
        //                return anrede.getAnrede().equals(anredeTxt);
        //            }
        //        });
        //        final int size = objSet.size();
        //        if(size==1){
        //            result = objSet.get(0);
        //        }else{
        //            result = null;
        //        }
        //        return result;
    }

    public Anrede loadAnredeFrau() {
        return load("Frau");
    }


    @Override
    public Anrede loadFull(Anrede entity) throws InvalidKeyException, NotYetImplementedException {
        throw new NotYetImplementedException();
    }

    @Override
    public Anrede createEntityFull(Anrede entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
        throw new NotYetImplementedException();
    }
}
