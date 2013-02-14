package org.rapidpm.persistence.prj.projectmanagement.execution.issuetrackingFactories;

import org.rapidpm.persistence.EntityFactory;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProjectName;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: cernst
 * Date: 12.10.11
 * Time: 13:31
 */
public class ProjectNameFactory extends EntityFactory<PlannedProjectName> {
    public ProjectNameFactory() {
        super(PlannedProjectName.class);
    }

    @Override
    public PlannedProjectName createRandomEntity() {
        final PlannedProjectName projectName = new PlannedProjectName();
        projectName.setNamepart(combineStringWithNextIndex(RND.nextWord(5, 12)));
        projectName.setOrdernr(RND.nextInt(10000, 99999));
        return projectName;
    }

    @Test
    public void testCreate() throws Exception {
        System.out.println(createRandomEntity());
    }
}
