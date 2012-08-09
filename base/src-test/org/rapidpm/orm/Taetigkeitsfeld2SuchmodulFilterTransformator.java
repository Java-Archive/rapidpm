package org.rapidpm.orm;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 29.11.11
 * Time: 13:18
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;

/**
 * Nur einmalig f Transformation
 */
@Deprecated
public class Taetigkeitsfeld2SuchmodulFilterTransformator extends BaseDAOTest {
    private static final Logger logger = Logger.getLogger(Taetigkeitsfeld2SuchmodulFilterTransformator.class);

//    @Test
//    public void testTransformation() throws Exception {
//        final List<WebDomain> finalDomainList = new ArrayList<WebDomain>();
//
//        final List<Organisationseinheit> orgList = daoFactory.getOrganisationseinheitDAO().loadAllEntities();
//        for (final Organisationseinheit organisationseinheit : orgList) {
//            final List<WebDomain> webdomains = organisationseinheit.getWebdomains();
//            final List<TaetigkeitsfeldAssoc> taetigkeitsfeldAssocs = organisationseinheit.getTaetigkeitsfeldAssocs();
//            for (final TaetigkeitsfeldAssoc taetigkeitsfeldAssoc : taetigkeitsfeldAssocs) {
//                final Taetigkeitsfeld taetigkeitsfeld = taetigkeitsfeldAssoc.getTaetigkeitsfeld();
//                final List<SuchmodulFilter> filterList = daoFactory.getSuchmodulFilterDAO().loadByName(taetigkeitsfeld.getNamen());
//                if(filterList.isEmpty() || filterList.size() >1){
//                    System.out.println("taetigkeitsfeld = " + taetigkeitsfeld);
//                    System.out.println("organisationseinheit = " + organisationseinheit);
//                    final Mandantengruppe mandantengruppe = organisationseinheit.getMandantengruppe();
//                    System.out.println("organisationseinheit.getMandantengruppe() = " + mandantengruppe);
//                    System.out.println("webdomains = " + webdomains);
//                    System.out.println("filterList = " + filterList);
//                    final Suchmodul suchmodul = daoFactory.getSuchmodulDAO().loadSuchmodul(mandantengruppe.getMandantengruppe());
//                    final List<SuchmodulFilterGruppe> suchmodulFilterGruppe = suchmodul.getSuchmodulFilterGruppe();
//                    Assert.assertFalse(suchmodulFilterGruppe.isEmpty());
//                    for (final SuchmodulFilterGruppe filterGruppe : suchmodulFilterGruppe) {
//                        final List<SuchmodulFilter> filterliste = filterGruppe.getFilterliste();
//                        Assert.assertFalse(filterliste.isEmpty());
//                        for (final SuchmodulFilter suchmodulFilter : filterliste) {
//                            if(suchmodulFilter.getFiltername().equals(taetigkeitsfeld.getNamen())){
//                                for (final WebDomain webdomain : webdomains) {
//                                    webdomain.setSuchmodulfilter(suchmodulFilter);
//                                    finalDomainList.add(webdomain);
//                                }
//                                break;
//                            } else{
//                            }
//                        }
//                    }
//                    for (final WebDomain webdomain : webdomains) {
//                        final SuchmodulFilter suchmodulfilter = webdomain.getSuchmodulfilter();
//                        if(suchmodulfilter == null){
//                            System.out.println("webdomain = " + webdomain);
//                        } else{
//                        }
//                        Assert.assertNotNull(suchmodulfilter);
//                    }
//
//                } else{
//
//                    Assert.assertFalse(filterList.isEmpty());
//                    Assert.assertEquals(1, filterList.size());
//                    final SuchmodulFilter suchmodulFilter = filterList.get(0);
//                    for (final WebDomain webdomain : webdomains) {
//                        webdomain.setSuchmodulfilter(suchmodulFilter);
//                        finalDomainList.add(webdomain);
//                    }
//                }
//            }
//        }
//        System.out.println("finalDomainList = " + finalDomainList.size());
//
//        daoFactory.new Transaction(){
//            @Override
//            public void doTask() {
//                for (final WebDomain webDomain : finalDomainList) {
//                    daoFactory.saveOrUpdate(webDomain);
//                }
//            }
//        }.execute();
//
//    }
}
