package org.rapidpm.orm.system.security;

import org.apache.log4j.Logger;
import org.rapidpm.orm.BaseDAO;

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
 * @since 29.03.2010
 *        Time: 16:15:23
 */

public class BenutzerGruppeDAO extends BaseDAO<Long, BenutzerGruppe> {
    private static final Logger logger = Logger.getLogger(BenutzerGruppeDAO.class);

    public BenutzerGruppeDAO(final EntityManager entityManager) {
        super(entityManager, BenutzerGruppe.class);
    }

    //    public List<BenutzerGruppe> loadAllEntities() {
    //        return super.loadAllEntities(BenutzerGruppe.class);
    //    }


    public BenutzerGruppe loadBenutzerGruppeByName(final String benutzerGruppenName) {
        final TypedQuery<BenutzerGruppe> typedQuery = entityManager.createQuery("from BenutzerGruppe bg where bg.gruppenname=:benutzerGruppenName", BenutzerGruppe.class).setParameter("benutzerGruppenName", benutzerGruppenName);
        return getSingleResultOrNull(typedQuery);
        //        return createWhereClause().eq("gruppenname", benutzerGruppenName).findUnique();

        //        final ObjectSet<BenutzerGruppe> objSet = entityManager.query(new Predicate<BenutzerGruppe>() {
        //            @Override
        //            public boolean match(final BenutzerGruppe benutzerGruppe) {
        //                return benutzerGruppe.getGruppenName().equals(benutzerGruppenName);
        //            }
        //        });
        //        final int size = objSet.size();
        //        final BenutzerGruppe result;
        //        if(size==1){
        //            result = objSet.get(0);
        //        }else{
        //            result = null;
        //        }

        //        BenutzerGruppe result;
        //        try {
        //            result = (BenutzerGruppe) entityManager.createNamedQuery("LoadBenutzerGruppeByName")
        //                    .setParameter("gruppenname", benutzerGruppenName)
        //                    .getSingleResult();
        //        } catch (Exception e) {
        //            logger.error(e);
        //            result = null;
        //        }
        //        return result;
        //        return result;
    }
}