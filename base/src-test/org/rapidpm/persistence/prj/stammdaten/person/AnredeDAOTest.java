package org.rapidpm.persistence.prj.stammdaten.person;

import org.rapidpm.persistence.prj.BaseDAOTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * AnredeDAO Tester.
 *
 * @author Sven Ruppert
 * @version 1.0
 * @since <pre>02/20/2010</pre>
 */
public class AnredeDAOTest extends BaseDAOTest {

    /**
     * Method: loadAnredeHerr()
     *
     * @throws Exception
     */
    @Test
    public void testLoadAnredeHerr() throws Exception {
        final AnredeDAO dao = daoFactory.getAnredeDAO();
        final Anrede anrede = dao.loadAnredeHerr();
        assertNotNull(anrede);
        assertEquals("Herr", anrede.getAnrede());
    }

    /**
     * Method: loadAnredeFrau()
     *
     * @throws Exception
     */
    @Test
    public void testLoadAnredeFrau() throws Exception {
        final AnredeDAO dao = daoFactory.getAnredeDAO();
        final Anrede anrede = dao.loadAnredeFrau();
        assertNotNull(anrede);
        assertEquals("Frau", anrede.getAnrede());
    }


//    public static Test suite() {
//        return new JUnit4TestAdapter(AnredeDAOTest.class);
//    }
}
