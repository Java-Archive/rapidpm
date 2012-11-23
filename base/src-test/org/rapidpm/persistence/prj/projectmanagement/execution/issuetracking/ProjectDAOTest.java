package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Dec 16, 2010
 * Time: 4:18:47 PM
 * To change this template use File | Settings | File Templates.
 */

import org.junit.Ignore;
import org.junit.Test;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.prj.BaseDAOTest;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProjectName;
import org.rapidpm.persistence.system.security.Benutzer;

import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@Ignore
public class ProjectDAOTest extends BaseDAOTest {

    @Test
    public void testPrjRegistrationen() throws Exception {
        final List<PlannedProject> projectList = daoFactoryFactory.getProjectDAO().loadAllEntities();
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

        final Set<PlannedProjectName> namen = new HashSet<PlannedProjectName>();
        final PlannedProjectName n = new PlannedProjectName();
        n.setNamepart("PrjName");
        n.setOrdernr(0);
        namen.add(n);
        p.setPlannedProjectName(namen);

        final DaoFactory daoFactoryFactory = new DaoFactory();
        daoFactoryFactory.setEntityManager(entityManager);

        final Benutzer benutzer = daoFactoryFactory.getBenutzerDAO().loadBenutzer("sven.ruppert", "NeoScioPortal");
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
        final DaoFactory daoFactoryFactory = new DaoFactory();
        daoFactoryFactory.setEntityManager(entityManager);

        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        final List<PlannedProject> projects = daoFactoryFactory.getProjectDAO().loadProjectsFor("NeoScioPortal");
        for (final PlannedProject project : projects) {
            entityManager.remove(project);
        }
        transaction.commit();

    }
}
