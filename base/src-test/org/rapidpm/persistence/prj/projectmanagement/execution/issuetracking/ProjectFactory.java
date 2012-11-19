package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.rapidpm.persistence.EntityFactory;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProjectName;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: cernst
 * Date: 12.10.11
 * Time: 13:58
 */
public class ProjectFactory extends EntityFactory<PlannedProject> {
    public ProjectFactory() {
        super(PlannedProject.class);
    }

    @Override
    public PlannedProject createRandomEntity() {
        final PlannedProject plannedProject = new PlannedProject();
        plannedProject.setActive(RND.nextBoolean());
//        plannedProject.setCreator(); // TODO Benutzer
        plannedProject.setFakturierbar(RND.nextBoolean());
        plannedProject.setInfo(RND.nextSentence(5, 12, 5, 12));
//        plannedProject.setMandantengruppe(); // TODO Mandantengruppe
        //plannedProject.setPlannedProjectName(new ArrayList<PlannedProjectName>() {{
        //    final ProjectNameFactory factory = new ProjectNameFactory();
        //    add(factory.createRandomEntity());
        //    add(factory.createRandomEntity());
        //}});
//        plannedProject.setResponsiblePerson(); // TODO Benutzer
        return plannedProject;
    }

    @Test
    public void testCreate() throws Exception {
        System.out.println(createRandomEntity());
        System.out.println("Braucht BENUTZER und MANDANTENGRUPPE!!");
    }
}
