package org.rapidpm.orm.prj.projectmanagement.execution.issuetracing;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Dec 16, 2010
 * Time: 4:18:47 PM
 * To change this template use File | Settings | File Templates.
 */

import org.rapidpm.orm.prj.BaseDAOTest;
import org.rapidpm.orm.DaoFactory;
import org.rapidpm.orm.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.orm.prj.projectmanagement.planning.PlannedProjectName;
import org.rapidpm.orm.system.security.Benutzer;
import org.junit.Test;

import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class ProjectDAOTest extends BaseDAOTest {

    @Test
    public void testPrjRegistrationen() throws Exception {
        final List<PlannedProject> projectList = daoFactory.getProjectDAO().loadAllEntities();
        assertNotNull(projectList);
        System.out.println("projectList.size() = " + projectList.size());
        assertFalse(projectList.isEmpty());
    }


    @Test
    public void testSaveProject() throws Exception {
        final PlannedProject p = new PlannedProject();
        p.setActive(true);
        //        p.setCreated(new Date());
        p.setFakturierbar(true);
        p.setInfo("Info");

        final List<PlannedProjectName> namen = new ArrayList<PlannedProjectName>();
        final PlannedProjectName n = new PlannedProjectName();
        n.setNamepart("PrjName");
        n.setOrdernr(0);
        namen.add(n);
        p.setPlannedProjectName(namen);

        final DaoFactory daoFactory = new DaoFactory();
        daoFactory.setEm(entityManager);

        final Benutzer benutzer = daoFactory.getBenutzerDAO().loadBenutzer("sven.ruppert", "NeoScioPortal");
        p.setCreator(benutzer);
        //        p.setIssues();
        p.setMandantengruppe(benutzer.getMandantengruppe());
        p.setResponsiblePerson(benutzer);

        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(p);
        transaction.commit();

    }

    @Test
    public void testDeleteProject() throws Exception {
        final DaoFactory daoFactory = new DaoFactory();
        daoFactory.setEm(entityManager);

        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        final List<PlannedProject> projects = daoFactory.getProjectDAO().loadProjectsFor("NeoScioPortal");
        for (final PlannedProject project : projects) {
            entityManager.remove(project);
        }
        transaction.commit();

    }
}
