package org.rapidpm.persistence.prj.stammdaten.organisationseinheit;

import org.rapidpm.persistence.BaseDaoFactory;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 03.03.2010
 *        Time: 16:33:54
 */

public class BrancheAssocDAO extends BaseDaoFactory.BaseDAO<Long, BrancheAssoc> {
    private static final Logger logger = Logger.getLogger(BrancheAssocDAO.class);

    public BrancheAssocDAO(final EntityManager entityManager) {
        super(entityManager, BrancheAssoc.class);
    }

    //    public BranchenAssoc loadBranchenAssocForWZ2008(final String branchenSchluessel, final String klassifizierung) {
    //
    //
    //        BranchenAssoc branchenAssoc = null;
    //        try {
    //            final List<BranchenAssoc> resultList = entityManager.createNamedQuery("LoadBranchenAssocForWZ2008Codes")
    //                    .setParameter("branchenSchluessel", branchenSchluessel)
    //                    .setParameter("klassifizierung", klassifizierung)
    //                    .getResultList();
    //            if (resultList.size() == 1) {
    //                branchenAssoc = resultList.get(0);
    //            } else {
    //                logger.warn("Kombination BranchenSchlüssel zu Klassifizierung wurde nicht gfunden.");
    //            }
    //        } catch (Exception e) {
    //            //TODO MEldung an die Oberfläche das Stammdaten fehlen.
    //            logger.error("BranchenAssoc konnte nicht gefunden werden : " + e);
    //            logger.error("BranchenAssoc Paramater branchenSchluessel/klassifizierung : " + branchenSchluessel + '/' + klassifizierung);
    //        }
    //        return branchenAssoc;
    //    }


}
