package org.rapidpm.orm;

import org.apache.log4j.Logger;


/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 25.06.2010
 *        Time: 21:28:31
 */

public class Db4oTest {
    private static final Logger logger = Logger.getLogger(Db4oTest.class);

    public static void main(final String[] args) {


//        final Set<InternalClass> stringSet = new HashSet<InternalClass>();
//        stringSet.add(new InternalClass());
//        stringSet.add(new InternalClass());
//        stringSet.add(new InternalClass());
//
//        final Set<Set> setSet = new HashSet<Set>();
//        setSet.add(stringSet);
//
//
//        final File dbFile = new File("testdb.db");
//        if(dbFile.exists()){
//            dbFile.delete();
//        }else{
//
//        }
//
//        final ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "testdb.db");
//        try { // do something with db4o
//            db.store(setSet);
//            db.commit();
//        } finally {
//            db.close();
//        }
//
//
//        final ObjectContainer dbReloaded = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "testdb.db");
//        try { // do something with db4o
//            final ObjectSet<HashSet> sets = dbReloaded.query(HashSet.class);
//            final Iterator<HashSet> setIterator = sets.iterator();
//            while (setIterator.hasNext()) {
//                final Set set = setIterator.next();
//                System.out.println("set = " + set);
//            }
//
//        } finally {
//            db.close();
//        }


//
//        final ObjectContainer dbReloaded = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "testdb.db");
//        try { // do something with db4o
//            final EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
//            final CommonConfiguration commonConfiguration = configuration.common();
//            final ObjectClass objectClass = commonConfiguration.objectClass(Organisationseinheit.class);
//            objectClass.cascadeOnActivate(true);
//            objectClass.cascadeOnUpdate(true);
////            objectClass.updateDepth(100);
////            objectClass.maximumActivationDepth(100);
////            objectClass.callConstructor(true);
//            commonConfiguration.automaticShutDown(true);
//
//            final Query query = dbReloaded.query();
//            query.constrain(Organisationseinheit.class);
//            final ObjectSet<Organisationseinheit> organisationseinheits = query.execute();
//            for (final Organisationseinheit organisationseinheiten : organisationseinheits) {
//                dbReloaded.ext().activate(organisationseinheiten);
//                final String orgName = organisationseinheiten.getOrganisationsName();
////                System.out.println("orgName = " + orgName);
////                System.out.println("organisationseinheiten = " + organisationseinheiten);
//            }
//
//
//            final Query query1 = dbReloaded.query();
//            query1.constrain(Benutzer.class);
//            final ObjectSet<Benutzer> objects = query1.execute();
//            System.out.println("objects = " + objects);
//
//
//            final ObjectSet<Benutzer> benutzerSet = dbReloaded.query(new Predicate<Benutzer>() {
//                @Override
//                public boolean match(final Benutzer benutzer) {
//                    return benutzer.getId().equals(1L);
//                }
//            });
//            final Iterator<Benutzer> iterator = benutzerSet.iterator();
//            while (iterator.hasNext()) {
//                final Benutzer benutzer = iterator.next();
//                System.out.println("benutzer = " + benutzer);
//            }
//
//        } finally {
//            dbReloaded.close();
//        }


    }


//    private static class InternalClass extends BaseEntity{
//
//        private long id;
//
//        @Override
//        public Long getId() {
//            return id;
//        }
//
//        @Override
//        public void setId(final Long id) {
//            this.id = id;
//        }
//
//        @Override
//        public String toString() {
//            final StringBuilder sb = new StringBuilder();
//            sb.append("InternalClass");
//            sb.append("{id=").append(id);
//            sb.append('}');
//            return sb.toString();
//        }
//    }
}