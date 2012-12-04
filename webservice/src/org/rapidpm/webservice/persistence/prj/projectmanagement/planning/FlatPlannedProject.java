package org.rapidpm.webservice.persistence.prj.projectmanagement.planning;

import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webservice.mapping.FlatEntity;

/**
 * User: Alexander Vos
 * Date: 03.12.12
 * Time: 14:00
 */
public class FlatPlannedProject extends FlatEntity<PlannedProject> {
    // TODO
    private String projektName;

    public String getProjektName() {
        return projektName;
    }

    public void setProjektName(final String projektName) {
        this.projektName = projektName;
    }

    @Override
    public void fromEntity(final PlannedProject plannedProject) {
        id = plannedProject.getId();
        projektName = plannedProject.getProjektName();
    }

    @Override
    public void toEntity(final PlannedProject plannedProject, final DaoFactory daoFactory) {
        plannedProject.setProjektName(projektName);
    }
}
