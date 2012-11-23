package org.rapidpm.persistence.prj.stammdaten.organisationseinheit;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.gesellschaftsformen.Gesellschaftsform;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 09.03.2010
 *        Time: 18:21:02
 */

public class GesellschaftsformDAO extends DAO<Long, Gesellschaftsform> {
    private static final Logger logger = Logger.getLogger(GesellschaftsformDAO.class);
    public static final String UNBEKANNT = "";

    public GesellschaftsformDAO(final EntityManager entityManager) {
        super(entityManager, Gesellschaftsform.class);
    }


    //    protected List<Gesellschaftsform> loadAllEntities() {
    //        return loadAllEntities(Gesellschaftsform.class);
    //    }

    /**
     * Es wird die Gesellschaftform geladen die durch die Bezeichnung angegeben wird. z.B. GmbH.
     * Wird die angegebene Gesellschaftsform nicht gefunden, so wird die Gesellschaftsform Unbekannt geladen und zureückgegeben.
     *
     * @param abkuerzung
     * @return siehe Beschreibung
     */
    public Gesellschaftsform loadGesellschaftsform(final String abkuerzung) {
        return load(abkuerzung);
    }


    public Gesellschaftsform loadGesellschaftsformUnbekannt() {
        return load(UNBEKANNT);
        //        return (Gesellschaftsform) entityManager.createNamedQuery("LoadGesellschaftsformUnbekannt").getSingleResult();
    }

    private Gesellschaftsform load(final String abkuerzung) {
        final TypedQuery<Gesellschaftsform> query = entityManager.createQuery("from Gesellschaftsform g where g.abkuerzung=:abkuerzung", Gesellschaftsform.class);
        final TypedQuery<Gesellschaftsform> typedQuery = query.setParameter("abkuerzung", abkuerzung);
        return getSingleResultOrNull(typedQuery);
        //        return createWhereClause().eq("abkuerzung", abkuerzung).findUnique();
        //        final Gesellschaftsform gesellschaftsform;
        //        final ObjectSet<Gesellschaftsform> objSet = entityManager.query(new Predicate<Gesellschaftsform>() {
        //            @Override
        //            public boolean match(final Gesellschaftsform gesellschaftsform) {
        //                return gesellschaftsform.getBezeichnung().equals(abkuerzung);
        //            }
        //        });
        //        final int size = objSet.size();
        //        if (size == 1) {
        //            gesellschaftsform = objSet.get(0);
        //        } else {
        //            gesellschaftsform = null;
        //        }
        //        return gesellschaftsform;
    }


}
