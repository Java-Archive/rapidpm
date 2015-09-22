/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.persistence.prj.stammdaten.organisationseinheit;
/**
 * NeoScio
 * User: svenruppert
 * Date: 12.03.2010
 * Time: 23:42:22
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 */

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.ormviews.ViewOrgEinheitHptTaetigkeitsfeld;
import org.rapidpm.ormviews.ViewOrgEinheitWebDomain;
import org.rapidpm.persistence.DAO;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.gesellschaftsformen.Gesellschaftsform;

import java.security.InvalidKeyException;
import java.util.List;

public class OrganisationseinheitDAO extends DAO<Long, Organisationseinheit> {
  private static final Logger logger = Logger.getLogger(OrganisationseinheitDAO.class);

  public OrganisationseinheitDAO(final OrientGraph orientDB) {
    super(orientDB, Organisationseinheit.class);
  }


  public Organisationseinheit findByID(final Long oid, final boolean fetcheagerAll) {
//        final Organisationseinheit byID = findByID(oid);
//        if (fetcheagerAll) {
//            return fetchEagerAll(byID);
//        } else {
//            return byID;
//        }
    return null;
  }

  public List<Organisationseinheit> loadWithOIDList(final List<Long> oidList, final boolean fetcheagerAll) {
//        final List<Organisationseinheit> organisationseinheitList = loadWithOIDList(oidList);
//        if (fetcheagerAll) {
//            return fetchEagerAll(organisationseinheitList);
//        } else {
//            return organisationseinheitList;
//        }
    return null;
  }

  public List<Organisationseinheit> loadAllEntities(final boolean fetcheagerAll) {
//        final List<Organisationseinheit> organisationseinheitList = loadAllEntities();
//        if (fetcheagerAll) {
//            return fetchEagerAll(organisationseinheitList);
//        } else {
//            return organisationseinheitList;
//        }
    return null;
  }

  public List<Organisationseinheit> loadOrganisationseinheitenForMandantengruppe(final String mandantengruppenName, final boolean fetcheagerAll) {
    final List<Organisationseinheit> organisationseinheitList = loadOrganisationseinheitenForMandantengruppe(mandantengruppenName);
    if (fetcheagerAll) {
      return fetchEagerAll(organisationseinheitList);
    } else {
      return organisationseinheitList;
    }
  }

  public List<Organisationseinheit> loadOrganisationseinheitenForMandantengruppe(final String mandantengruppenName) {
    //final TypedQuery<Organisationseinheit> query = orientDB.createQuery("from Organisationseinheit o inner join o.mandantengruppen m where m.mandantengruppe=:mandantengruppenName", Organisationseinheit.class)
    // final TypedQuery query = orientDB.createQuery("select o from Organisationseinheit o inner join o.mandantengruppen m where m.mandantengruppe=:mandantengruppenName", Organisationseinheit.class).setParameter("mandantengruppenName",
//        final TypedQuery query = orientDB.createQuery("select o from Organisationseinheit o where o.mandantengruppe.mandantengruppe=:mandantengruppenName", Organisationseinheit.class).setParameter("mandantengruppenName", mandantengruppenName);
//        return query.getResultList();
    return null;

    //        return createWhereClause().eq("mandantengruppen.mandantengruppe", mandantengruppenName).findList();
    //        final String sql = "select id from organisationseinheiten o where o.id in ( " +
    //                "select organisationseinheit_id from organisationseinheit_mandantengr om where om.mandantengruppe_id ="+SQLCreator.mandantengruppeID(mandantengruppenName)+" )";
    //        return createQuery(sql).findList();
    //        final ObjectSet<Organisationseinheit> objSet = orientDB.query(new Predicate<Organisationseinheit>() {
    //            @Override
    //            public boolean match(final Organisationseinheit organisationseinheiten) {
    //                final Set<Mandantengruppe> mandantengruppes = organisationseinheiten.getMandantengruppen();
    //                for (final Mandantengruppe mandantengruppe : mandantengruppes) {
    //                    final boolean b = mandantengruppe.getMandantengruppe().equals(mandantengruppenName);
    //                    if (b) {
    //                        return true;
    //                    } else {
    //                        //
    //                    }
    //                }
    //                return false;
    //            }
    //        });
    //
    //        return objSet;
  }

  private List<Organisationseinheit> fetchEagerAll(final List<Organisationseinheit> orgeinheiten) {
    for (final Organisationseinheit orgeinheit : orgeinheiten) {
      fetchEagerAll(orgeinheit);
    }
    return orgeinheiten;
  }

  private Organisationseinheit fetchEagerAll(final Organisationseinheit orgeinheit) {
    if (orgeinheit == null) {

    } else {
      orgeinheit.getWebdomains().size();
      orgeinheit.getAdressen().size();
      if (orgeinheit.getAusbildungseinheit() == null) {

      } else {
        orgeinheit.getAusbildungseinheit().getId();
      }
      orgeinheit.getBrancheAssocs().size();
      final Gesellschaftsform gesellschaftsform = orgeinheit.getGesellschaftsform();
      if (gesellschaftsform != null) {
        if (gesellschaftsform.getId() == null) {
        } else {
          orgeinheit.getGesellschaftsform().getId();
        }
      } else {
        if (logger.isDebugEnabled()) {
          logger.debug("Orgeinheit ohne Gesellschaftsform  OrgID:" + orgeinheit.getId());
        }
      }
      orgeinheit.getKommunikationsServiceUIDs().size();
      //            orgeinheit.getMandantengruppen().size();
      orgeinheit.getMetadata().size();
      //            orgeinheit.getPositionen().size();
      orgeinheit.getTaetigkeitsfeldAssocs().size();
      if (orgeinheit.getVerwaltungseinheit() == null) {

      } else {
        orgeinheit.getVerwaltungseinheit().getId();
      }
      if (orgeinheit.getWirtschaftseinheit() == null) {

      } else {
        orgeinheit.getWirtschaftseinheit().getId();
      }
    }
    return orgeinheit;
  }

  public List<Organisationseinheit> loadOrganisationseinheitForWebDomain(final String webdomain) {
    //return orientDB.createQuery("select o from Organisationseinheit o inner join o.webDomains w where w.domainName=:webdomain", Organisationseinheit.class).setParameter("webdomain", webdomain).getResultList();
    return null;
  }

  public List<Organisationseinheit> loadOrganisationseinheitForWebDomain(final String webdomain, final String mandantengruppe) {
//        return orientDB.createQuery("select o from Organisationseinheit o inner join o.webDomains w where w.domainName=:webdomain and o.mandantengruppe.mandantengruppe=:mandantengruppe", Organisationseinheit.class)
//                .setParameter("webdomain", webdomain)
//                .setParameter("mandantengruppe", mandantengruppe).getResultList();
    return null;
  }

  public List<ViewOrgEinheitWebDomain> loadViewOrgEinheitWebDomain(final String mandantengruppe) {
//        final List<ViewOrgEinheitWebDomain> resultList = orientDB.createQuery("select NEW org.rapidpm.ormviews.ViewOrgEinheitWebDomain(o.organisationsName, o.id, w.id, w.domainName) " +
//                "from Organisationseinheit o inner join o.webDomains w where o.id in (select distinct(o.id) from Organisationseinheit o where o.mandantengruppe.mandantengruppe=:mandantengruppe)")
//                .setParameter("mandantengruppe",
//                        mandantengruppe).getResultList();
//        return resultList;
    return null;
  }

  public List<ViewOrgEinheitHptTaetigkeitsfeld> loadViewOrgEinheitHptTaetigkeitsfeld(final String mandantengruppe) {
//        final
//        List<ViewOrgEinheitHptTaetigkeitsfeld>
//                resultList =
//                orientDB.createQuery("select NEW org.rapidpm.ormviews.ViewOrgEinheitHptTaetigkeitsfeld(o.organisationsName, o.id, ta.taetigkeitsfeld.namen, ta.taetigkeitsfeld.id) " + "from Organisationseinheit  o inner join o.taetigkeitsfeldAssocs ta "
//                        + "where o.id in (select distinct(o.id) from Organisationseinheit o where o.mandantengruppe.mandantengruppe=:mandantengruppe) " + "and ta.klassifizierung.klassifizierung='Haupttätigkeit'").setParameter(
//                        "mandantengruppe",
//                        mandantengruppe).getResultList();
//        return resultList;
    return null;
  }

  public List<Organisationseinheit> loadOrganisationseinheitenForMandantengruppe(final Long mandantengruppe, final boolean fetcheagerAll) {
    final List<Organisationseinheit> organisationseinheitList = loadOrganisationseinheitenForMandantengruppe(mandantengruppe);
    if (fetcheagerAll) {
      return fetchEagerAll(organisationseinheitList);
    } else {
      return organisationseinheitList;
    }
  }

  public List<Organisationseinheit> loadOrganisationseinheitenForMandantengruppe(final Long mandantengruppe) {
    //final TypedQuery<Organisationseinheit> query = orientDB.createQuery("from Organisationseinheit o inner join o.mandantengruppen m where m.mandantengruppe=:mandantengruppe", Organisationseinheit.class)
    //final TypedQuery query = orientDB.createQuery("select o from Organisationseinheit o inner join o.mandantengruppen m where m.id=:mandantengruppe", Organisationseinheit.class).setParameter("mandantengruppe", mandantengruppe);
//        final TypedQuery query = orientDB.createQuery("select o from Organisationseinheit o where o.mandantengruppe.id=:mandantengruppe", Organisationseinheit.class).setParameter("mandantengruppe", mandantengruppe);
//        return query.getResultList();
    return null;
  }

  public List<Organisationseinheit> loadOrganisationseinheitenFor(final String mandantengruppenName, final String taetigkeitsfeld, final boolean fetcheagerAll) {
    final List<Organisationseinheit> organisationseinheitList = loadOrganisationseinheitenFor(mandantengruppenName, taetigkeitsfeld);
    if (fetcheagerAll) {
      return fetchEagerAll(organisationseinheitList);
    } else {
      return organisationseinheitList;
    }
  }

  public List<Organisationseinheit> loadOrganisationseinheitenFor(final String mandantengruppenName, final String taetigkeitsfeld) {
//        final List<Organisationseinheit> result =
//                //orientDB.createQuery("select o from Organisationseinheit o " + "where o.id in (select distinct(o.id) from Organisationseinheit o inner join o.mandantengruppen m where m.mandantengruppe=:mandantengruppenName) " + "and o.id in
//                // (select distinct(o.id) from Organisationseinheit o inner join o.taetigkeitsfeldAssocs ta  where ta.taetigkeitsfeld.namen=:taetigkeitsfeld)",
//                orientDB.createQuery("select o from Organisationseinheit o " + "where o.id in (select distinct(o.id) from Organisationseinheit o where o.mandantengruppe.mandantengruppe=:mandantengruppenName) " + "and o.id in (select distinct(o.id) from Organisationseinheit o inner join o.taetigkeitsfeldAssocs ta  where ta.taetigkeitsfeld.namen=:taetigkeitsfeld)",
//                        Organisationseinheit.class)
//                        //                orientDB.createQuery("select o from Organisationseinheit o " +
//                        //                                          "inner join o.mandantengruppen m " +
//                        //                                          "inner join o.taetigkeitsfeldAssocs ta " +
//                        //                                          "where m.mandantengruppe=:mandantengruppenName " +
//                        //                                          "and ta.taetigkeitsfeld.namen=:taetigkeitsfeld " +
//                        //                                          "and ta.mandantengruppe.mandantengruppe=:mandantengruppenName",
//                        //                                          Organisationseinheit.class)
//                        //"and ta.mandantengruppe.mandantengruppe=:mandantengruppenName")
//                        .setParameter("mandantengruppenName", mandantengruppenName).setParameter("taetigkeitsfeld", taetigkeitsfeld).getResultList();
//        return result;
    return null;
    //        return createWhereClause()
    //                .eq("mandantengruppen.mandantengruppe", mandantengruppenName)
    //                .eq("taetigkeitsfeldAssocs.taetigkeitsfeld.namen", taetigkeitsfeld)
    //                .findList();

    //       final String sql = "select id from organisationseinheiten o where o.id in (\n" +
    //               "select organisationseinheit_id from taetigkeitsfeld_assoc ta " +
    //               "where ta.taetigkeitsfeld_id =" + SQLCreator.taetigkeitsfeldID(taetigkeitsfeld)+
    //               "and ta.mandantengruppe_id = ("+SQLCreator.mandantengruppeID(mandantengruppenName)+"))";
    //        return createQuery(sql).findList();

    //        final ObjectSet<Organisationseinheit> objSet = orientDB.query(new Predicate<Organisationseinheit>() {
    //            @Override
    //            public boolean match(final Organisationseinheit organisationseinheiten) {
    //                final Set<Mandantengruppe> mandantengruppes = organisationseinheiten.getMandantengruppen();
    //                for (final Mandantengruppe mandantengruppe : mandantengruppes) {
    //                    final boolean b = mandantengruppe.getMandantengruppe().equals(mandantengruppenName);
    //                    if (b) {
    //                        final Collection<TaetigkeitsfeldAssoc> assocCollection = organisationseinheiten.getTaetigkeitsfeldAssocs();
    //                        for (final TaetigkeitsfeldAssoc taetigkeitsfeldAssoc : assocCollection) {
    //                            final Mandantengruppe mandantengruppeTmp = taetigkeitsfeldAssoc.getMandantengruppe();
    //                            if(mandantengruppeTmp.getMandantengruppe().equals(mandantengruppenName)){
    //                                final Taetigkeitsfeld taetigkeitsfeldTmp = taetigkeitsfeldAssoc.getTaetigkeitsfeld();
    //                                if(taetigkeitsfeldTmp.getNamen().equals(taetigkeitsfeld)){
    //                                    return true;
    //                                }else{
    //                                    //
    //                                }
    //                            }else{
    //                                //
    //                            }
    //                        }
    //
    //                    } else {
    //                        //
    //                    }
    //                }
    //                return false;
    //            }
    //        });
    //        return objSet;

  }

  public Organisationseinheit loadOrganisationseinheitForID(final long id, final boolean fetcheagerAll) {
    final Organisationseinheit orgeinheit = loadOrganisationseinheitForID(id);
    if (fetcheagerAll) {
      return fetchEagerAll(orgeinheit);
    } else {
      return orgeinheit;
    }
  }

  public Organisationseinheit loadOrganisationseinheitForID(final long id) {
//        final TypedQuery<Organisationseinheit> query = orientDB.createQuery("from Organisationseinheit o where o.id=:id", Organisationseinheit.class);
//        final TypedQuery<Organisationseinheit> typedQuery = query.setParameter("id", id);
//        return getSingleResultOrNull(typedQuery);
    return null;

    //        return createWhereClause().eq("id", id).findUnique();
    //        final ObjectSet<Organisationseinheit> objSet = orientDB.query(new Predicate<Organisationseinheit>() {
    //                    @Override
    //                    public boolean match(final Organisationseinheit organisationseinheiten) {
    //                        final boolean b = organisationseinheiten.getId().equals(id);
    //                        if(b){
    //                            return true;
    //                        }else{
    //                            return false;
    //                        }
    //                    }
    //                });
    //        final Organisationseinheit organisationseinheiten;
    //        final int size = objSet.size();
    //        if(size == 0){                   //TODO per Exception lösen
    //            organisationseinheiten = null;
    //        }else if(size >1){
    //            organisationseinheiten = null;
    //        }else{
    //            organisationseinheiten = objSet.get(0);
    //        }
    //        return organisationseinheiten;
  }

  @Override
  public Organisationseinheit loadFull(Organisationseinheit entity) throws InvalidKeyException, NotYetImplementedException {
    throw new NotYetImplementedException();
  }

  @Override
  public Organisationseinheit createEntityFull(Organisationseinheit entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
    throw new NotYetImplementedException();
  }
}
