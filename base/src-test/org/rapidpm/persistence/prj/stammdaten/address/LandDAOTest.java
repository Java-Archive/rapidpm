package org.rapidpm.persistence.prj.stammdaten.address;

/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Nov 19, 2010
 * Time: 3:31:50 PM
 * To change this template use File | Settings | File Templates.
 */

public class LandDAOTest {

//    @Override
//    public void setUp() throws Exception {
//        super.setUp();
//
//        final Land landDE = new Land();
//        landDE.setIsoCode("DE");
//        landDE.setName("Deutschland");
//
//        final Land landEN = new Land();
//        landEN.setIsoCode("EN");
//        landEN.setName("England");
//
//        final EntityTransaction transaction = entityManager.getTransaction();
//        transaction.begin();
//        entityManager.persist(landDE);
//        entityManager.persist(landEN);
//        transaction.commit();
//    }
//
//    @Override
//    public void tearDown() throws Exception {
//        super.tearDown();
//
//        final LandDAO dao = daoFactory.getLandDAO();
//
//        final EntityTransaction transaction = entityManager.getTransaction();
//        transaction.begin();
//        entityManager.remove(dao.loadLandForIsoCode("DE"));
//        entityManager.remove(dao.loadLandForIsoCode("EN"));
//        transaction.commit();
//    }
//
//    @Test
//    public void testLoadLandForIsoCode() throws Exception {
//        final LandDAO dao = daoFactory.getLandDAO();
//        final Land land = dao.loadLandForIsoCode("DE");
//        assertNotNull(land);
//        assertEquals("DE", land.getIsoCode());
//        System.out.println("land = " + land);
//    }
//
//    @Test
//    public void testFindbyID() throws Exception {
//        final LandDAO dao = daoFactory.getLandDAO();
//        final Land land = dao.loadLandForIsoCode("DE");
//        assertNotNull(land);
//        assertEquals("DE", land.getIsoCode());
//        final Land byID = dao.findByID(land.getId());
//        assertNotNull(byID);
//        System.out.println("byID = " + byID);
//    }
//
//    @Test
//    public void testUpdate() throws Exception {
//        final LandDAO dao = daoFactory.getLandDAO();
//        Land land = dao.loadLandForIsoCode("DE");
//        assertNotNull(land);
//        assertEquals("DE", land.getIsoCode());
//        land.setName("Germany");
//
//        final EntityTransaction transaction = entityManager.getTransaction();
//        transaction.begin();
//        entityManager.persist(land);
//        transaction.commit();
//
//        land = dao.loadLandForIsoCode("DE");
//        assertNotNull(land);
//        assertEquals("DE", land.getIsoCode());
//        assertEquals("Germany", land.getName());
//    }
}
