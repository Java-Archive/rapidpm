package org.rapidpm.persistence.security;

import org.rapidpm.persistence.BaseDAOTest;
import org.rapidpm.persistence.system.security.Mandantengruppe;
import org.rapidpm.persistence.system.security.MandantengruppeDAO;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * MandantengruppeDAO Tester.
 *
 * @author Manfred Lindenberg
 * @version 1.0
 * @since <pre>02/26/2010</pre>
 */
public class MandantengruppeDAOTest extends BaseDAOTest {

//    public static Test suite() {
//        return new TestSuite(MandantengruppeDAOTest.class);
//    }

    @Test
    public void testLoadMandantengruppe() throws Exception {
        final MandantengruppeDAO mandantengruppeDAO = daoFactory.getMandantengruppeDAO();
        final Mandantengruppe mandantengruppe = mandantengruppeDAO.loadMandantengruppe("KIO Oberberg");
        assertNotNull(mandantengruppe);
        assertEquals("KIO Oberberg", mandantengruppe.getMandantengruppe());

    }

    @Test
    public void testAddMandantengruppe() throws Exception {
        final Mandantengruppe m = new Mandantengruppe();
        m.setMandantengruppe("TestMandantengruppe");
        daoFactory.saveOrUpdateTX(m);
    }

    @Test
    public void testModifyMandantengruppe() throws Exception {
        final Mandantengruppe m = daoFactory.getMandantengruppeDAO().loadMandantengruppe("TestMandantengruppe");
        m.setMandantengruppe("TestMandantengruppe_Modified");
        daoFactory.saveOrUpdateTX(m);

    }

    @Test
    public void testLoadAllRevisions() throws Exception {
        final List<Mandantengruppe> allRevisions = daoFactory.getMandantengruppeDAO().loadAllRevisionsFor(171L);
        for (final Mandantengruppe revision : allRevisions) {
            System.out.println("revision = " + ToStringBuilder.reflectionToString(revision, ToStringStyle.SIMPLE_STYLE));
        }


    }
}