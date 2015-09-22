package org.rapidpm.persistence.system.security;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import java.security.InvalidKeyException;

/**
 * NeoScio
 * User: Manfred
 * Date: 26.02.2010
 * Time: 12:17:07
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@me.com
 */
public class MandantengruppeDAO extends DAO<Long, Mandantengruppe> {
  private static final Logger logger = Logger.getLogger(MandantengruppeDAO.class);

  public MandantengruppeDAO(final OrientGraph orientDB) {
    super(orientDB, Mandantengruppe.class);
  }

  public Mandantengruppe loadMandantengruppe(final Mandantengruppe mandantengruppe) {
    return loadMandantengruppe(mandantengruppe.getMandantengruppe());
    //        return createWhereClause().eq("mandantengruppe", mandantengruppenName.getMandantengruppe()).findUnique();
  }

  //    public List<Mandantengruppe> loadAllEntities() {
  //        return super.loadAllEntities(Mandantengruppe.class);
  //    }

  //    public Mandantengruppe loadMandantengruppeByBenutzerID(long id) {
  //        orientDB.query(new Predicate<Benutzer>(){
  //            @Override
  //            public boolean match(Benutzer mandantengruppe) {
  //
  //            }
  //        })
  //        final Query query = orientDB.createNamedQuery("LoadMandantengruppeByBenutzerID")
  //                .setParameter("id", id);
  //        Mandantengruppe m;
  //        try {
  //            m = (Mandantengruppe) query.getSingleResult();
  //        } catch (Exception e) {
  //            logger.error(e);
  //            m = null;
  //        }
  //        return m;
  //    }

  public Mandantengruppe loadMandantengruppe(final String mandantengruppenName) {
//        final TypedQuery<Mandantengruppe> typedQuery = orientDB.createQuery(
//                "from Mandantengruppe  m where m.mandantengruppe=:mandantengruppenName",
//                Mandantengruppe.class).setParameter("mandantengruppenName", mandantengruppenName);
//        return getSingleResultOrNull(typedQuery);
    return null;
    //        return createWhereClause().eq("mandantengruppe", mandantengruppenName).findUnique();
    //        Mandantengruppe mandantengruppe = null;
    //        final ObjectSet<Mandantengruppe> objSet = orientDB.query(new Predicate<Mandantengruppe>() {
    //            @Override
    //            public boolean match(final Mandantengruppe mandantengruppe) {
    //                return mandantengruppe.getMandantengruppe().equals(mandantengruppenName);
    //            }
    //        });
    //        final int size = objSet.size();
    //        if(size==1){
    //            mandantengruppe = objSet.get(0);
    //        }else{
    //            mandantengruppe = null;
    //        }
    //
    ////        try {
    ////            mandantengruppe = (Mandantengruppe) orientDB.createNamedQuery("LoadMandantengruppe")
    ////                    .setParameter("mandantengruppe", mandantengruppenName)
    ////                    .getSingleResult();
    ////        } catch (Exception e) {
    ////            logger.warn("Konnten mandantengruppe nicht laden " + e);
    ////        }
    //        return mandantengruppe;
  }

  @Override
  public Mandantengruppe loadFull(Mandantengruppe entity) throws InvalidKeyException, NotYetImplementedException {
    return findByID(entity.getId(), false);
  }

  @Override
  public Mandantengruppe createEntityFull(Mandantengruppe entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
    return createEntityFlat(entity);
  }
}